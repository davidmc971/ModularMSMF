package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.PlayerKillConfig;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class BasicEvents implements Listener {
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();
	private ModularMSMFCore plugin;

	public BasicEvents() {
		this.plugin = ModularMSMFCore.Instance();
	}

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
						event.deathMessage().asComponent();
						break;
					case SUICIDE:
						event.deathMessage().asComponent();
						break;
					case HOMOCIDE:
						event.deathMessage().asComponent();
						break;
				}
				killedPlayers.remove(pkf);
				temp = true;
				break;
			}
		}
		if (!temp) {
			Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "coremodule.event.just_died", "_var", p.getName());
		}
	}
}
