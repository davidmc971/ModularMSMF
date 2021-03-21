package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @authors Lightkeks, davidmc971
 *
 */

public class CommandKick implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

    public CommandKick() {
        plugin = ModularMSMFCore.Instance();
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		//getting settings from settings.yml
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		//searching string from settings.yml
		String reason = language.getString("commands.kick.defaultkickreason");
		//target is always null unless target is online
		UUID target = null;
		
		switch(args.length){
		case 0:
			if(PermissionManager.checkPermission(sender, "kickplayer")){
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.KICK, "general.missing_playername");
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.KICK, "general.nopermission");
			}
			break;
		default:
			if(PermissionManager.checkPermission(sender, "kickplayer")){
				target = Utils.getPlayerUUIDByName(args[0]);
				if(target == null){
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.KICK, "general.playernotfound");
				} else {
					if(args.length == 1){
						kickPlayer(target, reason);
						Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.KICKED, "commands.kick.seekickedall", "_player", args[0]);
					} else {
						//adding custom reason for kick
						reason = "";
						for (int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						kickPlayer(target, reason);
						Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.KICKED, "commands.kick.seekickedallreason", "_reason", reason, "_player", args[0]);
					}
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.KICK, "general.nopermission");
			}
		}
		return true;
	}

	private void kickPlayer(UUID target, String reason) {
		plugin.getMainEvents().registerKickedPlayer(target);
		Bukkit.getPlayer(target).kick(Component.text(reason));
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "kick" };
	}
}