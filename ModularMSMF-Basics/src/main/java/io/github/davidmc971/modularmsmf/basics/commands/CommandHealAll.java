package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandHealAll implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandHealAll() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "healall_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
                handlePlayers(sender, command, label, args);
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                break;
        }
        return true;
    }

    private void handlePlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SUCCESS, "basicsmodule.commands.heal.others.gothealed", "_sender", sender.getName());
            p.setHealth(20);
        }
    }

    @Override
    public String Label() {
        return "healall";
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
