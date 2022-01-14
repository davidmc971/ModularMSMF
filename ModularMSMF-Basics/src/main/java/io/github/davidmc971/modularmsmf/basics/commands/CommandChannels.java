package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandChannels implements IModularMSMFCommand {

    public static final HashMap<String, String> setChannelUsr = new HashMap<String, String>();
    public static final HashSet<String> usrChPriv = new HashSet<String>();
    public static final HashSet<String> usrChPub = new HashSet<String>();
    public static final HashSet<String> defaultChannels = new HashSet<String>() {
        private static final long serialVersionUID = 1L;
        {
            // only for main channels to obtain
            add("admin"); /* /channel set admin */
            add("moderator"); /* /channel set moderator */
            add("support"); /* /channel set support */
            add("default"); /* /channel set default */
        }
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            channelHelp(sender);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help":
                channelHelp(sender);
                break;
            case "join":
                channelJoin(sender, args);
                break;
            case "create":
                channelCreate(sender, args); // automatically join?
                break;
            case "remove":
                channelRemove(sender, args);
                break;
            case "list":
                channelList(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private void channelJoin(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("dont forget private / public");
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("private")) {
                joinPrivate(sender, args);
            }
            if (args[1].equalsIgnoreCase("public")) {
                joinPublic(sender, args);
            }
            sender.sendMessage("wrong arg");
        }
    }

    private void joinPublic(CommandSender sender, String[] args) {
    }

    private void joinPrivate(CommandSender sender, String[] args) {
    }

    private void channelList(CommandSender sender, String[] args) {
        if (args.length == 1) {
            listAll(sender, args);
            return;
        }
        switch (args[1].toLowerCase()) {
            case "this":
            case "current":
                listCurrent(sender, args);
                break;
            case "public":
                listPublic(sender, args);
                break;
            case "private":
                listPrivate(sender, args);
                break;
            case "all":
                listAll(sender, args);
                break;
            default:
                sender.sendMessage("wrong arg");
                break;
        }
    }

    private void listCurrent(CommandSender sender, String[] args) {
    }

    private void listPublic(CommandSender sender, String[] args) {
        sender.sendMessage("List of all public channels: " + CommandUtil.printSet(usrChPriv));
    }

    private void listPrivate(CommandSender sender, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + CommandUtil.printSet(usrChPriv));
    }

    private void listAll(CommandSender sender, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_list_all")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + CommandUtil.printSet(usrChPriv));
        sender.sendMessage("List of all public channels: " + CommandUtil.printSet(usrChPub));
        sender.sendMessage("List of all default channels: " + CommandUtil.printSet(defaultChannels));
    }

    private boolean channelRemove(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("test");
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return true;
        }
        for (String key : usrChPub) {
            if (usrChPub.isEmpty()) {
                sender.sendMessage("no channel to remove");
                return true;
            }
            if (args[1].equals(key)) {
                sender.sendMessage(key + "got removed");
                usrChPub.remove(key);
                return true;
            }
        }
        for (String key : usrChPriv) {
            if (usrChPriv.isEmpty()) {
                sender.sendMessage("no channel to remove");
                return true;
            }
            if (args[1].equals(key)) {
                sender.sendMessage(key + "got removed");
                usrChPriv.remove(key);
                return true;
            }
        }
        sender.sendMessage(args[1] + " does not exist");
        // removes created channels
        return true;
    }

    private void channelCreate(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
            return;
        }
        if (args.length == 2) {
            sender.sendMessage("status channel? private oder public!");
            return;
        }
        if (defaultChannels.contains(args[1])) {
            sender.sendMessage("not allowed to create " + args[1].toLowerCase() + " because created already");
            return;
        }
        if (args[2].equalsIgnoreCase("public")) {
            createPublic(sender, args);
            return;
        }
        if (args[2].equalsIgnoreCase("private")) {
            createPrivate(sender, args);
            return;
        }
        sender.sendMessage("nope, only private or public");
        return;
    }

    private void createPrivate(CommandSender sender, String[] args) {
        if (!usrChPriv.contains(args[1].toLowerCase())) {
            usrChPriv.add(args[1]);
            sender.sendMessage("done creating " + usrChPriv.contains(args[1].toLowerCase().toString()));
            return;
        }
        sender.sendMessage("nope, exists already");
    }

    private void createPublic(CommandSender sender, String[] args) {
        if (usrChPub.contains(args[1].toLowerCase())) {
            sender.sendMessage("nope, exists already");
            return;
        }
        usrChPub.add(args[1]);
        sender.sendMessage("done creating " + usrChPub.contains(args[1].toLowerCase().toString()));
    }

    private void channelHelp(CommandSender sender) {
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
