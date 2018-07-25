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

/**
 * @author Lightkeks
 */

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

		// TODO: Implementieren
		// Playerdata plrdat =

		if (!(sender instanceof Player)) {
			switch(args[0].toLowerCase()) {
			case "console":
				if(args.length == 1) {
					sender.sendMessage(infoPrefix+"Console-helping Commands for Home");
					sender.sendMessage(" /home list <user> - Listing Homes of <user>");
					sender.sendMessage(" /home set <user> <default OR name> - Set's an home given to it's player's location with notification");
					sender.sendMessage(" /home remove <user> <default OR name> - Remove's an home given by it's player with notification");
					sender.sendMessage(" /home admin - Just type it in to get the commands");
				} else if(args.length == 2) {
					switch (args[1].toLowerCase()) {
					
					}
				}
				break;
			default:
				
				break;
			}
			return true;
		} else {
			UUID target = null; //default set to null because no UUID has been chosen
			target = Utils.getPlayerUUIDByName(args[0]); //getting UUID from player if target/sender is online and matches args[0]

			switch (args[0].toLowerCase()) {
			case "help": //shows help for these commands - FINISHED WRITING IT
				if(args.length == 1) {
					sender.sendMessage(infoPrefix+"List of your aviable commands:");
					sender.sendMessage(infoPrefix+"For more details just do /home help <command>");
					//shows help if permissions were given
					if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
						sender.sendMessage(ChatColor.GRAY+" /home list -> List all your homes");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
						sender.sendMessage(ChatColor.GRAY+" /home set -> Set's your individual home");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
						sender.sendMessage(ChatColor.GRAY+" /home remove -> Remove's your individual home");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
						sender.sendMessage(ChatColor.GRAY+" /home rtp -> Teleport request to any chosen default's home from a user");
					}
					if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) {
						sender.sendMessage(ChatColor.RED+" /home admin -> any admin-relevant commands for home");
					}
					//otherwise if no perms (no admin) have been given the statement under this will show up
					if(!sender.hasPermission(PermissionManager.getPermission("home_list")) && !sender.hasPermission(PermissionManager.getPermission("home_set")) && !sender.hasPermission(PermissionManager.getPermission("home_remove")) && !sender.hasPermission(PermissionManager.getPermission("home_rtp"))){
						sender.sendMessage(noPermPrefix+"It seem's like you don't have any relevant permissions to use any commands! Sorry, "+sender.getName());
						return true;
					}
				} else if(args.length == 2){
					switch (args[1].toLowerCase()){ //shows permission like above which perm is given and it's message
					case "list":
						if(args.length == 2) {
							if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
								sender.sendMessage(ChatColor.GRAY+" Listing all your own home's like the default non-named first and all other named homes, if you ever forget one home.");
							} else {
								sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
							}
						}
						break;
					case "set":
						if(args.length == 2) {
							if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
								sender.sendMessage(ChatColor.GRAY+" Setting your individual home means you can set a home without giving it a name by doing /home set < > or you can set a name by doing /home set <name>. Your choice, "+sender.getName());
							} else {
								sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
							}
						}
						break;
					case "remove":
						if(args.length == 2) {
							if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
								sender.sendMessage(ChatColor.GRAY+" Remove's your default or individual home which you can find under /home list. Remove it like this: /home remove < > or if a name is given with /home remove <NAME>");
							} else {
								sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
							}
						}
						break;
					case "rtp":
						if(args.length == 2) {
							if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
								sender.sendMessage(ChatColor.GRAY+" Requests an teleport to any user's home set. Mostly only default home is chosen, if not otherwise known.");
							} else {
								sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
							}
						}
						break;
					case "admin":
						if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) {
							if(args.length == 2) {
								sender.sendMessage(ChatColor.GRAY+" These are all relevant administrative tools to control all user's homes. There's nothing which could miss. Otherwise ask the dev's of this plugin under /mmsmf info ");
							}
							if(sender.isOp()) {
								sender.sendMessage(ChatColor.RED+" OP: There is nothing you can't do. Just use it wisely.");
							} else {
								sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
							}
						} else {
							sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
						}
						break;
					default:
						sender.sendMessage(errorPrefix+"This argument "+ChatColor.GRAY+args[1]+ChatColor.RED+" does not exist!");
						break;
					}
				} else {
					sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
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
					if(args[1].equals(target)) { //teleporting to user's first, default home

					}
					//request which can do /home rtp <username> <homename OR default> and the other, which receive invite do /home rtp <yes OR no> || @TODO should work IF THERE'S AN REQUEST, IF NOT, THE USER MUST BE NOTIFIED or NOTHING SHOULD HAPPEN!
					if(args[1].equals("yes") || args[2].equals("no")) {
						if(args[1].equals("yes")){

						} else if(args[2].equals("no")) {

						}
					}
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			case "admin":
				if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) {
					//all commands which an admin should use wisely. every child command has its own permission too to select.
					/**
					 * home admin list <target>
					 * home admin tp <target> <default OR name>
					 * home admin remove <target> <default OR name>
					 * home admin set <target> <default OR name>
					 * home admin - lists all commands for case admin
					 */

					if(args.length == 1) { //gives list of commands ingame
						sender.sendMessage(infoPrefix+" As admin,");
					}
					if(args.length == 2) { //checks if target exists

					}
					if(args.length == 3) { //checks if default or named home exists to tp, remove or set

					} else if(args.length > 3) {
						sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
					}
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			default:
				if(sender.hasPermission(PermissionManager.getPermission("home"))) {

				}
				//src for teleporting to its home or any home which has been labeled with a custom name, which was defined under "set"
				/** } else {
				 * sender.sendMessage(errorPrefix+"language.getString("commands.home.notfoundhome"));
				 * }
				 * 
				 * home <default OR name>
				 */
				break;
			}

			//args leer, entsprechende description senden
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "home" };
	}
}
