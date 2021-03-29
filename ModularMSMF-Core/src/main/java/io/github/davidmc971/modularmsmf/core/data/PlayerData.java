package io.github.davidmc971.modularmsmf.core.data;

import java.net.InetAddress;
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
	private static InetAddress getInetAddress;
	
	public PlayerData(UUID uuid, FileConfiguration cfg, InetAddress playerIp) {
		this.uuid = uuid;
		this.cfg = cfg;
		PlayerData.getInetAddress = playerIp;
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

	public static InetAddress getInetAddress() {
		return getInetAddress;
	}

	@Override
	public InetAddress IPAdress() {
		return null;
	}
}
