package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 * 
 * @author Lightkeks, davidmc971
 *         TODO rewrite
 *
 */

public class CommandKick implements IModularMSMFCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// getting settings from settings.yml
		FileConfiguration language = Utils.configureCommandLanguage(sender);
		// searching string from settings.yml
		String reason = language.getString("basicsmodule.commands.kick.defaultkickreason");
		// target is always null unless target is online
		UUID target = null;

		switch (args.length) {
			case 0:
				if (!PermissionManager.checkPermission(sender, "kickplayer")) {
					ChatUtils.sendMsgNoPerm(sender);
					return true;
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.KICK,
							"basicsmodule.commands.kick.missingname");
				}
				break;
			default:
				if (PermissionManager.checkPermission(sender, "kickplayer")) {
					target = Utils.getPlayerUUIDByName(args[0]);
					if (target == null) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.KICK, "coremodule.player.notfound");
					} else {
						if (args.length == 0) {
							kickPlayer(target, reason);
							Utils.broadcastWithConfiguredLanguageEach(ChatFormat.KICKED,
									"basicsmodule.commands.kick.seeforall", "_player", args[0]);
						} else {
							// adding custom reason for kick
							reason = "";
							for (int i = 1; i < args.length; i++) {
								reason += args[i] + " ";
							}
							kickPlayer(target, reason);
							Utils.broadcastWithConfiguredLanguageEach(ChatFormat.KICKED,
									"basicsmodule.commands.kick.seeforallreason", "_reason", reason, "_player",
									args[0]);
						}
					}
				} else {
					ChatUtils.sendMsgNoPerm(sender);
				}
		}
		return true;
	}

	private void kickPlayer(UUID target, String reason) {
		ModularMSMFCore.Instance().getMainEvents().registerKickedPlayer(target);
		Bukkit.getPlayer(target).kick(Component.text(reason));
	}

	@Override
	public String Label() {
		return "kick";
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