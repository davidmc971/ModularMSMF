package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;

/**
 * 
 * @author Lightkeks, davidmc971
 *
 */

public class CommandMoney extends AbstractCommand {

	public CommandMoney(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//TODO: behavior of old EconomySystems
		return false;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "money" };
	}

}
