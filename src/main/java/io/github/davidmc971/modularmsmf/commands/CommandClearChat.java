package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

public class CommandClearChat extends AbstractCommand{

	public CommandClearChat(ModularMSMF plugin) {
		super(plugin);
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        UUID target = null;

        switch(args.length){
            case 0:
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
                break;
            case 1:
                if(sender.hasPermission(PermissionManager.getPermission("clear_other"))){
                    int count = 0;
                    target = Utils.getPlayerUUIDByName(args[0]);
                    //if(target == null){
                    //    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
                    //    return true;
                    //}
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                            if(sender == p){
                                while (count!=99){
                                    count++;
                                    p.sendMessage("");
                                }
                                if(count==99){
                                    Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "commands.clear.gotcleared");
                                }
                            } else
                                while (count!=99){
                                    count++;
                                    p.sendMessage("");
                                }
                                if(count==99){
                                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.clear.target", "_target", p.getDisplayName());
                                    Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "commands.clear.gotcleared");
                                }
                            } else {
                                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotonline");
                            }
                            return true;
                        }
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
                    }
                    break;
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
                break;
            }
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{ "clearchat" };
    }

}
