package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.io.File;
import java.util.Comparator;

/**
 * Static utilities for {@code TraversePathIterable} and {@code TraversePathIterableFactory}.
 * <p>
 * To use the methods in this class create a new instance via {@link #TraversePathIterableUtils()}
 * or use the public static member {@link #INSTANCE}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see ITraversePathIterableUtils
 * @see TraversePathIterable
 * @see TraversePathIterableFactory
 */
public final class TraversePathIterableUtils
extends StatelessObject
implements ITraversePathIterableUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final TraversePathIterableUtils INSTANCE = new TraversePathIterableUtils();

    /**
     * Used by the constructor to set the default exception policy for path iterators.  The value
     * is {@link TraversePathExceptionPolicy#THROW}.
     *
     * @see #newInstance(File, TraversePathDepthPolicy)
     * @see TraversePathIterable#withExceptionPolicy()
     * @see TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)
     */
    public static final TraversePathExceptionPolicy DEFAULT_EXCEPTION_POLICY =
        TraversePathExceptionPolicy.THROW;

    public static final PathFilter DEFAULT_DESCEND_DIR_PATH_FILTER =
        new PathFilter() {
            @Override
            public boolean accept(File path, int depth) {
                return true;
            }
        };

    public static final Comparator<File> DEFAULT_DESCEND_DIR_PATH_COMPARATOR =
        new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return 0;
            }
        };

    public static final PathFilter DEFAULT_ITERATE_PATH_FILTER =
        DEFAULT_DESCEND_DIR_PATH_FILTER;

    public static final Comparator<File> DEFAULT_ITERATE_PATH_COMPARATOR =
        DEFAULT_DESCEND_DIR_PATH_COMPARATOR;

    public TraversePathIterableUtils() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable newInstance(File dirPath, TraversePathDepthPolicy depthPolicy) {
        TraversePathIterableFactory factory = getFactory();
        TraversePathIterable x = factory.newInstance(dirPath, depthPolicy);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterableFactory getFactory() {
        return TraversePathIterableFactoryImpl.INSTANCE;
    }
}
