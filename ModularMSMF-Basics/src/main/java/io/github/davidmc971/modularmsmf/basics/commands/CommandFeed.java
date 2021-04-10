package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
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
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
			break;
		}
		return true;
	}

	private boolean feedOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = null;
		target = Utils.getPlayerUUIDByName(args[0]);
		Player feeded = Bukkit.getPlayer(target);
		if ((feeded != null) && feeded.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
			return true;
		}
		if ((feeded != null) && feeded.getGameMode() == GameMode.CREATIVE) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
			return true;
		}
		if (!PermissionManager.checkPermission(sender, "feedothers")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			return true;
		}
		if (target == null) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
			return true;
		}
		if (sender == feeded) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "commands.feed.feeded");
			((HumanEntity) sender).setFoodLevel(20);
			return true;
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "commands.feed.feededperson",
					"_player", feeded.getName());
			Utils.sendMessageWithConfiguredLanguage(plugin, feeded, ChatFormat.FEED, "commands.feed.othersfeeded",
					"_sender", sender.getName());
			feeded.setFoodLevel(20);
		}
		return true;
	}

	private boolean feedSelf(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			return true;
		}
		if (((HumanEntity) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
			return true;
		}
		if(((HumanEntity)sender).getGameMode() == GameMode.CREATIVE){
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
			return true;
		}
		if (PermissionManager.checkPermission(sender, "feedself")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "commands.feed.feeded");
			((HumanEntity) sender).setFoodLevel(20);
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
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