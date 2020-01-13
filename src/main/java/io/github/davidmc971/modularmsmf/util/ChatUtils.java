package io.github.davidmc971.modularmsmf.util;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author davidmc971
 *
 */

public class ChatUtils {

	public enum ChatFormat {
		SUCCESS, INFO, ERROR, MSG, BROADCAST, STANDARD, RAINBOW, NOPERM, WELCOME, QUIT, DEATH, MONEY, CONSOLE, KICKED, /*BAN,*/ HOME_LIST, HOME, BANNED, KICK, BAN, UNBAN, REPORT, SPAWN, FEED, HEAL, LANGUAGE
	}
	
	public static String getFormattedPrefix(ChatFormat format) {
		switch(format) {
		case CONSOLE: //if console tries a command but fails
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Console" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case INFO: //if nothing special happens, just informational
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Info" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case ERROR: //if wrong syntax or argument
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Error" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case KICKED: //if user got kicked
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Kicked" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case BANNED: //if user got banned
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Banned" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case NOPERM: //if user has no permission
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Permission" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case SUCCESS: //if something was successful
			return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Success" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
		case HOME: //home-relevant stuff
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Home" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case HOME_LIST: //list homes with this prefix
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Home list" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case WELCOME: //if someone joins
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Join" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case QUIT: //if someone leaves
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Quit" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case DEATH: //if someone dies or all
			return (ChatColor.GOLD + "[" + ChatColor.DARK_GRAY + "Death" + ChatColor.GOLD + "]" + ChatColor.DARK_GRAY + " ");
		case KICK: //if someone uses kick-cmd
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Kick" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case BAN: //if someone uses ban-cmd
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Ban" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case UNBAN: //if someone uses unban-cmd
			return (ChatColor.GOLD + "[" + ChatColor.BLUE + "Unban" + ChatColor.GOLD + "]" + ChatColor.BLUE + " ");
		case REPORT: //report-relevant prefix
			return (ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Report" + ChatColor.GOLD + "]" + ChatColor.DARK_AQUA + " ");
		case SPAWN: //if someone uses spawn-cmd
			return (ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Spawn" + ChatColor.GOLD + "]" + ChatColor.DARK_AQUA + " ");
		case FEED: //if someone uses feed-cmd
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Feed" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case HEAL: //if someone uses heal-cmd
			return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Heal" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
		case LANGUAGE: //if someone uses heal-cmd
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Language" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		default: //if no enum has been set which doesnt work
			return "[ModularMSMF] ";

		}
	}
}
