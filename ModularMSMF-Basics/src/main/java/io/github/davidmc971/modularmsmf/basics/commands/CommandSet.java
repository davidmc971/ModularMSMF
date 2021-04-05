package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        if (PermissionManager.checkPermission(sender, "set_use")) {
            if(args.length == 0){
                return helpSet(sender, command, label, args);
            }
            switch (args[1].toLowerCase()) {
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
            if (!PermissionManager.checkPermission(sender, "set_use_life")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            //TODO[epic=SetCommand] to code life below
        }
        if (args[0].equalsIgnoreCase("food")) {
            if (!PermissionManager.checkPermission(sender, "set_use_food")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            //TODO[epic=SetCommand] to code food below
        }
        if(args[0].equalsIgnoreCase("sat") || args[0].equalsIgnoreCase("saturation")){
            if(!PermissionManager.checkPermission(sender, "set_use_saturation")){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            //TODO[epic=SetCommand] to code saturation below
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
        return false;
    }
}
