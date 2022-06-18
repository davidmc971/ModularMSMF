package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandBack implements IModularMSMFCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return backSender(sender, args);
        }
        if (args.length == 1) {
            return backPlayer(sender, args);
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
        return true;
    }

    private boolean backPlayer(CommandSender sender, String[] args) {
        if (!PermissionManager.checkPermission(sender, "back_others")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        if (target == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.offline"); // TODO: get string
                                                                                                  // exact
            return true;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                if (sender == p) {
                    return backSender(sender, args);
                }
                if (CoreEvents.lastLocation.containsKey(p.getName())) {
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                            "commands.back.other.success", "_player", p.getName());
                    Utils.sendMessageWithConfiguredLanguage(p, ChatFormat.SUCCESS,
                            "commands.back.other.done");
                    p.teleport(CoreEvents.lastLocation.get(p.getName()));
                    return true;
                }
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.back.other.error", "_player", p.getName());
                return true;
            }
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "player.offline");
        return true;
    }

    private boolean backSender(CommandSender sender, String[] args) {
        if (!PermissionManager.checkPermission(sender, "back") || sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (CoreEvents.lastLocation.containsKey(sender.getName())) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.back.success");
            ((Entity) sender).teleport(CoreEvents.lastLocation.get(sender.getName()));
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "commands.back.error");
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
