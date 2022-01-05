package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.listeners.CoreEvents;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandBack implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandBack() {
        plugin = ModularMSMFCore.Instance();
    }

    /**
     * @author Lightkeks Fully working command You can teleport back and teleport
     *         any user back to the last location
     */

/*   @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                return backCmd(sender, command, label, args);
            case 1:
                return backCmdSub(sender, command, label, args);
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                break;
        }
        return true;
    }
*/
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return backCmd(sender, command, label, args);
        }
        switch (args[0].toLowerCase()) {
            case "help":
            case "user":
            case "admin":
                return backCmdSub(sender, command, label, args);
            default:
                Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                        "basicsmodule.commands.arguments.toomany");
                break;
        }
        return true;
    }

    private boolean backCmdSub(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "back_others") || !PermissionManager.checkPermission(sender, "back_admin")) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if(args[0].equalsIgnoreCase("help")){
            return backHelp(sender, command, label, args);
        }
        UUID target = null;
        target = Utils.getPlayerUUIDByName(args[0]);
        if (target == null) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "coremodule.player.notfound");
            return true;
        } else
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
                    if (sender == p) {
                        if (CoreEvents.lastLocation.containsKey(sender.getName())) {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                    "basicsmodule.commands.back.success");
                            ((Entity) sender).teleport(CoreEvents.lastLocation.get(sender.getName()));
                            return true;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "basicsmodule.commands.back.error");
                        }
                    } else {
                        if (CoreEvents.lastLocation.containsKey(p.getName())) {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                                    "basicsmodule.commands.back.success");
                            p.teleport(CoreEvents.lastLocation.get(p.getName()));
                            return true;
                        } else {
                            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                                    "basicsmodule.commands.back.error");
                        }
                    }
                } else {
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "coremodule.player.notonline");
                }
            }
        return true;
    }

    private boolean backCmd(CommandSender sender, Command command, String label, String[] args) {
        if (!PermissionManager.checkPermission(sender, "back") || sender instanceof ConsoleCommandSender) {
            ChatUtils.sendMsgNoPerm(sender);
            return true;
        }
        if (CoreEvents.lastLocation.containsKey(sender.getName())) {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.SUCCESS,
                    "basicsmodule.commands.back.success");
            ((Entity) sender).teleport(CoreEvents.lastLocation.get(sender.getName()));
            return true;
        } else {
            Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                    "basicsmodule.commands.back.error");
        }
        return true;
    }

    private boolean backHelp(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("enter help here"); // TODO[epic=code needed,seq=31] help programming
        return true;
    }

    @Override
    public String Label() {
        return "back";
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
