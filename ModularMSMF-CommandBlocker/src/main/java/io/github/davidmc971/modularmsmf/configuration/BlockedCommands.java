package io.github.davidmc971.modularmsmf.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.ModularMSMFCommandBlocker;
import io.github.davidmc971.modularmsmf.util.ChatUtils;

public class BlockedCommands {

    // store the list of blocked commands
    private List<String> commandList = null;

    // load into list
    public void init() {
        // checks if file exists
        File file = new File("plugins/ModularMSMF/CommandBlocker/blockedcommands.yml");
        if (file.exists()) {
            System.out.println("File found");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            // checks if line underneath exists
            if (cfg.contains("BlockedCommands")) {
                commandList = cfg.getStringList("BlockedCommands");
                if (!commandList.isEmpty()) {
                    return;
                }
                commandList = null;
            }
            ModularMSMF.Instance().getLogger().warning(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.WARN)
                    + "File exists but no blocked commands specified.");
        } else {
            try {
                System.out.println("File not found, trying to create file");
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(ModularMSMFCommandBlocker.Instance().getResource("blockedcommands.yml").readAllBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean commandIsBlocked(String cmd) {
        return commandList.contains(cmd);
    }

    public void unload() {
        commandList = null;
    }
}
