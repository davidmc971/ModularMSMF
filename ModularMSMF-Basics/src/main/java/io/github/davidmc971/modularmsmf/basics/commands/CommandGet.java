package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

public class CommandGet implements IModularMSMFCommand {

    /**
     * @author Lightkeks
     *
     *         testing purposes atm
     *         Showing up statistics about a player, if needed
     * 
     */

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use")) {
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            return helpGet(sender, command, label, args);
        }
        switch (args[0].toLowerCase()) {
            case "help":
                helpGet(sender, command, label, args);
                break;
            case "life":
            case "health":
                handleLife(sender, command, label, args);
                break;
            case "food":
                handleFood(sender, command, label, args);
                break;
            case "saturation":
            case "sat":
                handleSaturation(sender, command, label, args);
                break;
            case "exp":
                handleExp(sender, command, label, args);
                break;
            case "level":
            case "lvl":
                handleLevel(sender, command, label, args);
                break;
            case "ip":
                handleIp(sender, command, label, args);
                break;
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private void handleIp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_ip")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.get.ip.usage");
        }
        if (args.length == 2) {
            getIp(sender, command, label, args);
        }
        if (args.length >= 3) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_life")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.get.life.usage");
        }
        if (args.length == 2) {
            getLife(sender, command, label, args);
        }
        if (args.length >= 3) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_food")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.get.food.usage");
        }
        if (args.length == 2) {
            getFood(sender, command, label, args);
        }
        if (args.length >= 3) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_saturation")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.get.saturation.usage");
        }
        if (args.length == 2) {
            getSaturation(sender, command, label, args);
        }
        if (args.length >= 3) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_exp")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.get.exp.usage");
        }
        if (args.length == 2) {
            getExp(sender, command, label, args);
        }
        if (args.length >= 3) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_level")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.get.level.usage");
        }
        if (args.length == 2) {
            getLevel(sender, command, label, args);
        }
        if (args.length >= 3) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private boolean helpGet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        sender.sendMessage("/get help");
        sender.sendMessage("/get life");
        sender.sendMessage("/get food");
        sender.sendMessage("/get (saturation/sat)");
        sender.sendMessage("/get exp");
        sender.sendMessage("/get (level/lvl)");
        sender.sendMessage("/get ip");
        return true;
    }

    private boolean getIp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
            return true;
        }
        if (player == sender) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.get.ip.done", "_value", player.getAddress().getAddress().getHostAddress());
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.get.ip.others", "_player", player.getName(), "_value",
                player.getAddress().getAddress().toString());
        return true;
    }

    private boolean getLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
            return true;
        }
        int i = player.getLevel();
        String s = String.valueOf(i);
        if (player == sender) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.get.level.done", "_value", s);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.get.level.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
            return true;
        }
        Float f = player.getExp() * 100;
        int i = f.intValue();
        String s = String.valueOf(i);
        if (player == sender) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.get.exp.done", "_value", s);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.get.exp.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
            return true;
        }
        Float f = player.getSaturation();
        int i = f.intValue();
        String s = String.valueOf(i);
        if (player == sender) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.get.saturation.done", "_value", s);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.get.saturation.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
            return true;
        }
        int i = player.getFoodLevel();
        String s = String.valueOf(i);
        if (player == sender) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.get.food.done", "_value", s);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.get.food.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Util.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
            return true;
        }
        Double d = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        int i = d.intValue();
        String att_string = String.valueOf(i);
        if (player == sender) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.get.life.done", "_value", att_string);
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "commands.get.life.others", "_player", player.getName(), "_value", att_string);
        return true;
    }

    @Override
    public String Label() {
        return "get";
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
