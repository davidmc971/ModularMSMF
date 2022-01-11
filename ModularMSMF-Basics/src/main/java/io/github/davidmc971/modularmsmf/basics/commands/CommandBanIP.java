package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

public class CommandBanIP implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "banip")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (args.length == 0) {
            handleHelp(sender);
            return true;
        }
        if (args.length == 1) {
            handlePlayerWithoutReason(sender, args);
            return true;
        } else {
            handlePlayerWithReason(sender, args);
        }
        return true;
    }

    private void handlePlayerWithReason(@NotNull CommandSender sender, @NotNull String[] args) {
        FileConfiguration language = Utils.configureCommandLanguage(sender);
        String reason = language.getString("coremodule.event.banned"); // FIXME: add string to lang file to remove null
        UUID uuid = getPlayerUUIDByNameForBan(args[0]);

        if (uuid == null) {
            // FIXME: add string to lang file to remove null
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.unknown");
        }

        if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                    "basicsmodule.commands.ban.already"); // FIXME: add string to lang file to remove null
        }

        Player player = Bukkit.getPlayer(uuid);
        if (!player.getUniqueId().toString().equals(uuid.toString())) {
            // FIXME: add string to lang file to remove null
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.notonline");
        }

        reason = "";
        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }

        banPlayerIp(uuid, reason, language, player);
    }

    private void handlePlayerWithoutReason(@NotNull CommandSender sender, String[] args) {
        FileConfiguration language = Utils.configureCommandLanguage(sender);
        // FIXME: add string to lang file to remove null
        String reason = language.getString("coremodule.event.banned");
        UUID uuid = getPlayerUUIDByNameForBan(args[0]);
        Player player = Bukkit.getPlayer(uuid);

        if (uuid == null) {
            // FIXME: add string to lang file to remove null
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.unknown");
        }

        if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                    "basicsmodule.commands.ban.already"); // FIXME: add string to lang file to remove null
        }

        if (!player.getUniqueId().toString().equals(uuid.toString())) {
            // FIXME: add string to lang file to remove null
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "coremodule.player.notonline");
        }

        banPlayerIp(uuid, reason, language, player);
    }

    private void handleHelp(@NotNull CommandSender sender) {
        // FIXME: add string to lang file to remove null
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                "basicsmodule.commands.banip.help.banip");
        // FIXME: add string to lang file to remove null
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                "basicsmodule.commands.banip.help.name");
    }

    public void banPlayerIp(UUID uuid, String reason, FileConfiguration language, Player player) {
        FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
        cfg.set("banned", true);
        cfg.set("reason", reason);
        cfg.set("ipAdress", player.getAddress().getAddress().getHostAddress().toString());
    }

    private UUID getPlayerUUIDByNameForBan(String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(name)) {
                return p.getUniqueId();
            }
        }
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName().equals(name)) {
                return p.getUniqueId();
            }
        }
        return null;
    }

    @Override
    public String Label() {
        return "banip";
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
