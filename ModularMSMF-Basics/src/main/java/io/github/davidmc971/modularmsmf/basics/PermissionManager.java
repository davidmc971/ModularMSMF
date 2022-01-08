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
		//all toggleable permissions
		put("toggle_commands", "modularmsmf.toggle.commands");
		//banning permissions
		put("banplayer", "modularmsmf.ban.banplayer");
		put("banip", "modularmsmf.ban.banip");
		put("unban", "modularmsmf.unban");
		//kicking permissions
		put("kickplayer", "modularmsmf.kick.kickplayer");
		//heal permissions
		put("heal_use", "modularmsmf.heal");
		put("healself", "modularmsmf.heal.healself");
		put("healother", "modularmsmf.heal.healother");
		put("healall_use", "modularmsmf.healall");
		//feed permissions
		put("feed_use", "modularmsmf.feed");
		put("feedself", "modularmsmf.feed.feedself");
		put("feedothers", "modularmsmf.feed.feedothers");
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
        //slaughter permissions
		put("slaughter", "modularmsmf.slaughter");
		//mute permissions
		put("mute", "modularmsmf.mute");
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
		//set permissions
		put("set_use","modularmsmf.set.use");
		put("set_use_life","modularmsmf.set.use.life");
		put("set_use_life_others", "modularmsmf.set.use.life.others");
		put("set_use_food","modularmsmf.set.use.food");
		put("set_use_food_others", "modularmsmf.set.use.food.others");
		put("set_use_saturation","modularmsmf.set.use.saturation");
		put("set_use_saturation_others","modularmsmf.set.use.saturation.others");
		put("set_use_exp","modularmsmf.set.use.exp");
		put("set_use_exp_others","modularmsmf.set.use.exp.others");
		put("set_use_level","modularmsmf.set.use.level");
		put("set_use_level_others","modularmsmf.set.use.level.others");
		//get permissions
		put("get_use","modularmsmf.get.use");
		put("get_use_life","modularmsmf.get.use.life");
		put("get_use_food","modularmsmf.get.use.food");
		put("get_use_saturation","modularmsmf.get.use.saturation");
		put("get_use_exp","modularmsmf.get.use.exp");
		put("get_use_level","modularmsmf.get.use.level");
		put("get_use_ip","modularmsmf.get.use.ip");
		put("get_use_inventory", "modularmsmf.get.use.inventory");
		//channel permissions
		put("channels_use", "modularmsmf.channels.use"); // to use the command
		put("channels_list_all", "modularmsmf.channel.list.all"); //admin related
		put("channels_list_public", "modularmsmf.channels.list.public"); //anyone can see public channels, only if permission is set
		put("channels_list_private", "modularmsmf.channels.list.private"); //mod/admin related
        //fly permissions
		put("fly_use", "modularmsmf.fly"); //to use the command
		put("fly_self", "modularmsmf.fly.self"); //for self activating fly
		put("fly_others", "modularmsmf.fly.others"); //for others like /fly <name>
		//clearinv permissions
		put("clearinv_use","modularmsmf.clearinventory.use");
		put("clearinv_self", "modularmsmf.clearinventory.self");
		put("clearinv_others", "modularmsmf.clearinventory.others");
        }

    };
    public static boolean checkPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
			return permissions.get(name);
	}
}
