package handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import core.PlayerManager;
import main.ModularMSMF;

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

	ModularMSMF plugin;
	PlayerManager plrm;
	Gson gson;
	private HashMap<UUID, ArrayList<Home>> homeMap;
	
	public HomeHandler(ModularMSMF plugin) {
		this.plugin = plugin;
		plrm = plugin.getPlayerManager();
		gson = new Gson();
		plrm.registerSaveTask(new SaveTask(this));
		//init homeMap from storage and
		//load all entries from config
		homeMap = new HashMap<UUID, ArrayList<Home>>();
		
		Map<UUID, JsonObject> storage = plrm.getPlayerStorage();
		storage.forEach((uuid, json) -> {
			if (json.has("homes")) {
				ArrayList<Home> homeList = gson.fromJson(json.get("homes"), new TypeToken<List<Home>>(){}.getType());
				homeMap.put(uuid, homeList);
			}
		});
	}
	
	private void saveAll() {
		
		homeMap.forEach((uuid, homes) -> {
			JsonElement temp = gson.toJsonTree(homes, new TypeToken<List<Home>>(){}.getType());
			JsonObject data = plrm.getPlayerStorage(uuid);
			if(data.has("homes")) {
				data.remove("homes");
			}
			data.add("homes", temp);
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
