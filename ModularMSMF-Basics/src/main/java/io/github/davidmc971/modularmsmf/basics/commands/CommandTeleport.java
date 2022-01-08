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
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;

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
			ChatUtils.sendMsgNoPerm(sender);
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
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"basicsmodule.commands.arguments.toomany");
				break;
		}
		return true;
	}

	private boolean teleportTwoArgsSub(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		UUID target1, target2;
		if (args[0].equalsIgnoreCase(args[1])) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"basicsmodule.commands.teleport.others.toself");
			return true;
		}
		target1 = Utils.getPlayerUUIDByName(args[0]);
		target2 = Utils.getPlayerUUIDByName(args[1]);
		Player p1 = Bukkit.getPlayer(target1), p2 = Bukkit.getPlayer(target2);
		if (p1 == null) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"basicsmodule.commands.teleport.notonline", "_player", args[0]);
			return true;
		}
		if (p2 == null) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"basicsmodule.commands.teleport.notonline", "_player", args[1]);
			return true;
		}
		p1.teleport(p2);
		if (p1 == sender) {
			Utils.sendMessageWithConfiguredLanguage(p2, ChatFormat.INFO,
					"basicsmodule.commands.teleport.others.toyou", "_player", p1.getName());
			Utils.sendMessageWithConfiguredLanguage(p1, ChatFormat.SUCCESS,
					"basicsmodule.commands.teleport.success", "_player", p2.getName());
			return true;
		}
		if (p2 == sender) {
			Utils.sendMessageWithConfiguredLanguage(p1, ChatFormat.SUCCESS,
					"basicsmodule.commands.teleport.force.toplayer", "_player", p2.getName());
			Utils.sendMessageWithConfiguredLanguage(p2, ChatFormat.INFO,
					"basicsmodule.commands.teleport.force.toyou", "_player", p1.getName());
			return true;
		}
		return true;
	}

	private boolean teleportOneArgsSub(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		UUID target;
		target = Utils.getPlayerUUIDByName(args[0]);
		Player p = Bukkit.getPlayer(target);
		if (target == null) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"basicsmodule.commands.teleport.notonline", "_player", args[0]);
			return true;
		}
		if (p == sender) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"basicsmodule.commands.teleport.others.toself");
			return true;
		}
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
				"basicsmodule.commands.teleport.success", "_player", p.getName());
		Utils.sendMessageWithConfiguredLanguage(p, ChatFormat.INFO,
				"basicsmodule.commands.teleport.others.toyou", "_player", sender.getName());
		((Player) sender).teleport(p);
		return true;
	}

	private boolean helpSub(CommandSender sender, Command command, String label, String[] args) {
		//Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
		sender.sendMessage("send help here"); // TODO[epic=help code] teleport help missing (help as
												// file(json/toml/yml)? maybe better organization without the
												// possibility to edit)
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
