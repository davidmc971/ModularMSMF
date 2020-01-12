package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.ModularMSMF;

public class CommandBack extends AbstractCommand {

    public CommandBack(ModularMSMF plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //TODO: write code in here
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{ "back" };
    }
    
}
