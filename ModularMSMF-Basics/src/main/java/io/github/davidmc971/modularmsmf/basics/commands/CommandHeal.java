package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks Fully working command with checking if Peaceful
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
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
					"basicsmodule.commands.arguments.toomany");
			break;
		}
		return true;
	}

	private boolean healOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = null;
		target = Utils.getPlayerUUIDByName(args[0]);
		Player player = Bukkit.getPlayer(target);
		if (!PermissionManager.checkPermission(sender, "healother")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (player == null) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
			return true;
		}
		if (player == sender) {
			if (player.getGameMode() == GameMode.CREATIVE) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
				return true;
			}
			if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
				return true;
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL,
						"basicsmodule.commands.heal.self");
				((Player) sender).setHealth(20);
				return true;
			}
		} else {
			if (player.getGameMode() == GameMode.CREATIVE) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.others", "_player", player.getName());
				return true;
			}
			if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.others", "_player", player.getName());
			return true;
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL,
						"basicsmodule.commands.heal.others.healother", "_player", player.getName());
				Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.HEAL,
						"basicsmodule.commands.heal.others.gothealed", "_sender", sender.getName());
				player.setHealth(20);
			}
		}
		return true;
	}

	private boolean healSelf(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "healself")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
			return true;
		}
		if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
			return true;
		} else {
			((Player) sender).setHealth(20);
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL, "basicsmodule.commands.heal.self");
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