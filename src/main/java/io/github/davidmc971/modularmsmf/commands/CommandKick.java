package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
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

		UUID target = null;
		String reason = language.getString("commands.kick.defaultkickreason");

		switch(args.length){
		case 0:
			if(sender.hasPermission(PermissionManager.getPermission("kickplayer"))){
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;
		default:
			if(sender.hasPermission(PermissionManager.getPermission("kickplayer"))){
				target = Utils.getPlayerUUIDByName(args[0]);
				if(target == null){
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
				} else {
					if(args.length == 1){
						kickPlayer(target, reason);
						Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.KICK, "commands.kick.seekickedall", "_player", args[0]);
					} else {
						reason = "";
						for (int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						kickPlayer(target, reason);
						Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.KICK, "commands.kick.seekickedallreason", "_reason", reason, "_player", args[0]);
					}
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
		}
		return true;
	}

	private void kickPlayer(UUID target, String reason) {
		plugin.getMainEvents().registerKickedPlayer(target);
		Bukkit.getPlayer(target).kickPlayer(reason);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "kick" };
	}
}