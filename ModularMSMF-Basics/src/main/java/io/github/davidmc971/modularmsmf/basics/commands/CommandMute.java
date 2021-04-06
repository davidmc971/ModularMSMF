package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;

/**
 * 
 * @authors Lightkeks, davidmc971
 *
 */

public class CommandMute implements IModularMSMFCommand {

	private ModularMSMFCore plugin;

	public CommandMute() {
		plugin = ModularMSMFCore.Instance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			if (PermissionManager.checkPermission(sender, "mute")) {
				/*
				 * TODO: implementation of mute with every subcommand
				 *
				 */
				UUID target = null;
				target = Utils.getPlayerUUIDByName(args[0]);
				toggleMute(target);
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
	 * 
	 * @param target
	 */

	public void toggleMute(UUID target) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(target);
		if (cfg.get("toggleablestuff.isMuted").toString().equals("false")) {
			cfg.set("toggleablestuff.isMuted", "true");
		}
		if (cfg.get("toggleablestuff.isMuted").toString().equals("true")) {
			cfg.set("toggleablestuff.isMuted", "false");
		}
	}

	public void setTempMute(UUID uuid, int time) {
		// UUID
		// Support
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg.get("toggleablestuff.isMuted").toString().equals("true")) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {
					cfg.set("muted", false);
				}
			}, time * 20);
		}
	}

	public boolean alreadyMute(UUID uuid) { // TODO already mute add
		return false;
	}

	public void unmute(Player player) { // TODO UUID Support
		FileConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", false);
	}

	@Override
	public String Label() {
		return "mute";
	}

	@Override
	public String[] Aliases() {
		return new String[] { "silence" };
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}