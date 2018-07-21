package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.ChatUtils;
import util.PermissionManager;
import util.Utils;

public class CommandHome extends AbstractCommand {

	public CommandHome(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
		
		if (!(sender instanceof Player)) {
			//TODO: console could maybe set home by hand to specific coordinates
			sender.sendMessage(noPermPrefix+"general.noconsole");
			return true;
		}
		// TODO: Implementieren
		// Playerdata plrdat =

		switch (label.toLowerCase()) {
		case "home":
			if(sender.hasPermission(PermissionManager.getPermission("home"))) {
				//src for home spawning
				if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
					if(args.length == 1 || args.equals("list")){
						/*
						 * LOGISCHE OPERATOREN
						 * ODER									||
						 * UND									&&
						 * KLEINER, KLEINER GLEICH				<	<=
						 * GROESSER								>	>=
						 * GLEICH								==
						 * NICHT GLEICH							!=
						 * NEGIERT (boolean)					!
						 * 
						 * 
						 */
						//listing all homes which the player has set
					} else {
						sender.sendMessage(errorPrefix+"commands.home.notset");
					}
				} else {
					sender.sendMessage(noPermPrefix+"general.nopermission");
				}
			} else {
				sender.sendMessage(noPermPrefix+"general.nopermission");
			}
				break;
		case "sethome":

			break;
		case "delhome":

			break;
		}
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "home";
	}
}
