package io.github.davidmc971.modularmsmf.basics.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;

public class PlayerAvailability {
    public static boolean isPlayerExistant(UUID uuid) {
        if (uuid == null)
            return false;
        return Bukkit.getPlayer(uuid) != null || Bukkit.getOfflinePlayer(uuid) != null;
    }

    public static boolean checkPlayer(CommandSender sender, UUID uuid, String[] args) {
        if (Bukkit.getPlayer(uuid) != null)
            return true;
        else if (isPlayerExistant(uuid))
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                    "player.offline", "_player", Bukkit.getOfflinePlayer(uuid).getName());
        else
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "player.nonexistant");
        return true;
    }
}