package io.github.davidmc971.modularmsmf.basics.util;

import java.util.ArrayList;
import java.util.Set;

public class SortList {

    public static String printSet(Set<String> usrchpriv) {
        String result = new String();
        for (String str : usrchpriv) {
            result = result.concat(str.concat(", "));
        }
        if (result.isBlank()) {
            return result;
        }
        return result.substring(0, result.length() - 2);
    }
    public static String printAList(ArrayList<String> usrchpriv) {
        String result = new String();
        for (String str : usrchpriv) {
            result = result.concat(str.concat(", "));
        }
        if (result.isBlank()) {
            return result;
        }
        return result.substring(0, result.length() - 2);
    }
}
