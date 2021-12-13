package io.github.davidmc971.modularmsmf.core.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

public class CommandClearChat implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandClearChat() {
        plugin = ModularMSMFCore.Instance();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        // checks if player has permission
        if (PermissionManager.checkPermission(sender, "clear_command")) {
            switch (args.length) {
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.commands.arguments.toomany");
                break;
            case 0:
                return clearSelf(sender, cmd, commandLabel, args);
            case 1:
            return clearOthers(sender, cmd, commandLabel, args);
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }

    private boolean clearOthers(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        UUID target = null;
        if (PermissionManager.checkPermission(sender, "clear_target")) {
            int count = 0;
            target = Utils.getPlayerUUIDByName(args[0]);
            if (target == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
                return true;
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "coremodule.commands.cclear.others", "_target", p.getName());
                        while (count <= 99) {
                            count++;
                            p.sendMessage("");
                        }
                        Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "coremodule.commands.cclear.done");
                        return true;
                    }
                }
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }

    private boolean clearSelf(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (PermissionManager.checkPermission(sender, "clear_all")) {
            int count = 0;
            while (count != 99) {
                count++;
                Bukkit.getOnlinePlayers().forEach((plr) -> plr.sendMessage(""));
            }
            Utils.broadcastWithConfiguredLanguageEach(plugin, ChatFormat.SUCCESS, "coremodule.commands.cclear.done");
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }

    @Override
    public String Label() {
        return "clearchat";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "clear", "clr", "cls", "cc" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}
