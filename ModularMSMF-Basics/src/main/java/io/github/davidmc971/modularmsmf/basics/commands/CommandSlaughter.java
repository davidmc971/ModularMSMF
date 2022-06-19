package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

public class CommandSlaughter implements IModularMSMFCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "slaughter")) {
			ChatUtil.sendMsgNoPerm(sender);
			return true;
		}
		if (!CommandUtil.isSenderEligible(sender, command)) {
			return false;
		}
		int count = ((Player) sender).getWorld().getEntities().size() - 1;
		String sCount = Integer.toString(count);
		Location playerloc = ((Player) sender).getLocation();
		if (args.length == 0) {
			for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
				if (!(e instanceof Player)) {
					e.remove();
				}
			}
			if (count == 1) {
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "commands.slaughter.single_succ",
						"_count", sCount);
			} else {
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
						"commands.slaughter.success", "_count", sCount);
				return true;
			}
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("passive")) {
			for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
				if (!(e instanceof Player) && (e instanceof Animals)) {
					e.remove();
				}
			}
			if (count == 1) {
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "commands.slaughter.single_pass",
						"_count", sCount);
			} else {
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
						"commands.slaughter.passive", "_count", sCount);
				return true;
			}
			return true;
		}
		if (args.length <= 2) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"arguments.toomany");
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
