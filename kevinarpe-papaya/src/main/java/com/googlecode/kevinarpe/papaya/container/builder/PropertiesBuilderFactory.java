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

import java.util.Map;
import java.util.Properties;

/**
 * Creates {@code PropertiesBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #create()
 * @see MapBuilderFactory
 * @see PropertiesBuilder
 * @see HashMapBuilderFactory
 * @see LinkedHashMapBuilderFactory
 * @see ImmutableMapBuilderFactory
 */
@FullyTested
public final class PropertiesBuilderFactory
extends AbstractMapBuilderFactory<Object, Object, Properties, PropertiesBuilder> {

    /**
     * Constructs a new builder factory.
     */
    public static PropertiesBuilderFactory create() {
        PropertiesBuilderFactory x = new PropertiesBuilderFactory();
        return x;
    }

    private PropertiesBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public PropertiesBuilder builder() {
        PropertiesBuilder x = PropertiesBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Properties copyOf(Map<?, ?> map) {
        Properties x = new Properties();
        x.putAll(map);
        return x;
    }

    private static final class _MapBuilderFactoryHelper
    implements MapBuilderFactoryHelper<Object, Object, Properties> {

        private final Properties prop;

        private _MapBuilderFactoryHelper() {
            prop = new Properties();
        }

        @Override
        public void put(Object key, Object value) {
            ObjectArgs.checkCast(key, String.class, "key");
            ObjectArgs.checkCast(value, String.class, "value");

            prop.put(key, value);
        }

        @Override
        public Properties getMap() {
            return prop;
        }
    }

    @Override
    protected MapBuilderFactoryHelper<Object, Object, Properties>
    createMapBuilderHelper(int initialCapacity) {
        return null;
    }
}
