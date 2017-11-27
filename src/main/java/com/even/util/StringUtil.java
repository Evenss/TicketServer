package com.even.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static List<String> listUtil(String arrayStr) {
        List<String> list = new ArrayList<String>();

        arrayStr.replace("[", "");
        arrayStr.replace("]", "");

        String[] array = arrayStr.split(",");
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }
}
