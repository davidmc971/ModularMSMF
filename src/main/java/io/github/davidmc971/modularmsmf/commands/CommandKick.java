package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @authors Lightkeks, davidmc971
 *
 */

public class CommandKick extends AbstractCommand {

	public CommandKick(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);

		UUID target = null;
		String reason = language.getString("commands.kick.defaultkickreason");

		switch(args.length){
		case 0: //missing prefix as text
			if(sender.hasPermission(PermissionManager.getPermission("kickplayer"))){
				sender.sendMessage(errorPrefix+language.getString("general.missing_playername"));
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermissions"));
			}
			break;
		default:
			if(sender.hasPermission(PermissionManager.getPermission("kickplayer"))){
				target = Utils.getPlayerUUIDByName(args[0]);
				if(target == null){
					sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
				} else {
					if(args.length == 1){
						Bukkit.getPlayer(target).kickPlayer(reason);
						Bukkit.broadcastMessage(noPermPrefix+language.getString("commands.kick.seekickedall").replaceAll("_player", args[0]));
					} else {
						reason = "";
						for (int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						Bukkit.getPlayer(target).kickPlayer(reason);
						Bukkit.broadcastMessage(noPermPrefix+language.getString("commands.kick.seekickedallreason").replaceAll("_reason", reason).replaceAll("_player", args[0]));
					}
				}
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermissions"));
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "kick" };
	}
}