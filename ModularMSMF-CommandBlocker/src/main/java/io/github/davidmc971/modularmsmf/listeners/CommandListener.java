package io.github.davidmc971.modularmsmf.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

public class CommandListener implements Listener{

    //setting "plugin"
    public ModularMSMF plugin;

    //here it will catch any command, which equals any string in file, if set
    @EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (!p.isOp()) {
			String commandName = e.getMessage().substring(1).split(" ")[0].toLowerCase();
        //TODO change "if" to "loadFromFile" and "saveToFile"
        //row: first saveToFile, then loadFromFile
			if (commandName.equals("pl") || commandName.equals("plugins")) {
				Utils.sendMessageWithConfiguredLanguage(plugin, p, ChatFormat.ERROR, "general.commands_blocked", "_var",
						commandName);
				e.setCancelled(true);
			}
		}
	}
}
