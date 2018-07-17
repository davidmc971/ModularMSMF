package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import main.ModularMSMF;
import net.md_5.bungee.api.ChatColor;
import util.ChatUtils;
import util.Utils;

public class ModularMSMFCommand {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {

		String toLowerCase = commandLabel.toLowerCase();
		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.ERROR);
		String noPermPrefix = ChatUtils.getFormattedPrefix(ChatUtils.MsgLevel.NOPERM);
		UUID target = null;
		
		switch (toLowerCase) {
		case "mmsmf":
			if (sender.hasPermission("mmsmf")) {
				if (args.length == 0) {
					sender.sendMessage(infoPrefix+"Plugin enabled on: " + Bukkit.getServerName());
					sender.sendMessage(infoPrefix+"More help:");
					sender.sendMessage(infoPrefix+"info || discord || report");
				} else if (args.length == 1) {
					switch (args[0].toLowerCase()) {
					case "info":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix+"Plugin Version: " + ChatColor.GREEN + plugin.pluginver);
							sender.sendMessage(infoPrefix+"Server's running at: " + ChatColor.YELLOW + Bukkit.getBukkitVersion());
							sender.sendMessage(infoPrefix+"Developer: " + ChatColor.LIGHT_PURPLE + plugin.authors);
							sender.sendMessage(errorPrefix+"Debug: Build [" + plugin.getDebugTimestamp() + "]");
						}
						break;
					case "discord":
						if (args.length == 1) {
							sender.sendMessage(infoPrefix+"Discord-URL: " + ChatColor.BLUE + "https://discord.gg/SxDQcJ6");
						}
						break;
					case "report":
						if(args.length == 1) {
							sender.sendMessage(infoPrefix+"Reporting-System for Cheaters and Bugs");
							sender.sendMessage(infoPrefix+"<Beschreibung über diesen CMD>");
						}
						if(args.length == 2) {
							sender.sendMessage(errorPrefix+"Please write down the correct report level and it's description!");
							sender.sendMessage(infoPrefix+"Level's you can use with permission:");
							if(sender.hasPermission("mmsmf.cheatrep")) {
								sender.sendMessage(infoPrefix+"/report cheater <Username>");
							}
							if(sender.hasPermission("mmsmf.bugrep")) {
								sender.sendMessage(infoPrefix+"/report bug <Describe your finding");
							}
							if(sender.hasPermission("mmsmf.otherreason")) {
								sender.sendMessage(infoPrefix+"/report others <Like any ideas?>");
							}
							// restliche Funktionen dazu ^^ wie report-lvl (bug = 1, cheater = 2, usw...) und beschreibung zum abrufen einer textdatei, auflisten der lvls als gruppe oder vollständige liste? david bitte <3
					} else if(args.length > 2) {
					 switch (args[2].toLowerCase()) {
					 	case "cheater":
					 		if (args.length == 0){
					 			sender.sendMessage(infoPrefix+"Please write down the Username, which has to be reported!");
					 		if (args.length == 1){
					 			target = Utils.getPlayerUUIDByName(args[0]);
				     				if(target == null){
					 					sender.sendMessage(language.getString("general.playernotfound"));
					 				
				     				}
				     				break;
				     				
				     				/** Hilfeeeee
				     				 * @TODO PLZ HELP ME DAVID x.x
				     				 */
					
						default:
						sender.sendMessage(errorPrefix+"This command '" + ChatColor.YELLOW + args[0] + ChatColor.RED + "' doesn't exist!");
					}
				} else if (args.length >= 2) {
					sender.sendMessage(errorPrefix+"Too many arguments!");
				} else {
					sender.sendMessage(noPermPrefix+"You don't have Permission to use this!!");
				}
			}
					}else {
				sender.sendMessage(noPermPrefix+"You don't have Permission to use this!");
			}
		}
	}
}
