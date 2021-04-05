package io.github.davidmc971.modularmsmf.economy;

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
		private static final long serialVersionUID = 8264538963637L;
		{
		//economy permissions
		put("eco_set", "modularmsmf.eco.set");
		put("eco_add", "modularmsmf.eco.add");
		put("eco_take", "modularmsmf.eco.take");
		put("eco_pay", "modularmsmf.eco.pay");
		put("eco_lookup", "modularmsmf.eco.lookup");
	}};

	public static boolean checkPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
			return permissions.get(name);
	}
}