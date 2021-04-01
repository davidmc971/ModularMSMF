package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // FIXME: commands won't load properly
        // @bug --^---
        if (PermissionManager.checkPermission(sender, "toggle_use")) {
            if (args.length == 0) {
                switch (label.toLowerCase()) {
                case "help":
                    return helpSub(sender, command, label, args);
                case "all":
                    return allSub(sender, command, label, args);
                case "teleport":
                    return teleportSub(sender, command, label, args);
                case "setspawn":
                    return setspawnSub(sender, command, label, args);
                case "back":
                    return backSub(sender, command, label, args);
                case "test":
                    return testSub(sender, command, label, args);
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.invalidarguments");
                    break;
                }
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            }
        }
        return true;
    }

    private boolean testSub(CommandSender sender, Command command, String label, String[] args) {
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.notimplementedyet");
        return false;
    }

    private boolean backSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;

        // if (cfg.isSet("toggle.commands")) { // FIXME: string has to be loaded when
        // module Basics is loaded
        // if (cfg.isSet("toggle.commands.back")) {
        // if (cfg.get("toggle.commands.back").toString().equals("false")) {
        // cfg.set("toggle.commands.back", "true");
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basics.toggle.setTrue", "_args",
                args[0]);
        // } else {
        // cfg.set("toggle.commands.back", "false");
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basics.toggle.setFalse", "_args",
                args[0]);
        // }
        // } else {
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.missingconfig.substring");
        // cfg.set("toggle.commands.back", "true");
        // }
        // } else {
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "basics.missingconfig.mainstring");
        // cfg.createSection("toggle.commands");
        // }
        return true;
    }

    private boolean setspawnSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        if (cfg.isSet("toggle.commands")) { // FIXME: string has to be loaded when module Basics is loaded
            if (cfg.isSet("toggle.commands.setspawn")) {
                if (cfg.get("toggle.commands.setspawn").toString().equals("false")) {
                    cfg.set("toggle.commands.setspawn", "true");
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basics.toggle.setTrue",
                            "_args", args[0]);
                } else {
                    cfg.set("toggle.commands.setspawn", "false");
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                            "basics.toggle.setFalse", "_args", args[0]);
                }
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
                        "basics.missingconfig.substring");
                cfg.set("toggle.commands.setspawn", "true");
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
                    "basics.missingconfig.mainstring");
            cfg.createSection("toggle.commands");
        }
        return true;
    }

    private boolean teleportSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        if (cfg.isSet("toggle.commands")) { // FIXME: string has to be loaded when module Basics is loaded
            if (cfg.isSet("toggle.commands.teleport")) {
                if (cfg.get("toggle.commands.teleport").toString().equals("false")) {
                    cfg.set("toggle.commands.teleport", "true");
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basics.toggle.setTrue",
                            "_args", args[0]);
                } else {
                    cfg.set("toggle.commands.teleport", "false");
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                            "basics.toggle.setFalse", "_args", args[0]);
                }
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
                        "basics.missingconfig.substring");
                cfg.set("toggle.commands.teleport", "true");
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG,
                    "basics.missingconfig.mainstring");
            cfg.createSection("toggle.commands");
        }
        return true;
    }

    private boolean allSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        return false;
    }

    private boolean helpSub(CommandSender sender, Command command, String commandLabel, String[] args) {
        return false;
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
