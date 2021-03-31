package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.formatter.qual.ReturnsFormat;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandTeleport implements IModularMSMFCommand {

	private ModularMSMFCore plugin;
	UUID target = null;

	public CommandTeleport() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration cfg = plugin.getDataManager().settingsyaml;
		if (cfg.get("toggle.commands.teleport").toString().equals("true")) {
			if (sender instanceof Player) {
				if (PermissionManager.checkPermission(sender, "teleport")) {
					switch (args.length) {
					case 0:
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"general.missing_playername");
						break;
					case 1:
						// TODO rewrite to better code

						target = Utils.getPlayerUUIDByName(args[0]);
						String Name = args[0];
						if (Bukkit.getPlayerExact(Name) != null) {
							Player target = (Player) Bukkit.getPlayerExact(Name);
							((Player) sender).teleport(target);
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
									"commands.teleport.success", "_target", target.displayName().toString());
						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"general.playernotfound");
						}
						break;
					case 2:
						UUID target1 = null;
						UUID target2 = null;
						target1 = Utils.getPlayerUUIDByName(args[0]);
						target2 = Utils.getPlayerUUIDByName(args[1]);
						// if both targets are offline
						if ((target1) == null && (target2) == null) {
							sender.sendMessage("nope1.1");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basics.commands.teleport.others.nooneonline", "_target1", args[0], "_target2",
									args[1]);
							return true;
						} else
						// if both targets are offline
						if (!(args[0].equals(target1)) && !(args[1].equals(target2))) {
							sender.sendMessage("nope1.2");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basics.commands.teleport.others.nooneonline", "_target1", args[0], "_target2",
									args[1]);
							return true;
						} else
						// if target1 is online and target2 is offline
						if ((target1 != null) && (target2 == null)) {
							sender.sendMessage("nope2.1");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basics.commands.teleport.others.target1", "_target1", args[0]);
							return true;
						} else
						// if target1 is online and target2 is offline
						if (args[0].equals(target1) && (!(args[1].equals(target2)))) {
							sender.sendMessage("nope2.2");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basics.commands.teleport.others.target1", "_target1", args[0]);
							return true;
						} else
						// if target1 is offline and target2 is online
						if ((target1 == null) && (target2 != null)) {
							sender.sendMessage("nope3.1");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basics.commands.teleport.others.target2", "_target2", args[1]);
							return true;
						} else
						// if target1 is offline and target2 is online
						if (!(args[0].equals(target1) && !(args.equals(target2)))) {
							sender.sendMessage("nope3.2");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basics.commands.teleport.others.target2", "_target2", args[1]);
							return true;
						} else
						// if both targets are online
						if ((target1 != null) && (target2 != null)) {
							// if /teleport <target1> <target2> (by arguments)
							if (args[0].equals(target1)) {
								sender.sendMessage("nope4.1");
								return true;
							} else
							// if /teleport <target1> <target1> (by arguments)
							if (args[0].equals(target1) && args[1].equals(target1)) {
								sender.sendMessage("nope5.1");
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
										"basics.commands.teleport.others.toself");
								return true;
							}
						} else
						// if both targets are online
						if (args[0].equals(target1) && args.equals(target2)) {
							// if /teleport <target1> <target2> (by arguments)
							if (args[0].equals(target1)) {
								sender.sendMessage("nope4.2");
								return true;
							} else
							// if /teleport <target1> <target1> (by arguments)
							if (args[0].equals(target1) && args[1].equals(target1)) {
								sender.sendMessage("nope.2");
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
										"basics.commands.teleport.others.toself");
								return true;
							}
						} else
							sender.sendMessage("nope6");
						/*
						 * Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
						 * "general.toomanyarguments");
						 */
						break;
					case 3:
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"general.toomanyarguments");
						break;
					}

				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basics.nottoggledtrue");
		}
		return true;

	}

	@Override
	public String[] getCommandLabels() {
		return new String[] { "teleport", "tele", "tp" };
	}
}
