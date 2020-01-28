package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandTeleport extends AbstractCommand {
	
	public CommandTeleport(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			if (PermissionManager.checkPermission(sender, "teleport")) {
				if (args.length == 0) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
				}
				if (args.length == 1) {
					String Name = args[0];
					if (Bukkit.getPlayerExact(Name) != null) {
						Player target = (Player) Bukkit.getPlayerExact(Name);
						((Player) sender).teleport(target);
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.teleport.success", "_target", target.getDisplayName());
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
					}
				} else if (args.length >= 2) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "teleport", "tele", "tp" };
	}
}
