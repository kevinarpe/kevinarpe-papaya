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

import com.beust.jcommander.internal.Sets;
import com.google.common.base.Splitter;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameNumericPrefixSmallestToLargestComparator;
import com.googlecode.kevinarpe.papaya.string.NumericPrefix;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TraversePathIteratorTestBase {

    private static final File baseDirPath = new File("rootdir." + UUID.randomUUID().toString());

    public static File getBaseDirPath() {
        return baseDirPath;
    }

    protected void core_ctor_Pass(TraversePathDepthPolicy depthPolicy) {
        new BaseTraversePathIterTest.ctor_Pass_Helper() {

            @Override
            protected BaseTraversePathIter newInstance(
                    File dirPath,
                    TraversePathDepthPolicy depthPolicy,
                    TraversePathExceptionPolicy exceptionPolicy,
                    PathFilter optDescendDirPathFilter,
                    Comparator<File> optDescendDirPathComparator,
                    PathFilter optIteratePathFilter,
                    Comparator<File> optIteratePathComparator) {
                return depthPolicy.createTraversePathIterator(
                    dirPath, exceptionPolicy, optDescendDirPathFilter, optDescendDirPathComparator,
                    optIteratePathFilter, optIteratePathComparator);
            }
        };
    }

    protected TraversePathIterable newInstance(TraversePathDepthPolicy depthPolicy) {
        Comparator<File> fileComparator = new FileNameNumericPrefixSmallestToLargestComparator();
        TraversePathIterable pathIter =
            new TraversePathIterable(baseDirPath, depthPolicy)
                .withOptionalDescendDirPathComparator(fileComparator)
                .withOptionalIteratePathComparator(fileComparator);
        return pathIter;
    }

    protected void core_hasNextAndNext_Pass(
            TraversePathIterator pathIter, String[] pathSpecArr)
    throws IOException {
        recursiveDeleteDir(baseDirPath);
        assertTrue(baseDirPath.mkdir());

        try {
            int max = createFiles(pathSpecArr);
            if (TraversePathDepthPolicy.DEPTH_LAST == pathIter.getDepthPolicy()) {
                assertTrue(pathIter.hasNext());
                assertTrue(pathIter.hasNext());  // check for no further side effects
                assertTrue(pathIter.hasNext());  // check for no further side effects
                File firstPath = pathIter.next();
                assertEquals(firstPath, baseDirPath);
            }
            int count = 0;
            // check for no further side effects
            while (pathIter.hasNext() && pathIter.hasNext() && pathIter.hasNext() && count < max) {
                ++count;
                File path = pathIter.next();
                long numericFileName = new NumericPrefix(path.getName()).getNumericValue();
                assertEquals(count, numericFileName);
            }
            assertEquals(count, max);
            if (TraversePathDepthPolicy.DEPTH_FIRST == pathIter.getDepthPolicy()) {
                assertTrue(pathIter.hasNext());
                assertTrue(pathIter.hasNext());  // check for no further side effects
                assertTrue(pathIter.hasNext());  // check for no further side effects
                File lastPath = pathIter.next();
                assertEquals(lastPath, baseDirPath);
            }
            assertFalse(pathIter.hasNext());
            assertFalse(pathIter.hasNext());  // check for no further side effects
            assertFalse(pathIter.hasNext());  // check for no further side effects
        }
        finally {
            recursiveDeleteDir(baseDirPath);
        }
    }

    protected void recursiveDeleteDir(File dirPath) {
        Stack<File> stack = new Stack<File>();
        stack.push(dirPath);
        while (!stack.isEmpty()) {
            if (stack.lastElement().isDirectory()) {
                File[] fileArr = stack.lastElement().listFiles();
                if (null != fileArr && fileArr.length > 0) {
                    for (File curr: fileArr) {
                        stack.push(curr);
                    }
                }
                else {
                    File path = stack.pop();
                    path.delete();
                }
            }
            else {
                File path = stack.pop();
                path.delete();
            }
        }
    }

    protected int createFiles(String[] pathSpecArr)
        throws IOException {
        Set<Integer> maxSet = Sets.newHashSet();
        int max = 0;
        for (String pathSpec : pathSpecArr) {
            File dirPath = baseDirPath;
            for (String part : Splitter.on("/").split(pathSpec)) {
                if ('{' == part.charAt(0) && '}' == part.charAt(part.length() - 1)) {
                    if (2 == part.length()) {
                        throw new IllegalArgumentException(String.format(
                            "File list is empty: %s", part));
                    }
                    String partWithoutCurlyBraces = part.substring(1, part.length() - 1);
                    for (String fileName : Splitter.on(",").split(partWithoutCurlyBraces)) {
                        max = _tryChooseMax(max, fileName, maxSet);
                        File filePath = new File(dirPath, fileName + ".regularFile");
                        filePath.createNewFile();
                    }
                }
                else {
                    max = _tryChooseMax(max, part, maxSet);
                    dirPath = new File(dirPath, part + ".directory");
                    dirPath.mkdir();
                }
            }
        }
        return max;
    }

    private int _tryChooseMax(int oldMax, String newMaxStr, Set<Integer> maxSet) {
        int newMax = Integer.MAX_VALUE;
        try {
            newMax = Integer.parseInt(newMaxStr);
        }
        catch (Exception e) {
            // Ignore.
        }
        if (newMax > oldMax) {
            if (!maxSet.add(newMax)) {
                throw new IllegalArgumentException(String.format(
                    "Duplicate name: %d", newMax));
            }
            return newMax;
        }
        return oldMax;
    }
}
