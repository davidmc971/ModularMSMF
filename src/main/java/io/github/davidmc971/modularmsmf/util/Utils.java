package io.github.davidmc971.modularmsmf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
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

	public static void broadcastWithConfiguredLanguageEach(ModularMSMF plugin, ChatUtils.ChatFormat format, String languageKey, String... toReplace) {
		String prefix = ChatUtils.getFormattedPrefix(format);
		List<CommandSender> broadcastList = new ArrayList<CommandSender>();
		broadcastList.add(Bukkit.getConsoleSender());
		broadcastList.addAll(Bukkit.getOnlinePlayers());
		broadcastList.forEach((subject) -> {
			FileConfiguration language = configureCommandLanguage(subject, plugin);
			String configuredMessage = prefix + language.getString(languageKey);
			if(toReplace.length % 2 == 0) {
				for(int i = 0; i < toReplace.length; i += 2) {
					configuredMessage.replaceAll(toReplace[i], toReplace[i + 1]);
				}
			} else {
				plugin.getLogger().severe("Missing argument inside toReplace for method broadcastWithConfiguredLanguageEach:");
				plugin.getLogger().severe("Every pattern String needs a value for replacing");
			}
			subject.sendMessage(configuredMessage);
		});
	}
}