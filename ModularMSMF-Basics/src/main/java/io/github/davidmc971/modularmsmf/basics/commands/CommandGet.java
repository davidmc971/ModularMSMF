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

    private String s_get_double = String.valueOf(double.class);
    private String s_get_float = String.valueOf(float.class);
    private String s_get_int = String.valueOf(int.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "get_use")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
                    "coremodule.player.nopermission");
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
            return subGet(sender, command, label, args);
        default:
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.invalid");
            break;
        }
        return true;
    }

    private boolean subGet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("life")) {
            // set life
            if (!PermissionManager.checkPermission(sender, "get_use_life")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
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
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
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
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
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
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
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
            if (!PermissionManager.checkPermission(sender, "set_use_level")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
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
        return true;
    }

    private boolean getLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        int d_level = player.getLevel();
        s_get_int = String.valueOf(d_level);
        if (sender instanceof ConsoleCommandSender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "coremodule.noconsole");
            return true;
        }
        if (player != null && player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
            return true;
        }
        if (player != null && player.getGameMode() == GameMode.CREATIVE) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.level.done", "_value", s_get_int);
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.level.others", "_player", args[1], "_value", s_get_int);
        }
        return true;
    }

    private boolean getExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        float d_exp = player.getExp();
        s_get_float = String.valueOf(d_exp);
        if (sender instanceof ConsoleCommandSender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "coremodule.noconsole");
            return true;
        }
        if (player != null && player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
            return true;
        }
        if (player != null && player.getGameMode() == GameMode.CREATIVE) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.exp.done", "_value", s_get_float);
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.exp.others", "_player", args[1], "_value", s_get_float);
        }
        return true;
    }

    private boolean getSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        int d_saturation = (int) player.getSaturation();
        s_get_int = String.valueOf(d_saturation);
        if (sender instanceof ConsoleCommandSender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "coremodule.noconsole");
            return true;
        }
        if (player != null && player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
            return true;
        }
        if (player != null && player.getGameMode() == GameMode.CREATIVE) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.saturation.done", "_value", s_get_int);
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.saturation.others", "_player", args[1], "_value", s_get_int);
        }
        return true;
    }

    private boolean getFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        int d_food = (int) player.getFoodLevel();
        s_get_int = String.valueOf(d_food);
        if (sender instanceof ConsoleCommandSender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "coremodule.noconsole");
            return true;
        }
        if (player != null && player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
            return true;
        }
        if (player != null && player.getGameMode() == GameMode.CREATIVE) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.food.done", "_value", s_get_int);
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.food.others", "_player", args[1], "_value", s_get_int);
        }
        return true;
    }

    private boolean getLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[1]);
        Player player = Bukkit.getPlayer(target);
        int d_life = (int) player.getHealth();
        s_get_int = String.valueOf(d_life);
        if (sender instanceof ConsoleCommandSender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "coremodule.noconsole");
            return true;
        }
        if (player != null && player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
            return true;
        }
        if (player != null && player.getGameMode() == GameMode.CREATIVE) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
            return true;
        }
        if (player == sender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.life.done", "_value", s_get_int);
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.get.life.others", "_player", args[1], "_value", s_get_int);
        }
        return true;
    }

    private boolean helpGet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        sender.sendMessage("help get");
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
