package io.github.davidmc971.modularmsmf.basics.commands;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

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

    private final static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use")) {
            ChatUtil.sendMsgNoPerm(sender);
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
                break;
            case "exp":
                handleExp(sender, command, label, args);
                break;
            case "level":
            case "lvl":
                handleLevel(sender, command, label, args);
                break;
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private void handleLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_level")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.set.level.usage");
        } else if (args.length <= 3) {
            setLevel(sender, command, label, args);
        } else {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_exp")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.set.exp.usage");
        } else if (args.length <= 3) {
            setExp(sender, command, label, args);
        } else {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_saturation")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.set.saturation.usage");
        } else if (args.length <= 3) {
            setSaturation(sender, command, label, args);
        } else {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private void handleFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_food")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.set.food.usage");
        } else if (args.length <= 3) {
            setFood(sender, command, label, args);
        } else {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    public void handleLife(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "set_use_life")) {
            ChatUtil.sendMsgNoPerm(sender);
        }
        if (args.length == 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
                    "commands.set.life.usage");
        } else if (args.length <= 3) {
            setLife(sender, command, label, args);
        } else {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.arguments.toomany");
        }
    }

    private boolean setLife(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        double d;
        try {
            d = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.noint");
            return true;
        }
        if (d == 0) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.nosuicide");
            return true;
        }
        if (d < 0) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.nonnegative");
            return true;
        }
        if (args.length == 2) {
            if (!CommandUtil.isSenderEligible(sender, command)) {
                return false;
            }
            ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(d);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.life.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Util.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_life_others")) {
                ChatUtil.sendMsgNoPerm(sender);
                return true;
            }
            if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
                return false;
            }
            if (player == sender) {
                ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(d);
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                        "commands.set.life.done", "_args", args[1]);
                return true;
            }
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(d);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.life.others", "_args", args[1], "_player",
                    player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                    "commands.set.life.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean setFood(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        int i;
        try {
            i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.noint");
            return true;
        }
        if (i < 0) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.nonnegative");
            return true;
        }
        if (i >= 21) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.upperlimit");
            return true;
        }
        if (args.length == 2) {
            if (!CommandUtil.isSenderEligible(sender, command)) {
                return false;
            }
            ((Player) sender).setFoodLevel(i);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.food.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Util.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_food_others")) {
                ChatUtil.sendMsgNoPerm(sender);
                return true;
            }
            if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
                return false;
            }
            if (player == sender) {
                player.setFoodLevel(i);
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                        "commands.set.food.done", "_args", args[1]);
                return true;
            }
            player.setFoodLevel(i);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.food.others", "_args", args[1], "_player",
                    player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                    "commands.set.food.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean setSaturation(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        float f;
        try {
            f = Float.parseFloat(args[1]);
        } catch (NumberFormatException e) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.noint");
            return true;
        }
        if (f < 0) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.nonnegative");
            return true;
        }
        if (f >= 81) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.toohigh");
            return true;
        }
        if (args.length == 2) {
            if (!CommandUtil.isSenderEligible(sender, command)) {
                return false;
            }
            ((Player) sender).setSaturation(f);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.saturation.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Util.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_saturation_others")) {
                ChatUtil.sendMsgNoPerm(sender);
                return true;
            }
            if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
                return false;
            }
            if (player == sender) {
                ((Player) sender).setSaturation(f);
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                        "commands.set.saturation.done", "_args", args[1]);
                return true;
            }
            player.setSaturation(f);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.saturation.others", "_args", args[1], "_player",
                    player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                    "commands.set.saturation.done", "_args", args[1]);
            return true;
        }
        return true;
    }

    private boolean setExp(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        float f;
        String f_exp = args[1];
        try {
            Float.parseFloat(f_exp);
        } catch (NumberFormatException e) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.noint");
            return true;
        }
        Float f_val = Float.parseFloat(f_exp);
        f = f_val / 100;
        if (f < 0) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.nonnegative");
            return true;
        }
        if (f > 100) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.toohigh");
            return true;
        }
        if (args.length == 2) {
            if (!CommandUtil.isSenderEligible(sender, command)) {
                return false;
            }
            ((Player) sender).setExp(f);
            df.setRoundingMode(RoundingMode.UP);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.exp.done", "_args", df.format(f_val));
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Util.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_exp_others")) {
                ChatUtil.sendMsgNoPerm(sender);
                return true;
            }
            if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
                return false;
            }
            if (player == sender) {
                ((Player) sender).setExp(f);
                df.setRoundingMode(RoundingMode.UP);
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                        "commands.set.exp.done", "_args", df.format(f_val));
                return true;
            }
            df.setRoundingMode(RoundingMode.UP);
            player.setExp(f);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.exp.others", "_args", df.format(f_val), "_player",
                    player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                    "commands.set.exp.done", "_args", df.format(f_val));
            return true;
        }
        return true;
    }

    private boolean setLevel(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        int i;
        try {
            i = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.noint");
            return true;
        }
        if (i < 0) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.nonnegative");
            return true;
        }
        if (i >= 101) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "commands.set.toohigh");
            return true;
        }
        if (args.length == 2) {
            if (!CommandUtil.isSenderEligible(sender, command)) {
                return false;
            }
            ((Player) sender).setLevel(i);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.level.done", "_args", args[1]);
            return true;
        }
        if (args.length == 3) {
            UUID target = null;
            target = Util.getPlayerUUIDByName(args[2]);
            Player player = Bukkit.getPlayer(target);
            if (!PermissionManager.checkPermission(sender, "set_use_level_others")) {
                ChatUtil.sendMsgNoPerm(sender);
                return true;
            }
            if (!CommandUtil.isPlayerEligible(sender, player, command, args)) {
                return false;
            }
            if (player == sender) {
                ((Player) sender).setLevel(i);
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                        "commands.set.level.done", "_args", args[1]);
                return true;
            }
            player.setLevel(i);
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                    "commands.set.level.others", "_args", args[1], "_player", player.getName());
            Util.sendMessageWithConfiguredLanguage(player, ChatFormat.SUCCESS,
                    "commands.set.level.done", "_args", args[1]);
            return true;
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