package io.github.davidmc971.modularmsmf.commands;

/**
 * 
 * @authors Lightkeks, davidmc971
 * 		Bannt Spieler. (fast) alles fertig. :D
 * 		TODO: Unterstützung für ip-banning fehlt
 */

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import net.kyori.adventure.text.Component;

/**	This command handles requests for player 'ban's, 'unban's and 'ban-ip's.
 * 	It uses util. Banutils for interfacing with DataManager.
 * 
 * @IDEAS:
 * @Lightkeks
 * Worldwide support for warning admins that any user who got banned <times> from <count servers> and which servers by checking?
 * need a database for checking
 * So an admin can check over this guy what's he doing?
 */

public class CommandBan implements IModularMSMFCommand {
	
	@Override
	public String[] getCommandLabels() {
		return new String[]{ "ban", "unban", "ban-ip" };
	}

	private ModularMSMFCore plugin;

    public CommandBan() {
        plugin = ModularMSMFCore.Instance();
    }

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		//if arg is equal to one of them, returns command
		switch (commandLabel.toLowerCase()) {
		case "ban":
			return banCommand(sender, cmd, commandLabel, args);
		case "unban":
			return unbanCommand(sender, cmd, commandLabel, args);
		case "ban-ip":
			return false; //banCommand(sender, cmd, commandLabel, args); //TODO: still not on working list
		}
		return false;
	}
	
	
	public boolean banCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
	FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
	//checks if sender has permission (console has permission)
	if(PermissionManager.checkPermission(sender, "banplayer")){
		//if args is 0, so no argument equivalent for choosing someone to ban
		if (args.length == 0) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.BAN, "general.missing_playername");
			return true;
		}

		String reason = language.getString("event.banned");
		UUID uuid = getPlayerUUIDByNameForBan(args[0]);
		//checks if args is not equivalent to username
		if (uuid == null) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playerunknow");
			return true;
		} else
		//returns true if name is same as args and config is already banned
		if (plugin.getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.BAN, "commands.ban.alreadybanned");
			return true;
		}

		//checks if args length is equivalent to cases
		switch (args.length) {
		case 1:
		//bans a player with given strings, default ban reason
		banPlayer(uuid, reason, language);
		break;
		//if args are longer than 1, custom reason can be given to add to reason
		default:
		reason = "";
		for (int i = 1; i < args.length; i++) {
			reason += args[i] + " ";
		}
		//bans player with given reason
		banPlayer(uuid, reason, language);
		break;
		}

		/* //TODO: code stuff in here for banip, still old permissions
		if(args[0].equalsIgnoreCase("banip")){
			if(sender.hasPermission(PermissionManager.getPermission("commands.ban.banip"))) {

			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.BAN, "general.nopermissioin");
			}
		}*/
		//gives back false if user has no permission
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
		}
		
		return true;
	}
	
	//bans an ip from a player
	public void banPlayerIp(UUID uuid, String name, FileConfiguration language) {
		//TODO: need work on it
	}

	//bans playername / uuid
	public void banPlayer(UUID uuid, String reason, FileConfiguration language) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		cfg.set("banned", true);
		cfg.set("reason", reason);
		//gives list of all players who are online
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			//gets playername by args, if true
			if (player.getUniqueId().toString().equals(uuid.toString())) {
				//kickbans the player if all stated true
				player.kick(Component.text(language.getString("event.banned").replaceAll("_reason", reason)));
			}
		}
	}

	//gets uuid from player by name
	private UUID getPlayerUUIDByNameForBan(String name) {
		//gives list of all players who are online
		for (Player p : Bukkit.getOnlinePlayers()) {
			//gets playername by args, if true
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		//gives list of all players who are offline
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			//gets playername by args, if true
			if (p.getName().equals(name)) {
				return p.getUniqueId();
			}
		}
		return null;
	}

/**
 * Unbanning command below this comment
 */
	//unbans a player
	public boolean unbanCommand(CommandSender sender, Command command, String label, String[] args) {
		//checks if player has permission
		if(PermissionManager.checkPermission(sender, "unban")){
		//check length of args
			switch(args.length){
				//#region unban no args
			case 0:
			//returns with no playername
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
			break;
			//#endregion no playername

			//#region unabn playername
			case 1:
			for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
				//checks offline players cause banned
				if(p.getName().equalsIgnoreCase(args[0])){
					//checks if args equals playername in configs
					FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(p.getUniqueId());
					//checks if string is existent
					if(cfg.getBoolean("banned")){
						//returns string to false and empty if found
						cfg.set("banned", false);
						cfg.set("reason", "none");
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN, "commands.unban.playerunbanned", "_player", p.getName());
					//if not found to be banned
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN, "commands.unban.notbanned");
					}
					return true;
				}
			}
			//if no player has been found which could have been banned
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
			break;
			//#endregion unban playername

			//#region unban playername uuid
			case 2: //same as case 1:
			if(args[0].toLowerCase().equals("uuid")){
				for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
					if(p.getUniqueId().toString().equals(args[1])){
						FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(p.getUniqueId());
						if(cfg.getBoolean("banned")){
							cfg.set("banned", false);
							cfg.set("reason", "none");
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN, "commands.unban.unbanuuid", "_player", p.getName());
						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN, "commands.unban.notbanned");
						}
						return true;
					}
				}
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.unban.playernotfounduuid");
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.unban.invalidcommand");
			}
			break;
			//#endregion unban playername uuid

			//#region unban by too many arguments
			default:
			//if too many arguments
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
			break;
			//#endregion unban by too many arguments

			}
			return true;
		}
		//gives out if no permissions are given
		Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.UNBAN, "general.nopermission");
		return true;
	}
}