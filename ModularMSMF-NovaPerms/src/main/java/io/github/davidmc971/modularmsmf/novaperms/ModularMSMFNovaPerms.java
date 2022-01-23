package io.github.davidmc971.modularmsmf.novaperms;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.novaperms.data.DataManager;

public class ModularMSMFNovaPerms extends JavaPlugin {

    public DataManager dataManager;

    @Override
    public void onEnable() {
        this.getLogger().info("Enabling NovaPerms...");
        this.dataManager = new DataManager(this);
    }

    @Override
    public void onDisable(){
        this.getLogger().info("Disabling NovaPerms...");
    }
}
