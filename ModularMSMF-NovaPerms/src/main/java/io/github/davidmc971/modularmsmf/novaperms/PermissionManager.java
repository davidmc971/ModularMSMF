package io.github.davidmc971.modularmsmf.novaperms;

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
		private static final long serialVersionUID = 365867436165437987L;
		
		{
		//novaperms permissions
        put("use","modularmsmf.novaperms.use");
	}};

	public static boolean checkPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}

	public static String getPermission(String name) {
			return permissions.get(name);
	}
}