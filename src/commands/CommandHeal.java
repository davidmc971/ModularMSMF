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
			if (sender.hasPermission(PermissionManager.getPermission("healself"))) {
				((Player) sender).setHealth(20);
				sender.sendMessage(infoPrefix+language.getString("commands.heal.healself"));
			} else {
				sender.sendMessage(noPermPrefix+"general.nopermission");
			}
			}else {
				sender.sendMessage(noPermPrefix+"general.noconsole");
			}
			break;
		default:
			target = Utils.getPlayerUUIDByName(args[0]);
			if (sender.hasPermission(PermissionManager.getPermission("healother"))) {
				if (target == null) {
					sender.sendMessage(errorPrefix+language.getString("general.playernotfound"));
					return true;
				} else
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getUniqueId().toString().equalsIgnoreCase(target.toString())) {
							Bukkit.getPlayer(target).setHealth(20);
							sender.sendMessage(infoPrefix+language.getString("commands.heal.healother").replaceAll("_player", p.getName()));
							p.sendMessage(infoPrefix+"You have been healed by "+sender.getName());
							return true;
						}
					}
				break;
			}
		}
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "heal";
	}
}