package io.github.davidmc971.modularmsmf.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class JSONConfigurationLoader extends AbstractConfigurationLoader {

    @Override
    public String getFileExtension() {
        return ".json";
    }

    @Override
    public FileConfiguration loadFromFile(File file) {
        return JSONConfiguration.loadConfiguration(file);
    }

}