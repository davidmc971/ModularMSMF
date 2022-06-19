package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "fly_use")) {
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
            case 1:
                handleFlight(sender, command, label, args);
                break;
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "arguments.toomany");
                break;
        }
        return true;
    }

    private void handleFlight(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args.length == 0) {
            if (!PermissionManager.checkPermission(sender, "fly_self")) {
                ChatUtil.sendMsgNoPerm(sender);
            }
            toggleFlightSender(sender, command, label, args);
        }
        if (args.length == 1) {
            if (!PermissionManager.checkPermission(sender, "fly_others")) {
                ChatUtil.sendMsgNoPerm(sender);
            }
            toggleFlightPlayer(sender, command, label, args);
        }
    }

    private boolean toggleFlightPlayer(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = Util.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
            return false;
        }
        if (sender == player) {
            return toggleFlightSender(sender, command, label, args);
        }
        for (Player plron : Bukkit.getOnlinePlayers()) {
            if (plron == player) {
                toggleFlight(sender, command, label, args);
                return true;
            }
        }
        for (OfflinePlayer plroff : Bukkit.getOfflinePlayers()) {
            if (plroff.getName() == args[0]) {
                toggleFlight(sender, command, label, args);
                return true;
            }
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.offline", "_player", args[0]);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
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
                    "commands.fly.set_true");
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
