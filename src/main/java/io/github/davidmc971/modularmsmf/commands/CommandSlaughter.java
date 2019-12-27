package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.util.ChatUtils;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandSlaughter extends AbstractCommand {

	public CommandSlaughter(ModularMSMF plugin) {
		super(plugin);
	}

	private String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
	private String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
	private String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
	private String successfulPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.SUCCESS);
		/**
		 * @TODO finishing this because does not work properly, only message works
		 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Location playerloc = ((Player) sender).getLocation();
			for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
				if (!(e instanceof Player) && (e instanceof Monster))
					e.remove();
				
			}
			sender.sendMessage("Es wurde alles im Umkreis abgeschlachtet");
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "slaughter" };
	}

}
