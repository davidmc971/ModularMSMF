package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;

public class CommandBack extends AbstractCommand {

    private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String succesPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);

    public CommandBack(ModularMSMF plugin) {
        super(plugin);
        // TODO: Auto-generated constructor stub
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO: Auto-generated method stub
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        // TODO: Auto-generated method stub
        return null;
    }
    
}
