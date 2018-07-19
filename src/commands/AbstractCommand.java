package commands;

import org.bukkit.command.CommandExecutor;

import main.ModularMSMF;

public abstract class AbstractCommand implements CommandExecutor {
	public abstract String getCommandLabel();
	protected ModularMSMF plugin;
	public AbstractCommand(ModularMSMF plugin) {
		this.plugin = plugin;
	}
}
