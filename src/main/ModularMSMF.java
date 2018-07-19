package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import commands.*;
import listeners.Events;
import util.ChatUtils;
import util.ChatUtils.ChatFormat;
import util.DataManager;
import util.LanguageManager;

/**
 * @author Lightkeks
 * @author ernikillerxd64
 */

public class ModularMSMF extends JavaPlugin implements CommandExecutor {
	private DataManager dataManager;
	private LanguageManager languageManager;
	private CommandMotd motd;
	
	public final boolean debug = true;
	private String debugTimestamp = "";

	public String getDebugTimestamp() {
		return debugTimestamp;
	}

	private Events mainEvents;

	public String pluginver = this.getDescription().getVersion();
	public String nameplugin = this.getDescription().getName();
	public List<String> authors = this.getDescription().getAuthors();

	//here our plugin is loaded and will be enabled
	@Override
	public void onEnable() {
		dataManager = new DataManager(this.getLogger());
		dataManager.init();
		this.getServer().getPluginManager().registerEvents(dataManager, this);

		languageManager = new LanguageManager(this);

		motd = new CommandMotd(this);
		motd.load();
		
		getLogger().info("Loading events...");
		mainEvents = new Events(this);
		this.getServer().getPluginManager().registerEvents(mainEvents, this);
		//this.getServer().getPluginManager().registerEvents(ecoSys, this);

		getLogger().info("Loading commands...");
		//get list of commands instantiated from commands package
		//TODO: compare to list of commands from plugin.yml
		//TODO: inform about missing commands
		ArrayList<AbstractCommand> commandList = new ArrayList<AbstractCommand>();
		ClassLoader classLoader = this.getClassLoader();
		String packageName = "commands";
		
		ClassPath path = null;
		try {
			path = ClassPath.from(classLoader);
		} catch (IOException e1) {
			getLogger().severe(e1.toString());
		}
		if(path != null) {
			for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive(packageName)) {
				if(!info.getName().equals("commands.AbstractCommand")) {
					try {
						Class<?> clazz = Class.forName(info.getName(), true, classLoader);
						commandList.add((AbstractCommand)clazz.getConstructor(ModularMSMF.class).newInstance(this));
						
					} catch (Exception e) {
						getLogger().severe(e.toString());
					}
				}
			}
		}
		
		{
			String temp = "";
			for (AbstractCommand cmd : commandList) {
				temp += cmd.getCommandLabel() + ", ";
			}
			getLogger().info("Commands [" + temp.substring(0, temp.length() - 2) + "] loaded!");
		}
		
		YamlConfiguration pluginyaml = YamlConfiguration
				.loadConfiguration(new InputStreamReader(this.getResource("plugin.yml")));
		ConfigurationSection cs = pluginyaml.getConfigurationSection("commands");
		Set<String> keyset = cs.getKeys(true);
		
		String temp = "";
		for (String s : keyset) {
			if (!s.contains(".")) {
				temp += s + " ";
			}
		}
		
		commandList.forEach(cmd -> this.getCommand(cmd.getCommandLabel()).setExecutor(cmd));

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
		
		getLogger().info("We are finished with enabling ModularMSMF, hooray!");
	}

	@Override
	public void onLoad() {
		this.getLogger().info("ModularMSMF is loading up.");
	}

	@Override
	public void onDisable() {
		// ecoSys.unload();
		dataManager.saveAllUserdata();
		this.getLogger().info("ModularMSMF has been disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		switch (commandLabel.toLowerCase()) {
		case "slaughter":
			if (sender instanceof Player) {
				Location playerloc = ((Player) sender).getLocation();
				for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
					if (!(e instanceof Player) && (e instanceof Monster))
						e.remove();
				}
			}
			return true;
		default:
			sender.sendMessage(ChatUtils.getFormattedPrefix(ChatFormat.ERROR) + "I guess that command is not correctly implemented yet :o");
			sender.sendMessage(ChatUtils.getFormattedPrefix(ChatFormat.INFO) + "You can report this using /report bug <description> if you wish!");
			return true;
		}
	}

	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}
}