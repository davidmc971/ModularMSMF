package io.github.davidmc971.modularmsmf.basics.util;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;

public class CommandUtil {

    public static boolean isPlayerEligible(CommandSender sender, Player player, Command command, String label,
            String[] args, String name) {
        if (player == sender) {
            return isSenderEligible(sender, command, name);
        }
        for (Player plron : Bukkit.getOnlinePlayers()) {
            if (plron == player) {
                switch (player.getGameMode()) {
                    case CREATIVE:
                        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                                "player.creative.others",
                                "_player", player.getName());
                        return false;
                    case SPECTATOR:
                        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                                "player.spectator.others",
                                "_player", player.getName());
                        return false;
                    default:
                        break;
                }
                switch (command.getLabel().toLowerCase()) {
                    case "healall":
                        break;
                    case "heal":
                        break;
                    case "slaughter":
                        break;
                    case "feed":
                        break;
                    case "fly":
                        break;
                    default: // FIXME: doesn't break here if world is in peaceful mode # important
                        if (player.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                            Util.sendMessageWithConfiguredLanguage(sender,
                                    ChatFormat.ERROR,
                                    "player.peaceful", "_worldname", Bukkit.getServer().getWorldContainer().toString());
                            return true;
                        }
                        break;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public static boolean isSenderEligible(CommandSender sender, Command command, String name) {
        if (sender instanceof ConsoleCommandSender) {
            ChatUtil.sendMsgNoPerm(sender);
            return false;
        }
        switch (((Player) sender).getGameMode()) {
            case CREATIVE:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "player.creative.self");
                return false;
            case SPECTATOR:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                        "player.spectator.self");
                return false;
            default:
                break;
        }
        switch (command.getLabel().toLowerCase()) {
            case "healall":
                break;
            case "heal":
                break;
            case "slaughter":
                break;
            case "feed":
                break;
            case "fly":
                break;
            case "set":
                break;
            default: // FIXME: doesn't break here if world is in peaceful mode # important
                if (((Player) sender).getWorld().getDifficulty() == Difficulty.PEACEFUL) {
                    Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                            "player.peaceful", "_worldname", returnWorldName(name));
                    return true;
                }
                return false;
        }
        return true;
    }

    private static String returnWorldName(String name) {
        return new String(Bukkit.getServer().getWorldContainer().toString());
    }
}
