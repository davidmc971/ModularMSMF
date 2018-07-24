package commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;

public class CommandSpawn extends AbstractCommand {

	public CommandSpawn(ModularMSMF plugin) {
		super(plugin);
	}
	
	/**
	 * @TODO finishing this
	 */
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			System.out.println("general.nospawnconsole");
		}
		Player p = (Player)sender;
		
		if(!p.hasPermission("spawn.spawn")){
			p.sendMessage("Du hast keine Rechte!");
		}
		
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
		
		p.teleport(loc);
		p.sendMessage("Du wurdest gespawnt!");
		
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "spawn" };
	}
}
