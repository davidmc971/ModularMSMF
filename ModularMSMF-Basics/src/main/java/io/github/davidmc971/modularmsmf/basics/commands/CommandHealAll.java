package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 *
 *         FIXME health to fill for higher scale than 20
 */

public class CommandHealAll implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "healall_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            handlePlayers(sender, command, label, args);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "basicsmodule.commands.arguments.toomany");
        return true;
    }

    private void handlePlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Utils.sendMessageWithConfiguredLanguage(p, ChatFormat.SUCCESS,
                    "basicsmodule.commands.heal.others.gothealed", "_sender", sender.getName());
            double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            p.setHealth(maxHealth);
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
