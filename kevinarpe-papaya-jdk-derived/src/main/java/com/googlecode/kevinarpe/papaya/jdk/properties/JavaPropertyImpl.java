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

import java.util.AbstractMap;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class JavaPropertyImpl
extends AbstractMap.SimpleImmutableEntry<String, String>
implements JavaProperty {

    public JavaPropertyImpl(String key, String value) {
        super(_checkNotNull(key, "key"), _checkNotNull(value, "value"));
    }

    private static String _checkNotNull(String value, String argName) {
        if (null == value) {
            throw new NullPointerException(String.format("Argument '%s' is null", argName));
        }
        return value;
    }

    @Override
    public String toString() {
        String x = String.format("'%s'='%s'", getKey(), getValue());
        return x;
    }
}
