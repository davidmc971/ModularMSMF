package io.github.davidmc971.modularmsmf.core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

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
			PlayerData data = loadJson(uuid);
			playerStorage.put(uuid, data);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			UUID uuid = p.getUniqueId();
			PlayerData data = loadJson(uuid);
			playerStorage.put(uuid, data);
		}
		
		playerStorage.forEach((uuid, data) -> {
			if (data == null) {
				data = new PlayerData(uuid);
				data.money = 500;
			}
		});

		plugin.getLogger().info("PlayerManager init done!");
	}

	public Map<UUID, PlayerData> getPlayerStorage() {
		return playerStorage;
	}

	public void saveAll() {
		saveTasks.forEach(task -> {
			task.run();
		});
		playerStorage.forEach((uuid, json) -> {
			saveJson(json, uuid);
		});
	}

	private PlayerData loadJson(File file) {
		PlayerData temp = null;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			temp = gson.fromJson(new JsonReader(new FileReader(file)), PlayerData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	private PlayerData loadJson(String path) {
		return loadJson(new File(path));
	}

	private PlayerData loadJson(UUID uuid) {
		String path = dataroot + folderPlayers + uuid + ".json";
		return loadJson(path);
	}

	private boolean saveJson(PlayerData json, File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String prettyJson = gson.toJson(json, PlayerData.class);
		/* FIXME: server throws big reflection error:
		[02:44:15] [Server thread/WARN]: [ModularMSMF] Task #5 for ModularMSMF v0.2-develop generated an exception
		java.lang.StackOverflowError: null
		at com.google.gson.internal.$Gson$Types.resolve($Gson$Types.java:378) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
		at com.google.gson.internal.$Gson$Types.resolve($Gson$Types.java:383) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
		...
		at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(ReflectiveTypeAdapterFactory.java:158) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
        at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.create(ReflectiveTypeAdapterFactory.java:100) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
        at com.google.gson.Gson.getAdapter(Gson.java:423) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
        at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.createBoundField(ReflectiveTypeAdapterFactory.java:115) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
		at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.getBoundFields(ReflectiveTypeAdapterFactory.java:164) ~[spigot-1.15.1.jar:git-Spigot-05bb8bc-8073dbe]
		...
		*/

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(prettyJson.toString());
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean saveJson(PlayerData json, String path) {
		return saveJson(json, new File(path));
	}

	private boolean saveJson(PlayerData json, UUID uuid) {
		String path = dataroot + folderPlayers + uuid + ".json";
		return saveJson(json, path);
	}

	public void registerSaveTask(BukkitRunnable task) {
		saveTasks.add(task);
	}
}
