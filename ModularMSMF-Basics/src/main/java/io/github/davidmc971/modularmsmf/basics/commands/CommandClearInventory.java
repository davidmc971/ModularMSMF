package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

public class CommandClearInventory implements IModularMSMFCommand {

    ModularMSMFCore plugin;

    public CommandClearInventory() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "clearinv_use")) {
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            clearSender(sender, command, label, args);
            return true;
        }
        if (args.length == 1) {
            clearPlayer(sender, command, label, args);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "arguments.toomany");
        return true;
    }

    private boolean clearSender(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "clearinv_self") || sender instanceof ConsoleCommandSender) {
            ChatUtil.sendMsgNoPerm(sender);
            return false;
        }
        if (((Player) sender).getInventory().isEmpty()) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.clearinv.isEmpty.self");
            return true;
        }
        ((Player) sender).getInventory().clear();
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.clearinv.gotEmptied.self");
        return true;
    }

    private boolean clearPlayer(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "clearinv_others")) {
            ChatUtil.sendMsgNoPerm(sender);
            return false;
        }
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (player == sender) {
            return clearSender(sender, command, label, args);
        }
        if (player.getInventory().isEmpty()) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.clearinv.isEmpty.others", "_player", player.getName());
            return true;
        }
        player.getInventory().clear();
        Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                "commands.clearinv.gotEmptied.self", "_player", player.getName());
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.clearinv.gotEmptied.others", "_player", player.getName());
        return true;
    }

    @Override
    public String Label() {
        return "clearinventory";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "ci, clearinv" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}
