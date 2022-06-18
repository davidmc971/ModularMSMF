package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;

public class CommandConfigure implements IModularMSMFCommand{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // TODO code
        return false;
    }

    @Override
    public String Label() {
        return "configure";
    }

    @Override
    public String[] Aliases() {
        return new String[] {"config", "conf"};
    }

    @Override
    public boolean Enabled() {
        // TODO enable
        return false;
    }
    
}
