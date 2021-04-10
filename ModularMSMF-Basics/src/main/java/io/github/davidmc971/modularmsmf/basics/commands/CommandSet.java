package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandSet implements IModularMSMFCommand {

    /**
     * @author Lightkeks
     */
    // TODO[epic=code needed] coding stuff here - set setting values like exp,
    // foodlevel, saturation, life etc.

    ModularMSMFCore plugin;

    public CommandSet() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
                    "coremodule.player.nopermission");
            return true;
        }
        if (args.length == 0) {
            return helpSet(sender, command, label, args);
        }
        switch (args[0].toLowerCase()) {
        case "help":
            return helpSet(sender, command, label, args);
        case "life":
        case "food":
        case "saturation":
        case "sat":
        case "exp":
        case "level":
            return subSet(sender, command, label, args);
        default:
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.arguments.invalid");
            break;
        }
        return true;
    }

    private boolean subSet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("life")) {
            if (!PermissionManager.checkPermission(sender, "set_use_life")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.set.life.usage");
            }
            if (args.length >= 2) { // for self
                return setLife(sender, command, label, args);
            }
            if (args.length >= 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("food")) {
            if (!PermissionManager.checkPermission(sender, "set_use_food")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.set.food.usage");
            }
            if (args.length >= 2) {
                return setFood(sender, command, label, args);
            }
        }
        if (args[0].equalsIgnoreCase("sat") || args[0].equalsIgnoreCase("saturation")) {
            if (!PermissionManager.checkPermission(sender, "set_use_saturation")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.set.saturation.usage");
            }
            if (args.length >= 2) {
                return setSaturation(sender, command, label, args);
            }
        }
        if (args[0].equalsIgnoreCase("exp")) {
            if (!PermissionManager.checkPermission(sender, "set_use_exp")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.player.nopermission");
                return true;
            }
            if (args.length == 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                        "basicsmodule.commands.set.exp.usage");
            }
            if (args.length >= 2) {
                return setExp(sender, command, label, args);
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
                        "basicsmodule.commands.set.level.usage");
            }
            if (args.length >= 2) {
                return setLevel(sender, command, label, args);
            }
        }
        return true;
    }

    private boolean setLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
                int i = 0;
                if (args.length == 2) {
                    try {
                        i = Integer.parseInt(args[1]);
                    } catch (NumberFormatException exception) {
                        exception.printStackTrace(); // debug only
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.noint");
                        return true;
                    }
                    if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                        return true;
                    }
                    if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                        return true;
                    }
                    if ((i < 0)) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.nonnegative");
                        return true;
                    }
                    if (i >= 101) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.toohigh");
                        return true;
                    }
                    ((Player) sender).setLevel(i);
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                            "basicsmodule.commands.set.level.done", "_args", args[1]);
                    return true;
                }
                if (args.length == 3) {
                    UUID target = null;
                    target = Utils.getPlayerUUIDByName(args[2]);
                    Player set = Bukkit.getPlayer(target);
                    try {
                        i = Integer.parseInt(args[1]);
                    } catch (NumberFormatException exception) {
                        exception.printStackTrace(); // debug only
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.noint");
                        return true;
                    }
                    if (set != null && set.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                        return true;
                    }
                    if (set != null && set.getGameMode() == GameMode.CREATIVE) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                        return true;
                    }
                    if ((i < 0)) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.nonnegative");
                        return true;
                    }
                    if (i >= 101) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.toohigh");
                        return true;
                    }
                    if (set == sender) {
                        set.setLevel(i);
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                "basicsmodule.commands.set.level.done", "_args", args[1]);
                        return true;
                    }
                    if (target == null) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
                        return true;
                    } else {
                        set.setLevel(i);
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                "basicsmodule.commands.set.level.others", "_args", args[1], "_player", args[2]);
                        Utils.sendMessageWithConfiguredLanguage(plugin, set, ChatFormat.SUCCESS,
                                "basicsmodule.commands.set.level.done", "_args", args[1]);
                    }
                }
                return true;
    }

    private boolean setExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        float f = 0;
        if (args.length == 2) {
            try {
                f = Float.parseFloat(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if ((f < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (f > 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.toohigh");
                return true;
            }
            ((Player) sender).setExp(f);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.exp.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player set = Bukkit.getPlayer(target);
            try {
                f = Float.parseFloat(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (set != null && set.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (set != null && set.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if ((f < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (f > 1) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.toohigh");
                return true;
            }
            if (set == sender) {
                set.setExp(f);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.exp.done", "_args", args[1]);
                return true;
            }
            if (target == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
                return true;
            } else {
                set.setExp(f);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.exp.others", "_args", args[1], "_player", args[2]);
                Utils.sendMessageWithConfiguredLanguage(plugin, set, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.exp.done", "_args", args[1]);
            }
        }
        return true;
    }

    private boolean setSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        int i = 0;
        if (args.length == 2) {
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (i >= 81) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.toohigh");
                return true;
            }
            ((Player) sender).setSaturation(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.saturation.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player set = Bukkit.getPlayer(target);
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (set != null && set.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (set != null && set.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (i >= 81) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.toohigh");
                return true;
            }
            if (set == sender) {
                set.setSaturation(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.saturation.done", "_args", args[1]);
                return true;
            }
            if (target == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
                return true;
            } else {
                set.setSaturation(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.saturation.others", "_args", args[1], "_player", args[2]);
                Utils.sendMessageWithConfiguredLanguage(plugin, set, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.saturation.done", "_args", args[1]);
            }
        }
        return true;
    }

    private boolean setFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        int i = 0;
        if (args.length == 2) {
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (i >= 21) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.upperlimit");
                return true;
            }
            ((Player) sender).setFoodLevel(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.food.done");
            return true;
        }
        if (args.length == 3) {
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player set = Bukkit.getPlayer(target);
            if (set != null && set.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (set != null && set.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (i >= 21) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.upperlimit");
                return true;
            }
            if (set == sender) {
                set.setFoodLevel(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.food.done");
                return true;
            }
            if (target == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
                return true;
            } else {
                set.setFoodLevel(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.food.others", "_player", args[2]);
                Utils.sendMessageWithConfiguredLanguage(plugin, set, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.food.done");
            }
            return true;
        }
        return true;
    }

    private boolean setLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        int i = 0;
        if (args.length == 2) {
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if (i == 0) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nosuicide");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (i >= 21) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.upperlimit");
                return true;
            }
            ((Player) sender).setHealth(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.life.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player set = Bukkit.getPlayer(target);
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                exception.printStackTrace(); // debug only
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.noint");
                return true;
            }
            if (set != null && set.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return true;
            }
            if (set != null && set.getGameMode() == GameMode.CREATIVE) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative");
                return true;
            }
            if (i == 0) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nokill");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if (i >= 21) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.upperlimit");
                return true;
            }
            if (set == sender) {
                set.setHealth(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.life.done", "_args", args[1]);
                return true;
            }
            if (target == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
                return true;
            } else {
                set.setHealth(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.life.others", "_args", args[1], "_player", args[2]);
                Utils.sendMessageWithConfiguredLanguage(plugin, set, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.life.done", "_args", args[1]);
            }
            return true;
        }
        return true;
    }

    private boolean helpSet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        sender.sendMessage("help set");
        return true;
    }

    @Override
    public String Label() {
        return "set";
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
