package io.github.davidmc971.modularmsmf.commands;

import java.io.File;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @authors davidmc971
 *  
 */

public class CommandSetSpawn extends AbstractCommand {

	public CommandSetSpawn(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);

	/**
	 * @TODO Complete rewrite
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
		} else {
			Player p = (Player)sender;

			File file = new File("plugins/ModularMSMF/spawnconfig.yml");

			if(!file.exists()){
				try{
					file.createNewFile();
				}catch(IOException e){
					e.printStackTrace();

				}
			}
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
			Location loc = p.getLocation();

			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			double yaw = loc.getYaw();
			double pitch = loc.getPitch();
			String worldname = loc.getWorld().getName();

			cfg.set("X", x);
			cfg.set("Y", y);
			cfg.set("Z", z);
			cfg.set("Yaw", yaw);
			cfg.set("Pitch", pitch);
			cfg.set("Worldname", worldname);

			try {
				cfg.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.sendMessage(successfulPrefix+language.getString("commands.spawn.spawnset"));
			return true;
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "setspawn" };
	}

}
