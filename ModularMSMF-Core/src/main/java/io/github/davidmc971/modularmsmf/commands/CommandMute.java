package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.Utils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;

/**
 * 
 * @author Lightkeks, davidmc971
 *
 */

public class CommandMute implements IModularMSMFCommand {

	private ModularMSMF plugin;

    public CommandMute() {
        plugin = ModularMSMF.Instance();
    }

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player){
			if(sender.hasPermission(PermissionManager.getPermission("mute"))){
				/* TODO: implementation of mute with every subcommand
				*
				*/
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.notimplementedyet");
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.CONSOLE, "general.noconsole");
		}
		return true;
		
	}
	
	/**
	 * TODO implement mute
	 * @param player
	 */

	public void mute(Player player) { // TODO UUID Support
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", true);
	}

	/**
	public void setTempMute(Player player, int time) { // TODO
		// UUID
		// Support
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", true);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				playercfg.set("muted", false);
			}
		}, time * 20);
	}
	*/
	public boolean alreadyMute(Player player) { // TODO UUID Support
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		if (playercfg.isBoolean("muted")) {
			return playercfg.getBoolean("muted");
		}
		return false;
	}

	public void unmute(Player player) { // TODO UUID Support
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", false);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "mute" };
	}

}