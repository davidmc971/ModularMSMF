package util;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.bukkit.configuration.file.YamlConfiguration;

public class YamlManager {
//	public static String pathMain = "plugins/ModularMSMF/";
//	public static String pathUserdata = pathMain + "userdata/";
//	public static String pathBankdata = pathMain + "bankdata/";
//
//	public static YamlConfiguration load(String path) {
//		File file = new File(path);
//		if (!file.exists()) {
//			try {
//				file.getParentFile().mkdirs();
//				file.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return YamlConfiguration.loadConfiguration(file);
//	}
//
//	public static YamlConfiguration getPlayerCfg(String uuid) {
//		File file = new File(pathUserdata + uuid + ".yml");
//		if (!file.exists()) {
//			try {
//				file.getParentFile().mkdirs();
//				file.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return YamlConfiguration.loadConfiguration(file);
//	}
//
//	public static YamlConfiguration getBankCfg(String bankname) {
//		File file = new File(pathBankdata + bankname + ".yml");
//		if (!file.exists()) {
//			try {
//				file.getParentFile().mkdirs();
//				file.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return YamlConfiguration.loadConfiguration(file);
//	}
//
//	public static void savePlayer(YamlConfiguration playercfg, String playername) {
//		File file = new File(pathUserdata + playername + ".yml");
//		try {
//			playercfg.save(file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
