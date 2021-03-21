package io.github.davidmc971.modularmsmf.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
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
 * @authors davidmc971
 * 
 */

public class CommandSetSpawn implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

    public CommandSetSpawn() {
        plugin = ModularMSMFCore.Instance();
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (PermissionManager.checkPermission(sender, "setspawn")) {
				File file = new File("plugins/ModularMSMF/settings.yml");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				Player p = (Player) sender;
				Location loc = p.getLocation();
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				double yaw = loc.getYaw();
				double pitch = loc.getPitch();
				String worldname = loc.getWorld().getName();
				if (sender instanceof ConsoleCommandSender) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
				} else {
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					cfg.set("X", x);
					cfg.set("Y", y);
					cfg.set("Z", z);
					cfg.set("Yaw", yaw);
					cfg.set("Pitch", pitch);
					cfg.set("Worldname", worldname);
					try {
						cfg.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.spawnset");
					return true;
				}
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[] { "setspawn" };
	}

}
