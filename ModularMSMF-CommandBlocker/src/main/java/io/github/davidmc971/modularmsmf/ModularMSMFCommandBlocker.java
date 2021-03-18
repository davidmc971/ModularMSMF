package io.github.davidmc971.modularmsmf;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.commands.CommandCmdBlckr;
import io.github.davidmc971.modularmsmf.listeners.CommandListener;

public class ModularMSMFCommandBlocker extends JavaPlugin {
    //plugin will be loaded and enabled
    @Override
    public void onEnable() {
        //if file does not exist, new file will be created
        
        //getting Events
        CommandListener cmdListener = new CommandListener();
		this.getServer().getPluginManager().registerEvents(cmdListener, this);

        CommandCmdBlckr cmdBlckr = new CommandCmdBlckr(ModularMSMF.Instance());
        this.getCommand("cmdblckr").setExecutor(cmdBlckr);
        
    }
}
