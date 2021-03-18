package io.github.davidmc971.modularmsmf.data;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

public class PlayerData {
	private UUID uuid;
	private FileConfiguration cfg;
	
	public PlayerData(UUID uuid, FileConfiguration cfg) {
		this.uuid = uuid;
		this.cfg = cfg;
	}
	
	public UUID getUUID() { return uuid; }
	public FileConfiguration getConfiguration() { return cfg; }
}
