package util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import main.ModularMSMF;

public class DataManager implements Listener {
	public static final String pathMain = "plugins/ModularMSMF/";
	public static final String pathUserdata = pathMain + "userdata/";
	public static final String pathBankdata = pathMain + "bankdata/";
	public static final YamlConfiguration settingsyaml = loadCfg(pathMain + "settings.yml");
	public static final YamlConfiguration defaultUserdatayaml = loadCfg(pathUserdata + "default.yml");
	
	private Logger logger;
	
	private Map<String, Object> defaultSettings = new HashMap<String, Object>();
	private Map<String, Object> defaultUserdata = new HashMap<String, Object>();
	
	private static Map<UUID, YamlConfiguration> allUsers = new HashMap<UUID, YamlConfiguration>();
	
	public DataManager(Logger logger){
		this.logger = logger;
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
	
	public static YamlConfiguration getPlayerCfg(UUID uuid){
		for(Entry<UUID, YamlConfiguration> e : allUsers.entrySet()){
			if(e.getKey().toString().equalsIgnoreCase(uuid.toString()))
				return e.getValue();
		}
		return null;
	}
	
	public static YamlConfiguration loadCfg(String path) {
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
	
	public static YamlConfiguration loadPlayerCfg(UUID uuid){
		return loadCfg(pathUserdata + uuid.toString() + ".yml");
	}
	
	public static void saveCfg(YamlConfiguration cfg, String path) {
		File file = new File(path);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void savePlayerCfg(YamlConfiguration playercfg, UUID uuid) {
		saveCfg(playercfg, pathUserdata + uuid.toString() + ".yml");
	}
	
	private void initDefaultSettings(){
		defaultSettings.clear();
		defaultSettings.put("economy.money", 500.0);
		defaultSettings.put("kickcounter", 0);
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
		defaultUserdata.put("economyalex.money", settingsyaml.get("economyalex.money"));
		defaultUserdata.put("kickcounter", settingsyaml.get("kickcounter"));
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
