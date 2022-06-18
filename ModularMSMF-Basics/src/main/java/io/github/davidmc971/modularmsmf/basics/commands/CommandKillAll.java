package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.ModularMSMFBasics;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.listeners.BasicEvents;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

public class CommandKillAll implements IModularMSMFCommand {

    private BasicEvents basicEvents;

    public CommandKillAll() {
        basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "kill_all")) {
            ChatUtil.sendMsgNoPerm(sender);
            return false;
        }
        if (args.length == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                basicEvents.registerKilledPlayer(player, KillType.HOMOCIDE);
                player.setHealth(0);
            }
            Util.broadcastWithConfiguredLanguageEach(ChatUtil.ChatFormat.DEATH,
                    "events.homocide");
            return true;
        }
        if (args.length <= 1) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
            return true;
        }
        return true;
    }

    @Override
    public String Label() {
        return "killall";
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
