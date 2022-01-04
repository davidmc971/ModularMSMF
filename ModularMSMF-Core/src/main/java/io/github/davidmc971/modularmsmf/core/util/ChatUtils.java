package io.github.davidmc971.modularmsmf.core.util;

//import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import net.md_5.bungee.api.ChatColor;

/**
 *
 * @author David Alexander Pfeiffer (davidmc971)
 *
 */

public class ChatUtils {

	static ChatColor bold = ChatColor.BOLD;
	static ChatColor gold = ChatColor.GOLD;
	static ChatColor reset = ChatColor.RESET;
	static ChatColor dark_red = ChatColor.DARK_RED;

	public enum ChatFormat {
		SUCCESS, INFO, ERROR, MSG, BROADCAST, STANDARD, RAINBOW, NOPERM, WELCOME, QUIT, DEATH, MONEY, CONSOLE, KICKED,
		HOME_LIST, HOME, BANNED, KICK, BAN, UNBAN, REPORT, SPAWN, FEED, HEAL, LANGUAGE, DEBUG, WARN, FLY_ON, FLY_OFF, ONLINE
	}

	public static String getFormattedPrefix(ChatFormat format) {
		switch (format) {
			case CONSOLE: // if console tries a command but fails
				return (/*bold +*/ gold + "[" + reset + dark_red + "Console" + gold + bold + "]" + reset + dark_red + " ");
			case INFO: // if nothing special happens, just informational
				return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Info" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
			case DEBUG: // used in debug builds
				return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Debug" + ChatColor.GOLD + "]" + ChatColor.WHITE
						+ " ");
			case ERROR: // if wrong syntax or argument
				return (ChatColor.GOLD + "[" + ChatColor.RED + "Error" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
			case KICKED: // if user got kicked
				return (ChatColor.GOLD + "[" + ChatColor.RED + "Kicked" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
			case BANNED: // if user got banned
				return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Banned" + ChatColor.GOLD + "]" + ChatColor.DARK_RED
						+ " ");
			case NOPERM: // if user has no permission
				return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Permission" + ChatColor.GOLD + "]"
						+ ChatColor.DARK_RED + " ");
			case SUCCESS: // if something was successful
				return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Success" + ChatColor.GOLD + "]" + ChatColor.GREEN
						+ " ");
			case HOME: // home-relevant stuff
				return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Home" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
			case HOME_LIST: // list homes with this prefix
				return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Home list" + ChatColor.GOLD + "]" + ChatColor.GRAY
						+ " ");
			case WELCOME: // if someone joins
				return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Join" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
			case QUIT: // if someone leaves
				return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Quit" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
			case DEATH: // if someone dies or all
				return (ChatColor.GOLD + "[" + ChatColor.DARK_GRAY + "Death" + ChatColor.GOLD + "]"
						+ ChatColor.DARK_GRAY + " ");
			case KICK: // if someone uses kick-cmd
				return (ChatColor.GOLD + "[" + ChatColor.RED + "Kick" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
			case BAN: // if someone uses ban-cmd
				return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Ban" + ChatColor.GOLD + "]" + ChatColor.DARK_RED
						+ " ");
			case UNBAN: // if someone uses unban-cmd
				return (ChatColor.GOLD + "[" + ChatColor.BLUE + "Unban" + ChatColor.GOLD + "]" + ChatColor.BLUE + " ");
			case REPORT: // report-relevant prefix
				return (ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Report" + ChatColor.GOLD + "]"
						+ ChatColor.DARK_AQUA + " ");
			case SPAWN: // if someone uses spawn-cmd
				return (ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Spawn" + ChatColor.GOLD + "]"
						+ ChatColor.DARK_AQUA + " ");
			case FEED: // if someone uses feed-cmd
				return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Feed" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
			case HEAL: // if someone uses heal-cmd
				return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Heal" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
			case LANGUAGE: // if someone uses heal-cmd
				return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Language" + ChatColor.GOLD + "]" + ChatColor.GRAY
						+ " ");
			case WARN:
				return (ChatColor.GOLD + "[" + ChatColor.YELLOW + "Warning" + ChatColor.GOLD + "]" + ChatColor.GRAY
						+ " ");
			case FLY_ON: // related to flight turning on
				return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Flight" + ChatColor.GOLD + "]" + ChatColor.WHITE
						+ " ");
			case FLY_OFF: // related to flight turning on
				return (ChatColor.GOLD + "[" + ChatColor.RED + "Flight" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
			case ONLINE: //related for list command
				return (ChatColor.BLACK + "[" + ChatColor.BOLD + ChatColor.UNDERLINE + ChatColor.LIGHT_PURPLE + " Online " + ChatColor.RESET + ChatColor.BLACK + "]" + ChatColor.GRAY + " ");
			default: // if no enum has been set which doesnt work
				return "[ModularMSMF] ";
		}
	}

	public static void sendMsgNoPerm(CommandSender sender) {
		if (sender instanceof Player) {
			Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.NOPERM,
					"coremodule.player.nopermission");
		}
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.CONSOLE, "coremodule.noconsole");
		}
	}
}
