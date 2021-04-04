package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks Fully working command
 */

public class CommandHeal implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandHeal() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 0:
			return healSelf(sender, command, label, args);
		case 1:
			return healOthers(sender, command, label, args);
		default:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
			break;
		}
		return true;
	}

	private boolean healOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = null;
		target = Utils.getPlayerUUIDByName(args[0]);
		if (!PermissionManager.checkPermission(sender, "healother")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			return true;
		}
		if (target == null) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
			return true;
		} else
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
					if (sender == p) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL,
								"commands.heal.healself");
						((Player) sender).setHealth(20);
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL,
								"commands.heal.healother", "_player", p.getName());
						Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.HEAL, "commands.heal.gothealed",
								"_sender", sender.getName());
						p.setHealth(20);
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"general.playernotonline");
				}
			}
		return true;
	}

	private boolean healSelf(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			return true;
		}
		if (PermissionManager.checkPermission(sender, "healself")) {
			((Damageable) sender).setHealth(20);
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL, "commands.heal.healself");
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
		}
		return true;
	}

	@Override
	public String Label() {
		return "heal";
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