package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

/**
 * @author Lightkeks
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandHeal extends AbstractCommand {

	public CommandHeal(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		UUID target = null;
		switch (args.length) {
		case 0:
			if((sender instanceof Player)) {
				//checking if command sender is player instead of console
				if (sender.hasPermission(PermissionManager.getPermission("healself"))) {
					//checking, if user has permission to use /heal
					((Player) sender).setHealth(20); //full heal for sender
					sender.sendMessage(successfulPrefix+language.getString("commands.heal.healself"));
					//using ChatUtils and YamlConfiguration for easier messages
				} else {
					//if no permission was given, it will negate the if phrase
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
			} else {
				//if console should not be permitted to use a command, this comes out
				sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
			}
			break;
		case 1: // missing args length
			target = Utils.getPlayerUUIDByName(args[0]);
			//trying to find out the UUID by player name
			if (sender.hasPermission(PermissionManager.getPermission("healother"))) {
				//check if user has permission to heal others
				if (target == null) {
					//check if user exists
					sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
					return true; //return's true if target's not online
				} else //return's true otherwise if target's online
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
							//questioning it, if sender is the target
							if(sender == p) {
								sender.sendMessage(successfulPrefix+language.getString("commands.heal.healself"));
								((Player)sender).setHealth(20);
							} else { //if sender is not target, then the target will be healed then
								sender.sendMessage(successfulPrefix+language.getString("commands.heal.healother").replaceAll("_player", p.getName()));
								p.sendMessage(successfulPrefix+language.getString("commands.heal.gothealed").replaceAll("_sender", sender.getName()));
								p.setHealth(20);
							}
						}
					}
				break;
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			}
		default:
			sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			break;
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() { //only using for AbstractCommand
		return new String[]{ "heal" };
	}
}