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
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandKillMe implements IModularMSMFCommand {

    private BasicEvents basicEvents;

    public CommandKillMe() {
        basicEvents = ModularMSMFBasics.Instance().getBasicEvents();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender || !PermissionManager.checkPermission(sender, "kill_me")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            basicEvents.registerKilledPlayer(((Player) sender), KillType.SUICIDE);
            Utils.broadcastWithConfiguredLanguageEach(ChatFormat.DEATH, "events.suicide", "_var",
                    sender.getName());
            ((Player) sender).setHealth(0);
            return true;
        }
        if(args.length <= 1){
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
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
