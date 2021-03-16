package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.CommandExecutor;

import io.github.davidmc971.modularmsmf.ModularMSMF;

public abstract class AbstractCommand implements CommandExecutor {
	public abstract String[] getCommandLabels();
	protected ModularMSMF plugin;
	public AbstractCommand(ModularMSMF plugin) {
		this.plugin = plugin;
	}
}
