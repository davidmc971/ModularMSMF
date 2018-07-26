package data;

import org.json.simple.JSONArray;

/*	This should be an object to contain a certain language.
 * 	Every player should have a Language attached.
 * 	Should provide easy localization.
 * 	To avoid unnecessary usage of space, there will be one object per Language
 * 	and every PlayerConfiguration points to the configured Language object. 
 */

/**
 * 
 * @author davidmc971
 *
 */

public class Language {
	private String name;
	private JSONArray root;

	public Language(String name, JSONArray root) {
		this.name = name;
		this.root = root;
	}

	public String getName() {
		return name;
	}
	
	public JSONArray getRoot() {
		return root;
	}
}
