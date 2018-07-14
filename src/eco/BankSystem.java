package eco;

import org.bukkit.configuration.file.FileConfiguration;

import main.ModularMSMF;
import util.DataManager;

public class BankSystem {

	//private MultiPluxPlugin plugin;
	private FileConfiguration cfg_bank;

	private double setting_bankCreateCost;

	public BankSystem(ModularMSMF info) {
		cfg_bank = DataManager.loadCfg("bankConfig.yml"); // ggf config f�r banken
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
