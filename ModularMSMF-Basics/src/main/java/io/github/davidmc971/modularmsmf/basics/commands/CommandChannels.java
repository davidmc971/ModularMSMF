package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandChannels implements IModularMSMFCommand {

    public static final HashMap<String, String> channelsList = new HashMap<String, String>();
    public static final HashMap<String, String> channelsUse = new HashMap<String, String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // checks permission
        if (!PermissionManager.checkPermission(sender, "channels_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) { // same as return channelsHelp
            // help for "/channel"
            return channelsHelp(sender, command, label, args);
        }
        switch (args[0].toLowerCase()) {
            case "help":
                return channelsHelp(sender, command, label, args); // same as args[0]
            case "set":
                return channelsSet(sender, command, label, args);
            case "create":
                return channelsCreate(sender, command, label, args);
            case "remove":
                return channelsRemove(sender, command, label, args);
            case "list":
                return channelsList(sender, command, label, args);
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private boolean channelsList(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length < 1 ? "all" : args[0].toLowerCase()) {
            case "public":
                return listPublic(sender, command, label, args);
            case "private":
                return listPrivate(sender, command, label, args);
            case "all":
                return listAll(sender, command, label, args);
            default:
                return listAll(sender, command, label, args);
        }
    }

    private boolean listPublic(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    private boolean listPrivate(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    private boolean listAll(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_list_all")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        sender.sendMessage("List of all channels: " + channelsList.toString());
        return true;
    }

    private boolean channelsRemove(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("test");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return true;
        }
        if (channelsList.containsKey(args[1])) {
            sender.sendMessage(args[1] + " has been removed");
            channelsList.remove(args[1]);
            return true;
        }
        sender.sendMessage(args[1] + " does not exist");
        // removes created channels
        return true;
    }

    private boolean channelsCreate(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
            return true;
        }
        if (args.length == 2) {
            sender.sendMessage("status channel? private oder public!");
            return true;
        }
        if (args[2].equalsIgnoreCase("private") || args[2].equalsIgnoreCase("public")) {
            if (!channelsList.containsValue(args[1])) {
                sender.sendMessage(args[1] + " has been created");
                channelsList.put(args[1], args[2]);
                sender.sendMessage("channeldebug: " + channelsList.toString()/* .split("[\\]")[0] */);
                return true;
            }
            sender.sendMessage(args[1] + " is already created as " + channelsList.values().contains(args[1]));
            return true;
        }
        sender.sendMessage("nope, only private or public");
        return true;
    }

    private boolean channelsSet(CommandSender sender, Command command, String label, String[] args) {
        String prefix = channelsUse.get(((Player) sender).getName().toString());
        if (args.length == 1) {
            sender.sendMessage("pls help");
            return true;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase(channelsUse.get((((Player) sender).getName().toString())))) {
                sender.sendMessage("before putting you into list");
                sender.sendMessage("list: " + channelsUse.get(((Player) sender).getName().toString())
                        + " <-- key/username/sender");
                sender.sendMessage(
                        "list: " + channelsUse.containsValue(channelsUse.get(((Player) sender).getName()).toString())
                                + " <-- value/channelname");
                channelsUse.put(((Player) sender).getName().toString(), channelsList.get(args[1]));
                sender.sendMessage(prefix + " done putting sender to channel "
                        + channelsUse.containsValue(channelsUse.get(((Player) sender).getName().toString())));
                return true;
            }
            if(args[1].equals(channelsList.containsValue(channelsList.get(args[1])))){
                sender.sendMessage("is private");
                return true;
            }
            sender.sendMessage("channel not existant or private");
            return true;
        }
        sender.sendMessage("too many args");
        return true;
    }

    private boolean channelsHelp(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

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
