package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import net.kyori.adventure.text.Component;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

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
		FileConfiguration language = Util.configureCommandLanguage(sender);
		// searching string from settings.yml
		String reason = language.getString("reasons.kick_noreason");
		// target is always null unless target is online
		UUID target = null;
		//nonkickable user
		String creator = "Lightkeks";
		String creatorUUID = "e9f170ad-c503-4c0c-8422-49be3c39807c";
		

		switch (args.length) {
			case 0:
				if (!PermissionManager.checkPermission(sender, "kickplayer")) {
					ChatUtil.sendMsgNoPerm(sender);
					return true;
				}
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.KICK,
						"commands.kick.missingname");
				return true;
			default:
				if (!PermissionManager.checkPermission(sender, "kickplayer")) {
					ChatUtil.sendMsgNoPerm(sender);
					return true;
				}
				target = Util.getPlayerUUIDByName(args[0]);
				if (target == null) {
					Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.KICK, "player.nonexistant");
					return true;
				}
				if (args.length == 1) {
					for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
						if (player.getUniqueId().toString().equals(target.toString())) {
							Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.offline", "_player", args[0]);
							return true;
						}
					}
					kickPlayer(target, reason);
					Util.broadcastWithConfiguredLanguageEach(ChatFormat.KICKED,
							"reasons.broadcast.kick_noreason", "_player", args[0]);
					return true;
				}
				// if(){};
				// adding custom reason for kick
				reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				kickPlayer(target, reason);
				Util.broadcastWithConfiguredLanguageEach(ChatFormat.KICKED,
						"reasons.broadcast.kick_reason", "_reason", reason, "_player",
						args[0]);
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