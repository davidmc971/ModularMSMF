package io.github.davidmc971.modularmsmf.core.storage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModule;

/**
 * This class is intended to give a centralized approach to data handling.
 * Data should be managed by ModularMSMF-Core and all modules can get / request
 * their specific storage needs and retrieve the corresponding Configuration
 * handles.
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 */
public class StorageHandler {
    private final String pathModularMSMF = "plugins/ModularMSMF/";
    // private final String pathUserdata = pathMain + "userdata/";
    // private final String pathPlayers = pathMain + "players/";

    // General settings
    private FileConfiguration settingsStorage = new YamlConfiguration();
    // Central player storage
    private Map<UUID, FileConfiguration> playerStorage = new HashMap<>();
    // Module storage
    private Map<IModule, Map<Entry<String, File>, FileConfiguration>> moduleStorage;

    public StorageHandler() {
        initialize();
    }

    private void initialize() {
        // Load settings file

    }

    /**
     * This is where ModularMSMF-Core and all modules can retrieve a handle
     * to store player-related data.
     * @return A MemoryConfiguration instance to store player data.
     */
    public MemoryConfiguration PlayerStorage(UUID uuid) {
        return playerStorage.get(uuid);
    }

    public MemoryConfiguration PlayerStorage(Player player) {
        return PlayerStorage(player.getUniqueId());
    }
}
