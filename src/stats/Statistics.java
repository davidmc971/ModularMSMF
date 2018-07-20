package stats;

import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import util.DataManager;

public class Statistics {

	private static String kickkey = "kickcounter";

	public static double getKickCounter(UUID uuid) {
		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
		if (cfg.isDouble(kickkey)) {
			return cfg.getDouble(kickkey);
		}
		return 0;
	}
// TODO Lets rewrite it.
//	private void setKickCounter(UUID uuid) {
//		YamlConfiguration cfg = DataManager.getPlayerCfg(uuid);
//		if (cfg.isDouble(kickkey)) {
//			double count = cfg.getDouble(kickkey);
//			count += 1;
//			cfg.set(kickkey, uuid);
//		}
//	}

	@SuppressWarnings("unused")
	private void getIpAdress(UUID uuid) {

	}

	@SuppressWarnings("unused")
	private void saveIpAdress(UUID uuid) {

	}

}
