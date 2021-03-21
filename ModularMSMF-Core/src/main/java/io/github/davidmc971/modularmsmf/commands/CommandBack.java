package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.listeners.Events;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

public class CommandBack implements IModularMSMFCommand {
    private ModularMSMF plugin;

    public CommandBack() {
        plugin = ModularMSMF.Instance();
    }

    /**
     * @author Lightkeks
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //checks if sent command has been done by player
        if (sender instanceof Player) {
            //checks permission for sender
            if (PermissionManager.checkPermission(sender, "back")) {
                //if args more than one
                if (args.length >= 1) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
                //if args is 0
                } else {
                    //checks if sender has a lastLocation (meant player died) saved
                    if (Events.lastLocation.containsKey(sender.getName())) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.back.success");
                        ((Entity) sender).teleport(Events.lastLocation.get(sender.getName()));
                        Events.lastLocation.remove(sender.getName());
                        return true;
                    //give back if no lastLocation has been found
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.back.error");
                    }
                }
            //give back if no permission were given
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            }
        //give back if sender was console
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
        }
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{ "back" };
    }
    
}
