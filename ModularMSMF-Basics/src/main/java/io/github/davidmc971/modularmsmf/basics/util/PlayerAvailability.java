package io.github.davidmc971.modularmsmf.basics.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;

public class PlayerAvailability {
    public static boolean isPlayerExistant(UUID uuid) {
        return uuid != null || Bukkit.getPlayer(uuid) != null || Bukkit.getOfflinePlayer(uuid) != null;
    }

    public static void checkPlayer(CommandSender sender, UUID uuid, String[] args) {
        if (Bukkit.getPlayer(uuid) != null)
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.online");
        else if (isPlayerExistant(uuid))
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "player.offline", "_player", Bukkit.getOfflinePlayer(uuid).getName());
        else
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
    }
}