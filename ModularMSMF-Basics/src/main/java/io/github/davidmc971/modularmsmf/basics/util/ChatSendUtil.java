package io.github.davidmc971.modularmsmf.basics.util;

import io.github.davidmc971.modularmsmf.basics.util.ChatUtils.ChannelPrefix;

public class ChatSendUtil { //TODO: new module?
    public static String sendMessageWithConfiguredLanguage(String string, ChannelPrefix format) {
		String prefix = ChatUtils.getFormattedPrefix(format);
		String configuredMessage = prefix;
        return configuredMessage;
	}
}
