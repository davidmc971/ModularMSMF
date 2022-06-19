package io.github.davidmc971.modularmsmf.basics.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;

public class PlayerAviability {

    public static boolean isPlayerExistant(CommandSender sender, Player player, Command command, String[] args) {
        if (args.length == 1) {
            switch (command.getLabel().toLowerCase()) {
                case "back":
                case "heal":
                case "kill":
                case "set":
                case "fly":
                case "feed":
                case "clearinventory":
                case "kick":
                case "ban":
                case "home":
                    checkPlayer(sender, player, args);
                    break;
                default:
                    break;
            }
            return false;
        }
        if (args.length == 2) {
            switch (command.getLabel().toLowerCase()) {
                case "get":
                    checkPlayer(sender, player, args);
                    break;
                default:
                    break;
            }
            return false;
        }
        if (args.length == 3) {
            switch (command.getLabel().toLowerCase()) {
                case "set":
                    checkPlayer(sender, player, args);
                    break;
                default:
                    break;
            }
            return false;
        }
        return true;
    }

    private static void checkPlayer(CommandSender sender, Player player, String[] args) {
        for (OfflinePlayer plroff : Bukkit.getOfflinePlayers()) {
            if (plroff != player) {
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
                return;
            }
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "player.offline", "_player", player.getName());
        }
    }
}