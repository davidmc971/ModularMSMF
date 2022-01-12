package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.PlayerKillConfig;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;

public class BasicEvents implements Listener {
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();

	public void registerKilledPlayer(Player p, KillType kt) {
		killedPlayers.add(new PlayerKillConfig(p, kt));
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity().getPlayer();
		CoreEvents.lastLocation.put(p.getName(), p.getLocation());
		boolean temp = false;
		for (PlayerKillConfig pkf : killedPlayers) {
			if (pkf.getP().getName().equals(event.getEntity().getName())) {
				switch (pkf.getKt()) {
					case KILL:
						event.deathMessage(Component.empty());
						break;
					case SUICIDE:
						event.deathMessage(Component.empty());
						break;
					case HOMOCIDE:
						event.deathMessage(Component.empty());
						break;
				}
				killedPlayers.remove(pkf);
				temp = true;
				break;
			}
		}
		if (!temp) {
			Utils.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "events.just_died", "_player",
					p.getName());
		}
	}

	@EventHandler
	public void onLogin(PlayerPreLoginEvent event, UUID uuid) {
		// TODO: move event PlayerLoginEvent to early loading
		FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
		if (cfg.getBoolean("player.flying", true)) {
		}
		System.out.println(cfg.getString("banned") + ": Lightkeks" + " -> PlayerPreLoginEvent");
		System.out.println(cfg.getBoolean("banned") + ": Lightkeks" + " -> PlayerPreLoginEvent");
		// TODO: working on disallow login
		// cfg.set("players.ipAddress", event.getAddress().getHostAddress());
		if (cfg.isSet("banned") && cfg.getBoolean("banned", true)) {
			System.out.println(cfg.getString("banned") + " should be set" + " -> PlayerPreLoginEvent");
			System.out.println(cfg.isBoolean("banned") + " should be true" + " -> PlayerPreLoginEvent");
			// event.disallow(Result.KICK_BANNED, Component.text(cfg.getString("reason")));
			event.kickMessage(Component.empty());
		}
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event, UUID uuid) {
		// TODO: move event PlayerLoginEvent to early loading
		FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
		if (cfg.getBoolean("player.flying", true)) {
		}
		System.out.println(cfg.getString("banned") + ": Lightkeks" + " -> PlayerLoginEvent");
		System.out.println(cfg.getBoolean("banned") + ": Lightkeks" + " -> PlayerLoginEvent");
		// TODO: working on disallow login
		// cfg.set("players.ipAddress", event.getAddress().getHostAddress());
		if (cfg.isSet("banned") && cfg.getBoolean("banned", true)) {
			System.out.println(cfg.getString("banned") + " should be set" + " -> PlayerLoginEvent");
			System.out.println(cfg.isBoolean("banned") + " should be true" + " -> PlayerLoginEvent");
			event.disallow(Result.KICK_BANNED, Component.text(cfg.getString("reason")));
			event.kickMessage(Component.empty());
		}
	}

	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent event, UUID uuid) {
		// TODO: move event PlayerLoginEvent to early loading
		FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
		if (cfg.getBoolean("player.flying", true)) {
		}
		System.out.println(cfg.getString("banned") + ": Lightkeks" + " -> AsyncPlayerPreLoginEvent");
		System.out.println(cfg.getBoolean("banned") + ": Lightkeks" + " -> AsyncPlayerPreLoginEvent");
		// TODO: working on disallow login
		// cfg.set("players.ipAddress", event.getAddress().getHostAddress());
		if (cfg.isSet("banned") && cfg.getBoolean("banned", true)) {
			System.out.println(cfg.getString("banned") + " should be set" + " -> AsyncPlayerPreLoginEvent");
			System.out.println(cfg.isBoolean("banned") + " should be true" + " -> AsyncPlayerPreLoginEvent");
			event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
					Component.text(cfg.getString("reason")));
			event.kickMessage(Component.empty());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Utils.broadcastWithConfiguredLanguageEach(ChatFormat.QUIT, "events.quit", "_player",
				event.getPlayer().getName(), "_servername", Bukkit.getServer().getName());
	}
}
