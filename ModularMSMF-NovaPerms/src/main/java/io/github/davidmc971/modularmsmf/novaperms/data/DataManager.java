package io.github.davidmc971.modularmsmf.novaperms.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.davidmc971.modularmsmf.novaperms.ModularMSMFNovaPerms;

public class DataManager {

    private ModularMSMFNovaPerms plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    private String configName = "config.yml";

    public DataManager(ModularMSMFNovaPerms plugin) {
        this.plugin = plugin;
        //save/init config
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), configName);

            this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

            InputStream defaultStream = this.plugin.getResource(configName);
            if (defaultStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration
                        .loadConfiguration(new InputStreamReader(defaultStream));
                this.dataConfig.setDefaults(defaultConfig);
            }
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            reloadConfig();
        }
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't save config to " + this.configName, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), configName);
        }
        if (!this.configFile.exists()) {
            this.plugin.saveResource(configName, false);
        }
    }

}
