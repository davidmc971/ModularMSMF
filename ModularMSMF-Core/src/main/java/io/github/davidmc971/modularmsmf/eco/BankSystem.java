package io.github.davidmc971.modularmsmf.eco;

import org.bukkit.configuration.file.FileConfiguration;

import io.github.davidmc971.modularmsmf.core.ModularMSMFCore;

/**
 * 
 * @author Lightkeks
 * TODO: Changes for currency changing and tax/rent maybe?
 */

public class BankSystem {

	private ModularMSMFCore plugin;
	private FileConfiguration cfg_bank;

	private double setting_bankCreateCost;

	public BankSystem(ModularMSMFCore plugin) {
		this.plugin = plugin;
		cfg_bank = this.plugin.getDataManager().loadCfg("bankConfig.yml"); // ggf config f�r banken
		init();
	}

	public void init() {
		if (cfg_bank.isDouble("bankCreateCost")) {
			setting_bankCreateCost = (double) cfg_bank.get("bankCreateCost");
		} else {
			setting_bankCreateCost = 1000;
			cfg_bank.set("bankCreateCost", setting_bankCreateCost);
		}
	}

}
