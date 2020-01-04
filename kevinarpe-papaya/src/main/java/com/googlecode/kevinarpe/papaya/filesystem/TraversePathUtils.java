package com.googlecode.kevinarpe.papaya.filesystem;

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

import java.io.File;

/**
 * Static utilities for {@link TraversePathIterable} and {@link TraversePathIterableFactory}.
 * To use the methods in this class create a new instance via {@link #TraversePathUtils()} or use
 * the public static member {@link #INSTANCE}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see ITraversePathUtils
 */
public final class TraversePathUtils
extends StatelessObject
implements ITraversePathUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final TraversePathUtils INSTANCE = new TraversePathUtils();

    /**
     * Used by the constructor to set the default exception policy for path iterators.  The value
     * is {@link TraversePathExceptionPolicy#THROW}.
     *
     * @see #newTraversePathIterable(File, TraversePathDepthPolicy)
     * @see TraversePathIterable#withExceptionPolicy()
     * @see TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)
     */
    public static final TraversePathExceptionPolicy DEFAULT_EXCEPTION_POLICY =
        TraversePathExceptionPolicy.THROW;

    public TraversePathUtils() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable newTraversePathIterable(
        File dirPath, TraversePathDepthPolicy depthPolicy) {
        TraversePathIterableImpl x = new TraversePathIterableImpl(dirPath, depthPolicy);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterableFactory getTraversePathIterableFactory() {
        return TraversePathIterableFactoryImpl.INSTANCE;
    }
}
