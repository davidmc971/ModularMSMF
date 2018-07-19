package commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import main.ModularMSMF;

public class CommandMotd extends AbstractCommand {
	
	// @TODO HALLOOOOOOOONFC#+poarwenb+oprenb
	
	//private File dataStore = new File("plugins/ModularMSMF/motd.txt");
	
	//private FileConfiguration cfg;

	public CommandMotd(ModularMSMF plugin) {
		super(plugin);
	}

	@SuppressWarnings("unused")
	public void load() {

		File file = new File("plugins/ModularMSMF/motd.yml");
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

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender.hasPermission("modularmsmf.motd"))) {
			String toLowerCase = label.toLowerCase();
			switch (toLowerCase) {
			case "motd":

				switch (args[0].toLowerCase()) {
				case "on":

				case "off":

				default:

				}
			}
		}
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
	public String getCommandLabel() {
		return "motd";
	}
}
