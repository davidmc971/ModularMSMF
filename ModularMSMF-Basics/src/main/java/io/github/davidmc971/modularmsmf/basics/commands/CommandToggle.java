package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.util.ToggleCommandsConfig;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandToggle implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandToggle() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //// FIXME: commands won't load properly
        if (!PermissionManager.checkPermission(sender, "toggle_use")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        if (args.length > 3) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
            return true;
        }
        switch ((args.length < 1) ? "help" : args[0].toLowerCase()) {
        case "help":
            return helpSub(sender, args);
        case "all":
            return allSub(sender, args);
        case "list":
        return listSub(sender, args);
        case "teleport":
        case "setspawn":
        case "back":
        case "test":
            if (!listCommands.equals(togglecmds)){ //FIXME: REEEEEEEE
                // if its not set in list
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setTrue",
                        "_args", args[0]);
                ToggleCommandsConfig.togglecmds.put(args[0], true);
            } else {
                // else it was already in list
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setFalse",
                        "_args", args[0]);
                ToggleCommandsConfig.togglecmds.put(args[0], false);

            }
            // only for debug stuff
            System.out.println(ToggleCommandsConfig.togglecmds);
            break;
        default: // if args[0] does not equal any case labels
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
            break;
        }
        return true;
    }

    private boolean listSub(CommandSender sender, String[] args) {
        for (String cmd : togglecmds.keySet()){ //FIXME:
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.list.1"); //TODO change to easier language
            System.out.println(cmd+togglecmds.get(cmd)); //FIXME:
        }
        
        return false;
    }

    private boolean allSub(CommandSender sender, String[] args) {
        /*
        if (!togglecmds.isEmpty()) {
            togglecmds.clear();
            togglecmds.add("all-basics-module");
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.all-blocked");
        } else {
            togglecmds.add("all-basics-module");
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.all-blocked");
        }*/
        return false;
    }

    private boolean helpSub(CommandSender sender, String[] args) {
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "basics.toggle.help");
        return true;

    }

    @Override
    public String Label() {
        return "toggle";
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
