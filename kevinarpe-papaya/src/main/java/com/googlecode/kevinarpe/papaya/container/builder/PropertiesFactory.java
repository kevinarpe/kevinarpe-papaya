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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Map;
import java.util.Properties;

/**
 * Creates {@code PropertiesBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #create()
 * @see MapFactory
 * @see PropertiesBuilder
 * @see HashMapFactory
 * @see LinkedHashMapFactory
 * @see ImmutableMapFactory
 */
public final class PropertiesFactory
extends ForwardingMapFactoryHelper<Object, Object, Properties>
implements MapFactory<Object, Object, Properties, PropertiesBuilder> {

    /**
     * Constructs a new builder factory.
     */
    public static PropertiesFactory create() {
        PropertiesFactory x = new PropertiesFactory();
        return x;
    }

    private final MapFactoryHelper<Object, Object, Properties> _mapFactoryHelper;

    private PropertiesFactory() {
        this(new MapBuilderUtils<Object, Object, Properties>());
    }

    PropertiesFactory(IMapBuilderUtils<Object, Object, Properties> mapBuilderUtils) {
        _mapFactoryHelper =
            new MapFactoryHelperImpl<Object, Object, Properties>(
                new _MinimalistMapBuilderFactory(),
                mapBuilderUtils);
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

    @Override
    protected MapFactoryHelper<Object, Object, Properties> delegate() {
        return _mapFactoryHelper;
    }

    private static final class _MinimalistMapBuilderFactory
    extends StatelessObject
    implements MinimalistMapBuilderFactory
                    <
                        Object,
                        Object,
                        Properties,
                        _MinimalistMapBuilder
                    > {

        @Override
        public _MinimalistMapBuilder newInstance() {
            _MinimalistMapBuilder x = new _MinimalistMapBuilder();
            return x;
        }
    }

    private static final class _MinimalistMapBuilder
    implements MinimalistMapBuilder<Object, Object, Properties> {

        private final Properties _prop;

        private _MinimalistMapBuilder() {
            _prop = new Properties();
        }

        @Override
        public void put(Object key, Object value) {
            ObjectArgs.checkCast(key, String.class, "key");
            ObjectArgs.checkCast(value, String.class, "value");

            _prop.put(key, value);
        }

        @Override
        public Properties getMap() {
            return _prop;
        }
    }
}
