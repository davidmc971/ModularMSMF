package io.github.davidmc971.modularmsmf.basics;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.commands.*;

public class CommandLoader {
    public static void registerCommands(ModularMSMFBasics plugin) {
        for (IModularMSMFCommand cmd : new IModularMSMFCommand[] {
                new CommandBan(),
				new CommandBack(),
				new CommandFeed(),
				new CommandHeal(),
				new CommandHome(),
				new CommandKick(),
				new CommandKill(),
				new CommandMotd(),
				new CommandMute(),
				new CommandSetSpawn(),
				new CommandSlaughter(),
				new CommandSpawn(),
				new CommandTeleport(),
				new CommandFly(),
				new CommandChannels(),
				new CommandSet()
        }) {
            plugin.getCommand(cmd.Label()).setExecutor(cmd);
        }
    }
}
