package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
			Utils.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "coremodule.events.just_died", "_var",
					p.getName());
		}
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event, FileConfiguration language, UUID uuid) {
		FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
		if (cfg.getBoolean("players.flying", true)) {
		}

		//FIXME: Player connect even banned - config broken?
		String reason = language.getString("coremodule.events.banned");
		cfg.set("players.ipAddress", event.getAddress().getHostAddress());
		if (cfg.isBoolean("banned") && cfg.getBoolean("banned", true)) {
			if (cfg.isString("reason")) {
				event.disallow(Result.KICK_BANNED, Component.text(cfg.getString("reason")));
			}
			event.disallow(Result.KICK_BANNED, Component.text(reason));
		}
	}
}
