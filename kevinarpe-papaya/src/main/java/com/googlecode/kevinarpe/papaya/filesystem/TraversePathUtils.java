package com.googlecode.kevinarpe.papaya.filesystem;

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
