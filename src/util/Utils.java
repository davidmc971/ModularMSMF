package util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;

public class Utils {

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

	public static YamlConfiguration configureCommandLanguage(CommandSender sender, ModularMSMF plugin){
		LanguageManager languageManager = plugin.getLanguageManager();
		YamlConfiguration language = languageManager.getStandardLanguage();
		if(sender instanceof Player){
			String lang = DataManager.getPlayerCfg(((Player) sender).getUniqueId()).getString("language");
			if(lang != null){
				YamlConfiguration temp = languageManager.getLanguage(lang);
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