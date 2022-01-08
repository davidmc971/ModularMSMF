package io.github.davidmc971.modularmsmf.basics.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.basics.PermissionManager;
import io.github.davidmc971.modularmsmf.basics.handlers.HomeHandler;
import io.github.davidmc971.modularmsmf.basics.handlers.HomeHandler.Home;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;

/**
 * @author Lightkeks
 */

public class CommandHome implements IModularMSMFCommand {
	private HomeHandler homeHandler;

	private ModularMSMFCore plugin;

	public CommandHome() {
		plugin = ModularMSMFCore.Instance();
		this.homeHandler = new HomeHandler(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		/**
		 * TODO: FIXME:
		 * Complete Rewrite
		 */

		/**
		 * Important notice: Home will be edited in some days. Thinking about the output
		 * of sending messages
		 * 
		 * FIXME: changing structure for each subcommand
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
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
							"basicsmodule.commands.home.notset");
				} else {
					// TODO: teleport user to position where home is located
				}
			}
			break;

		case 1:
			// TODO: check if user uses args like "help" or home name
			if (args[0].equalsIgnoreCase("list")) {
				if (PermissionManager.checkPermission(sender, "home_list")) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
							"basicsmodule.commands.home.list"/* , "_list", getHomeList() */); // TODO: config for list
																								// homes
				}
			}
			if (args[0].equalsIgnoreCase("help")) {
				if (PermissionManager.checkPermission(sender, "home_help")) {
					// list home commands by permissions set for each node
					if (PermissionManager.checkPermission(sender, "home_set")) { // shows the help for "home set"
						helpHome(sender, "/home set", "basicsmodule.commands.home.help.set",
								"basicsmodule.commands.home.component.set");
					}
					if (PermissionManager.checkPermission(sender, "home_remove")) { // shows the help for "home remove"
						helpHome(sender, "/home remove", "basicsmodule.commands.home.help.remove",
								"basicsmodule.commands.home.component.remove");
					}
					if (PermissionManager.checkPermission(sender, "home_rtp")) { // shows the help for "home rtp" - rtp
																					// = request to teleport (to
																					// someone's home)
						helpHome(sender, "/home rtp", "basicsmodule.commands.home.help.rtp", "basicsmodule.commands.home.component.rtp");
					}
					if (PermissionManager.checkPermission(sender, "home_admin")) { // shows the help for "home admin"
						helpHome(sender, "/home admin", "basicsmodule.commands.home.help.admin", "basicsmodule.commands.home.component.admin");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
				}
			}
			if (args[0].equalsIgnoreCase("set")) {
				// command for "home set"
				if (PermissionManager.checkPermission(sender, "home_set")) {

				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
			case 0:
				if (PermissionManager.checkPermission(sender, "home_self")) {
					boolean success = teleportToHome((Player) sender, sender.getName());
					if (!success) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
								"basicsmodule.commands.home.notset");
					} else {
						// TODO: teleport user to position where home is located
					}
				}
				break;

				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
				}
			}
			if (args[0].equalsIgnoreCase("rtp")) {
				// command for "home rtp"
				if (PermissionManager.checkPermission(sender, "home_rtp")) {
					if (args.length == 0) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO,
								"basicsmodule.commands.home.rtp.noargument");
			case 1:
				// TODO: check if user uses args like "help" or home name
				if (args[0].equalsIgnoreCase("list")) {
					if (PermissionManager.checkPermission(sender, "home_list")) {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
								"basicsmodule.commands.home.list"/* , "_list", getHomeList() */); // TODO: config for
																									// list
																									// homes
					}
				}
				if (args[0].equalsIgnoreCase("help")) {
					if (PermissionManager.checkPermission(sender, "home_help")) {
						// list home commands by permissions set for each node
						if (PermissionManager.checkPermission(sender, "home_set")) { // shows the help for "home set"
							helpHome(sender, "/home set", "basicsmodule.commands.home.help.set",
									"basicsmodule.commands.home.component.set");
						}
						if (PermissionManager.checkPermission(sender, "home_remove")) { // shows the help for "home
																						// remove"
							helpHome(sender, "/home remove", "basicsmodule.commands.home.help.remove",
									"basicsmodule.commands.home.component.remove");
						}
						if (PermissionManager.checkPermission(sender, "home_rtp")) { // shows the help for "home rtp" -
																						// rtp
																						// = request to teleport (to
																						// someone's home)
							helpHome(sender, "/home rtp", "basicsmodule.commands.home.help.rtp",
									"basicsmodule.commands.home.component.rtp");
						}
						if (PermissionManager.checkPermission(sender, "home_admin")) { // shows the help for "home
																						// admin"
							helpHome(sender, "/home admin", "basicsmodule.commands.home.help.admin",
									"basicsmodule.commands.home.component.admin");
						}
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
								"coremodule.player.nopermission");
					}
				}
				if (args[0].equalsIgnoreCase("set")) {
					// command for "home set"
					if (PermissionManager.checkPermission(sender, "home_set")) {

					} else {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
								"basicsmodule.commands.arguments.toomany");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
								"coremodule.player.nopermission");
					}
				}
				if (args[0].equalsIgnoreCase("remove")) {
					// command for "home remove"
					if (PermissionManager.checkPermission(sender, "home_remove")) {

				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
				}
			} else {
				if (PermissionManager.checkPermission(sender, "home_help")) {
					//
					if (!(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("list")
							|| args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("set")
							|| args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rtp")
							|| args[0].equalsIgnoreCase("admin"))) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
								"basicsmodule.commands.home.invalidargument");
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
								"coremodule.player.nopermission");
					}
				}
				if (args[0].equalsIgnoreCase("rtp")) {
					// command for "home rtp"
					if (PermissionManager.checkPermission(sender, "home_rtp")) {
						if (args.length == 0) {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.INFO,
									"basicsmodule.commands.home.rtp.noargument");
						}
						if (args.length == 1) {

						} else {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basicsmodule.commands.arguments.toomany");
						}
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
								"coremodule.player.nopermission");
					}
				}
				if (args[0].equalsIgnoreCase("admin")) {
					// command for "home admin"
					if (PermissionManager.checkPermission(sender, "home_admin")) {

					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
								"coremodule.player.nopermission");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
					if (PermissionManager.checkPermission(sender, "home_help")) {
						//
						if (!(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("list")
								|| args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("set")
								|| args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rtp")
								|| args[0].equalsIgnoreCase("admin"))) {
							Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.ERROR,
									"basicsmodule.commands.home.invalidargument");
						} else {
							// if(/** something that should match home name from args[0] */){

							// }
						}
					} else {
						Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.NOPERM,
								"coremodule.player.nopermission");
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
		FileConfiguration language = Utils.configureCommandLanguage(sender);

		// FIXME: first send description of command where you can then execute it
		Component message = Component.text(ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO))
				.append(Component.text(language.getString(msg))).append(Component.text(" ")).append(Component.text(cmd))
				.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand(cmd))
				.hoverEvent(net.kyori.adventure.text.event.HoverEvent
						.showText(Component.text(language.getString(eventmsg))));

		sender.sendMessage(Identity.nil(), message, MessageType.SYSTEM);
	}

	@Override
	public String Label() {
		return "home";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
