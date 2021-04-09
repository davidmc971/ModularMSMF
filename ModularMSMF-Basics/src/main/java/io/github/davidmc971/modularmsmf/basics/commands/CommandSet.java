package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
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

    int i = 20;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (PermissionManager.checkPermission(sender, "set_use")) {
            switch (args[0].toLowerCase()) {
            case "help":
                return helpSet(sender, command, label, args);
            case "life":
            case "food":
            case "saturation":
            case "sat":
                return subSet(sender, command, label, args); // for subcommands like "/set args[1] (life, saturation,
                                                             // food,...)"
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
                break;
            }
        }
        return true;
    }

    private boolean subSet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("life")) {
            // /set life
            if (!PermissionManager.checkPermission(sender, "set_use_life")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage("1," + args[0]);
            }
            if (args.length >= 2) { // for self
                return setLife(sender, command, label, args);
            }
        }
        if (args[0].equalsIgnoreCase("food")) {
            if (!PermissionManager.checkPermission(sender, "set_use_food")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            // TODO[epic=SetCommand] to code food below
        }
        if (args[0].equalsIgnoreCase("sat") || args[0].equalsIgnoreCase("saturation")) {
            if (!PermissionManager.checkPermission(sender, "set_use_saturation")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            // TODO[epic=SetCommand] to code saturation below
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
            if(i == 0){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.set.nosuicide");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if(i >= 21){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.set.upperlimit");
                return true;
            }
            ((Damageable) sender).setHealth(i);
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.set.life.done");
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
            Player setlife = Bukkit.getPlayer(target);
            if(i == 0){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.set.nokill");
                return true;
            }
            if ((i < 0)) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.set.nonnegative");
                return true;
            }
            if(i >= 21){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.set.upperlimit");
                return true;
            }
            if(setlife == sender){
                setlife.setHealth(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                "basicsmodule.commands.set.life.done");
                return true;
            }
            if(target == null){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.unknown");
                return true;
            } else {
                setlife.setHealth(i);
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basicsmodule.commands.set.life.others", "_player", args[2]);
                Utils.sendMessageWithConfiguredLanguage(plugin, setlife, ChatFormat.SUCCESS, "basicsmodule.commands.set.life.done");
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
