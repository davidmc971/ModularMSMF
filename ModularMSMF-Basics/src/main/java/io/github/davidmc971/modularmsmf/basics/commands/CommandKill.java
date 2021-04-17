package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.basics.ModularMSMFBasics;
import io.github.davidmc971.modularmsmf.basics.listeners.DeathListener;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandKill implements IModularMSMFCommand {

	private ModularMSMFCore MMSMFCore;
	private DeathListener deathListener;

	public CommandKill() {
		MMSMFCore = ModularMSMFCore.Instance();
		deathListener = ModularMSMFBasics.Instance().getDeathListener();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "kill")) {
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.CONSOLE, "general.nopermission");
			return true;
		}
		if (args.length == 0) {
			return killHelp(sender,command,label,args);
		}
		switch (args[0].toLowerCase()) {
		case "me":
			return killMeSub(sender, command, label, args);
		case "all":
			return killAllSub(sender, command, label, args);
		default:
			return killDefault(sender, command, label, args);
		}
	}

	private boolean killHelp(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("/kill (me/all/<target>)");
		sender.sendMessage("/kill me - suicide");
		sender.sendMessage("/kill all - all players die");
		sender.sendMessage("/kill <target> - <target> dies");
		return false;
	}

	private boolean killMeSub(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.CONSOLE, "general.noconsole");
			return true;
		}
		if (!PermissionManager.checkPermission(sender, "kill_me")) {
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.NOPERM, "general.nopermission");
			return true;
		}
		Player player = ((Player) sender);
		deathListener.registerKilledPlayer(player, KillType.SUICIDE);
		Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatFormat.DEATH, "event.suicide", "_var",
				player.getName());
		player.setHealth(0);
		return true;
	}

	private boolean killAllSub(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender){
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.CONSOLE, "general.noconsole");
			return true;
		}
		if (!PermissionManager.checkPermission(sender, "kill_all")) {
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.NOPERM, "general.nopermission");
			return true;
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			deathListener.registerKilledPlayer(player, KillType.HOMOCIDE);
			player.setHealth(0);
		}
		Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatUtils.ChatFormat.DEATH, "event.homocide");
		return true;
	}

	private boolean killDefault(CommandSender sender, Command command, String label, String[] args) {
		boolean temp = false;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
				deathListener.registerKilledPlayer(player, KillType.KILL);
				Utils.broadcastWithConfiguredLanguageEach(MMSMFCore, ChatFormat.DEATH, "event.killed_player",
						"_var", player.getName());
				player.setHealth(0);
				temp = true;
				break;
			}
		}
		if (!temp) {
			Utils.sendMessageWithConfiguredLanguage(MMSMFCore, sender, ChatFormat.ERROR, "general.playernotfound");
		}
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