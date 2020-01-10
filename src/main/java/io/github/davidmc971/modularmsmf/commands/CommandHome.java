package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.handlers.HomeHandler;
import io.github.davidmc971.modularmsmf.handlers.HomeHandler.Home;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * @author Lightkeks
 */

public class CommandHome extends AbstractCommand {
	private HomeHandler homeHandler;
	
	public CommandHome(ModularMSMF plugin) {
		super(plugin);
		this.homeHandler = new HomeHandler(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		/**home <help>
		 * home <default OR name>
		 * home list <admin:user>
		 * home set <default OR name>
		 * home remove <default OR name>
		 * home rtp <user>
		 * home admin <list:user set:user:default OR name remove:user:default OR name tp:user:default OR name>
		 * 
		 * 	CONSOLE COMMANDS:
		 * 	home console
		 * 	home help -> home console
		 * 	home -> home console
		 * 	home * -> error!
		 * 
		 * 	PLAYER COMMANDS:
		 * 	home [optional: homeName]
		 * 	home set [optional: homeName]
		 * 	home remove [optional: homeName]
		 * 	home list
		 * 	home rtp [userName] [optional: homeName]
		 * 	home admin [args]
		 */

		// TODO: Implementieren
		// Playerdata plrdat =

		String infoPrefix = "";

		boolean isPlayer = sender instanceof Player;

		if (args.length == 0) {
			//only /home without args
			if (isPlayer) {
				//teleport player to default home if set
				boolean success = teleportToHome((Player)sender, null);
				if (!success) {
					sender.sendMessage(infoPrefix + "Default home has not been set."); //TODO: adding to language file
				}
				return true;
			} else {
				//modify args to display console subcommand
				args = new String[] {"help"};
			}
		}

		switch (args[0].toLowerCase()) {
		//HELP COMMANDS
		case "help":
			if(isPlayer) {
				//display player help
				return displayHelp(sender, args);
			} else {
				//display console help //TODO: adding to language file
				if(args.length == 1) {
					sender.sendMessage(infoPrefix+"Console-helping Commands for Home"); 
					sender.sendMessage(" /home list <user> - Listing Homes of <user>");
					sender.sendMessage(" /home set <user> <default OR name> - Set's an home given to it's player's location with notification");
					sender.sendMessage(" /home remove <user> <default OR name> - Remove's an home given by it's player with notification");
				}
			}
			break;
		//FUNCTIONAL COMMANDS
		case "set":
			if (isPlayer) {
				//PLAYER VERSION of /home set
				switch(args.length) {
				case 1:
					//set default home
					homeHandler.setPlayerHome(((Player)sender).getUniqueId(), null, ((Player)sender).getLocation());
					break;
				case 2:
					//set named home
					homeHandler.setPlayerHome(((Player)sender).getUniqueId(), args[1].toLowerCase(), ((Player)sender).getLocation());
					break;
				default:
					
					break;
				}
			} else {
				//CONSOLE VERSION of /home set
			}
			
			
			if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
				//src for setting a home's player.

			} else {
				//well done, you don't have permission
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;
		case "remove":
			//removing a home
			if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
				//src for removing home(s) which has been set
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;
		case "list":
			if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
				//src for listing all homes set by it's own.
				if(args.length == 1) {
					//should list all homes of it's own
					//sender.sendMessage(infoPrefix+language.getString("commands.home.Utils.getPlayerHome(sender); //TODO: adding to language file
				}
			} else {
				//checks if user has permission for "list"
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;
		case "rtp":
			UUID target = null; //default set to null because no UUID has been chosen
			target = Utils.getPlayerUUIDByName(args[0]); //getting UUID from player if target/sender is online and matches args[0]
			
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
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;
		case "admin":
			if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) { //TODO: adding to language file
				//all commands which an admin should use wisely. every child command has its own permission too to select.
				/**
				 * home admin list <target>
				 * home admin tp <target> <default OR name>
				 * home admin remove <target> <default OR name>
				 * home admin set <target> <default OR name>
				 * home admin - lists all commands for case admin
				 */

				if(args.length == 1) { //gives list of commands ingame
				}
				if(args.length == 2) { //checks if target exists

				}
				if(args.length == 3) { //checks if default or named home exists to tp, remove or set

				} else if(args.length > 3) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
				}
			} else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;
		default:
			boolean success = teleportToHome((Player)sender, args[0].toLowerCase());
			if (!success) {
				sender.sendMessage(infoPrefix + "Home \"" + args[0] + "\" has not been set."); //TODO: adding to lang files
				
			}
			break;
		}
		
