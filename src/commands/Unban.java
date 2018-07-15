package commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import main.ModularMSMF;
import util.DataManager;
import util.Utils;

public class Unban {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args, ModularMSMF plugin) {
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if (!sender.hasPermission("modularmsmf.unban")) {
			sender.sendMessage(language.getString("general.nopermission"));
			return;
		}
		
		switch(args.length){
		case 0:
			sender.sendMessage(language.getString("general.missing_playername"));
			break;
		case 1:
			for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
				if(p.getName().equalsIgnoreCase(args[0])){
					YamlConfiguration cfg = DataManager.getPlayerCfg(p.getUniqueId());
					if(cfg.getBoolean("banned")){
						cfg.set("banned", false);
						cfg.set("reason", "none");
						sender.sendMessage(language.getString("commands.unban.playerunbanned").replaceAll("_player", args[0]));
					} else {
						sender.sendMessage(language.getString("commands.unban.notbanned"));
					}
					return;
				}
			}
			sender.sendMessage(language.getString("general.playernotfound"));
			break;
		case 2:
			if(args[0].toLowerCase().equals("uuid")){
				for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
					if(p.getUniqueId().toString().equals(args[1])){
						YamlConfiguration cfg = DataManager.getPlayerCfg(p.getUniqueId());
						if(cfg.getBoolean("banned")){
							cfg.set("banned", false);
							cfg.set("reason", "none");
							sender.sendMessage(language.getString("commands.unban.unbanuuid").replaceAll("_player", p.getName()));
						} else {
							sender.sendMessage(language.getString("commands.unban.notbanned"));
						}
						return;
					}
				}
				sender.sendMessage(language.getString("commands.unban.playernotfounduuid"));
			} else {
				sender.sendMessage(language.getString("commands.unban.invalidcommand"));
			}
			break;
		default:
			sender.sendMessage(language.getString("general.toomanyarguments"));
			break;
		}
	}
}
