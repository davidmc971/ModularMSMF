package io.github.davidmc971.modularmsmf.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.davidmc971.modularmsmf.data.PlayerData;
import io.github.davidmc971.modularmsmf.ModularMSMF;

/*	Manages all players settings and data.
 * 	Files are saved in json format.
 * 
 * 
 * 
 */
public class PlayerManager {
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
	
	ModularMSMF plugin;

	List<BukkitRunnable> saveTasks;
	
	private String folderPlayers = "/players/";
	//private String filePlayerDefaults = "default.json";
	private String dataroot = null;

	private Map<UUID, PlayerData> playerStorage = null;

	public PlayerManager(ModularMSMF plugin) {
		this.plugin = plugin;
		saveTasks = new ArrayList<BukkitRunnable>();
		init();
		autosaveTask = new AutosaveTask(this);
		/*	Autosave every 10 minutes
		 * 	-> 10(min) * 60(sec) * 20(tps)
		 *  TODO: load tick delay from config
		 */
		long ticks = (10*60*20);
		if(plugin.debug)
			ticks = 1200; 
		autosaveTask.runTaskTimer(plugin, ticks, ticks);
	}
	
	public PlayerData getPlayerData(UUID uuid) {
		return playerStorage.get(uuid);
	}

	private void init() {
		plugin.getLogger().info("PlayerManager init start.");
		
		dataroot = plugin.getDataFolder().getPath().replace("\\", "/");

		//load userdata for each player
		playerStorage = new HashMap<UUID, PlayerData>();

		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			UUID uuid = p.getUniqueId();
			//TODO:
			//PlayerData data = loadJson(uuid);
			//playerStorage.put(uuid, data);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			UUID uuid = p.getUniqueId();
			//TODO:
			//PlayerData data = loadJson(uuid);
			//playerStorage.put(uuid, data);
		}
		
		playerStorage.forEach((uuid, data) -> {
			if (data == null) {
				//TODO:
				//data = new PlayerData(uuid);
				//data.money = 500;
			}
		});

		plugin.getLogger().info("PlayerManager init done!");
	}

	public Map<UUID, PlayerData> getPlayerStorage() {
		return playerStorage;
	}

	public void saveAll() {
		plugin.getLogger().info("[playerManager saving is disabled for now]");
		// saveTasks.forEach(task -> {
		// 	task.run();
		// });
		// playerStorage.forEach((uuid, json) -> {
		// 	saveJson(json, uuid);
		// });
	}

	public void registerSaveTask(BukkitRunnable task) {
		saveTasks.add(task);
	}
}
