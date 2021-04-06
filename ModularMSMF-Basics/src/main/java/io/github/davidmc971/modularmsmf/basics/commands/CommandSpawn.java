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
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 *
 * @authors Lightkeks, davidmc971
 */

public class CommandSpawn implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandSpawn() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (args.length) {
        case 0:
            return spawnSub(sender, command, label, args);
        case 1:
            return spawnOthersSub(sender, command, label, args);
        default:
            if (sender instanceof ConsoleCommandSender) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
                return true;
            }
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
            break;
        }
        return true;
    }

    private boolean spawnOthersSub(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        Player spawned = Bukkit.getPlayer(target);
        double x = cfg.getDouble("worldspawn.coordinates.X");
        double y = cfg.getDouble("worldspawn.coordinates.Y");
        double z = cfg.getDouble("worldspawn.coordinates.Z");
        double yaw = cfg.getDouble("worldspawn.coordinates.Yaw");
        double pitch = cfg.getDouble("worldspawn.coordinates.Pitch");
        String worldname = cfg.getString("worldspawn.world");
        if (args[0].equalsIgnoreCase("remove")) {
            return spawnRemoveSub(sender, command, label, args);
        }
        if (!PermissionManager.checkPermission(sender, "spawn_others")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        if (target == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.playernotfound");
            return true;
        }
        if (!cfg.get("worldspawn.isTrue").toString().equals("true")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.spawn.nospawnset");
        }
        if (spawned == sender) {
            World welt = Bukkit.getWorld(worldname);
            Location loc = spawned.getLocation();
            loc.setX(x);
            loc.setY(y);
            loc.setZ(z);
            loc.setYaw((float) yaw);
            loc.setPitch((float) pitch);
            loc.setWorld(welt);
            spawned.teleport(loc);
            Utils.sendMessageWithConfiguredLanguage(plugin, spawned, ChatFormat.SPAWN, "commands.spawn.spawned");
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.spawn.others",
                    "_player", spawned.getName());
        } else {
            World welt = Bukkit.getWorld(worldname);
            Location loc = spawned.getLocation();
            loc.setX(x);
            loc.setY(y);
            loc.setZ(z);
            loc.setYaw((float) yaw);
            loc.setPitch((float) pitch);
            loc.setWorld(welt);
            spawned.teleport(loc);
            Utils.sendMessageWithConfiguredLanguage(plugin, spawned, ChatFormat.SPAWN, "commands.spawn.spawned");
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.spawn.others",
                    "_player", spawned.getName());
        }
        return true;
    }

    private boolean spawnRemoveSub(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        if (!PermissionManager.checkPermission(sender, "spawn_remove")) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            return true;
        }
        if (cfg.get("worldspawn.isTrue").toString().equals("true")) {
            cfg.set("worldspawn", null);
            cfg.set("worldspawn.isTrue", "false");
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "commands.spawn.removed");
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.spawn.alreadyremoved");
        }
        return true;
    }

    private boolean spawnSub(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;

        double x = cfg.getDouble("worldspawn.coordinates.X");
        double y = cfg.getDouble("worldspawn.coordinates.Y");
        double z = cfg.getDouble("worldspawn.coordinates.Z");
        double yaw = cfg.getDouble("worldspawn.coordinates.Yaw");
        double pitch = cfg.getDouble("worldspawn.coordinates.Pitch");
        String worldname = cfg.getString("worldspawn.world");

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
