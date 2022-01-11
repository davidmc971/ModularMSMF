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

public class CommandUnbanPlayer implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "unban")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN, "player.missingname");
            return true;
        }
        if (args.length == 1) {
            handlePlayer(sender, args);
            return true;
        }
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
                "commands.arguments.toomany");
        // FIXME: add string to lang file to remove null
        return true;
    }

    private void handlePlayer(@NotNull CommandSender sender, String[] args) {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName().equalsIgnoreCase(args[0])) {
                UUID uuid = null;
                FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
                if (cfg.getBoolean("banned")) {
                    cfg.set("banned", false);
                    cfg.set("reason", false);
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
                            "commands.unban.done", "_player", p.getName());
                } // FIXME: add string to lang file to remove null
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN, "commands.unban.player.notbanned",
                        "_player", p.getName());
            } // FIXME: add string to lang file to remove null
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.UNBAN,
                    "commands.unban.player.notfound");
        }
    }

    @Override
    public String Label() {
        return "unban";
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
