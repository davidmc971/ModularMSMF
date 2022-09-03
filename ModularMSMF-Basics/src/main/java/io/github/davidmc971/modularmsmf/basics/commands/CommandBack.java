package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;
import io.github.davidmc971.modularmsmf.basics.util.PlayerAvailability;

/**
 * <h4>Basic command for return to last place</h4>
 * Usage: /back 'optional:username'
 *
 * @author Lightkeks (Alex)
 */

public class CommandBack implements IModularMSMFCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (!PermissionManager.checkPermission(sender, "back") || sender instanceof ConsoleCommandSender)
                    return ChatUtil.sendMsgNoPerm(sender);
                return backSender(sender, args);
            case 1:
                if (!PermissionManager.checkPermission(sender, "back_others"))
                    return ChatUtil.sendMsgNoPerm(sender);
                return backPlayer(sender, command, args);
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
                break;
        }
        return true;
    }

    private boolean backPlayer(CommandSender sender, Command command, String[] args) {
        UUID target = Util.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);
        if (sender == player)
            return backSender(sender, args);
        if (!PlayerAvailability.checkPlayer(sender, target, args))
            return true;
        if (player == null)
            return true;
        if (CoreEvents.lastLocation.containsKey(player.getName())) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.back.other.success", "_player", player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                    "commands.back.other.done");
            player.teleport(CoreEvents.lastLocation.get(player.getName()));
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "commands.back.other.error", "_player", player.getName());
        return true;
    }

    private boolean backSender(CommandSender sender, String[] args) {
        if (CoreEvents.lastLocation.containsKey(sender.getName())) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.back.success");
            ((Entity) sender).teleport(CoreEvents.lastLocation.get(sender.getName()));
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
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