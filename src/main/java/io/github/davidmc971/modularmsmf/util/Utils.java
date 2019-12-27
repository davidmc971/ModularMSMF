package io.github.davidmc971.modularmsmf.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;

/**
 * 
 * @author davidmc971
 *
 */

public class Utils {

	//public static String listPlayerHome; //not implemented yet ~Lightkeks

	public static Player getPlayerByName(String name) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	
	public static UUID getPlayerUUIDByName(String name){
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p.getUniqueId();
			}
		}
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p.getUniqueId();
			}
		}
		return null;
	}

	public static FileConfiguration configureCommandLanguage(CommandSender sender, ModularMSMF plugin){
		LanguageManager languageManager = plugin.getLanguageManager();
		FileConfiguration language = languageManager.getStandardLanguage();
		if(sender instanceof Player){
			String lang = plugin.getDataManager().getPlayerCfg(((Player) sender).getUniqueId()).getString("language");
			if(lang != null){
				FileConfiguration temp = languageManager.getLanguage(lang);
				if(temp != null && temp.contains("language.id")){
					language = temp;
				} else {
					plugin.getLogger().warning(
							languageManager.getStandardLanguage().getString("general.invalidcustomlanguage")
							.replaceAll("_player", ((Player)sender).getName()).replaceAll("_language", lang));
				}
			}
			
		}
		return language;
	}
}