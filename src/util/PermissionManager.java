package util;

public class PermissionManager {

	private static final String[][] permissions = { { "banplayer", "modularmsmf.ban.banplayer" },
			{ "banip", "modularmsmf.ban.banip" }, { "kickplayer", "modularmsmf.kick.kickplayer" },
			{ "healself", "modularmsmf.heal.healself" }, { "healother", "modularmsmf.heal.healother" },
			{ "feedself", "modularmsmf.feed.feedself" }, { "feedothers", "modularmsmf.feed.feedothers" },
			{ "eco_set", "modularmsmf.eco.set" }, { "eco_add", "modularmsmf.eco.add" },
			{ "eco_take", "modularmsmf.eco.take" }, { "eco_pay", "modularmsmf.eco.pay" },
			{ "eco_lookup", "modularmsmf.eco.lookup" }, { "home", "modularmsmf.home" },
			{ "home_list", "modularmsmf.home.list" }, { "home_set", "modularmsmf.home.set"},
			{ "home_remove", "modularmsmf.home.remove" }, { "home_rtp", "modularmsmf.home.rtp" }

	};

	public static String getPermission(String name) {
		for (String[] s : permissions) {
			if (s[0].equals(name)) {
				return s[1];
			}
		}
		return null;
	}
}
