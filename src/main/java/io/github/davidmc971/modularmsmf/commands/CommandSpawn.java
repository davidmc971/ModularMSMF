package io.github.davidmc971.modularmsmf.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandSpawn extends AbstractCommand {

	public CommandSpawn(ModularMSMF plugin) {
		super(plugin);
	}

	/**
	 * @TODO: Complete rewrite
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		} else {
			Player p = (Player)sender;

			if(!p.hasPermission(PermissionManager.getPermission("spawn"))){
				Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "general.nopermission");
			}

			File file = new File("plugins/ModularMSMF/settings.yml");
			if(!file.exists()){
				Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.nospawnset");
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
			Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.spawned");
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "spawn" };
	}
}
