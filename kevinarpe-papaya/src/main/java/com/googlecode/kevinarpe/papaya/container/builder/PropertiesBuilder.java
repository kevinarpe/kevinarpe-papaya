package com.googlecode.kevinarpe.papaya.container.builder;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Builds {@code Properties} collections.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #create()
 * @see MapBuilder
 * @see Properties
 * @see PropertiesFactory
 * @see HashMapBuilder
 * @see LinkedHashMapBuilder
 * @see ImmutableMapBuilder
 */
@FullyTested
public final class PropertiesBuilder
extends AbstractMapBuilder<Object, Object, Properties, PropertiesBuilder> {

    /**
     * Constructs a new builder.
     */
    public static PropertiesBuilder create() {
        PropertiesBuilder x = new PropertiesBuilder();
        return x;
    }

    private PropertiesBuilder() {
        this(new HashMap<Object, Object>());
    }

    PropertiesBuilder(Map<Object, Object> map) {
        super(map);
    }

    /**
     * Builds a new {@code Properties} from values stored in the builder.
     */
    @Override
    public Properties build() {
        Properties x = new Properties();
        x.putAll(delegate());
        return x;
    }

    @Override
    protected PropertiesBuilder self() {
        return this;
    }

    /**
     * Adds a new key-value pair, or replaces the value for an existing pair.  Class
     * {@link Properties} is a subclass of {@code Hashtable&lt;Object, Object>}, so the argument
     * types here are {@code Object}, but the type for {@code key} and {@code value} is checked to
     * be {@link String}.
     *
     * @param key
     *        must be type {@code String}, but may be {@code null}
     * @param value
     *        must be type {@code String}, but may be {@code null}
     *
     * @return previous value for {@code key}
     *
     * @throws ClassCastException
     *         if {@code key} or {@code value} cannot be safely cast to type {@code String}
     *
     * @see Properties#put(Object, Object)
     * @see #putAll(Map)
     * @see #setProperty(String, String)
     */
    public Object put(Object key, Object value) {
        ObjectArgs.checkCast(key, String.class, "key");
        ObjectArgs.checkCast(value, String.class, "value");

        Object oldValue = super.put(key, value);
        return oldValue;
    }

    /**
     * Adds all key-value pairs from a map.
     *
     * @param map
     *        keys and values must be type {@code String}.  Can be empty, but not {@code null}
     *
     * @throws NullPointerException
     *         if {@code map} is {@code null}
     * @throws ClassCastException
     *         if a key or value cannot be safely cast to type {@code String}
     *
     * @see Properties#putAll(Map)
     * @see #put(Object, Object)
     */
    @Override
    public void putAll(Map<?, ?> map) {
        ObjectArgs.checkNotNull(map, "map");

        int index = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String keyArgName = "key[" + index + "]";
            String valueArgName = "value[" + index + "]";
            ObjectArgs.checkCast(entry.getKey(), String.class, keyArgName);
            ObjectArgs.checkCast(entry.getValue(), String.class, valueArgName);
            ++index;
        }
        super.putAll(map);
    }

    /**
     * Convenience method to call {@link #get(Object)}.
     *
     * @see Properties#getProperty(String)
     * @see #getProperty(String, String)
     * @see #setProperty(String, String)
     */
    public String getProperty(String key) {
        String x = (String) get(key);
        return x;
    }

    /**
     * Convenience method to call {@link #get(Object)}, but replace {@code null} value with
     * {@code defaultValue} (which may also be {@code null}).
     *
     * @see Properties#getProperty(String, String)
     * @see #getProperty(String)
     * @see #setProperty(String, String)
     */
    public String getProperty(String key, String defaultValue) {
        String x = (String) get(key);
        if (null == x) {
            x = defaultValue;
        }
        return x;
    }

    /**
     * Convenience method to call {@link #put(Object, Object)}.
     *
     * @see Properties#setProperty(String, String)
     * @see #getProperty(String)
     * @see #getProperty(String, String)
     */
    public Object setProperty(String key, String value) {
        Object x = put(key, value);
        return x;
    }
}
