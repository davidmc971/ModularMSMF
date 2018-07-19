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

import commands.*;
import eco.EconomySystemAlex;
import eco.EconomySystemDavid;
import listeners.Events;
import util.ChatUtils;
import util.DataManager;
import util.LanguageManager;

/**
 * @author Lightkeks
 * @author ernikillerxd64
 */

public class ModularMSMF extends JavaPlugin implements CommandExecutor {

	private EconomySystemDavid ecoSys;
	private EconomySystemAlex ecoPlug;
	private DataManager dataManager;
	private LanguageManager languageManager;
	private CommandMotd motd;
	//private CommandLoader commandLoader;
	
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

		ecoSys = new EconomySystemDavid(this);
		//ecoSys.load();
		ecoPlug = new EconomySystemAlex(this);

		motd = new CommandMotd(this);
		motd.load();
		
		getLogger().info(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO) + "Loading events...");
		mainEvents = new Events(this);
		this.getServer().getPluginManager().registerEvents(mainEvents, this);
		//this.getServer().getPluginManager().registerEvents(ecoSys, this);

		getLogger().info(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO) + "Loading commands...");
		
		//commandLoader = new CommandLoader(this);
		//ArrayList<AbstractCommand> commands = commandLoader.loadCommands();
		
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
		
		getLogger().info(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO) + "We are finished with enabling ModularMSMF, hooray!");
	}

	@Override
	public void onLoad() {
		this.getLogger().info("ModularMSMF is starting up.");
		this.getLogger().info("ModularMSMF is loading Events.");
	}

	@Override
	public void onDisable() {
		// ecoSys.unload();
		dataManager.saveAllUserdata();
		this.getLogger().info("ModularMSMF has been disabled.");
		this.getLogger().info("ModularMSMF disabling Events.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		switch (commandLabel.toLowerCase()) {
		case "report":
			CommandReport.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "money":
			return ecoSys.cmd(sender, cmd, commandLabel, args); // economy commands david /money
		case "setspawn":
			CommandSetSpawn.cmd(sender, cmd, commandLabel, args);
			return true;
		case "spawn":
			CommandSpawn.cmd(sender, cmd, commandLabel, args);
			return true;
		case "eco":
			if (ecoPlug.cmd(sender, cmd, commandLabel, args, this))
				return true; // economy commands alex /eco
			return true;
		case "kill":
			CommandKill.cmd(sender, cmd, commandLabel, args, mainEvents);
			return true;
		case "mmsmf":
			CommandModularMSMF.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "teleport":
			CommandTeleport.cmd(sender, cmd, commandLabel, args);
			return true;
		case "heal":
			CommandHeal.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "feed":
			CommandFeed.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "kick":
			CommandKick.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "ban":
			//CommandBan.onCommand(sender, cmd, commandLabel, args, this);
			return true;
		case "unban":
			CommandUnban.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "home":
			return true;
		case "language":
			CommandLanguage.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "sethome":
			CommandHome.cmd(sender, cmd, commandLabel, args);
			return true;
		case "mute":
			CommandMute.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "serverinfo":
			CommandServerInfo.cmd(sender, cmd, commandLabel, args);
			return true;
		case "motd":
			CommandMotd.cmd(sender, cmd, commandLabel, args);
			return true;
		case "slaughter":
			if (sender instanceof Player) {
				Location playerloc = ((Player) sender).getLocation();
				for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
					if (!(e instanceof Player) && (e instanceof Monster))
						e.remove();
				}
			}
			return true;
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