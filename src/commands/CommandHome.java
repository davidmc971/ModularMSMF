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
import net.md_5.bungee.api.ChatColor;
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
		
		/**home <help>
		 * home <default OR name>
		 * home list <admin:user>
		 * home set <default OR name>
		 * home remove <default OR name>
		 * home rtp <user>
		 * home admin <list:user set:user:default OR name remove:user:default OR name tp:user:default OR name>
		 */
		
		if (!(sender instanceof Player)) {
			//TODO: console could maybe set home by hand to specific coordinates
			/*} else {*/sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
			return true;
		}
		// TODO: Implementieren
		// Playerdata plrdat =

		if(args.length > 0) {
			
			UUID target = null; //default set to null because no UUID has been chosen
			target = Utils.getPlayerUUIDByName(args[0]); //getting UUID from player if target/sender is online and matches args[0]
			
			switch (args[0].toLowerCase()) {
			case "help": //shows help for these commands
				if(args.length == 1) {
					sender.sendMessage(infoPrefix+"List of your aviable commands:");
					sender.sendMessage(infoPrefix+"For more details just do /home help <command>");
					//shows help if permissions were given
					if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
						sender.sendMessage(ChatColor.GRAY+" [Home:list] "+" /home list -> List all your homes");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
						sender.sendMessage(ChatColor.GRAY+" [Home:set] "+" /home set -> Set's your individual home");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
						sender.sendMessage(ChatColor.GRAY+" [Home:remove] "+" /home remove -> Remove's your individual home");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
						sender.sendMessage(ChatColor.GRAY+" [Home:rtp] " +" /home rtp -> Teleport request to any chosen default's home from a user");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) {
						sender.sendMessage(ChatColor.RED+" [Home:admin] "+" /home admin -> any admin-relevant commands for home");
					}
					//otherwise if no perms (no admin) have been given the statement under this will show up
					if(!sender.hasPermission(PermissionManager.getPermission("home_list")) && !sender.hasPermission(PermissionManager.getPermission("home_set")) && !sender.hasPermission(PermissionManager.getPermission("home_remove")) && !sender.hasPermission(PermissionManager.getPermission("home_rtp"))){
						sender.sendMessage(noPermPrefix+"It seem's like you don't have any relevant permissions to use any commands! Sorry, "+sender.getName());
					return true;
					}
				} else if(args.length == 2){
					switch (args[1].toLowerCase()){
					case "list":
						if(args.length == 2) {
							if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
								sender.sendMessage(ChatColor.GRAY+" [Home:list] "+" Listing all your own home's like the default non-named first and all other named homes, if you ever forget one home.");
							} else {
								sender.sendMessage(noPermPrefix+"You don't have permission to use /home list ? Ask your permission manager why not.");
							}
						}
						break;
					case "set":
						if(args.length == 2) {
							if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
								sender.sendMessage(ChatColor.GRAY+" [Home:set] "+" Setting your individual home means you can set a home without giving it a name by doing /home set < > or you can set a name by doing /home set <name>. Your choice, "+sender.getName());
							} else {
								sender.sendMessage(noPermPrefix+"You don't have permission to use /home set ? Ask your permission manager why not.");
							}
						}
						break;
					case "remove":
						break;
					case "rtp":
						break;
					case "admin":
						break;
					default:
						sender.sendMessage(errorPrefix+"This argument "+ChatColor.GRAY+args[0]+ChatColor.RED+" does not exist!");
						break;
					}
				}
				
				break;
			case "list": //for listing homes of users
				if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
					//src for listing all homes set by it's own.
					if(args.length == 1) {
						//should list all homes of it's own
					//sender.sendMessage(infoPrefix+language.getString("commands.home.Utils.getPlayerHome(sender);
					}
				} else {
					//checks if user has permission for "list"
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "set": //for setting home's for players
				if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
					//src for setting a home's player.
					
				} else {
					//well done, you don't have permission
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "remove":
				//removing a home
				if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
					//src for removing home(s) which has been set
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "rtp":
				if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
					//src for requesting an teleport for another default or first home's location from a player who's online.
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "admin":
				//all commands which an admin should use wisely. every child command has its own permission too to select.
				
				break;
			default:
				if(sender.hasPermission(PermissionManager.getPermission("home"))) {
					
				}
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
