package io.github.davidmc971.modularmsmf.basics.handlers;

import java.util.UUID;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;

/**
 * @author David Alexander Pfeiffer (davidmc971)
 *         This class will be a manager object to help CommandBan interface with
 *         the DataManager.
 *         It isolates CommandBan from handling the bans so it is easier to work
 *         on them separately.
 *
 *
 */

public class BanHandler {
	public enum STDBanReason {
		DEFAULT("banreason.default"), // FIXME: add string to lang file to remove null
		HACKING("banreason.hacking"), // FIXME: add string to lang file to remove null
		VERBAL("banreason.verbal"), // FIXME: add string to lang file to remove null
		GRIEFING("banreason.griefing"); // FIXME: add string to lang file to remove null

		private String reason;

		STDBanReason(String reason) {
			this.reason = reason;
		}

		public String getReason() {
			return reason;
		}
	}

	ModularMSMFCore plugin;

	public BanHandler(ModularMSMFCore plugin) {
		this.plugin = plugin;
	}

	public boolean banPlayer(UUID playerid) {
		return banPlayer(playerid, STDBanReason.DEFAULT);
	}

	public boolean banPlayer(UUID playerid, STDBanReason reason) {
		// convert reason into localized string
		return banPlayer(playerid, reason.getReason());
	}

	public boolean banPlayer(UUID playerid, String reason) {
		// DataManager dtm = plugin.getDataManager();
		// LanguageManager lgm = plugin.getLanguageManager();

		return true;
	}
}
