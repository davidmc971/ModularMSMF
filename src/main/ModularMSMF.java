package main;

import java.io.IOException;
import java.io.InputStream;
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
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import commands.Ban;
import commands.Feed;
import commands.GetServer;
import commands.Heal;
import commands.Home;
import commands.Kick;
import commands.Kill;
import commands.Language;
import commands.Motd;
import commands.ModularMSMFCommand;
import commands.Mute;
import commands.SetSpawn;
import commands.Spawn;
import commands.Teleport;
import commands.Unban;
import eco.EconomySystemAlex;
import eco.EconomySystemDavid;
//import eco.EconomySystemDavid;
import listeners.Events;
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
	private Motd motd;
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

		motd = new Motd(this);
		motd.load();

		System.out.println("ModularMSMF has been enabled.");
		System.out.println("ModularMSMF enabling Events.");

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
		case "money":
			if (ecoSys.cmd(sender, cmd, commandLabel, args))
				return true; // economy commands david /money
			break;
<<<<<<< HEAD
		case "setspawn":
=======
*/		case "setspawn":
>>>>>>> 98f64a1c3c9813988cebd3915a1f882a08ce461b
			SetSpawn.cmd(sender, cmd, commandLabel, args);
			return true;
		case "spawn":
			Spawn.cmd(sender, cmd, commandLabel, args);
			return true;
		case "eco":
			if (ecoPlug.cmd(sender, cmd, commandLabel, args, this))
				return true; // economy commands alex /eco
			return true;
		case "kill":
			Kill.cmd(sender, cmd, commandLabel, args, mainEvents);
			return true;
		case "multiplux":
			ModularMSMFCommand.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "teleport":
			Teleport.cmd(sender, cmd, commandLabel, args);
			return true;
		case "heal":
			Heal.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "feed":
			Feed.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "kick":
			Kick.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "ban":
			Ban.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "unban":
			Unban.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "home":
			return true;
		case "language":
			Language.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "sethome":
			Home.cmd(sender, cmd, commandLabel, args);
			return true;
		case "mute":
			Mute.cmd(sender, cmd, commandLabel, args, this);
			return true;
		case "getserver":
			GetServer.cmd(sender, cmd, commandLabel, args);
			return true;
		case "motd":
			Motd.cmd(sender, cmd, commandLabel, args);
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