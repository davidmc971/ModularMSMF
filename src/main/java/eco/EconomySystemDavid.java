package eco;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.Utils;

/**
 * 
 * @author davidmc971
 *
 */

public class EconomySystemDavid {
	private ModularMSMF plugin;

	public EconomySystemDavid(ModularMSMF plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unused")
	public boolean cmd(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		String lowercaseLabel = commandLabel.toLowerCase();

		if (!(sender instanceof Player)) { sender.sendMessage("Die Konsole hat kein Geld. :D"); return true; }

		if (lowercaseLabel.equals("money")) {
			if (args.length == 0) {
				// sender.sendMessage("�6[�7Multi�8Plux�6]�9 Dein Geld:
				// "+getPlayerdata(sender.getName()).getMoney()+"�.");
				sender.sendMessage(
						"§6[§7Multi§8Plux§6]§9 Dein Geld: " + ""/* TODO */ + "€.");
			} else if (args.length > 0) {
				switch (args[0].toLowerCase()) {
				case "set": // money set [name] [amount]
					if (args.length == 2) {
						try {
							//TODO
						} catch (NumberFormatException e) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du musst eine Zahl angeben.");
							return true;
						}
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dein Kontostand wurde auf " + args[1] + "€ gesetzt.");
					} else if (args.length == 3) {
						if (true /* TODO */) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dieses Konto existiert nicht.");
							return true;
						}
						try {
							//TODO
						} catch (NumberFormatException e) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du musst eine Zahl angeben.");
							return true;
						}
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Der Kontostand von " + args[1] + " wurde auf "
								+ args[2] + "€ gesetzt.");
					}
					break;
				case "add": // money add [name] [amount]
					if (args.length == 2) {
						try {
							//TODO
						} catch (NumberFormatException e) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du musst eine Zahl angeben.");
							return true;
						}
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Deinem Kontostand wurden " + args[1]
								+ "€ gutgeschrieben. Dieser beträgt nun " + "" //TODO
								+ "€.");
					} else if (args.length == 3) {
						if (true /* TODO */) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dieses Konto existiert nicht.");
							return true;
						}
						try {
							//TODO
						} catch (NumberFormatException e) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du musst eine Zahl angeben.");
							return true;
						}
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dem Kontostand von " + args[1] + " wurden " + args[2]
								+ "€ gutgeschrieben. Dieser betr�gt nun " + "" /* TODO */ + "€.");
					}
					break;
				case "pay": // money pay [name] [amount]
					if (args.length == 3) {
						double amount;
						try {
							amount = Double.parseDouble(args[2]);
						} catch (NumberFormatException e) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du musst eine Zahl angeben.");
							return true;
						}
						/* TODO if (!playerExists(args[1])) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dieser Spieler existiert nicht.");
							return true;
						}*/
						if (amount <= 0) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du kannst nur positive Betr�ge �berweisen.");
							return true;
						}
						/* TODO if (!getPlayerdata(args[1]).isOnline()) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dieser Spieler ist offline.");
							return true;
						}*/
						/* TODO if(getPlayerdata(sender.getName()).getMoney() < amount ) {
							sender.sendMessage("§6[§7Multi§8Plux§6]§9 Du würdest dich verschulden.");
							return true;
						}*/
						// TODO getPlayerdata(sender.getName()).addMoney(-amount);
						// TODO getPlayerdata(args[1]).addMoney(amount);
						sender.sendMessage(
								"§6[§7Multi§8Plux§6]§9 Du hast " + args[1] + " " + args[2] + "€ überwiesen.");
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dein Kontostand beträgt nun "
								+ "" /* TODO getPlayerdata(sender.getName()).getMoney() */ + "€.");
						Player p = Utils.getPlayerByName(args[1]);
						if (p != null) {
							p.sendMessage(new String[] {
									"§6[§7Multi§8Plux§6]§9 Dir wurden von " + sender.getName() + " " + args[2]
											+ "€ überwiesen.",
									"§6[§7Multi§8Plux§6]§9 Dein Kontostand beträgt nun "
											+ "" /* TODO getPlayerdata(args[1]).getMoney() */ + "€." });
						} else {
							// TODO: send mail
						}
					}
					break;
				case "account": // money account [args]

					break;
				case "admin_clear_playerdata":
					if (plugin.debug) {
						//TODO
						sender.sendMessage("TODO: clear and reload EconomySystem.");
						sender.sendMessage("EconomySystem cleared and reloaded.");
						break;
					} else {
						sender.sendMessage("Debug mode must be enabled.");
					}
				default:
					/* TODO Playerdata temp = getPlayerdata(args[0]);
					if (temp != null) {
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Geld von " + args[0] + ": " + temp.getMoney() + "€.");
					} else {
						sender.sendMessage("§6[§7Multi§8Plux§6]§9 Dieses Konto existiert nicht.");
					}*/
				}
			}
			return true;
		}
		return false;
	}
}