		return true;
	}
	
	private boolean teleportToHome(Player player, String name) {
		UUID uuid = player.getUniqueId();
		Home h = homeHandler.getPlayerHome(uuid, null);
		if (h != null) {
			player.teleport(h.getLoc());
			return true;
		}
		return false;
	}

	private boolean displayHelp(CommandSender sender, String[] args) {
		if(args.length == 1) {
			/**
			 * if you only use the command without any arg, this will show if perms are given
			 * /home
			 */
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help"); //TODO: adding to lang files
			//shows help if permissions were given
			if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.list"); //TODO: adding to lang files
			}
			if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.set"); //TODO: adding to lang files
			}
			if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.remove"); //TODO: adding to lang files
			}
			if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
				//sender.sendMessage(ChatColor.GRAY+" /home rtp -> Teleport request to any chosen default's home from a user"); // DONT REMOVE THIS LINE YET
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.rtp"); //TODO: adding to lang files
			}
			if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.admin"); //TODO: adding to lang files
			}
			//otherwise if no perms (no admin) have been given the statement under this will show up
			if(!sender.hasPermission(PermissionManager.getPermission("home_list")) && !sender.hasPermission(PermissionManager.getPermission("home_set")) && !sender.hasPermission(PermissionManager.getPermission("home_remove")) && !sender.hasPermission(PermissionManager.getPermission("home_rtp"))){
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission"); //TODO: adding to lang files
				return true;
			}
		} else if(args.length == 2){
			switch (args[1].toLowerCase()){ //shows permission like above which perm is given and it's message

			/**
			 * Help for each command, given by this switch...
			 * /home <case>
			 */

			case "list":
				if(args.length == 2) {
					if(sender.hasPermission(PermissionManager.getPermission("home_list"))) {
						/**
						 * if sender has homes set, they should be shown instead of an explanation... TODO: need work on it
						 */
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.list1"); //TODO: adding to lang files
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
				}
				break;
			case "set":
				if(args.length == 2) {
					if(sender.hasPermission(PermissionManager.getPermission("home_set"))) {
						//sender.sendMessage(ChatColor.GRAY+" Setting your individual home means you can set a home without giving it a name by doing /home set < > or you can set a name by doing /home set <name>. Your choice, "+sender.getName());
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.set1");
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
				}
				break;
			case "remove":
				if(args.length == 2) {
					if(sender.hasPermission(PermissionManager.getPermission("home_remove"))) {
						//sender.sendMessage(ChatColor.GRAY+" Remove's your default or individual home which you can find under /home list. Remove it like this: /home remove < > or if a name is given with /home remove <NAME>");
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.remove1"); //TODO: adding to lang files
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
				}
				break;
			case "rtp":
				if(args.length == 2) {
					if(sender.hasPermission(PermissionManager.getPermission("home_rtp"))) {
						//sender.sendMessage(ChatColor.GRAY+" Requests an teleport to any user's home set. Mostly only default home is chosen, if not otherwise known.");
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.rtp1"); //TODO: adding to lang files
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
				}
				break;
			case "admin":
				if(sender.hasPermission(PermissionManager.getPermission("home_admin"))) {
					if(args.length == 2) {
						//sender.sendMessage(ChatColor.GRAY+" These are all relevant administrative tools to control all user's homes. There's nothing which could miss. Otherwise ask the dev's of this plugin under /mmsmf info ");
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.HOME, "commands.home.help.admin1"); //TODO: adding to lang files
					}
					if(sender.isOp()) {
						//sender.sendMessage(ChatColor.RED+" OP: There is nothing you can't do. Just use it wisely.");
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "commands.home.help.opinfo"); //TODO: adding to lang files
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
				break;
			default:
				//sender.sendMessage(errorPrefix+"This argument "+ChatColor.GRAY+args[1]+ChatColor.RED+" does not exist!");
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.invalidarguments");
				break;
			}
		} else {
			Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
		}

		return true;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "home" };
	}
	
}
