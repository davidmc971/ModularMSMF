package io.github.davidmc971.modularmsmf.commandblocker.listeners;

//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
//import io.github.davidmc971.modularmsmf.commandblocker.PermissionManager;
//import io.github.davidmc971.modularmsmf.commandblocker.configuration.BlockedCommands;
//import io.github.davidmc971.modularmsmf.core.util.Utils;
//import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandListener implements Listener {

	// setting "plugin"
	public ModularMSMFCore plugin = ModularMSMFCore.Instance();

/*	private BlockedCommands blockedCommands;

	public CommandListener(BlockedCommands blockedCommands) {
		this.blockedCommands = blockedCommands;
	}

	// here it will catch any command, which equals any string in file, if set
	@EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent e) {
		String commandName = e.getMessage().substring(1).split(" ")[0].toLowerCase();
		Player p = e.getPlayer();
		if (blockedCommands.commandIsBlocked(commandName)) {
			Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.ERROR, "general.commands_blocked", "_var",
					commandName);
			e.setCancelled(false);
		}
		}*/
}