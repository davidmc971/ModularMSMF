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
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;

/**
 * @author Lightkeks
 */

public class CommandBanPlayer implements IModularMSMFCommand {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!PermissionManager.checkPermission(sender, "banplayer")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (args.length == 0) { // FIXME: add string to lang file to remove null
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.name");
			return true;
		}
		UUID uuid = getPlayerUUIDByNameForBan(args[0]);
		if (uuid == null) { // FIXME: add string to lang file to remove null
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.unknown");
			return true;
		}
		if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
					"basicsmodule.commands.ban.already"); // FIXME: add string to lang file to remove null
			return true;
		}
		Player player = Bukkit.getPlayer(uuid);
		if (!player.getUniqueId().toString().equals(uuid.toString())) { // FIXME: add string to lang file to remove null
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.notonline");
		}
		FileConfiguration language = Utils.configureCommandLanguage(sender);
		String reason = language.getString("coremodule.event.banned"); // FIXME: add string to lang file to remove null
		switch (args.length) {
			case 1:
				banPlayer(sender, uuid, reason, language);
				break;
			default:
				reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				banPlayer(sender, uuid, reason, language);
				break;
		}
		return true;
	}

	public static void banPlayer(CommandSender sender, UUID uuid, String reason, FileConfiguration language) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
			cfg.set("banned", true);
			cfg.set("reason", reason);
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				player.kick( // FIXME: add string to lang file to remove null
						Component.text(language.getString("coremodule.events.banned").replaceAll("_reason", reason)));
			}
		}

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