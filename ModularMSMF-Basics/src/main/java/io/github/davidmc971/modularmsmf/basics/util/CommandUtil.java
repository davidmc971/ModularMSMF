package io.github.davidmc971.modularmsmf.basics.util;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandUtil {

    public static boolean isPlayerEligible(CommandSender sender, Player player) {
        if (player == sender) {
            return isSenderEligible(sender);
        }
        for (Player plron : Bukkit.getOnlinePlayers()) {
            if (plron == player) {
                switch (player.getGameMode()) {
                    case CREATIVE:
                        Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.ERROR,
                                "basicsmodule.creative.others",
                                "_player", player.getName());
                        return false;
                    case SPECTATOR:
                        Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.ERROR,
                                "basicsmodule.spectator.others",
                                "_player", player.getName());
                        return false;
                    default:
                        break;
                }
                return true;
            }
        }
        Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.ERROR,
                "coremodule.player.notonline");
        return false;
    }

    public static boolean isSenderEligible(CommandSender sender) {
        if (sender instanceof Player) {
            switch (((Player) sender).getGameMode()) {
                case CREATIVE:
                    Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.ERROR,
                            "basicsmodule.creative.self");
                    return false;
                case SPECTATOR:
                    Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.ERROR,
                            "basicsmodule.spectator.self");
                    return false;
                default:
                    break;
            }
            if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                Utils.sendMessageWithConfiguredLanguage(ModularMSMFCore.Instance(), sender, ChatFormat.ERROR,
                        "basicsmodule.peaceful");
                return false;
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return false;
        }
        return true;
    }
}
