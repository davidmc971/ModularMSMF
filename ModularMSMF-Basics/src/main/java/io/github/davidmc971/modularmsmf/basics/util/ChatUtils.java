package io.github.davidmc971.modularmsmf.basics.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils { //TODO: new module maybe?

    public enum ChannelPrefix {
        ADMIN, SUPPORT, DEFAULT, MODERATOR
    }

    public static String getFormattedPrefix(ChannelPrefix format) {
        switch (format) {
            case ADMIN:
                return ("Admins");
            default:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    return player.getWorld().toString();
                }
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            return player.getWorld().toString();
        }
        return null;
    }
}
