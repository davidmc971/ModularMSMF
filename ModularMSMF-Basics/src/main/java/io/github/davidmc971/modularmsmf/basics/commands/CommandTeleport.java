package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 *
 * @author davidmc971
 *
 */

public class CommandTeleport implements IModularMSMFCommand {

	private ModularMSMFCore plugin;
	UUID target = null;

	public CommandTeleport() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		/*FileConfiguration cfg = plugin.getDataManager().settingsyaml;
		if (cfg.get("toggle.commands.teleport").toString().equals("true")) {
		*/	if (!PermissionManager.checkPermission(sender, "teleport")) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				return true;
			}
			UUID target1, target2;
			switch (args.length) {
			case 0:
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missing_playername");
				break;
			case 1:
				if (!(sender instanceof Player)) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
					return true;
				}
				target1 = Utils.getPlayerUUIDByName(args[0]);
				Player p = Bukkit.getPlayer(target1);
				if (p != null) {
					((Player) sender).teleport(p);
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
							"commands.teleport.success", "_target", p.getName());
					return true;
				}
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
				break;
			case 2:
				if (args[0].equalsIgnoreCase(args[1])) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
							"basics.commands.teleport.error.samename");
					return true;
				}
				target1 = Utils.getPlayerUUIDByName(args[0]);
				target2 = Utils.getPlayerUUIDByName(args[1]);
				Player p1 = Bukkit.getPlayer(target1), p2 = Bukkit.getPlayer(target2);
				if (p1 != null && p2 != null) {
					// Both are online and can be teleported
					p1.teleport(p2);
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
							"basics.commands.teleport.success.toplayer", "_player", p2.getName());
					Utils.sendMessageWithConfiguredLanguage(plugin, p2, ChatFormat.INFO,
							"basics.commands.teleport.success.toyou", "_player", sender.getName());
					return true;
				}
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
						"basics.commands.teleport.error.notonline", "_player", p2.getName());
				break;
			case 3:
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
				break;
			}
		/*} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basics.nottoggledtrue");
		}*/
		return true;
	}

	@Override
	public String Label() {
		return "teleport";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "tp" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
