package io.github.davidmc971.modularmsmf.util;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author davidmc971
 *
 */

public class ChatUtils {

	/**public ChatUtils(ModularMSMF plugin) {
		super();
	}
	FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
	*/ //TODO: maybe need this.
	public enum ChatFormat {
		SUCCESS, INFO, ERROR, MSG, BROADCAST, STANDARD, RAINBOW, NOPERM, WELCOME, QUIT, DEATH, MONEY, CONSOLE, KICKED, /*BAN,*/ HOME_LIST, HOME, BANNED, KICK, BAN, UNBAN, REPORT, SPAWN, FEED, HEAL
	}
	
	public static String getFormattedPrefix(ChatFormat format) {
		switch(format) {
		case CONSOLE: //if console tries a command but fails
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Console" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case INFO: //if nothing special happens, just informational
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Info" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case ERROR: //if wrong syntax or argument
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Error" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case KICKED:
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Kicked" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case BANNED:
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Banned" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case NOPERM:
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Permission" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case SUCCESS:
			return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Success" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
		case HOME:
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Home" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case HOME_LIST:
			return (ChatColor.GOLD + "[" + ChatColor.GRAY + "Home list" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case WELCOME:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Join" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case QUIT:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Quit" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case DEATH:
			return (ChatColor.GOLD + "[" + ChatColor.DARK_GRAY + "Death" + ChatColor.GOLD + "]" + ChatColor.DARK_GRAY + " ");
		case KICK:
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Kick" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case BAN:
			return (ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Ban" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case UNBAN:
			return (ChatColor.GOLD + "[" + ChatColor.BLUE + "Unban" + ChatColor.GOLD + "]" + ChatColor.BLUE + " ");
		case REPORT:
			return (ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "Report" + ChatColor.GOLD + "]" + ChatColor.DARK_AQUA + " ");
		case SPAWN:
			return (ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Spawn" + ChatColor.GOLD + "]" + ChatColor.DARK_AQUA + " ");
		case FEED:
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Feed" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case HEAL:
			return (ChatColor.GOLD + "[" + ChatColor.GREEN + "Heal" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
		default:
			return "[ModularMSMF] ";

		}
	}
}
