package io.github.davidmc971.modularmsmf.economy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.Utils;

/**
 * TODO: Mainclass for economysystem <3
 * 
 * @author Lightkeks with help from @author David Alexander Pfeiffer (davidmc971) TODO: changing all
 *         sendMessage with Utils.sendMessage...
 */

public class EconomySystem implements IModularMSMFCommand {
	private ModularMSMFCore plugin;
	public static double defaultMoney = 500.0;

    public EconomySystem() {
        plugin = ModularMSMFCore.Instance();
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		UUID uuid = null;
		UUID target = null;
		double amount = 0;
		if (sender instanceof Player) {
			uuid = ((Player) sender).getUniqueId();
		}
		String currencyFormat = "\\$";
		// TODO: something like language.getCurrencyFormat() which loads from
		// settings.yml

		if (args.length == 0) {
			if (sender instanceof Player) {
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "economy-module.commands.eco.balance.self", "_value", (String) (getMoney(uuid) + currencyFormat));
			} else {
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.CONSOLE, "economy-module.commands.eco.error.console");
			}
		} else if (args.length > 0) {
			switch (args[0].toLowerCase()) {
			case "help":
				if (args.length > 1) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "economy-module.commands.eco.helponly");
				} else {
					/**
					 * TODO: Rewrite into changeable language without using lang-yml
					 */
					// sender.sendMessage("[Eco] Alle Befehle:");
					// sender.sendMessage("[Eco] /eco help <- Diese Hilfe");
					// sender.sendMessage("[Eco] /eco set [Name] [Amount] <- Geld setzen");
					// sender.sendMessage("[Eco] /eco [Name] <- Anzeigen des Geldes vom Spieler");
					// sender.sendMessage("[Eco] /eco add [Name] [Amount] <- Geld hinzufügen");
					// sender.sendMessage("[Eco] /eco take [Name] [Amount] <- Geld von Spieler
					// nehmen");
					// sender.sendMessage("[Eco] /eco pay [Name] [Amount] <- Geld an Spieler
					// überweisen");
					return true;
				}
				break;
			case "set":
			case "add":
			case "take":
				// Argument checking
				if (args.length < 2) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.eco.error.syntax");
					return true;
				} else if (args.length > 3) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.commands.arguments.toomany");
					return true;
				}

				boolean toSelf = (args.length == 2);

				if (!(sender instanceof Player) && toSelf) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.CONSOLE, "economy-module.commands.eco.error.console");
					break;
				}

				if (!toSelf) {
					target = Utils.getPlayerUUIDByName(args[1]);
					if (target == uuid && (sender instanceof Player)) toSelf = true;
				}

				String action = args[0];

				// Permission checking
				// TODO: handle ".other" differently
				String permissionStringInternal = "eco_" + action;

				if (plugin.debug) plugin.getLogger().info("Checking permStrInt [" + permissionStringInternal + "]");

				String permissionStringExternal = PermissionManager.getPermission(permissionStringInternal) + (toSelf ? "" : ".other");

				if (plugin.debug) plugin.getLogger().info("Checking permStrExt [" + permissionStringExternal + "]");

				if (!sender.hasPermission(permissionStringExternal)) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
					break;
				}

				if (!toSelf) {
					if (target == null) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.unknown");
						return true;
					} else {
						Player p = Bukkit.getPlayer(target);
						if (p == null || !p.isOnline()) {
							// Do we want an online check here?
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notonline");
							return true;
						}
					}
				}

				// value is always last arg, we can save a few lines of code here
				// discuss whether we want to check if it's a negative number or not
				try {
					amount = Double.parseDouble(args[args.length - 1]);
				} catch (Exception e) {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.eco.error.syntax");
					return true;
				}

				UUID temp = (toSelf ? uuid : target);
				double before = getMoney(temp);
				//String targetSender = Bukkit.getName();

				// perform action
				if (action.equals("set")) setMoney(temp, amount);
				else if (action.equals("add")) addMoney(temp, amount);
				else if (action.equals("take")) takeMoney(temp, amount);

				// TODO: working, still want to use Utils.sendMessageWithConfiguredLanguage()
				//sender.sendMessage((toSelf ? language.getString("commands.eco.set.self.full") : language.getString("commands.eco.set.other.full").replace("_target", args[1])) .replace("_value_old", before + currencyFormat) .replace("_value_new", getMoney(temp) + currencyFormat));
				if(sender instanceof ConsoleCommandSender){
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.CONSOLE, "coremodule.noconsole");
				} else
				if (toSelf = true){
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "economy-module.commands.eco.set.self.full", "_target", args[1], "_value_old", (String) (before+currencyFormat), "_value_new", (String) (getMoney(temp)+currencyFormat));
					//Utils.sendMessageWithConfiguredLanguage(plugin, Bukkit.getPlayer(target), ChatFormat.INFO, "commands.eco.set.notify", "_sender", sender.getName(), "_value_old", (String) (before+currencyFormat), "_value_new", (String) (getMoney(temp)+currencyFormat));
				}else{
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "economy-module.commands.eco.set.other.full", "_target", args[1], "_value_old", (String) (before+currencyFormat), "_value_new", (String) (getMoney(temp)+currencyFormat));
					Utils.sendMessageWithConfiguredLanguage(Bukkit.getPlayer(target), ChatFormat.INFO, "economy-module.commands.eco.set.notify", "_sender", sender.getName(), "_value_old", (String) (before+currencyFormat), "_value_new", (String) (getMoney(temp)+currencyFormat));
			//}	
				}


				// notify other player if !toSelf
				//if (!toSelf)
					//Bukkit.getPlayer(target) .sendMessage(language.getString("commands.eco.set.notify") .replace("_sender", sender.getName()).replace("_value_old", before + currencyFormat) .replace("_value_new", getMoney(temp) + currencyFormat));
					//Utils.sendMessageWithConfiguredLanguage(plugin, Bukkit.getPlayer(target), ChatFormat.INFO, "commands.eco.set.notify", "_sender", sender.getName(), "_value_old", (String) (before+currencyFormat), "_value_new", (String) (getMoney(temp)+currencyFormat));
				break;
			// case "add":
			// if (sender.hasPermission(PermissionManager.getPermission("eco_add"))) {
			// switch (args.length) {
			// default:
			// if (args.length < 2) {
			// //sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!");
			// return true;
			// } else if (args.length > 3) {
			// sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			// return true;
			// }
			// target = Utils.getPlayerUUIDByName(args[1]);
			// if (target == null) {
			// sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
			// return true;
			// } else if (!Bukkit.getPlayer(target).isOnline()) {
			// sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
			// return true;
			// }
			// case 2:
			// try {
			// amount = Integer.parseInt(args[1]);
			// } catch (NumberFormatException e) {
			// sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount"));
			// //need to change language string
			// return true;
			// }
			// if (amount <= 0) {
			// sender.sendMessage(errorPrefix+language.getString("commands.eco.ecoforbiddenminus"));
			// } else {
			// //sender.sendMessage("[Eco] Du hast nun " + amount + "$ zu deinem Konto
			// hinzugefügt!"); //need to change language string
			// addMoney(uuid, amount);
			// }
			// break;
			// case 3:
			// try {
			// amount = Integer.parseInt(args[1]);
			// } catch (NumberFormatException e) {
			// sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount"));
			// //need to change language string
			// return true;
			// }
			// addMoney(target, amount);
			// //sender.sendMessage("[Eco] Du hast " + target + " " + amount + "$
			// hinzugefügt!"); //need to change language string
			// //Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$
			// gutgeschrieben. Neuer Stand: " + getMoney(target) + "$"); //need to change
			// language string
			// break;
			// }
			// } else {
			// sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			// }
			// break;
			// case "take":
			// if (sender.hasPermission(PermissionManager.getPermission("eco_take"))) {
			// if (args.length < 2) {
			// //sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!");
			// //need to change language string
			// } else if (args.length == 2) {
			// try {
			// amount = Integer.parseInt(args[1]);
			// } catch (NumberFormatException e) {
			// sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount"));
			// //need to change language string
			// return true;
			// }
			// if (amount <= 0) {
			// sender.sendMessage(errorPrefix+language.getString("commands.money.invalidpayment"));
			// //need to change language string
			// } else {
			// //sender.sendMessage("[Eco] Du hast nun " + amount + "$ von deinem Konto
			// weggenommen!"); //need to change language string
			// takeMoney(uuid, amount);
			// }
			// } else if (args.length == 3) {
			// target = Utils.getPlayerUUIDByName(args[1]);
			// if (target == null) {
			// sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
			// return true;
			// } else if (!Bukkit.getPlayer(target).isOnline()) {
			// sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
			// return true;
			// }
			// //sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$
			// weggenommen!"); //need to change language string
			// takeMoney(target, amount);
			// //Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$
			// weggenommen. Neuer Stand: " + getMoney(target) + "$"); //need to change
			// language string
			// } else {
			// sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			// }
			// } else {
			// sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			// }
			// break;
			case "pay": //FIXME: Wichtig - Output = null, please fix
				if (PermissionManager.checkPermission(sender, "eco_pay")) {
					if (args.length < 2) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.money.missingamountandplayer");
					}
					if (args.length == 2) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.money.missingamount");
					}
					if (args.length == 3) {
						target = Utils.getPlayerUUIDByName(args[1]);
						if (target == null) {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.unknown");
							return true;
						} else if (!Bukkit.getPlayer(target).isOnline()) {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.player.notfound");
							return true;
						}
						try {
							amount = Integer.parseInt(args[2]);
						} catch (NumberFormatException e) {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.money.amountonly");
							return true;
						}
						if (amount <= 0) {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.money.invalidpayment");
						} else if (getMoney(uuid) - amount < 0) {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "economy-module.commands.money.nomoneyleft");
						} else {
							takeMoney(uuid, amount);
							addMoney(target, amount);
							// sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$
							// überwiesen!"); //TODO: need to change language string
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.SUCCESS, "economy-module.commands.eco.paytoothers", "_target", args[1], "_amount", Double.toString(getMoney(target)));
							//plugin.getLogger().info(sender.getName()+"sender"+Double.toString(getMoney(target)));;
							// Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$ von "
							// + ((Player) sender).getName() + " überwiesen. Neuer Stand: " +
							// getMoney(target) + "$"); //TODO: need to change language string
							Utils.sendMessageWithConfiguredLanguage(Bukkit.getPlayer(target), ChatFormat.SUCCESS, "economy-module.commands.eco.payfromothers", "_sender", sender.getName(), "_amount", Double.toString(getMoney(target)));
							//plugin.getLogger().info(args[1]+"target"+Double.toString(getMoney(target)));;
						}
					} else if (args.length >= 3) {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
								"coremodule.commands.arguments.toomany");
					}
				} else {
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
				}
				break;
			default:
				switch (args.length) {
				case 1:
					if (PermissionManager.checkPermission(sender, "eco_lookup")) {
						target = Utils.getPlayerUUIDByName(args[0]);
						if (target == null) {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR,
									"coremodule.player.unkown");
							return true;
						} else {
							Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.INFO, "economy-module.commands.eco.balance.other", "_target", args[0], "_value", (String) (getMoney(uuid) + currencyFormat));
						}
						break;
					} else {
						Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.NOPERM, "coremodule.player.nopermission");
					}
				default:
					Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.ERROR, "coremodule.commands.arguments.invalid");
					break;
				}

			}
		}
		return true;
	}

	private String moneykey = "economy.money";

	private FileConfiguration getPlayerCfg(UUID uuid) {
		return plugin.getDataManager().getPlayerCfg(uuid);
		// return plugin.getPlayerManager().getPlayerData(uuid).getConfiguration();
	}

	private void setMoney(UUID uuid, double amount) {
		FileConfiguration cfg = getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			cfg.set(moneykey, amount);
		}
	}

	private void takeMoney(UUID uuid, double amount) {
		FileConfiguration cfg = getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money -= amount;
			cfg.set(moneykey, money);
		}
	}

	private void addMoney(UUID uuid, double amount) {
		FileConfiguration cfg = getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money += amount;
			cfg.set(moneykey, money);
		}
	}

	private double getMoney(UUID uuid) {
		FileConfiguration cfg = getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			/**
			 * int index = moneykey.indexOf("."); if (index == -1){ index =
			 * moneykey.length(); }else if(moneykey.length() - index >= 2){ index +=2; }
			 */ // kam von Kevin :*
			return cfg.getDouble(moneykey/** .substring(0, index) */);
		}
		return -1;
	}

	@Override
    public String Label() {
        return "money";
    }

    @Override
    public String[] Aliases() {
        return new String[] { "eco" };
    }

    @Override
    public boolean Enabled() {
        return true;
    }
}