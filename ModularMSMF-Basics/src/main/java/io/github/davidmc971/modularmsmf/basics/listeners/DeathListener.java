package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.PlayerKillConfig;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.listeners.Events;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class DeathListener implements Listener {
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();
    private ModularMSMFCore MMSMFCore;

    public DeathListener() {
        this.MMSMFCore = ModularMSMFCore.Instance();
    }

	public void registerKilledPlayer(Player p, KillType kt) {
		killedPlayers.add(new PlayerKillConfig(p, kt));
	}
    
    // FIXME[epic=code needed,seq=22] Replace deprecated methods
    @EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		Events.lastLocation.put(p.getName(), p.getLocation());
		boolean temp = false;
		for (PlayerKillConfig pkf : killedPlayers) {
			if (pkf.getP().getName().equals(event.getEntity().getName())) {
				switch (pkf.getKt()) {
				case KILL:
					event.deathMessage(null);
					break;
				case SUICIDE:
					event.deathMessage(null);
					break;
				case HOMOCIDE:
					event.deathMessage(null);
					break;
				}
				killedPlayers.remove(pkf);
				temp = true;
				break;
			}
		}
		if (!temp) {
			Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatFormat.DEATH, "basicsmodule.event.just_died", "_var",
					event.getEntity().getName());
		}
	}
}
