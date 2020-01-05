package io.github.davidmc971.modularmsmf.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import org.bukkit.event.player.PlayerQuitEvent;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.KillType;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Lightkeks, davidmc971 TODO: changing text with prefix
 */

public class Events implements Listener {

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);
	private String welcomePrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.WELCOME);
	private String quitPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.QUIT);
	private String deathPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.DEATH);
	private String paintedPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.PAINTED);

	public ModularMSMF plugin;
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();

	public Events(ModularMSMF plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		event.setJoinMessage(
				welcomePrefix + language.getString("event.welcome").replaceAll("_var", player.getDisplayName()));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException {
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		event.setQuitMessage(quitPrefix + language.getString("event.quit").replaceAll("_var", player.getDisplayName()));
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
		Player player = event.getEntity();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);

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
			event.setDeathMessage(deathPrefix
					+ language.getString("event.just_died").replaceAll("_var", event.getEntity().getDisplayName()));
		}
	}

	public void registerKilledPlayer(Player p, KillType kt) {
		killedPlayers.add(new PlayerKillConfig(p, kt));
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

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		plugin.getLogger().info("Async Chat Event");
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(event.getPlayer().getUniqueId());
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		if (playercfg.isBoolean("muted") && playercfg.getBoolean("muted") && !event.getMessage().startsWith("/")) {
			event.setCancelled(true);
			Utils.sendMessageWithConfiguredLanguage(plugin, event.getPlayer(), ChatFormat.NOPERM, "event.muted");
			// old:
			// event.getPlayer().sendMessage(noPermPrefix+language.getString("event.muted"));
		}
		ChatColor cc = ChatColor.getByChar((char)(plugin.getDataManager().settingsyaml.getString("chat.color").charAt(0)));



		event.setMessage(cc + event.getMessage());
		plugin.getLogger().info(event.getFormat());
		event.setFormat(event.getFormat().replaceAll("<", ""/**TODO: DataManager und settingsyaml */).replaceAll(">",ChatColor.RED + "]" + ChatColor.GRAY));

	}
}
