package io.github.davidmc971.modularmsmf.novaperms.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import net.kyori.adventure.text.Component;

public class CommandNovaPerms implements IModularMSMFCommand {

    public ModularMSMFCore plugin = ModularMSMFCore.Instance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "novaperms.use")) {
            // TODO: allow a user to see their own info?
            sender.sendMessage(Component.text("No permission."));
            return true;
        }

        for (String string : args) {
            string = string.toLowerCase();
        }
        // switch for subcommands
        switch (args[0]) {
        case "groups":
        case "g":
            // list available groups
            break;
        case "inspect":
        case "info":
        case "i":
            // inspect group or player to get detailed info
            break;
        case "assign":
        case "add":
        case "a":
            // assign a player to a group
            break;
        case "permit":
        case "allow":
        case "p":
            // permit a group or player for a specific permission
            break;
        case "create":
        case "new":
        case "c":
            // create a new group
            break;
        case "prefix":
            // set a group's prefix
            break;
        case "forbid":
        case "remove":
        case "rm":
        case "r":
            // remove a permission from a group or user
            break;
        case "delete":
        case "del":
        case "d":
            // remove player from all groups or delete a group
            break;
        }
        return false;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[] { "novaperms", "nperms", "novap", "nope", "np" };
    }

}