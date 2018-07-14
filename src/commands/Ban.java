package commands;

/**
 * 
 * @authors Lightkeks, ernikillerxd64
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
import util.DataManager;
import util.PermissionsHandler;
import util.Utils;

public class Ban {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if (sender.hasPermission(PermissionsHandler.getPermission("banplayer"))) {
			if (args.length == 0) {
				sender.sendMessage(language.getString("general.missing_playername"));
				return;
			}

			String reason = language.getString("commands.ban.defaultbanreason");
			UUID uuid = getPlayerUUIDByNameForBan(args[0]);
			if (uuid == null) {
				sender.sendMessage(language.getString("general.playerunknown"));
				return;
			} else if (DataManager.getPlayerCfg(uuid).getBoolean("banned")) {
				sender.sendMessage(language.getString("commands.ban.alreadybanned"));
				return;
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
			sender.sendMessage(language.getString("commands.ban.playerbanned").replaceAll("_player", args[0])
					.replaceAll("_reason", reason));
		}
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
}