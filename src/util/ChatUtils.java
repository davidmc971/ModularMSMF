package util;

public class ChatUtils {
	public enum MsgLevel {
		INFO, ERROR, MSG, BROADCAST, STANDARD, RAINBOW
	}
	
	public static String getFormattedPrefix(MsgLevel lvl) {
		switch(lvl) {
		case INFO:
			return "";
		case ERROR:
			return "";
		default:
			return "";
		}
	}
}
