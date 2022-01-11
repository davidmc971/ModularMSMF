package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import io.github.davidmc971.modularmsmf.api.ILanguage;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.PlayerKillConfig;
import io.github.davidmc971.modularmsmf.core.LanguageManager;
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
	public void onLogin(PlayerLoginEvent event, UUID uuid) {
		// TODO: move event PlayerLoginEvent to early loading
		FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
		if (cfg.getBoolean("player.flying", true)) {
		}
		//TODO: working on disallow login
		String reason = cfg.getString("reason");
		// cfg.set("players.ipAddress", event.getAddress().getHostAddress());
		if (cfg.contains("banned", true)) {
			if (cfg.getString("reason").contains(/* getReason */"")) {
				event.disallow(Result.KICK_BANNED, Component.text(reason));
			}
			event.disallow(Result.KICK_BANNED, Component.text(cfg.getString("reason")));
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Utils.broadcastWithConfiguredLanguageEach(ChatFormat.QUIT, "events.quit", "_player",
				event.getPlayer().getName(), "_servername", Bukkit.getServer().getName());
	}
}
