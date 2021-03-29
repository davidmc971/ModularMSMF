package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandSlaughter implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

    public CommandSlaughter() {
        plugin = ModularMSMFCore.Instance();
    }
		/**
		 * @TODO: fully working command
		 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(PermissionManager.checkPermission(sender, "slaughter")){
			if(sender instanceof ConsoleCommandSender){
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
			} else {
				if(args.length == 0) {
					Location playerloc = ((Player) sender).getLocation();
					for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
						if (!(e instanceof Player) && (e instanceof Monster))
							e.remove();
					}
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.slaughter.success");
				} else if(args.length == 1 && args[0].equalsIgnoreCase("passive")){
					Location playerloc = ((Player) sender).getLocation();
					for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
						if (!(e instanceof Player) && (e instanceof Animals))
							e.remove();
					}
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.slaughter.success");
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
				}
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
		}
		return true;
	}


	@Override
	public String[] getCommandLabels() {
		return new String[]{ "slaughter" };
	}

}
