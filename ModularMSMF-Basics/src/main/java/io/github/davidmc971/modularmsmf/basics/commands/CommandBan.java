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
 * This command handles requests for player 'ban's, 'unban's and 'ban-ip's. It
 * uses util. Banutils for interfacing with DataManager.
 * 
 * @Lightkeks Worldwide support for warning admins that any user who got banned
 *            <times> from <count servers> and which servers by checking? need a
 *            database for checking So an admin can check over this guy what's
 *            he doing?
 */

public class CommandBan implements IModularMSMFCommand {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		switch (commandLabel.toLowerCase()) {
			case "ban":
				return banCommand(sender, cmd, commandLabel, args);
			case "unban": // FIXME: does not work - new command?
				return unbanCommand(sender, cmd, commandLabel, args);
			case "ban-ip":
				return banIpCommand(sender, cmd, commandLabel, args);
		}
		return true;
	}

	private boolean banIpCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!PermissionManager.checkPermission(sender, "banip")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		switch (args.length) {
			case 0:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
						"basicsmodule.commands.banip.help.banip");
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
						"basicsmodule.commands.banip.help.name");
				break;
			case 1:
				UUID uuid = getPlayerUUIDByNameForBan(args[0]);
				if (uuid == null) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
							"basicsmodule.commands.banip.missingname");
				} else {
					if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
								"basicsmodule.commands.ban.alreadybanned");
						return true;
					} else {
						FileConfiguration language = Utils.configureCommandLanguage(sender);
						String reason = language.getString("event.banned");
						banPlayer(sender, uuid, reason, language);
						Player player = Bukkit.getPlayer(uuid);
						banPlayerIp(uuid, reason, language, player);
					}
				}
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"basicsmodule.commands.arguments.toomany");
				break;
		}
		return true;
	}

	public boolean banCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!PermissionManager.checkPermission(sender, "banplayer")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (args.length == 0) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.name");
			return true;
		}
		UUID uuid = getPlayerUUIDByNameForBan(args[0]);
		if (uuid == null) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.unknown");
			return true;
		}
		if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
					"basicsmodule.commands.ban.already");
			return true;
		}
		Player player = Bukkit.getPlayer(uuid);
		if (!player.getUniqueId().toString().equals(uuid.toString())) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.notonline");
		}
		FileConfiguration language = Utils.configureCommandLanguage(sender);
		String reason = language.getString("coremodule.event.banned");
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

	public void banPlayerIp(UUID uuid, String reason, FileConfiguration language, Player player) {
		FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
		cfg.set("banned", true);
		cfg.set("reason", reason);
		cfg.set("ipAdress", player.getAddress().getAddress().getHostAddress());
	}

	public void banPlayer(CommandSender sender, UUID uuid, String reason, FileConfiguration language) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
			cfg.set("banned", true);
			cfg.set("reason", reason);
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				player.kick(
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

	public boolean unbanCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "unban")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		switch (args.length) {
			case 0:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.name");
				break;
			case 1: // FIXME: unban does not work - new command?
				for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					if (p.getName().equalsIgnoreCase(args[0])) {
						UUID uuid = null;
						FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
						if (cfg.getBoolean("banned")) {
							cfg.set("banned", false);
							cfg.set("reason", "none");
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
									"basicsmodule.commands.unban.player", "_player", p.getName());
						} else {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
									"basicsmodule.commands.unban.notfound");
						}
						return true;
					}
				}
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
				break;
			case 2:
				if (args[0].toLowerCase().equals("uuid")) {
					for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
						if (p.getUniqueId().toString().equals(args[1])) {
							UUID uuid = Bukkit.getPlayerUniqueId(p.getName());
							FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
							if (cfg.getBoolean("banned")) {
								cfg.set("banned", false);
								cfg.set("reason", "none");
								Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
										"basicsmodule.commands.unban.uuid", "_player", p.getName());
							} else {
								Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
										"basicsmodule.commands.unban.notfound");
							}
							return true;
						}
					}
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
							"basicsmodule.commands.unban.playernotfounduuid");
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
							"basicsmodule.commands.arguments.invalid");
				}
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"basicsmodule.commands.arguments.toomany");
				break;
		}
		return true;
	}

	@Override
	public String Label() {
		return "ban";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "unban", "ban-ip" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}