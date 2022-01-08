package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 * 
 * Flying command
 */

public class CommandFly implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "fly_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
            case 1:
                handleFlight(sender, command, label, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                break;
        }
        return true;
    }

    private void handleFlight(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args.length == 0) {
            if (!PermissionManager.checkPermission(sender, "fly_self")) {
                ChatUtils.sendMsgNoPerm(sender);
            }
            toggleFlightSender(sender, command, label, args);
        }
        if (args.length == 1) {
            if (!PermissionManager.checkPermission(sender, "fly_others")) {
                ChatUtils.sendMsgNoPerm(sender);
            }
            toggleFlightPlayer(sender, command, label, args);
        }
    }

    private boolean toggleFlightPlayer(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (!CommandUtil.isPlayerEligible(sender, player, command)) {
            return false;
        }
        if (sender == player) {
            return toggleFlightSender(sender, command, label, args);
        }
        if (!player.getAllowFlight()) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_ON,
                    "basicsmodule.commands.fly.others.set_true", "_player", player.getName());
            Utils.sendMessageWithConfiguredLanguage(player, ChatFormat.FLY_ON,
                    "basicsmodule.commands.fly.set_true");
            player.setAllowFlight(true);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_OFF,
                "basicsmodule.commands.fly.others.set_false", "_player", player.getName());
        Utils.sendMessageWithConfiguredLanguage(player, ChatFormat.FLY_OFF,
                "basicsmodule.commands.fly.set_false");
        player.setAllowFlight(false);
        return true;
    }

    private boolean toggleFlightSender(CommandSender sender, Command command, String label, String[] args) {
        if (!CommandUtil.isSenderEligible(sender, command)) {
            return false;
        }
        if (!((Player) sender).getAllowFlight()) {
            ((Player) sender).setAllowFlight(true);
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_ON,
                    "basicsmodule.commands.fly.set_true");
            return true;
        }
        ((Player) sender).setAllowFlight(false);
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.FLY_OFF,
                "basicsmodule.commands.fly.set_false");
        return true;
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
