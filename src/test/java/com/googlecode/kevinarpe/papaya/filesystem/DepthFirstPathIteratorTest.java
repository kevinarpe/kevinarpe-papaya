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
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameNumericPrefixSmallestToLargestComparator;
import com.googlecode.kevinarpe.papaya.string.NumericPrefix;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DepthFirstPathIteratorTest {

    private static final File baseDirPath = new File("rootdir." + UUID.randomUUID().toString());

//    @BeforeTest
//    public void beforeTest() {
//        Assert.assertTrue(baseDirPath.mkdir());
//    }
//
//    @AfterTest
//    public void afterTest() {
//        _recursiveDeleteDir(baseDirPath);
//    }

    private void _recursiveDeleteDir(File dirPath) {
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
                    stack.pop().delete();
                }
            }
            else {
                stack.pop().delete();
            }
        }
    }

    @DataProvider
    private Object[][] _x_Data() {
        return new Object[][] {
            // TODO: More tests!
            new Object[] {
                new String[] {
                    "9/8/7/6/5/4/3/2/{1}",
                },
            },
            new Object[] {
                new String[] {
                    "{15,16,17}",
                    "14/{1,2,3}",
                    "18/{4,5,6}",
                    "19/13/{7,8,9}",
                    "19/{10,11,12}",
                },
            },
            new Object[] {
                new String[] {
                    "{10,11,12}",
                    "13/{1,2,3}",
                    "14/{4,5,6}",
                    "15/{7,8,9}",
                },
            },
            new Object[] {
                new String[] {
                    "10/{1,2,3}",
                    "11/{4,5,6}",
                    "12/{7,8,9}",
                    "{13,14,15}",
                },
            },
        };
    }

    @Test(dataProvider = "_x_Data")
    public void x(String[] pathSpecArr)
    throws IOException {
        _recursiveDeleteDir(baseDirPath);
        Assert.assertTrue(baseDirPath.mkdir());

        try {
            int max = _createFiles(pathSpecArr);
            int count = 0;
            List<Comparator<File>> fileComparatorList =
                ImmutableList.<Comparator<File>>of(new FileNameNumericPrefixSmallestToLargestComparator());
            // TODO: Fixme
            TraversePathDepthFirstIterator dfpi = null;
//            DepthFirstPathIterator dfpi = null;
//                new TraversePathIterable(baseDirPath)
//                    .withDescendDirPathComparatorList(fileComparatorList)
//                    .withPathComparatorList(fileComparatorList)
//                    .iterator();
            while (dfpi.hasNext()) {
                ++count;
                File path = dfpi.next();
                long numericFileName = new NumericPrefix(path.getName()).getNumericValue();
                Assert.assertEquals(count, numericFileName);
            }
            Assert.assertEquals(count, max);
        }
        finally {
            _recursiveDeleteDir(baseDirPath);
        }
    }

    private int _createFiles(String[] pathSpecArr)
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
                        max = _chooseMax(max, fileName, maxSet);
                        File filePath = new File(dirPath, fileName + ".regularFile");
                        filePath.createNewFile();
                    }
                }
                else {
                    max = _chooseMax(max, part, maxSet);
                    dirPath = new File(dirPath, part + ".directory");
                    dirPath.mkdir();
                }
            }
        }
        return max;
    }

    private int _chooseMax(int oldMax, String newMaxStr, Set<Integer> maxSet) {
        int newMax = Integer.parseInt(newMaxStr);
        if (newMax > oldMax) {
            if (!maxSet.add(newMax)) {
                throw new IllegalArgumentException(String.format(
                    "Duplicate name: %d", newMax));
            }
            return newMax;
        }
//        throw new AssertionError(String.format("newMax (%d) <= oldMax (%d)", newMax, oldMax));
        return oldMax;
    }
}
