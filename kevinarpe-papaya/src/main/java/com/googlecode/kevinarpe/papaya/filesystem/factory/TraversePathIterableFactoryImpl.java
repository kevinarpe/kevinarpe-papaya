package com.googlecode.kevinarpe.papaya.filesystem.factory;

import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;

import java.io.File;

/**
 * Stateless factory for {@link TraversePathIterable}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathIterableFactory
 * @see TraversePathIterable
 */
public class TraversePathIterableFactoryImpl
implements TraversePathIterableFactory {

    /**
     * An instance of this (stateless) class.
     */
    public static final TraversePathIterableFactoryImpl INSTANCE =
        new TraversePathIterableFactoryImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public TraversePathIterable newInstance(File dirPath, TraversePathDepthPolicy depthPolicy) {
        TraversePathIterable x = new TraversePathIterable(dirPath, depthPolicy);
        return x;
    }
}
