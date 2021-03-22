package io.github.davidmc971.modularmsmf.novaperms.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandNovaPerms implements IModularMSMFCommand {

    public ModularMSMFCore plugin = ModularMSMFCore.Instance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // TODO structure for using novaperms command
        /*
        TODO NovaPerms list ->
                groups (list groups)
                perms (for selecting group or player)
                player (lists player in a group)
            set ->
                user (set permission node for specified player)
                group (set a new group)
            remove ->
                user (remove permission node from specified player)
                node (remove permission node from a group)
                group (remove group)
            add ->
                node (adds a permission node to a group)
                user (adds a permission node to specified user)
        */
        // checks if perms are set
        if(PermissionManager.checkPermission(sender, "novaperms.use")){
            //switch for subcommands
            switch(args[0].toLowerCase()){
                case "list":
                //TODO list subcommand for groups or perms in a group or for a player
                switch(args[0].toLowerCase()){
                    default:
                    //if any written stuff is not applicable to the cases below
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "novaperms.invalidargument");
                    break;
                    case "groups":
                    //TODO list all groups
                    break;
                    
                }
            }

        } else {
            //TODO if no perms
        }
        return false;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{ "novaperms", "np" };
    }
    
}
