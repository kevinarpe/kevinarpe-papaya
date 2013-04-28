package com.googlecode.kevinarpe.papaya;

import java.util.HashMap;

public final class MapUtil {
    
    public static <TKey, TValue> HashMap<TKey, TValue> asHashMap(
            Object... keyAndValueArr) {
        if (1 == (keyAndValueArr.length % 2)) {
            throw new IllegalArgumentException(String.format(
                "Key-Value array length is not even: %d", keyAndValueArr.length));
        }
        int capacity = keyAndValueArr.length / 2;
        HashMap<TKey, TValue> map = new HashMap<TKey, TValue>(capacity);
        for (int i = 0; i < keyAndValueArr.length; i += 2) {
            Object key = keyAndValueArr[i];
            Object value = keyAndValueArr[1 + i];
            TKey castKey = null;
            try {
                castKey = (TKey) key;
            }
            catch (Exception e) {
                String msg = String.format("Failed to cast key #%d (one-based): [%s]",
                    1 + (i / 2), key);
                throw new IllegalArgumentException(msg, e);
            }
            TValue castValue = null;
            try {
                castValue = (TValue) value;
            }
            catch (Exception e) {
                String msg = String.format("Failed to cast value #%d (one-based): [%s]",
                    1 + (i / 2), value);
                throw new IllegalArgumentException(msg, e);
            }
            map.put(castKey, castValue);
        }
        return map;
    }
}
