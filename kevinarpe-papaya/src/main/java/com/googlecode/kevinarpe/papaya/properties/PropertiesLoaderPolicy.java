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

import com.googlecode.kevinarpe.papaya.jdk.properties.JavaProperty;

import java.util.List;

/**
 * Checks a group of properties for correctness.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PropertiesLoaderPolicyForDuplicates
 */
public interface PropertiesLoaderPolicy {

    /**
     * Applies a policy to a group of properties.  On failure, an exception is thrown.
     *
     * @param propertyList
     *        may be empty, but must not be {@code null}
     *
     * @throws PropertiesLoaderException
     *         if group of properties fails any checks/tests/policies
     */
    void apply(List<? extends JavaProperty> propertyList)
    throws PropertiesLoaderException;
}