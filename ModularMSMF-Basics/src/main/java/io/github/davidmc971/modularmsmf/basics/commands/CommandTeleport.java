package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
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

	/**
	 * TODO[epic=code needed,seq=29] rewrite to better code
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "teleport")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			return true;
		}
		switch (args.length) {
		case 0:
			return helpSub(sender, command, label, args);
		case 1:
			return teleportOneArgsSub(sender, command, label, args);
		case 2:
		return teleportTwoArgsSub(sender, command, label, args);
		default:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
			break;
		}
		return true;
	}

	private boolean teleportTwoArgsSub(CommandSender sender, Command command, String label, String[] args) {
		UUID target1, target2;
		if (args[0].equalsIgnoreCase(args[1])) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
					"basics.commands.teleport.error.samename");
			return true;
		}
		target1 = Utils.getPlayerUUIDByName(args[0]);
		target2 = Utils.getPlayerUUIDByName(args[1]);
		Player p1 = Bukkit.getPlayer(target1), p2 = Bukkit.getPlayer(target2);
		if (p1 != null && p2 != null) { //FIXME fix null players if not online or nonexistant
			// Both are online and can be teleported
			p1.teleport(p2);
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
					"basics.commands.teleport.success.toplayer", "_player", p2.getName());
			Utils.sendMessageWithConfiguredLanguage(plugin, p2, ChatFormat.INFO,
					"basics.commands.teleport.success.toyou", "_player", sender.getName());
			return true;
		}
		Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
				"basics.commands.teleport.error.notonline", "_player", p2.getName());
		return true;
	}

	private boolean teleportOneArgsSub(CommandSender sender, Command command, String label, String[] args) {
		UUID target1;
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			return true;
		}
		target1 = Utils.getPlayerUUIDByName(args[0]); //FIXME fix null players if not online or nonexistant
		Player p = Bukkit.getPlayer(target1);
		if (p != sender) {
			((Player) sender).teleport(p);
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.teleport.success.toplayer",
					"_player", p.getName());
			return true;
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basics.commands.teleport.error.samename");
		}
		return true;
	}

	private boolean helpSub(CommandSender sender, Command command, String label, String[] args) {
		Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
		sender.sendMessage("send help here"); // TODO code help here
		return true;
	}

	@Override
	public String Label() {
		return "teleport";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "tp" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
