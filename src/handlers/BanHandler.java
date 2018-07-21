package handlers;

import java.util.UUID;

import main.ModularMSMF;

/*	@author davidmc971
 * 	
 * 	This class will be a manager object to help CommandBan interface with the DataManager.
 * 	It isolates CommandBan from handling the bans so it is easier to work on them separately.
 * 
 * 
 */

public class BanHandler {
	public enum STDBanReasons {
		DEFAULT ("modularmsmf.commands.ban.reason.default"),
		HACKING ("modularmsmf.commands.ban.reason.default"),
		VERBAL ("modularmsmf.commands.ban.reason.default"),
		GRIEFING ("modularmsmf.commands.ban.reason.default");

		private String reason;
		
		STDBanReasons(String reason){
			this.reason = reason;
		}

		public String getReason() {
			return reason;
		}
		
		/*public String getLocalizedReason(Language language) {
			
			return reason;
		}*/
	}
	
	ModularMSMF plugin;
	
	public BanHandler(ModularMSMF plugin) {
		this.plugin = plugin;
	}
	
	public boolean banPlayer(UUID playerid) {
		return banPlayer(playerid, null);
	}
	
	public boolean banPlayer(UUID playerid, String reason) {
		//get players language for localizing reason
		
		return true;
	}
}
