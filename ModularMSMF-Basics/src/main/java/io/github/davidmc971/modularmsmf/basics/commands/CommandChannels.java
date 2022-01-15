package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 * @TODO Rewrite methods to comply permissions system
 */

public class CommandChannels implements IModularMSMFCommand {

    private String defaultCh = "default";

    // CommandUtil as instance?

    /**
     * Map descriptions:
     *
     * setChannelUsr - key(sender.getName()) as string, value(channelname//args[1])
     * as string
     * will be used for following commands:
     * /channel create <name> <private/public> //@deprecated
     * /channel join <name>
     * /channel remove <name>
     * /channel leave <name>
     * /channel get
     * will be used for following events: onChat(AsyncPlayerChatEvent e)
     * //FIXME: Prefix couldn't show up
     *
     * USRCHANNEL_MAP - key(channelname//args[1]) as string,
     * value(channeltype//args[2]) as string
     * will be used for following commands:
     * /channel create <name> <private/public>
     *
     * Set descriptions:
     * usrChPriv & usrChPub - key(channelname//args[1]) as string
     * will be used for following commands:
     * /channel create <name> <private>
     * /channel join <name>
     * /channel remove <name>
     * /channel leave <name>
     * /channel list <args>
     */

    public static final HashMap<String/* username */, String/* channelname */> setChannelUsr = new LinkedHashMap<String, String>();
    public static final HashMap<String/* username */, String/* channelname */> USRCHANNEL_MAP = new LinkedHashMap<String, String>();
    // <USRCHANNEL_MAP> not used yet
    /**
     * TODO: HashMap for channelname and private/public?
     */
    public static final HashSet<String/* channelname */> usrChPriv = new LinkedHashSet<String>();
    public static final HashSet<String/* channelname */> usrChPub = new LinkedHashSet<String>();
    private static final HashSet<String> defaultChannels = new HashSet<String>() {
        private static final long serialVersionUID = 1L;
        {
            // only for main channels to obtain, cannot be removed
            add("support"); /* /channel set support */
            add("default"); /* /channel set default */
            add("admin"); /* /channel set admin */
            add("moderator"); /* /channel set moderator */
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
            case "get":
                if (sender.isOp()) {
                    channelGet(sender);
                    break;
                }
                ChatUtils.sendMsgNoPerm(sender);
                break;
            case "help":
                channelHelp(sender);
                break;
            case "leave":
                channelLeave(sender, args);
                break;
            case "join":
                channelJoin(sender, args);
                break;
            case "create":
                channelCreate(sender, args);
                break;
            case "remove":
                channelRemove(sender, args);
                break;
            // case "switch":
            // channelSwitch(sender, args); //TODO: switch channels maybe?
            // break;
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

    private void channelGet(CommandSender sender) {
        if (setChannelUsr.get(((Player) sender).getName()) != null) {
            sender.sendMessage("you're in: " + setChannelUsr.get(((Player) sender).getName())
                    + " <-- key returns channelname // value --> "
                    + setChannelUsr.containsValue(setChannelUsr.get((((Player) sender).getName()))));
            return; // return true if not null
        }
        sender.sendMessage("you're in no channel");
    }

    private void channelHelp(CommandSender sender) {
        sender.sendMessage("help here");
    }

    private void channelLeave(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                leave(sender, args);
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    private void leave(CommandSender sender, String[] args) {
        if (usrChPriv.contains(args[1]) && !usrChPriv.isEmpty()) {
            if (setChannelUsr.containsValue(args[1])) {
                sender.sendMessage(setChannelUsr.get(args[1]) + " " + setChannelUsr.containsKey(args[1])
                        + " <-- false = " + args[1]);
                setChannelUsr.remove(((Player) sender).getName(), args[1]);
                return;
            }
            return;
        }
        if (defaultChannels.contains(args[1]) && !defaultChannels.isEmpty()) {
            sender.sendMessage("couldn't leave");
            return;
        }
        if (usrChPub.contains(args[1]) && !usrChPub.isEmpty()) {
            if (setChannelUsr.containsValue(args[1])) {
                sender.sendMessage(setChannelUsr.get(args[1]) + " " + setChannelUsr.containsKey(args[1])
                        + " <-- false = " + args[1]);
                setChannelUsr.remove(((Player) sender).getName(), args[1]);
                return;
            }
            return;
        }
        sender.sendMessage("channel doesn't exist");
        return;
    }

    private void channelJoin(CommandSender sender, String[] args) { // FIXME: still joining channels while not created
                                                                    // any channel
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                if (!PermissionManager.checkPermission(sender, "channels_join_admins") || sender.isOp()) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                if (!usrChPriv.contains(args[1])) {
                    joinPrivate(sender, args);
                    break;
                }
                sender.sendMessage("dont forget private / public");
                break;
            case 3:
                if (args[2].equalsIgnoreCase("private")) {
                    joinPrivate(sender, args);
                    return;
                }
                if (args[2].equalsIgnoreCase("public")) {
                    joinPublic(sender, args);
                    return;
                }
                sender.sendMessage("wrong arg");
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    private void joinPrivate(CommandSender sender, String[] args) { // FIXME: still joining channels while not created
                                                                    // any channel
        if (usrChPriv.contains(args[1]) || sender.isOp()
                || ((PermissionManager.checkPermission(sender, "channels_join_admins")
                        && defaultChannels.contains(args[1])))) {
            if (!setChannelUsr.containsValue(args[1])) {
                setChannelUsr.put(((Player) sender).getName(), args[1]);
                sender.sendMessage(
                        setChannelUsr.get(((Player) sender).getName()) + " " + setChannelUsr.containsKey(args[1])
                                + " <-- true = " + args[1]);
                return;
            }
            sender.sendMessage("you're already in this channel!");
            return;
        }
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("this a public channel!");
            return;
        }
        sender.sendMessage("channel not created yet");
    }

    private void joinPublic(CommandSender sender, String[] args) { // FIXME: still joining channels while not created
                                                                   // any channel
        if (usrChPub.contains(args[1]) || sender.isOp()
                || ((PermissionManager.checkPermission(sender, "channels_join_admins")
                        && defaultChannels.contains(args[1])))) {
            if (!setChannelUsr.containsValue(args[1])) {
                setChannelUsr.put(((Player) sender).getName(), args[1]);
                sender.sendMessage(
                        setChannelUsr.get(((Player) sender).getName()) + " " + setChannelUsr.containsKey(args[1])
                                + " <-- true = " + args[1]);
                return;
            }
            sender.sendMessage("you're already in this channel!");
            return;
        }
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("this a private channel!");
            return;
        }
        sender.sendMessage("channel not created yet");
    }

    private void channelList(CommandSender sender, String[] args) {
        if (args.length == 1) {
            listAll(sender);
            return;
        }
        switch (args[1].toLowerCase()) {
            case "public":
                listPublic(sender);
                break;
            case "private":
                listPrivate(sender);
                break;
            case "all":
                listAll(sender);
                break;
            default:
                sender.sendMessage("wrong arg");
                break;
        }
    }

    private void listPublic(CommandSender sender) {
        sender.sendMessage("List of all public channels: " + CommandUtil.printSet(usrChPriv));
    }

    private void listPrivate(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + CommandUtil.printSet(usrChPriv));
    }

    private void listAll(CommandSender sender) {
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
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (usrChPub.isEmpty()) {
                    sender.sendMessage("no channel to remove");
                    return true;
                }
                if (args[1].equals(key)) {
                    sender.sendMessage(key + "got removed");
                    usrChPub.remove(key);
                    setChannelUsr.put(player.getName(), defaultCh);
                    return true;
                }
            }
        }
        for (String key : usrChPriv) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (usrChPriv.isEmpty()) {
                    sender.sendMessage("no channel to remove");
                    return true;
                }
                if (args[1].equals(key)) {
                    sender.sendMessage(key + "got removed");
                    usrChPriv.remove(key);
                    setChannelUsr.put(player.getName(), defaultCh);
                    return true;
                }
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
            if (defaultChannels.contains(args[1])) {
                sender.sendMessage("not allowed to create " + args[1] + " because created already");
                return;
            }
            sender.sendMessage("status channel? private oder public!");
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
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("nope, exists already as public!");
            return;
        }
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("nope, exists already!");
            return;
        }
        usrChPriv.add(args[1]);
        setChannelUsr.put(((Player) sender).getName(), args[1]);
        sender.sendMessage("done creating " + usrChPriv.contains(args[1]));
    }

    private void createPublic(CommandSender sender, String[] args) {
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("nope, exists already as private!");
            return;
        }
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("nope, exists already!");
            return;
        }
        usrChPub.add(args[1]);
        setChannelUsr.put(((Player) sender).getName(), args[1]);
        sender.sendMessage("done creating " + usrChPub.contains(args[1]));
    }

    @Override
    public String Label() {
        return "channel";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "ch", "chan" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}
