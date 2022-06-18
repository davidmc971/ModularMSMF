package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.PlayerKillConfig;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;

/**
 * Events in this class are valid for all states which complies to the
 * BasicsModule.
 * 
 * @author Lightkeks
 * @since 0.3
 * @version 0.1
 */
public class BasicEvents implements Listener, TabCompleter {
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();

	/**
	 * Register any player who got killed and return the Player <code>p</code> their
	 * KillType <code>kt</code>
	 * 
	 * @param p  Player which got killed
	 * @param kt Player how it got killed
	 */
	public void registerKilledPlayer(Player p, KillType kt) {
		killedPlayers.add(new PlayerKillConfig(p, kt));
	}

	/**
	 * EventHandler which show's the type of kill the player gone through
	 * 
	 * @param event Player who died
	 */
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

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {

		return null;
	}
}
