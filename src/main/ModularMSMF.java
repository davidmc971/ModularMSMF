package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.plugin.java.JavaPlugin;

import listeners.Events;
import util.DataManager;
import util.LanguageManager;

/**
 * @author Lightkeks
 * @author ernikillerxd64
 */

public class ModularMSMF extends JavaPlugin implements CommandExecutor {

	private DataManager dataManager;
	private LanguageManager languageManager;
	public final boolean debug = true;
	private String debugTimestamp = "";

	public String getDebugTimestamp() {
		return debugTimestamp;
	}

	private Events mainEvents;

	public String pluginver = this.getDescription().getVersion();
	public String nameplugin = this.getDescription().getName();
	public List<String> authors = this.getDescription().getAuthors();

	@Override
	public void onEnable() {
		dataManager = new DataManager(this.getLogger());
		dataManager.init();
		this.getServer().getPluginManager().registerEvents(dataManager, this);

		languageManager = new LanguageManager(this);

		System.out.println("ModularMSMF has been enabled.");

		mainEvents = new Events(this);
		this.getServer().getPluginManager().registerEvents(mainEvents, this);
		// this.getServer().getPluginManager().registerEvents(ecoSys, this);

		YamlConfiguration pluginyaml = YamlConfiguration
				.loadConfiguration(new InputStreamReader(this.getResource("plugin.yml")));
		ConfigurationSection cs = pluginyaml.getConfigurationSection("commands");
		Set<String> keyset = cs.getKeys(true);
		String temp = "";
		for (String s : keyset) {
			if (!s.contains(".")) {
				temp += s + " ";
				this.getCommand(s).setExecutor(this);
			}
		}

		if (debug) {
			this.getLogger().info(temp);
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
	}

	@Override
	public void onLoad() {
		this.getLogger().info("MultiPluxPlugin is starting up.");
		this.getLogger().info("MultiPluxPlugin is loading Events.");
	}

	@Override
	public void onDisable() {
		// ecoSys.unload();
		dataManager.saveAllUserdata();
		this.getLogger().info("MultiPluxPlugin has been disabled.");
		this.getLogger().info("MultiPluxPlugin disabling Events.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		switch (commandLabel.toLowerCase()) {
		
		}
		return false;
	}

	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}
}