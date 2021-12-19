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
 * @author Lightkeks Fully working command, as it should be. TODO[epic=done]
 *         Feed - done
 */

public class CommandFeed implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandFeed() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 0:
			return feedSelf(sender, command, label, args);
		case 1:
			return feedOthers(sender, command, label, args);
		default:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.arguments.toomany");
			break;
		}
		return true;
	}

	private boolean feedOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = null;
		target = Utils.getPlayerUUIDByName(args[0]);
		Player player = Bukkit.getPlayer(target);
		if (!PermissionManager.checkPermission(sender, "feedothers")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (target == null) {
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
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "basicsmodule.commands.feed.feeded");
				((Player) sender).setFoodLevel(20);
				return true;
			}
		} else {
			if (player.getGameMode() == GameMode.CREATIVE) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
						"basicsmodule.creative.others", "_player", player.getName());
				return true;
			}
			if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
						"basicsmodule.peaceful.others", "_player", player.getName());
				return true;
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "basicsmodule.commands.feed.feededperson",
						"_player", player.getName());
				Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.FEED, "basicsmodule.commands.feed.othersfeeded",
						"_sender", sender.getName());
				player.setFoodLevel(20);
			}
		}
		return true;
	}

	private boolean feedSelf(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
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
		}
		if (PermissionManager.checkPermission(sender, "feedself")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "basicsmodule.commands.feed.feeded");
			((Player) sender).setFoodLevel(20);
		} else {
			ChatUtils.sendMsgNoPerm(sender);
		}
		return true;
	}

	@Override
	public String Label() {
		return "feed";
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