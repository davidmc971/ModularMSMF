package io.github.davidmc971.modularmsmf.basics.util;

import java.util.ArrayList;

public class SortList {

    public static String printSet(ArrayList<String> arrayList) {
        String result = new String();
        for (String str : arrayList) {
            result = result.concat(str.concat(", "));
        }
        if (result.isBlank()) {
            return result;
        }
        return result.substring(0, result.length() - 2);
    }
}
