package io.github.davidmc971.modularmsmf.basics.util;

import java.util.Set;

public class SortList {

    public static String printSet(Set<String> set) {
        String result = new String();
        for (String str : set) {
            result = result.concat(str.concat(", "));
        }
        if (result.isBlank()) {
            return result;
        }
        return result.substring(0, result.length() - 2);
    }
}
