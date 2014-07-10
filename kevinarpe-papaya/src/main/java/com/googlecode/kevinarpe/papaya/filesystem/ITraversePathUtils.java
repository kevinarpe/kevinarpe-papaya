package com.googlecode.kevinarpe.papaya.filesystem;

import java.io.File;
import java.util.Comparator;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link TraversePathUtils} or {@link TraversePathUtils#INSTANCE}
 * will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathUtils
 */
public interface ITraversePathUtils {

    /**
     * Constructs an iterable with its required attributes and none of its optional attributes:
     * <ul>
     *     <li>descend directory path filter</li>
     *     <li>descend directory comparator</li>
     *     <li>iterate path filter</li>
     *     <li>iterate comparator</li>
     * </ul>
     * <p>
     * The exception policy is set to the default:
     * {@link TraversePathUtils#DEFAULT_EXCEPTION_POLICY}.  It can be changed via
     * {@link TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)}.
     *
     * @param dirPath
     *        root directory to traverse.  Must not be {@code null}, but need not exist.  Directory
     *        trees may be highly ephemeral, so the existance of this directory at the time of
     *        construction is not required.
     *
     * @param depthPolicy
     *        when to descend directories: first or last.  Must not be {@code null}.
     *
     * @throws NullPointerException
     *         if {@code dirPath} or {@code depthPolicy} is {@code null}
     *
     * @see TraversePathIterable#withRootDirPath(File)
     * @see TraversePathIterable#withDepthPolicy(TraversePathDepthPolicy)
     * @see TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)
     * @see TraversePathUtils#DEFAULT_EXCEPTION_POLICY
     * @see TraversePathIterable#withOptionalDescendDirPathFilter(PathFilter)
     * @see TraversePathIterable#withOptionalDescendDirPathComparator(Comparator)
     * @see TraversePathIterable#withOptionalIteratePathFilter(PathFilter)
     * @see TraversePathIterable#withOptionalIteratePathComparator(Comparator)
     * @see #getTraversePathIterableFactory()
     */
    TraversePathIterable newTraversePathIterable(File dirPath, TraversePathDepthPolicy depthPolicy);

    /**
     * Retrieves the global instance that implements interface {@link TraversePathIterableFactory}.
     *
     * @return global instance
     *
     * @see #newTraversePathIterable(File, TraversePathDepthPolicy)
     */
    TraversePathIterableFactory getTraversePathIterableFactory();
}
