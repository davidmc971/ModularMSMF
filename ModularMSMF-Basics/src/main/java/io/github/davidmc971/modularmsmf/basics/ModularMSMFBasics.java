package io.github.davidmc971.modularmsmf.basics;

import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.basics.listeners.BasicEvents;
import io.github.davidmc971.modularmsmf.core.configuration.AbstractConfigurationLoader;
import io.github.davidmc971.modularmsmf.core.configuration.JSONConfigurationLoader;
import io.github.davidmc971.modularmsmf.core.configuration.YamlConfigurationLoader;

public class ModularMSMFBasics extends JavaPlugin {

	public static String moduleName = "Basics";

    private static ModularMSMFBasics instance = null;
    public static ModularMSMFBasics Instance() {
        return instance;
    }

    public ModularMSMFBasics() {
        instance = this;
    }
    public final boolean debug = true;
    private PluginManager pluginManager;

    private DataManager dataManager;
	private LanguageManager languageManager;

    private PluginManager plgman;
    private BasicEvents basicEvents;

    public BasicEvents getBasicEvents() {
        return basicEvents;
    }
    private String debugTimestamp = "";

    @Override
    public void onEnable() {
		pluginManager = getServer().getPluginManager();
		if (debug) getLogger().info("--- onEnable() ---");
		dataManager = new DataManager(this);
		pluginManager.registerEvents(dataManager, this);

		languageManager = new LanguageManager(this);

		//motd = new CommandMotd();
		//motd.load();

		getLogger().info("Loading events...");

		if (debug) {
			try {
				YamlConfiguration buildprop = YamlConfiguration
				.loadConfiguration(new InputStreamReader(this.getResource("props.yml")));
				debugTimestamp = buildprop.getString("build_timestamp").replaceAll("_", " ");
				this.getLogger().info("Debug: Build [" + debugTimestamp + "]");
			} catch (Exception e) {
				Bukkit.getLogger().warning("Loading props.yml failed.");
			}
		}
        this.plgman = getServer().getPluginManager();
        plgman.registerEvents(basicEvents, this);
        CommandLoader.registerCommands(this);
    }

    @Override
    public void onLoad() {
        if (debug) getLogger().info("--- onLoad() ---");
		this.getLogger().info("ModularMSMFBasics is loading up.");
        basicEvents = new BasicEvents();
    }



    @Override
    public void onDisable(){
        if (debug) getLogger().info("--- onDisable() ---");
        dataManager.unload();
        this.getLogger().info("ModularMSMFBasics has been disabled.");
    }


    private YamlConfigurationLoader yamlLoader;
	public YamlConfigurationLoader yamlLoader() {
		if (yamlLoader == null) yamlLoader = new YamlConfigurationLoader();
		return yamlLoader;
	}
	private JSONConfigurationLoader jsonLoader;
	public JSONConfigurationLoader jsonLoader() {
		if (jsonLoader == null) jsonLoader = new JSONConfigurationLoader();
		return jsonLoader;
	}
    private Map<String, AbstractConfigurationLoader> configLoaders;
	public Map<String, AbstractConfigurationLoader> configLoaders() {
		if (configLoaders == null) configLoaders =
			new LinkedHashMap<String,AbstractConfigurationLoader>(){
				private static final long serialVersionUID = 1L;
				{
					put(yamlLoader().getFileExtension(), yamlLoader());
					put(jsonLoader().getFileExtension(), jsonLoader());
				}
			};
		return configLoaders;
	}

    public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}
}
