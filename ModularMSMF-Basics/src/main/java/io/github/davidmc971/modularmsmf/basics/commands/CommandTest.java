package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandTest implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandTest() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.switch.check");
        if (cfg.isSet("toggle.commands")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.cfgisset.checktrue");
            switch (label.toLowerCase()) {
            case "test":
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.switch.commandtest.check");
                return testSub(sender, command, label, args);
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.switch.checktrue");
                break;
            }
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.cfgisset.check");
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.cfgisset.checkfalse");
            cfg.set("toggle.commands", null); //nothing to do with the FIXME below
        }
        return true;
    }

    private boolean testSub(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.switch.commandtest.checktrue");
        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.cfgget.substring.check");
        if(cfg.get("toggle.commands.test").toString().equals("true")){ //FIXME NullPointerException: Cannot invoke Object.toString() because the return value is null
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.cfgget.substring.checktrue");
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.DEBUG, "general.test.cfgget.substring.checkfalse");
            cfg.set("toggle.commands.test", null);
        }
        return true;
    }

    @Override
    public String Label() {
        return "test";
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
