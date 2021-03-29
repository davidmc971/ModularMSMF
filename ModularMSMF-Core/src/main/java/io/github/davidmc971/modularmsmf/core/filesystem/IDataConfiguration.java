package io.github.davidmc971.modularmsmf.core.filesystem;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

/*	Should serve as standard for implementing different file storage formats.
 * 	Methods for loading and saving files, as well as fields should be there.
 * 
 * 	Not yet sure if it will be needed. Might get removed later!
 */

/**
 * 
 * @author davidmc971
 *
 */

public class IDataConfiguration extends FileConfiguration {

	@Override
	protected String buildHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromString(String contents) throws InvalidConfigurationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String saveToString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
