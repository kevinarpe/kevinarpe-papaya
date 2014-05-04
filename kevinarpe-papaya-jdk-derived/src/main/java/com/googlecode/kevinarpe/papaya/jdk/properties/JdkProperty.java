package com.googlecode.kevinarpe.papaya.jdk.properties;

/*
 * #%L
 * This file is part of Papaya (JDK Derived Classes).
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya (JDK Derived Classes) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only as
 * published by the Free Software Foundation.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in the LICENSE
 * file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Special notice for this module of Papaya:
 * Classes in this module may contain significant portions that are originally
 * part of the JDK source base.  In such cases, prominent notices appear before
 * these blocks of source code.
 * #L%
 */

import java.util.Arrays;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JdkProperty {

    private String[] _keyValueArr;

    public JdkProperty(String key, String value) {
        _keyValueArr = new String[] { key, value };
    }

    public String getKey() {
        return _keyValueArr[0];
    }

    public void setKey(String key) {
        _keyValueArr[0] = key;
    }

    public String getValue() {
        return _keyValueArr[1];
    }

    public void setValue(String value) {
        _keyValueArr[1] = value;
    }

    @Override
    public int hashCode() {
        int x = Arrays.hashCode(_keyValueArr);
        return x;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof JdkProperty) {
            final JdkProperty other = (JdkProperty) obj;
            result = Arrays.equals(_keyValueArr, other._keyValueArr);
        }
        return result;
    }

    @Override
    public String toString() {
        String x = String.format("Key: '%s', Value: '%s'", _keyValueArr[0], _keyValueArr[1]);
        return x;
    }
}
