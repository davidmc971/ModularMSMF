package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandFly implements IModularMSMFCommand {

    /*
     * It needs to be toggleable in config to turn on and off fly
     */

    private ModularMSMFCore plugin;
    private boolean isFlight;

    public CommandFly() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        UUID target = null;
        // checks if command sender is a player or console
        if (sender instanceof Player) {
            // checks if sender has permission
            if (PermissionManager.checkPermission(sender, "fly")) {
                Player p = (Player) sender;
                // checks length of argument given
                switch (args.length) {
                // if 0, normally fly should be activated
                case 0:
                    /*
                     * INSERT CODE HERE "code"
                     */

                    break;
                // maybe someone else should get fly?
                case 1:
                    // checks if you have permissions to give other players fly
                    if (PermissionManager.checkPermission(sender, "fly.others")) {
                        target = Utils.getPlayerUUIDByName(args[0]);
                        // checks if player (target) is online
                        if (target == null) {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "general.playernotfound");
                        } else {
                            /*
                             * INSERT CODE HERE "code"
                             */

                            // UUID target = null;
                            target = Utils.getPlayerUUIDByName(args[0]);
                            toggleFlight(target);
                        }
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
                                "general.nopermission");
                    }
                    break;
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.toomanyarguments");
                    break;
                }
            } else {
                // denies if sender has no permission
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
            }
        } else {
            // denies if command sender is console
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
        }
        return true;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[] { "fly" };
    }

    private boolean toggleFlight(UUID uuid) {
        FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
        Player player = Bukkit.getPlayer(uuid);
        if (cfg.getBoolean("modes.flight")) {
            // set allow flight to true for player
            player.setAllowFlight(false);
            cfg.set("modes.flight", false);
        } else {
            // set allow flight to false for player
            player.setAllowFlight(true);
            cfg.set("modes.flight", true);
        }

        return true;
    }

}
