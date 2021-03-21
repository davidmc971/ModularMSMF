package io.github.davidmc971.modularmsmf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.ModularMSMF;
import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.util.Utils;

public class CommandReport implements IModularMSMFCommand {

	//public static HashMap<Player, String[]/**Ansammlung von mehreren argumenten in einem string? */> reportList = new HashMap<>(); //TODO: @david, kann man das so lassen?
	
	private ModularMSMF plugin;

    public CommandReport() {
        plugin = ModularMSMF.Instance();
    }

	/**
	 * The "/report" command.
	 * There are different categories of reports:
	 * 	player - report a player for different reasons
	 * 	bug - report a bug
	 * 	other - report what you think e.g. additions to the server // will be removed because official discord server. only working on an official server where devs work with us.
	 * The command structure of each category:
	 * 	/report player <playername> <reason>
	 * 	/report bug <description>
	 * 	/report other <description> // will be removed because official discord server. only working on an official server where devs work with us.
	 * 
	 * Individual report should be saved in a database of some kind
	 * and be accessible through an app and/or webinterface.
	 * 
	 * Reports can have permissions but will be sorted after the
	 * players rank, either op and normal user or the configured rank
	 * if available.
	 *
	 * @param sender the CommandSender
	 * @param cmd the Command
	 * @param commandLabel the label of the command, case-sensitive
	 * @param args provided arguments without the label
	 * @param plugin the parent plugin
	 * 
	 * @TODO using database for worldwide support ingame and webinterface
	 * @TODO using ingame chat as worldwide support ingame and webinterface
	 * 
	 * @author Lightkeks, davidmc971
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration language = Utils.configureCommandLanguage(sender, plugin);

		if(args.length == 0){
		//no arguments, plain /report command
			//TODO: send description of command, in player's language
			//sender.sendMessage(infoPrefix + "Report system for reporting players, bugs and other stuff.");
			//sender.sendMessage(infoPrefix + "Level's you are allowed to use:");
			if(PermissionManager.checkPermission(sender, "report_player")) {
			//	sender.sendMessage(infoPrefix + "/report player <playername> <reason>");
			}
			if(PermissionManager.checkPermission(sender, "report_bug")) {
			//	sender.sendMessage(infoPrefix + "/report bug <description of finding>");
			}
			if(PermissionManager.checkPermission(sender, "report_other")) {
			//	sender.sendMessage(infoPrefix + "/report other <describe your idea>");
			}
		} else {
		//at least one argument
			switch(args[0].toLowerCase()){
			//let's check for the category and if it's valid, as well as permission for use
			case "player":
				if(PermissionManager.checkPermission(sender, "report_player"))
				{
					reportPlayer(sender, args, plugin, language);
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.REPORT, "general.nopermission");
				}
				break;
			case "bug":
				if(PermissionManager.checkPermission(sender, "report_bug"))
				{
					reportBug(sender, args, plugin, language);
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.REPORT, "general.nopermission");
				}
				break;
			case "other":
				if(PermissionManager.checkPermission(sender, "report_other"))
				{
					reportOther(sender, args, plugin, language);
				} else {
					Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.REPORT, "general.nopermission");
				}
				break;
			default:
				//non valid category
				//DONE: send error and prompt user to use /report for description
				Utils.sendMessageWithConfiguredLanguage(plugin, sender, ChatFormat.REPORT, "report.help");
				break;
			}
		}
		return true;
	}

	private static void reportPlayer(CommandSender sender, String[] args, ModularMSMF plugin, FileConfiguration language) {
		//TODO: incomplete
		
	}

	private static void reportBug(CommandSender sender, String[] args, ModularMSMF plugin, FileConfiguration language) {
		//TODO: incomplete
	}

	private static void reportOther(CommandSender sender, String[] args, ModularMSMF plugin, FileConfiguration language) {
		//TODO: incomplete
	}

	@Override
	public String[] getCommandLabels() {
		return new String[]{ "report" };
	}
}

