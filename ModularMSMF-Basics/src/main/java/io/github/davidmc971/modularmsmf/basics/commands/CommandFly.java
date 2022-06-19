package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.PlayerAviability;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * Flying command
 *
 * @author Lightkeks
 */

public class CommandFly implements IModularMSMFCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "fly_use")) {
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
                toggleFlightSender(sender, command, label, args);
                break;
            case 1:
                if (!PermissionManager.checkPermission(sender, "fly_others")) {
                    ChatUtil.sendMsgNoPerm(sender);
                    return true;
                }
                toggleFlightPlayer(sender, command, label, args);
                break;
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "arguments.toomany");
                break;
        }
        return true;
    }

    private boolean toggleFlightPlayer(CommandSender sender, Command command, String label, String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (!PlayerAviability.isPlayerExistant(sender, player, command, args)) {
            return true;
        }
        if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
            return false;
        }
        if (sender == player) {
            toggleFlightSender(sender, command, label, args);
            return true;
        }
        toggleFlight(sender, command, label, args);
        return true;
    }

    private boolean toggleFlightSender(CommandSender sender, Command command, String label, String[] args) {
        if (!CommandUtil.isSenderEligible(sender, command)) {
        return false;
        }
        if (!((Player) sender).getAllowFlight()) {
            ((Player) sender).setAllowFlight(true);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_ON,
                    "commands.fly.settrue");
            return true;
        }
        ((Player) sender).setAllowFlight(false);
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_OFF,
                "commands.fly.setfalse");
        return true;
    }

    private void toggleFlight(CommandSender sender, Command command, String label, String[] args) {
        UUID target = Util.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (!player.getAllowFlight()) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_ON,
                    "commands.fly.others.settrue", "_player", player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.FLY_ON,
                    "commands.fly.settrue");
            player.setAllowFlight(true);
            return;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_OFF,
                "commands.fly.others.setfalse", "_player", player.getName());
        Util.sendMessageWithConfiguredLanguage(player, ChatFormat.FLY_OFF,
                "commands.fly.setfalse");
        player.setAllowFlight(false);
        return;
    }

    @Override
    public String Label() {
        return "fly";
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
