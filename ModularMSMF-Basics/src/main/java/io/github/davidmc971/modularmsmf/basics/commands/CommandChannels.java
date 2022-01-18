package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.SortList;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 * @since 0.3
 *        Channels to use for writing with players on the server. Channels can
 *        seperate the default channel into little other channels to communicate
 *        between players.
 * @TODO Rewrite methods to comply permissions system
 */

public class CommandChannels implements IModularMSMFCommand, TabCompleter {

    private String defaultCh = "default";

    // SortList as instance?

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

    public static final HashMap<String/* username */, String/* channelname */> setChannelUsr = new HashMap<String, String>();
    public static final HashMap<String/* username */, String/* channelname */> USRCHANNEL_MAP = new LinkedHashMap<String, String>();
    // <USRCHANNEL_MAP> not used yet
    /**
     * TODO: HashMap for channelname and private/public?
     */
    public static final Set<String/* channelname */> usrChPriv = new HashSet<String>();
    public static final Set<String/* channelname */> usrChPub = new HashSet<String>();

    private static final ArrayList<String> defaultChannels = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            // only for main channels to obtain, cannot be removed
            add("support"); /* /channel set support */
            add("default"); /* /channel set default */
            add("admin"); /* /channel set admin */
            add("moderator"); /* /channel set moderator */
        }
    };

    /**
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            handleChannelHelp(sender);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "admin":
                if (!sender.isOp() || !PermissionManager.checkPermission(sender, "channels_admin")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                handleAdmin(sender, args);
                break;
            case "get":
                if (!sender.isOp() || !PermissionManager.checkPermission(sender, "channels_get_player")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                handleChannelGet(sender, args);
                break;
            case "help":
                handleChannelHelp(sender);
                break;
            case "leave":
                handleChannelLeave(sender, args);
                break;
            case "join":
                handleChannelJoin(sender, args);
                break;
            case "create":
                if (!PermissionManager.checkPermission(sender, "channels_create")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                handleChannelCreate(sender, args);
                break;
            case "remove":
                if (!PermissionManager.checkPermission(sender, "channels_remove")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                handleChannelRemove(sender, args);
                break;
            case "switch":
                handleChannelSwitch(sender, args);
                break;
            case "list":
                handleChannelList(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    /**
     * @param sender
     * @param args
     */
    private void handleAdmin(CommandSender sender, String[] args) {
        switch (args[0].toLowerCase()) {
            case "create":
                handleCreateAdmin(sender, args);
                break;
            case "join":
            case "leave":
            case "remove":
                handlePlayerChannelAdmin(sender, args);
                break;
            case "switch":
                handleChannelSwitchAdmin(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
        }
    }

    /**
     * @param sender
     * @param args
     */
    private void handleCreateAdmin(CommandSender sender, String[] args) {
    }

    /**
     * @param sender
     * @param args
     */
    private void handlePlayerChannelAdmin(CommandSender sender, String[] args) {
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelSwitchAdmin(CommandSender sender, String[] args) {
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelSwitch(CommandSender sender, String[] args) {
        if (!usrChPriv.contains(args[1]) || !usrChPub.contains(args[1])) {
            sender.sendMessage("channel non existant");
            return;
        }
        switch (args.length) {
            case 1: // public channel name
                handleSwitchPublic(sender, args);
                break;
            case 2: // private channel name
                handleSwitchPrivate(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "commands.args.toomany");
                break;
        }
    }

    private void switchChannel(String key, Set<String> channel) {

    }

    private void handleSwitchPublic(CommandSender sender, String[] args) {
        if (!setChannelUsr.containsValue(args[1].toLowerCase())) {
            sender.sendMessage("you cannot switch because you're not in a channel yet");
            return;
        }
        sender.sendMessage("You switched from " + setChannelUsr.get(((Player) sender).getName()) + " to: "
                + args[1].toLowerCase());
        setChannelUsr.put(((Player) sender).getName(), args[1].toLowerCase());
    }

    private void handleSwitchPrivate(CommandSender sender, String[] args) {
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelGet(CommandSender sender, String[] args) {
        if (args.length == 1) {
            handleChannelGetSelf(sender);
            return;
        }
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == sender) {
            handleChannelGetSelf(sender);
            return; // return true if not null
        }
        if (player == null) {
            sender.sendMessage("non existant player");
            return;
        }
        if (setChannelUsr.get(player.getName()) != null) {
            sender.sendMessage("you're in: " + setChannelUsr.get(player.getName())
                    + " <-- key returns channelname // value --> "
                    + setChannelUsr.containsValue(setChannelUsr.get((player.getName()))));
            return;
        }
        sender.sendMessage(player.getName() + " in no channel boi");
        return;
    }

    /**
     * @param sender
     */
    private void handleChannelGetSelf(CommandSender sender) {
        if (setChannelUsr.get(((Player) sender).getName()) != null) {
            sender.sendMessage("you're in: " + setChannelUsr.get(((Player) sender).getName())
                    + " <-- key returns channelname // value --> "
                    + setChannelUsr.containsValue(setChannelUsr.get((((Player) sender).getName()))));
            return; // return true if not null
        }
        sender.sendMessage("you're in no channel boi");
        return;
    }

    /**
     * @param sender
     */
    private void handleChannelHelp(CommandSender sender) {
        sender.sendMessage("help here");
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelLeave(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                if (channelName.contains(args[1])) {
                    sender.sendMessage("it's a placeholder --> " + args[1]);
                    break;
                }
                handleLeave(sender, args);
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    /**
     * @param sender
     * @param args
     */
    private void handleLeave(CommandSender sender, String[] args) {
        if (usrChPriv.contains(args[1])) {
            if (setChannelUsr.containsValue(args[1])) {
                sender.sendMessage(setChannelUsr.get(args[1]) + " " + setChannelUsr.containsKey(args[1])
                        + " <-- false = " + args[1]);
                setChannelUsr.remove(((Player) sender).getName(), args[1]);
                return;
            }
            return;
        }
        if (defaultChannels.contains(args[1])) {
            sender.sendMessage("couldn't leave");
            return;
        }
        if (usrChPub.contains(args[1])) {
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

    /**
     * @param sender
     * @param (channelName.contains(args[1])
     */
    private void handleChannelJoin(CommandSender sender, String[] args) { // FIXME: still joining channels while not
                                                                          // created
        // any channel
        if (channelName.contains(args[1])) {
            sender.sendMessage("it's a placeholder --> " + args[1]);
            return;
        }
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                if (!PermissionManager.checkPermission(sender, "channels_join_admins") ||
                        sender.isOp()) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                if (!usrChPriv.contains(args[1])) {
                    handleJoinPublic(sender, args);
                    break;
                }
                sender.sendMessage("dont forget private / public");
                break;
            case 3:
                if (args[2].equalsIgnoreCase("private")) {
                    handleJoinPrivate(sender, args);
                    break;
                }
                if (args[2].equalsIgnoreCase("public")) {
                    handleJoinPublic(sender, args);
                    break;
                }
                sender.sendMessage("wrong arg");
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    /**
     * @param sender
     * @param key
     * @param channel
     */
    private void handleJoin(CommandSender sender, String key, Set<String> channel) {
        if (!channel.contains(key)) {
            sender.sendMessage("channel not created yet");
            return;
        }
        if (!setChannelUsr.containsValue(key)) {
            setChannelUsr.put(((Player) sender).getName(), key);
            sender.sendMessage(
                    setChannelUsr.get(((Player) sender).getName()) + " " + setChannelUsr.containsKey(key)
                            + " <-- true = " + key);
            return;
        }
        sender.sendMessage("you're already in this channel!");
    }

    /**
     * @param sender
     * @param args
     */
    private void handleJoinPrivate(CommandSender sender, String[] args) {
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("this a public channel!");
            return;
        }
        handleJoin(sender, args[1], usrChPriv);
    }

    /**
     * @param sender
     * @param args
     */
    private void handleJoinPublic(CommandSender sender, String[] args) {
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("this a private channel!");
            return;
        }
        handleJoin(sender, args[1], usrChPub);
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelList(CommandSender sender, String[] args) {
        if (args.length == 1) {
            handleListAll(sender);
            return;
        }
        switch (args[1].toLowerCase()) {
            case "public":
                handleListPublic(sender);
                break;
            case "private":
                handleListPrivate(sender);
                break;
            case "all":
                handleListAll(sender);
                break;
            default:
                sender.sendMessage("wrong arg");
                break;
        }
    }

    /**
     * @param sender
     */
    private void handleListPublic(CommandSender sender) {
        sender.sendMessage("List of all public channels: " + SortList.printSet(usrChPriv));
    }

    /**
     * @param sender
     */
    private void handleListPrivate(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + SortList.printSet(usrChPriv));
    }

    /**
     * @param sender
     */
    private void handleListAll(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_all")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + SortList.printSet(usrChPriv));
        sender.sendMessage("List of all public channels: " + SortList.printSet(usrChPub));
        sender.sendMessage("List of all default channels: " + SortList.printAList(defaultChannels));
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelRemove(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return;
        }
        if (args.length == 2) {
            if (usrChPub.isEmpty() && usrChPriv.isEmpty()) {
                sender.sendMessage("no channel to remove");
                return;
            }
            removeChannel(sender, args[1], usrChPub);
            removeChannel(sender, args[1], usrChPriv);
        }
        sender.sendMessage(args[1] + " does not exist");
        // removes created channels
        return;
    }

    /**
     * @param sender
     * @param key
     * @param channel
     */
    private void removeChannel(CommandSender sender, String key, Set<String> channel) {
        if (channel.contains(key)) {
            sender.sendMessage(key + " got removed");
            channel.remove(key);
            for (Player player : Bukkit.getOnlinePlayers()) {
                setChannelUsr.put(player.getName(), defaultCh);
            }
            return;
        }
    }

    /**
     * @param sender
     * @param args
     */
    private void handleChannelCreate(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
            return;
        }
        if (channelName.contains(args[1])) {
            sender.sendMessage("it's a placeholder --> " + args[1]);
            return;
        }
        if (args.length == 2) {
            if (defaultChannels.contains(args[1].toLowerCase())) {
                sender.sendMessage("not allowed to create " + args[1] + " because created already");
                return;
            }
            sender.sendMessage("status channel? private oder public!");
            return;
        }
        if (args[2].equalsIgnoreCase("public")) {
            handleCreatePublic(sender, args);
            return;
        }
        if (args[2].equalsIgnoreCase("private")) {
            handleCreatePrivate(sender, args);
            return;
        }
        sender.sendMessage("nope, only private or public");
        return;
    }

    /**
     * @param sender
     * @param args
     */
    private void handleCreatePrivate(CommandSender sender, String[] args) {
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("nope, exists already as public!");
            return;
        }
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("nope, exists already as private!");
            return;
        }
        usrChPriv.add(args[1]);
        setChannelUsr.put(((Player) sender).getName(), args[1]);
        sender.sendMessage("done creating " + usrChPriv.contains(args[1]));
    }

    /**
     * @param sender
     * @param args
     */
    private void handleCreatePublic(CommandSender sender, String[] args) {
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("nope, exists already as private!");
            return;
        }
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("nope, exists already as public!");
            return;
        }
        usrChPub.add(args[1]);
        setChannelUsr.put(((Player) sender).getName(), args[1]);
        sender.sendMessage("done creating " + usrChPub.contains(args[1]));
    }

    ArrayList<String> labels = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("create");
            add("get");
            add("help");
            add("join");
            add("leave");
            add("list");
            add("remove");
            add("switch");
        }
    };

    ArrayList<String> labelsAdmins = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("admin");
            addAll(labels);
        }
    };

    ArrayList<String> labelsArgs = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("create");
            add("join");
            add("leave");
            add("list");
            add("remove");
            add("switch");
        }

    };
    ArrayList<String> labelsArgsAdmins = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            addAll(labelsArgs);
            remove("list");
        }
    };

    ArrayList<String> typeChannel = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("private");
            add("public");
        }
    };

    ArrayList<String> channelName = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("<name>");
        }
    };

    /**
     * @param onTabComplete(
     * @return List<String>
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String alias, @NotNull String[] args) {

        List<String> privChannels = new ArrayList<String>(usrChPriv);
        Collections.sort(privChannels);

        List<String> pubChannels = new ArrayList<String>(usrChPub);
        Collections.sort(pubChannels);

        ArrayList<String> playernames = new ArrayList<String>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (int i = 0; i < players.length; i++) {
            playernames.add(players[i].getName());
        }

        switch (args.length) {
            case 1:
                if (sender.isOp() || PermissionManager.checkPermission(sender, "channels_admin")) {
                    return labelsAdmins;
                }
                // return labels;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "switch":
                        return pubChannels;
                    case "admin":
                        return labelsArgsAdmins;
                    // case "leave": // TODO: Show up player's channel where it joined or created
                    case "help":
                        return labelsArgs;
                    case "get":
                        Collections.sort(playernames);
                        return playernames;
                    case "create":
                        return channelName;
                    case "join":
                    case "remove":
                        return pubChannels;
                    default:
                        return null;
                }
            case 3:
                switch (args[0].toLowerCase()) {
                    case "create":
                        return typeChannel;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    /**
     * @return String
     */
    @Override
    public String Label() {
        return "channel";
    }

    /**
     * @return String[]
     */
    @Override
    public String[] Aliases() {
        return new String[] { "ch", "chan" };
    }

    /**
     * @return boolean
     */
    @Override
    public boolean Enabled() {
        return true;
    }
}
