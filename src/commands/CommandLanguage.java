package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import core.LanguageManager;
import main.ModularMSMF;
import util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandLanguage extends AbstractCommand {

	public CommandLanguage(ModularMSMF plugin) {
		super(plugin);
	}
	
	/**
	 * @TODO need prefix as text
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		LanguageManager languageManager = plugin.getLanguageManager();
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		//sender.sendMessage(language.getString("commands.language.commandnotforconsole"));
		
		switch(args.length){
			case 0:
				sender.sendMessage(language.getString("commands.language.activelanguage")
						.replaceAll("_language", (language.getString("language.id") + " (" + language.getString("language.name") + ")")));
				break;
			case 1:
				if(args[0].equalsIgnoreCase("list")){
					String out = "[";
					for(YamlConfiguration languageCfg : languageManager.getAvailableLanguages()){
						if(!out.equals("[")) out +=", ";
						out += languageCfg.getString("language.id");
					}
					out += "]";
					sender.sendMessage(language.getString("commands.language.availablelanguages").replaceAll("_var", out));
				} else {
					sender.sendMessage(language.getString("general.invalidarguments"));
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
							sender.sendMessage(language.getString("commands.language.setsuccessplayer"));
						} else {
							sender.sendMessage(language.getString("commands.language.notvalid"));
						}
					} else {
						if(plugin.getLanguageManager().setStandardLanguage(args[1])){
							sender.sendMessage(language.getString("commands.language.setsuccessconsole"));
						} else {
							sender.sendMessage(language.getString("commands.language.notvalid"));
						}
					}
				} else if(args[0].equalsIgnoreCase("get")){
					//TODO: get other players language
				}
				break;
			default:
				sender.sendMessage(language.getString("general.invalidarguments"));
				break;
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "language" };
	}

}
