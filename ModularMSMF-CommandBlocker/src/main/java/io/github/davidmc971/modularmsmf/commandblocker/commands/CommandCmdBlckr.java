package io.github.davidmc971.modularmsmf.commandblocker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.commandblocker.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandCmdBlckr implements IModularMSMFCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionManager.getPermission("use"))) {
            // TODO structure given:
            /*
             * commandblocker/cb -> help//shows "this" help add //adds a command to list
             * remove //removes a command from list list //lists all blocked commands
             */
            switch (args.length) {
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "arguments.invalid");
                break;
            case 1:
                switch (args[0].toLowerCase()) {
                default:
                    Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
                            "arguments.invalid");
                    break;
                case "help":
                    break;
                case "add":
                    break;
                case "remove":
                    break;
                case "list":
                    break;
                }
                break;
            }
        } else {
            Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "player.nopermission");
        }
        return true;
    }

    @Override
    public String Label() {
        return "commandblocker";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "cmdblckr", "cb" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}
