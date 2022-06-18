package io.github.davidmc971.modularmsmf.core.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 * ClearChat - Clear the whole chat of the server or the specific client
 * It only fills the chat with empty spaces 99 times just to be sure everything
 * is gone, which shouldn't be read.
 * 
 * @author Lightkeks (Alexandre Lardeux)
 *         davidmc971 (David Alexander Pfeiffer)
 */
public class CommandClearChat implements IModularMSMFCommand {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PermissionManager.checkPermission(sender, "clear_command")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "arguments.toomany");
                break;
            case 0:
                clearChatSender();
                break;
            case 1:
                UUID target = null;
                clearChat(target, sender, args);
                break;
        }
        return true;
    }

    int count = 0;

    private void clearChat(UUID target, CommandSender sender, String args[]) {
        if (args.length == 0) {
            clearChatSender();
            return;
        }
        if (args.length == 1) {
            target = Utils.getPlayerUUIDByName(args[0]);
            if (target == null) {
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
                return;
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                    if (sender == p) {
                        clearChatSender();
                        return;
                    }
                    while (count != 99) {
                        count++;
                        p.sendMessage("");
                    }
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS,
                            "commands.cclear.others", "_target", p.getName());
                    Utils.sendMessageWithConfiguredLanguage(p, ChatFormat.SUCCESS,
                            "commands.cclear.done");
                    count = 0;
                    return;
                }
            }
        }
    }

    private void clearChatSender() {
        while (count != 99) {
            count++;
            Bukkit.getOnlinePlayers().forEach((plr -> plr.sendMessage("")));
        }
        Utils.broadcastWithConfiguredLanguageEach(ChatFormat.SUCCESS, "commands.cclear.done");
        count = 0;
        return;
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