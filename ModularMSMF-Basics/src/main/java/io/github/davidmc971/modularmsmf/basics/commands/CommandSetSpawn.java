package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 * @authors Lightkeks, davidmc971
 */

public class CommandSetSpawn implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandSetSpawn() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration cfg = plugin.getDataManager().settingsyaml;
		if (!PermissionManager.checkPermission(sender, "setspawn")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			return true;
		}
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			return true;
		}
		if (args.length == 0) {
			Player p = (Player) sender;
			Location loc = p.getLocation();
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			double yaw = loc.getYaw();
			double pitch = loc.getPitch();
			String worldname = loc.getWorld().getName();
			cfg.set("worldspawn.coordinates.X", x);
			cfg.set("worldspawn.coordinates.Y", y);
			cfg.set("worldspawn.coordinates.Z", z);
			cfg.set("worldspawn.coordinates.Yaw", yaw);
			cfg.set("worldspawn.coordinates.Pitch", pitch);
			cfg.set("worldspawn.world", worldname);
			cfg.set("worldspawn.isTrue", "true");
			Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.spawnset");
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
		}
		return true;
	}

	@Override
	public String Label() {
		return "setspawn";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
