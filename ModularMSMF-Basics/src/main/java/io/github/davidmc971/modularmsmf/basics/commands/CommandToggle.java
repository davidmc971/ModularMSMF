package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemoryConfiguration;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandToggle implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandToggle() {
        plugin = ModularMSMFCore.Instance();
    }

    public Set<String> togglecmds = new LinkedHashSet<String>();

    public static void togglecmds(String command) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //// FIXME: commands won't load properly
        // @bug --^---
        if (!PermissionManager.checkPermission(sender, "toggle_use")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        if (args.length < 1) {
            return helpSub(sender, command, label, args);
        }
        if (args.length > 3) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
            return true;
        }
        switch (args[0].toLowerCase()) {
        case "help":
            return helpSub(sender, command, label, args); //TODO: have to add
        case "all":
            return allSub(sender, command, label, args); //TODO: have to add
        case "teleport":
            return teleportSub(sender, command, label, args); //TODO: have to add
        case "setspawn":
            return setspawnSub(sender, command, label, args); //TODO: have to add
        case "back":
            return backSub(sender, command, label, args); //TODO: have to add
        case "test":
            return testSub(sender, command, label, args); //TODO: have to add
        default: //if args[0] does not equal any case labels
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
            break;
        }
        return true;
    }

    private boolean testSub(CommandSender sender, Command command, String label, String[] args) {
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.notimplementedyet");
        return false;
    }

    private boolean backSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        // checking if teleport has been added to list
        if (!togglecmds.contains(args[0])) {
            //if its not set in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setTrue", "_args", args[0]);
            togglecmds.add(args[0]);
        } else {
            //else it was already in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setFalse", "_args", args[0]);
            togglecmds.remove(args[0]);
        }
        //only for debug stuff
        System.out.println(togglecmds);

        return true;
    }

    private boolean setspawnSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        // checking if teleport has been added to list
        if (!togglecmds.contains("setspawn")) {
            //if its not set in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setTrue", "_args", args[0]);
            togglecmds.add("setspawn");
        } else {
            //else it was already in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setFalse", "_args", args[0]);
            togglecmds.remove("setspawn");
        }
        //only for debug stuff
        System.out.println(togglecmds);

        return true;
    }

    private boolean teleportSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        // checking if teleport has been added to list
        if (!togglecmds.contains("teleport")) {
            //if its not set in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setTrue", "_args", args[0]);
            togglecmds.add("teleport");
        } else {
            //else it was already in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setFalse", "_args", args[0]);
            togglecmds.remove("teleport");
        }
        //only for debug stuff
        System.out.println(togglecmds);

        return true;
    }

    private boolean allSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        // checking if teleport has been added to list
        if (!togglecmds.contains("all-module-basic")) {
            //if its not set in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setTrue", "_args", args[0]);
            togglecmds.add("all-module-basic");
        } else {
            //else it was already in list
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.toggle.setFalse", "_args", args[0]);
            togglecmds.remove("all-module-basic");
        }
        //only for debug stuff
        System.out.println(togglecmds);

        return true;
    }

    private boolean helpSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "basics.toggle.help");
        /*
         * MemoryConfiguration cfg = plugin.getDataManager().settingsyaml;
         *
         * if(!cfg.isSet("toggle.commands")){
         * Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
         * "basics.missingconfig.mainstring"); cfg.createSection("toggle.commands");
         * return true; } if(!cfg.isSet("toggle.commands.help")){
         * Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
         * "basics.missingconfig.substring"); cfg.set("toggle.commands.help", "true");
         * return true; } if
         * (!cfg.get("toggle.commands.help").toString().equals("false")) {
         * Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
         * "basics.toggle.setFalse", "_args", args[0]); cfg.set("toggle.commands.help",
         * "false"); } else { Utils.sendMessageWithConfiguredLanguage(plugin, sender,
         * ChatFormat.DEBUG, "basics.toggle.setTrue", "_args", args[0]);
         * cfg.set("toggle.commands.help", "true"); return true; }
         */
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
