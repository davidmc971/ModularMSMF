package io.github.davidmc971.modularmsmf.core;

import org.bukkit.command.CommandSender;

/**
 * 
 * @author davidmc971
 *
 */

public class PermissionManager {

	//every permission has one internal desciptor and one string for the official permission
	private static final String[][] permissions = {
		{ "banplayer", "modularmsmf.ban.banplayer" },
		{ "banip", "modularmsmf.ban.banip" },
		{ "kickplayer", "modularmsmf.kick.kickplayer" },
		{ "healself", "modularmsmf.heal.healself" },
		{ "healother", "modularmsmf.heal.healother" },
		{ "feedself", "modularmsmf.feed.feedself" },
		{ "feedothers", "modularmsmf.feed.feedothers" },
		{ "eco_set", "modularmsmf.eco.set" },
		{ "eco_add", "modularmsmf.eco.add" },
		{ "eco_take", "modularmsmf.eco.take" },
		{ "eco_pay", "modularmsmf.eco.pay" },
		{ "eco_lookup", "modularmsmf.eco.lookup" },
		{ "home", "modularmsmf.home" },
		{ "home_list", "modularmsmf.home.list" },
		{ "home_set", "modularmsmf.home.set"},
		{ "home_remove", "modularmsmf.home.remove" },
		{ "home_rtp", "modularmsmf.home.rtp" },
		{ "home_admin", "modularmsmf.home.admin" },
		{ "kill_me", "modularmsmf.kill.me" },
		{ "kill_all", "modularmsmf.kill.all" },
		{ "kill", "modularmsmf.kill" },
		{ "mmsmfhelp", "modularmsmf.help"},
		{ "mmsmf", "modularmsmf.mmsmf"},
		{ "slaughter", "modularmsmf.slaughter"}
	};

	public static boolean checkPermission(CommandSender sender, String permission) {
		//TODO: use Permission objects
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
		for (String[] s : permissions) {
			if (s[0].equals(name)) {
				return s[1];
			}
		}
		return null;
	}
}
