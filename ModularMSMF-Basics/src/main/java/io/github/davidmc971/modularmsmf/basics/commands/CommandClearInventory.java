package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandClearInventory implements IModularMSMFCommand {

    ModularMSMFCore plugin;

    public CommandClearInventory() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "clearinv_use")) {
            ChatUtils.sendMsgNoPerm(sender);
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
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                "coremodule.commands.arguments.toomany");
        return true;
    }

    private boolean clearSender(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "clearinv_self")) {
            ChatUtils.sendMsgNoPerm(sender);
            return false;
        }
        if (((Player) sender).getInventory().isEmpty()) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.clearinv.isEmpty.self");
            return true;
        }
        ((Player) sender).getInventory().clear();
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.clearinv.gotEmptied.self");
        return true;
    }

    private boolean clearPlayer(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "clearinv_others")) {
            ChatUtils.sendMsgNoPerm(sender);
            return false;
        }
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (player == sender) {
            return clearSender(sender, command, label, args);
        }
        if (player.getInventory().isEmpty()) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.clearinv.isEmpty.others", "_player", player.getName());
            return true;
        }
        player.getInventory().clear();
        Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS,
                "basicsmodule.commands.clearinv.gotEmptied.self", "_player", player.getName());
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.clearinv.gotEmptied.others", "_player", player.getName());
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
