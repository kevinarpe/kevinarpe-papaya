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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.HashMap;
import java.util.List;

/**
 * Checks a group of properties for correctness.
 * <ul>
 *     <li>There must be no duplicate key-value pairs.
 *     <br/>Example: {@code ("abc" -> "def")} and {@code ("abc" -> "def")}</li>
 *     <li>There must be no duplicate keys.
 *     <br/>Example: {@code ("abc" -> "def")} and {@code ("abc" -> "ghi")}</li>
 * </ul>
 * <p>
 * This is the default policy for instances of {@link PropertiesLoader} created with
 * {@link PropertiesLoaderUtils#newInstance()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PropertiesLoaderUtils#DEFAULT_POLICY
 * @see #INSTANCE
 * @see StatelessObject
 * @see PropertiesLoaderPolicy
 */
@FullyTested
public final class DefaultPropertiesLoaderPolicy
extends StatelessObject
implements PropertiesLoaderPolicy {

    public static final DefaultPropertiesLoaderPolicy INSTANCE =
        new DefaultPropertiesLoaderPolicy();

    static final String DUP_KEY_VALUE_PAIR_MSG = "Duplicate key-value pair";
    static final String DUP_KEY_MSG = "Duplicate key";

    @Override
    public void apply(List<JdkProperty> propertyList)
    throws PropertiesLoaderException {
        ObjectArgs.checkNotNull(propertyList, "propertyList");

        HashMap<String, String> map = Maps.newHashMap();
        List<String> errorList = Lists.newArrayList();
        final int size = propertyList.size();

        for (int index = 0; index < size; ++index) {
            JdkProperty property = propertyList.get(index);
            _checkKeyAndValue(map, errorList, size, index, property.getKey(), property.getValue());
        }

        if (!errorList.isEmpty()) {
            String msg = Joiner.on(StringUtils.NEW_LINE).join(errorList);
            throw new PropertiesLoaderException(msg);
        }
    }

    private void _checkKeyAndValue(
            HashMap<String, String> map,
            List<String> errorList,
            int size,
            int index,
            String key,
            String value) {
        if (map.containsKey(key)) {
            if (Objects.equal(value, map.get(key))) {
                errorList.add(
                    String.format("[%d of %d] %s: '%s' -> '%s'",
                        1 + index, size, DUP_KEY_VALUE_PAIR_MSG, key, value));
            }
            else {
                errorList.add(
                    String.format("[%d of %d] %s: '%s' -> '%s' and '%s'",
                        1 + index, size, DUP_KEY_MSG, key, map.get(key), value));
            }
        }
        else {
            map.put(key, value);
        }
    }
}
