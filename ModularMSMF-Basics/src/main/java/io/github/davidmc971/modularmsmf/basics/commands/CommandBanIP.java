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
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;

/**
 * @author Lightkeks
 */

public class CommandBanIP implements IModularMSMFCommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "ban_ip"))
            return ChatUtil.sendMsgNoPerm(sender);
        if (args.length == 0) {
            return handleHelp(sender);
        }
        if (args.length == 1) {
            return handlePlayerWithoutReason(sender, args);
        } else {
            return handlePlayerWithReason(sender, args);
        }
    }

    private boolean handlePlayerWithReason(@NotNull CommandSender sender, @NotNull String[] args) {
        FileConfiguration language = Util.configureCommandLanguage(sender);
        String reason = language.getString("reasons.banned_reason");
        UUID uuid = getPlayerUUIDByNameForBan(args[0]);
        if (uuid == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.nonexistant");
            return true;
        }
        if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                    "commands.ban.already");
            return true;
        }
        Player player = Bukkit.getPlayer(uuid);
        if (!player.getUniqueId().toString().equals(uuid.toString())) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.offline", "_player",
                    player.getName());
            return true;
        }
        reason = "";
        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }
        banPlayerIp(sender, uuid, reason, language, player);
        Util.broadcastWithConfiguredLanguageEach(ChatFormat.BAN, "reasons.broadcast.ban_reason", "_player",
                player.getName(), "_reason", reason);
        return true;
    }

    private boolean handlePlayerWithoutReason(@NotNull CommandSender sender, String[] args) {
        FileConfiguration language = Util.configureCommandLanguage(sender);
        String reason = language.getString("reason.banned_noreason");
        UUID uuid = getPlayerUUIDByNameForBan(args[0]);
        Player player = Bukkit.getPlayer(uuid);
        if (uuid == null) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.nonexistant");
            return true;
        }
        if (ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid).getBoolean("banned")) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN,
                    "commands.ban.already");
            return true;
        }
        if (!player.getUniqueId().toString().equals(uuid.toString())) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "player.offline", "_player",
                    player.getName());
            return true;
        }
        banPlayerIp(sender, uuid, reason, language, player);
        Util.broadcastWithConfiguredLanguageEach(ChatFormat.BAN, "reasons.broadcast.banned_noreason", "_player",
                player.getName());
        return true;
    }

    private boolean handleHelp(@NotNull CommandSender sender) {
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "commands.banip.use");
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.BAN, "commands.banip.name");
        return true;
    }

    public boolean banPlayerIp(CommandSender sender, UUID uuid, String reason, FileConfiguration language,
            Player player) {
        FileConfiguration cfg = ModularMSMFCore.Instance().getDataManager().getPlayerCfg(uuid);
        cfg.set("banned", true);
        cfg.set("reason", reason);
        cfg.set("ipAdress", player.getAddress().getAddress().getHostAddress().toString()); // FIXME not adding IP
        return true;
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
