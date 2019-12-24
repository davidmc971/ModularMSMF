package io.github.davidmc971.modularmsmf.util;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author davidmc971
 *
 */

public class ChatUtils {
	
	public enum ChatFormat {
		SUCCESS, INFO, ERROR, MSG, BROADCAST, STANDART, RAINBOW, NOPERM
	}
	
	public static String getFormattedPrefix(ChatFormat format) {
		switch(format) {
		case INFO:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case ERROR:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		case NOPERM:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.DARK_RED + " ");
		case SUCCESS:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GREEN + " ");
		default:
			return "[ModularMSMF] ";
		}
	}
}
