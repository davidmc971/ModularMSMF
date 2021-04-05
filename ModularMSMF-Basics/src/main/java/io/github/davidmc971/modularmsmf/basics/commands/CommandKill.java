package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.basics.ModularMSMFBasics;
import io.github.davidmc971.modularmsmf.basics.listeners.DeathListener;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandKill implements IModularMSMFCommand {

	private ModularMSMFCore MMSMFCore;
	private DeathListener deathListener;

	public CommandKill() {
		MMSMFCore = ModularMSMFCore.Instance();
		deathListener = ModularMSMFBasics.Instance().getDeathListener();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// checks if command sender is player instead of console
		if (sender instanceof Player) {
			// checks permission of user
			if (PermissionManager.checkPermission(sender, "kill")) {
				if (args.length == 0) {
					Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.ERROR,
							"general.missingarguments");
					return true;
				}

				switch (args[0].toLowerCase()) {
				case "me":
					// suicide
					if (PermissionManager.checkPermission(sender, "kill_me")) {
						Player player = ((Player) sender);
						deathListener.registerKilledPlayer(player, KillType.SUICIDE);
						Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatFormat.DEATH, "event.suicide", "_var",
								player.displayName().toString());
						player.setHealth(0);
					} else {
						Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.NOPERM,
								"general.nopermission");
					}
					break;

				case "all":
					// kills all players online
					if (PermissionManager.checkPermission(sender, "kill_all")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							deathListener.registerKilledPlayer(player, KillType.HOMOCIDE);
							player.setHealth(0);
						}
						Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatUtils.ChatFormat.DEATH,
								"event.homocide");
					} else {
						Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.NOPERM,
								"general.nopermission");
					}
					break;

				default:
					// kills specified player
					boolean temp = false;
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
							deathListener.registerKilledPlayer(player, KillType.KILL);
							Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatFormat.DEATH,
									"event.killed_player", "_var", player.displayName().toString());
							player.setHealth(0);
							temp = true;
							break;
						}
					}
					if (!temp) {
						Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.ERROR,
								"general.playernotfound");
					}
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.CONSOLE, "general.nopermission");
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
	}

	@Override
	public String Label() {
		return "kill";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}