package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * Feed players
 * #need rewrite
 *
 * @author Lightkeks
 *
 */

public class CommandFeed implements IModularMSMFCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
			case 0:
				if (!PermissionManager.checkPermission(sender, "feed_self")) {
					ChatUtil.sendMsgNoPerm(sender);
					return true;
				}
				if (!CommandUtil.isSenderEligible(sender, command)) {
					return false;
				}
				feedSelf(sender, command, label, args);
				break;
			case 1:
				if (!PermissionManager.checkPermission(sender, "feed_others")) {
					ChatUtil.sendMsgNoPerm(sender);
					return true;
				}
				feedOthers(sender, command, label, args);
				break;
			default:
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"arguments.toomany");
				break;
		}
		return true;
	}

	private boolean feedSelf(CommandSender sender, Command command, String label, String[] args) {
		Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.FEED, "commands.feed.feeded");
		((Player) sender).setFoodLevel(20);
		return true;
	}

	private boolean feedOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = null;
		target = Util.getPlayerUUIDByName(args[0]);
		Player player = Bukkit.getPlayer(target);
		if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
			return false;
		}
		if (sender == player) {
			return feedSelf(sender, command, label, args);
		}
		Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.FEED,
				"commands.feed.feededperson", "_player", player.getName());
		Util.sendMessageWithConfiguredLanguage(player, ChatFormat.FEED,
				"commands.feed.othersfeeded", "_sender", sender.getName());
		player.setFoodLevel(20);
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