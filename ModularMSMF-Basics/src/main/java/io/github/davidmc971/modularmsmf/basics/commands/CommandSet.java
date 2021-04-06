package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandSet implements IModularMSMFCommand {

    /**
     * @author Lightkeks
     */
    // TODO[epic=code needed] coding stuff here - set setting values like exp,
    // foodlevel, saturation, life etc.

    ModularMSMFCore plugin;

    public CommandSet() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (PermissionManager.checkPermission(sender, "set_use")) {
            switch (args[0].toLowerCase()) {
            case "help":
                return helpSet(sender, command, label, args);
            case "life":
            case "food":
            case "saturation":
            case "sat":
                return subSet(sender, command, label, args); // for subcommands like "/set args[1] (life, saturation,
                                                             // food,...)"
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
                break;
            }
        }
        return true;
    }

    private boolean subSet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("life")) {
            // /set life
            if (!PermissionManager.checkPermission(sender, "set_use_life")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            // TODO[epic=SetCommand] to code life below
            if (args.length > 2) {
                return lifeSetSub(sender, command, label, args);
            }
            if (args.length > 3) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("food")) {
            if (!PermissionManager.checkPermission(sender, "set_use_food")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            // TODO[epic=SetCommand] to code food below
        }
        if (args[0].equalsIgnoreCase("sat") || args[0].equalsIgnoreCase("saturation")) {
            if (!PermissionManager.checkPermission(sender, "set_use_saturation")) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.nopermission");
                return true;
            }
            // TODO[epic=SetCommand] to code saturation below
        }
        return true;
    }

    private boolean lifeSetSub(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // /set life <name> <int>
        // TODO[epic=SetCommand,seq=82] still need code
        UUID target;
        target = Utils.getPlayerUUIDByName(args[0]);
        Player p = Bukkit.getPlayer(target);
        ((HumanEntity) p).getGameMode();
        if (target == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotonline");
            return true;
        }
        if (p == sender) {
            int i;
            try {
                i = Integer.parseInt(args[0]);
                // /set life <name> '<int>'
                // deny if creative or spectator mode
                if ((GameMode.CREATIVE != null || GameMode.ADVENTURE != null || GameMode.SPECTATOR != null)) {
                    if (i >= 0) {
                        if (i <= 20) {
                            ((Damageable) sender).setHealth(i);
                            return true;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "basicsmodule.commands.set.upperlimit");
                        }
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.nonnegative");
                    }
                    return true;
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.commands.set.survivalonly");
                }
                return true;
            } catch (NumberFormatException e) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "languageKey");
                return false;
            }
        } else {
            int i;
            try {
                i = Integer.parseInt(args[0]);
                // /set life <name> '<int>'
                // deny if creative or spectator mode
                if ((GameMode.CREATIVE != null || GameMode.ADVENTURE != null || GameMode.SPECTATOR != null)) {
                    if (i >= 0) {
                        if (i <= 20) {
                            ((Damageable) sender).setHealth(i);
                            return true;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "basicsmodule.commands.set.upperlimit");
                        }
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "basicsmodule.commands.set.nonnegative");
                    }
                    return true;
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.commands.set.survivalonly");
                }
                return true;
            } catch (NumberFormatException e) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "languageKey");
                return false;
            }
        }
    }

    private boolean helpSet(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        sender.sendMessage("help set");
        return true;
    }

    @Override
    public String Label() {
        return "set";
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
