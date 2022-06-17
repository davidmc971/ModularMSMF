package io.github.davidmc971.modularmsmf.core.commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;
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

public class CommandModularMSMF implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandModularMSMF() {
		plugin = ModularMSMFCore.Instance();
	}

	private final String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private final String debugPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.DEBUG);

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (!PermissionManager.checkPermission(sender, "mmsmf")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		switch (args.length) {
			case 0:
				defaultCmd(sender);
				break;
			case 1:
				handleArgs(sender, args);
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"arguments.toomany");
				break;
		}
		return true;
	}

	private void handleArgs(@NotNull CommandSender sender, @NotNull String[] args) {
		switch (args[0].toLowerCase()) {
			case "info":
				mmsmfInfo(sender, args);
				break;
			case "report":
				mmsmfReport(sender, args);
				break;
			case "discord":
				mmsmfDiscord(sender, args);
				break;
			case "teamspeak":
				mmsmfTeamspeak(sender, args);
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"arguments.invalid");
				break;
		}
	}

	private void defaultCmd(@NotNull CommandSender sender) {
		FileConfiguration language = Utils.configureCommandLanguage(sender);
		ArrayList<String> modulenames = new ArrayList<String>();
		Component pluginnames = Component.text(infoPrefix + "Enabled modules: ")
				.style(Style.empty().decorate(TextDecoration.BOLD))
				.append(Component.text("modulenames").color(TextColor.color(127, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent
								.showText(Component.text(modulenames.toString()))));

		Component help = Component.text(infoPrefix + "More help: ")
				.style(Style.empty().decorate(TextDecoration.BOLD))
				.append(Component.text("info").color(TextColor.color(127, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent
								.showText(Component.text(language.getString("commands.mmsmf.info"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf info")))
				.append(Component.text(" "))
				.append(Component.text("report a bug").color(TextColor.color(255, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent
								.showText(Component.text(language.getString("commands.mmsmf.report"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf report")))
				.append(Component.text(" "))
				.append(Component.text("teamspeak").color(TextColor.color(127, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(
								Component.text(language.getString("commands.mmsmf.teamspeak.click"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf teamspeak")))
				.append(Component.text(" "))
				.append(Component.text("discord").color(TextColor.color(127, 127, 255))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(
								Component.text(language.getString("commands.mmsmf.discord.click"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf discord")));

		sender.sendMessage(pluginnames);
		sender.sendMessage(help);
	}

	private void mmsmfInfo(CommandSender sender, String[] args) {
		if (args.length == 1) {
			sender.sendMessage(
					infoPrefix + "Plugin Version: " + ChatColor.GREEN + ModularMSMFCore.Instance().pluginver);
			sender.sendMessage(infoPrefix + "Server's running at: " + ChatColor.YELLOW + Bukkit.getVersion());
			sender.sendMessage(
					infoPrefix + "Developer: " + ChatColor.LIGHT_PURPLE + ModularMSMFCore.Instance().authors);
			sender.sendMessage(debugPrefix + "Build Timestamp: " + ChatColor.YELLOW
					+ ModularMSMFCore.Instance().getDebugTimestamp());
			return;
		}
	}

	private void mmsmfReport(CommandSender sender, String[] args) {
		/**
		 * TODO: will be implemented. otherwise discord/github for reporting bugs.
		 */
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "notimplementedyet");
	}

	private void mmsmfDiscord(CommandSender sender, String[] args) {
		if (args.length == 1) {
			File file = new File("plugins/ModularMSMF/settings.yml");
			if (!file.exists()) {
				sender.sendMessage(ChatColor.DARK_RED + "file should be created!");
				return;
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			if (cfg.contains("discordID") && !cfg.getString("discordID").equals("")) {
				String discordID = cfg.getString("discordID");
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
						"commands.mmsmf.discord.link", "_link", discordID);
				return;
			}
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.mmsmf.discord.linkmissing");
		}
	}

	private void mmsmfTeamspeak(CommandSender sender, String[] args) {
		if (args.length == 1) {
			File file = new File("plugins/ModularMSMF/settings.yml");
			if (!file.exists()) {
				sender.sendMessage(ChatColor.DARK_RED + "file should be created!");
				return;
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			if (cfg.contains("teamspeakIP") && !cfg.getString("teamspeakIP").equals("")) {
				String teamspeakIP = cfg.getString("teamspeakIP");
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
						"commands.mmsmf.teamspeak.ip", "_ip", teamspeakIP);
			}
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
					"commands.mmsmf.teamspeak.ipmissing");
		}
	}

	@Override
	public String Label() {
		return "modularmsmf";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "mmsmf" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
