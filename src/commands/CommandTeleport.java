package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.ModularMSMF;
import net.md_5.bungee.api.ChatColor;

public class CommandTeleport extends AbstractCommand {
	
	public CommandTeleport(ModularMSMF plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//Player player = null;
		if (!(sender instanceof Player)) {
			//TODO: console should be able to tp players to other players or waypoints
			sender.sendMessage(ChatColor.RED + "[ModularMSMFPlugin] No use for console. Player only.");
		}

		if (sender.hasPermission("modularmsmf.teleport")) {
			if (args.length == 0) {
				sender.sendMessage("[ModularMSMF] Du musst jemanden angeben!");
			}
			if (args.length == 1) {
				String Name = args[0];
				if (Bukkit.getPlayerExact(Name) != null) {
					Player target = (Player) Bukkit.getPlayerExact(Name);
					((Player) sender).teleport(target);
					sender.sendMessage("[ModularMSMF] Erfolgreich zu " + target.getDisplayName() + " teleportiert!");
				} else {
					sender.sendMessage("[ModularMSMF] " + args[0] + " ist nicht online!");
				}
			} else if (args.length >= 2) {
				sender.sendMessage("[ModularMSMF] Zu viele Argumente!");
			}
		}
		return true;
	}

	@Override
	public String getCommandLabel() {
		return "teleport";
	}
}
