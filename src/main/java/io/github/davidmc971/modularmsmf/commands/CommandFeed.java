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
 *	@author Lightkeks
 *	Fully working command, as it should be.
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
		//target is always null unless target is online
		UUID target = null;
		//checks if sender is player instead of console
		if(sender instanceof Player){
			switch (args.length) {
				case 0:
				//feed yourself
				if(PermissionManager.checkPermission(sender, "feedself")){
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "commands.feed.feeded");
					((Player) sender).setSaturation(20);
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
				break;

				case 1:
				//feed target
				if(PermissionManager.checkPermission(sender, "feedothers")){
					target = Utils.getPlayerUUIDByName(args[0]);
					if (target == null) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
					return true;
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
						if(sender == p) {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "commands.feed.feeded");
							((Player) sender).setSaturation(20);
						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FEED, "commands.feed.feededperson", "_player", p.getDisplayName());
							Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.FEED, "commands.feed.othersfeeded", "_sender", sender.getName());
							p.setSaturation(20);
						}
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotonline");
					}
					return true;
				}
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
				break;

				default: 
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
				break;
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
	}
}