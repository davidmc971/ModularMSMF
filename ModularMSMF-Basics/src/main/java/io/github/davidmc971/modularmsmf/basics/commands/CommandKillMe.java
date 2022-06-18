package io.github.davidmc971.modularmsmf.basics.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

public class CommandKillMe implements IModularMSMFCommand {

    private BasicEvents basicEvents;

    public CommandKillMe() {
        basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "kill_me")) {
            ChatUtil.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            basicEvents.registerKilledPlayer(((Player) sender), KillType.SUICIDE);
            Util.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "events.suicide", "_player",
                    sender.getName());
            ((Player) sender).setHealth(0);
            return true;
        }
        if(args.length <= 1){
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
            return true;
        }
        return true;
    }

    @Override
    public String Label() {
        return "killme";
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
