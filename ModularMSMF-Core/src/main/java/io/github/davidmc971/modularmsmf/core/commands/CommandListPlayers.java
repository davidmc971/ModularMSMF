package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

    String[] offline = {"offline", "off"};
    //String[] adjustedList = cfg.get(); //only some idea - maybe good or not - Array?

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

        if(sender.hasPermission(PermissionManager.getPermission("list_all_players"))){

        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
        }
        return true;
    }
    private boolean listPlayers(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch(args.length){
            /**
             * TODO: list can be adjusted later with configs to even show groups like admin/moderator/etc. (online and offline)
             */
            case 0:
                if(sender instanceof ConsoleCommandSender){ //shows all players even without args

                    return true;
                }
                if((sender.isOp()) || (sender.hasPermission(PermissionManager.getPermission("list_all_players")))){ //shows all players if sender is op or has permission for it

                    return true;
                }
                if(!sender.isOp() && sender.hasPermission(PermissionManager.getPermission("list_players_online"))){ //shows players which are online

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
                if(args.equals(offline)){ //checks string[] offline if one of these strings equals the argument
                    if(sender.hasPermission(PermissionManager.getPermission("list_players_offine"))){

                        return true;
                    } else {
                        Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
                    }
                    return true;
                } else {
                    /**
                     * here will be the adjustable code for lists like moderator/admin/etc. (online and offline)
                     */
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
