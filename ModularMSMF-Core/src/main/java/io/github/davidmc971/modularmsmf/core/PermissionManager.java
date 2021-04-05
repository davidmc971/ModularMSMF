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
		//banning permissions
		put("banplayer", "modularmsmf.ban.banplayer");
		put("banip", "modularmsmf.ban.banip");
		put("unban", "modularmsmf.unban");
		//kicking permissions
		put("kickplayer", "modularmsmf.kick.kickplayer");
		//heal permissions
		put("healself", "modularmsmf.heal.healself");
		put("healother", "modularmsmf.heal.healother");
		//feed permissions
		put("feedself", "modularmsmf.feed.feedself");
		put("feedothers", "modularmsmf.feed.feedothers");
		//economy permissions
		put("eco_set", "modularmsmf.eco.set");
		put("eco_add", "modularmsmf.eco.add");
		put("eco_take", "modularmsmf.eco.take");
		put("eco_pay", "modularmsmf.eco.pay");
		put("eco_lookup", "modularmsmf.eco.lookup");
		//home permissions
		put("home", "modularmsmf.home");
		put("home_self", "modular.home.self");
		put("home_help", "modularmsmf.home.help");
		put("home_list", "modularmsmf.home.list");
		put("home_set", "modularmsmf.home.set");
		put("home_remove", "modularmsmf.home.remove");
		put("home_rtp", "modularmsmf.home.rtp");
		put("home_admin", "modularmsmf.home.admin");
		//kill permissions
		put("kill_me", "modularmsmf.kill.me");
		put("kill_all", "modularmsmf.kill.all");
		put("kill", "modularmsmf.kill");
		//mmsmf permissions
		put("mmsmfhelp", "modularmsmf.help");
		put("mmsmf", "modularmsmf.mmsmf");
		//mute permissions
		put("mute", "modularmsmf.mute");
		//slaughter permissions
		put("slaughter", "modularmsmf.slaughter");
		//spawn permissions
		put("spawn", "modularmsmf.spawn");
		put("spawn_others", "modularmsmf.spawn.others");
		put("setspawn", "modularmsmf.setspawn");
		put("spawn_remove", "modularmsmf.spawn.remove");
		//teleport permissions
		put("teleport", "modularmsmf.teleport");
		//back permissions
		put("back", "modularmsmf.back");
		put("back_others", "modularmsmf.back.others");
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
		//fly permissions
		put("fly", "modularmsmf.fly");
		put("fly.others", "modularmsmf.fly.others");
		/*
		Basic module permissions
		*/
		//set permissions
		put("set_use","modularmsmf.set.use");
		put("set_use_life","modularmsmf.set.use.life");
		put("set_use_food","modularmsmf.set.use.food");
		put("set_use_saturation","modularmsmf.set.use.saturation");
		//toggle permissions
		put("toggle_use", "modularmsmf.toggle.use"); //to toggle commands for use
		//channel permissions
		put("channels_use", "modularmsmf.channels.use"); // to use the command
		put("channels_list_all", "modularmsmf.channel.list.all"); //admin related
		put("channels_list_public", "modularmsmf.channels.list.public"); //anyone can see public channels, only if permission is set
		put("channels_list_private", "modularmsmf.channels.list.private"); //mod/admin related
	}};

	public static boolean checkPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
			return permissions.get(name);
	}
}