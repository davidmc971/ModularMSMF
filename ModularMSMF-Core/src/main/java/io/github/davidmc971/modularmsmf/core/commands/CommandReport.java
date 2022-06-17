package io.github.davidmc971.modularmsmf.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.api.IModularMSMFCommand;
import io.github.davidmc971.modularmsmf.core.PermissionManager;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils.ChatFormat;
import io.github.davidmc971.modularmsmf.core.util.ChatUtils;
import io.github.davidmc971.modularmsmf.core.util.Utils;

public class CommandReport implements IModularMSMFCommand {

	// public static HashMap<Player, String[]/**Ansammlung von mehreren argumenten
	// in einem string? */> reportList = new HashMap<>(); //TODO: @david, kann man
	// das so lassen?

	/**
	 * The "/report" command. There are different categories of reports: player -
	 * report a player for different reasons bug - report a bug other - report what
	 * you think e.g. additions to the server // will be removed because official
	 * discord server. only working on an official server where devs work with us.
	 * The command structure of each category: /report player <playername> <reason>
	 * /report bug <description> /report other <description> // will be removed
	 * because official discord server. only working on an official server where
	 * devs work with us.
	 * 
	 * Individual report should be saved in a database of some kind and be
	 * accessible through an app and/or webinterface.
	 * 
	 * Reports can have permissions but will be sorted after the players rank,
	 * either op and normal user or the configured rank if available.
	 *
	 * @param sender       the CommandSender
	 * @param cmd          the Command
	 * @param commandLabel the label of the command, case-sensitive
	 * @param args         provided arguments without the label
	 * @param plugin       the parent plugin
	 * 
	 * @TODO using database for worldwide support ingame and webinterface
	 * @TODO using ingame chat as worldwide support ingame and webinterface
	 * 
	 * @author Lightkeks, davidmc971
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!PermissionManager.checkPermission(sender, "report_use")) {
			ChatUtils.sendMsgNoPerm(sender);
			return true;
		}

		FileConfiguration language = Utils.configureCommandLanguage(sender);

		if (args.length == 0) {
			reportHelp(sender);
			return true;
		}
		switch (args[0].toLowerCase()) {
			case "player":
				reportPlayer(sender, args, language);
				break;
			case "bug":
				reportBug(sender, args, language);
				break;
			case "other":
				reportOther(sender, args, language);
				break;
			default:
				Utils.sendMessageWithConfiguredLanguage(sender, ChatFormat.REPORT,
						"commands.report.help");
				break;
		}
		return true;
	}

	private void reportHelp(CommandSender sender) {

	}

	private void reportPlayer(CommandSender sender, String[] args, FileConfiguration language) {
		if (!PermissionManager.checkPermission(sender, "report_player")) {
			ChatUtils.sendMsgNoPerm(sender);
		}

	}

	private void reportBug(CommandSender sender, String[] args, FileConfiguration language) {
		if (!PermissionManager.checkPermission(sender, "report_bug")) {
			ChatUtils.sendMsgNoPerm(sender);
		}

	}

	private void reportOther(CommandSender sender, String[] args, FileConfiguration language) {
		if (!PermissionManager.checkPermission(sender, "report_other")) {
			ChatUtils.sendMsgNoPerm(sender);
		}

	}

	@Override
	public String Label() {
		return "report";
	}

	@Override
	public String[] Aliases() {
		return null;
	}

	@Override
	public boolean Enabled() {
		return true;
	}
}
