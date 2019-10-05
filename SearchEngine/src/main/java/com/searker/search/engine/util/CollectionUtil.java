package com.searker.search.engine.util;

import java.util.Arrays;
import java.util.Map;

public abstract class CollectionUtil {

    public static <K> void reduce(Map<K, ?> reducer, Map<K, ?> data) {
        for (K k : data.keySet()) {
            reducer.remove(k);
        }
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] union(K[] a, K[] b) {
        K[] result = (K[]) Arrays.copyOf(a, a.length + b.length, a.getClass());
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}
