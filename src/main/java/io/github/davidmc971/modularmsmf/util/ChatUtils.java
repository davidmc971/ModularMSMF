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
		SUCCESS, INFO, ERROR, MSG, BROADCAST, STANDARD, RAINBOW, NOPERM, WELCOME, QUIT, DEATH, PAINTED, MONEY, CONSOLE, KICK, BAN, HOME_LIST, HOME
	}
	
	public static String getFormattedPrefix(ChatFormat format) {
		switch(format) {
		case CONSOLE:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case INFO:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case ERROR:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case KICK:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Kicked" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case BAN:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Banned" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case NOPERM:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case SUCCESS:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
		case HOME:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Home" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case HOME_LIST:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Home list" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case WELCOME:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Join" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case QUIT:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Quit" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case DEATH:
			return (ChatColor.GOLD + "[" + ChatColor.RED + "Death" + ChatColor.GOLD + "]" + ChatColor.DARK_GRAY + " ");
		case PAINTED:
			return (ChatColor.GRAY + " ");
		/**case SPAWN:
		for (Player p : Bukkit.getOnlinePlayers())
			return (ChatColor.GREEN + language.getString("").replaceAll("_player", p.getName()));
			*/
		default:
			return "[ModularMSMF] ";

		}
	}
}
