package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.listeners.BasicEvents;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.basics.ModularMSMFBasics;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandKill implements IModularMSMFCommand {

	private BasicEvents basicEvents;

	public CommandKill() {
		basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "kill")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (args.length == 0) {
			return killHelp(sender, command, label, args);
		}
		if (args.length == 1) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (args[0].toLowerCase().equals(sender.getName().toLowerCase())) {
					return killMeSub(sender, command, label, args);
				}
				if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
					basicEvents.registerKilledPlayer(player, KillType.KILL);
					Utils.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "basicsmodule.events.killed",
							"_var", player.getName());
					player.setHealth(0);
					return true;
				}
			}
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
			return true;
		}
		if (args.length <= 2) {
			Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "basicsmodule.arguments.toomany");
			return true;
		}
		return true;
	}

	private boolean killHelp(CommandSender sender, Command command, String label, String[] args) {
		// sender.sendMessage("/kill (me/all/<target>)");
		sender.sendMessage("/kill me - suicide");
		sender.sendMessage("/kill all - all players die");
		sender.sendMessage("/kill <target> - <target> dies");
		return true;
	}

	private boolean killMeSub(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "kill_me")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		basicEvents.registerKilledPlayer(((Player) sender), KillType.SUICIDE);
		Utils.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "basicsmodule.events.suicide", "_var",
				sender.getName());
		((Player) sender).setHealth(0);
		return true;
	}

	@Override
	public String Label() {
		return "kill";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}