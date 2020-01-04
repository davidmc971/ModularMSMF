package io.github.davidmc971.modularmsmf.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.data.Language;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.KillType;
import io.github.davidmc971.modularmsmf.util.Utils;

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
	private String coloredPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.COLORED);

	public ModularMSMF plugin;
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();

	public Events(ModularMSMF plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		event.setJoinMessage(welcomePrefix+language.getString("event.welcome").replaceAll("_var", player.getDisplayName()));
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException {
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		event.setQuitMessage(quitPrefix+language.getString("event.quit").replaceAll("_var", player.getDisplayName()));
	}

	@EventHandler
	/**
	 * @TODO: needs some rewrite
	 */
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
	/**
	 * @TODO: needs some rewrite
	 */

	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		
		boolean temp = false;
		for(PlayerKillConfig pkf : killedPlayers){
			if(pkf.getP().getName().equals(event.getEntity().getName())){
				//String msg = "";
				switch(pkf.getKt()){
				case KILL:
					event.setDeathMessage(deathPrefix+language.getString("event.killed_player").replaceAll("_var", event.getEntity().getDisplayName()));
					//Bukkit.broadcastMessage(deathPrefix+language.getString("event.killed_player").replaceAll("_var", event.getEntity().getDisplayName()));
					break;
				case SUICIDE:
					event.setDeathMessage(deathPrefix+language.getString("event.suicide").replaceAll("_var", event.getEntity().getDisplayName()));
					//Bukkit.broadcastMessage(deathPrefix+language.getString("event.suicide").replaceAll("_var", event.getEntity().getDisplayName()));
					break;
				case HOMOCIDE:
					//event.setDeathMessage(deathPrefix+language.getString("event.homocide"));
					//Bukkit.broadcastMessage(deathPrefix + language.getString("event.homocide"));
					player.sendMessage("You got killed in a homocide.");
					break;
				}
				//event.setDeathMessage(msg);
				killedPlayers.remove(pkf);
				temp = true;
				break;
			}
		}
		if(!temp){
			//event.setDeathMessage(deathPrefix+language.getString("event.just_died").replaceAll("_var", event.getEntity().getDisplayName()));
			
		}
	}

	public void registerKilledPlayer(Player p, KillType kt){
		killedPlayers.add(new PlayerKillConfig(p, kt));
	}
 
	private class PlayerKillConfig {
		private Player p;
		private KillType kt;
		public PlayerKillConfig(Player p, KillType kt){ this.p = p; this.kt = kt; }
		public Player getP() { return p; }
		public KillType getKt() { return kt; }
	}

	@EventHandler
	public <Sender> void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(event.getPlayer().getUniqueId());
		Player player = event.getPlayer();
		FileConfiguration language = Utils.configureCommandLanguage(player, plugin);
		if (playercfg.isBoolean("muted") && playercfg.getBoolean("muted") && !event.getMessage().startsWith("/")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(noPermPrefix+language.getString("event.muted"));
		} else {
			event.getPlayer().sendMessage(coloredPrefix+language.getString("event.muted"));
		}
	}

	/*
	 * public HandlerList getHandlers() { return getHandlers(); }
	 */

	/**
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();

		File file = new File("plugins/ModularMSMF/settings.yml");
		if(!file.exists()){
			p.sendMessage("Es wurde kein Spawn gesetzt");
		}

		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double x = cfg.getDouble("X");
		double y = cfg.getDouble("Y");
		double z = cfg.getDouble("Z");
		double yaw = cfg.getDouble("Yaw");
		double pitch = cfg.getDouble("Pitch");
		String worldname = cfg.getString("Worldname");

		Location loc = p.getLocation();

		loc.setX(x);
		loc.setY(y);
		loc.setZ(z);
		loc.setYaw((float)yaw);
		loc.setPitch((float)pitch);

		World welt = Bukkit.getWorld(worldname);
		loc.setWorld(welt);

		e.setRespawnLocation(loc);;
		p.sendMessage("Du wurdest gespawnt!");
	}
	*/
}
