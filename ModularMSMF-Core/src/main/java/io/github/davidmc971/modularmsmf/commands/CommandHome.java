package io.github.davidmc971.modularmsmf.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.handlers.HomeHandler;
import io.github.davidmc971.modularmsmf.handlers.HomeHandler.Home;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

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

		// boolean isPlayer = sender instanceof Player;

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
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
							"commands.home.list"/* , "_list", getHomeList() */); // TODO: config for list homes
				}
			}
			if (args[0].equalsIgnoreCase("help")) {
				if (PermissionManager.checkPermission(sender, "home_help")) {
					// list home commands by permissions set for each node
					if (PermissionManager.checkPermission(sender, "home_set")) { // shows the help for "home set"
						helpHome(sender, "/home set", "commands.home.help.set", "general.component.set");
					}
					if (PermissionManager.checkPermission(sender, "home_remove")) { // shows the help for "home remove"
						helpHome(sender, "/home remove", "commands.home.help.remove", "general.component.remove");
					}
					if (PermissionManager.checkPermission(sender, "home_rtp")) { // shows the help for "home rtp" - rtp
																					// = request to teleport (to
																					// someone's home)
						helpHome(sender, "/home rtp", "commands.home.help.rtp", "general.component.rtp");
					}
					if (PermissionManager.checkPermission(sender, "home_admin")) { // shows the help for "home admin"
						helpHome(sender, "/home admin", "commands.home.help.admin", "general.component.admin");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if (args[0].equalsIgnoreCase("set")) {
				// command for "home set"
				if (PermissionManager.checkPermission(sender, "home_set")) {

				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if (args[0].equalsIgnoreCase("remove")) {
				// command for "home remove"
				if (PermissionManager.checkPermission(sender, "home_remove")) {

				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if (args[0].equalsIgnoreCase("rtp")) {
				// command for "home rtp"
				if (PermissionManager.checkPermission(sender, "home_rtp")) {
					if (args.length == 0) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
								"commands.home.rtp.noargument");
					}
					if (args.length == 1) {

					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"general.toomanyarguments");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			}
			if (args[0].equalsIgnoreCase("admin")) {
				// command for "home admin"
				if (PermissionManager.checkPermission(sender, "home_admin")) {

				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
			} else {
				if (PermissionManager.checkPermission(sender, "home_help")) {
					//
					if (!(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("list")
							|| args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("set")
							|| args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rtp")
							|| args[0].equalsIgnoreCase("admin"))) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"commands.home.invalidargument");
					} else {
						// if(/** something that should match home name from args[0] */){

						// }
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM, "general.nopermission");
				}
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

	private void helpHome(CommandSender sender, String cmd, String msg, String eventmsg) {
		/*
		 * TODO: cache language objects per language and generate mappings to players to
		 * improve performance
		 */
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		// FIXME: first send description of command where you can then execute it
		Component message = Component.text(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO))
				.append(Component.text(language.getString(msg))).append(Component.text(" ")).append(Component.text(cmd))
				.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand(cmd))
				.hoverEvent(net.kyori.adventure.text.event.HoverEvent
						.showText(Component.text(language.getString(eventmsg))));

		sender.sendMessage(Identity.nil(), message, MessageType.SYSTEM);
	}

	@Override
	public String[] getCommandLabels() {
		return new String[] { "home" };
	}

}
