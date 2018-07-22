package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import core.PermissionManager;
import main.ModularMSMF;
import util.ChatUtils;
import util.Utils;

public class CommandHeal extends AbstractCommand {

	public CommandHeal(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.NOPERM);

		UUID target = null;
		switch (args.length) {
		case 0:
			if((sender instanceof Player)) {
				//checking if command sender is player instead of console
			if (sender.hasPermission(PermissionManager.getPermission("healself"))) {
				//checking, if user has permission to use /heal
				((Player) sender).setHealth(20); //full heal for sender
				sender.sendMessage(infoPrefix+language.getString("commands.heal.healself"));
				//using ChatUtils and YamlConfiguration for easier messages
			} else {
				//if no permission was given, it will negate the if phrase
				sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
			}
			}else {
				//if console should not be permitted to use a command, this comes out
				sender.sendMessage(noPermPrefix+language.getString("general.noconsole"));
			}
			break;
		default:
			target = Utils.getPlayerUUIDByName(args[0]);
			//trying to find out the UUID by player name
			if (sender.hasPermission(PermissionManager.getPermission("healother"))) {
				//check if user has permission to heal others
				if (target == null) {
					//check if user exists
					sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
					return true; //return's true if target's not online
				} else //return's true otherwise if target's online
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
							Bukkit.getPlayer(target).setHealth(20); //full heal for target
							sender.sendMessage(infoPrefix+language.getString("commands.heal.healother").replaceAll("_player", p.getName()));
							p.sendMessage(infoPrefix+language.getString("commands.heal.gothealed").replaceAll("_sender", sender.getName()));
							return true;
						}
					}
				break;
			}
		}
		return true;
	}

	@Override
	public String[] getCommandLabels() { //only using for AbstractCommand
		return new String[]{ "heal" };
	}
}