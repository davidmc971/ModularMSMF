package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks Defines channels for users, which want private
 *         conversations or admin-related channels/ moderator-related channels/
 *         etc.
 */

public class CommandChannels implements IModularMSMFCommand {
    private ModularMSMFCore plugin;

    public CommandChannels() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // checks permission
        if (PermissionManager.checkPermission(sender, "channels_use")) {
            if (args.length == 0) { // same as return channelsHelp
                // help for "/channel"
                return channelsHelp(sender, label, args);
            }
            switch ((args.length < 1) ? "help" : args[0].toLowerCase()) {
            case "help":
                return channelsHelp(sender, label, args); // same as args[0]
            case "set":
                return channelsSet(sender, label, args);
            case "create":
                return channelsCreate(sender, label, args);
            case "remove":
                return channelsRemove(sender, label, args);
            case "list":
                return channelsList(sender, label, args);
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
                break;
            }
            return true;
        } else {
            // return statement if no permission is given
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
        }
        return true;
    }

    private boolean channelsList(CommandSender sender, String label, String[] args) {
        switch (args.length < 1 ? "all" : args[0].toLowerCase()) {
        case "public":
            return listPublic(sender, label, args);
        case "private":
            return listPrivate(sender, label, args);
        case "all":
            return listAll(sender, label, args);
        default:
            return listAll(sender, label, args);
        }
    }

    private boolean listPublic(CommandSender sender, String label, String[] args) {
        return false;
    }

    private boolean listPrivate(CommandSender sender, String label, String[] args) {
        return false;
    }

    private boolean listAll(CommandSender sender, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_list_all")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        channelsList.forEach(element -> {
            sender.sendMessage(element);
        });
        return true;
    }

    private boolean channelsRemove(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("test");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return true;
        }
        if (channelsList.contains(args[1])) {
            sender.sendMessage(args[1] + " has been removed");
            channelsList.remove(args[1]);
        } else {
            sender.sendMessage(args[1] + " does not exist");
        }
        // removes created channels
        return true;
    }

    private boolean channelsCreate(CommandSender sender, String label, String[] args) {
        // FIXME: creates a channel and moves you in automatically
        if (args.length == 0) {
            sender.sendMessage("test");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
            return true;
        }
        if (!channelsList.contains(args[1])) {
            sender.sendMessage(args[1] + " has been created");
            channelsList.add(args[1]);
        } else {
            sender.sendMessage(args[1] + " is already created");
        }
        channelsList.add(args[1]);
        return true;
    }

    private boolean channelsSet(CommandSender sender, String label, String[] args) {
        // FIXME: Set the channel type youre currently in to chosen type
        // ENUM: PUBLIC, PRIVATE, ADMIN, MOD, SUPPORT
        return true;
    }

    private boolean channelsHelp(CommandSender sender, String label, String[] args) {
        return true;
    }

    public HashSet<String> channelsList = new HashSet<String>();

    @Override
    public String Label() {
        return "channel";
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
