package io.github.davidmc971.modularmsmf.api;

import org.bukkit.command.CommandExecutor;

/**
 * An Interface to represent the key requirements for a command to be used and
 * executed in ModularMSMF.
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 * @since 0.3.0
 */
public interface IModularMSMFCommand extends CommandExecutor {
    /**
     * Get the main label of this command. Command will be registered using this
     * label.
     * 
     * @return The commands' label.
     */
    public String Label();

    /**
     * Get the aliases of this command. Aliases will be registered. Can be empty.
     * 
     * @return The commands' aliases.
     */
    public String[] Aliases();

    /**
     * Get if the command is currently enabled. If the command will always be
     * enabled, this can just return true. If it is configurable whether the
     * command is enabled or not, you can give that info here.
     * 
     * @return true if command is enabled.
     */
    public boolean Enabled();
}
