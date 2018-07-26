package defaults;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;

public class PlayerDefaults {
	private Map<String, Object> defaults;
	public PlayerDefaults() {
		 defaults = new HashMap<String, Object>();
		 defaults.put("money", 500);
		 defaults.put("online", false);
		 JsonArray stuff = new JsonArray();
		 stuff.add(1);
		 stuff.add(2);
		 stuff.add(3);
		 stuff.add(4);
		 defaults.put("stuff", stuff);
	}
	public Map<String, Object> getDefaults(){
		return defaults;
	}
}
