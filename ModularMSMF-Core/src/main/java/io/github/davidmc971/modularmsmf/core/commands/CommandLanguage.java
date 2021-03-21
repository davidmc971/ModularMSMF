package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandLanguage implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

    public CommandLanguage() {
        plugin = ModularMSMFCore.Instance();
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		LanguageManager languageManager = plugin.getLanguageManager();
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		switch(args.length){
			case 0:
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "commands.language.activelanguage", "_language", (language.getString("language.id")+" ("+language.getString("language.name")+") "));
				break;
			case 1:
				if(args[0].equalsIgnoreCase("list")){
					String out = "[";
					for(YamlConfiguration languageCfg : languageManager.getAvailableLanguages()){
						if(!out.equals("[")) out +=", ";
						out += languageCfg.getString("language.id");
					}
					out += "]";
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.LANGUAGE, "commands.language.availablelanguages", "_var", out);
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
				}
				break;
			case 2:
				if(args[0].equalsIgnoreCase("set")){
					if(sender instanceof Player){
						boolean success = false;;
						for(YamlConfiguration cfg : languageManager.getAvailableLanguages()){
							if(cfg.getString("language.id").equals(args[1])){
								success = true;
								break;
							}
						}
						if(success){
							plugin.getDataManager().getPlayerCfg(((Player)sender).getUniqueId()).set("language", args[1]);
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.language.setsuccessplayer");
						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.language.notvalid");
						}
					} else {
						if(plugin.getLanguageManager().setStandardLanguage(args[1])){
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.language.setsuccessconsole");
						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.language.notvalid");
						}
					}
				} else if(args[0].equalsIgnoreCase("get")){
					//TODO: get other players language
				}
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
				break;
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "language" };
	}

}
