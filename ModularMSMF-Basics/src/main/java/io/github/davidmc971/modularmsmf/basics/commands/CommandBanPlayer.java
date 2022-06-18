package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;
import net.kyori.adventure.text.Component;

/**
 * @author Lightkeks
 */

public class CommandBanPlayer implements IModularMSMFCommand {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!PermissionManager.checkPermission(sender, "ban_player")) {
			ChatUtil.sendMsgNoPerm(sender);
			return true;
		}
		FileConfiguration language = Util.configureCommandLanguage(sender);
		String reason = language.getString("reasons.banned_noreason");
		switch (args.length) {
			case 0:
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "arguments.missing_name");
				break;
			case 1:
				return banPlayer(sender, reason, language, args);
			default:
				return banPlayer(sender, reason, language, args);
		}
		return true;
	}

	public boolean banPlayer(CommandSender sender, String reason, FileConfiguration language, String[] args) {
		UUID uuid = getPlayerUUIDByNameForBan(args[0]);
		if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "commands.ban.already");
			return true;
		}
		for (int i = 1; i < args.length; i++) {
			reason += args[i] + " ";
		}
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				cfg.set("banned", true);
				cfg.set("reason", reason);
				if (!reason.contains("")) {
					Util.broadcastWithConfiguredLanguageEach(ChatFormat.BAN,
							"events.banned_reason", "_player",
							player.getName(), "_reason", reason);
					player.kick(
							Component.text(language.getString("events.banned_reason").replaceAll("_reason", reason)));
					return true;
				}
				Util.broadcastWithConfiguredLanguageEach(ChatFormat.BAN, "events.banned_noreason",
						"_player", player.getName());
				return true;
			}
			Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
					"player.offline", "_player",
					player.getName());
		}
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
			cfg.set("banned", true);
			cfg.set("reason", reason);
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				Util.broadcastWithConfiguredLanguageEach(ChatFormat.BAN,
						"events.banned_reason", "_player",
						player.getName(), "_reason", reason);
				return true;
			}
		}
		return true;
	}

	private UUID getPlayerUUIDByNameForBan(String name) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		return null;
	}

	@Override
	public String Label() {
		return "ban";
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