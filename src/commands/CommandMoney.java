package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;

public class CommandMoney extends AbstractCommand {

	public CommandMoney(ModularMSMF plugin) {
		super(plugin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCommandLabel() {
		return "money";
	}

}
