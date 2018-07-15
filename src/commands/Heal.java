package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.Utils;

public class Heal {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {

		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		String permself = "modularmsmf.heal";
		String permothers = "modularmsmf.heal.others";

		UUID target = null;

		switch (args.length) {
		case 0:
			if (sender.hasPermission(permself)) {
				((Player) sender).setHealth(20);
				sender.sendMessage(language.getString("commands.heal.healself"));
			}
			break;
		default:
			target = Utils.getPlayerUUIDByName(args[0]);
			if (sender.hasPermission(permothers)) {
				if (target == null) {
					sender.sendMessage(language.getString("general.playernotfound"));
					return;
				} else
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
							Bukkit.getPlayer(target).setHealth(20);
							sender.sendMessage(
									language.getString("commands.heal.healother").replaceAll("_player", p.getName()));
							return;
						}
					}
				break;
			}
		}
	}
}