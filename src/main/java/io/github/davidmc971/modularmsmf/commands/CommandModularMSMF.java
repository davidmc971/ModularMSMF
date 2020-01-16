package io.github.davidmc971.modularmsmf.commands;

import java.io.File;

import javax.swing.text.TabSet;

import org.bukkit.Bukkit;
import org.bukkit.Server.Spigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

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
					 *       implemented.
					 */
					sender.sendMessage(infoPrefix + "Plugin enabled on: " + Bukkit.getName());

					TextComponent infoComponent = new TextComponent("info");
					TextComponent reportComponent = new TextComponent("report");
					TextComponent teamspeakComponent = new TextComponent("teamspeak");
					TextComponent discordComponent = new TextComponent("discord");

					infoComponent.setColor(net.md_5.bungee.api.ChatColor.GRAY);
					reportComponent.setColor(net.md_5.bungee.api.ChatColor.RED);
					teamspeakComponent.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
					discordComponent.setColor(net.md_5.bungee.api.ChatColor.AQUA);

					infoComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/mmsmf info" ) );
					infoComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( language.getString("commands.mmsmf.info") ).create() ) );

					reportComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/mmsmf report" ) );
					reportComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( language.getString("commands.mmsmf.report") ).create() ) );

					teamspeakComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/mmsmf teamspeak" ) );
					teamspeakComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( language.getString("commands.mmsmf.teamspeak") ).create() ) );

					discordComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/mmsmf discord" ) );
					discordComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( language.getString("commands.mmsmf.discord") ).create() ) );

					TextComponent message = new TextComponent();
					message.addExtra(infoPrefix + "More help: ");
					message.setBold(true);
					message.addExtra(infoComponent);
					message.addExtra(" ");
					message.addExtra(teamspeakComponent);
					message.addExtra(" ");
					message.addExtra(discordComponent);

					sender.spigot().sendMessage(message);
					/*
					sender.spigot().sendMessage(infoComponent);
					sender.spigot().sendMessage(reportComponent);
					sender.spigot().sendMessage(teamspeakComponent);
					sender.spigot().sendMessage(discordComponent);
					*/
					

				} else if (args.length == 1) {
					switch (args[0].toLowerCase()) {
					case "info":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix + "Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
							sender.sendMessage(infoPrefix + "Server's running at: " + ChatColor.YELLOW + Bukkit.getVersion());
							sender.sendMessage(infoPrefix + "Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
							sender.sendMessage(errorPrefix + "Debug: Build [" + plugin.getDebugTimestamp() + "]");
						}
						break;
					case "report":
						/**
						 * TODO: will be implemented. otherwise discord/github for reporting bugs.
						 */
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.notimplementedyet");
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
									Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "commands.mmsmf.teamspeak", "_ip", teamspeakIP);
								}
							}
							if(!ts) Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.mmsmf.teamspeakmissing");
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
									Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "commands.mmsmf.discord", "_link", discordID);
								}
							}
							if(!dc) Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.mmsmf.discordmissing");
						}
						break;
					default:
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
					}
				} else if (args.length >= 2) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
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
