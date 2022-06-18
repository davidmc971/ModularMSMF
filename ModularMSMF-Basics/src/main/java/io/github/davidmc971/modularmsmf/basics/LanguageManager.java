package io.github.davidmc971.modularmsmf.basics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 *
 */

public class LanguageManager {
	private final ModularMSMFBasics plugin;
	private final ArrayList<String> standards;
	private final String pathExternalLanguages = "plugins/ModularMSMF-Basics/languages/";
	private Map<LanguageInformation, YamlConfiguration> allLanguages = new HashMap<LanguageInformation, YamlConfiguration>();
	private String defaultLanguage;
	private Entry<LanguageInformation, YamlConfiguration> standardLanguageEntry;
	
	public LanguageManager(ModularMSMFBasics plugin){
		this.plugin = plugin;

		standards = new ArrayList<String>();
		standards.add("en_US");
		standards.add("de_DE");
		try {
			init();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		standardLanguageEntry = null;
		for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
			if(e.getValue().getString("language.id").equalsIgnoreCase(plugin.getDataManager().settingsyaml.getString("language.standard"))){
				standardLanguageEntry = e;
			}
		}
		if(standardLanguageEntry == null){
			for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
				if(e.getValue().getString("language.id").equalsIgnoreCase(defaultLanguage) && e.getKey().isDefaultLang()){
					standardLanguageEntry = e;
				}
			}
			plugin.getLogger().warning("No valid standard language specified, falling back to 'en_US'. Check your settings.yml");
		}
		
		plugin.getLogger().info("Standard Language: " + standardLanguageEntry.getValue().getString("language.id"));
	}
	
	private void init() {
		initDefaults();
		
		defaultLanguage = "en_US";
		
		YamlConfiguration defaultLanguageYaml = null;
		for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
			if(e.getValue().getString("language.id").equals(defaultLanguage) && e.getKey().isDefaultLang()){
				defaultLanguageYaml = e.getValue();
			}
		}
		
		YamlConfiguration standardBase_en_US = null;
		for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
			if(e.getValue().getString("language.id").equals("en_US") && e.getKey().isDefaultLang()){
				standardBase_en_US = e.getValue();
			}
		}
		
		/*if(!defaultLanguage.equals("en_US")){
			checkIntegrity(standardLanguageYaml, standardBase_en_US);
		}*/
		
		plugin.getLogger().info("Default Language: " + defaultLanguageYaml.getString("language.id"));
		
		File folder = new File(pathExternalLanguages);
		for(File f : folder.listFiles()){
			if(f.isDirectory()) continue;
			if(!f.getName().endsWith(".yml")){
				plugin.getLogger().warning("Language files should end with '.yml', ignoring '" + f.getName() + "'.");
				continue;
			}
			
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			if(!(cfg.contains("language.id") && cfg.contains("language.name"))){
				plugin.getLogger().warning("Language File '" + f.getName() + "' is missing needed features. Please specify 'language.id' and 'language.name'. File is skipped.");
				continue;
			}
			
			checkIntegrity(cfg, standardBase_en_US);
			
			cfg.set("language.isDefault", false);
			allLanguages.put(new LanguageInformation(cfg.getString("language.id"), false, f.getName()), cfg);
		}
		
		getLanguageInformations();
	}
	
	private void checkIntegrity(YamlConfiguration cfg, YamlConfiguration def) {
		String missingKeys = "";
		for(String s : def.getKeys(true)){
			if(!cfg.contains(s) || !cfg.get(s).getClass().equals(def.get(s).getClass())){
				cfg.set(s, def.get(s));
				if(!missingKeys.equals("")) missingKeys += ", ";
				missingKeys += s;
			}
		}
		
		if(!missingKeys.equals("")){
			plugin.getLogger().info("Language '" + cfg.getString("language.id") + "'('" + cfg.getString("language.name") + "') is missing keys [" + missingKeys + "]. Defaulting them to '" + def.getString("language.id") + "'('" + def.getString("language.name") + "') language.");
		}
	}

	public YamlConfiguration getStandardLanguage(){
		return standardLanguageEntry.getValue();
	}
	
	private void initDefaults(){
		for(String lang : standards){
			File file = new File(pathExternalLanguages + "default/" + lang + ".yml");
			if (!file.exists()) {
				try {
					file.getParentFile().mkdirs();
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			InputStream is = plugin.getResource("languages/" + lang + ".yml");
			YamlConfiguration defaultCfg = YamlConfiguration.loadConfiguration(new InputStreamReader(is));
			boolean edited = false;
			for(String s : defaultCfg.getKeys(true)){
				if(cfg.get(s) == null || !cfg.get(s).equals(defaultCfg.get(s))){
					cfg.set(s, defaultCfg.get(s));
					edited = true;
				}
			}
			if(edited){
				try {
					cfg.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			defaultCfg.set("language.isDefault", true);
			allLanguages.put(new LanguageInformation(defaultCfg.getString("language.id"), true, file.getName()), defaultCfg);
		}
	}
	
	public YamlConfiguration getLanguage(String id){
		for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
			if(e.getValue().getString("language.id").equals(id)){
				return e.getValue();
			}
		}
		return null;
	}
	
	public void getLanguageInformations(){
		for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
			YamlConfiguration cfg = e.getValue();
			plugin.getLogger().info("Language [ID="+cfg.getString("language.id")+" NAME="+cfg.getString("language.name")+" FILE="+e.getKey().getFilename()+" DEFAULT="+e.getKey().isDefaultLang()+"]");
		}
	}
	
	public Collection<YamlConfiguration> getAvailableLanguages(){
		return allLanguages.values();
	}
	
	private class LanguageInformation {
		private String id;
		private boolean defaultLang;
		private String filename;
		public LanguageInformation(String id, boolean defaultLang, String filename){
			this.id = id; this.defaultLang = defaultLang; this.filename = filename;
		}
		@SuppressWarnings("unused")
		public String getId() { return id; }
		public boolean isDefaultLang() { return defaultLang; }
		public String getFilename() { return filename; }
	}

	public boolean setStandardLanguage(String id) {
		for(Entry<LanguageInformation, YamlConfiguration> e : allLanguages.entrySet()){
			if(e.getValue().getString("language.id").equalsIgnoreCase(id)){
				standardLanguageEntry = e;
				return true;
			}
		}
		return false;
	}
}
