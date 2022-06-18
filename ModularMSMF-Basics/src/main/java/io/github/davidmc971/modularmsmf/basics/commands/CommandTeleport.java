package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 *
 * @author davidmc971, Lightkeks
 *
 */

public class CommandTeleport implements IModularMSMFCommand {

	UUID target = null;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "teleport")) {
			ChatUtil.sendMsgNoPerm(sender);
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
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"arguments.toomany");
				break;
		}
		return true;
	}

	private boolean teleportTwoArgsSub(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		UUID target1, target2;
		if (args[0].equalsIgnoreCase(args[1])) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.teleport.others.toself");
			return true;
		}
		target1 = Util.getPlayerUUIDByName(args[0]);
		target2 = Util.getPlayerUUIDByName(args[1]);
		Player p1 = Bukkit.getPlayer(target1), p2 = Bukkit.getPlayer(target2);
		if (p1 == null) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.teleport.notonline", "_player", args[0]);
			return true;
		}
		if (p2 == null) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.teleport.notonline", "_player", args[1]);
			return true;
		}
		p1.teleport(p2);
		if (p1 == sender) {
			Util.sendMessageWithConfiguredLanguage(p2, ChatFormat.INFO,
					"commands.teleport.others.toyou", "_player", p1.getName());
			Util.sendMessageWithConfiguredLanguage(p1, ChatFormat.SUCCESS,
					"commands.teleport.success", "_player", p2.getName());
			return true;
		}
		if (p2 == sender) {
			Util.sendMessageWithConfiguredLanguage(p1, ChatFormat.SUCCESS,
					"commands.teleport.force.toplayer", "_player", p2.getName());
			Util.sendMessageWithConfiguredLanguage(p2, ChatFormat.INFO,
					"commands.teleport.force.toyou", "_player", p1.getName());
			return true;
		}
		return true;
	}

	private boolean teleportOneArgsSub(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			ChatUtil.sendMsgNoPerm(sender);
			return true;
		}
		UUID target;
		target = Util.getPlayerUUIDByName(args[0]);
		Player p = Bukkit.getPlayer(target);
		if (target == null) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"player.offline", "_player", args[0]);
			return true;
		}
		if (p == sender) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.teleport.others.toself");
			return true;
		}
		Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
				"commands.teleport.success", "_player", p.getName());
		Util.sendMessageWithConfiguredLanguage(p, ChatFormat.INFO,
				"commands.teleport.others.toyou", "_player", sender.getName());
		((Player) sender).teleport(p);
		return true;
	}

	private boolean helpSub(CommandSender sender, Command command, String label, String[] args) {
		// Util.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
		// "general.missing_playername");
		sender.sendMessage("send help here"); // create file to edit help in an easier way, adopt language
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
