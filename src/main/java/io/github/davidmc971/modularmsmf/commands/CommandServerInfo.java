package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;

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
		
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		
		if (sender.hasPermission("modularmsmf.command.serverinfo")) {
			sender.sendMessage(infoPrefix+"Aktuelle Server-Version: " + Bukkit.getVersion());
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "serverinfo" };
	}
}
