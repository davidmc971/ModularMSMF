package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.PlayerAvailability;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * @author Lightkeks
 *
 */

public class CommandHeal implements IModularMSMFCommand {

	String name = null;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "heal_use"))
			return ChatUtil.sendMsgNoPerm(sender);
		switch (args.length) {
			case 0:
			case 1:
				return handlePlayers(sender, command, label, args);
			default:
				Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"arguments.toomany");
				break;
		}
		return true;
	}

	private boolean handlePlayers(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (!PermissionManager.checkPermission(sender, "heal_self"))
				return ChatUtil.sendMsgNoPerm(sender);
			return healSelf(sender, command, label, args);
		}
		if (args.length == 1) {
			if (!PermissionManager.checkPermission(sender, "heal_other"))
				return ChatUtil.sendMsgNoPerm(sender);
			return healOthers(sender, command, label, args);
		}
		return true;
	}

	private boolean healOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = Util.getPlayerUUIDByName(args[0]);
		Player player = Bukkit.getPlayer(target);
		if (sender == player)
			return healSelf(sender, command, label, args);
		if (!CommandUtil.isPlayerEligible(sender, player, command, label, args, name))
			return true;
		if (!PlayerAvailability.checkPlayer(sender, target, args))
			return true;
		if (player == null)
			return true;
		Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.HEAL,
				"commands.heal.healother", "_player", player.getName());
		Util.sendMessageWithConfiguredLanguage(player, ChatFormat.HEAL,
				"commands.heal.gothealed", "_sender", sender.getName());
		double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		player.setHealth(maxHealth);
		return true;
	}

	private boolean healSelf(CommandSender sender, Command command, String label, String[] args) {
		if (!CommandUtil.isSenderEligible(sender, command, name))
			return true;
		double maxHealth = ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		((Player) sender).setHealth(maxHealth);
		Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.HEAL, "commands.heal.self");
		return true;
	}

	@Override
	public String Label() {
		return "heal";
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