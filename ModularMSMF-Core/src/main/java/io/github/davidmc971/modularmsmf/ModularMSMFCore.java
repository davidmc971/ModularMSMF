package io.github.davidmc971.modularmsmf;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
//import io.github.davidmc971.modularmsmf.commands.*;
import io.github.davidmc971.modularmsmf.configuration.AbstractConfigurationLoader;
import io.github.davidmc971.modularmsmf.configuration.JSONConfigurationLoader;
import io.github.davidmc971.modularmsmf.configuration.YamlConfigurationLoader;
import io.github.davidmc971.modularmsmf.core.DataManager;
import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.core.PlayerManager;
import io.github.davidmc971.modularmsmf.listeners.CommandPreprocessor;
import io.github.davidmc971.modularmsmf.listeners.Events;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.main.CommandLoader;

/**
 * @author Lightkeks, davidmc971
 */

public class ModularMSMFCore extends JavaPlugin {
	private static ModularMSMFCore instance = null;
	public static ModularMSMFCore Instance() {
		return instance;
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

	private DataManager dataManager;
	private LanguageManager languageManager;
	private PlayerManager playerManager;
	

	//private CommandMotd motd;

	public final boolean debug = true;
	private String debugTimestamp = "";

	public String getDebugTimestamp() {
		return debugTimestamp;
	}

	private Events mainEvents;

	public Events getMainEvents() {
		return mainEvents;
	}

	public String pluginver = this.getDescription().getVersion();
	public String nameplugin = this.getDescription().getName();
	public List<String> authors = this.getDescription().getAuthors();

	private Map<String, IModularMSMFCommand> commandMap = null;
	
	//here our plugin is loaded and will be enabled
	@Override
	public void onEnable() {
		ModularMSMFCore.instance = this;
		if (debug) getLogger().info("--- onEnable() ---");
		dataManager = new DataManager(this);
		this.getServer().getPluginManager().registerEvents(dataManager, this);

		languageManager = new LanguageManager(this);
		
		playerManager = new PlayerManager(this);

		//motd = new CommandMotd();
		//motd.load();

		getLogger().info("Loading events...");
		mainEvents = new Events(this);
		this.getServer().getPluginManager().registerEvents(mainEvents, this);
		//this.getServer().getPluginManager().registerEvents(ecoSys, this);
		this.getServer().getPluginManager().registerEvents(CommandPreprocessor.Instance(), this);
		
		/// COMMAND LOADING START ///
		getLogger().info("Loading commands...");
		//DONE: get list of commands instantiated from commands package
		//DONE: compare to list of commands from plugin.yml
		//DONE: inform about missing commands
		
		CommandLoader loader = new CommandLoader(this);
		
		//<commandLabel, instance of AbstractCommand>
		commandMap = new LinkedHashMap<String, IModularMSMFCommand>();
		loader.loadCommands(this.getClassLoader()).forEach(cmd -> {
			Arrays.asList(cmd.getCommandLabels()).forEach(label -> {
				commandMap.put(label.toLowerCase(), cmd);
			});
		});

		//load plugin.yml for comparison
		YamlConfiguration pluginyaml = YamlConfiguration
				.loadConfiguration(new InputStreamReader(this.getResource("plugin.yml")));
		ConfigurationSection cs = pluginyaml.getConfigurationSection("commands");
		Set<String> keyset = cs.getKeys(true);
		keyset.removeIf(s -> s.contains("."));
		keyset.forEach(s -> s.toLowerCase());
		
		//all the commandLabels loaded from commands package
		ArrayList<String> loadedCommandLabels = new ArrayList<String>();
		commandMap.forEach((label, cmd) -> loadedCommandLabels.add(label));
		
		//create a list for commands not covered by plugin.yml and remove all that are covered from it
		ArrayList<String> commandsNotCoveredByYaml = new ArrayList<String>(loadedCommandLabels);
		commandsNotCoveredByYaml.removeAll(keyset);
		commandsNotCoveredByYaml.forEach(s -> getLogger().severe("The command " + s + " has been loaded but is not in the plugin.yml, it will be unloaded!"));
		//TODO: modify own plugin.yml to include commands
		
		//create a list for all commands that are in plugin.yml but not loaded from commands package
		ArrayList<String> commandsNotCoveredByCommandList = new ArrayList<String>(keyset);
		commandsNotCoveredByCommandList.removeAll(loadedCommandLabels);
		commandsNotCoveredByCommandList.forEach(s -> getLogger().severe("The command " + s + " from the plugin.yml is not loaded! A notification will be displayed when it is executed."));

		//commandMap contains only commands present in commands package
		commandMap.forEach((label, cmd) -> {
			if (!commandsNotCoveredByYaml.contains(label)) {
				//register all commands normally that are present in plugin.yml and commands package
				this.getCommand(label).setExecutor(cmd);
			}
		});
		
		//register commands that are in plugin.yml but not in commands package to display error message
		commandsNotCoveredByCommandList.forEach(s -> this.getCommand(s).setExecutor(this));
		
		//clean up
		commandsNotCoveredByYaml.clear();
		commandsNotCoveredByCommandList.clear();
		
		/// COMMAND LOADING END ///
		
		//TODO: load build time from maven (?)
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
		
		getLogger().info("We are finished with enabling ModularMSMF, hooray!");

		// if (debug) {
		// 	getLogger().info("Testing from main class starting.");
		// 	JSONConfiguration testcfg = new JSONConfiguration();
		// 	Map<Object, Object> nestedMap = new HashMap<Object, Object>();
		// 	nestedMap.put("String1", "Hello ");
		// 	nestedMap.put("String1", "World!");
		// 	//nestedMap.put("String1", "\":#^^}{");
		// 	Map<Object, Object> map = new HashMap<Object, Object>();
		// 	map.put("String", "abcdefg");
		// 	map.put("Integer", 420);
		// 	map.put("Double", 566.23d);
		// 	map.put("Map", nestedMap);
		// 	testcfg.createSection("root", map);
		// 	try {
		// 		testcfg.save(new File("plugins/ModularMSMF/jsonTest/mainTest.json"));
		// 	} catch (Exception e) {
		// 		e.printStackTrace();
		// 	}
		// }
	}

	@Override
	public void onLoad() {
		if (debug) getLogger().info("--- onLoad() ---");
		this.getLogger().info("ModularMSMF is loading up.");
	}

	@Override
	public void onDisable() {
		if (debug) getLogger().info("--- onDisable() ---");
		dataManager.unload();
		playerManager.saveAll();
		this.getLogger().info("ModularMSMF has been disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (debug) getLogger().info("--- onCommand() ---");
		sender.sendMessage(ChatUtils.getFormattedPrefix(ChatFormat.ERROR) + "I guess that command is not correctly implemented yet :o");
		sender.sendMessage(ChatUtils.getFormattedPrefix(ChatFormat.INFO) + "You can report this using /report bug <description> if you wish!");
		return true;
	}

	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public Map<String, IModularMSMFCommand> getCommandMap(){
		return commandMap;
	}
}