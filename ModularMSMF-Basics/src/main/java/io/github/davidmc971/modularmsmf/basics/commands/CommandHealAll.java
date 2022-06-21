package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 * @author Lightkeks
 *
 */

public class CommandHealAll implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "heal_all_use"))
            return ChatUtil.sendMsgNoPerm(sender);
        if (args.length == 0)
            return handlePlayers(sender, command, label, args);
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "arguments.toomany");
        return true;
    }

    private boolean handlePlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Util.sendMessageWithConfiguredLanguage(p, ChatFormat.SUCCESS,
                    "commands.heal.others.gothealed", "_sender", sender.getName());
            double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            p.setHealth(maxHealth);
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "commands.heal.all");
        return true;
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
