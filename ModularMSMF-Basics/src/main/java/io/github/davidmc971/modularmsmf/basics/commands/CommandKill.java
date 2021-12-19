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

	private ModularMSMFCore plugin;
	private DeathListener deathListener;

	public CommandKill() {
		plugin = ModularMSMFCore.Instance();
		deathListener = ModularMSMFBasics.Instance().getDeathListener();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "kill")) {
			ChatUtils.sendMsgNoPerm(sender);
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
		if (sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "kill_me")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		Player player = ((Player) sender);
		deathListener.registerKilledPlayer(player, KillType.SUICIDE);
		Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "basicsmodule.events.suicide", "_var",
				player.getName());
		player.setHealth(0);
		return true;
	}

	private boolean killAllSub(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "kill_all")){
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			deathListener.registerKilledPlayer(player, KillType.HOMOCIDE);
			player.setHealth(0);
		}
		Utils.broadcastWithConfiguredLanguageEach(plugin, ChatUtils.ChatFormat.DEATH, "basicsmodule.events.homocide");
		return true;
	}

	private boolean killDefault(CommandSender sender, Command command, String label, String[] args) {
		boolean temp = false;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
				deathListener.registerKilledPlayer(player, KillType.KILL);
				Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.DEATH, "basicsmodule.events.killed",
						"_var", player.getName());
				player.setHealth(0);
				temp = true;
				break;
			}
		}
		if (!temp) {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
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