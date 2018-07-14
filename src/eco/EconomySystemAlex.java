package eco;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.DataManager;
import util.PermissionsHandler;
import util.Utils;

public class EconomySystemAlex {
	public static double defaultMoney = 500.0;
	
	public EconomySystemAlex(ModularMSMF plugin) {
	}

	public boolean cmd(CommandSender sender, Command cmd, String commandLabel, String[] args, ModularMSMF plugin) {

		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		UUID uuid = null;
		UUID target = null;
		double amount = 0;
		if (sender instanceof Player) {
			uuid = ((Player) sender).getUniqueId();
		}
		switch (commandLabel.toLowerCase()) {
		case "eco":
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("Konsole nix Geld.");
					return true;
				}
				sender.sendMessage("[Eco] Dein derzeitiger Kontostand: " + getMoney(uuid) + "$");
			} else if (args.length > 0) {
				switch (args[0].toLowerCase()) {
				case "help":
					if (args.length > 2) {
						sender.sendMessage("[Eco] Bitte nur '/eco help'!");
					} else {
						sender.sendMessage("[Eco] Alle Befehle:");
						sender.sendMessage("[Eco] /eco help <- Diese Hilfe");
						sender.sendMessage("[Eco] /eco set [Name] [Amount] <- Geld setzen");
						sender.sendMessage("[Eco] /eco [Name] <- Anzeigen des Geldes vom Spieler");
						sender.sendMessage("[Eco] /eco add [Name] [Amount] <- Geld hinzufügen");
						sender.sendMessage("[Eco] /eco take [Name] [Amount] <- Geld von Spieler nehmen");
						sender.sendMessage("[Eco] /eco pay [Name] [Amount] <- Geld an Spieler überweisen");
						return true;
					}
					break;
				case "set":
					if (sender.hasPermission(PermissionsHandler.getPermission("eco_set"))) {
						switch (args.length) {
						default:
							if (args.length < 2) {
								sender.sendMessage(language.getString("commands.money.mssingamountandplayer"));
								return true;
							} else if (args.length > 3) {
								sender.sendMessage(language.getString("general.toomanyarguments"));
								return true;
							}
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(language.getString("general.playernotfound"));
								return true;
							}
						case 2:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(language.getString("commands.money.missingamount"));
								return true;
							}
							sender.sendMessage("[Eco] Dein Kontostand vorher: " + getMoney(uuid) + "$");
							setMoney(uuid, amount);
							sender.sendMessage("[Eco] Dein Kontostand danach: " + getMoney(uuid) + "$");
							break;
						case 3:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(language.getString("commands.money.missingamount"));
								return true;
							}
							sender.sendMessage("[Eco] Du hast von " + args[1] + " das Geldkonto neu gesetzt.");
							setMoney(target, amount);
							sender.sendMessage("[Eco] Neuer Kontostand: " + getMoney(target) + "$");
							Bukkit.getPlayer(target).sendMessage(
									"[Eco] Dein Konto wurde neu gesetzt! Neuer Betrag: " + getMoney(target) + "$");
							break;
						}
					} else {
						sender.sendMessage(language.getString("general.nopermission"));
					}
					break;
				case "add":
					if (sender.hasPermission(PermissionsHandler.getPermission("eco_add"))) {
						switch (args.length) {
						default:
							if (args.length < 2) {
								sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!");
								return true;
							} else if (args.length > 3) {
								sender.sendMessage(language.getString("general.toomanyarguments"));
								return true;
							}
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(language.getString("general.playernotfound"));
								return true;
							}
						case 2:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(language.getString("commands.money.missingamount"));
								return true;
							}
							if (amount <= 0) {
								sender.sendMessage("Du darfst keine Minusbeträge angeben!");
							} else {
								sender.sendMessage("[Eco] Du hast nun " + amount + "$ zu deinem Konto hinzugefügt!");
								addMoney(uuid, amount);
							}
							break;
						case 3:
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(language.getString("commands.money.missingamount"));
								return true;
							}
							addMoney(target, amount);
							sender.sendMessage("[Eco] Du hast " + target + " " + amount + "$ hinzugefügt!");
							Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount
									+ "$ gutgeschrieben. Neuer Stand: " + getMoney(target) + "$");
							break;
						}
					}
					break;
				case "take":
					if (sender.hasPermission(PermissionsHandler.getPermission("eco_take"))) {
						if (args.length < 2) {
							sender.sendMessage("[Eco] Bitte gib einen Spielernamen sowie Betrag an!");
						} else if (args.length == 2) {
							try {
								amount = Integer.parseInt(args[1]);
							} catch (NumberFormatException e) {
								sender.sendMessage(language.getString("commands.money.missingamount"));
								return true;
							}
							if (amount <= 0) {
								sender.sendMessage(language.getString("commands.money.invalidpayment"));
							} else {
								sender.sendMessage("[Eco] Du hast nun " + amount + "$ von deinem Konto weggenommen!");
								takeMoney(uuid, amount);
							}
						} else if (args.length == 3) {
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(language.getString("general.playernotfound"));
								return true;
							}
							sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$ weggenommen!");
							takeMoney(target, amount);
							Bukkit.getPlayer(target).sendMessage("[Eco] Dir wurden " + amount
									+ "$ weggenommen. Neuer Stand: " + getMoney(target) + "$");
						} else {
							sender.sendMessage(language.getString("general.toomanyarguments"));
						}
					}
					break;
				case "pay":
					if (sender.hasPermission(PermissionsHandler.getPermission("eco_pay"))) {
						if (args.length < 2) {
							sender.sendMessage("[Eco] Zu wenige Argumente!");
						}
						if (args.length == 2) {
							sender.sendMessage(language.getString("commands.money.missingamount"));
						}
						if (args.length == 3) {
							target = Utils.getPlayerUUIDByName(args[1]);
							if (target == null) {
								sender.sendMessage(language.getString("general.playerunknown"));
								return true;
							} else if (!Bukkit.getPlayer(target).isOnline()) {
								sender.sendMessage(language.getString("general.playernotfound"));
								return true;
							}
							try {
								amount = Integer.parseInt(args[2]);
							} catch (NumberFormatException e) {
								sender.sendMessage(language.getString("commands.money.amountonly"));
								return true;
							}
							if (amount <= 0) {
								sender.sendMessage(language.getString("commands.money.invalidpayment"));
							} else if (getMoney(uuid) - amount < 0) {
								sender.sendMessage("[Eco] Du würdest dich verschulden!");
							} else {
								takeMoney(uuid, amount);
								addMoney(target, amount);
								sender.sendMessage("[Eco] Du hast " + args[1] + " " + amount + "$ überwiesen!");
								Bukkit.getPlayer(target).sendMessage(
										"[Eco] Dir wurden " + amount + "$ von " + ((Player) sender).getName()
												+ " überwiesen. Neuer Stand: " + getMoney(target) + "$");
							}
						} else if (args.length >= 3) {
							sender.sendMessage(language.getString("general.toomanyarguments"));
						}
					}
					break;
				default:
					switch (args.length) {
					case 1:
						if (sender.hasPermission(PermissionsHandler.getPermission("eco_lookup"))) {
							target = Utils.getPlayerUUIDByName(args[0]);
							if (target == null) {
								sender.sendMessage(language.getString("general.playerunknown"));
								return true;
							}
							sender.sendMessage("Geld von " + args[0] + ": " + getMoney(target));
							break;
						}
					default:
						sender.sendMessage(language.getString("general.invalidarguments"));
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
		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			cfg.set(moneykey, amount);
		}
	}

	private void takeMoney(UUID uuid, double amount) {
		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money -= amount;
			cfg.set(moneykey, money);
		}
	}

	private void addMoney(UUID uuid, double amount) {
		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			double money = cfg.getDouble(moneykey);
			money += amount;
			cfg.set(moneykey, money);
		}
	}

	private double getMoney(UUID uuid) {
		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
		if (cfg.isDouble(moneykey)) {
			return cfg.getDouble(moneykey);
		}
		return -1;
	}

}