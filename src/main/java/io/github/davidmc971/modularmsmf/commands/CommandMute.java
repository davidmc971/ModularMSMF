package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;

/**
 * 
 * @author Lightkeks, davidmc971
 *
 */

public class CommandMute extends AbstractCommand {

	public CommandMute(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		switch ("") {
		case "temp":
			if (true) {
			}
			;
			break;
		default:
		}

		return true;
	}
	
	/**
	 * @TODO implement mute
	 * @param player
	 */

	public void mute(Player player) { // TODO UUID Support
		YamlConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", true);
	}

	public void setTempMute(Player player, int time) { // TODO
																			// UUID
																			// Support
		YamlConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", true);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				playercfg.set("muted", false);
			}
		}, time * 20);
	}

	public boolean alreadyMute(Player player) { // TODO UUID Support
		YamlConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		if (playercfg.isBoolean("muted")) {
			return playercfg.getBoolean("muted");
		}
		return false;
	}

	public void unmute(Player player) { // TODO UUID Support
		YamlConfiguration playercfg = plugin.getDataManager().getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", false);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "mute" };
	}

}