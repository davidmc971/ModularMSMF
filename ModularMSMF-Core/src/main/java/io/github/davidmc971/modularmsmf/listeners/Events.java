package io.github.davidmc971.modularmsmf.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
//import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.davidmc971.modularmsmf.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.KillType;
import io.github.davidmc971.modularmsmf.util.Utils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Lightkeks, davidmc971
 */

public class Events implements Listener {

	public ModularMSMFCore plugin;
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();
	private ArrayList<UUID> kickedPlayers = new ArrayList<UUID>();
	public static HashMap<String, Location> lastLocation = new HashMap<>();
	public static ArrayList<String> blacklistedExpressions = new ArrayList<String>();

	public Events(ModularMSMFCore plugin) {
		this.plugin = plugin;
		// currently an ArrayList containing words to be filtered
		// to be loaded from a configuration file later on
		blacklistedExpressions.add("hacker");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
		Player player = event.getPlayer();
		event.joinMessage(Component.empty());
		Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.WELCOME, "event.welcome", "_var",
				player.getDisplayName());

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException {
		// check if kicked players are still to be processed
		if (!kickedPlayers.isEmpty()) {
			if (kickedPlayers.contains(event.getPlayer().getUniqueId())) {
				event.setQuitMessage(null);
				kickedPlayers.remove(event.getPlayer().getUniqueId());
				return;
			}
		}
		// if check if uuid is same and if yes cancel event and remove from list

		Player player = event.getPlayer();
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		String reason = cfg.getString("reason");
		if (cfg.isBoolean("banned") == true) {
			Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.BANNED, "commands.ban.playerbanned", "_player",
					player.getDisplayName(), "_reason", reason);
			event.setQuitMessage(null);
		} else {
			Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.QUIT, "event.quit", "_var",
					player.getDisplayName());
			event.setQuitMessage(null);
		}
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		String reason = language.getString("event.banned");
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		if (cfg.isBoolean("banned") && cfg.getBoolean("banned")) {
			if (cfg.isString("reason")) {
				event.disallow(Result.KICK_BANNED, cfg.getString("reason"));
			} else {
				event.disallow(Result.KICK_BANNED, reason);
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		Events.lastLocation.put(p.getName(), p.getLocation());
		boolean temp = false;
		for (PlayerKillConfig pkf : killedPlayers) {
			if (pkf.getP().getName().equals(event.getEntity().getName())) {
				switch (pkf.getKt()) {
				case KILL:
					event.setDeathMessage(null);
					break;
				case SUICIDE:
					event.setDeathMessage(null);
					break;
				case HOMOCIDE:
					event.setDeathMessage(null);
					break;
				}
				killedPlayers.remove(pkf);
				temp = true;
				break;
			}
		}
		if (!temp) {
			Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "event.just_died", "_var",
					event.getEntity().getDisplayName());
		}
	}

	public void registerKilledPlayer(Player p, KillType kt) {
		killedPlayers.add(new PlayerKillConfig(p, kt));
	}

	public void registerKickedPlayer(UUID uuid) {
		kickedPlayers.add(uuid);
	}

	private class PlayerKillConfig {
		private Player p;
		private KillType kt;

		public PlayerKillConfig(Player p, KillType kt) {
			this.p = p;
			this.kt = kt;
		}

		public Player getP() {
			return p;
		}

		public KillType getKt() {
			return kt;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String msg = event.getMessage();

		// p.sendMessage(msg);

		msg = filterMessage(msg, blacklistedExpressions, p);

		// p.sendMessage(msg);

		// for(int i = 0; i < blacklistedExpressions.size(); i++){
		// if(!blacklistedExpressions.contains(msg.toLowerCase())) {
		// //plugin.getLogger().info("Async Chat Event");
		// FileConfiguration playercfg =
		// plugin.getDataManager().getPlayerCfg(event.getPlayer().getUniqueId());
		// if (playercfg.isBoolean("muted") && playercfg.getBoolean("muted") &&
		// !event.getMessage().startsWith("/")) {
		// event.setCancelled(true);
		// Utils.sendMessageWithConfiguredLanguage(plugin, event.getPlayer(),
		// ChatFormat.NOPERM, "event.muted");
		// }

		// FileConfiguration settings = plugin.getDataManager().settingsyaml; //TODO:
		// change to getter
		// ChatColor cl_prefix = toColor(settings, "chat.colors.prefix");
		// ChatColor cl_name = toColor(settings, "chat.colors.displayname");
		// ChatColor cl_msg = toColor(settings, "chat.colors.message");

		// //l.info("cl_prefix: " + cl_prefix);
		// //l.info("cl_name: " + cl_name);
		// //l.info("cl_msg: " + cl_msg);

		// String format = settings.getString("chat.format");
		// //l.info("format #1: " + format);
		// format = format.replaceAll("_name", "%1\\$s")
		// .replaceAll("_message", "%2\\$s")
		// .replaceAll("_clpre", cl_prefix.toString())
		// .replaceAll("_clname", cl_name.toString())
		// .replaceAll("_clmessage", cl_msg.toString());
		// //l.info("format #2: " + format);

		// event.setFormat(format);
		// } else {
		// p.sendMessage("Blacklisted text dectected. Not allowed to send: " +
		// msg.toLowerCase());
		// msg.replaceAll(blacklistedExpressions.get(i), "#");
		// event.setCancelled(true);
		// System.out.println("Sending text "+msg);
		// break;
		// }
		// }
		event.setMessage(msg);
	}

	private String filterMessage(String msg, List<String> blacklist, CommandSender cmdsnd) {
		for (String expr : blacklist) {
			expr = expr.toLowerCase();
			// cmdsnd.sendMessage("Checking for expression: " + expr);
			if (msg.toLowerCase().contains(expr)) {
				// cmdsnd.sendMessage("Found match for expression: " + expr);
				String replacement = "";
				for (int i = 0; i < expr.length(); i++) {
					replacement += "*";
				}
				// cmdsnd.sendMessage("Replacement text: " + replacement);
				msg = msg.replaceAll("(?i)" + Pattern.quote(expr), replacement);
			}
		}
		return msg;
	}

	public ChatColor toColor(FileConfiguration settings, String colorKey) {
		return ChatColor.getByChar(settings.getString(colorKey).charAt(0));
	}

	
}
