package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.KillType;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandKill implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

    public CommandKill() {
        plugin = ModularMSMFCore.Instance();
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {	
		//checks if command sender is player instead of console
		if(sender instanceof Player){
			//checks permission of user
			if(PermissionManager.checkPermission(sender, "kill")){
				if (args.length == 0) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missingarguments");
					return true;
				}

				switch (args[0].toLowerCase()) {
					case "me":
					//suicide
					if (PermissionManager.checkPermission(sender, "kill_me")) {
						Player player = ((Player) sender);
						plugin.getMainEvents().registerKilledPlayer(player, KillType.SUICIDE);
						Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "event.suicide", "_var", player.displayName().toString());
						player.setHealth(0);
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
					break;

					case "all":
					//kills all players online
					if (PermissionManager.checkPermission(sender, "kill_all")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							plugin.getMainEvents().registerKilledPlayer(player, KillType.HOMOCIDE);
							player.setHealth(0);
						}
						Utils.broadcastWithConfiguredLanguageEach(plugin, ChatUtils.ChatFormat.DEATH, "event.homocide");
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
					break;

					default:
					//kills specified player
					boolean temp = false;
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
							plugin.getMainEvents().registerKilledPlayer(player, KillType.KILL);
							Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "event.killed_player", "_var", player.displayName().toString());
							player.setHealth(0);
							temp = true;
							break;
						}
					}
					if (!temp){
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
					}
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.nopermission");
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "kill" };
	}
}