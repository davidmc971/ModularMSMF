package io.github.davidmc971.modularmsmf.eco;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.commands.AbstractCommand;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * TODO: Mainclass for economysystem <3
 * @author Lightkeks
 * with help from @author davidmc971
 */

public class EconomySystem extends AbstractCommand {
	private ModularMSMF plugin;
	public static double defaultMoney = 500.0;
	
	public EconomySystem(ModularMSMF plugin) {
		super(plugin);
		this.plugin = plugin;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{"eco","money"};
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);

		UUID uuid = null;
		UUID target = null;
		double amount = 0;
		if (sender instanceof Player) {
			uuid = ((Player) sender).getUniqueId();
		}
		String currencyFormat = "$";
		//TODO: something like language.getCurrencyFormat() which loads from settings.yml

		//checks contained string above in getCommandLabels()
		if(!Arrays.asList(getCommandLabels()).contains(commandLabel.toLowerCase())) return false;

		if (args.length == 0) {
			if((sender instanceof Player)) {
			sender.sendMessage(infoPrefix+language.getString("commands.eco.balance.self").replace("_value", (String)(getMoney(uuid) + currencyFormat)));
			} else {
				sender.sendMessage(errorPrefix+language.getString("commands.eco.error.console"));
			}
		} else if (args.length > 0) {
			switch (args[0].toLowerCase()) {
			case "help":
				if (args.length > 1) {
					//sender.sendMessage("[Eco] Bitte nur '/eco help'!");
				} else {
					/**
					 * TODO: Rewrite into changeable language without using lang-yml
					 */
					//sender.sendMessage("[Eco] Alle Befehle:");
					//sender.sendMessage("[Eco] /eco help <- Diese Hilfe");
					//sender.sendMessage("[Eco] /eco set [Name] [Amount] <- Geld setzen");
					//sender.sendMessage("[Eco] /eco [Name] <- Anzeigen des Geldes vom Spieler");
					//sender.sendMessage("[Eco] /eco add [Name] [Amount] <- Geld hinzufügen");
					//sender.sendMessage("[Eco] /eco take [Name] [Amount] <- Geld von Spieler nehmen");
					//sender.sendMessage("[Eco] /eco pay [Name] [Amount] <- Geld an Spieler überweisen");
					return true;
				}
				break;
			case "set":
			case "add":
			case "take":
				//Argument checking
				if (args.length < 2) {
					sender.sendMessage(errorPrefix+language.getString("commands.eco.error.syntax")); //need to change language string
					return true;
				} else if (args.length > 3) {
					sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
					return true;
				}
				
				boolean toSelf = (args.length == 2);

				if (!(sender instanceof Player) && toSelf) {
					sender.sendMessage(errorPrefix+language.getString("commands.eco.error.console"));
					break;
				}

				if(!toSelf) {
					target = Utils.getPlayerUUIDByName(args[1]);
					if(target == uuid && (sender instanceof Player)) toSelf = true;
				}

				String action = args[0];
				
				//Permission checking
				//TODO: handle ".other" differently
				String permissionStringInternal = "eco_" + action;
				if (plugin.debug) plugin.getLogger().info("Checking permStrInt [" + permissionStringInternal + "]");
				String permissionStringExternal = PermissionManager.getPermission(permissionStringInternal) + (toSelf ? "" : ".other");
				if (plugin.debug) plugin.getLogger().info("Checking permStrExt [" + permissionStringExternal + "]");
				if (!sender.hasPermission(permissionStringExternal)) {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
					break;
				}

				if(!toSelf) {
					if (target == null) {
						sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
						return true;
					} else {
						Player p = Bukkit.getPlayer(target);
						if (p == null || !p.isOnline()) {
							//Do we want an online check here?
							sender.sendMessage(errorPrefix+language.getString("general.playernotonline"));
							return true;
						}
					}
				}

				//value is always last arg, we can save a few lines of code here
				//TODO: discuss whether we want to check if it's a negative number or not
				try {
					amount = Double.parseDouble(args[args.length - 1]);
				} catch (Exception e) {
					sender.sendMessage(errorPrefix+language.getString("commands.eco.error.syntax")); //need to change language string
					return true;
				}
				
				UUID temp = (toSelf ? uuid : target);
				double before = getMoney(temp);

				//perform action
				if (action.equals("set")) setMoney(temp, amount);
				else if (action.equals("add")) addMoney(temp, amount);
				else if (action.equals("take")) takeMoney(temp, amount);

				sender.sendMessage(
					(toSelf ?
					language.getString("commands.eco.set.self.full")
					:
					language.getString("commands.eco.set.other.full")
					.replace("_target", args[1]))
					.replace("_value_old", before + currencyFormat)
					.replace("_value_new", getMoney(temp) + currencyFormat)
				);
				//notify other player if !toSelf
				if(!toSelf) Bukkit.getPlayer(target).sendMessage(
					language.getString("commands.eco.set.notify")
					.replace("_sender", sender.getName())
					.replace("_value_old", before + currencyFormat)
					.replace("_value_new", getMoney(temp) + currencyFormat)
				);
				break;
			// case "add":
			// 	if (sender.hasPermission(PermissionManager.getPermission("eco_add"))) {
			// 		switch (args.length) {
			// 		default:
			// 			if (args.length < 2) {
			// 				//sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!");
			// 				return true;
			// 			} else if (args.length > 3) {
			// 				sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			// 				return true;
			// 			}
			// 			target = Utils.getPlayerUUIDByName(args[1]);
			// 			if (target == null) {
			// 				sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
			// 				return true;
			// 			} else if (!Bukkit.getPlayer(target).isOnline()) {
			// 				sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
			// 				return true;
			// 			}
			// 		case 2:
			// 			try {
			// 				amount = Integer.parseInt(args[1]);
			// 			} catch (NumberFormatException e) {
			// 				sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
			// 				return true;
			// 			}
			// 			if (amount <= 0) {
			// 				sender.sendMessage(errorPrefix+language.getString("commands.eco.ecoforbiddenminus"));
			// 			} else {
			// 				//sender.sendMessage("[Eco] Du hast nun " + amount + "$ zu deinem Konto hinzugefügt!"); //need to change language string
			// 				addMoney(uuid, amount);
			// 			}
			// 			break;
			// 		case 3:
			// 			try {
			// 				amount = Integer.parseInt(args[1]);
			// 			} catch (NumberFormatException e) {
			// 				sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
			// 				return true;
			// 			}
			// 			addMoney(target, amount);
			// 			//sender.sendMessage("[Eco] Du hast " + target + " " + amount + "$ hinzugefügt!"); //need to change language string
			// 			//Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$ gutgeschrieben. Neuer Stand: " + getMoney(target) + "$"); //need to change language string
			// 			break;
			// 		}
			// 	} else {
			// 		sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			// 	}
			// 	break;
			// case "take":
			// 	if (sender.hasPermission(PermissionManager.getPermission("eco_take"))) {
			// 		if (args.length < 2) {
			// 			//sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!"); //need to change language string
			// 		} else if (args.length == 2) {
			// 			try {
			// 				amount = Integer.parseInt(args[1]);
			// 			} catch (NumberFormatException e) {
			// 				sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
			// 				return true;
			// 			}
			// 			if (amount <= 0) {
			// 				sender.sendMessage(errorPrefix+language.getString("commands.money.invalidpayment")); //need to change language string
			// 			} else {
			// 				//sender.sendMessage("[Eco] Du hast nun " + amount + "$ von deinem Konto weggenommen!"); //need to change language string
			// 				takeMoney(uuid, amount);
			// 			}
			// 		} else if (args.length == 3) {
			// 			target = Utils.getPlayerUUIDByName(args[1]);
			// 			if (target == null) {
			// 				sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
			// 				return true;
			// 			} else if (!Bukkit.getPlayer(target).isOnline()) {
			// 				sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
			// 				return true;
			// 			}
			// 			//sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$ weggenommen!"); //need to change language string
			// 			takeMoney(target, amount);
			// 			//Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$ weggenommen. Neuer Stand: " + getMoney(target) + "$"); //need to change language string
			// 		} else {
			// 			sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
			// 		}
			// 	} else {
			// 		sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			// 	}
			// 	break;
			case "pay":
				if (sender.hasPermission(PermissionManager.getPermission("eco_pay"))) {
					if (args.length < 2) {
						//sender.sendMessage("[Eco] Zu wenige Argumente!"); //need to change language string
					}
					if (args.length == 2) {
						sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
					}
					if (args.length == 3) {
						target = Utils.getPlayerUUIDByName(args[1]);
						if (target == null) {
							sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
							return true;
						} else if (!Bukkit.getPlayer(target).isOnline()) {
							sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
							return true;
						}
						try {
							amount = Integer.parseInt(args[2]);
						} catch (NumberFormatException e) {
							sender.sendMessage(errorPrefix+language.getString("commands.money.amountonly")); //need to change language string
							return true;
						}
						if (amount <= 0) {
							sender.sendMessage(errorPrefix+language.getString("commands.money.invalidpayment")); //need to change language string
						} else if (getMoney(uuid) - amount < 0) {
							//sender.sendMessage("[Eco] Du würdest dich verschulden!"); //need to change language string
						} else {
							takeMoney(uuid, amount);
							addMoney(target, amount);
							//sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$ überwiesen!"); //need to change language string
							//Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$ von " + ((Player) sender).getName() + " überwiesen. Neuer Stand: " + getMoney(target) + "$"); //need to change language string
						}
					} else if (args.length >= 3) {
						sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
					}
				} else {
					sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				}
				break;
			default:
				switch (args.length) {
				case 1:
					if (sender.hasPermission(PermissionManager.getPermission("eco_lookup"))) {
						target = Utils.getPlayerUUIDByName(args[0]);
						if (target == null) {
							sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
							return true;
						} else {
						sender.sendMessage(infoPrefix+language.getString("commands.eco.balance.other")
							.replace("_target", args[0])
							.replace("_value", getMoney(target) + currencyFormat));
						}
						break;
					} else {
						sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
					}
				default:
					sender.sendMessage(errorPrefix+language.getString("general.invalidarguments"));
					break;
				}

			}
		}
		return true;
	}

	private String moneykey = "economyalex.money";

	private void setMoney(UUID uuid, double amount) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			cfg.set(moneykey, amount);
		}
	}

	private void takeMoney(UUID uuid, double amount) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money -= amount;
			cfg.set(moneykey, money);
		}
	}

	private void addMoney(UUID uuid, double amount) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money += amount;
			cfg.set(moneykey, money);
		}
	}

	private double getMoney(UUID uuid) {
		FileConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg != null && cfg.isDouble(moneykey)) {
			return cfg.getDouble(moneykey);
		}
		return -1;
	}

}