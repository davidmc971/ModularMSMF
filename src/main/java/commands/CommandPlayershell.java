package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import main.ModularMSMF;

/**
 * 
 * @author davidmc971
 *
 */

/*	This command is supposed to be a shell, enabling
 * 	the console to emulate being a player for testing
 * 	purposes.
 */

public class CommandPlayershell extends AbstractCommand implements Listener {

	private Player activeShellPlayer = null;
	
	
	public CommandPlayershell(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			sender.sendMessage("Only for console.");
			return true;
		}
		
		/*	/playershell start <playername>
		 * 	/playershell exit
		 */
		
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "playershell" };
	}

	@EventHandler
	public void onServerCommandEvent(ServerCommandEvent event) {
		if (activeShellPlayer != null) {
			
		}
	}
}
