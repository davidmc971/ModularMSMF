package io.github.davidmc971.modularmsmf.commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @authors Lightkeks, davidmc971
 *
 */

public class CommandSpawn implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandSpawn() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String empty = "";

		FileConfiguration cfg = plugin.getDataManager().settingsyaml;

		double x = cfg.getDouble("worldspawn.coordinates.X");
		double y = cfg.getDouble("worldspawn.coordinates.Y");
		double z = cfg.getDouble("worldspawn.coordinates.Z");
		double yaw = cfg.getDouble("worldspawn.coordinates.Yaw");
		double pitch = cfg.getDouble("worldspawn.coordinates.Pitch");
		String worldname = cfg.getString("worldspawn.world");

		switch (args.length) {
		case 0:
			if (sender instanceof ConsoleCommandSender) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			} else {
				Player p = (Player) sender;
				if (!(PermissionManager.checkPermission(sender, "spawn"))) {
					Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "general.nopermission");
				} else {
					if (!cfg.contains("worldspawn")) {
						Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN,
								"commands.spawn.nospawnset");
					} else {
						if (cfg.get("worldspawn.world").toString().equals("")) {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"commands.spawn.nospawnset");
						} else {
							World welt = Bukkit.getWorld(worldname);
							Location loc = p.getLocation();
							loc.setX(x);
							loc.setY(y);
							loc.setZ(z);
							loc.setYaw((float) yaw);
							loc.setPitch((float) pitch);
							loc.setWorld(welt);
							p.teleport(loc);
							Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN,
									"commands.spawn.spawned");
						}
					}
				}
			}
			break;
		case 1:
			if (args[0].equalsIgnoreCase("remove")) {
				cfg.set("worldspawn", null);
				// if (!cfg.get("worldspawn.world").toString().equals("")) {
				// 	cfg.set("worldspawn.coordinates.X", empty);
				// 	cfg.set("worldspawn.coordinates.Y", empty);
				// 	cfg.set("worldspawn.coordinates.Z", empty);
				// 	cfg.set("worldspawn.coordinates.Yaw", empty);
				// 	cfg.set("worldspawn.coordinates.Pitch", empty);
				// 	cfg.set("worldspawn.world", worldname);
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
							"commands.spawn.removed");
				// } else {
					// Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.spawn.nospawnset");
				// }
			} else {
				UUID target = null;
				target = Utils.getPlayerUUIDByName(args[0]);
				if (target == null) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
				} else {
					if (!cfg.contains("worldspawn")) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SPAWN,
								"commands.spawn.nospawnset");
					} else {
						if (cfg.get("worldspawn.world").toString().equals("")) {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"commands.spawn.nospawnset");
						} else {
							for (Player p : Bukkit.getOnlinePlayers()) {
								World welt = Bukkit.getWorld(worldname);
								Location loc = p.getLocation();
								loc.setX(x);
								loc.setY(y);
								loc.setZ(z);
								loc.setYaw((float) yaw);
								loc.setPitch((float) pitch);
								loc.setWorld(welt);
								p.teleport(loc);
								Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN,
										"commands.spawn.spawned");
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
										"commands.spawn.others", "_player", args[0]);
							}
						}
					}
				}
			}
			break;
		default:
			if (sender instanceof ConsoleCommandSender) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			} else {
				if (args.length >= 2) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"general.toomanyarguments");
				}
			}
			break;
		}
		return true;

	}

	@Override
	public String[] getCommandLabels() {
		return new String[] { "spawn" };
	}
}
