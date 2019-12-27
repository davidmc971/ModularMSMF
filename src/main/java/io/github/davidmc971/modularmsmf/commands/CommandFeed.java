package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * @author Lightkeks
 *
 */

public class CommandFeed extends AbstractCommand {

	public CommandFeed(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "feed" };
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		UUID target = null;

		switch (args.length) {
		case 0:
			if((sender instanceof Player)) {
				if (sender.hasPermission(PermissionManager.getPermission("feedself"))) {
					sender.sendMessage(successfulPrefix+language.getString("commands.feed.feeded"));
					((Player) sender).setSaturation(20);
					return true;
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				} 
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
			}
			break;
		case 1: 
			target = Utils.getPlayerUUIDByName(args[0]);
			if (target == null) {
				sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
				return true;
			}
			if (!sender.hasPermission(PermissionManager.getPermission("feedothers"))) {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				return true;
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
					if(sender == p) {
						sender.sendMessage(successfulPrefix+language.getString("commands.feed.feeded"));
						((Player) sender).setSaturation(20);
					} else {
						sender.sendMessage(successfulPrefix+language.getString("commands.feed.feededperson").replaceAll("_player", p.getName()));
						p.sendMessage(successfulPrefix+language.getString("commands.feed.othersfeeded").replaceAll("_sender", sender.getName()));
						p.setSaturation(20);
					}
					return true;
				}
			}
			sender.sendMessage(noPermPrefix+language.getString("general.playernotfound"));
			break;
		default:
			sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			break;
		}
		return true;
	}
}