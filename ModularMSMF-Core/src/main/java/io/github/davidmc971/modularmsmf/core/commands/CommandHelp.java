package io.github.davidmc971.modularmsmf.core.commands;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandHelp implements IModularMSMFCommand {

    private ModularMSMFCore plugin;

    public CommandHelp(ModularMSMFCore plugin) {
        plugin = ModularMSMFCore.Instance();
    }

    // lists commands as List<String>
    private List<String> forHelpListedCmds = null;
    //get commands from CommandLoader as list

    private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
    private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (sender instanceof Player) {
            switch (args.length) {
            /**
             * base for player-related commands like tp/heal/feed/(...) as player
             */
            default:
                sender.sendMessage(infoPrefix + "test1");
                break;
            case 1:
                switch (args[0].toLowerCase()) {
                case "test"/* forHelpListedCmds */:
                    sender.sendMessage(infoPrefix + "test2");
                    break;
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.invalidarguments");
                    break;
                }
                break;
            }
            return true;
        } else {
            switch (args.length) {
            /**
             * base for console-related commands like debug stuff or any heal/feed/(...) via
             * console
             **/
            default:
                sender.sendMessage(infoPrefix + "test1console");
                break;
            case 1:
                switch (args[0].toLowerCase()) {
                case "test"/* forHelpListedCmds */:
                    sender.sendMessage(infoPrefix + "test2console");
                    break;
                default:
                    Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
                            "general.invalidarguments");
                    break;
                }
                break;
            }
            return true;
        }
    }

    @Override
    public String Label() {
        return "help";
    }

    @Override
    public String[] Aliases() {
        return null;
    }

    @Override
    public boolean Enabled() {
        return false;
    }

}
