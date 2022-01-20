package io.github.davidmc971.modularmsmf.basics.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class SortList {

    public static String printHashMapKeysWOValues(HashMap<String, String> channeltreeMap) {
        String result = new String();
        for (Entry<String, String> entry : channeltreeMap.entrySet()) {
            for (String key : channeltreeMap.keySet()) {
                result = result.concat(key.concat(", "));
            }
            // use getKey method to get a key from entry
            System.out.println("Key: " + entry.getKey());

            // use getValue method to get a value from entry
            System.out.println("Value: " + entry.getValue());
        }

        if (result.isBlank()) {
            return result;
        }
        return result.substring(0, result.length() - 2);
    }

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
