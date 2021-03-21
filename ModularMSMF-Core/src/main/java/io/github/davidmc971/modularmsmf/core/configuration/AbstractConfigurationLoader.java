package io.github.davidmc971.modularmsmf.core.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

public abstract class AbstractConfigurationLoader {
    public abstract String getFileExtension();

    public abstract FileConfiguration loadFromFile(File file);

    public void saveToFile(FileConfiguration cfg, File file) throws IOException { cfg.save(file); }
}