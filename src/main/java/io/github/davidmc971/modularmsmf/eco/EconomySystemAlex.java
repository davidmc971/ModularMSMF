package io.github.davidmc971.modularmsmf.eco;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.commands.AbstractCommand;
import io.github.davidmc971.modularmsmf.util.ChatUtils;
import io.github.davidmc971.modularmsmf.util.Utils;

/**
 * 
 * @author Lightkeks
 * with help from @author davidmc971
 */

public class EconomySystemAlex extends AbstractCommand {
	private ModularMSMF plugin;
	public static double defaultMoney = 500.0;
	
	public EconomySystemAlex(ModularMSMF plugin) {
		super(plugin);
		this.plugin = plugin;
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{"eco"};
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

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
		switch (commandLabel.toLowerCase()) {
		case "eco":
			if (args.length == 0) {
				if((sender instanceof Player)) {
				sender.sendMessage(infoPrefix+language.getString("commands.eco.ecobalance") + getMoney(uuid) + "$");
				} else {
					sender.sendMessage(errorPrefix+language.getString("commands.eco.ecoconsole"));
				}
			} else if (args.length > 0) {
				switch (args[0].toLowerCase()) {
				case "help":
					if (args.length > 1) {
						//sender.sendMessage("[Eco] Bitte nur '/eco help'!");
					} else {
						/**
						 * TODO Rewrite into changeable language without using lang-yml
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
					if (sender.hasPermission(PermissionManager.getPermission("eco_set"))) {
						switch (args.length) {
						default:
							if (args.length < 2) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.missingamountandplayer")); //need to change language string
								return true;
							} else if (args.length > 3) {
								sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
								return true;
							}
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
								return true;
							}
						case 2:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
								return true;
							}
							//sender.sendMessage("[Eco] Dein Kontostand vorher: " + getMoney(uuid) + "$"); //need to change language string
							setMoney(uuid, amount);
							//sender.sendMessage("[Eco] Dein Kontostand danach: " + getMoney(uuid) + "$"); //need to change language string
							break;
						case 3:
							try {
								amount = Integer.parseInt(args[2]);
							} catch (NumberFormatException e) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
								return true;
							}
							//sender.sendMessage("[Eco] Du hast von " + args[1] + " das Geldkonto neu gesetzt."); //need to change language string
							plugin.getLogger().info(infoPrefix + target); //testing purposes only
							setMoney(target, amount);
							//sender.sendMessage("[Eco] Neuer Kontostand: " + getMoney(target) + "$"); //need to change language string
							//Bukkit.getPlayer(target).sendMessage("[Eco] Dein Konto wurde neu gesetzt! Neuer Betrag: " + getMoney(target) + "$"); //need to change language string
							break;
						}
					} else {
						sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
					}
					break;
				case "add":
					if (sender.hasPermission(PermissionManager.getPermission("eco_add"))) {
						switch (args.length) {
						default:
							if (args.length < 2) {
								//sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!");
								return true;
							} else if (args.length > 3) {
								sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
								return true;
							}
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
								return true;
							}
						case 2:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
								return true;
							}
							if (amount <= 0) {
								sender.sendMessage(errorPrefix+language.getString("commands.eco.ecoforbiddenminus"));
							} else {
								//sender.sendMessage("[Eco] Du hast nun " + amount + "$ zu deinem Konto hinzugefügt!"); //need to change language string
								addMoney(uuid, amount);
							}
							break;
						case 3:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
								return true;
							}
							addMoney(target, amount);
							//sender.sendMessage("[Eco] Du hast " + target + " " + amount + "$ hinzugefügt!"); //need to change language string
							//Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$ gutgeschrieben. Neuer Stand: " + getMoney(target) + "$"); //need to change language string
							break;
						}
					} else {
						sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
					}
					break;
				case "take":
					if (sender.hasPermission(PermissionManager.getPermission("eco_take"))) {
						if (args.length < 2) {
							//sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!"); //need to change language string
						} else if (args.length == 2) {
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.missingamount")); //need to change language string
								return true;
							}
							if (amount <= 0) {
								sender.sendMessage(errorPrefix+language.getString("commands.money.invalidpayment")); //need to change language string
							} else {
								//sender.sendMessage("[Eco] Du hast nun " + amount + "$ von deinem Konto weggenommen!"); //need to change language string
								takeMoney(uuid, amount);
							}
						} else if (args.length == 3) {
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(errorPrefix+language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
								return true;
							}
							//sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$ weggenommen!"); //need to change language string
							takeMoney(target, amount);
							//Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount + "$ weggenommen. Neuer Stand: " + getMoney(target) + "$"); //need to change language string
						} else {
							sender.sendMessage(errorPrefix+language.getString("general.toomanyarguments"));
						}
					} else {
						sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
					}
					break;
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
							sender.sendMessage(infoPrefix+language.getString("commands.eco.ecobalanceother")+args[0]+": "+getMoney(target)+"$"); //testing
							sender.sendMessage(infoPrefix+language.getString("commands.eco.ecobalanceother")+": "+getMoney(target)+"$"); //testing
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
		return false;
	}

	private String moneykey = "economyalex.money";

	private void setMoney(UUID uuid, double amount) {
		YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			cfg.set(moneykey, amount);
		}
	}

	private void takeMoney(UUID uuid, double amount) {
		YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money -= amount;
			cfg.set(moneykey, money);
		}
	}

	private void addMoney(UUID uuid, double amount) {
		YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money += amount;
			cfg.set(moneykey, money);
		}
	}

	private double getMoney(UUID uuid) {
		YamlConfiguration cfg = plugin.getDataManager().getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			return cfg.getDouble(moneykey);
		}
		return -1;
	}

}