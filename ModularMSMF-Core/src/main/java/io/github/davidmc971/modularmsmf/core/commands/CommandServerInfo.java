package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks Will be looking for some features in future like addition
 *         of showing IP adress or some other stuff like TP
 *
 */

public class CommandServerInfo implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandServerInfo() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (PermissionManager.checkPermission(sender, "serverinfo")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "general.bukkitversion",
					"_bukkitver", Bukkit.getBukkitVersion());
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
