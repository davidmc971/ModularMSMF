package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;

public class CommandStats implements IModularMSMFCommand {

    /**
     * @author Lightkeks
     */

    ModularMSMFCore plugin;

    public CommandStats() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String Label() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] Aliases() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean Enabled() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
