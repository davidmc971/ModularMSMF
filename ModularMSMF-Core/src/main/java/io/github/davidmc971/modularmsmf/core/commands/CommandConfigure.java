package io.github.davidmc971.modularmsmf.core.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

public class CommandConfigure implements IModularMSMFCommand, TabCompleter {

    private ModularMSMFCore plugin;

    private CommandConfigure() {
        plugin = ModularMSMFCore.Instance();
    }

    FileConfiguration cfg = plugin.getDataManager().settingsyaml;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        // TODO code
        if (!PermissionManager.checkPermission(sender, "configure_use"))
            return ChatUtils.sendMsgNoPerm(sender);
        if (args.length == 0) {
            return handleNoArgs(sender);
        }
        switch (args[0].toLowerCase()) {
            case "set":
                if (!PermissionManager.checkPermission(sender, "configure_set"))
                    return ChatUtils.sendMsgNoPerm(sender);
                return handleConfigSet(sender, args);
            case "view":
                if (PermissionManager.checkPermission(sender, "configure_view"))
                    return ChatUtils.sendMsgNoPerm(sender);
                return handleConfigView(sender, args);
            case "reset":
                if (PermissionManager.checkPermission(sender, "configure_reset"))
                    return ChatUtils.sendMsgNoPerm(sender);
                return handleConfigReset(sender, args);
            default:
                Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "commands.arguments.invalid");
                break;
        }
        return true;
    }

    private boolean handleConfigReset(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    private boolean handleConfigView(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    private boolean handleConfigSet(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    private boolean handleNoArgs(@NotNull CommandSender sender) {
        return false;
    }

    ArrayList<String> keys = new ArrayList<String>(){
        private static final long serialVersionUID = 1L;
        {
            addAll(plugin.getConfig().getKeys(true));
        }
    };

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String alias, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                switch (args[0].toLowerCase()) {
                    case "set":
                    case "view":
                    case "reset":
                    default:
                        return null;
                }
            case 2:
                switch (args[0].toLowerCase()) {
                    case "set":
                    case "view":
                    case "reset":
                        return keys;
                    default:
                        return null;
                }
            case 3:
            default:
                return null;
        }
    }

    @Override
    public String Label() {
        return "configure";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "config", "conf" };
    }

    @Override
    public boolean Enabled() {
        // TODO enable
        return false;
    }

}
