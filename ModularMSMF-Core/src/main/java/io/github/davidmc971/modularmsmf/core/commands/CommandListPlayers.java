package io.github.davidmc971.modularmsmf.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * @author Lightkeks
 * /list <args> || /listall
 * will show online players in mmsmf style (can show online, offline players or both)
 *
 */

public class CommandListPlayers implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandListPlayers() {
		plugin = ModularMSMFCore.Instance();
	}

    //String[] adjustedList = cfg.get(); //only some idea - maybe good or not - Array?
    final List<Player> playerListOnline = new ArrayList<>();
    final List<Player> playerListOffline = new ArrayList<>();
    final List<Player> playerListMixed = new ArrayList<>();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /**
         * stuff for command
         */

        switch(label.toLowerCase()){
            case "list":
                return listPlayers(sender, command, label, args);
            case "listall":
                return listAllWithoutArgs(sender, command, label, args);
        }
        return true;
    }
    private boolean listAllWithoutArgs(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /**
         * likely shortcut for all
         */

        if(PermissionManager.checkPermission(sender, "list_all_players")){
            sender.sendMessage("Online: " + playerListOnline);
            sender.sendMessage("Offline: " + playerListOffline);
            playerListOnline.clear();
            playerListOffline.clear();
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }
    private boolean listPlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        for (final Player player : Bukkit.getOnlinePlayers()) {
            playerListOnline.add(player);
        }
        for (final OfflinePlayer player : Bukkit.getOfflinePlayers()) { //FIXME: needs to put all offline players in a list (likely for moderator and higher ranks)
            //playerListOffline.add(player);
        }

        switch(args.length){
            /**
             * TODO: list can be adjusted later with configs to even show groups like admin/moderator/etc. (online and offline)
             */
            case 0:
                if(sender instanceof ConsoleCommandSender){ //shows all players even without args
                    sender.sendMessage("Online: " + playerListOnline);
                    sender.sendMessage("Offline: " + playerListOffline);
                    playerListOnline.clear();
                    playerListOffline.clear();
                    return true;
                }
                if((sender.isOp()) || ((PermissionManager.checkPermission(sender, "list_all_players")))){ //shows all players if sender is op or has permission for it
                    sender.sendMessage("Online: " + playerListOnline);
                    sender.sendMessage("Offline: " + playerListOffline);
                    playerListOnline.clear();
                    playerListOffline.clear();
                    return true;
                }
                if(!sender.isOp() && PermissionManager.checkPermission(sender, "list_players_online")){ //shows players which are online
                    sender.sendMessage("Online: " + playerListOnline);
                    playerListOnline.clear();
                    return true;
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
                }
                break;
            case 1:
                if(args[0].equalsIgnoreCase("help")){
                    /**
                     * list all related arguments to show as help
                     */
                    return true;
                }
                if(args[0].equalsIgnoreCase("offline") || args[0].equalsIgnoreCase("off")){
                    if(PermissionManager.checkPermission(sender, "list_players_offline")){
                        sender.sendMessage("Offline: " + playerListOffline);
                        playerListOffline.clear();
                        return true;
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
                    }
                    return true;
                } else {
                    /**
                     * here will be the adjustable code for lists like moderator/admin/etc. (online and offline)
                     */
                    sender.sendMessage("nope"); //only for testing purposes
                }
                break;
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.commands.arguments.toomany");
                break;
        }
        return true;
    }

    @Override
    public String Label() {
        return "list";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "listall" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}
