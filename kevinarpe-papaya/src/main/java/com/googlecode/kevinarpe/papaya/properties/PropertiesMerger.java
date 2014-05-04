package com.googlecode.kevinarpe.papaya.properties;

import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;

import java.util.List;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
interface PropertiesMerger {

    void merge(Map<String, String> map, List<JdkProperty> propertyList);
}
