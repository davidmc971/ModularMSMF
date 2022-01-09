package io.github.davidmc971.modularmsmf.core.commands;

import java.io.File;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

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

public class CommandModularMSMF implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandModularMSMF() {
		plugin = ModularMSMFCore.Instance();
	}

	private final String bukkitVersion = Bukkit.getBukkitVersion();
	private final String name = Bukkit.getName();
	private final String version = Bukkit.getVersion();
	private final String mcversion = Bukkit.getMinecraftVersion();
	private final int maxplayers = Bukkit.getMaxPlayers();

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
				return mmsmfCmd(sender, command, label, args);
			case 1:
				return mmsmfCmdMore(sender, command, label, args);
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"coremodule.commands.arguments.toomany");
				break;
		}
		return true;
	}

	private boolean mmsmfCmd(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender); // get the language string for selected
																				// language files

		// FIXME: Just put this back here temporarily for easy insight into relevant
		// development information.
		sender.sendMessage(infoPrefix + "Server Type: " + name);
		sender.sendMessage(infoPrefix + "Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
		sender.sendMessage(
				infoPrefix + "Server Version: " + ChatColor.YELLOW + bukkitVersion);
		sender.sendMessage(infoPrefix + "Developers: " + ChatColor.LIGHT_PURPLE + plugin.authors);
		sender.sendMessage(
				debugPrefix + "Build Timestamp: " + ChatColor.YELLOW + plugin.getDebugTimestamp());
		sender.sendMessage("Test: " + name + " <-- getName , " + bukkitVersion + " <-- getBukkitVersion , "
				+ version + " <-- getVersion , " + mcversion + " <-- getMinecraftVersion , "
				+ maxplayers + " <-- getMaxPlayers(int) , ");

		Component testComponent = Component.text(infoPrefix + "More help: ")
				.style(Style.empty().decorate(TextDecoration.BOLD))
				.append(Component.text("info").color(TextColor.color(127, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent
								.showText(Component.text(language.getString("coremodule.commands.mmsmf.info"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf info")))
				.append(Component.text(" "))
				.append(Component.text("report a bug").color(TextColor.color(255, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent
								.showText(Component.text(language.getString("coremodule.commands.mmsmf.report"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf report")))
				.append(Component.text(" "))
				.append(Component.text("teamspeak").color(TextColor.color(127, 127, 127))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(
								Component.text(language.getString("coremodule.commands.mmsmf.teamspeak.click"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf teamspeak")))
				.append(Component.text(" "))
				.append(Component.text("discord").color(TextColor.color(127, 127, 255))
						.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(
								Component.text(language.getString("coremodule.commands.mmsmf.discord.click"))))
						.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/mmsmf discord")));
		sender.sendMessage(Identity.nil(), testComponent, MessageType.SYSTEM);
		// TODO: testing iridiumcolorapi
		// solid tag
		sender.sendMessage(IridiumColorAPI.process("<SOLID:8200FF>REEEEEEEEEEEEEE"));
		sender.sendMessage("Solid tag: <SOLID:8200FF>'string'");
		// gradient tag
		sender.sendMessage(IridiumColorAPI.process("<GRADIENT:2C08BA>Cool string with a gradient</GRADIENT:028A97>"));
		sender.sendMessage("Gradient tag: <GRADIENT:2C08BA>'string'</GRADIENT:028A97>");
		// rainbow1
		sender.sendMessage(IridiumColorAPI.process("<RAINBOW1>THIS IS A REALLY COOL Rainbow</RAINBOW>"));
		sender.sendMessage("Rainbow1 tag: <RAINBOW1>'string'</RAINBOW>");
		// rainbow100
		sender.sendMessage(IridiumColorAPI.process("<RAINBOW100>THIS IS A REALLY COOL Rainbow</RAINBOW>"));
		sender.sendMessage("Rainbow100 tag: <RAINBOW100>'string'</RAINBOW>");
		return true;
	}

	private boolean mmsmfCmdMore(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0].toLowerCase()) {
			case "info":
				return mmsmfInfo(sender, command, label, args);
			case "report":
				return mmsmfReport(sender, command, label, args);
			case "discord":
				return mmsmfDiscord(sender, command, label, args);
			case "teamspeak":
				return mmsmfTeamspeak(sender, command, label, args);
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"coremodule.commands.arguments.invalid");
				break;
		}
		return true;
	}

	private boolean mmsmfInfo(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			sender.sendMessage(infoPrefix + "Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
			sender.sendMessage(infoPrefix + "Server's running at: " + ChatColor.YELLOW + Bukkit.getVersion());
			sender.sendMessage(infoPrefix + "Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
			sender.sendMessage(debugPrefix + "Build Timestamp: " + ChatColor.YELLOW + plugin.getDebugTimestamp());
			return true;
		}
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.commands.arguments.toomany");
		return true;
	}

	private boolean mmsmfReport(CommandSender sender, Command command, String label, String[] args) {
		/**
		 * TODO: will be implemented. otherwise discord/github for reporting bugs.
		 */
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.notimplementedyet");
		return true;
	}

	private boolean mmsmfDiscord(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			File file = new File("plugins/ModularMSMF/settings.yml");
			if (file.exists()) {
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				if (cfg.contains("discordID") && !cfg.getString("discordID").equals("")) {
					String discordID = cfg.getString("discordID");
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
							"coremodule.commands.mmsmf.discord.link", "_link", discordID);
					return true;
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
							"coremodule.commands.mmsmf.discord.linkmissing");
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "file should be created!"); // TODO: only for testing purposes
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.commands.arguments.toomany");
		}
		return true;
	}

	private boolean mmsmfTeamspeak(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			File file = new File("plugins/ModularMSMF/settings.yml");
			if (file.exists()) {
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				if (cfg.contains("teamspeakIP") && !cfg.getString("teamspeakIP").equals("")) {
					String teamspeakIP = cfg.getString("teamspeakIP");
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
							"coremodule.commands.mmsmf.teamspeak.ip", "_ip", teamspeakIP);
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
							"coremodule.commands.mmsmf.teamspeak.ipmissing");
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "file should be created!"); // TODO: only for testing purposes
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.commands.arguments.toomany");
		}
		return true;
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
