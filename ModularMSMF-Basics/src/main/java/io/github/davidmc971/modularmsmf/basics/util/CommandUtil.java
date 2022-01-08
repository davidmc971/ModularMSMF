package io.github.davidmc971.modularmsmf.basics.util;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandUtil {

    public static boolean isPlayerEligible(CommandSender sender, Player player, Command command) {
        if (player == sender) {
            return isSenderEligible(sender, command);
        }
        for (Player plron : Bukkit.getOnlinePlayers()) {
            if (plron == player) {
                switch (player.getGameMode()) {
                    case CREATIVE:
                        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                                "basicsmodule.creative.other",
                                "_player", player.getName());
                        return false;
                    case SPECTATOR:
                        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                                "basicsmodule.spectator.other",
                                "_player", player.getName());
                        return false;
                    default:
                        break;
                }
                switch (command.getLabel().toLowerCase()) {
                    case "fly":
                        break;
                    default:
                        if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                            Utils.sendMessageWithConfiguredLanguage(sender,
                                    ChatFormat.ERROR,
                                    "basicsmodule.peaceful");
                            return false;
                        }
                }
                return true;
            }
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                "coremodule.player.notonline");
        return false;
    }

    public static boolean isSenderEligible(CommandSender sender, Command command) {
        if (sender instanceof Player) {
            switch (((Player) sender).getGameMode()) {
                case CREATIVE:
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                            "basicsmodule.creative.self");
                    return false;
                case SPECTATOR:
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                            "basicsmodule.spectator.self");
                    return false;
                default:
                    break;
            }
            switch (command.getLabel().toLowerCase()) {
                case "fly":
                    break;
                default:
                    if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                                "basicsmodule.peaceful");
                        return false;
                    }
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return false;
        }
        return true;
    }
}
