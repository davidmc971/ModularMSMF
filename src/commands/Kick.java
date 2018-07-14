package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import main.ModularMSMF;
import util.PermissionsHandler;
import util.Utils;

public class Kick {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args, ModularMSMF plugin) {

		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		UUID target = null;
		String reason = language.getString("commands.kick.defaultkickreason");

		switch(args.length){
		case 0:
			if(sender.hasPermission(PermissionsHandler.getPermission("kickplayer"))){
				sender.sendMessage(language.getString("general.missing_playername"));
			} else {
				sender.sendMessage(language.getString("general.nopermissions"));
			}
			break;
		default:
			if(sender.hasPermission(PermissionsHandler.getPermission("kickplayer"))){
				target = Utils.getPlayerUUIDByName(args[0]);
				if(target == null){
					sender.sendMessage(language.getString("general.playernotfound"));
				} else {
					if(args.length == 1){
						Bukkit.getPlayer(target).kickPlayer(reason);
						Bukkit.broadcastMessage(language.getString("commands.kick.seekickedall").replaceAll("_player", args[0]));
					} else {
						reason = "";
						for (int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						Bukkit.getPlayer(target).kickPlayer(reason);
						Bukkit.broadcastMessage(language.getString("commands.kick.seekickedallreason").replaceAll("_reason", reason).replaceAll("_player", args[0]));
					}
				}
			} else {
				sender.sendMessage(language.getString("general.nopermissions"));
			}
		}
	}
}