package io.github.davidmc971.modularmsmf.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandSpawn extends AbstractCommand {

	public CommandSpawn(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);

	/**
	 * @TODO: Complete rewrite
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage(errorPrefix+language.getString("general.noconsole"));
		/**} else {
			Player p = (Player)sender;

			if(!p.hasPermission(PermissionManager.getPermission("spawn"))){
				p.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			}

			File file = new File("plugins/ModularMSMF/settings.yml");
			if(!file.exists()){
				p.sendMessage(language.getString("commands.spawn.nospawnset"));
			}

			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			double x = cfg.getDouble("X");
			double y = cfg.getDouble("Y");
			double z = cfg.getDouble("Z");
			double yaw = cfg.getDouble("Yaw");
			double pitch = cfg.getDouble("Pitch");
			String worldname = cfg.getString("Worldname");

			Location loc = p.getLocation();

			loc.setX(x);
			loc.setY(y);
			loc.setZ(z);
			loc.setYaw((float)yaw);
			loc.setPitch((float)pitch);

			World welt = Bukkit.getWorld(worldname); //TODO: not working line, need to check
			loc.setWorld(welt);

			p.teleport(loc);
			p.sendMessage(successfulPrefix+language.getString("commands.spawn.spawned"));
		}
		*/
	}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "spawn" };
	}
}
