package io.github.davidmc971.modularmsmf.basics.commands;

import com.google.common.base.Objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;

/**
 * @author Lightkeks Defines channels for users, which want private
 *         conversations or admin-related channels/ moderator-related channels/
 *         etc.
 */

public class CommandChannels implements IModularMSMFCommand {
    private ModularMSMFCore plugin;

    public CommandChannels() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        return false;
    }

    @Override
    public String Label() {
        return "channel";
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
