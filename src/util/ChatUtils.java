package util;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {
	public enum MsgLevel {
		INFO, ERROR, MSG, BROADCAST, STANDARD, RAINBOW
	}
	
	public static String getFormattedPrefix(MsgLevel lvl) {
		switch(lvl) {
		case INFO:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case ERROR:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "ModularMSMF" + ChatColor.GOLD + "]" + ChatColor.RED + " ");
		default:
			return "[ModularMSMF] ";
		}
	}
}
