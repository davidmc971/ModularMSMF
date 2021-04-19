package io.github.davidmc971.modularmsmf.novaperms.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.novaperms.PermissionManager;

public class CommandNovaPerms implements IModularMSMFCommand {

    public ModularMSMFCore plugin = ModularMSMFCore.Instance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "novaperms_use")) {
            // TODO: allow a user to see their own info?
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
            return true;
        }
        // switch for subcommands
        switch (args[0].toLowerCase()) {
        case "groups":
        case "g":
            // list available groups
            novaperms_groups(sender, command, label, args);
            break;
        case "inspect":
        case "info":
        case "i":
            // inspect group or player to get detailed info
            novaperms_inspect(sender, command, label, args);
            break;
        case "assign":
        case "add":
        case "a":
            // assign a player to a group
            novaperms_assign(sender, command, label, args);
            break;
        case "permit":
        case "allow":
        case "p":
            // permit a group or player for a specific permission
            novaperms_permit(sender, command, label, args);
            break;
        case "create":
        case "new":
        case "c":
            // create a new group
            novaperms_create(sender, command, label, args);
            break;
        case "prefix":
            // set a group's prefix
            novaperms_prefix(sender, command, label, args);
            break;
        case "forbid":
        case "remove":
        case "rm":
        case "r":
            // remove a permission from a group or user
            novaperms_forbid(sender, command, label, args);
            break;
        case "delete":
        case "del":
        case "d":
            // remove player from all groups or delete a group
            novaperms_delete(sender, command, label, args);
            break;
        default:
            // unknown subcommand
            return false;
        }
        return true;
    }

    private void novaperms_groups(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_inspect(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_assign(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_permit(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_create(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_prefix(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_forbid(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    private void novaperms_delete(CommandSender sender, Command command, String label, String[] args) {
        // TODO: implementation
    }

    @Override
    public String Label() {
        return "novaperms";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "nperms", "novap", "nope", "np" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}