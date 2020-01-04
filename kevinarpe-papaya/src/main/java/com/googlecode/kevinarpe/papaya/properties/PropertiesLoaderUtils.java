package com.googlecode.kevinarpe.papaya.properties;

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

import com.googlecode.kevinarpe.papaya.object.StatelessObject;

/**
 * Constants and static utilities for {@link PropertiesLoader}.  To use the methods in this class
 * create a new instance via {@link #PropertiesLoaderUtils()} or use the public static member
 * {@link #INSTANCE}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see #newInstance()
 * @see StatelessObject
 * @see IPropertiesLoaderUtils
 */
public final class PropertiesLoaderUtils
extends StatelessObject
implements IPropertiesLoaderUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final PropertiesLoaderUtils INSTANCE = new PropertiesLoaderUtils();

    /**
     * Default value for {@link PropertiesLoader#withOptionalPolicy()}.
     *
     * @see DefaultPropertiesLoaderPolicy
     */
    public static final PropertiesLoaderPolicy DEFAULT_POLICY =
        DefaultPropertiesLoaderPolicy.INSTANCE;

    /**
     * For projects that require total, static-free mocking capabilities, use this constructor.
     * Else, the static constant {@link #INSTANCE} will suffice.
     */
    public PropertiesLoaderUtils() {
        // Empty
    }

    /**
     * Constructs a new instance of {@code PropertiesLoader} with a default loader policy.
     *
     * @see #DEFAULT_POLICY
     */
    @Override
    public PropertiesLoader newInstance() {
        PropertiesLoaderImpl x = new PropertiesLoaderImpl();
        return x;
    }
}
