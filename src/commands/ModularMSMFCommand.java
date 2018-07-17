package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;
import net.md_5.bungee.api.ChatColor;
import util.ChatUtils;

public class ModularMSMFCommand {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {

		String toLowerCase = commandLabel.toLowerCase();
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.NOPERM);
		
		switch (toLowerCase) {
		case "mmsmf":
			if (sender.hasPermission("mmsmf")) {
				if (args.length == 0) {
					sender.sendMessage(infoPrefix+"Plugin enabled on: " + Bukkit.getServerName());
					sender.sendMessage(infoPrefix+"More help:");
					sender.sendMessage(infoPrefix+"info || discord || report");
				} else if (args.length == 1) {
					switch (args[0].toLowerCase()) {
					case "info":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix+"Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
							sender.sendMessage(infoPrefix+"Server's running at: " + ChatColor.YELLOW + Bukkit.getBukkitVersion());
							sender.sendMessage(infoPrefix+"Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
							sender.sendMessage(errorPrefix+"Debug: Build [" + plugin.getDebugTimestamp() + "]");
						}
						break;
					case "discord":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix+"Discord-URL: " + ChatColor.BLUE + "https://discord.gg/SxDQcJ6");
						}
						break;
					}
				} else if (args.length >= 2) {
					sender.sendMessage(errorPrefix+"Too many arguments!");
				} else {
					sender.sendMessage(noPermPrefix+"You don't have Permission to use this!!");
				}
			} else {
				sender.sendMessage(noPermPrefix+"You don't have Permission to use this!");
			}
		}
	}
}
