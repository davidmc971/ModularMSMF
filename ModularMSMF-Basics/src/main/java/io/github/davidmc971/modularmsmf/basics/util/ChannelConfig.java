package io.github.davidmc971.modularmsmf.basics.util;

import java.util.HashSet;

/**
 * @author Lightkeks
 * ANCHOR code needed for config
 * TODO[epic=code needed,seq=20] maybe not the right way to make a config like this
 */
public class ChannelConfig {
    public static final HashSet<String> channels = new HashSet<String>() {
        private static final long serialVersionUID = 1L;
        {
            // only for main channels to obtain
            add("admin"); /* /channel set admin */
            add("moderator"); /* /channel set moderator */
        }
    };
}
