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

import com.googlecode.kevinarpe.papaya.input.InputSource2;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Loads groups of Java properties, checks for correctness, and carefully logs any overrides.
 * <p>
 * To construct new instances, see {@link PropertiesLoaderUtils#newInstance()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PropertiesLoaderUtils
 */
public interface PropertiesLoader {

    /**
     * Retrieves the current optional policy to check each group of properties.
     *
     * @return current policy which can be {@code null}
     */
    PropertiesLoaderPolicy withOptionalPolicy();

    /**
     * Constructs a new instance with a new optional policy to check each group of properties.
     *
     * @param optionalPolicy
     *        policy for each group of properties.  Can be {@code null}
     *
     * @return new instance
     */
    PropertiesLoaderImpl withOptionalPolicy(PropertiesLoaderPolicy optionalPolicy);

    /**
     *
     * @param inputSourceList
     * @return
     * @throws PropertiesLoaderException
     */
    Properties loadAsProperties(List<InputSource2> inputSourceList)
    throws PropertiesLoaderException;

    Map<String, String> loadAsMap(List<InputSource2> inputSourceList)
    throws PropertiesLoaderException;
}
