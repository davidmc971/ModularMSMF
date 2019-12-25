package io.github.davidmc971.modularmsmf.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.davidmc971.modularmsmf.ModularMSMF;

/**
 * 
 * @author davidmc971
 *
 */

/*	Should serve as a single access point to the plugin's data management.
 * 	All files and structures in volatile memory will be held here.
 * 	Saving and loading from the file system will be done through
 * 	different FileManagers that base off the same interface
 * 	so that different formats can be implemented easily.
 * 	First goal is to be able to handle YAML and JSON formats.
 * 
 * 	TODO: review / rewrite whole DataManager
 */

public class DataManager implements Listener {
	private class AutosaveTask extends BukkitRunnable {
		DataManager dtm;

		public AutosaveTask(DataManager dtm) {
			this.dtm = dtm;
		}

		@Override
		public void run() {
			dtm.logger.info("Autosaving userdata...");
			dtm.saveAllUserdata();
			dtm.logger.info("Autosaved userdata.");
		}
	}

	private AutosaveTask autosaveTask;

	public String pathMain = "plugins/ModularMSMF/";
	public String pathUserdata = pathMain + "userdata/";
	//public String pathBankdata = pathMain + "bankdata/";
	public YamlConfiguration settingsyaml = loadCfg(pathMain + "settings.yml");
	public YamlConfiguration defaultUserdatayaml = loadCfg(pathUserdata + "default.yml");

	private Logger logger;
	private ModularMSMF plugin;

	private Map<String, Object> defaultSettings = new HashMap<String, Object>();
	private Map<String, Object> defaultUserdata = new HashMap<String, Object>();

	private Map<UUID, YamlConfiguration> allUsers = new HashMap<UUID, YamlConfiguration>();

	public DataManager(ModularMSMF plugin){
		this.plugin = plugin;
		this.logger = this.plugin.getLogger();
		autosaveTask = new AutosaveTask(this);
		/*	Autosave every 10 minutes
		 * 	-> 10(min) * 60(sec) * 20(tps)
		 *  TODO: load tick delay from config
		 */
		long ticks = (10*60*20);
		if(plugin.debug)
			ticks = 1200; 
		autosaveTask.runTaskTimer(plugin, ticks, ticks);

		init();
	}

	public void init(){
		initDefaultSettings();
		initDefaultUserdata();

		allUsers.clear();
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
			YamlConfiguration cfg = loadPlayerCfg(p.getUniqueId());
			allUsers.put(p.getUniqueId(), cfg);
		}
		for(Player p : Bukkit.getOnlinePlayers()){
			YamlConfiguration cfg = loadPlayerCfg(p.getUniqueId());
			allUsers.put(p.getUniqueId(), cfg);
		}
		allUsers.forEach((uuid, cfg) -> initUserDefaults(cfg, uuid));
		saveAllUserdata();
		logger.info("DataManager init done.");
	}

	public void saveAllUserdata(){
		allUsers.forEach((uuid, cfg) -> savePlayerCfg(cfg, uuid));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		for(Entry<UUID, YamlConfiguration> e : allUsers.entrySet()){
			if(e.getKey().toString().equalsIgnoreCase(uuid.toString())){
				initUserDefaults(e.getValue(), uuid);
				e.getValue().set("online", true);
				return;
			}
		}
		YamlConfiguration newcfg = loadPlayerCfg(uuid);
		initUserDefaults(newcfg, uuid);
		newcfg.set("online", true);
		allUsers.put(uuid, newcfg);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		for(Entry<UUID, YamlConfiguration> e : allUsers.entrySet()){
			if(e.getKey().toString().equalsIgnoreCase(event.getPlayer().getUniqueId().toString())){
				e.getValue().set("online", false);
			}
		}
	}

	public YamlConfiguration getPlayerCfg(UUID uuid){
		for(Entry<UUID, YamlConfiguration> e : allUsers.entrySet()){
			if(e.getKey().toString().equalsIgnoreCase(uuid.toString()))
				return e.getValue();
		}
		return null;
	}

	public YamlConfiguration loadCfg(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return YamlConfiguration.loadConfiguration(file);
	}

	public YamlConfiguration loadPlayerCfg(UUID uuid){
		return loadCfg(pathUserdata + uuid.toString() + ".yml");
	}

	public void saveCfg(YamlConfiguration cfg, String path) {
		File file = new File(path);
		try {
			cfg.save(file);
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error during saving userdata:");
			e.printStackTrace();
		}
	}

	public void savePlayerCfg(YamlConfiguration playercfg, UUID uuid) {
		saveCfg(playercfg, pathUserdata + uuid.toString() + ".yml");
	}

	private void initDefaultSettings(){
		defaultSettings.clear();
		defaultSettings.put("economy.money", 500.0d);
		//defaultSettings.put("kickcounter", 0);
		defaultSettings.put("language.standard", "en_US");
		for(Entry<String, Object> e : defaultSettings.entrySet()){
			if(settingsyaml.get(e.getKey()) == null){
				settingsyaml.set(e.getKey(), e.getValue());
			}
		}
		saveCfg(settingsyaml, pathMain + "settings.yml");
	}

	private void initDefaultUserdata(){
		defaultUserdata.clear();
		defaultUserdata.put("economy.money", settingsyaml.get("economy.money"));
		defaultUserdata.put("economyalex.money", settingsyaml.get("economy.money"));
		//defaultUserdata.put("kickcounter", settingsyaml.get("kickcounter"));
		defaultUserdata.put("language", null);
		for(Entry<String, Object> e : defaultUserdata.entrySet()){
			if(defaultUserdatayaml.get(e.getKey()) == null){
				defaultUserdatayaml.set(e.getKey(), e.getValue());
			}
		}
		saveCfg(defaultUserdatayaml, pathUserdata + "default.yml");
	}

	private void initUserDefaults(YamlConfiguration cfg, UUID uuid){
		for(String s : defaultUserdatayaml.getKeys(true)){
			if(cfg.get(s) == null || !cfg.get(s).getClass().equals(defaultUserdatayaml.get(s).getClass())){
				cfg.set(s, defaultUserdatayaml.get(s));
			}
		}
		
		/*
		if		(!cfg.contains("name"))		{ cfg.set("name", Bukkit.getPlayer(uuid).getName()); }
		else if	(!cfg.isString("name"))		{ cfg.set("name", Bukkit.getPlayer(uuid).getName()); }
		if		(!cfg.contains("online"))	{ cfg.set("online", Bukkit.getPlayer(uuid).isOnline()); }
		else if	(!cfg.isBoolean("online"))	{ cfg.set("online", Bukkit.getPlayer(uuid).isOnline()); }
		 */
		
		savePlayerCfg(cfg, uuid);
	}
}

/* penis
 * int foo() {
 *  int y = 5;
 *  auto f = [&] (int x) { return x + y; };
 *  std::cout << f(4) << std::endl;
 *  
 *  #madybykevin
 *  #lambdainc++
 *  Somebody once told me the world is gonna roll me
 *  LA ist ein tolles fach
 *  in der uni hat man nie langeweile
 */
