package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.KillType;

public class CommandKill extends AbstractCommand {

	public CommandKill(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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
			return true;
		}
		switch (args[0]) { // missing prefix as text
		case "me":
			if (sender instanceof Player) {
				if (sender.hasPermission("modularmsmf.kill.me")) {
					Player player = ((Player) sender);
					plugin.getMainEvents().registerKilledPlayer(player, KillType.SUICIDE);
					player.setHealth(0);
				}
			} else {
				sender.sendMessage("Konsole darf kein Suizid durchfuehren");
			}
			break;
		case "all":
			if (sender.hasPermission("modularmsmf.kill.all")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					plugin.getMainEvents().registerKilledPlayer(player, KillType.HOMOCIDE);
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
							plugin.getMainEvents().registerKilledPlayer(player, KillType.KILL);
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
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "kill" };
	}
}