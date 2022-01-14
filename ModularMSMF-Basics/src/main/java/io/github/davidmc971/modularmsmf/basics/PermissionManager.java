package io.github.davidmc971.modularmsmf.basics;

import java.util.HashMap;

import org.bukkit.command.CommandSender;

public class PermissionManager {
	private static final HashMap<String, String> permissions = new HashMap<String, String>() {
		/**
		*
		*/
		private static final long serialVersionUID = 2685763426239L;
		{
			// all toggleable permissions
			put("toggle_commands", "modularmsmf.basics.toggle.commands");
			// banning permissions
			put("banplayer", "modularmsmf.basics.ban.banplayer");
			put("banip", "modularmsmf.basics.ban.banip");
			put("unban", "modularmsmf.basics.unban");
			put("unbanip", "modularmsmf.basics.unbanip");
			// kicking permissions
			put("kickplayer", "modularmsmf.basics.kick.kickplayer");
			// heal permissions
			put("heal_use", "modularmsmf.basics.heal");
			put("healself", "modularmsmf.basics.heal.healself");
			put("healother", "modularmsmf.basics.heal.healother");
			put("healall_use", "modularmsmf.basics.healall");
			// feed permissions
			put("feed_use", "modularmsmf.basics.feed");
			put("feedself", "modularmsmf.basics.feed.feedself");
			put("feedothers", "modularmsmf.basics.feed.feedothers");
			// home permissions
			put("home", "modularmsmf.basics.home");
			put("home_self", "modular.home.self");
			put("home_help", "modularmsmf.basics.home.help");
			put("home_list", "modularmsmf.basics.home.list");
			put("home_set", "modularmsmf.basics.home.set");
			put("home_remove", "modularmsmf.basics.home.remove");
			put("home_rtp", "modularmsmf.basics.home.rtp");
			put("home_admin", "modularmsmf.basics.home.admin");
			// kill permissions
			put("kill_me", "modularmsmf.basics.kill.me");
			put("kill_all", "modularmsmf.basics.kill.all");
			put("kill", "modularmsmf.basics.kill");
			// slaughter permissions
			put("slaughter", "modularmsmf.basics.slaughter");
			// mute permissions
			put("mute", "modularmsmf.basics.mute");
			// spawn permissions
			put("spawn", "modularmsmf.basics.spawn");
			put("spawn_others", "modularmsmf.basics.spawn.others");
			put("setspawn", "modularmsmf.basics.setspawn");
			put("spawn_remove", "modularmsmf.basics.spawn.remove");
			// teleport permissions
			put("teleport", "modularmsmf.basics.teleport");
			// back permissions
			put("back", "modularmsmf.basics.back");
			put("back_others", "modularmsmf.basics.back.others");
			// set permissions
			put("set_use", "modularmsmf.basics.set.use");
			put("set_use_life", "modularmsmf.basics.set.use.life");
			put("set_use_life_others", "modularmsmf.basics.set.use.life.others");
			put("set_use_food", "modularmsmf.basics.set.use.food");
			put("set_use_food_others", "modularmsmf.basics.set.use.food.others");
			put("set_use_saturation", "modularmsmf.basics.set.use.saturation");
			put("set_use_saturation_others", "modularmsmf.basics.set.use.saturation.others");
			put("set_use_exp", "modularmsmf.basics.set.use.exp");
			put("set_use_exp_others", "modularmsmf.basics.set.use.exp.others");
			put("set_use_level", "modularmsmf.basics.set.use.level");
			put("set_use_level_others", "modularmsmf.basics.set.use.level.others");
			// get permissions
			put("get_use", "modularmsmf.basics.get.use");
			put("get_use_life", "modularmsmf.basics.get.use.life");
			put("get_use_food", "modularmsmf.basics.get.use.food");
			put("get_use_saturation", "modularmsmf.basics.get.use.saturation");
			put("get_use_exp", "modularmsmf.basics.get.use.exp");
			put("get_use_level", "modularmsmf.basics.get.use.level");
			put("get_use_ip", "modularmsmf.basics.get.use.ip");
			// channel permissions
			put("channels_use", "modularmsmf.basics.channels.use"); // to use the command
			put("channels_list_all", "modularmsmf.basics.channel.list.all"); // admin related
			put("channels_list_public", "modularmsmf.basics.channels.list.public"); // anyone can see public channels, only if permission is set
			put("channels_list_private", "modularmsmf.basics.channels.list.private"); // mod/admin related
			put("channels_list_admins", "modularmsmf.basics.channels.list.private.admins");
			put("channels_join_admins", "modularmsmf.basics.channels.join.admins");
			// fly permissions
			put("fly_use", "modularmsmf.basics.fly"); // to use the command
			put("fly_self", "modularmsmf.basics.fly.self"); // for self activating fly
			put("fly_others", "modularmsmf.basics.fly.others"); // for others like /fly <name>
			// clearinv permissions
			put("clearinv_use", "modularmsmf.basics.clearinventory.use");
			put("clearinv_self", "modularmsmf.basics.clearinventory.self");
			put("clearinv_others", "modularmsmf.basics.clearinventory.others");
		}

	};

	public static boolean checkPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
		return permissions.get(name);
	}
}
