package io.github.davidmc971.modularmsmf.core.commands;

import java.util.ArrayList;

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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        /**
         * This command is written to show up players if they're online or not.
         * It can be customized to show up even groups like moderators or admins or
         * whatever group has been made via NovaPerms.
         * Only working if sender has permission
         */
        for (Player player : Bukkit.getOnlinePlayers()) {
            onlineWithoutSpecialCon.add(player.getName());
        }
        if (!PermissionManager.checkPermission(sender, "list_use")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        switch (label.toLowerCase()) {
            case "list":
                /**
                 * This will show up players without vanished or being in spectator.
                 * Special conditions are given if sender has permission or is op to see even
                 * vanished or spectator in list.
                 * Only working if sender has permission
                 */
                if (!PermissionManager.checkPermission(sender, "list_players")) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return true;
                }
                return listPlayers(sender, command, label, args);
            case "listall":
                /**
                 * This command will only "work" if sender has permission or is op to see every
                 * player online on the server regardless of the special conditions given
                 */
                if (!PermissionManager.checkPermission(sender, "list_all") | !sender.isOp()) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return true;
                }
                return listAllPlayers(sender, command, label, args);
        }
        return true;
    }

    final ArrayList<String> onlineWithoutSpecialCon = new ArrayList<String>();
    final ArrayList<String> onlineWithSpecialCon = new ArrayList<String>();

    private boolean listAllPlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        return false;
    }

    private boolean listPlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        /**
         * Here you will see all players without any special conditions if no arguments
         * are given
         */
        if (args.length == 0) {
            if (sender.isOp() || PermissionManager.checkPermission(sender, "list_all")) {
                sender.sendMessage("Online: " + onlineWithSpecialCon + onlineWithoutSpecialCon);
                ;
            } else {
                sender.sendMessage("Online: " + onlineWithoutSpecialCon);
            }
            return true;
        }
        /**
         * Here it will ask for the arguments to have more functions to each argument
         */
        switch (args[0].toLowerCase()) {
            default:
                /**
                 * Will return an invalid argument if nothing matches any cases down below
                 */
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "coremodule.commands.arguments.invalid");
                break;
            case "help":
            case "group":
                /**
                 * here you can show up like other arguments or groups to list in chat
                 */
                return useAsPlayer(sender, command, label, args);
            case "settings":
                /**
                 * only useful in combination with other modules which work together
                 */
                if (!PermissionManager.checkPermission(sender, "list_admin_settings") || !sender.isOp()) {
                    ChatUtils.sendMsgNoPerm(sender);
                    return true;
                } else {
                    return useAsAdmin(sender, command, label, args);
                }
        }
        return true;
    }

    private boolean useAsAdmin(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // TODO: have to write stuff down here
        return false;
    }

    private boolean useAsPlayer(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (!PermissionManager.checkPermission(sender, "list_use_groups")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        } else {
            if (args[1].equalsIgnoreCase("help")) {
                sender.sendMessage("/list");
                sender.sendMessage("/list help");
                sender.sendMessage("/list group");
                sender.sendMessage("/list settings" + ChatColor.GRAY + " [admin/op only]");
            }
            if (args[1].equalsIgnoreCase("group")) {

            }
        }
        return false;
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
