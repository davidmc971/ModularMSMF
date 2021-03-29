package io.github.davidmc971.modularmsmf.basics;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.basics.listeners.DeathListener;

public class ModularMSMFBasics extends JavaPlugin {
    private static ModularMSMFBasics instance = null;
    public static ModularMSMFBasics Instance() {
        return instance;
    }

    public ModularMSMFBasics() {
        instance = this;
    }
    
    private PluginManager plgman;
    private DeathListener deathListener;

    @Override
    public void onLoad() {
        deathListener = new DeathListener();
    }

    @Override
    public void onEnable() {
        this.plgman = getServer().getPluginManager();
        plgman.registerEvents(deathListener, this);
        CommandLoader.registerCommands(this);
    }

    public DeathListener getDeathListener() {
        return deathListener;
    }
}
