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

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.HashMap;
import java.util.List;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
final class PropertiesLoaderPolicyImpl
extends StatelessObject
implements PropertiesLoaderPolicy {

    public static final PropertiesLoaderPolicyImpl INSTANCE =
        new PropertiesLoaderPolicyImpl();

    // TODO: Dense method.  Can we split up?
    @Override
    public void apply(List<JdkProperty> propertyList)
    throws PropertiesLoaderException {
        HashMap<String, String> map = Maps.newHashMap();
        List<String> errorList = Lists.newArrayList();
        final int size = propertyList.size();
        for (int index = 0; index < size; ++index) {
            JdkProperty property = propertyList.get(index);
            final String key = property.getKey();
            final String value = property.getValue();
            if (map.containsKey(key)) {
                if (Objects.equal(value, map.get(key))) {
                    errorList.add(
                        String.format("[%d of %d] Duplicate key-value pair: '%s' -> '%s'",
                            1 + index, size, key, value));
                }
                else {
                    errorList.add(
                        String.format("[%d of %d] Duplicate key: '%s' -> '%s' and '%s'",
                            1 + index, size, key, map.get(key), value));
                }
            }
        }
        if (!errorList.isEmpty()) {
            String msg = Joiner.on(StringUtils.NEW_LINE).join(errorList);
            throw new PropertiesLoaderException(msg);
        }
    }
}
