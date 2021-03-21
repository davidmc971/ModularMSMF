package io.github.davidmc971.modularmsmf.data;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.api.IPlayerData;

// TODO: Implement missing methods.

public class PlayerData implements IPlayerData {
	private UUID uuid;
	private FileConfiguration cfg;
	
	public PlayerData(UUID uuid, FileConfiguration cfg) {
		this.uuid = uuid;
		this.cfg = cfg;
	}
	
	public UUID getUUID() { return uuid; }
	public FileConfiguration getConfiguration() { return cfg; }

	@Override
	public String Name() {
		return null;
	}

	@Override
	public UUID ID() {
		return uuid;
	}

	@Override
	public boolean IsOnline() {
		return false;
	}

	@Override
	public Date LastLogin() {
		return null;
	}

	@Override
	public Location CurrentLocation() {
		return null;
	}

	@Override
	public World CurrentWorld() {
		return null;
	}

	@Override
	public Configuration PlayerConfiguration() {
		return cfg;
	}
}
