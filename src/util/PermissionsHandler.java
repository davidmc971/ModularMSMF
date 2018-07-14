package util;

public class PermissionsHandler {

	private static final String[][] permissions = { { "banplayer", "multiplux.ban.banplayer" },
			{ "banip", "multiplux.ban.banip" }, { "kickplayer", "multiplux.kick.kickplayer" },
			{ "healself", "multiplux.heal.healself" }, { "healother", "multiplux.heal.healother" },
			{ "feedself", "multiplux.feed.feedself" }, { "feedothers", "multiplux.feed.feedothers" },
			{ "eco_set", "multiplux.eco.set" }, { "eco_add", "multiplux.eco.add" },
			{ "eco_take", "multiplux.eco.take" }, { "eco_pay", "multiplux.eco.pay" },
			{ "eco_lookup", "multiplux.eco.lookup" }, { "home_home", "multiplux.home.home" },
			{ "home_list", "multiplux.home.list" }

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
