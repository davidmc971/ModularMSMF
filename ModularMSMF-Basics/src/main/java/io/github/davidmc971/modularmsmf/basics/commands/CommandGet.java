package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
     */
    // TODO[epic=code needed] coding stuff here - get setting values like exp,
    // foodlevel, saturation, life etc.

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
        if (args.length == 0) {
            return helpGet(sender, command, label, args);
        }
        switch (args[0].toLowerCase()) {
        case "help":
            return helpGet(sender, command, label, args);
        case "life":
        case "food":
        case "saturation":
        case "sat":
        case "exp":
        case "level":
        case "lvl":
        case "ip":
            return subGet(sender, command, label, args);
        default:
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.invalid");
            break;
        }
        return true;
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

    private boolean subGet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("life")) {
            // set life
            if (!PermissionManager.checkPermission(sender, "get_use_life")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.get.life.usage");
                return true;
            }
            if (args.length == 2) {
                return getLife(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("food")) {
            if (!PermissionManager.checkPermission(sender, "get_use_food")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.get.food.usage");
                return true;
            }
            if (args.length == 2) {
                return getFood(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("sat") || args[0].equalsIgnoreCase("saturation")) {
            if (!PermissionManager.checkPermission(sender, "get_use_saturation")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.get.saturation.usage");
                return true;
            }
            if (args.length == 2) {
                return getSaturation(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("exp")) {
            if (!PermissionManager.checkPermission(sender, "get_use_exp")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.get.exp.usage");
                return true;
            }
            if (args.length == 2) {
                return getExp(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("level") || args[0].equalsIgnoreCase("lvl")) {
            if (!PermissionManager.checkPermission(sender, "get_use_level")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.get.level.usage");
                return true;
            }
            if (args.length == 2) {
                return getLevel(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("ip")) {
            if (!PermissionManager.checkPermission(sender, "get_use_ip")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.get.ip.usage");
                return true;
            }
            if (args.length == 2) {
                return getIp(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        return true;
    }

    private boolean getIp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.ip.done", "_value", player.getAddress().getAddress().getHostAddress());
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.ip.others", "_player", player.getName(), "_value",
                    player.getAddress().getAddress().toString());
        }
        return true;
    }

    private boolean getLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                return true;
            } else {
                int i = player.getLevel();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.level.done", "_value", s);
            }
        } else {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.creative.others", "_player", player.getName());
                return true;
            } else {
                int i = player.getLevel();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.level.others", "_player", player.getName(), "_value", s);
            }
        }
        return true;
    }

    private boolean getExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                Float f = player.getExp() * 100;
                int i = f.intValue();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.exp.done", "_value", s);
            }
        } else {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.creative.others", "_player", player.getName());
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                Float f = player.getExp() * 100;
                int i = f.intValue();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.exp.others", "_player", player.getName(), "_value", s);
                return true;
            }
        }
        return true;
    }

    private boolean getSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                Float f = player.getSaturation();
                int i = f.intValue();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.saturation.done", "_value", s);
            }
        } else {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.creative.others", "_player", player.getName());
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                Float f = player.getSaturation();
                int i = f.intValue();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.saturation.others", "_player", player.getName(), "_value", s);
            }
        }
        return true;
    }

    private boolean getFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                int i = player.getFoodLevel();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.food.done", "_value", s);
                return true;
            }
        } else {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.creative.others", "_player", player.getName());
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                int i = player.getFoodLevel();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.food.others", "_player", player.getName(), "_value", s);
            }
        }
        return true;
    }

    private boolean getLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        if (sender instanceof ConsoleCommandSender) { //use /stats <name> instead - for langyaml
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (player == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        }
        if (player == sender) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.self");
                return true;
            } else {
                Double d = player.getHealth();
                int i = d.intValue();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.life.done", "_value", s);
                return true;
            }
        } else {
            if (player.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.creative.others", "_player", player.getName());
                return true;
            }
            if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful.others", "_player", player.getName());
                return true;
            } else {
                Double d = player.getHealth();
                int i = d.intValue();
                String s = String.valueOf(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.get.life.others", "_player", player.getName(), "_value", s);
            }
        }
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
