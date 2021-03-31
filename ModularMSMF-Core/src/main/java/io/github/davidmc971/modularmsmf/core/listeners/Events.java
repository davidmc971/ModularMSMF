package io.github.davidmc971.modularmsmf.core.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
//import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Lightkeks, davidmc971
 */

public class Events implements Listener {

	public ModularMSMFCore plugin;
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
	public void onLogin(PlayerLoginEvent event, FileConfiguration language, UUID uuid) {
		// Player player = event.getPlayer();
		// FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		String reason = language.getString("event.banned");
		// FileConfiguration cfg =
		// plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		cfg.set("players.ipAddress", event.getAddress());
		if (cfg.isBoolean("banned") && cfg.getBoolean("banned")) {
			if (cfg.isString("reason")) {
				event.disallow(Result.KICK_BANNED, cfg.getString("reason"));
			} else {
				event.disallow(Result.KICK_BANNED, reason);
			}
		}
	}

	public void registerKickedPlayer(UUID uuid) {
		kickedPlayers.add(uuid);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncChatEvent event, UUID uuid) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg.get("toggleablestuff.isMuted").toString().equals("true")) {
			event.setCancelled(true);
		} else {
			Player p = event.getPlayer();
			Component msg = event.message();

			msg = filterMessage(msg, blacklistedExpressions, p);

			event.message(msg);
		}
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

	private Component filterMessage(Component msg, List<String> blacklist, CommandSender cmdsnd) {
		for (String expr : blacklist) {
			expr = expr.toLowerCase();
			// TODO: asdasdasd
		}
		return msg;
	}

	public ChatColor toColor(FileConfiguration settings, String colorKey) {
		return ChatColor.getByChar(settings.getString(colorKey).charAt(0));
	}

}