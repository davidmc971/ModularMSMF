package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandUnbanIP implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "unbanip")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN, "arguments.missingname");
            return true;
        }
        if (args.length == 1) {
            handlePlayer(sender, args);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
                "arguments.toomany");
        return true;
    }

    private void handlePlayer(@NotNull CommandSender sender, String[] args) {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName().equalsIgnoreCase(args[0])) {
                UUID uuid = null;
                FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
                if (cfg.getBoolean("banned") && cfg.isBoolean("banned")) {
                    cfg.set("banned", false);
                    cfg.set("reason", false);
                    cfg.set("ipAdress", false);
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
                            "commands.unban.player", "_player", p.getName());
                }
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN, "commands.unban.notfound",
                        "_player", p.getName());
            }
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
                    "player.nonexistant");
        }
    }

    @Override
    public String Label() {
        return "unbanip";
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
