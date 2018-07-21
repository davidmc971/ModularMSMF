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

		if(args.length > 0) {
			switch (args[0].toLowerCase()) {
			case "list":
				
				break;
			case "set":
	
				break;
			case "remove":
	
				break;
			case "rtp":
	
				break;
			default:
	
				break;
			}
			
		} else {
			//args leer, entsprechende description senden
		}
		return true;
	}
	
	@Override
	public String getCommandLabel() {
		return "home";
	}
}
