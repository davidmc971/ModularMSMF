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
        if(PermissionManager.checkPermission(sender, "clear_command")){
            UUID target = null;
			switch (args.length){
                default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
                break;

                case 0:
                if(PermissionManager.checkPermission(sender, "clear_all")){
                    int count = 0;
                    while (count!=99){
                        count++;
                        Bukkit.broadcastMessage("");
                    }
                    Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.SUCCESS, "commands.clear.done");
                } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
                }
                break;

                case 1:
                if(PermissionManager.checkPermission(sender, "clear_target")){
                    int count = 0;
                    target = Utils.getPlayerUUIDByName(args[0]);
                    if (target == null) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
                        return true;
                    } else {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.clear.target", "_target", p.getDisplayName());
                                while (count <= 99){
                                    count++;
                                    p.sendMessage("");
                                }
                                Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "commands.clear.gotcleared");
                                return true;
                            }
                        }
                    }
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
                }
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
        }
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{ "clearchat" };
    }

}
