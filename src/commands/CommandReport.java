package commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import main.ModularMSMF;
import util.ChatUtils;
import util.Utils;

public class CommandReport {

	public static void cmd(CommandSender sender, Command cmd, String commandLabel, String[] args,
			ModularMSMF plugin) {

		String infoPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.INFO);
		String errorPrefix = ChatUtils.getFormattedPrefix(ChatUtils.ChatFormat.ERROR);
		UUID target = null;
		YamlConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		switch(commandLabel.toLowerCase()) {

		case "report":
			if(args.length == 0) {
				sender.sendMessage(infoPrefix+"Reporting-System for Cheaters and Bugs");
				sender.sendMessage(infoPrefix+"<Beschreibung ueber diesen CMD>");
			}
			if(args.length == 1) {
				sender.sendMessage(errorPrefix+"Please write down the correct report level and it's description!");
				sender.sendMessage(infoPrefix+"Level's you can use with permission:");
				if(sender.hasPermission("mmsmf.cheatrep")) {
					sender.sendMessage(infoPrefix+"/report player <Username>");
				}
				if(sender.hasPermission("mmsmf.bugrep")) {
					sender.sendMessage(infoPrefix+"/report bug <Describe your finding");
				}
				if(sender.hasPermission("mmsmf.otherrep")) {
					sender.sendMessage(infoPrefix+"/report others <Like any ideas?>");
				}
				// restliche Funktionen dazu ^^ wie report-lvl (bug = 1, cheater = 2, usw...) und beschreibung zum abrufen einer textdatei, auflisten der lvls als gruppe oder vollstaendige liste? david bitte <3
			} else if(args.length > 1) {
				switch (args[2].toLowerCase()) {
				case "player":
					if (args.length == 0){
						sender.sendMessage(infoPrefix+"Please write down the Username, which has to be reported!");
						if (args.length == 1){
							target = Utils.getPlayerUUIDByName(args[0]);
							if(target == null){
								sender.sendMessage(language.getString("general.playernotfound"));

							}
						}
					}
					break;

					/** Hilfeeeee
					 * @TODO PLZ HELP ME DAVID x.x
					 */

				default:
					sender.sendMessage(errorPrefix+"This command '" + ChatColor.YELLOW + args[0] + ChatColor.RED + "' doesn't exist!");
				}
			}
		}
	}
}
