package io.github.davidmc971.modularmsmf.basics.util;

import java.util.Collection;
import java.util.HashMap;

public class ToggleCommandsConfig {
    public static final HashMap<String, Boolean> togglecmds = new HashMap<String, Boolean>() {
        private static final long serialVersionUID = 1L;
        {
            put("test", null);
        };
    };
    public Collection<Boolean> listCommands(String list){
        return togglecmds.values();
    }
}