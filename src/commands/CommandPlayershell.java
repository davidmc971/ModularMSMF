package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;

/*	This command is supposed to be a shell, enabling
 * 	the console to emulate being a player for testing
 * 	purposes.
 */

public class CommandPlayershell extends AbstractCommand {

	public CommandPlayershell(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("bla");
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "playershell";
	}

}
