package io.github.davidmc971.modularmsmf.basics.commands;

//import java.net.InetAddress;

/**
 * 
 * @authors Lightkeks, davidmc971
 * 		Bannt Spieler. (fast) alles fertig. :D
 * 		TODO[epic=code needed,seq=14] Unterstützung für ip-banning fehlt
 */

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
//import io.github.davidmc971.modularmsmf.core.data.PlayerData;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;

/**
 * This command handles requests for player 'ban's, 'unban's and 'ban-ip's. It
 * uses util. Banutils for interfacing with DataManager.
 * 
 * @IDEAS:
 * @Lightkeks Worldwide support for warning admins that any user who got banned
 *            <times> from <count servers> and which servers by checking? need a
 *            database for checking So an admin can check over this guy what's
 *            he doing?
 */

public class CommandBan implements IModularMSMFCommand {
	private ModularMSMFCore plugin;

	public CommandBan() {
		plugin = ModularMSMFCore.Instance();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			switch (commandLabel.toLowerCase()) { // if arg is equal to one of them, returns command
			case "ban":
				return banCommand(sender, cmd, commandLabel, args);
			case "unban":
				return unbanCommand(sender, cmd, commandLabel, args);
			case "ban-ip":
				return banIpCommand(sender, cmd, commandLabel, args); // TODO: still not on working list
			}
		return true;
	}

	private boolean banIpCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// TODO: code stuff in here for banip
		//FIXME: subcommand does not work 
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		if (PermissionManager.checkPermission(sender, "banip")) {
			switch (args.length) {
			case 0: // if no argument or playername has given, likely as "help"
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "basicsmodule.commands.banip.help.banip");
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "basicsmodule.commands.banip.help.name");
				break;
			case 1: // if given playername
				String reason = language.getString("event.banned");
				UUID uuid = getPlayerUUIDByNameForBan(args[0]);
				Player player = Bukkit.getPlayer(uuid);
				if (uuid == null) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"basicsmodule.commands.banip.missingname");
				} else {
					if (plugin.getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"basicsmodule.commands.ban.alreadybanned");
						return true;
					} else {
						banPlayer(uuid, reason, language);
						banPlayerIp(uuid, reason, language, player);
					}
				}
				break;
			default: // if too many arguments are given
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.arguments.toomany");
				break;
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.BAN, "coremodule.player.nopermissioin");
		}
		return true;
	}

	public boolean banCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		// checks if sender has permission (console has permission)
		if (PermissionManager.checkPermission(sender, "banplayer")) {
			// if args is 0, so no argument equivalent for choosing someone to ban
			if (args.length == 0) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.name");
				return true;
			}

			String reason = language.getString("event.banned");
			UUID uuid = getPlayerUUIDByNameForBan(args[0]);
			// checks if args is not equivalent to username
			if (uuid == null) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
				return true;
			} else
			// returns true if name is same as args and config is already banned
			if (plugin.getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.ban.alreadybanned");
				return true;
			}

			// checks if args length is equivalent to cases
			switch (args.length) {
			case 1:
				// bans a player with given strings, default ban reason
				banPlayer(uuid, reason, language);
				break;
			// if args are longer than 1, custom reason can be given to add to reason
			default:
				reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				// bans player with given reason
				banPlayer(uuid, reason, language);
				break;
			}
			// gives back false if user has no permission
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
		}

		return true;
	}

	// bans an ip from a player
	public void banPlayerIp(UUID uuid, String reason, FileConfiguration language, Player player) {
		// TODO[epic=code needed,seq=19] need work on it
		// Player ipAdress = ipAdress.getPlayer().getAddress();
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		cfg.set("banned", true);
		cfg.set("reason", reason);
		cfg.set("ipAdress", player.getAddress().getAddress().getHostAddress()); //TODO[epic=code needed,seq=16] still missing configuration to save ipadress
	}

	// bans playername / uuid
	public void banPlayer(UUID uuid, String reason, FileConfiguration language) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		cfg.set("banned", true);
		cfg.set("reason", reason);
		// gives list of all players who are online
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			// gets playername by args, if true
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				// kickbans the player if all stated true
				player.kick(Component.text(language.getString("coremodule.events.banned").replaceAll("_reason", reason)));
			}
		}
	}

	// gets uuid from player by name
	private UUID getPlayerUUIDByNameForBan(String name) {
		// gives list of all players who are online
		for (Player p : Bukkit.getOnlinePlayers()) {
			// gets playername by args, if true
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		// gives list of all players who are offline
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			// gets playername by args, if true
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		return null;
	}

	/*
	 * Unbanning command below this comment
	 */
	// unbans a player
	public boolean unbanCommand(CommandSender sender, Command command, String label, String[] args) {
		// checks if player has permission
		if (PermissionManager.checkPermission(sender, "unban")) {
			// check length of args
			switch (args.length) {
			// #region unban no args
			case 0:
				// returns with no playername
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.name");
				break;
			// #endregion no playername

			// #region unabn playername
			case 1:
				for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
					// checks offline players cause banned
					if (p.getName().equalsIgnoreCase(args[0])) {
						// checks if args equals playername in configs
						FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(p.getUniqueId());
						// checks if string is existent
						if (cfg.getBoolean("banned")) {
							// returns string to false and empty if found
							cfg.set("banned", false);
							cfg.set("reason", "none");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN,
									"basicsmodule.commands.unban.player", "_player", p.getName());
							// if not found to be banned
						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN,
									"basicsmodule.commands.unban.notfound");
						}
						return true;
					}
				}
				// if no player has been found which could have been banned
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
				break;
			// #endregion unban playername

			// #region unban playername uuid
			case 2: // same as case 1:
				if (args[0].toLowerCase().equals("uuid")) {
					for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
						if (p.getUniqueId().toString().equals(args[1])) {
							FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(p.getUniqueId());
							if (cfg.getBoolean("banned")) {
								cfg.set("banned", false);
								cfg.set("reason", "none");
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN,
										"basicsmodule.commands.unban.uuid", "_player", p.getName());
							} else {
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN,
										"basicsmodule.commands.unban.notfound");
							}
							return true;
						}
					}
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"basicsmodule.commands.unban.playernotfounduuid");
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"basicsmodule.commands.arguments.invalid");
				}
				break;
			// #endregion unban playername uuid

			// #region unban by too many arguments
			default:
				// if too many arguments
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.arguments.toomany");
				break;
			// #endregion unban by too many arguments

			}
			return true;
		}
		// gives out if no permissions are given
		Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN, "coremodule.player.nopermission");
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