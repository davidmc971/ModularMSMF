package listeners;

import java.io.IOException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import main.ModularMSMF;

public class Events implements Listener {

	public ModularMSMF plugin;

	public Events(ModularMSMF plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
		
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) throws IOException {
		
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		
	}
}
