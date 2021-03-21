package io.github.davidmc971.modularmsmf.api;

import org.bukkit.command.CommandExecutor;

/**
 * An Interface to represent the key requirements for a command to be used
 * executed in ModularMSMF.
 * 
 * @author davidmc971 (David Alexander Pfeiffer)
 * @since 0.3.0
 */
public interface IModularMSMFCommand extends CommandExecutor {
    /**
     * Get the labels associated with this command.
     * 
     * @return An array containing at least one command label without a leading
     *         slash (e.g. "command1").
     */
    public String[] getCommandLabels();
}
