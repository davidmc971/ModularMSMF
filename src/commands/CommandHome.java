package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.ModularMSMF;

public class CommandHome extends AbstractCommand {

	public CommandHome(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			//TODO: console could maybe set home by hand to specific coordinates
			sender.sendMessage("Konsole kann Homebefehle nicht verwenden.");
			return true;
		}
		// TODO: Implementieren
		// Playerdata plrdat =

		switch (label.toLowerCase()) {
		case "home":

			break;
		case "sethome":

			break;
		case "delhome":

			break;
		}
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "home";
	}
}
