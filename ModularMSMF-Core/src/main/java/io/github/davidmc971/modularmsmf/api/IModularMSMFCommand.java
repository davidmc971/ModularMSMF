package io.github.davidmc971.modularmsmf.api;

import org.bukkit.command.CommandExecutor;

public interface IModularMSMFCommand extends CommandExecutor {
    public String[] getCommandLabels();
}
