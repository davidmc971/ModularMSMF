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
 * @author David Alexander Pfeiffer (davidmc971)
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

		switch (args.length) {
		case 0:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "coremodule.commands.language.active",
					"_language",
					(language.getString("language.id") + " (" + language.getString("language.name") + ") "));
			break;
		case 1:
			if (args[0].equalsIgnoreCase("help")) {
				// TODO[epic=code needed,seq=23] still wip
			}
			if (args[0].equalsIgnoreCase("list")) {
				String out = "[";
				for (YamlConfiguration languageCfg : languageManager.getAvailableLanguages()) {
					if (!out.equals("["))
						out += ", ";
					out += languageCfg.getString("language.id");
				}
				out += "]";
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.LANGUAGE,
						"coremodule.commands.language.available", "_var", out);
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.commands.arguments.missing");
			}
			break;
		case 2:
			if (args[0].equalsIgnoreCase("set")) {
				if (sender instanceof Player) {
					boolean success = false;
					;
					for (YamlConfiguration cfg : languageManager.getAvailableLanguages()) {
						if (cfg.getString("language.id").equals(args[1])) {
							success = true;
							break;
						}
					}
					if (success) {
						plugin.getDataManager().getPlayerCfg(((Player) sender).getUniqueId()).set("language", args[1]);
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
								"coremodule.commands.language.setsuccessplayer");
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"coremodule.commands.language.notvalid");
					}
				} else {
					if (plugin.getLanguageManager().setStandardLanguage(args[1])) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
								"coremodule.commands.language.setsuccessconsole");
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"coremodule.commands.language.notvalid");
					}
				}
			} else if (args[0].equalsIgnoreCase("get")) {
				// TODO[epic=code needed,seq=24] get other players language
			}
			break;
		default:
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.commands.arguments.invalid");
			break;
		}
		return true;
	}

	@Override
	public String Label() {
		return "language";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "lang", "locale" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
