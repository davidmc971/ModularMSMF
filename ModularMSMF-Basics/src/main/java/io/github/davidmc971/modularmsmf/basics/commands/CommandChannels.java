package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    public static final ArrayList<String/* channelname */> usrChPriv = new ArrayList<String>();
    public static final ArrayList<String/* channelname */> usrChPub = new ArrayList<String>();

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
            case "get":
                if (sender.isOp()) {
                    handleChannelGet(sender, args);
                    break;
                }
                ChatUtils.sendMsgNoPerm(sender);
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
                handleChannelCreate(sender, args);
                break;
            case "remove":
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

    private void handleChannelSwitch(CommandSender sender, String[] args) {
    }

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

    private void handleChannelHelp(CommandSender sender) {
        sender.sendMessage("help here");
    }

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

    private void handleLeave(CommandSender sender, String[] args) {
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

    private void handleChannelJoin(CommandSender sender, String[] args) { // FIXME: still joining channels while not
                                                                          // created
        // any channel
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                // if (!PermissionManager.checkPermission(sender, "channels_join_admins") ||
                // sender.isOp()) {
                // ChatUtils.sendMsgNoPerm(sender);
                // break;
                // }
                if (channelName.contains(args[1])) {
                    sender.sendMessage("it's a placeholder --> " + args[1]);
                    return;
                }
                if (!usrChPriv.contains(args[1])) {
                    handleJoinPrivate(sender, args);
                    break;
                }
                sender.sendMessage("dont forget private / public");
                break;
            case 3:
                if (args[2].equalsIgnoreCase("private")) {
                    if (channelName.contains(args[1])) {
                        sender.sendMessage("it's a placeholder --> " + args[1]);
                        break;
                    }
                    handleJoinPrivate(sender, args);
                    break;
                }
                if (args[2].equalsIgnoreCase("public")) {
                    if (channelName.contains(args[1])) {
                        sender.sendMessage("it's a placeholder --> " + args[1]);
                        break;
                    }
                    handleJoinPublic(sender, args);
                    break;
                }
                sender.sendMessage("wrong arg");
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    private void handleJoinPrivate(CommandSender sender, String[] args) {
        if (usrChPriv.contains(args[1])/* && !defaultChannels.contains(args[1]) */) {
            sender.sendMessage("you're already in this channel!");
            return;
        }
        if (!setChannelUsr.containsValue(args[1])) {
            setChannelUsr.put(((Player) sender).getName(), args[1]);
            sender.sendMessage(
                    setChannelUsr.get(((Player) sender).getName()) + " " + setChannelUsr.containsKey(args[1])
                            + " <-- true = " + args[1]);
            return;
        }
        if (usrChPub.contains(args[1])) {
            sender.sendMessage("this a public channel!");
            return;
        }
        sender.sendMessage("channel not created yet");
    }

    private void handleJoinPublic(CommandSender sender, String[] args) { // FIXME: still joining channels while not
                                                                         // created
        // any channel
        if (usrChPub.contains(args[1])/* && defaultChannels.contains(args[1]))) */) {
            sender.sendMessage("you're already in this channel!");
            return;
        }
        if (!setChannelUsr.containsValue(args[1])) {
            setChannelUsr.put(((Player) sender).getName(), args[1]);
            sender.sendMessage(
                    setChannelUsr.get(((Player) sender).getName()) + " " + setChannelUsr.containsKey(args[1])
                            + " <-- true = " + args[1]);
            return;
        }
        if (usrChPriv.contains(args[1])) {
            sender.sendMessage("this a private channel!");
            return;
        }
        sender.sendMessage("channel not created yet");
    }

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

    private void handleListPublic(CommandSender sender) {
        sender.sendMessage("List of all public channels: " + SortList.printSet(usrChPriv));
    }

    private void handleListPrivate(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + SortList.printSet(usrChPriv));
    }

    private void handleListAll(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_all")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        sender.sendMessage("List of all private channels: " + SortList.printSet(usrChPriv));
        sender.sendMessage("List of all public channels: " + SortList.printSet(usrChPub));
        sender.sendMessage("List of all default channels: " + SortList.printSet(defaultChannels));
    }

    private void handleChannelRemove(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return;
        }
        if (args.length == 2) {
            for (String key : usrChPub) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (usrChPub.isEmpty()) {
                        sender.sendMessage("no channel to remove");
                        return;
                    }
                    if (args[1].equals(key)) {
                        sender.sendMessage(key + "got removed");
                        usrChPub.remove(key);
                        setChannelUsr.put(player.getName(), defaultCh);
                        return;
                    }
                }
            }
            for (String key : usrChPriv) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (usrChPriv.isEmpty()) {
                        sender.sendMessage("no channel to remove");
                        return;
                    }
                    if (args[1].equals(key)) {
                        sender.sendMessage(key + "got removed");
                        usrChPriv.remove(key);
                        setChannelUsr.put(player.getName(), defaultCh);
                        return;
                    }
                }
            }
        }
        if (channelName.contains(args[1])) {
            sender.sendMessage("it's a placeholder --> " + args[1]);
            return;
        }
        sender.sendMessage(args[1] + " does not exist");
        // removes created channels
        return;
    }

    private void handleChannelCreate(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
            return;
        }
        if (args.length == 2) {
            if (defaultChannels.contains(args[1].toLowerCase())) {
                sender.sendMessage("not allowed to create " + args[1] + " because created already");
                return;
            }
            if (channelName.contains(args[1])) {
                sender.sendMessage("it's a placeholder --> " + args[1]);
                return;
            }
            sender.sendMessage("status channel? private oder public!");
            return;
        }
        if (args[2].equalsIgnoreCase("public")) {
            if (channelName.contains(args[1])) {
                sender.sendMessage("it's a placeholder --> " + args[1]);
                return;
            }
            handleCreatePublic(sender, args);
            return;
        }
        if (args[2].equalsIgnoreCase("private")) {
            if (channelName.contains(args[1])) {
                sender.sendMessage("it's a placeholder --> " + args[1]);
                return;
            }
            handleCreatePrivate(sender, args);
            return;
        }
        sender.sendMessage("nope, only private or public");
        return;
    }

    private void handleCreatePrivate(CommandSender sender, String[] args) {
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

    private void handleCreatePublic(CommandSender sender, String[] args) {
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

    ArrayList<String> labels = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("get");
            add("help");
            add("leave");
            add("join");
            add("create");
            add("remove");
            add("switch");
            add("list");
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
            add("leave");
            add("join");
            add("switch");
            add("create");
            add("remove");
            add("list");
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

    ArrayList<String> playernames = new ArrayList<String>();
    Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String alias, @NotNull String[] args) { // TODO: implement this too in events
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (int i = 0; i < players.length; i++) {
            playernames.add(players[i].getName());
        }
        switch (args.length) {
            case 1:
                Collections.sort(labels);
                if (sender.isOp() || PermissionManager.checkPermission(sender, "channels_admin")) {
                    return labelsAdmins;
                }
                return labels;
            case 2: // FIXME: show's case 3
                if (labels.contains("join")) {
                    // TODO: Show up any public channels to join
                    Collections.sort(channelName);
                    return channelName;
                }
                if (labels.contains("get")) {
                    Collections.sort(playernames);
                    return playernames;
                }
                if (labels.contains("remove") || labels.contains("leave")) {
                    return channelName;
                    // TODO: Show up player's channel where it joined or created
                }
            case 3: // FIXME: show's case 3 in case 2
                if (labels.contains("create")) {
                    Collections.sort(typeChannel);
                    return typeChannel;
                }
            default:
                return null;
        }
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
