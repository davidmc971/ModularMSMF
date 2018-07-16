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
		
		switch (toLowerCase) {
		case "mmsmf":
			if (sender.hasPermission("mmsmf")) {
				if (args.length == 0) {
					sender.sendMessage(infoPrefix+"Plugin enabled on: " + Bukkit.getServerName());
					sender.sendMessage(infoPrefix+"More help:");
					sender.sendMessage(infoPrefix+"info || discord");
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
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Discord-URL: " + ChatColor.BLUE + "https://discord.gg/SxDQcJ6");
						}
						break;
					default:
						sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " This command '" + ChatColor.YELLOW + args[0] + ChatColor.GRAY + "' doesn't exist!");
					}
				} else if (args.length >= 2) {
					sender.sendMessage(errorPrefix+"Too many arguments!");
				} else {
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " You don't have permission for this!");
				}
			}
		}
	}
}