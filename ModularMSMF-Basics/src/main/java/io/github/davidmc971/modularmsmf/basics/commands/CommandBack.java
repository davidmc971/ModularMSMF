package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.listeners.Events;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandBack implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandBack() {
        plugin = ModularMSMFCore.Instance();
    }

    /**
     * @author Lightkeks Fully working command You can teleport back and teleport
     *         any user back to the last location
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
        case 0:
            return backSub(sender, command, label, args);
        case 1:
            return backOthersSub(sender, command, label, args);
        default:
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
            break;
        }
        return true;
    }

    private boolean backOthersSub(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "back_others")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        if (target == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
            return true;
        } else
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                    if (sender == p) {
                        if (Events.lastLocation.containsKey(sender.getName())) {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                    "commands.back.success");
                            ((Entity) sender).teleport(Events.lastLocation.get(sender.getName()));
                            return true;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "commands.back.error");
                        }
                    } else {
                        if (Events.lastLocation.containsKey(p.getName())) {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                    "commands.back.success");
                            p.teleport(Events.lastLocation.get(p.getName()));
                            return true;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "commands.back.error");
                        }
                    }
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.playernotonline");
                }
            }
        return true;
    }

    private boolean backSub(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            return backHelp(sender, command, label, args);
        }
        if (!PermissionManager.checkPermission(sender, "back")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        if (Events.lastLocation.containsKey(sender.getName())) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.back.success");
            ((Entity) sender).teleport(Events.lastLocation.get(sender.getName()));
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.back.error");
        }
        return true;
    }

    private boolean backHelp(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("enter help here"); // TODO[epic=code needed,seq=31] help programming
        return true;
    }

    @Override
    public String Label() {
        return "back";
    }

    @Override
    public String[] Aliases() {
        return null;
    }

    @Override
    public boolean Enabled() {
        return true;
    }

}
