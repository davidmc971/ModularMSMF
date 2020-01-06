package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandHeal extends AbstractCommand {

	public CommandHeal(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		UUID target = null;
		switch (args.length) {
		case 0:
			if((sender instanceof Player)) {
				//checking if command sender is player instead of console
				if (sender.hasPermission(PermissionManager.getPermission("healself"))) {
					//checking, if user has permission to use /heal
					((Player) sender).setHealth(20); //full heal for sender
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.heal.healself");
				} else {
					//if no permission was given, it will negate the if phrase
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			} else {
				//if console should not be permitted to use a command, this comes out
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			}
			break;
		case 1: // missing args length
			target = Utils.getPlayerUUIDByName(args[0]);
			//trying to find out the UUID by player name
			if (sender.hasPermission(PermissionManager.getPermission("healother"))) {
				//check if user has permission to heal others
				if (target == null) {
					//check if user exists
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
					return true; //return's true if target's not online
				} else //return's true otherwise if target's online
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
							//questioning it, if sender is the target
							if(sender == p) {
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.heal.healself");
								((Player)sender).setHealth(20);
							} else { //if sender is not target, then the target will be healed then
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.heal.healother", "_player", p.getDisplayName());
								Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "commands.heal.gothealed", "_sender", sender.getName());
								p.setHealth(20);
							}
						}
					}
				break;
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
		default:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
			break;
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() { //only using for AbstractCommand
		return new String[]{ "heal" };
	}
}