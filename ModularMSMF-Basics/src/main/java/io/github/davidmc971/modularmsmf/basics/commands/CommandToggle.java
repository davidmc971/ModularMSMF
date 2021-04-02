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
        if (args.length > 3) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
            return true;
        }
        switch ((args.length < 1) ? "help" : args[0].toLowerCase()) {
        case "help":
            return helpSub(sender, args);
        case "all":
        case "teleport":
        case "setspawn":
        case "back":
        case "test":
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
            break;
        default: //if args[0] does not equal any case labels
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
            break;
        }
        return true;
    }

    private boolean helpSub(CommandSender sender, String[] args) {
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
