package io.github.davidmc971.modularmsmf.basics;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.commands.*;

public class CommandLoader {
	public static void registerCommands(ModularMSMFBasics plugin) {
		for (IModularMSMFCommand cmd : new IModularMSMFCommand[] {
				new CommandBanPlayer(),
				new CommandBanIP(),
				new CommandUnbanPlayer(),
				new CommandUnbanIP(),
				new CommandBack(),
				new CommandFeed(),
				new CommandHeal(),
				new CommandHome(),
				new CommandKick(),
				new CommandKill(),
				new CommandKillAll(),
				new CommandKillMe(),
				new CommandMotd(),
				new CommandMute(),
				new CommandSetSpawn(),
				new CommandSlaughter(),
				new CommandSpawn(),
				new CommandTeleport(),
				new CommandFly(),
				new CommandChannels(),
				new CommandSet(),
				new CommandGet(),
				new CommandHealAll(),
				new CommandClearInventory()
		}) {
			plugin.getCommand(cmd.Label()).setExecutor(cmd);
		}
	}
}
