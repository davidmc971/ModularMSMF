package io.github.davidmc971.modularmsmf.basics.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.davidmc971.modularmsmf.basics.commands.CommandChannels;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtils;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.PlayerKillConfig;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtils.ChannelPrefix;
import io.github.davidmc971.modularmsmf.core.commands.CommandListPlayers;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;

/**
 * Events in this class are valid for all states which complies to the
 * BasicsModule.
 * 
 * @author Lightkeks
 * @since 0.3
 * @version 0.1
 */
public class BasicEvents implements Listener {
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

	/**
	 * Players who leave the server show up a leave message to all players
	 * 
	 * @param event Player who leave
	 */
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Utils.broadcastWithConfiguredLanguageEach(ChatFormat.QUIT, "events.quit", "_player",
				event.getPlayer().getName(), "_servername", Bukkit.getServer().getName());
		CommandListPlayers.playerlist.remove(event.getPlayer().getName());
	}

	/**
	 * Players, which want to communicate, can see their prefix/world they're in or
	 * even see their group prefix
	 * 
	 * @param e Player who tries to chat
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncChatEvent e, String... args) { //FIXME: error show up
		if (CommandChannels.setChannelUsr.containsValue(e.getPlayer().getName())
				&& CommandChannels.setChannelUsr.containsKey("admin")) {
			e.message(Component.text(ChatUtils.getFormattedPrefix(ChannelPrefix.ADMIN) + e.originalMessage())); // TODO: testing purposes only
		}
	}
}
