package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import main.ModularMSMF;
import util.DataManager;

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

	public static void mute(Player player) { // TODO UUID Support
		YamlConfiguration playercfg = DataManager.getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", true);
	}

	public static void setTempMute(Player player, int time, Plugin plugin) { // TODO
																			// UUID
																			// Support
		YamlConfiguration playercfg = DataManager.getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", true);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				playercfg.set("muted", false);
			}
		}, time * 20);
	}

	public static boolean alreadyMute(Player player) { // TODO UUID Support
		YamlConfiguration playercfg = DataManager.getPlayerCfg(player.getUniqueId());
		if (playercfg.isBoolean("muted")) {
			return playercfg.getBoolean("muted");
		}
		return false;
	}

	public static void unmute(Player player) { // TODO UUID Support
		YamlConfiguration playercfg = DataManager.getPlayerCfg(player.getUniqueId());
		playercfg.set("muted", false);
	}

	@Override
	public String getCommandLabel() {
		return "mute";
	}

}