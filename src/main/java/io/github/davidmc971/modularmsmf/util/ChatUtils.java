package io.github.davidmc971.modularmsmf.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
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
		SUCCESS, INFO, ERROR, MSG, BROADCAST, STANDART, RAINBOW, NOPERM, WELCOME, QUIT, DEATH
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
		case WELCOME:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Join" + ChatColor.GOLD + "]" + ChatColor.WHITE + " ");
		case QUIT:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Quit" + ChatColor.GOLD + "]" + ChatColor.GRAY + " ");
		case DEATH:
			return (ChatColor.GOLD + "[" + ChatColor.WHITE + "Death" + ChatColor.GOLD + "]" + ChatColor.DARK_GRAY + " ");
		/**case SPAWN:
		for (Player p : Bukkit.getOnlinePlayers())
			return (ChatColor.GREEN + language.getString("").replaceAll("_player", p.getName()));
		*/ //TODO: Rewrite for working language support
		default:
			return "[ModularMSMF] ";

		}
	}
}
