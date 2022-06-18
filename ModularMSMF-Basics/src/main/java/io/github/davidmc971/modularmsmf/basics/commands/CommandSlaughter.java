package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * @authors David Alexander Pfeiffer (davidmc971), Lightkeks
 */

public class CommandSlaughter implements IModularMSMFCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "slaughter") || !CommandUtil.isSenderEligible(sender, command)) {
			ChatUtil.sendMsgNoPerm(sender);
			return true;
		}
		if (args.length == 0) {
			Location playerloc = ((Player) sender).getLocation();
			for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
				if (!(e instanceof Player) && (e instanceof Monster)) {
					e.remove();
					Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
							"commands.slaughter.success");
				}
			}
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("passive")) {
			Location playerloc = ((Player) sender).getLocation();
			for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
				if (!(e instanceof Player) && (e instanceof Animals)) {
					e.remove();
					Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
							"commands.slaughter.success");
				}
			}
			return true;
		}
		if (args.length <= 2) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.arguments.toomany");
			return true;
		}
		return true;
	}

	@Override
	public String Label() {
		return "slaughter";
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
