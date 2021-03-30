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

        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        if (PermissionManager.checkPermission(sender, "toggle_use")) {
            if (args.length == 0) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.missingarguments");
            } else if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.invalidargument");
                    break;
                case "help":
                    break;
                case "back":
                    if (cfg.isSet("toggle.commands")) {
                        if (cfg.isSet("toggle.commands.back")) {
                            if (cfg.get("toggle.commands.back").toString().equals("false")) {
                                cfg.set("toggle.commands.back", "true");
                                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                        "basics.toggle.setTrue", "_args", args[0]);
                            } else {
                                cfg.set("toggle.commands.back", "false");
                                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                        "basics.toggle.setFalse", "_args", args[0]);
                            }
                            break;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.missingconfig.substring");
                            cfg.set("toggle.commands.back", "false");
                        }
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "general.missingconfig");
                        cfg.createSection("toggle.commands");
                    }
                }
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
        }
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[] { "toggle", "tog" };
    }

}
