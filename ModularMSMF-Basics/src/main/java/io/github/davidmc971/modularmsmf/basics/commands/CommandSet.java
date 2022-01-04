package io.github.davidmc971.modularmsmf.basics.commands;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 * @ Any valuable setting can be changed here - each player or each
 * setting can be set here.
 * @see CommandGet for more
 *
 *      Trying to clean up code as well...
 */
@NotNull
public class CommandSet implements IModularMSMFCommand {

    ModularMSMFCore plugin;

    public CommandSet() {
        plugin = ModularMSMFCore.Instance();
    }

    private static DecimalFormat df = new DecimalFormat("0.00");
    private int i;
    private float f;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            // return helpSet(sender, command, label, args);
            sender.sendMessage("test");
            return true;
        }
        switch (args[0].toLowerCase()) {
            // case "help":
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
            case "exp":
                handleExp(sender, command, label, args);
            case "level":
            case "lvl":
                handleLevel(sender, command, label, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.invalid");
                break;
        }
        return true;
    }

    private void handleLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_level")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                    "basicsmodule.commands.set.level.usage");
        } else if (args.length <= 3) {
            setLevel(sender, command, label, args);
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_exp")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                    "basicsmodule.commands.set.exp.usage");
        } else if (args.length <= 3) {
            setExp(sender, command, label, args);
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_saturation")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                    "basicsmodule.commands.set.saturation.usage");
        } else if (args.length <= 3) {
            setSaturation(sender, command, label, args);
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private void handleFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_food")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                    "basicsmodule.commands.set.food.usage");
        } else if (args.length <= 3) {
            setFood(sender, command, label, args);
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    public void handleLife(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_life")) {
            ChatUtils.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
                    "basicsmodule.commands.set.life.usage");
        } else if (args.length <= 3) {
            setLife(sender, command, label, args);
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.arguments.toomany");
        }
    }

    private boolean setLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        try {
            i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.noint");
            return true;
        }
        if (i == 0) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.nosuicide");
            return true;
        }
        if (i < 0) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.nonnegative");
            return true;
        }
        if (i >= 21) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.upperlimit");
            return true;
        }
        if (args.length == 2) {
            if (!isSenderEligible(sender)) {
                return false;
            }
            ((Player) sender).setHealth(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.life.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_life_others")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (!isPlayerEligible(sender, player)) {
                return false;
            }
            if (player == sender) {
                player.setHealth(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.life.done", "_args", args[1]);
                return true;
            }
            player.setHealth(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.life.others", "_args", args[1], "_player",
                    player.getName());
            Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.life.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean setFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        try {
            i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.noint");
            return true;
        }
        if (i < 0) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.nonnegative");
            return true;
        }
        if (i >= 21) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.upperlimit");
            return true;
        }
        if (args.length == 2) {
            if (!isSenderEligible(sender)) {
                return false;
            }
            ((Player) sender).setFoodLevel(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.food.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_food_others")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (!isPlayerEligible(sender, player)) {
                return false;
            }
            if (player == sender) {
                player.setFoodLevel(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.food.done", "_args", args[1]);
                return true;
            }
            player.setFoodLevel(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.food.others", "_args", args[1], "_player",
                    player.getName());
            Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.food.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean setSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        try {
            i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.noint");
            return true;
        }
        if (i < 0) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.nonnegative");
            return true;
        }
        if (i >= 81) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.toohigh");
            return true;
        }
        if (args.length == 2) {
            if (!isSenderEligible(sender)) {
                return false;
            }
            ((Player) sender).setSaturation(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.saturation.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_saturation_others")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (!isPlayerEligible(sender, player)) {
                return false;
            }
            if (player == sender) {
                player.setSaturation(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.saturation.done", "_args", args[1]);
                return true;
            }
            player.setSaturation(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.saturation.others", "_args", args[1], "_player",
                    player.getName());
            Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.saturation.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean setExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        String f_exp = args[1];
        try {
            Float.parseFloat(f_exp);
        } catch (NumberFormatException e) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.noint");
            return true;
        }
        Float f_val = Float.parseFloat(f_exp);
        f = f_val / 100;
        if (f < 0) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.nonnegative");
            return true;
        }
        if (f > 100) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.toohigh");
            return true;
        }
        if (args.length == 2) {
            if (!isSenderEligible(sender)) {
                return false;
            }
            ((Player) sender).setExp(f);
            df.setRoundingMode(RoundingMode.UP);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.exp.done", "_args", df.format(f_val));
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_exp_others")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (!isPlayerEligible(sender, player)) {
                return false;
            }
            if (player == sender) {
                ((Player) sender).setExp(f);
                df.setRoundingMode(RoundingMode.UP);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.exp.done", "_args", df.format(f_val));
                return true;
            }
            df.setRoundingMode(RoundingMode.UP);
            player.setExp(f);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.exp.others", "_args", df.format(f_val), "_player",
                    player.getName());
            Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.exp.done", "_args", df.format(f_val));
            return true;
        }
        return true;
    }

    private boolean setLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        try {
            i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.noint");
            return true;
        }
        if (i < 0) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.nonnegative");
            return true;
        }
        if (i >= 101) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.set.toohigh");
            return true;
        }
        if (args.length == 2) {
            if (!isSenderEligible(sender)) {
                return false;
            }
            ((Player) sender).setLevel(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.level.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Utils.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_level_others")) {
                ChatUtils.sendMsgNoPerm(sender);
                return true;
            }
            if (!isPlayerEligible(sender, player)) {
                return false;
            }
            if (player == sender) {
                ((Player) sender).setLevel(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                        "basicsmodule.commands.set.level.done", "_args", args[1]);
                return true;
            }
            player.setLevel(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.level.others", "_args", args[1], "_player", player.getName());
            Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.level.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean isPlayerEligible(@NotNull CommandSender sender, @NotNull Player player) {
        if (player == sender)
            return isSenderEligible(sender);
        for (Player plron : Bukkit.getOnlinePlayers()) {
            if (plron == player) {
                switch (player.getGameMode()) {
                    case CREATIVE:
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.creative.others",
                                "_player", player.getName());
                        return false;
                    case SPECTATOR:
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.spectator.others",
                                "_player", player.getName());
                        return false;
                    default:
                        break;
                }
                return true;
            }
        }
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                "coremodule.player.notonline");
        return false;
    }

    private boolean isSenderEligible(@NotNull CommandSender sender) {
        if (sender instanceof Player) {
            switch (((Player) sender).getGameMode()) {
                case CREATIVE:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.creative.self");
                    return false;
                case SPECTATOR:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.spectator.self");
                    return false;
                default:
                    break;
            }
            if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.peaceful");
                return false;
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return false;
        }
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