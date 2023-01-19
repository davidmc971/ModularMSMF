package io.github.davidmc971.modularmsmf.commandblocker.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.davidmc971.modularmsmf.commandblocker.ModularMSMFCommandBlocker;

public class BlockedCommands {

    // store the list of blocked commands
    private List<String> commandList = null;

    // load into list
    public void init() {
        // checks if file exists
        File file = new File("plugins/ModularMSMF-CommandBlocker/blockedcommands.yml");
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
            ModularMSMFCommandBlocker.Instance().getLogger().warning("File exists but no blocked commands specified.");
            return;
        } else {
            try {
                ModularMSMFCommandBlocker.Instance().getLogger().warning("File not found, trying to create file");
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                InputStreamReader reee = new InputStreamReader(ModularMSMFCommandBlocker.Instance().getResource("blockedcommands.yml"));
                while (reee.ready()) {
                    fos.write(reee.read());
                }
                // fos.write(ModularMSMFCommandBlocker.Instance().getResource("blockedcommands.yml").readAllBytes());
                reee.close();
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
