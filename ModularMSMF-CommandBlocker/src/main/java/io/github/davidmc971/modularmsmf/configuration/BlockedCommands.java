package io.github.davidmc971.modularmsmf.configuration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BlockedCommands {

    // store the list of blocked commands
    private List<String> commandList = new LinkedList<>();

    // load into list
    public void init() {
        // checks if file exists
        File file = new File("/ModularMSMF/CommandBlocker/blockedcommands.yml");
        if (file.exists()) {
            System.out.println("File found");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            //checks if line underneath exists
            if (cfg.contains("BlockedCommands")) {
                
            }

        } else {
            try {
                System.out.println("File not found, trying to create file");
                file.getParentFile().mkdirs();
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
