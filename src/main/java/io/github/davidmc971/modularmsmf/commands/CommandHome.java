package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.LanguageManager;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.handlers.HomeHandler;
import io.github.davidmc971.modularmsmf.handlers.HomeHandler.Home;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
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

		/**
		 * Important notice: Home will be edited in some days. Thinking about the output
		 * of sending messages
		 */

		/**
		 * home <help> home <default OR name> home list <admin:user> home set <default
		 * OR name> home remove <default OR name> home rtp <user> home admin <list:user
		 * set:user:default OR name remove:user:default OR name tp:user:default OR name>
		 * 
		 * CONSOLE COMMANDS: home console home help -> home console home -> home console
		 * home * -> error!
		 * 
		 * PLAYER COMMANDS: home [optional: homeName] home set [optional: homeName] home
		 * remove [optional: homeName] home list home rtp [userName] [optional:
		 * homeName] home admin [args]
		 */

		// TODO: Implementieren
		// Playerdata plrdat =

		//boolean isPlayer = sender instanceof Player;

		switch (args.length) {
		case 0:
			if (PermissionManager.checkPermission(sender, "home_self")) {
				boolean success = teleportToHome((Player) sender, sender.getName());
				if (!success) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.home.notset");
				} else {
					// TODO: teleport user to position where home is located
				}
			}
			break;

		case 1:
			// TODO: check if user uses args like "help" or home name
			if (args[0].equalsIgnoreCase("list")) {
				if (PermissionManager.checkPermission(sender, "home_list")) {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "commands.home.list"/*, "_list", getHomeList()*/); //TODO: config for list homes
				}
			}
			if (args[0].equalsIgnoreCase("help")) {
				if (PermissionManager.checkPermission(sender, "home_help")) {
					// list home commands by permissions set for each node
					if (PermissionManager.checkPermission(sender, "home_set")) { //shows the help for "home set"
						helpHome(sender, "/home set", "commands.home.help.set", "general.component.set");
					}
					if(PermissionManager.checkPermission(sender, "home_remove")){
						helpHome(sender, "/home remove", "commands.home.help.remove", "general.component.remove");
					}
					if(PermissionManager.checkPermission(sender, "home_rtp")){
						helpHome(sender, "/home rtp", "commands.home.help.rtp", "general.component.rtp");
					}
					if(PermissionManager.checkPermission(sender, "home_admin")){
						helpHome(sender, "/home admin", "commands.home.help.admin", "general.component.admin");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if(args[0].equalsIgnoreCase("set")){
				if(PermissionManager.checkPermission(sender, "home_set")){

				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if(args[0].equalsIgnoreCase("remove")){
				if(PermissionManager.checkPermission(sender, "home_remove")){

				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if(args[0].equalsIgnoreCase("rtp")){
				if(PermissionManager.checkPermission(sender, "home_rtp")){
					if(args.length == 0){
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO, "commands.home.rtp.noargument");
					}
					if(args.length == 1){

					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "general.toomanyarguments");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if(args[0].equalsIgnoreCase("admin")){
				if(PermissionManager.checkPermission(sender, "home_admin")){

				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}

			if(PermissionManager.checkPermission(sender, "home_help")){
				if(!(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rtp") || args[0].equalsIgnoreCase("admin"))){
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR, "commands.home.invalidargument");
				} else {
					if(/** args[0] + something which matches any player home name? */){

					}
				}
			}  else {
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
			}
			break;

			case 2:

			break;

			default:
			
			break;
		}
		return true;
	}

	boolean teleportToHome(Player player, String name) {
		UUID uuid = player.getUniqueId();
		Home h = homeHandler.getPlayerHome(uuid, null);
		if (h != null) {
			player.teleport(h.getLoc());
			return true;
		}
		return false;
	}
	


	private void helpHome(CommandSender sender, String cmd, String msg, String eventmsg){

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);
		TextComponent homeHelpComponent = new TextComponent(cmd);
		homeHelpComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
		homeHelpComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(language.getString(eventmsg) ).create()));
		
		TextComponent message = new TextComponent();
		message.addExtra(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO));
		message.addExtra(language.getString(msg));
		message.addExtra(" ");
		message.addExtra(homeHelpComponent);
		sender.spigot().sendMessage(message); 
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "home" };
	}
	
}
