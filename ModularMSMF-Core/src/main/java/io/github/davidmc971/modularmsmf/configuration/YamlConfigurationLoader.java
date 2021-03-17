package io.github.davidmc971.modularmsmf.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlConfigurationLoader extends AbstractConfigurationLoader {

    @Override
    public String getFileExtension() {
        return ".yml";
    }

    @Override
    public FileConfiguration loadFromFile(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

}