package io.github.davidmc971.modularmsmf.data;

import java.util.UUID;

import io.github.davidmc971.modularmsmf.handlers.HomeHandler.Home;

public class PlayerData {
	private UUID uuid;
	public Home[] homes = null;
	public long money = -1;
	
	public PlayerData(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUUID() {
		return uuid;
	}
}
