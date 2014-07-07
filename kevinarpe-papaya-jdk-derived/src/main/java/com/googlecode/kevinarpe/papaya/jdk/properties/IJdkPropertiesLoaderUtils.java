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

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link JdkPropertiesLoaderUtils} or
 * {@link JdkPropertiesLoaderUtils#INSTANCE} will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see JdkPropertiesLoaderUtils
 */
public interface IJdkPropertiesLoaderUtils {

    /**
     * Retrieves the global instance that implements interface {@link JdkPropertiesLoader}.
     *
     * @return global instance
     */
    JdkPropertiesLoader getInstance();
}
