package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.listeners.Events;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

public class CommandBack extends AbstractCommand {

    public CommandBack(ModularMSMF plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionManager.checkPermission(sender, "back")) {
                if (args.length >= 1) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
                } else {
                    if (Events.lastLocation.containsKey(sender.getName())) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.back.success");
                        ((Entity) sender).teleport(Events.lastLocation.get(sender.getName()));
                        Events.lastLocation.remove(sender.getName());
                        return true;
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.back.error");
                    }
                }
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            }
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
