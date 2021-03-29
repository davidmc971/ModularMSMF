package io.github.davidmc971.modularmsmf.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.davidmc971.modularmsmf.core.data.PlayerData;

/*	Manages all players settings and data.
 * 	Files are saved in json format.
 * 
 * 
 * 
 */
public class PlayerManager {
	private MemoryConfiguration defaultPlayerConfig() {
		MemoryConfiguration temp = new MemoryConfiguration();
		temp.addDefaults(new LinkedHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("player.ipAdress", "");
				put("player.uuid", 0);
				put("player.name", "");
			}
		});
		return temp;
	}

	private class AutosaveTask extends BukkitRunnable {
		PlayerManager plrm;

		public AutosaveTask(PlayerManager plrm) {
			this.plrm = plrm;
		}

		@Override
		public void run() {
			plrm.plugin.getLogger().info("Autosaving playerdata...");
			plrm.saveAll();
			plrm.plugin.getLogger().info("Autosaved playerdata.");
		}
	}

	private AutosaveTask autosaveTask;

	ModularMSMFCore plugin;

	List<BukkitRunnable> saveTasks;

	private String folderPlayers = "players/";
	// private String filePlayerDefaults = "default.json";
	private String dataroot = "plugins/ModularMSMF/";

	private Map<UUID, PlayerData> playerStorage = null;

	public PlayerManager(ModularMSMFCore plugin) {
		this.plugin = plugin;
		// V`-- needed?
		saveTasks = new ArrayList<BukkitRunnable>();
		init(defaultPlayerConfig());
		autosaveTask = new AutosaveTask(this);
		/*
		 * Autosave every 10 minutes -> 10(min) * 60(sec) * 20(tps) //TODO: load tick
		 * delay from config
		 */
		long ticks = (10 * 60 * 20);
		if (plugin.debug)
			ticks = 1200;
		autosaveTask.runTaskTimer(plugin, ticks, ticks);
	}

	public PlayerData getPlayerData(UUID uuid) {
		return playerStorage.get(uuid);
	}

	//TODO: other location for configuring if .yml or .json is used
	private String playerDataPath = dataroot + folderPlayers + "_UUID.json";

	private void init(MemoryConfiguration defaultConfig) {
		plugin.getLogger().info("PlayerManager init start.");

		dataroot = plugin.getDataFolder().getPath().replace("\\", "/");

		// load userdata for each player
		playerStorage = new LinkedHashMap<UUID, PlayerData>();

		List<UUID> uuids = new ArrayList<UUID>();
		Arrays.asList(Bukkit.getOfflinePlayers()).forEach((e) -> uuids.add(e.getUniqueId()));
		Bukkit.getOnlinePlayers().forEach((e) -> uuids.add(e.getUniqueId()));

		for (UUID uuid : uuids) {
			PlayerData data = new PlayerData(uuid,
					plugin.getDataManager().loadCfg(playerDataPath.replace("_UUID", uuid.toString())), null);
			data.getConfiguration().addDefaults(defaultConfig);
			playerStorage.put(uuid, data);
		}

		plugin.getLogger().info("PlayerManager init done!");
	}

	public Map<UUID, PlayerData> getPlayerStorage() {
		return playerStorage;
	}

	public void saveAll() {
		// plugin.getLogger().info("[playerManager saving is disabled for now]");
		// saveTasks.forEach(task -> {
		// task.run();
		// });
		playerStorage.forEach((uuid, data) -> {
			File file = new File(playerDataPath.replace("_UUID", uuid.toString()));
			try {
				data.getConfiguration().save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void registerSaveTask(BukkitRunnable task) {
		saveTasks.add(task);
	}
}
