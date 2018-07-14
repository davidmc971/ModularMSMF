package commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import main.ModularMSMF;

public class Motd {

	@SuppressWarnings("unused")
	private ModularMSMF plugin;
	
	@SuppressWarnings("unused")
	private File dataStore = new File("plugins/MultiPlux/motd.txt");
	
	@SuppressWarnings("unused")
	private FileConfiguration cfg;

	public Motd(ModularMSMF modularMSMF) {
		this.plugin = modularMSMF;
	}

	@SuppressWarnings("unused")
	public void load() {

		File file = new File("plugins/MultiPlux/motd.yml");
		if (file.exists()) {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		} else {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender.hasPermission("multiplux.motd"))) {
			String toLowerCase = commandLabel.toLowerCase();
			switch (toLowerCase) {
			case "motd":

				switch (args[0].toLowerCase()) {
				case "on":

				case "off":

				default:

				}
			}
		}

	}

	public void displayMotdFile() {

	}

	public static void setMotdStateOn() {

	}

	public static void setMotdStateOff() {

	}

	public static void getMotdState() {

	}
}
