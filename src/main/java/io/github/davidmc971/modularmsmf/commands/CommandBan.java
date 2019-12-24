package io.github.davidmc971.modularmsmf.commands;

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
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**	This command handles requests for player 'ban's, 'unban's and 'ban-ip's.
 * 	It uses util. Banutils for interfacing with DataManager.
 * 
 * @IDEAS:
 * @Lightkeks
 * Worldwide support for warning admins that any user who got banned <times> from <count servers> and which servers by checking?
 * need a database for checking
 * So an admin can check over this guy what's he doing?
 */

public class CommandBan extends AbstractCommand {
	
	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	
	@Override
	public String[] getCommandLabels() {
		return new String[]{ "ban", "unban", "ban-ip" };
	}

	public CommandBan(ModularMSMF plugin) {
		super(plugin);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		switch (commandLabel.toLowerCase()) {
		case "ban":
			return banCommand(sender, cmd, commandLabel, args);
		case "unban":
			return unbanCommand(sender, cmd, commandLabel, args);
		case "ban-ip":
			return false; //banCommand(sender, cmd, commandLabel, args);
		}
		return false;
	}
	
	
	public boolean banCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

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
			} else if (plugin.getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
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
			if(args[0].equalsIgnoreCase("banip")){
				if(sender.hasPermission(PermissionManager.getPermission("commands.ban.banip"))) {
					/**
					 * @TODO adding fnctn to ban ip from a player, if not already banned as normal name, otherwise ip can get banned too
					 * @Lightkeks
					 */
					
				}
			}
			sender.sendMessage(infoPrefix+language.getString("commands.ban.playerbanned").replaceAll("_player", args[0]).replaceAll("_reason", reason));
		} else {
			sender.sendMessage(PermissionManager.getPermission(noPermPrefix+language.getString("general.nopermission")));
		}
		
		return true;
	}
	
	public void banPlayerIp(UUID uuid, String name, YamlConfiguration language) {

	}

	public void banPlayer(UUID uuid, String reason, YamlConfiguration language) {
		YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		cfg.set("banned", true);
		cfg.set("reason", reason);
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				player.kickPlayer(language.getString("commands.ban.youwerebanned").replaceAll("_reason", reason));
			}
		}
	}

	private UUID getPlayerUUIDByNameForBan(String name) {
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

	public boolean unbanCommand(CommandSender sender, Command command, String label, String[] args) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if (!sender.hasPermission("modularmsmf.unban")) {
			sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			return true;
		}
		
		switch(args.length){
		case 0:
			sender.sendMessage(errorPrefix+language.getString("general.missing_playername"));
			break;
		case 1:
			for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){
					YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(p.getUniqueId());
					if(cfg.getBoolean("banned")){
						cfg.set("banned", false);
						cfg.set("reason", "none");
						sender.sendMessage(infoPrefix+language.getString("commands.unban.playerunbanned").replaceAll("_player", args[0]));
					} else {
						sender.sendMessage(errorPrefix+language.getString("commands.unban.notbanned"));
					}
					return true;
				}
			}
			sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
			break;
		case 2:
			if(args[0].toLowerCase().equals("uuid")){
				for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
					if(p.getUniqueId().toString().equals(args[1])){
						YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(p.getUniqueId());
						if(cfg.getBoolean("banned")){
							cfg.set("banned", false);
							cfg.set("reason", "none");
							sender.sendMessage(infoPrefix+language.getString("commands.unban.unbanuuid").replaceAll("_player", p.getName()));
						} else {
							sender.sendMessage(errorPrefix+language.getString("commands.unban.notbanned"));
						}
						return true;
					}
				}
				sender.sendMessage(errorPrefix+language.getString("commands.unban.playernotfounduuid"));
			} else {
				sender.sendMessage(errorPrefix+language.getString("commands.unban.invalidcommand"));
			}
			break;
		default:
			sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			break;
		}
		return true;
	}
}