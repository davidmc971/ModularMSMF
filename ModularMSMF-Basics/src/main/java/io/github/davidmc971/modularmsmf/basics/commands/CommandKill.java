package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

public class CommandKill implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!PermissionManager.checkPermission(sender, "kill")){
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        switch(args.length){
            case 0:
            case 1:
            case 2:
            default:
        }

    return true;
    }

    @Override
    public String Label() {
        return "kill";
    }

    @Override
    public String[] Aliases() {
        return null;
    }

    @Override
    public boolean Enabled() {
        return false;
    }
    
}
