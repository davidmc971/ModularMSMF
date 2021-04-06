package io.github.davidmc971.modularmsmf.commandblocker;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.commandblocker.commands.*;

public class CommandLoader {
    public static void registerCommands(ModularMSMFCommandBlocker plugin) {
        for (IModularMSMFCommand cmd : new IModularMSMFCommand[] {
                new CommandCmdBlckr()
        }) {
            plugin.getCommand(cmd.Label()).setExecutor(cmd);
        }
    }
}