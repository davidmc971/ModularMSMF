package io.github.davidmc971.modularmsmf.handlers;

import java.util.UUID;

import io.github.davidmc971.modularmsmf.ModularMSMF;

/*	@author davidmc971
 * 	
 * 	This class will be a manager object to help CommandBan interface with the DataManager.
 * 	It isolates CommandBan from handling the bans so it is easier to work on them separately.
 * 
 * 
 */

/**
 * 
 * @author davidmc971
 *
 */

public class BanHandler {
	public enum STDBanReason {
		DEFAULT ("modularmsmf.commands.ban.reason.default"),
		HACKING ("modularmsmf.commands.ban.reason.hacking"),
		VERBAL ("modularmsmf.commands.ban.reason.verbal"),
		GRIEFING ("modularmsmf.commands.ban.reason.griefing");

		private String reason;
		
		STDBanReason(String reason){
			this.reason = reason;
		}

		public String getReason() {
			return reason;
		}
	}
	
	ModularMSMF plugin;
	
	public BanHandler(ModularMSMF plugin) {
		this.plugin = plugin;
	}
	
	public boolean banPlayer(UUID playerid) {
		return banPlayer(playerid, STDBanReason.DEFAULT);
	}
	
	public boolean banPlayer(UUID playerid, STDBanReason reason) {
		//convert reason into localized string
		return banPlayer(playerid, reason.getReason());
	}
	
	public boolean banPlayer(UUID playerid, String reason) {
		//DataManager dtm = plugin.getDataManager();
		//LanguageManager lgm = plugin.getLanguageManager();
		
		return true;
	}
}
