package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.util.CommandUtil;
import io.github.davidmc971.modularmsmf.basics.util.PlayerAvailability;
import io.github.davidmc971.modularmsmf.basics.util.Util;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil.ChatFormat;
import io.github.davidmc971.modularmsmf.basics.util.ChatUtil;

/**
 *
 * @authors Lightkeks, davidmc971
 */

public class CommandSpawn implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandSpawn() {
        plugin = ModularMSMFCore.Instance();
    }

    // FIXME generates 2 times "[Console] Console is not permitted for this
    // command."
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                return spawnSub(sender, command, label, args);
            case 1:
                return spawnOthersSub(sender, command, label, args);
            default:
                Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.toomany");
                break;
        }
        return true;
    }

    String name = null;

    private boolean spawnOthersSub(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        UUID target = Util.getPlayerUUIDByName(args[0]);
        Player spawned = Bukkit.getPlayer(target);
        double x = cfg.getDouble("worldspawn.coordinates.X");
        double y = cfg.getDouble("worldspawn.coordinates.Y");
        double z = cfg.getDouble("worldspawn.coordinates.Z");
        double yaw = cfg.getDouble("worldspawn.coordinates.Yaw");
        double pitch = cfg.getDouble("worldspawn.coordinates.Pitch");
        String worldname = cfg.getString("worldspawn.world");
        if (args[0].equalsIgnoreCase("remove"))
            return spawnRemoveSub(sender, command, label, args);
        if (!PermissionManager.checkPermission(sender, "spawn_others"))
            return ChatUtil.sendMsgNoPerm(sender);
        if (!CommandUtil.isSenderEligible(sender, command, name))
            return true;
        if (!PlayerAvailability.checkPlayer(sender, target, args))
            return true;
        if (!cfg.get("worldspawn.isTrue").toString().equals("true")) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "commands.spawn.notset");
            return true;
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
            Util.sendMessageWithConfiguredLanguage(spawned, ChatFormat.SPAWN, "commands.spawn.spawned");
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "commands.spawn.others", "_player",
                    spawned.getName());
            return true;
        }
        World welt = Bukkit.getWorld(worldname);
        Location loc = spawned.getLocation();
        loc.setX(x);
        loc.setY(y);
        loc.setZ(z);
        loc.setYaw((float) yaw);
        loc.setPitch((float) pitch);
        loc.setWorld(welt);
        spawned.teleport(loc);
        Util.sendMessageWithConfiguredLanguage(spawned, ChatFormat.SPAWN, "commands.spawn.spawned");
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "commands.spawn.others", "_player",
                spawned.getName());
        return true;
    }

    private boolean spawnRemoveSub(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration cfg = plugin.getDataManager().settingsyaml;
        if (!PermissionManager.checkPermission(sender, "spawn_remove"))
            return ChatUtil.sendMsgNoPerm(sender);
        if (!CommandUtil.isSenderEligible(sender, command, name))
            return true;
        if (cfg.get("worldspawn.isTrue").toString().equals("true")) {
            cfg.set("worldspawn", null);
            cfg.set("worldspawn.isTrue", "false");
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "commands.spawn.removed");
            return true;
        }
        Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "commands.spawn.alreadyremoved");
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
        if (!PermissionManager.checkPermission(sender, "spawn"))
            return ChatUtil.sendMsgNoPerm(sender);
        if (!CommandUtil.isSenderEligible(sender, command, name))
            return true;
        if (cfg.get("worldspawn.isTrue").toString().equals("false")) {
            Util.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "commands.spawn.notset");
            return true;
        }
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
        Util.sendMessageWithConfiguredLanguage(p, ChatFormat.SPAWN, "commands.spawn.spawned");
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
