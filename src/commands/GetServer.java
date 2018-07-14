package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GetServer {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (sender.hasPermission("multiplux.getserver")) {
			sender.sendMessage("Aktuelle Server-Version: " + Bukkit.getVersion());
		}
	}
}
