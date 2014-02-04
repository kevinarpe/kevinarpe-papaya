package com.googlecode.kevinarpe.papaya.filesystem.factory;

import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;

import java.io.File;

/**
 * Factory for {@link TraversePathIterable}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathIterable
 */
public interface TraversePathIterableFactory {

    /**
     * @see TraversePathIterable#TraversePathIterable(File, TraversePathDepthPolicy)
     */
    TraversePathIterable newInstance(File dirPath, TraversePathDepthPolicy depthPolicy);
}
