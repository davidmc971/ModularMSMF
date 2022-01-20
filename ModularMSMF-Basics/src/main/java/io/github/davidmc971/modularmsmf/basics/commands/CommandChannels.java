package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private String forbittenName = "<name>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "channels_use")
                || !PermissionManager.checkPermission(sender, "channels_*")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            handleChannelHelpNoArgs(sender); // channel{0}
            return true;
        }
        switch (args[0].toLowerCase()) { // /channel{0} args{1/-1}
            case "admin": // TODO: kicking players out of channels if misbehavior
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
            case "help": // TODO: only remove if done {3}
                handleChannelHelpArgs(sender, args);
                break;
            case "leave": // TODO: only replace channelname to defaultCh
                handleChannelLeave(sender, args);
                break;
            case "join":
                handleChannelJoin(sender, args);
                break;
            case "create": // TODO: only create new channel if player is in no channel - has to leave first
                           // in order to create a new channel
                if (!PermissionManager.checkPermission(sender, "channels_create")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                handleChannelCreate(sender, args);
                break;
            case "remove": // TODO: should replace all players' channel in the removed channel to defaultCh
                if (!PermissionManager.checkPermission(sender, "channels_remove")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                handleChannelRemove(sender, args);
                break;
            case "switch": // TODO: only remove if done {8} //TODO: only switch if player is in a channel &
                           // shouldn't switch in same channel over and over again
                handleChannelSwitch(sender, args);
                break;
            case "list": // TODO: permissions respective list (without any perms only public etc.)
                handleChannelList(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private void handleChannelHelpNoArgs(CommandSender sender) {
        sender.sendMessage("help provided here");
    }

    private void handleAdmin(CommandSender sender, String[] args) {
        switch (args[1].toLowerCase()) {
            case "kick":
                if (!PermissionManager.checkPermission(sender, "channels_admins_kick")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return;
                }
                handleKickAdmin(sender, args);
                break;
            case "create":
                if (!PermissionManager.checkPermission(sender, "channels_admins_create")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return;
                }
                handleCreateAdmin(sender, args);
                break;
            case "join":
                if (!PermissionManager.checkPermission(sender, "channels_admins_join")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return;
                }
                handleAdminJoinChannel(sender, args);
                break;
            case "remove":
                if (!PermissionManager.checkPermission(sender, "channels_admins_remove")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return;
                }
                handleAdminRemoveChannel(sender, args);
                break;
            case "switch":
                if (!PermissionManager.checkPermission(sender, "channels_admins_switch")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return;
                }
                handleChannelSwitchAdmin(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
        }
    }

    private void handleKickAdmin(CommandSender sender, String[] args) {
    }

    private void handleChannelSwitchAdmin(CommandSender sender, String[] args) {
        switch (args.length) {
            case 4:
                if (args[2].equalsIgnoreCase("public")) {
                    channelPrivateToPublic(sender, args);
                    return;
                }
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
            case 5:
                if (args[2].equalsIgnoreCase("private")) {
                    channelPublicToPrivate(sender, args);
                    return;
                }
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "commands.arguments.invalid");
        }
    }

    private void channelPrivateToPublic(CommandSender sender, String[] args) {
        Set<String> key = CHANNELTREE_MAP.keySet();
        Collection<String> value = CHANNELTREE_MAP.values();
        if (key.isEmpty()) {
            sender.sendMessage("missing created channels, create one");
            return;
        }
        if (key.contains(args[3]) && value.equals(null)) {
            CHANNELTREE_MAP.replace(args[3], null);
            sender.sendMessage("@NOTNULL channel " + args[3] + " will set new value with "
                    + CHANNELTREE_MAP.getOrDefault(args[3], null));
            return;
        }
        if (key.contains(args[3]) && !value.equals(null)) {
            CHANNELTREE_MAP.replace(args[3], null);
            sender.sendMessage("@NULL channel " + args[3] + " will set new value with "
                    + CHANNELTREE_MAP.getOrDefault(args[3], null));
            return;
        }
        sender.sendMessage("channel " + args[3] + " doesn't exist");
        return;
    }

    private void channelPublicToPrivate(CommandSender sender, String[] args) {
        Set<String> key = CHANNELTREE_MAP.keySet();
        if (key.isEmpty()) {
            sender.sendMessage("missing created channels, create one");
            return;
        }
        if (key.contains(args[3]) && CHANNELTREE_MAP.containsValue(null)) {
            CHANNELTREE_MAP.replace(args[3], null, args[4]);
            sender.sendMessage("@NULL channel " + args[3] + " will set new value with "
                    + CHANNELTREE_MAP.getOrDefault(args[3], args[4]));
            return;
        }
        if (key.contains(args[3]) && !CHANNELTREE_MAP.containsValue(null)) {
            CHANNELTREE_MAP.replace(args[3], args[4]);
            sender.sendMessage("@NOTNULL channel " + args[3] + " will set new value with "
                    + CHANNELTREE_MAP.getOrDefault(args[3], args[4]));
            return;
        }
        sender.sendMessage("channel " + args[3] + " doesn't exist");
        return;
    }

    private void handleAdminRemoveChannel(CommandSender sender, String[] args) {
        switch (args.length) {
            case 2:
                sender.sendMessage("missing name to remove channel");
                break;
            case 3:
                removeAdmin(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
        }
    }

    private void removeAdmin(CommandSender sender, String[] args) {
        Set<String> key = CHANNELTREE_MAP.keySet();
        if (!key.contains(args[2]) || !channelsPrivate.contains(args[2]) || !channelsPublic.contains(args[2])) {
            sender.sendMessage("Channel " + args[2] + " has not been found");
            return;
        }
        CHANNELTREE_MAP.remove(args[2], null);
        sender.sendMessage("Channel " + args[2] + " removed");
    }

    private void handleAdminJoinChannel(CommandSender sender, String[] args) {
        Set<String> channel = CHANNELTREE_MAP.keySet();
        if (channel.isEmpty()) {
            sender.sendMessage("create channel before joining");
            return;
        }
        if (args[2].equalsIgnoreCase(forbittenName)) {
            sender.sendMessage("its a placeholder");
            return;
        }
        if (!channel.contains(args[2])) {
            sender.sendMessage(args[2] + " does not exist");
            return;
        }
        if (USERCHANNEL_MAP.containsValue(args[2])) {
            sender.sendMessage("already in this channel");
            return;
        }
        USERCHANNEL_MAP.put(((Player) sender).getName(), args[2]);
        sender.sendMessage("changed channel to " + args[2]);
        return;
    }

    private void handleCreateAdmin(CommandSender sender, String[] args) {
        switch (args.length) {
            case 2:
                sender.sendMessage("missing name and/or criteria for join");
                break;
            case 3:
                if (defaultChannels.contains(args[2])) {
                    sender.sendMessage("default channel not allowed to re-create");
                    return;
                }
                if (args[2].equalsIgnoreCase(forbittenName)) {
                    sender.sendMessage("its a placeholder");
                    return;
                }
                createAdmin(sender, args);
                break;
            case 4:
                if (defaultChannels.contains(args[2])) {
                    sender.sendMessage("default channel not allowed to re-create");
                    return;
                }
                if (args[2].equalsIgnoreCase(forbittenName)) {
                    sender.sendMessage("its a placeholder");
                    return;
                }
                createAdminCriteria(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
        }
    }

    private void createAdmin(CommandSender sender, String[] args) {
        Set<String> key = CHANNELTREE_MAP.keySet();
        Collection<String> value = CHANNELTREE_MAP.values();
        if (key.contains(args[2])) {
            sender.sendMessage("Channel " + args[2] + " with value "
                    + CHANNELTREE_MAP.getOrDefault(args[2], value.toString().replaceAll("\\[|\\]", ""))
                    + " is already created");
            return;
        }
        CHANNELTREE_MAP.put(args[2], null);
        sender.sendMessage("Channel " + args[2] + " created");
        sender.sendMessage(
                "value: " + CHANNELTREE_MAP.getOrDefault(args[2], value.toString().replaceAll("\\[|\\]", "")));
    }

    private void createAdminCriteria(CommandSender sender, String[] args) {
        Set<String> key = CHANNELTREE_MAP.keySet();
        Collection<String> value = CHANNELTREE_MAP.values();
        if (key.contains(args[2])) {
            sender.sendMessage("Channel " + args[2] + " with value " + value.toString().replaceAll("\\[|\\]", "")
                    + " is already created");
            return;
        }
        CHANNELTREE_MAP.put(args[2], args[3]);
        sender.sendMessage(
                "Channel " + args[2] + " with " + CHANNELTREE_MAP.getOrDefault(args[2], args[3]) + " created");
        sender.sendMessage("value: " + CHANNELTREE_MAP.getOrDefault(args[2], args[3]));
    }

    private void handleChannelSwitch(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                sender.sendMessage("missing args like channel name and criteria/private/public");
            case 2: // public channel name
                if (!CHANNELTREE_MAP.containsKey(args[1])) {
                    sender.sendMessage("channel non existant");
                    return;
                }
                handleSwitchPublic(sender, args);
                break;
            case 3: // private channel name
                if (!CHANNELTREE_MAP.containsKey(args[1])) {
                    sender.sendMessage("channel non existant");
                    return;
                }
                handleSwitchPrivate(sender, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.args.toomany");
                break;
        }
    }

    private void handleSwitchPublic(CommandSender sender, String[] args) {
        if (CHANNELTREE_MAP.containsKey(args[1])) {
            if (!USERCHANNEL_MAP.containsValue(args[1])) {
                sender.sendMessage("you cannot switch because you're not in a channel yet");
                return;
            }
            sender.sendMessage("You switched from " + USERCHANNEL_MAP.get(((Player) sender).getName()) + " to: "
                    + args[1].toLowerCase());
            USERCHANNEL_MAP.put(((Player) sender).getName(), args[1]);
            return;
        }
        sender.sendMessage("channel is private! use criteria to join!");
    }

    private void handleSwitchPrivate(CommandSender sender, String[] args) {
        if (!USERCHANNEL_MAP.containsValue(args[1])) {
            sender.sendMessage("you cannot switch because you're not in a channel yet");
            return;
        }
        if (CHANNELTREE_MAP.containsKey(args[1]) && !CHANNELTREE_MAP.containsValue(null)) {
            sender.sendMessage("You switched from " + USERCHANNEL_MAP.get(((Player) sender).getName()) + " to: "
                    + args[1].toLowerCase());
            USERCHANNEL_MAP.put(((Player) sender).getName(), args[1]);
            return;
        }
        sender.sendMessage("channel is public!");
    }

    private void handleChannelGet(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                handleChannelGetSelf(sender);
                break;
            case 2:
                UUID target = null;
                target = Utils.getPlayerUUIDByName(args[1]);
                Player player = Bukkit.getPlayer(target);
                if (player == sender) {
                    handleChannelGetSelf(sender);
                    return;
                }
                if (player == null) {
                    sender.sendMessage("non existant player");
                    return;
                }
                if (USERCHANNEL_MAP.containsKey(player.getName())) {
                    for (String key : USERCHANNEL_MAP.keySet()) {
                        for (String value : USERCHANNEL_MAP.values()) {
                            sender.sendMessage("user is in: "
                                    + USERCHANNEL_MAP.getOrDefault(((Player) sender).getName(),
                                            value.toString().replaceAll("\\[|\\]", ""))
                                    + " <-- value // channelname || key --> "
                                    + key.toString().replaceAll("\\[|\\]", ""));
                            return;
                        }
                    }
                }
                sender.sendMessage(player.getName() + " in no channel boi");
                break;
            default:
                sender.sendMessage("too many args");
                break;
        }
    }

    private void handleChannelGetSelf(CommandSender sender) {
        if (USERCHANNEL_MAP.get(((Player) sender).getName()) != null) {
            for (String key : USERCHANNEL_MAP.keySet()) {
                for (String value : USERCHANNEL_MAP.values()) {
                    sender.sendMessage("you're in: "
                            + USERCHANNEL_MAP.getOrDefault(((Player) sender).getName(),
                                    value.toString().replaceAll("\\[|\\]", ""))
                            + " <-- value // channelname || key --> "
                            + key.toString().replaceAll("\\[|\\]", ""));
                    return;
                }
            }
        }
        sender.sendMessage("you're in no channel boi");
        return;
    }

    private void handleChannelHelpArgs(CommandSender sender, String[] args) { // TODO: create help for whole command
        if (args.length == 1) {
            handleChannelHelpNoArgs(sender);
            return;
        }
        switch (args[2].toLowerCase()) {
            case "admin":
                if (!sender.isOp() || !PermissionManager.checkPermission(sender, "channels_admin")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                if (args.length > 2) {
                    switch (args[3].toLowerCase()) {
                        case "kick":
                            if (!PermissionManager.checkPermission(sender, "channels_admins_kick")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("kicking player out of an channel");
                            break;
                        case "join":
                            if (!PermissionManager.checkPermission(sender, "channels_admins_join")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("joining a channel without criteria even when set");
                            break;
                        case "remove":
                            if (!PermissionManager.checkPermission(sender, "channels_admins_remove")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("removing a channel with players even when not in a channel");
                            break;
                        case "switch":
                            if (!PermissionManager.checkPermission(sender, "channels_admins_switch")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("switching state of a channel even with or without criteria");
                            break;
                        case "create":
                            if (!PermissionManager.checkPermission(sender, "channels_admins_create")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("create a channel without any player with or without criteria");
                            break;
                        default:
                            sender.sendMessage("wrong argument");
                            break;
                    }
                }
                sender.sendMessage("admin related commands");
                break;
            case "create":
                if (!PermissionManager.checkPermission(sender, "channels_create")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                sender.sendMessage("using command to create channel with or without criteria to join");
                break;
            case "get":
                if (!sender.isOp() || !PermissionManager.checkPermission(sender, "channels_get_player")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                sender.sendMessage("get channel from self or from player");
                break;
            case "join":
                sender.sendMessage("using command to join any channel with or without criteria");
                break;
            case "leave":
                sender.sendMessage("using command to leave current channel");
                break;
            case "list":
                if (args.length > 2) {
                    switch (args[3].toLowerCase()) {
                        case "private":
                            if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("using command to list private channels");
                            break;
                        case "all":
                            if (!PermissionManager.checkPermission(sender, "channels_list_*")) {
                                ChatUtils.sendMsgNoPerm(sender);
                                break;
                            }
                            sender.sendMessage("using command to list all available channels");
                            break;
                        default:
                            sender.sendMessage("wrong arguments");
                            break;
                    }
                }
                sender.sendMessage("using command to list channels");
                break;
            case "remove":
                if (!PermissionManager.checkPermission(sender, "channels_remove")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    break;
                }
                sender.sendMessage("using command to remove current channel the player is in");
                break;
            case "switch":
                sender.sendMessage("using command to switch state of channel with or without criteria");
                break;
            default:
                sender.sendMessage("help provided here");
                break;
        }
    }

    private void handleChannelLeave(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                if (args[1].equalsIgnoreCase(forbittenName)) {
                    sender.sendMessage("its a placeholder");
                    return;
                }
                handleLeave(sender, args);
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    private void handleLeave(CommandSender sender, String[] args) {
        if (CHANNELTREE_MAP.containsKey(args[1])) {
            if (USERCHANNEL_MAP.containsValue(args[1])) {
                sender.sendMessage(USERCHANNEL_MAP.get(((Player) sender).getName()) + " " +
                        USERCHANNEL_MAP.containsKey(args[1])
                        + " <-- false = " + args[1]);
                USERCHANNEL_MAP.remove(((Player) sender).getName(), args[1]);
                return;
            }
            return;
        }
        if (defaultChannels.contains(args[1])) {
            sender.sendMessage("couldn't leave");
            return;
        }
        if (args[1].equalsIgnoreCase(forbittenName)) {
            sender.sendMessage("its a placeholder");
            return;
        }
        sender.sendMessage("channel doesn't exist");
        return;
    }

    private void handleChannelJoin(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                sender.sendMessage("channel name?");
                break;
            case 2:
                if (args[1].equalsIgnoreCase(forbittenName)) {
                    sender.sendMessage("its a placeholder");
                    return;
                }
                if (CHANNELTREE_MAP.containsKey(args[1])) {
                    handleJoinPublic(sender, args);
                    return;
                }
                sender.sendMessage("channel non existant");
                break;
            case 3:
                if (args[1].equalsIgnoreCase(forbittenName)) {
                    sender.sendMessage("its a placeholder");
                    return;
                }
                handleJoinPublic(sender, args);
                break;
            case 4:
                if (args[1].equalsIgnoreCase(forbittenName)) {
                    sender.sendMessage("its a placeholder");
                    return;
                }
                handleJoinPrivate(sender, args);
                break;
            default:
                sender.sendMessage("too many args");
        }
    }

    private void handleJoinMap(CommandSender sender, String key, HashMap<String, String> hashymap) {
        if (!hashymap.containsKey(key)) {
            sender.sendMessage("channel not created yet");
            return;
        }
        if (!USERCHANNEL_MAP.containsValue(key)) {
            USERCHANNEL_MAP.put(((Player) sender).getName(), key);
            sender.sendMessage(
                    USERCHANNEL_MAP.get(((Player) sender).getName()) + " " +
                            USERCHANNEL_MAP.containsKey(key)
                            + " <-- true = " + key);
            return;
        }
        sender.sendMessage("you're already in this channel!");
    }

    private void handleJoinPrivate(CommandSender sender, String[] args) {
        if (CHANNELTREE_MAP.containsKey(args[1]) && CHANNELTREE_MAP.containsValue(null)) {
            sender.sendMessage("this is a public channel!");
            return;
        }
        if (USERCHANNEL_MAP.containsValue(args[1]) && CHANNELTREE_MAP.containsKey(args[1])) {
            sender.sendMessage("you already joined this channel!");
            return;
        }
        handleJoinMap(sender, args[1], CHANNELTREE_MAP);
    }

    private void handleJoinPublic(CommandSender sender, String[] args) {
        if (CHANNELTREE_MAP.containsKey(args[1]) && !CHANNELTREE_MAP.containsValue(null)) {
            sender.sendMessage("this is a locked channel!");
            return;
        }
        if (USERCHANNEL_MAP.containsValue(args[1]) && CHANNELTREE_MAP.containsKey(args[1])) {
            sender.sendMessage("you already joined this channel!");
            return;
        }
        handleJoinMap(sender, args[1], CHANNELTREE_MAP);
    }

    private void handleChannelList(CommandSender sender, String[] args) {
        if (args.length == 1) {
            handleListAll(sender);
            return;
        }
        switch (args[1].toLowerCase()) {
            case "private":
                handleListPrivate(sender);
                break;
            case "all":
                handleListAll(sender);
                break;
            default:
                handleListPublic(sender);
                break;
        }
    }

    ArrayList<String> channelsPublic = new ArrayList<>();

    private void handleListPublic(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_public")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        for (Map.Entry<String, String> e : CHANNELTREE_MAP.entrySet()) {
            if (e.getValue() != null) {
                continue;
            }
            channelsPublic.add(e.getKey());
        }
        Collections.sort(channelsPublic);
        sender.sendMessage("List of all public channels: " + channelsPublic.toString().replaceAll("\\[|\\]", ""));
    }

    ArrayList<String> channelsPrivate = new ArrayList<>();

    private void handleListPrivate(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_private")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        for (Map.Entry<String, String> e : CHANNELTREE_MAP.entrySet()) {
            if (e.getValue() == null) {
                continue;
            }
            channelsPrivate.add(e.getKey());
        }
        Collections.sort(channelsPrivate);
        sender.sendMessage("List of all private channels: " + channelsPrivate.toString().replaceAll("\\[|\\]", ""));
    }

    private void handleListAll(CommandSender sender) {
        if (!PermissionManager.checkPermission(sender, "channels_list_*")) {
            ChatUtils.sendMsgNoPerm(sender);
            return;
        }
        handleListPublic(sender);
        handleListPrivate(sender);
        sender.sendMessage("List of all default channels: " +
                SortList.printAList(defaultChannels));
    }

    private void handleChannelRemove(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen löschen");
            return;
        }
        if (args.length == 2) {
            if (CHANNELTREE_MAP.isEmpty()) {
                sender.sendMessage("no channel to remove");
                return;
            }
            removeChannel(sender, args[1], CHANNELTREE_MAP);
            return;
        }
        sender.sendMessage(args[1] + " does not exist");
        return;
    }

    private void removeChannel(CommandSender sender, String key, HashMap<String, String> channel) {
        if (channel.containsKey(key)) {
            sender.sendMessage(key + " got removed");
            channel.remove(key);
            for (Player player : Bukkit.getOnlinePlayers()) {
                USERCHANNEL_MAP.put(player.getName(), defaultCh); // TODO: should be added to joinevent too
            }
            return;
        }
    }

    private void handleChannelCreate(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("eig. zum channel namen erstellen");
            return;
        }
        if (name.contains(args[1])) {
            sender.sendMessage("it's a placeholder --> " + args[1]);
            return;
        }
        if (args.length == 2) {
            if (defaultChannels.contains(args[1].toLowerCase())) {
                sender.sendMessage("not allowed to create " + args[1] + " because created already");
                return;
            }
            handleCreatePublic(sender, args);
            return;
        }
        handleCreatePrivate(sender, args);
        return;
    }

    private void handleCreatePrivate(CommandSender sender, String[] args) {
        if (CHANNELTREE_MAP.containsKey(args[1])) {
            if (CHANNELTREE_MAP.get(args[1]) == null) {
                sender.sendMessage("nope, exists already as public!");
                return;
            }
            sender.sendMessage("nope, exists already as private!");
            return;
        }
        CHANNELTREE_MAP.put(args[1], args[2]);
        USERCHANNEL_MAP.put(((Player) sender).getName(), args[1]);
        sender.sendMessage("done creating Channel " + args[1] + " with "
                + CHANNELTREE_MAP.getOrDefault(args[1], args[2]) + " created");
    }

    private void handleCreatePublic(CommandSender sender, String[] args) {
        if (CHANNELTREE_MAP.containsKey(args[1])) {
            if (CHANNELTREE_MAP.get(args[1]) != null) {
                sender.sendMessage("nope, exists already as private!");
                return;
            }
            sender.sendMessage("nope, exists already as public!");
            return;
        }
        CHANNELTREE_MAP.put(args[1], null);
        USERCHANNEL_MAP.put(((Player) sender).getName(), args[1]);
        sender.sendMessage("done creating " + args[1]);
    }

/**
 * USERCHANNEL_MAP:
 * This map should contain usernames as keys and channelnames as values.
 * It will be used to say which player is in which channel.
 * channelname will replace value at the key of the playername if you join another channel. Doesn't check if channel is public or private.
 *
 * CHANNELTREE_MAP:
 * This map should contain channelname as keys and criteria as null or as args.
 * It will be used to filter channels when they are created - if value = null it will show as public, if value != null it will show as private
 * It will be used for the /channel join <name> <criteria> to join private channels if not listed as public.
 */

    public static final Map<String/* username // K */, String/* channelname // V */> USERCHANNEL_MAP = new HashMap<String, String>();
    public static final HashMap<String/* channelname // K */, String/* join criteria or null // V */> CHANNELTREE_MAP = new HashMap<>();

    public static final ArrayList<String> allChannels = CHANNELTREE_MAP.keySet().stream()
            .collect(Collectors.toCollection(ArrayList::new));
    public static final ArrayList<String> channelvalue = CHANNELTREE_MAP.values().stream()
            .collect(Collectors.toCollection(ArrayList::new));

    public static final ArrayList<String> allusers = USERCHANNEL_MAP.keySet().stream()
            .collect(Collectors.toCollection(ArrayList::new));
    public static final ArrayList<String> userchannel = USERCHANNEL_MAP.values().stream()
            .collect(Collectors.toCollection(ArrayList::new));

    public static final ArrayList<String> defaultChannels = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            // only for main channels to obtain, cannot be removed
            add("support"); /* /channel set support */
            add("default"); /* /channel set default */
            add("admin"); /* /channel set admin */
            add("moderator"); /* /channel set moderator */
        }
    };

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

    ArrayList<String> labelsHelp = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            addAll(labelsAdmins);
        }
    };

    ArrayList<String> labelsArgsAdmins = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            addAll(labelsArgs);
            remove("list");
            remove("leave");
        }
    };

    ArrayList<String> criteria = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("<criteria>");
        }
    };

    ArrayList<String> name = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("<name>");
        }
    };

    ArrayList<String> playernames = new ArrayList<String>();
    Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String alias, @NotNull String[] args) {
        /**
         * TODO: return arraylists but aren't
         * following arraylists won't show up atm:
         * allChannels, playernames, "null"
         */

        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (int i = 0; i < players.length; i++) {
            playernames.add(players[i].getName());
        }

        switch (args.length) {
            case 1:
                if (sender.isOp() || PermissionManager.checkPermission(sender, "channels_admin")) {
                    return labelsAdmins;
                }
                return labels;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "switch":
                    case "join":
                        return allChannels;
                    case "admin":
                        if (sender.isOp() || PermissionManager.checkPermission(sender, "channels_admin")) {
                            return labelsArgsAdmins;
                        }
                        return null;
                    case "help":
                        return labelsHelp;
                    case "get":
                        Collections.sort(playernames);
                        return playernames;
                    case "create":
                    case "remove":
                        return name;
                    default:
                        return null;
                }
            case 3:
                switch (args[0].toLowerCase()) {
                    case "admin":
                        switch (args[1].toLowerCase()) {
                            case "create":
                                return name;
                            case "join":
                            case "remove":
                                Collections.sort(allChannels);
                                return allChannels; // FIXME: doesn't show up any channels
                            default:
                                return null;
                        }
                    case "create":
                        return criteria;
                    default:
                        return null;
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
