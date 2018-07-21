package commands;

/**
 * 
 * @authors Lightkeks, davidmc971
 * 		Bannt Spieler. Alles fertig. :D
 */

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.ChatUtils;
import util.DataManager;
import util.PermissionManager;
import util.Utils;

/*	This command handles requests for player 'ban's, 'unban's and 'ban-ip's.
 * 	It uses util. Banutils for interfacing with DataManager.
 * 
 * 
 */

public class CommandBan extends AbstractCommand {
	@Override
	public String[] getCommandLabels() {
		return new String[]{ "ban", "unban", "ban-ip" };
	}

	public CommandBan(ModularMSMF plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		//String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.NOPERM);

		if (sender.hasPermission(PermissionManager.getPermission("banplayer"))) {
			if (args.length == 0) {
				sender.sendMessage(errorPrefix+language.getString("general.missing_playername"));
				return true;
			}

			String reason = language.getString("commands.ban.defaultbanreason");
			UUID uuid = getPlayerUUIDByNameForBan(args[0]);
			if (uuid == null) {
				sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
				return true;
			} else if (DataManager.getPlayerCfg(uuid).getBoolean("banned")) {
				sender.sendMessage(errorPrefix+language.getString("commands.ban.alreadybanned"));
				return true;
			}

			switch (args.length) {
			case 1:
				banPlayer(uuid, reason, language);
				break;
			default:
				reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				banPlayer(uuid, reason, language);
				break;
			}
			sender.sendMessage(infoPrefix+language.getString("commands.ban.playerbanned").replaceAll("_player", args[0]).replaceAll("_reason", reason));
		}
		
		return true;
	}

	public static void banPlayer(UUID uuid, String reason, YamlConfiguration language) {
		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
		cfg.set("banned", true);
		cfg.set("reason", reason);
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				player.kickPlayer(language.getString("commands.ban.youwerebanned").replaceAll("_reason", reason));
			}
		}
	}

	private static UUID getPlayerUUIDByNameForBan(String name) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		return null;
	}
	/* FROM OLD UNBAN
	 * 
	 * @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if (!sender.hasPermission("modularmsmf.unban")) {
			sender.sendMessage(language.getString("general.nopermission"));
			return true;
		}
		
		switch(args.length){
		case 0:
			sender.sendMessage(language.getString("general.missing_playername"));
			break;
		case 1:
			for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){
					YamlConfiguration cfg = DataManager.getPlayerCfg(p.getUniqueId());
					if(cfg.getBoolean("banned")){
						cfg.set("banned", false);
						cfg.set("reason", "none");
						sender.sendMessage(language.getString("commands.unban.playerunbanned").replaceAll("_player", args[0]));
					} else {
						sender.sendMessage(language.getString("commands.unban.notbanned"));
					}
					return true;
				}
			}
			sender.sendMessage(language.getString("general.playernotfound"));
			break;
		case 2:
			if(args[0].toLowerCase().equals("uuid")){
				for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
					if(p.getUniqueId().toString().equals(args[1])){
						YamlConfiguration cfg = DataManager.getPlayerCfg(p.getUniqueId());
						if(cfg.getBoolean("banned")){
							cfg.set("banned", false);
							cfg.set("reason", "none");
							sender.sendMessage(language.getString("commands.unban.unbanuuid").replaceAll("_player", p.getName()));
						} else {
							sender.sendMessage(language.getString("commands.unban.notbanned"));
						}
						return true;
					}
				}
				sender.sendMessage(language.getString("commands.unban.playernotfounduuid"));
			} else {
				sender.sendMessage(language.getString("commands.unban.invalidcommand"));
			}
			break;
		default:
			sender.sendMessage(language.getString("general.toomanyarguments"));
			break;
		}
		return true;
	}
	 * 
	 * 
	 * 
	 */
}