package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks
 * Will be looking for some features in future like addition of showing IP adress or some other stuff like TP
 *
 */

public class CommandServerInfo extends AbstractCommand {

	public CommandServerInfo(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (PermissionManager.checkPermission(sender, "serverinfo")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "general.bukkitversion", "_bukkitver", Bukkit.getBukkitVersion());
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "serverinfo" };
	}
}
