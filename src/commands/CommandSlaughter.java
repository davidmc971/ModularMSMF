package commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import main.ModularMSMF;

/**
 * 
 * @author davidmc971
 *
 */

public class CommandSlaughter extends AbstractCommand {

	public CommandSlaughter(ModularMSMF plugin) {
		super(plugin);
	}
		/**
		 * @TODO finishing this
		 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Location playerloc = ((Player) sender).getLocation();
			for (Entity e : ((Player) sender).getWorld().getNearbyEntities(playerloc, 500, 500, 500)) {
				if (!(e instanceof Player) && (e instanceof Monster))
					e.remove();
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "slaughter" };
	}

}
