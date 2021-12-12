package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandFly implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandFly() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (PermissionManager.checkPermission(sender, "fly_use")) {
            switch (args.length) {
                case 0:
                    return selfFlight(sender, command, label, args);
                case 1:
                    return othersFlight(sender, command, label, args);
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.commands.arguments.toomany");
                    break;
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
                    "coremodule.player.nopermission");
        }
        return true;
    }

    private boolean selfFlight(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "coremodule.noconsole");
            return true;
        } else {
            if (PermissionManager.checkPermission(sender, "fly_self")) {
                if (((Player) sender).getGameMode() == GameMode.CREATIVE) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.creative.self");
                    return true;
                } else {
                    return toggleFlight(sender, command, label, args);
                }
            } else {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
                        "coremodule.player.nopermission");
            }
        }
        return true;
    }

    private boolean toggleFlight(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        
        UUID uuid = p.getUniqueId();
        // FIXME: can be null for existing player
        MemoryConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);

        // check if status is true for flying
        if (p.getAllowFlight()) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FLY_OFF,
                    "basicsmodule.commands.fly.set_false");
            // turns off flying if toggled true
            p.setAllowFlight(false);

            // Maintain config section
            cfg.set("flying", false);
        } else {
            p.setAllowFlight(true); // otherwise turns true if not flying
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FLY_ON,
                    "basicsmodule.commands.fly.set_true");
            /*
             * Player p = (Player) sender;
             * Location loc = p.getLocation();
             * double x = loc.getX();
             * double y = loc.getY();
             * double z = loc.getZ();
             * double yaw = loc.getYaw();
             * double pitch = loc.getPitch();
             * String worldname = loc.getWorld().getName();
             * cfg.set("players.position.x", x); //FIXME: cfg will not be set cause strings
             * missing in "players"-folder - NullPointerException
             * cfg.set("players.position.y", y);
             * cfg.set("players.position.z", z);
             * cfg.set("players.position.yaw", yaw);
             * cfg.set("players.position.pitch", pitch);
             * cfg.set("players.position.world", worldname);
             * cfg.set("players.flying", true); //boolean for event
             */
            return true;
        }
        return true;
    }

    private boolean othersFlight(CommandSender sender, Command command, String label, String[] args) {
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        Player player = Bukkit.getPlayer(target);

        if (PermissionManager.checkPermission(sender, "fly_self")) {
            if (player == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
                return true;
            }
            if (player == sender) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.creative.self");
                    return true;
                }
                return toggleFlight(sender, command, label, args);
            } else {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "basicsmodule.creative.others", "_player", player.getName());
                    return true;
                }
                if (player.getAllowFlight() == true) { // check if status is true for flying
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FLY_OFF,
                            "basicsmodule.commands.fly.others.set_false", "_player", player.getName());
                    Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.FLY_OFF,
                            "basicsmodule.commands.fly.set_false");
                    player.setAllowFlight(false); // turns off flying if toggled true
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.FLY_ON,
                            "basicsmodule.commands.fly.others.set_true", "_player", player.getName());
                    Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.FLY_ON,
                            "basicsmodule.commands.fly.set_true");
                    player.setAllowFlight(true); // otherwise turns true if not flying
                    return true;
                }
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
                    "coremodule.player.nopermission");
        }
        return true;
    }

    @Override
    public String Label() {
        return "fly";
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
