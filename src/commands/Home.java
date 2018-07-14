package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Konsole kann Homebefehle nicht verwenden.");
			return;
		}
		// TODO: Implementieren
		// Playerdata plrdat =

		switch (commandLabel.toLowerCase()) {
		case "home":

			break;
		case "sethome":

			break;
		case "delhome":

			break;
		}
	}
}
