package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandLanguage extends AbstractCommand {

	public CommandLanguage(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);
	
	/**
	 * @TODO need prefix as text
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		LanguageManager languageManager = plugin.getLanguageManager();
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		//sender.sendMessage(language.getString("commands.language.commandnotforconsole"));
		
		switch(args.length){
			case 0:
				sender.sendMessage(infoPrefix+language.getString("commands.language.activelanguage").replaceAll("_language", (language.getString("language.id") + " (" + language.getString("language.name") + ")")));
				break;
			case 1:
				if(args[0].equalsIgnoreCase("list")){
					String out = "[";
					for(YamlConfiguration languageCfg : languageManager.getAvailableLanguages()){
						if(!out.equals("[")) out +=", ";
						out += languageCfg.getString("language.id");
					}
					out += "]";
					sender.sendMessage(infoPrefix+language.getString("commands.language.availablelanguages").replaceAll("_var", out));
				} else {
					sender.sendMessage(errorPrefix+language.getString("general.invalidarguments"));
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
							sender.sendMessage(successfulPrefix+language.getString("commands.language.setsuccessplayer"));
						} else {
							sender.sendMessage(errorPrefix+language.getString("commands.language.notvalid"));
						}
					} else {
						if(plugin.getLanguageManager().setStandardLanguage(args[1])){
							sender.sendMessage(successfulPrefix+language.getString("commands.language.setsuccessconsole"));
						} else {
							sender.sendMessage(errorPrefix+language.getString("commands.language.notvalid"));
						}
					}
				} else if(args[0].equalsIgnoreCase("get")){
					//TODO: get other players language
				}
				break;
			default:
				sender.sendMessage(errorPrefix+language.getString("general.invalidarguments"));
				break;
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "language" };
	}

}
