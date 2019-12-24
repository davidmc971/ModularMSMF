package io.github.davidmc971.modularmsmf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidmc971.modularmsmf.commands.*;
import io.github.davidmc971.modularmsmf.core.DataManager;
import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.core.PlayerManager;
import io.github.davidmc971.modularmsmf.listeners.Events;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.main.CommandLoader;

/**
 * @author Lightkeks, davidmc971
 */

public class ModularMSMF extends JavaPlugin implements CommandExecutor {
	private DataManager dataManager;
	private LanguageManager languageManager;
	private PlayerManager playerManager;

	private CommandMotd motd;

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

	private Map<String, AbstractCommand> commandMap = null;
	
	//here our plugin is loaded and will be enabled
	@Override
	public void onEnable() {
		dataManager = new DataManager(this);
		this.getServer().getPluginManager().registerEvents(dataManager, this);

		languageManager = new LanguageManager(this);
		
		playerManager = new PlayerManager(this);

		motd = new CommandMotd(this);
		motd.load();

		getLogger().info("Loading events...");
		mainEvents = new Events(this);
		this.getServer().getPluginManager().registerEvents(mainEvents, this);
		//this.getServer().getPluginManager().registerEvents(ecoSys, this);

		
		/// COMMAND LOADING START ///
		getLogger().info("Loading commands...");
		//DONE: get list of commands instantiated from commands package
		//DONE: compare to list of commands from plugin.yml
		//DONE: inform about missing commands
		
		CommandLoader loader = new CommandLoader(this);
		
		//<commandLabel, instance of AbstractCommand>
		commandMap = new LinkedHashMap<String, AbstractCommand>();
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
		
		
		if (debug) {
			InputStream in = this.getResource("build_timestamp.properties");
			Properties buildprop = new Properties();
			try {
				buildprop.load(in);
				debugTimestamp = buildprop.getProperty("timestamp").replaceAll("_", " ");
				this.getLogger().info("Debug: Build [" + debugTimestamp + "]");
			} catch (IOException e) {
				Bukkit.getLogger().warning("Loading buildprop failed.");
			}
		}
		
		getLogger().info("We are finished with enabling ModularMSMF, hooray!");
	}

	@Override
	public void onLoad() {
		this.getLogger().info("ModularMSMF is loading up.");
	}

	@Override
	public void onDisable() {
		dataManager.saveAllUserdata();
		playerManager.saveAll();
		this.getLogger().info("ModularMSMF has been disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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
	
	public Map<String, AbstractCommand> getCommandMap(){
		return commandMap;
	}
}