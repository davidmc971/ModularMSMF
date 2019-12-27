package io.github.davidmc971.modularmsmf.data;

import org.bukkit.configuration.file.FileConfiguration;

/**	@author davidmc971
 * 	@since v0.2
 *	This should be an object to contain a certain language.
 * 	Every player should have a Language attached.
 * 	Should provide easy localization.
 * 	To avoid unnecessary usage of space, there will be one object per Language
 * 	and every PlayerConfiguration points to the configured Language object.
 */

public class Language {
	private FileConfiguration languageConfiguration;
	public FileConfiguration getLanguageConfiguration() { return languageConfiguration; }
	public void setFileConfiguration(FileConfiguration cfg) { this.languageConfiguration = cfg; }

	public Language(FileConfiguration cfg) { this.languageConfiguration = cfg; }

	public String getID() { return languageConfiguration.getString("language.id"); }
	public void setID(String id) { languageConfiguration.set("language.id", id); }

	public String getName() { return languageConfiguration.getString("language.name"); }
	public void setName(String name) { languageConfiguration.set("language.name", name); }
}
