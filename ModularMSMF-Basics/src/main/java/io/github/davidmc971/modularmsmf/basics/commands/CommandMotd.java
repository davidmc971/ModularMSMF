package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;

public class CommandMotd implements IModularMSMFCommand {

	/**
	 * @TODO onplayerjoinevent und normal zum abrufen
	 * @param plugin
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
		/*
		 * if (!(sender.hasPermission("modularmsmf.motd"))) { String toLowerCase =
		 * label.toLowerCase(); switch (toLowerCase) { case "motd":
		 * 
		 * switch (args[0].toLowerCase()) { case "on":
		 * 
		 * case "off":
		 * 
		 * default:
		 * 
		 * } } }
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
