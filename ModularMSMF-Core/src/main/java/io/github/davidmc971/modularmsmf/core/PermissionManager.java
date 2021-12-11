package io.github.davidmc971.modularmsmf.core;

import java.util.HashMap;

import org.bukkit.command.CommandSender;

/**
 *
 * @author David Alexander Pfeiffer (davidmc971)
 *
 */

public class PermissionManager {

	private static final HashMap<String, String> permissions = new HashMap<String, String>() {
		/**
		*
		*/
		private static final long serialVersionUID = 7255133207146959566L;
		{
		//economy permissions
		put("eco_set", "modularmsmf.eco.set");
		put("eco_add", "modularmsmf.eco.add");
		put("eco_take", "modularmsmf.eco.take");
		put("eco_pay", "modularmsmf.eco.pay");
		put("eco_lookup", "modularmsmf.eco.lookup");
		//mmsmf permissions
		put("mmsmfhelp", "modularmsmf.help");
		put("mmsmf", "modularmsmf.mmsmf");
		//clearcommand permissions
		put("clear_command", "modularmsmf.clear");
		put("clear_all", "modularmsmf.clear.all");
		put("clear_target", "modularmsmf.clear.target");
		//serverinfo permissions
		put("serverinfo", "modularmsmf.serverinfo");
		//report permissions
		put("report_player", "modularmsmf.report.player");
		put("report_bug", "modularmsmf.report.bug");
		put("report_other", "modularmsmf.report.other");
		//list permissions
		put("list_players_online", "modularmsmf.list.players.online");
		put("list_players_offline", "modularmsmf.list.players.offline");
		put("list_all_players", "modularmsmf.list.allplayers");
	}};

	public static boolean checkPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
			return permissions.get(name);
	}
}