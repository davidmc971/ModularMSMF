package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandGet implements IModularMSMFCommand {

    /**
     * @author Lightkeks
     *
     *         testing purposes atm
     *         Showing up statistics about a player, if needed
     * 
     */

    ModularMSMFCore plugin;

    public CommandGet() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
                return helpGet(sender, command, label, args);
            case 1:
            case 2:
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
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.arguments.invalid");
                        break;
                }
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.invalid");
                break;
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
        }
        return true;
    }

    private void handleIp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_ip")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "basicsmodule.commands.get.ip.usage");
        }
        if (args.length == 2) {
            getIp(sender, command, label, args);
        }
        if (args.length >= 3) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_life")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "basicsmodule.commands.get.life.usage");
        }
        if (args.length == 2) {
            getLife(sender, command, label, args);
        }
        if (args.length >= 3) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_food")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "basicsmodule.commands.get.food.usage");
        }
        if (args.length == 2) {
            getFood(sender, command, label, args);
        }
        if (args.length >= 3) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_saturation")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "basicsmodule.commands.get.saturation.usage");
        }
        if (args.length == 2) {
            getSaturation(sender, command, label, args);
        }
        if (args.length >= 3) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_exp")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "basicsmodule.commands.get.exp.usage");
        }
        if (args.length == 2) {
            getExp(sender, command, label, args);
        }
        if (args.length >= 3) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use_level")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "basicsmodule.commands.get.level.usage");
        }
        if (args.length == 2) {
            getLevel(sender, command, label, args);
        }
        if (args.length >= 3) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
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
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.ip.done", "_value", player.getAddress().getAddress().getHostAddress());
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.get.ip.others", "_player", player.getName(), "_value",
                player.getAddress().getAddress().toString());
        return true;
    }

    private boolean getLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        int i = player.getLevel();
        String s = String.valueOf(i);
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.level.done", "_value", s);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.get.level.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        Float f = player.getExp() * 100;
        int i = f.intValue();
        String s = String.valueOf(i);
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.exp.done", "_value", s);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.get.exp.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        Float f = player.getSaturation();
        int i = f.intValue();
        String s = String.valueOf(i);
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.saturation.done", "_value", s);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.get.saturation.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        int i = player.getFoodLevel();
        String s = String.valueOf(i);
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.food.done", "_value", s);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.get.food.others", "_player", player.getName(), "_value", s);
        return true;
    }

    private boolean getLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        Double d = player.getHealth();
        int i = d.intValue();
        String s = String.valueOf(i);
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.life.done", "_value", s);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.get.life.others", "_player", player.getName(), "_value", s);
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
