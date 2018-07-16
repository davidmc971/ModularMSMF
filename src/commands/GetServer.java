package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import util.ChatUtils;

public class GetServer {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.INFO);
		
		if (sender.hasPermission("modularmsmf.getserver")) {
			sender.sendMessage(infoPrefix+"Aktuelle Server-Version: " + Bukkit.getVersion());
		}
	}
}
