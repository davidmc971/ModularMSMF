package io.github.davidmc971.modularmsmf;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.commands.CommandCmdBlckr;
import io.github.davidmc971.modularmsmf.configuration.BlockedCommands;
import io.github.davidmc971.modularmsmf.listeners.CommandListener;

@SuppressWarnings("unused")
public class ModularMSMFCommandBlocker extends JavaPlugin {
    private static ModularMSMFCommandBlocker instance = null;
	public static ModularMSMFCommandBlocker Instance() {
		return instance;
	}
    private BlockedCommands blockedCommands;
    // plugin will be loaded and enabled
    @Override
    public void onEnable() {
        instance = this;
        // initiating blocked commands config
        blockedCommands = new BlockedCommands();
        blockedCommands.init();

        // getting Events
        CommandListener cmdListener = new CommandListener(blockedCommands);
		this.getServer().getPluginManager().registerEvents(cmdListener, this);

        // Still need to figure out what to do with this
        CommandCmdBlckr cmdBlckr = new CommandCmdBlckr();
    }

    @Override
    public void onDisable() {
        blockedCommands.unload();
    }
}
