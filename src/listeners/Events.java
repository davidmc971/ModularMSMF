package listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.LogRecord;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.util.ForwardLogHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;

import main.ModularMSMF;
import net.md_5.bungee.api.ChatColor;
import stats.Statistics;
import util.DataManager;
import util.KillType;
import util.Utils;

public class Events implements Listener {

	public ModularMSMF plugin;
	private ArrayList<PlayerKillConfig> killedPlayers = new ArrayList<PlayerKillConfig>();

	public Events(ModularMSMF plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
		Player player = event.getPlayer();
		event.setJoinMessage(ChatColor.GRAY + player.getDisplayName() + " hat den Server " + ChatColor.GREEN
				+ "betreten" + ChatColor.GRAY + "!");
		// MOTD
		/*
		 * player.sendMessage("§6[ §e§lMOTD§r §6]§7 - Message Of The Day");
		 * player.sendMessage("§6[ §e§l" + Bukkit.getServerName() +
		 * " §r§6]§7 - Unser Server."); /** MOTD muss bald auf yaml erstellt
		 * werden!
		 */
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		YamlConfiguration cfg = DataManager.getPlayerCfg(player.getUniqueId());
		if (cfg.isBoolean("banned") && cfg.getBoolean("banned")) {
			if (cfg.isString("reason")) {
				event.disallow(Result.KICK_BANNED, cfg.getString("reason"));
			} else {
				event.disallow(Result.KICK_BANNED, "Du wurdest gebannt.");
			}
		}
	}

	@EventHandler // TODO Muss auch geändert werden!
	public void onQuit(PlayerQuitEvent event) throws IOException {
		Player player = event.getPlayer();
		event.setQuitMessage(ChatColor.GRAY + player.getName() + " hat den Server " + ChatColor.RED + "verlassen.");
	}

	@EventHandler // TODO Muss auch geändert werden!
	public void onDeath(PlayerDeathEvent event) {
		boolean temp = false;
		for(PlayerKillConfig pkf : killedPlayers){
			if(pkf.getP().getName().equals(event.getEntity().getName())){
				String msg = "";
				switch(pkf.getKt()){
				case KILL:
					msg = "Der arme Mitspieler namens " + event.getEntity().getName() + " wurde getötet!\n";
					break;
				case SUICIDE:
					msg = "Der arme Mitspieler namens " + event.getEntity().getName() + " hat Suizid begangen!\n";
					break;
				case HOMOCIDE:
					msg = "Der arme Mitspieler namens " + event.getEntity().getName() + " wurde in einem Homozid brutal ermordet!\n";
					break;
				}
				event.setDeathMessage(msg);
				killedPlayers.remove(pkf);
				temp = true;
				break;
			}
		}
		if(!temp){
			event.setDeathMessage("Der arme Mitspieler namens " + event.getEntity().getName() + " ist gestorben!\n");
		}
	}

	@EventHandler // TODO Muss auch geändert werden!
	public void onKick(PlayerKickEvent event) {
		Statistics.getKickCounter(event.getPlayer().getUniqueId()); //TODO man siehe stats.Statistics.java
	}

	CommandSender[] activeConsole = new CommandSender[2];
	ForwardLogHandler myHandler = new ForwardLogHandler(){
		@Override
		public void publish(LogRecord record) {
			for(CommandSender c : activeConsole){
				c.sendMessage("["+record.getLevel().getName()+"] "+record.getMessage());
			}
			super.publish(record);
		}
	};

	@EventHandler
	public void onRemoteServerCommand(RemoteServerCommandEvent event){
		//CommandSender sender = event.getSender();
		
		switch(event.getCommand().toLowerCase()){
		case "getconsoleoutput":
			activeConsole[0] = Bukkit.getPlayer(Utils.getPlayerUUIDByName("ernikillerxd64"));
			activeConsole[1] = Bukkit.getPlayer(Utils.getPlayerUUIDByName("Lightkeks"));
			//System.setOut(myOutStream);
			plugin.getLogger().getParent().getParent().addHandler(myHandler);
			
			event.setCancelled(true);
			break;
		case "gethandlers":
			plugin.getServer().getLogger().addHandler(myHandler);
			
			event.setCancelled(true);
			break;
		}
		
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		YamlConfiguration playercfg = DataManager.getPlayerCfg(event.getPlayer().getUniqueId());
		if (playercfg.isBoolean("muted") && playercfg.getBoolean("muted") && !event.getMessage().startsWith("/")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("[Muter] Du bist gemuted!");
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

	/*
	 * public HandlerList getHandlers() { return getHandlers(); }
	 */
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		
		File file = new File("plugins/SetSpawn/config.yml");
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
}
