package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import listeners.Events;
import util.KillType;

public class CommandKill {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args, Events mainEvents) {

		/**
		 * @author Lightkeks "/kill me" killt den sender "/kill <target>" killt
		 *         das target "/kill all" killt alle
		 * 
		 *         > groesserr als < kleiner als
		 * 
		 *         Sollte schon klappen
		 */

		if (args.length == 0) {
			sender.sendMessage("Bitte gib Argumente an!");
			return;
		}
		switch (args[0]) {
		case "me":
			if (sender instanceof Player) {
				if (sender.hasPermission("modularmsmf.kill.me")) {
					Player player = ((Player) sender);
					mainEvents.registerKilledPlayer(player, KillType.SUICIDE);
					player.setHealth(0);
				}
			} else {
				sender.sendMessage("Konsole darf kein Suizid durchfuehren");
			}
			break;
		case "all":
			if (sender.hasPermission("modularmsmf.kill.all")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					mainEvents.registerKilledPlayer(player, KillType.HOMOCIDE);
					player.setHealth(0);
				}
			}
			break;
		default:
			if (sender.hasPermission("modularmsmf.kill")) {
				if (sender instanceof Player) {
					boolean temp = false;
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
							mainEvents.registerKilledPlayer(player, KillType.KILL);
							player.setHealth(0);
							temp = true;
							break;
						}
					}
					if (!temp)
						sender.sendMessage("Dieser Spieler ist nicht online!");
				} else {
					sender.sendMessage("Die Konsole darf keinen Spieler toeten!");
				}
			} else {
				sender.sendMessage("Du hast keine Rechte, jemanden zu toeten!");
			}
		}
	}
}