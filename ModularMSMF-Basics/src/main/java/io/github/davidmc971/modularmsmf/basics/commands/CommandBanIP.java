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
        String reason = language.getString("reason.banned_reason");
        UUID uuid = getPlayerUUIDByNameForBan(args[0]);
        if (uuid == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.nonexistant");
        }
        if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                    "commands.ban.already");
        }
        Player player = Bukkit.getPlayer(uuid);
        if (!player.getUniqueId().toString().equals(uuid.toString())) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.offline", "_player", player.getName());
        }
        reason = "";
        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }
        banPlayerIp(sender, uuid, reason, language, player);
        Utils.broadcastWithConfiguredLanguageEach(ChatFormat.BAN, "reasons.broadcast.ban_reason", "_player", player.getName(), "_reason", reason);
    }

    private void handlePlayerWithoutReason(@NotNull CommandSender sender, String[] args) {
        FileConfiguration language = Utils.configureCommandLanguage(sender);
        String reason = language.getString("reason.banned_noreason");
        UUID uuid = getPlayerUUIDByNameForBan(args[0]);
        Player player = Bukkit.getPlayer(uuid);
        if (uuid == null) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.nonexistant");
        }
        if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                    "commands.ban.already");
        }
        if (!player.getUniqueId().toString().equals(uuid.toString())) {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.offline", "_player", player.getName());
        }
        banPlayerIp(sender, uuid, reason, language, player);
        Utils.broadcastWithConfiguredLanguageEach(ChatFormat.BAN, "reasons.broadcast.banned_noreason", "_player", player.getName());
    }

    private void handleHelp(@NotNull CommandSender sender) {
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                "commands.banip.use");
        Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                "commands.banip.name");
    }

    public void banPlayerIp(CommandSender sender, UUID uuid, String reason, FileConfiguration language, Player player) {
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
