package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * @author Lightkeks
 * 
 * will be merged into CommandKill.java
 */

public class CommandKillPlayer implements IModularMSMFCommand/* , Listener, InventoryHolder */ {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "kill")) {
			ChatUtil.sendMsgNoPerm(sender);
			return true;
		}
		if (args.length == 0) {
			return killHelp(sender, command, label, args); // will be changed with a usable GUI
		}

		if (args.length <= 2) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
			return true;
		}
		return true;
	}

	private boolean killHelp(CommandSender sender, Command command, String label, String[] args) {
		// sender.sendMessage("/kill (me/all/<target>)");
		// sender.sendMessage("/kill me - suicide");
		sender.sendMessage("/kill all - all players die");
		sender.sendMessage("/kill <target> - <target> dies");
		return true;
	}

	@Override
	public String Label() {
		return "kill";
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