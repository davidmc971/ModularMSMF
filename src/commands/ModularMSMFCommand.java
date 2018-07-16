package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;
import net.md_5.bungee.api.ChatColor;

public class ModularMSMFCommand {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {

		String toLowerCase = commandLabel.toLowerCase();
		switch (toLowerCase) {
		case "mmsmf":
			if (sender.hasPermission("mmsmf")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Plugin enabled on: " + Bukkit.getServerName());
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " More help:");
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " info || /*server || website || /*ts3 ||*/ discord");
				} else if (args.length == 1) {
					switch (args[0].toLowerCase()) {
					case "info":
						if (args.length == 1) {
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Server's running at: " + ChatColor.YELLOW + Bukkit.getBukkitVersion());
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.RED + " Debug: Build [" + plugin.getDebugTimestamp() + "]");
						}
						break;
/*					case "server":
						if (args.length == 1) {
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Server befindet sich noch in der Aufbauphase und wird stetig verbessert!");
						}
						break;
					case "website":
						if (args.length == 1) {
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Website ist noch keine in Planung.");
						}
						break;
					case "ts3":
						if (args.length == 1) {
							//sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " IP:  - " + ChatColor.RED + "Vorsicht, steht unter Aufbau!");
							TextComponent ts3link = new TextComponent(ChatColor.DARK_GRAY+"TS"+ChatColor.GRAY+"3 "+ChatColor.DARK_GREEN+"<< Link zum Verbinden >>");
							ts3link.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://invite.teamspeak.com/ts37.nitrado.net/?port=10200"));
							ts3link.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klick mich für TS3!").create()));
							sender.spigot().sendMessage(ts3link);
						}
						break;
*/					case "discord":
						if (args.length == 1) {
							sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " Discord-URL: " + ChatColor.BLUE + "https://discord.gg/SxDQcJ6");
						}
						break;
					default:
						sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " This command '" + ChatColor.YELLOW + args[0] + ChatColor.GRAY + "' doesn't exist!");
					}
				} else if (args.length >= 2) {
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.RED + " Too many arguments!");
				} else {
					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " You don't have permission for this!");
				}
			}
		}
	}
}