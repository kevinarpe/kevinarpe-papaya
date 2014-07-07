package com.googlecode.kevinarpe.papaya.properties;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class PropertiesMergerImpl
implements PropertiesMerger {

    private final Logger _logger;

    PropertiesMergerImpl(Logger logger) {
        this._logger = ObjectArgs.checkNotNull(logger, "logger");
    }

    @Override
    public void merge(Map<? super String, ? super String> map, List<JdkProperty> propertyList) {
        _logger.debug("Loaded %d properties", propertyList.size());
        final int size = propertyList.size();
        int index = 0;
        for (Iterator<JdkProperty> iter = propertyList.iterator(); iter.hasNext(); ++index) {
            final JdkProperty property = iter.next();
            final String key = property.getKey();
            final String value = property.getValue();

            _logger.trace("\t[%d of %d]: '%s' -> '%s'", 1 + index, size, key, value);
            if (map.containsKey(key)) {
                Object oldValue = map.get(key);
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
