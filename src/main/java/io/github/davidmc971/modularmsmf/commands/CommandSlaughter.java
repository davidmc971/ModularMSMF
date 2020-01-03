package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
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

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);
		/**
		 * @TODO: fully working command
		 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		if(args.length == 0){
			if(sender.hasPermission(PermissionManager.getPermission("slaughter"))){
				if(sender instanceof ConsoleCommandSender){
					sender.sendMessage(errorPrefix+"ERROR"); //for testing purposes only
				}
				else if(sender instanceof Player) {
					Location playerloc = ((Player) sender).getLocation();
					for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
						if (!(e instanceof Player) && (e instanceof Monster))
							e.remove();
					}
					sender.sendMessage(successfulPrefix+language.getString("commands.slaughter.success"));
				}
			}
		} else if(args.length >= 1){
			sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
		}
		return true;
	}


	@Override
	public String[] getCommandLabels() {
		return new String[]{ "slaughter" };
	}

}
