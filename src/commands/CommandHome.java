package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import core.PermissionManager;
import main.ModularMSMF;
import util.ChatUtils;
import util.Utils;

public class CommandHome extends AbstractCommand {

	public CommandHome(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);
		
		if (!(sender instanceof Player)) {
			//TODO: console could maybe set home by hand to specific coordinates
			sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
			return true;
		}
		// TODO: Implementieren
		// Playerdata plrdat =

		if(args.length > 0) {
			
			UUID target = null; //default set to null because no UUID has been chosen
			target = Utils.getPlayerUUIDByName(args[0]); //getting UUID from player if target/sender is online and matches args[0]
			
			switch (args[0].toLowerCase()) {
			case "list": //for listing homes of users
				if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
					//src for listing all homes set by it's own. admin can list player's home's for itself
					if(args.length == 1) {
						//should list all homes of it's own
					//sender.sendMessage(infoPrefix+language.getString("commands.home.Utils.getPlayerHome(sender);
					}
					
					else if(args.length == 2) {
						//checks if there are more than only "list" as arg
						if(sender.hasPermission(PermissionManager.getPermission("home_list_admin"))) {
							//checks if user granted permission to list other player's home's
							for (Player p : Bukkit.getOnlinePlayers()) {
								//checks if specified user is online
								if(p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
									//listing homes of specified target
									//sender.sendMessage(infoPrefix+"All homes of /*_player <-- need to replace it with args*/:"+(Utils.listPlayerHome)); // not implemented yet in Utils ~Lightkeks
								}
							}
							for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
								//otherwise checks if user is offline
								if(p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
									sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
								}
							}
						} else {
							//so this boi doesnt have permission to use admin privilegues
							sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
						}
					} // @davidmc971: kann man hier noch checken ob der user ueberhaupt auf dem server war?
				} else {
					//checks if user has permission for "list"
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "set": //for setting home's for players
				if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
					//src for setting a home's player. admin can set on it's own position or from a player's a new home too.
					
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "remove":
				if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
					//src for removing home(s) which has been set, otherwise admin can remove selected or all homes from a player
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "rtp":
				if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
					//src for requesting an teleport for another home's location from a player who's online. admin can w/o requesting.
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			default:
				//src for teleporting to its home or any home which has been labeled with a custom name, which was defined under "set"
				/** } else {
				 * sender.sendMessage(errorPrefix+"language.getString("commands.home.notfoundhome"));
				 * }
				 */
				break;
			}
			
		} else {
			//args leer, entsprechende description senden
		}
		return true;
	}
	
	@Override
	public String[] getCommandLabels() {
		return new String[]{ "home" };
	}
}
