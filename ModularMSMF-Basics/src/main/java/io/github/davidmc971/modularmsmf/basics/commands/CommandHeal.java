package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 *
 */

public class CommandHeal implements IModularMSMFCommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "heal_use")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		switch (args.length) {
			case 1:
				handlePlayers(sender, command, label, args);
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
						"basicsmodule.commands.arguments.toomany");
				break;
		}
		return true;
	}

	private void handlePlayers(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			healSelf(sender, command, label, args);
		}
		if (args.length == 1) {
			healOthers(sender, command, label, args);
		}
	}

	private boolean healOthers(CommandSender sender, Command command, String label, String[] args) {
		UUID target = null;
		target = Utils.getPlayerUUIDByName(args[0]);
		Player player = Bukkit.getPlayer(target);
		if (!PermissionManager.checkPermission(sender, "healother")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (!CommandUtil.isPlayerEligible(sender, player, command)) {
			return false;
		}
		if (sender == player) {
			return healSelf(sender, command, label, args);
		}
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.HEAL,
				"basicsmodule.commands.heal.healother", "_player", player.getName());
		Utils.sendMessageWithConfiguredLanguage(player, ChatFormat.HEAL,
				"basicsmodule.commands.heal.gothealed", "_sender", sender.getName());
		double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		player.setHealth(maxHealth);
		return true;
	}

	private boolean healSelf(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionManager.checkPermission(sender, "healself")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}
		if (!CommandUtil.isSenderEligible(sender, command)) {
			return false;
		}
		Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.HEAL, "basicsmodule.commands.heal.self");
		double maxHealth = ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		((Player) sender).setHealth(maxHealth);
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