package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 *
 * @authors Lightkeks, davidmc971
 * TODO[epic=code needed,seq=30] rewrite to better code
 */

public class CommandSpawn implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandSpawn() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;

        double x = cfg.getDouble("worldspawn.coordinates.X");
        double y = cfg.getDouble("worldspawn.coordinates.Y");
        double z = cfg.getDouble("worldspawn.coordinates.Z");
        double yaw = cfg.getDouble("worldspawn.coordinates.Yaw");
        double pitch = cfg.getDouble("worldspawn.coordinates.Pitch");
        String worldname = cfg.getString("worldspawn.world");

        switch (args.length) {
        case 0:
            if (!(PermissionManager.checkPermission(sender, "spawn"))) {
                Player p = (Player) sender;
                Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.NOPERM, "general.nopermission");
            } else {
                if (sender instanceof ConsoleCommandSender) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
                } else {
                    if (cfg.get("worldspawn.isTrue").toString().equals("false")) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "commands.spawn.nospawnset");
                    } else {
                        Player p = (Player) sender;
                        World welt = Bukkit.getWorld(worldname);
                        Location loc = p.getLocation();
                        loc.setX(x);
                        loc.setY(y);
                        loc.setZ(z);
                        loc.setYaw((float) yaw);
                        loc.setPitch((float) pitch);
                        loc.setWorld(welt);
                        p.teleport(loc);
                        Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN, "commands.spawn.spawned");
                    }
                }
            }
            break;
        case 1:
            if (args[0].equalsIgnoreCase("remove")) {
                if (PermissionManager.checkPermission(sender, "spawn_remove")) {
                    if (cfg.get("worldspawn.isTrue").toString().equals("false")) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "commands.spawn.alreadyremoved");
                    } else {
                        cfg.set("worldspawn", null);
                        cfg.set("worldspawn.isTrue", "false");
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                "commands.spawn.removed");
                    }
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
                }
            } else {
                if (PermissionManager.checkPermission(sender, "spawn_others")) {
                    UUID target = null;
                    target = Utils.getPlayerUUIDByName(args[0]);
                    if (target == null) {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                "general.playernotfound");
                    } else {
                        if (!cfg.get("worldspawn.isTrue").toString().equals("true")) {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "commands.spawn.nospawnset");
                        } else {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                World welt = Bukkit.getWorld(worldname);
                                Location loc = p.getLocation();
                                loc.setX(x);
                                loc.setY(y);
                                loc.setZ(z);
                                loc.setYaw((float) yaw);
                                loc.setPitch((float) pitch);
                                loc.setWorld(welt);
                                p.teleport(loc);
                                Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.SPAWN,
                                        "commands.spawn.spawned");
                                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                        "commands.spawn.others", "_player", args[0]);
                            }
                        }
                    }
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
                }
            }
            break;
        default:
            if (sender instanceof ConsoleCommandSender) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
            } else {
                if (args.length >= 2) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.toomanyarguments");
                }
            }
            break;
        }
        return true;
    }

    @Override
    public String Label() {
        return "spawn";
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
