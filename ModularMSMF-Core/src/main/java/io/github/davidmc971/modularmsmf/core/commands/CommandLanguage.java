package io.github.davidmc971.modularmsmf.core.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.CommandRegistry;
import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 *
 * @author David Alexander Pfeiffer (davidmc971)
 *         Permissionless command because it's player specific and not server
 *         side (Lightkeks)
 *
 */

@CommandRegistry
public class CommandLanguage implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandLanguage() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
			case 0:
				handleNoArgs(sender);
				break;
			case 1:
				return languageSubCmd(sender, command, label, args);
			case 2:
				return languageSetGet(sender, command, label, args);
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"coremodule.commands.arguments.invalid");
				break;
		}
		return true;
	}

	private void handleNoArgs(CommandSender sender) {
		FileConfiguration language = Utils.configureCommandLanguage(sender);
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "coremodule.commands.language.active",
				"_language",
				(language.getString("language.id") + " (" + language.getString("language.name") + ") "));
	}

	private boolean languageSetGet(CommandSender sender, Command command, String label, String[] args) {
		LanguageManager languageManager = plugin.getLanguageManager();
		if (args[0].equalsIgnoreCase("set")) {
			boolean success = false;
			for (YamlConfiguration cfg : languageManager.getAvailableLanguages()) {
				if (cfg.getString("language.id").equals(args[1])) {
					success = true;
					break;
				}
			}
			if (!success && (sender instanceof Player || sender instanceof ConsoleCommandSender)) {
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"coremodule.commands.language.notvalid");
				return true;
			}
			if (sender instanceof ConsoleCommandSender) {
				if (plugin.getLanguageManager().setStandardLanguage(args[1])) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
							"coremodule.commands.language.setsuccessconsole");
					return true;
				}
			}
			plugin.getDataManager().getPlayerCfg(((Player) sender).getUniqueId()).set("language", args[1]);
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
					"coremodule.commands.language.setsuccessplayer");
			success = false;
			return true;
		}
		if (args[0].equalsIgnoreCase("get")) {
			UUID target = null;
			target = Utils.getPlayerUUIDByName(args[0]);
			String getPlayerLanguage = plugin.getDataManager().getPlayerCfg(target).get("language").toString(); // FIXME:
																												// return
																												// null
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player == null) {
					sender.sendMessage("player non existant");
					return true;
				}
				if (player == sender) {
					handleNoArgs(sender);
					return true;
				}
				sender.sendMessage(player.getName() + "'s lang set to: " + getPlayerLanguage);
				return true;
			}
			sender.sendMessage("player not online");
		}
		return true;
	}

	private boolean languageSubCmd(CommandSender sender, Command command, String label, String[] args) {
		LanguageManager languageManager = plugin.getLanguageManager();

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
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.LANGUAGE,
					"coremodule.commands.language.available", "_var", out);
			return true;
		}
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.commands.arguments.missing");
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
