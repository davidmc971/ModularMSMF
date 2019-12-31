package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.data.Language;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;
import net.md_5.bungee.api.ChatColor;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandTeleport extends AbstractCommand {
	
	public CommandTeleport(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);

	
	/**
	 * TODO: implement and finishing this
	 * TODO: whole rewrite for logical reasons
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		//Player player = null;

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if (!(sender instanceof Player)) {
			//TODO: console should be able to tp players to other players or waypoints
			sender.sendMessage(errorPrefix+language.getString("general.noconsole"));
		}

		if (sender.hasPermission(PermissionManager.getPermission("teleport"))) {
			if (args.length == 0) {
				sender.sendMessage(errorPrefix+language.getString("general.missing_playername"));
			}
			if (args.length == 1) {
				String Name = args[0];
				if (Bukkit.getPlayerExact(Name) != null) {
					Player target = (Player) Bukkit.getPlayerExact(Name);
					((Player) sender).teleport(target);
					//TODO: changing sender text to language strings
					sender.sendMessage("[ModularMSMF] Erfolgreich zu " + target.getDisplayName() + " teleportiert!");
					
				} else {
					//TODO: changing sender text to language strings
					sender.sendMessage("[ModularMSMF] " + args[0] + " ist nicht online!");
				}
			} else if (args.length >= 2) {
				//TODO: changing sender text to language strings
				sender.sendMessage("[ModularMSMF] Zu viele Argumente!");
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "teleport" };
	}
}
