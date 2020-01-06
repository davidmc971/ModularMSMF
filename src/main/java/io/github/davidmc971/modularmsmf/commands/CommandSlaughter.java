package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
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

public class CommandSlaughter extends AbstractCommand {

	public CommandSlaughter(ModularMSMF plugin) {
		super(plugin);
	}
		/**
		 * @TODO: fully working command
		 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(args.length == 0){
			if(sender.hasPermission(PermissionManager.getPermission("slaughter"))){
				if(sender instanceof ConsoleCommandSender){
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
				}
				else if(sender instanceof Player) {
					Location playerloc = ((Player) sender).getLocation();
					for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
						if (!(e instanceof Player) && (e instanceof Monster))
							e.remove();
					}
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.slaughter.success");
				}
			}
		} else if(args.length == 1 && args[0].equalsIgnoreCase("passive")){
			if(sender.hasPermission(PermissionManager.getPermission("slaughter"))){
				if(sender instanceof ConsoleCommandSender){
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
				}
				else if(sender instanceof Player) {
					Location playerloc = ((Player) sender).getLocation();
					for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
						if (!(e instanceof Player) && (e instanceof Animals))
							e.remove();
					}
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.slaughter.success");
				}
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
		}
		return true;
	}


	@Override
	public String[] getCommandLabels() {
		return new String[]{ "slaughter" };
	}

}
