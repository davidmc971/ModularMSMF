package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.PermissionsHandler;
import util.Utils;

public class Feed {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {

		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		UUID target = null;

		switch (args.length) {
		case 0:
			if (sender.hasPermission(PermissionsHandler.getPermission("feedself"))) {
				sender.sendMessage(language.getString("commands.feed.feeded"));
				((Player) sender).setSaturation(20);
				return;
			} else {
				sender.sendMessage(language.getString("general.nopermission"));
			}
			break;
		default:
			target = Utils.getPlayerUUIDByName(args[0]);
			if (target == null) {
				sender.sendMessage(language.getString("general.playernotfound"));
				return;
			}
			if (!sender.hasPermission(PermissionsHandler.getPermission("feedothers"))) {
				sender.sendMessage(language.getString("general.nopermission"));
				return;
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
					Bukkit.getPlayer(target).setSaturation(20);
					sender.sendMessage(
							language.getString("commands.feed.feededperson").replaceAll("_player", p.getName()));
					p.sendMessage(sender.getName() + " " + language.getString("commands.feed.othersfeeded"));
					return;
				}
			}
			sender.sendMessage(language.getString("general.playernotfound"));
			break;
		}
	}
}