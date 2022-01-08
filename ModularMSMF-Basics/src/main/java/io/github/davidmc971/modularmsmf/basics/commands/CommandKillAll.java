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
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandKillAll implements IModularMSMFCommand {

    private BasicEvents basicEvents;

    public CommandKillAll() {
        basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "kill_all")) {
            ChatUtils.sendMsgNoPerm(sender);
            return false;
        }
        if (args.length == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                basicEvents.registerKilledPlayer(player, KillType.HOMOCIDE);
                player.setHealth(0);
            }
            Utils.broadcastWithConfiguredLanguageEach(ChatUtils.ChatFormat.DEATH,
                    "basicsmodule.events.homocide");
            return true;
        }
        if (args.length <= 1) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "basicsmodule.arguments.toomany");
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
