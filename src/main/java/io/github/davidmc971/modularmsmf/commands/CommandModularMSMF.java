package io.github.davidmc971.modularmsmf.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandModularMSMF extends AbstractCommand {

	public CommandModularMSMF(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String debugPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.DEBUG);

	@Override
	public String[] getCommandLabels() {
		return new String[] { "mmsmf" };
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		String toLowerCase = label.toLowerCase();
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		switch (toLowerCase) {
		case "mmsmf":
			if (PermissionManager.checkPermission(sender, "mmsmf")) {
				if (args.length == 0) {
					/**
					 * TODO: changing server name as its own by new command, needs to be
					 * implemented.
					 */
					sender.sendMessage(infoPrefix + "Plugin enabled on: " + Bukkit.getName());

					Component testComponent = Component.text(infoPrefix + "More help: ")
							.style(Style.empty().decorate(TextDecoration.BOLD))
							.append(Component.text("info")
									.color(TextColor.color(127, 127, 127))
									.hoverEvent(net.kyori.adventure.text.event.HoverEvent
											.showText(Component.text(language.getString("commands.mmsmf.info"))))
									.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf info")))
							.append(Component.text(" "))
							.append(Component.text("report a bug")
									.color(TextColor.color(255, 127, 127))
									.hoverEvent(net.kyori.adventure.text.event.HoverEvent
											.showText(Component.text(language.getString("commands.mmsmf.report"))))
									.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf report")))
							.append(Component.text(" "))
							.append(Component.text("teamspeak")
									.color(TextColor.color(127, 127, 127))
									.hoverEvent(net.kyori.adventure.text.event.HoverEvent
											.showText(Component.text(language.getString("commands.mmsmf.teamspeak"))))
									.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf teamspeak")))
							.append(Component.text(" "))
							.append(Component.text("discord")
									.color(TextColor.color(127, 127, 255))
									.hoverEvent(net.kyori.adventure.text.event.HoverEvent
							.showText(Component.text(language.getString("commands.mmsmf.discord"))))
									.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf discord")));

					sender.sendMessage(Identity.nil(), testComponent, MessageType.SYSTEM);

				} else if (args.length == 1) {
					switch (args[0].toLowerCase()) {
					case "info":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix + "Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
							sender.sendMessage(
									infoPrefix + "Server's running at: " + ChatColor.YELLOW + Bukkit.getVersion());
							sender.sendMessage(infoPrefix + "Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
							sender.sendMessage(
									debugPrefix + "Build Timestamp: " + ChatColor.YELLOW + plugin.getDebugTimestamp());
						}
						break;
					case "report":
						/**
						 * TODO: will be implemented. otherwise discord/github for reporting bugs.
						 */
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"general.notimplementedyet");
						break;
					case "teamspeak":
						if (args.length == 1) {
							File file = new File("plugins/ModularMSMF/settings.yml");
							boolean ts = false;
							if (file.exists()) {
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
								if (cfg.contains("teamspeakIP") && !cfg.getString("teamspeakIP").equals("")) {
									ts = true;
									String teamspeakIP = cfg.getString("teamspeakIP");
									Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
											"commands.mmsmf.teamspeak", "_ip", teamspeakIP);
								}
							}
							if (!ts)
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
										"commands.mmsmf.teamspeakmissing");
						}
						break;
					case "discord":
						if (args.length == 1) {
							File file = new File("plugins/ModularMSMF/settings.yml");
							boolean dc = false;
							if (file.exists()) {
								YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
								if (cfg.contains("discordID") && !cfg.getString("discordID").equals("")) {
									dc = true;
									String discordID = cfg.getString("discordID");
									Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
											"commands.mmsmf.discord", "_link", discordID);
								}
							}
							if (!dc)
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
										"commands.mmsmf.discordmissing");
						}
						break;
					default:
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"general.invalidarguments");
					}
				} else if (args.length >= 2) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"general.toomanyarguments");
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
		}
		return true;
	}
}
