package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks
 * Fully working command
 */

public class CommandHeal extends AbstractCommand {

	public CommandHeal(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//target is always null unless target is online
		UUID target = null;
		if(sender instanceof Player){
			switch (args.length) {
			case 0:
				if (PermissionManager.checkPermission(sender, "healself")) {
					((Player) sender).setHealth(20);
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL, "commands.heal.healself");
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
				break;
			case 1:
				target = Utils.getPlayerUUIDByName(args[0]);
				if (PermissionManager.checkPermission(sender, "healother")) {
					if (target == null) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
						return true;
					} else
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
								if(sender == p) {
									Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL, "commands.heal.healself");
									((Player)sender).setHealth(20);
								} else {
									Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HEAL, "commands.heal.healother", "_player", p.getDisplayName());
									Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.HEAL, "commands.heal.gothealed", "_sender", sender.getName());
									p.setHealth(20);
								}
							} else {
								Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotonline");
							}
						}
					break;
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			default:
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
				break;
			}
			
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "heal" };
	}
}