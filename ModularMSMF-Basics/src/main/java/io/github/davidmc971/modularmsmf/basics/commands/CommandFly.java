package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandFly implements IModularMSMFCommand {

    /*
     * It needs to be toggleable in config to turn on and off fly
     */

    private ModularMSMFCore plugin;
    // private boolean isFlight;

    public CommandFly() {
        plugin = ModularMSMFCore.Instance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //TODO: Change structure completely
        if(PermissionManager.checkPermission(sender, "fly_use")){
            switch (args.length) {
                case 0:
                    return selfFlight(sender, command, label, args);
                case 1:
                    return othersFlight(sender, command, label, args);
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.arguments.toomany");
                break;
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }

    private boolean selfFlight(CommandSender sender, Command command, String label, String[] args){
        if(PermissionManager.checkPermission(sender, "fly_self")){
            if(((Player)sender).getGameMode() == GameMode.CREATIVE){
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                return true;
            } else {
                return toggleFlight(sender, command, label, args);
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }

    private boolean toggleFlight(CommandSender sender, Command command, String label, String[] args) {
        if(((Player)sender).getAllowFlight() == true){ //check if status is true for flying
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.fly.set_false");
            ((Player)sender).setAllowFlight(false); //turns off flying if toggled true
        } else {
            ((Player)sender).setAllowFlight(true); //otherwise turns true if not flying
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basicsmodule.commands.fly.set_true");
            return true;
        }
        return true;
    }

    private boolean othersFlight(CommandSender sender, Command command, String label, String[] args){
        UUID target = null;
		target = Utils.getPlayerUUIDByName(args[0]);
		Player player = Bukkit.getPlayer(target);

        if(PermissionManager.checkPermission(sender, "fly_self")){
            if (player == null) {
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
                return true;
            }
            if (player == sender) {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.self");
                    return true;
                }
                return toggleFlight(sender, command, label, args);
            } else {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.creative.others", "_player", player.getName());
                    return true;
                }
                if(player.getAllowFlight() == true){ //check if status is true for flying
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "basicsmodule.commands.fly.others.set_false", "_player", player.getName());
                    Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.ERROR, "basicsmodule.commands.fly.set_false");
                    player.setAllowFlight(false); //turns off flying if toggled true
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS, "basicsmodule.commands.fly.others.set_true", "_player", player.getName());
                    Utils.sendMessageWithConfiguredLanguage(plugin, player, ChatFormat.SUCCESS, "basicsmodule.commands.fly.set_true");
                    player.setAllowFlight(true); //otherwise turns true if not flying
                    return true;
                }
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
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
