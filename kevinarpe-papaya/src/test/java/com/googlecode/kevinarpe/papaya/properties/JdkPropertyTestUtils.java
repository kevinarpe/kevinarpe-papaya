package com.googlecode.kevinarpe.papaya.properties;

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JdkPropertyTestUtils {

    public static List<JdkProperty> asPropertyList(String... argArr) {
        List<JdkProperty> list = Lists.newArrayListWithCapacity(argArr.length);
        for (int i = 0; i < argArr.length; i += 2) {
            JdkProperty property = new JdkProperty(argArr[i], argArr[1 + i]);
            list.add(property);
        }
        return list;
    }
}
