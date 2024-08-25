package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.ModularMSMFBasics;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.listeners.BasicEvents;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.KillType;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.Util;

public class CommandKill implements IModularMSMFCommand, Listener {

    private BasicEvents basicEvents;

    public CommandKill(){
        basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        if (!PermissionManager.checkPermission(sender, "kill")) {
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
                // This will open a GUI where you can select specific types of kills or users.
                return killOpenGUI(sender, command, label, args);
            case 1:
                // This will only kill the specified user
                return killSpecificUser(sender, command, label, args);
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "general.toomanyargs");
                return true;
        }
    }

    private boolean killOpenGUI(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {

        if (!CommandUtil.isSenderEligible(sender, command, label)) {
            return true;
        }
        return true;
    }

    private boolean killSpecificUser(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (args[0].toLowerCase().equals(player.getName().toLowerCase())) {
                basicEvents.registerKilledPlayer(player, KillType.KILL);
                Util.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "events.killed",
                        "_player", player.getName());
                player.setHealth(0);
                return true;
            }
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
        return true;

    }

    @Override
    public String Label() {
        return "kill";
    }

    @Override
    public String[] Aliases() {
        return null;
    }

    @Override
    public boolean Enabled() {
        return false;
    }

}
