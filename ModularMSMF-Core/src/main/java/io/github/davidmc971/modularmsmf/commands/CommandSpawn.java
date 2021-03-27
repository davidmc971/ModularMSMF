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

import io.github.davidmc971.modularmsmf.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandSpawn implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandSpawn() {
		plugin = ModularMSMFCore.Instance();
	}

	/**
	 * @TODO: Complete rewrite
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// Have to figure out, if no config is set, error line should pop up
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		} else {
			Player p = (Player) sender;

			if (!(PermissionManager.checkPermission(sender, "spawn"))) {
				Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "general.nopermission");
			} else {
				File file = new File("plugins/ModularMSMF/settings.yml");
				if (!file.exists()) {
					Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.nospawnset");
				} else {
					YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
					if (cfg.get("worldspawn.world").toString().equals("")) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"commands.spawn.nospawnset");
					} else {
						// YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
						double x = cfg.getDouble("worldspawn.coordinates.X");
						double y = cfg.getDouble("worldspawn.coordinates.Y");
						double z = cfg.getDouble("worldspawn.coordinates.Z");
						double yaw = cfg.getDouble("worldspawn.coordinates.Yaw");
						double pitch = cfg.getDouble("worldspawn.coordinates.Pitch");
						String worldname = cfg.getString("worldspawn.world");
						World welt = Bukkit.getWorld(worldname);
						Location loc = p.getLocation();

						loc.setX(x);
						loc.setY(y);
						loc.setZ(z);
						loc.setYaw((float) yaw);
						loc.setPitch((float) pitch);
						loc.setWorld(welt);

						p.teleport(loc);
						Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.spawned");
					}
				}
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[] { "spawn" };
	}
}
