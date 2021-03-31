package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;

/**
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 *
 */

/*
 * This command is supposed to be a shell, enabling the console to emulate being
 * a player for testing purposes.
 */

public class CommandPlayershell implements IModularMSMFCommand, Listener {

	private Player activeShellPlayer = null;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			sender.sendMessage("Only for console.");
			return true;
		}

		/*
		 * /playershell start <playername> /playershell exit
		 */

		return true;
	}

	@EventHandler
	public void onServerCommandEvent(ServerCommandEvent event) {
		if (activeShellPlayer != null) {

		}
	}

	@Override
	public String Label() {
		return "playershell";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "plrsh", "plsh" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
