package io.github.davidmc971.modularmsmf.commands;

import com.google.common.eventbus.DeadEvent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.KillType;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks
 *
 */

public class CommandKill extends AbstractCommand {

	public CommandKill(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);
	private String deathPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.DEATH);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		/**
		 * @author Lightkeks "/kill me" killt den sender "/kill <target>" killt
		 *         das target "/kill all" killt alle
		 * 
		 *         > groesserr als < kleiner als
		 * 
		 *         Sollte schon klappen
		 */
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);		
		
		if (args.length == 0) {
			sender.sendMessage(errorPrefix+language.getString("general.missingarguments"));
			return true;
		}
		
		switch (args[0].toLowerCase()) { // missing prefix as text
		case "me":
			if (sender instanceof Player) {
				if (sender.hasPermission(PermissionManager.getPermission("kill_me"))) {
					Player player = ((Player) sender);
					plugin.getMainEvents().registerKilledPlayer(player, KillType.SUICIDE);
					player.setHealth(0);
				}
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
			}
			break;
			//TODO: change so one message will be created instead of every player
		case "all":
			if (sender.hasPermission(PermissionManager.getPermission("kill_all"))) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					plugin.getMainEvents().registerKilledPlayer(player, KillType.HOMOCIDE);
					player.setHealth(0);
				}
				Utils.broadcastWithConfiguredLanguageEach(plugin, ChatUtils.ChatFormat.DEATH, "event.homocide");
			}
			break;
		default:
			if (sender.hasPermission(PermissionManager.getPermission("kill"))) {
				if (sender instanceof Player) {
					boolean temp = false;
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
							plugin.getMainEvents().registerKilledPlayer(player, KillType.KILL);
							//sender.sendMessage(deathPrefix+language.getString("event.killed_player").replaceAll("_var", player.getDisplayName()));
							Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "event.killed_player", "_var", player.getDisplayName());
							player.setHealth(0);
							temp = true;
							break;
						}
					}
					if (!temp)
						sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
				}
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "kill" };
	}
}