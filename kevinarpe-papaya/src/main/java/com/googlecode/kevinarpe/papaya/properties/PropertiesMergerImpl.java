package com.googlecode.kevinarpe.papaya.properties;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
class PropertiesMergerImpl
implements PropertiesMerger {

    private final Logger _logger;

    PropertiesMergerImpl(Logger logger) {
        this._logger = ObjectArgs.checkNotNull(logger, "logger");
    }

    @Override
    public void merge(Map<String, String> map, List<JdkProperty> propertyList) {
        _logger.debug("Loaded %d properties", propertyList.size());
        final int size = propertyList.size();

        for (int index = 0; index < size; ++index) {
            JdkProperty property = propertyList.get(index);
            final String key = property.getKey();
            final String value = property.getValue();

            _logger.trace("\t[%d of %d]: '%s' -> '%s'", 1 + index, size, key, value);
            if (map.containsKey(key)) {
                String oldValue = map.get(key);
                if (oldValue.equals(value)) {
                    continue;
                }
                else {
                    _logger.trace("\t\tOverrides old value: '%s'", oldValue);
                }
            }

            map.put(key, value);
        }
    }
}
