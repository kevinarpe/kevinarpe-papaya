package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

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

import com.googlecode.kevinarpe.papaya.object.StatelessObject;

/**
 * Methods to create new instances that implement interface SLF4JMockLoggerFactory.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StatelessObject
 * @see ISLF4JMockLoggerUtils
 * @see SLF4JMockLoggerFactory
 */
public class SLF4JMockLoggerUtils
extends StatelessObject
implements ISLF4JMockLoggerUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final SLF4JMockLoggerUtils INSTANCE = new SLF4JMockLoggerUtils();

    /**
     * For projects that require total, static-free mocking capabilities, use this constructor.
     * Else, the static constant {@link #INSTANCE} will suffice.
     */
    public SLF4JMockLoggerUtils() {
        // Empty.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SLF4JMockLoggerFactory newFactoryInstance() {
        SLF4JMockLoggerFactoryImpl x = new SLF4JMockLoggerFactoryImpl();
        return x;
    }
}
