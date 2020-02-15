package com.wangkai.mms.base;

import java.util.HashSet;
import java.util.Set;

public class BaseFuncation {
    //去重
    public static String [] deduplication (String [] array) {
        Set<String> set = new HashSet<>();
        for(int i=0;i<array.length;i++){
            set.add(array[i]);
        }
        return (String[]) set.toArray(new String[set.size()]);

    }
}
