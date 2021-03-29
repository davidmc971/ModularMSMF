package io.github.davidmc971.modularmsmf.core.listeners;

import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;

public class CommandPreprocessor implements Listener {
    private static CommandPreprocessor instance = null;
    public static CommandPreprocessor Instance() {
        if (instance == null) instance = new CommandPreprocessor();
        return instance;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        ModularMSMFCore.Instance().getLogger().info("PlayerCommandPreprocessEvent: " + event.getMessage());
        handleCustomEvents(event.getMessage().substring(1), event);
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        ModularMSMFCore.Instance().getLogger().info("ServerCommandEvent: " + event.getCommand());
        handleCustomEvents(event.getCommand(), event);
    }

    /**
     * Method to check for custom external handling of a command. In case it is handled, it gets cancelled.
     * @param command The command for which to check whether it gets handled by a custom handler.
     * @param event The event which to cancel if handled in a custom way.
     */
    private void handleCustomEvents(String command, Cancellable event) {
        switch (command.split(" ")[0].toLowerCase()) {
            case "help":
                ModularMSMFCore.Instance().getLogger().info("help command intercepted and cancelled.");
                event.setCancelled(true);
                break;
        
            default:
                break;
        }
        
    }
}
