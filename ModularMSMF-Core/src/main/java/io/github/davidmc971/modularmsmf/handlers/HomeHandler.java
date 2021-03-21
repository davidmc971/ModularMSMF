package io.github.davidmc971.modularmsmf.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.davidmc971.modularmsmf.core.PlayerManager;
import io.github.davidmc971.modularmsmf.data.PlayerData;
import io.github.davidmc971.modularmsmf.ModularMSMFCore;

public class HomeHandler {
	/*	Class representing a players home.
	 */
	public class Home {
		Location loc;
		String name;
		
		public Location getLoc() {
			return loc;
		}

		public void setLoc(Location loc) {
			this.loc = loc;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Home(Location loc, String name) {
			this.loc = loc;
			this.name = name;
		}
	}
	
	private class SaveTask extends BukkitRunnable {
		HomeHandler handler;
		
		public SaveTask(HomeHandler handler) {
			this.handler = handler;
		}
		
		@Override
		public void run() {
			handler.saveAll();
		}
		
	}

	ModularMSMFCore plugin;
	PlayerManager plrm;
	
	private HashMap<UUID, ArrayList<Home>> homeMap;
	
	public HomeHandler(ModularMSMFCore plugin) {
		this.plugin = plugin;
		plrm = plugin.getPlayerManager();
		plrm.registerSaveTask(new SaveTask(this));
		
		homeMap = new HashMap<UUID, ArrayList<Home>>();
		
		plrm.getPlayerStorage().forEach((uuid, data) -> {
			//TODO: fix home list
			// if (data != null && data.homes != null) {
			// 	ArrayList<Home> homeList = new ArrayList<Home>();
			// 	for (Home h : data.homes) {
			// 		homeList.add(h);
			// 	}
			// 	homeMap.put(uuid, homeList);
			// }
		});
	}
	
	private void saveAll() {
		homeMap.forEach((uuid, homes) -> {
			PlayerData data = plrm.getPlayerData(uuid);
			if (data == null) {
				//TODO: data = new PlayerData(uuid);
			}
			
			Home[] temp = new Home[homes.size()];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = homes.get(i);
			}
			//TODO: data.homes = temp;
		});
	}

	/*	Method for getting a named home from a player whose
	 * 	UUID is given. If name equals null, default home is
	 * 	returned (if existing). If home was not found,
	 * 	the return value is null.
	 * 
	 * 	@param		uuid	UUID of the player whose home is to be returned.
	 * 	@param		name	name of the home which is to be returned. If name
	 * 						equals null, it is replaced with "default".
	 * 	@return 			Home object if it was found, null if search failed.
	 */
	public Home getPlayerHome(UUID uuid, String name) {
		if (name == null) {
			name = "default";
		}
		
		for (Entry<UUID, ArrayList<Home>> e : homeMap.entrySet()) {
			if (e.getKey().compareTo(uuid) == 0) {
				for (Home h : e.getValue()) {
					if (h.getName().equalsIgnoreCase(name)) {
						return h;
					}
				}
			}
		}
		
		return null;
	}
	
	/*	Sets a home if arguments are valid.
	 * 	
	 * 	@param		uuid	UUID of the player whose home is to be set.
	 * 	@param		name	name of the home which is to be set. If name
	 * 						equals null, it is replaced with "default".
	 * 	@return 			true if successful, false otherwise.
	 */
	public boolean setPlayerHome(UUID uuid, String name, Location loc) {
		if (name == null) {
			name = "default";
		}
		
		if (!homeMap.containsKey(uuid)) {
			homeMap.put(uuid, new ArrayList<Home>());
		}
		
		ArrayList<Home> homes = homeMap.get(uuid);
		for (Home h : homes) {
			if (h.getName().equalsIgnoreCase(name)) {
				h.setLoc(loc);
				return true;
			}
		}
		homes.add(new Home(loc, name));
		return true;
	}
	
	/*	Removes a home if present.
	 * 	
	 * 	@param		uuid	UUID of the player whose home is to be removed.
	 * 	@param		name	name of the home which is to be removed. If name
	 * 						equals null, it is replaced with "default".
	 * 	@return 			true if successful, false if player does not have that home.
	 */
	public boolean removePlayerHome(UUID uuid, String name) {
		if (name == null) {
			name = "default";
		}
		
		if (!homeMap.containsKey(uuid)) {
			return false;
		}
		
		ArrayList<Home> homes = homeMap.get(uuid);
		for (Home h : homes) {
			if (h.getName().equalsIgnoreCase(name)) {
				homes.remove(h);
				return true;
			}
		}
		
		return false;
	}
	
	/*	Returns a copy of the list of a players homes if present.
	 * 	
	 * 	@param		uuid	UUID of the player whose homes are to be returned.
	 * 	@return 			the list of this players homes, or null if none is present.
	 */
	public ArrayList<Home> getAllPlayerHomes(UUID uuid) {
		if (!homeMap.containsKey(uuid)) {
			return null;
		}
		
		return new ArrayList<Home>(homeMap.get(uuid));
	}
	
}
