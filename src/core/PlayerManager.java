package core;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import defaults.PlayerDefaults;
import main.ModularMSMF;

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
	private String filePlayerDefaults = "default.json";
	private String dataroot = null;

	private JsonObject configuredDefaults = null;

	private Map<UUID, JsonObject> playerStorage = null;

	public Map<UUID, JsonObject> getPlayerStorage() {
		return playerStorage;
	}

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
	
	public JsonObject getPlayerStorage(UUID uuid) {
		return playerStorage.get(uuid);
	}

	private void init() {
		plugin.getLogger().info("PlayerManager init start.");
		
		dataroot = plugin.getDataFolder().getPath().replace("\\", "/");
		initDefaultJSON();

		//load userdata for each player
		playerStorage = new HashMap<UUID, JsonObject>();

		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			UUID uuid = p.getUniqueId();
			JsonObject cfg = loadJson(uuid);
			playerStorage.put(uuid, cfg);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			UUID uuid = p.getUniqueId();
			JsonObject cfg = loadJson(uuid);
			playerStorage.put(uuid, cfg);
		}

		//populate defaults
		playerStorage.forEach((uuid, json) -> {
			configuredDefaults.entrySet().forEach((entry) -> {
				if (!json.has(entry.getKey())) {
					json.add(entry.getKey(), entry.getValue());
				}
			});
		});

		plugin.getLogger().info("PlayerManager init done!");
	}

	public void saveAll() {
		saveTasks.forEach(task -> {
			task.run();
		});
		playerStorage.forEach((uuid, json) -> {
			saveJson(json, uuid);
		});
	}

	private JsonObject loadJson(File file) {
		JsonObject temp = null;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		JsonParser parser = new JsonParser();
		try {
			temp = parser.parse(new FileReader(file)).getAsJsonObject();
		} catch (Exception e) {
			temp = new JsonObject();
		}
		return temp;
	}

	private JsonObject loadJson(String path) {
		return loadJson(new File(path));
	}

	private JsonObject loadJson(UUID uuid) {
		String path = dataroot + folderPlayers + uuid + ".json";
		return loadJson(path);
	}

	private boolean saveJson(JsonObject json, File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);
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

	private boolean saveJson(JsonObject json, String path) {
		return saveJson(json, new File(path));
	}

	private boolean saveJson(JsonObject json, UUID uuid) {
		String path = dataroot + folderPlayers + uuid + ".json";
		return saveJson(json, path);
	}

	private void initDefaultJSON() {
		File defaultsFile = new File(dataroot + folderPlayers + filePlayerDefaults);
		configuredDefaults = loadJson(defaultsFile);

		PlayerDefaults playerDefaults = new PlayerDefaults();

		playerDefaults.getDefaults().forEach((key, value) -> {
			if (!configuredDefaults.has(key)) {
				if (value instanceof JsonArray) {
					configuredDefaults.add(key, (JsonArray) value);
				} else {
					configuredDefaults.addProperty(key, value.toString());
				}
			}
		});

		if(plugin.debug) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(configuredDefaults);
			plugin.getLogger().info("Loaded player defaults:\n" + prettyJson);
		}

		saveJson(configuredDefaults, defaultsFile);
	}

	public void registerSaveTask(BukkitRunnable task) {
		saveTasks.add(task);
	}
}
