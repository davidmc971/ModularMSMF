package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.HashMap;
import java.util.Set;

// import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandChannels implements IModularMSMFCommand {

    public static final HashMap<String, String> channelsListPrivate = new HashMap<>();
    public static final HashMap<String, String> channelsListPublic = new HashMap<>();

    int i = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            channelHelp(sender, command, label, args);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                channelHelp(sender, command, label, args);
                break;
            case "join":
                channelJoin(sender, command, label, args);
                break;
            case "create":
                channelCreate(sender, command, label, args); //automatically join?
                break;
            case "remove":
                channelRemove(sender, command, label, args);
                break;
            case "list":
                channelList(sender, command, label, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private void channelJoin(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("dont forget private / public");
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("private")) {
                joinPrivate(sender, command, label, args);
            }
            if (args[1].equalsIgnoreCase("public")) {
                joinPublic(sender, command, label, args);
            }
            sender.sendMessage("wrong arg");
        }
    }

    private void joinPublic(CommandSender sender, Command command, String label, String[] args) {
    }

    private void joinPrivate(CommandSender sender, Command command, String label, String[] args) {
    }

    private void channelList(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length < 1 ? "all" : args[0].toLowerCase()) {
            case "public":
                listPublic(sender, command, label, args);
                break;
            case "private":
                listPrivate(sender, command, label, args);
                break;
            case "all":
                listAll(sender, command, label, args);
                break;
            default:
                listAll(sender, command, label, args);
                break;
        }
    }

    private void listPublic(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("List of all public channels: " + printSet(channelsListPublic.keySet()));
    }

    private void listPrivate(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + printSet(channelsListPrivate.keySet()));
    }

    private void listAll(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_list_all")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + printSet(channelsListPrivate.keySet()));
        sender.sendMessage("List of all public channels: " + printSet(channelsListPublic.keySet()));
    }

    private String printSet(Set<String> keySet) {
        String result = new String();
        for (String str : keySet) {
            result = result.concat(str.concat(", "));
        }
        if (result.isBlank()) {
            return result;
        }
        return result.substring(0, result.length() - 2);
    }

    private boolean channelRemove(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("test");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return true;
        }
        for (String key : channelsListPublic.keySet()) {
            if (args[1].equals(key)) {
                channelsListPublic.remove(key);
                sender.sendMessage("got removed");
                sender.sendMessage(args[1] + " is now gone // " + channelsListPublic.get(args[1]));
                i--;
                return true;
            }
        }
        for (String key : channelsListPrivate.keySet()) {
            if (args[1].equals(key)) {
                channelsListPrivate.remove(key);
                sender.sendMessage("got removed");
                sender.sendMessage(args[1] + " is now gone // " + channelsListPrivate.get(args[1]));
                i--;
                return true;
            }
        }
        sender.sendMessage(args[1] + " does not exist");
        // removes created channels
        return true;
    }

    private void channelCreate(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
        }
        if (args.length == 2) {
            sender.sendMessage("status channel? private oder public!");
        }
        if (args[2].equalsIgnoreCase("public")) {
            createPublic(sender, args);
        }
        if (args[2].equalsIgnoreCase("private")) {
            createPrivate(sender, args);
        }
        sender.sendMessage("nope, only private or public");
    }

    private void createPrivate(CommandSender sender, String[] args) {
        if (channelsListPrivate.containsKey(args[1].toLowerCase())) {
            sender.sendMessage("nope, exists already");
            return;
        }
        channelsListPrivate.put(args[1], sender.getName());
        sender.sendMessage("done creating " + channelsListPrivate.get(args[1].toLowerCase()) + " as "
                + channelsListPrivate.containsValue(args[2].toLowerCase()));
        i++;
    }

    private void createPublic(CommandSender sender, String[] args) {
        if (channelsListPublic.containsKey(args[1].toLowerCase())) {
            sender.sendMessage("nope, exists already");
            return;
        }
        channelsListPublic.put(args[1], sender.getName());
        sender.sendMessage("done creating " + channelsListPrivate.get(args[1].toLowerCase()) + " as "
                + channelsListPrivate.containsValue(args[2].toLowerCase()));
        i++;
    }

    private void channelHelp(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("help here");
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
