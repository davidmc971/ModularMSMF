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
			put("toggle_*", "modularmsmf.basics.toggle.*");
			put("toggle_commands", "modularmsmf.basics.toggle.commands");
			// banning permissions
			put("ban_*", "modularmsmf.basics.ban.*");
			put("ban_player", "modularmsmf.basics.ban.banplayer");
			put("ban_ip", "modularmsmf.basics.ban.banip");
			// unbanning permissions
			put("unban_*", "modularmsmf.basics.unban.*");
			put("unban", "modularmsmf.basics.unban");
			put("unban_ip", "modularmsmf.basics.unbanip");
			// kicking permissions
			put("kickplayer", "modularmsmf.basics.kick.kickplayer");
			// heal permissions
			put("heal_*", "modularmsmf.basics.heal.*");
			put("heal_use", "modularmsmf.basics.heal");
			put("heal_self", "modularmsmf.basics.heal.healself");
			put("heal_other", "modularmsmf.basics.heal.healother");
			// healall permissions
			put("heal_all_use", "modularmsmf.basics.healall");
			// feed permissions
			put("feed_*", "modularmsmf.basics.feed.*");
			put("feed_use", "modularmsmf.basics.feed");
			put("feed_self", "modularmsmf.basics.feed.feedself");
			put("feed_others", "modularmsmf.basics.feed.feedothers");
			// home permissions
			put("home_*", "modularmsmf.basics.home.*");
			put("home", "modularmsmf.basics.home");
			put("home_self", "modular.home.self");
			put("home_help", "modularmsmf.basics.home.help");
			put("home_list", "modularmsmf.basics.home.list");
			put("home_set", "modularmsmf.basics.home.set");
			put("home_remove", "modularmsmf.basics.home.remove");
			put("home_rtp", "modularmsmf.basics.home.rtp");
			put("home_admin", "modularmsmf.basics.home.admin");
			// kill permissions
			put("kill_*", "modularmsmf.basics.kill.*");
			put("kill_me", "modularmsmf.basics.kill.me");
			put("kill_all", "modularmsmf.basics.kill.all");
			put("kill", "modularmsmf.basics.kill");
			// slaughter permissions
			put("slaughter", "modularmsmf.basics.slaughter");
			// mute permissions
			put("mute_*", "modularmsmf.basics.mute.*");
			put("mute", "modularmsmf.basics.mute");
			// spawn permissions
			put("spawn_*", "modularmsmf.basics.spawn.*");
			put("spawn", "modularmsmf.basics.spawn");
			put("spawn_others", "modularmsmf.basics.spawn.others");
			put("spawn_remove", "modularmsmf.basics.spawn.remove");
			// setspawn permissions
			put("setspawn_*", "modularmsmf.basics.setspawn.*");
			put("setspawn", "modularmsmf.basics.setspawn");
			// teleport permissions
			put("teleport", "modularmsmf.basics.teleport");
			// back permissions
			put("back", "modularmsmf.basics.back");
			put("back_others", "modularmsmf.basics.back.others");
			// set permissions
			put("set_*", "modularmsmf.basics.set.*");
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
			put("get_*", "modularmsmf.basics.get.*");
			put("get_use", "modularmsmf.basics.get.use");
			put("get_use_life", "modularmsmf.basics.get.use.life");
			put("get_use_food", "modularmsmf.basics.get.use.food");
			put("get_use_saturation", "modularmsmf.basics.get.use.saturation");
			put("get_use_exp", "modularmsmf.basics.get.use.exp");
			put("get_use_level", "modularmsmf.basics.get.use.level");
			put("get_use_ip", "modularmsmf.basics.get.use.ip");
			// channel permissions
			put("channels_*", "modularmsmf.basics.channels.*"); // use anything in channels
			put("channels_use", "modularmsmf.basics.channels.use"); // to use the command
			put("channels_list_*", "modularmsmf.basics.channel.list.*"); // mod/admin related
			put("channels_list_public", "modularmsmf.basics.channels.list.public");
			put("channels_list_private", "modularmsmf.basics.channels.list.private"); // mod/admin related
			put("channels_get_player", "modularmsmf.basics.channels.get.player"); // shows channel the player is in
			put("channels_create", "modularmsmf.basics.channels.create"); // create channel
			put("channels_remove", "modularmsmf.basics.channels.remove"); // remove channel
			put("channels_admin", "modularmsmf.basics.channels.admin"); // mod/admin related
			put("channels_admin_create", "modularmsmf.basics.channels.create.admin"); // create channel as admin
			put("channels_admin_remove", "modularmsmf.basics.channels.remove.admin"); // remove channel as admin
			put("channels_admins_join", "modularmsmf.basics.channels.join.admins"); // mod/admin related channels
			put("channels_admins_switch", "modularmsmf.basics.channels.switch.admins");
			put("channels_admins_kick", "modularmsmf.basics.channels.kick");
			// fly permissions
			put("fly_*", "modularmsmf.basics.fly.*");
			put("fly_use", "modularmsmf.basics.fly"); // to use the command
			put("fly_self", "modularmsmf.basics.fly.self"); // for self activating fly
			put("fly_others", "modularmsmf.basics.fly.others"); // for others like /fly <name>
			// clearinv permissions
			put("clearinv_*", "modularmsmf.basics.clearinventory.*");
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
