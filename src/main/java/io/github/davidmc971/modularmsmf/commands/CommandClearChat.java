package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

public class CommandClearChat extends AbstractCommand{

	public CommandClearChat(ModularMSMF plugin) {
		super(plugin);
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if(args.length == 0){
            if(sender.hasPermission(PermissionManager.getPermission("clear"))){
                int count = 0;
                while (count!=99){
                    count++;
                    Bukkit.broadcastMessage("");
                }
            if(count==99){
                Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.SUCCESS, "commands.clear.done");
            }
        } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
        }
        
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{ "clearchat" };
    }

}
