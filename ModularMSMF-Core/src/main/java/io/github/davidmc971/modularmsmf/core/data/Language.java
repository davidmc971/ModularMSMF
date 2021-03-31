package io.github.davidmc971.modularmsmf.core.data;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.api.ILanguage;

/**	@author David Alexander Pfeiffer (davidmc971)
 * 	@since v0.2
 *	This should be an object to contain a certain language.
 * 	Every player should have a Language attached.
 * 	Should provide easy localization.
 * 	To avoid unnecessary usage of space, there will be one object per Language
 * 	and every PlayerConfiguration points to the configured Language object.
 */

public class Language implements ILanguage {
	private FileConfiguration languageConfiguration;
	public FileConfiguration getLanguageConfiguration() { return languageConfiguration; }
	public void setFileConfiguration(FileConfiguration cfg) { this.languageConfiguration = cfg; }

	public Language(FileConfiguration cfg) { this.languageConfiguration = cfg; }

	public String getID() { return languageConfiguration.getString("language.id"); }
	public void setID(String id) { languageConfiguration.set("language.id", id); }

	public String getName() { return languageConfiguration.getString("language.name"); }
	public void setName(String name) { languageConfiguration.set("language.name", name); }

	@Override
	public String ID() {
		return getID();
	}
	@Override
	public String Name() {
		return getName();
	}
	@Override
	public String Localize(String localizationKey) {
		if (languageConfiguration.contains(localizationKey)) {
			return languageConfiguration.getString(localizationKey);
		}
		return null;
	}
}
