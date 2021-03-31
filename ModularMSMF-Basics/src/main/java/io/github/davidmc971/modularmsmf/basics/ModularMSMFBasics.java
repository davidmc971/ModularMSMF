package io.github.davidmc971.modularmsmf.basics;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.basics.listeners.DeathListener;
import io.github.davidmc971.modularmsmf.core.configuration.AbstractConfigurationLoader;
import io.github.davidmc971.modularmsmf.core.configuration.JSONConfigurationLoader;
import io.github.davidmc971.modularmsmf.core.configuration.YamlConfigurationLoader;

public class ModularMSMFBasics extends JavaPlugin {
    private static ModularMSMFBasics instance = null;
    public static ModularMSMFBasics Instance() {
        return instance;
    }

    public ModularMSMFBasics() {
        instance = this;
    }
    
    private PluginManager plgman;
    private DeathListener deathListener;

    @Override
    public void onLoad() {
        deathListener = new DeathListener();
    }

    @Override
    public void onEnable() {
        this.plgman = getServer().getPluginManager();
        plgman.registerEvents(deathListener, this);
        CommandLoader.registerCommands(this);
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

    public DeathListener getDeathListener() {
        return deathListener;
    }
}
