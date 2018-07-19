package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;
import net.md_5.bungee.api.ChatColor;
import util.ChatUtils;

public class CommandModularMSMF extends AbstractCommand {

	public CommandModularMSMF(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String toLowerCase = label.toLowerCase();
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
		
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
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "mmsmf";
	}
}
