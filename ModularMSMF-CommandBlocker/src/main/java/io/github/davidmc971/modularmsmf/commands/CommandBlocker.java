package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import io.github.davidmc971.modularmsmf.ModularMSMF;

public class CommandBlocker extends AbstractCommand{

    public CommandBlocker(ModularMSMF plugin) {
        super(plugin);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String[] getCommandLabels() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
