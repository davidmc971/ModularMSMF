package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 *
 */

public class CommandFeed extends AbstractCommand {

	public CommandFeed(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "feed" };
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		UUID target = null;

		switch (args.length) {
		case 0:
			if((sender instanceof Player)) {
				if (sender.hasPermission(PermissionManager.getPermission("feedself"))) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.feed.feeded");
					((Player) sender).setSaturation(20);
					return true;
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				} 
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			}
			break;
		case 1: 
			target = Utils.getPlayerUUIDByName(args[0]);
			if (target == null) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
				return true;
			}
			if (!sender.hasPermission(PermissionManager.getPermission("feedothers"))) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				return true;
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
					if(sender == p) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.feed.feeded");
						((Player) sender).setSaturation(20);
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.feed.feededperson", "_player", p.getDisplayName());
						Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "commands.feed.othersfeeded", "_sender", sender.getName());
						p.setSaturation(20);
					}
					return true;
				}
			}
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
			break;
		default:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
			break;
		}
		return true;
	}
}