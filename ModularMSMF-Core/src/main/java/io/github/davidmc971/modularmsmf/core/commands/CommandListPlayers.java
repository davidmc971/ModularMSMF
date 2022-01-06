package io.github.davidmc971.modularmsmf.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import net.md_5.bungee.api.ChatColor;

/**
 * @author Lightkeks:
 *
 */

public class CommandListPlayers implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandListPlayers() {
        plugin = ModularMSMFCore.Instance();
    }

    private static List<String> playerlist = new ArrayList<String>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerlist.add(player.getName());
        }
        if (!PermissionManager.checkPermission(sender, "list_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        switch (args.length) {
            case 0:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ONLINE, "languageKey", "_players",
                        playerlist.toString());
                break;
            case 1:
                switch (args[0].toLowerCase()) {
                    default:
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "coremodule.commands.arguments.invalid");
                        break;
                    case "help":
                    case "group":
                    case "all":
                        return useAsPlayer(sender, command, label, args);
                    case "settings":
                        if (!PermissionManager.checkPermission(sender, "list_admin_settings") || !sender.isOp()) {
                            ChatUtils.sendMsgNoPerm(sender);
                            return true;
                        } else {
                            return useAsAdmin(sender, command, label, args);
                        }
                }
        }
        return true;
    }

    private boolean useAsAdmin(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // TODO: have to write stuff down here

        return true;
    }

    private boolean useAsPlayer(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "list_use_groups")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        } else {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("/list");
                sender.sendMessage("/list help");
                sender.sendMessage("/list group");
                sender.sendMessage("/list all");
                if (PermissionManager.checkPermission(sender, "list_admin_settings")) {
                    sender.sendMessage("/list settings" + ChatColor.GRAY + " [admin/op only]");
                    return true;
                } else {
                    ChatUtils.sendMsgNoPerm(sender);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("group")) {

                return true;
            }
            if (args[0].equalsIgnoreCase("all")) {

                return true;
            }
        }
        return true;
    }

    @Override
    public String Label() {
        return "list";
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
