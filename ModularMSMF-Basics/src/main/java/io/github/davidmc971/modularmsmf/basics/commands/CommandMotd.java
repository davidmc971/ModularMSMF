package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
//import org.bukkit.command.ConsoleCommandSender;
//import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

public class CommandMotd implements IModularMSMFCommand {

	/**
	 * @TODO onplayerjoinevent und normal zum abrufen
	 * @author Lightkeks
	 */

	// private File dataStore = new File("plugins/ModularMSMF/motd.txt");

	// private FileConfiguration cfg;

	// public void load() {

	// File file = new File("plugins/ModularMSMF/motd.yml");
	// if (file.exists()) {
	// FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	// } else {
	// try {
	// file.getParentFile().mkdirs();
	// file.createNewFile();

	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		switch (args.length) {
			case 0:
				return showMotd(sender, command, label, args);
			case 1:
				return subMotd(sender, command, label, args);
			default:
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"coremodule.commands.arguments.toomany");
		}
		return true;
	}

	private boolean subMotd(CommandSender sender, Command command, String label, String[] args) {

		switch (args[0].toLowerCase()) {
			case "on":
			case "off":
				return motdToggle(sender, command, label, args);
			case "admin":
				if (PermissionManager.checkPermission(sender, "motd_admin") || sender.isOp()) {
					return motdAdmin(sender, command, label, args);
				} else {
					ChatUtil.sendMsgNoPerm(sender);
				}
			default:
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"coremodule.commands.invalid");
				break;
		}
		return true;
	}

	private boolean motdAdmin(CommandSender sender, Command command, String label, String[] args) {

		/**
		 * related to admins/ops or moderators
		 */

		if (args[0].equalsIgnoreCase("on")) {
			if (PermissionManager.checkPermission(sender, "motd_admin_all")) {
				if (args[1].equalsIgnoreCase("all")) {

				}
			} else {
				ChatUtil.sendMsgNoPerm(sender);
			}
		}
		if (args[0].equalsIgnoreCase("off")) {

		}

		return true;
	}

	private boolean motdToggle(CommandSender sender, Command command, String label, String[] args) {

		if (PermissionManager.checkPermission(sender, "motd_use")) {
			// if "/motd on" it should turn on motd for each player
			if (args[0].equalsIgnoreCase("on")) {

			}
			// if "/motd off" it should turn off motd for each player
			if (args[0].equalsIgnoreCase("off")) {

			}
		} else {
			ChatUtil.sendMsgNoPerm(sender);
		}
		return true;
	}

	private boolean showMotd(CommandSender sender, Command command, String label, String[] args) {

		/**
		 * TODO: getting file to display whole file with formatting, if possible
		 */
		return true;
	}

	public void displayMotdFile() {

	}

	public static void setMotdStateOn() {

	}

	public static void setMotdStateOff() {

	}

	public static void getMotdState() {

	}

	@Override
	public String Label() {
		return "motd";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
