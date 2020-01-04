package com.googlecode.kevinarpe.papaya.container.builder;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.Properties;

/**
 * Builds {@code Properties} collections.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #create()
 * @see MapBuilder
 * @see Properties
 * @see PropertiesBuilderFactory
 * @see HashMapBuilder
 * @see LinkedHashMapBuilder
 * @see ImmutableMapBuilder
 */
@FullyTested
public final class PropertiesBuilder
extends AbstractMapBuilder<Properties, Object, Object> {

    /**
     * Constructs a new builder.
     */
    public static PropertiesBuilder create() {
        PropertiesBuilder x = new PropertiesBuilder();
        return x;
    }

    private PropertiesBuilder() {
        // Empty
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
}
