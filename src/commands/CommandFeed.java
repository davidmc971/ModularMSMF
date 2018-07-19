package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import util.ChatUtils;
import util.PermissionManager;
import util.Utils;

public class CommandFeed extends AbstractCommand {

	public CommandFeed(ModularMSMF plugin) {
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
			if (sender.hasPermission(PermissionManager.getPermission("feedself"))) {
				sender.sendMessage(infoPrefix+language.getString("commands.feed.feeded"));
				((Player) sender).setSaturation(20);
				return true;
			} else {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				} 
			} else {
			sender.sendMessage(noPermPrefix+"You cannot feed the console");
			}
			break;
		default:
			target = Utils.getPlayerUUIDByName(args[0]);
			if (target == null) {
				sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
				return true;
			}
			if (!sender.hasPermission(PermissionManager.getPermission("feedothers"))) {
				sender.sendMessage(noPermPrefix+language.getString("general.nopermission"));
				return true;
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
					Bukkit.getPlayer(target).setSaturation(20);
					sender.sendMessage(infoPrefix+language.getString("commands.feed.feededperson").replaceAll("_player", p.getName()));
					p.sendMessage(infoPrefix+sender.getName() + " " + language.getString("commands.feed.othersfeeded"));
					return true;
				}
			}
			sender.sendMessage(noPermPrefix+language.getString("general.playernotfound"));
			break;
		}
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "feed";
	}
}