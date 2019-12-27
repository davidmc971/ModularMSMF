package io.github.davidmc971.modularmsmf.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import net.md_5.bungee.api.ChatColor;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandModularMSMF extends AbstractCommand {

	public CommandModularMSMF(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[] { "mmsmf" };
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		String toLowerCase = label.toLowerCase();
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);

		switch (toLowerCase) {
		case "mmsmf":
			if (PermissionManager.checkPermission(sender, "mmsmf")) {
				if (args.length == 0) {
					/**
					 * @TODO changing server name as its own by new command, needs to be
					 *       implemented.
					 */
					sender.sendMessage(infoPrefix + "Plugin enabled on: " + Bukkit.getName());
					sender.sendMessage(infoPrefix + "More help:");
					sender.sendMessage(infoPrefix + "info || report || teamspeak || discord"); // missing report, not
																								// implemented yet
					/**
					 * TODO: adding ability to check for config.yml like if teamspeak is enabled or
					 * discord, and if both are enabled it will show both
					 * 
					 */
				} else if (args.length == 1) {
					switch (args[0].toLowerCase()) {
					case "info":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix + "Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
							sender.sendMessage(
									infoPrefix + "Server's running at: " + ChatColor.YELLOW + Bukkit.getVersion());
							sender.sendMessage(infoPrefix + "Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
							sender.sendMessage(errorPrefix + "Debug: Build [" + plugin.getDebugTimestamp() + "]");
						}
						break;
					case "report":
						/**
						 * TODO will be implemented. otherwise discord/github for reporting bugs.
						 */
						sender.sendMessage("Not implemented yet.");
						break;
					case "teamspeak":
						if (args.length == 1) {
							/**
							 * stuff adding to question if something is enabled in config or disabled. if
							 * enabled, teamspeak ip should be shown
							 */
							File file = new File("plugins/ModularMSMF/settings.yml");
							boolean ts = false;
							if (file.exists()) {
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
								if (cfg.contains("teamspeakIP") && !cfg.getString("teamspeakIP").equals("")) {
									ts = true;
									String teamspeakIP = cfg.getString("teamspeakIP");
									sender.sendMessage(infoPrefix + language.getString("commands.mmsmf.teamspeak") + " " + teamspeakIP);
								}
							}
							if(!ts) sender.sendMessage(errorPrefix + language.getString("commands.mmsmf.teamspeakmissing"));
						}
						break;
					case "discord":
						if (args.length == 1) {
							/**
							 * stuff adding to question if something is enabled in config or disabled. if
							 * enabled, discord link should be shown
							 */
							File file = new File("plugins/ModularMSMF/settings.yml");
							boolean dc = false;
							if (file.exists()) {
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
								if (cfg.contains("discordID") && !cfg.getString("discordID").equals("")) {
									dc = true;
									String discordID = cfg.getString("discordID");
									sender.sendMessage(infoPrefix + language.getString("commands.mmsmf.discord") + " " + discordID);
								}
							}
							if(!dc) sender.sendMessage(errorPrefix + language.getString("commands.mmsmf.discordmissing"));
						}
						break;
					default:
						sender.sendMessage(errorPrefix + language.getString("general.invalidarguments"));
					}
				} else if (args.length >= 2) {
					sender.sendMessage(errorPrefix + language.getString("general.toomanyarguments"));
				} else {
					sender.sendMessage(noPermPrefix + language.getString("general.nopermission"));
				}
			} else {
				sender.sendMessage(noPermPrefix + language.getString("general.nopermission"));
			}
		}
		return true;
	}
}
