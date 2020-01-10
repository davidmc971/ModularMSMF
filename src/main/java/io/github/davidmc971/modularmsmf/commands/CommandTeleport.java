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

	
	/**
	 * TODO: implement and finishing this
	 * TODO: whole rewrite for logical reasons
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		//Player player = null;

		if (!(sender instanceof Player)) {
			//TODO: console should be able to tp players to other players or waypoints
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}

		if (sender.hasPermission(PermissionManager.getPermission("teleport"))) {
			if (args.length == 0) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
			}
			if (args.length == 1) {
				String Name = args[0];
				if (Bukkit.getPlayerExact(Name) != null) {
					Player target = (Player) Bukkit.getPlayerExact(Name);
					((Player) sender).teleport(target);
					//TODO: changing sender text to language strings
					sender.sendMessage("[ModularMSMF] Erfolgreich zu " + target.getDisplayName() + " teleportiert!");
					
				} else {
					//TODO: changing sender text to language strings
					sender.sendMessage("[ModularMSMF] " + args[0] + " ist nicht online!");
				}
			} else if (args.length >= 2) {
				//TODO: changing sender text to language strings
				sender.sendMessage("[ModularMSMF] Zu viele Argumente!");
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "teleport" };
	}
}
