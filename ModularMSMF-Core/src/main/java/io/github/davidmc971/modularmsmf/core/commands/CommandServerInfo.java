package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks Will be looking for some features in future like addition
 *         of showing IP adress or some other stuff like TP
 *
 */

public class CommandServerInfo implements IModularMSMFCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "serverinfo")) {
			ChatUtils.sendMsgNoPerm(sender);
		}
		if (args.length > 0) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "argument.toomany");
			return true;
		}
		// Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "bukkitversion",
		// 		"_bukkitver", Bukkit.getBukkitVersion().split("[-\\:]")[0]);
		try {
			Runtime r = Runtime.getRuntime();
			float usedMemory = (r.totalMemory() - r.freeMemory()) / 1048576F;
			int usedMemoryPercentage = (int) ((100 * usedMemory) / (r.maxMemory() / 1048576F));
			sender.sendMessage("Current RAM Usage: " + (int) usedMemory + "MB | " + usedMemoryPercentage + "%");
		} catch (IllegalArgumentException e) {
			sender.sendMessage("Error ocurred");
		}
		try {
			Runtime r = Runtime.getRuntime();
			sender.sendMessage("Actual usage of available CPU's: " + r.availableProcessors());
		} catch (IllegalArgumentException e) {
			sender.sendMessage("Error ocurred");
		}
		
		return true;
	}

	@Override
	public String Label() {
		return "serverinfo";
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
