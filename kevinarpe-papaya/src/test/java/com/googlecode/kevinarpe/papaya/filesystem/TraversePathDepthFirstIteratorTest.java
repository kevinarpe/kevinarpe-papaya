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

import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TraversePathDepthFirstIteratorTest
extends TraversePathIteratorTestBase {

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathDepthFirstIterator.ctor()
    //

    @Test
    public void ctor_Pass() {
        core_ctor_Pass(TraversePathDepthPolicy.DEPTH_FIRST);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathDepthFirstIterator.hasNext()/next()
    //

    @DataProvider
    private Object[][] _hasNextAndNext_Pass_Data() {
        return new Object[][] {
            new Object[] {
                new String[] {
                },
            },
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
            new Object[] {
                new String[] {
                    "10/3/2/1",
                    "11/6/5/4",
                    "12/9/8/7",
                },
            },
        };
    }

    @Test(dataProvider = "_hasNextAndNext_Pass_Data")
    public void hasNextAndNext_Pass(String[] pathSpecArr)
    throws IOException {
        TraversePathIterator pathIter = newInstance(TraversePathDepthPolicy.DEPTH_FIRST).iterator();
        core_hasNextAndNext_Pass(pathIter, pathSpecArr);
    }

    @Test(dataProvider = "_hasNextAndNext_Pass_Data",
            expectedExceptions = NoSuchElementException.class)
    public void hasNextAndNext_FailWithNoSuchElementException(String[] pathSpecArr)
    throws IOException {
        core_hasNextAndNext_FailWithNoSuchElementException(
            TraversePathDepthPolicy.DEPTH_FIRST, pathSpecArr);
    }

    @Test(dataProvider = "_hasNextAndNext_Pass_Data")
    public void hasNextAndNext_PassWithFilter(String[] pathSpecArr)
    throws IOException {
        core_hasNextAndNext_PassWithEvenNumericPrefixFilter(
            TraversePathDepthPolicy.DEPTH_FIRST, pathSpecArr);
    }

    @Test(dataProvider = "_hasNextAndNext_Pass_Data")
    public void hasNextAndNext_PassWithOnlyRootDirFilter(String[] pathSpecArr)
    throws IOException {
        core_hasNextAndNext_PassWithOnlyRootDirFilter(
            TraversePathDepthPolicy.DEPTH_LAST, pathSpecArr);
    }

    @Test(dataProvider = "_hasNextAndNext_Pass_Data")
    public void hasNextAndNext_PassWithAcceptNoneFilter(String[] pathSpecArr)
    throws IOException {
        core_hasNextAndNext_PassWithAcceptNoneFilter(
            TraversePathDepthPolicy.DEPTH_LAST, pathSpecArr);
    }

    @Test(expectedExceptions = PathRuntimeException.class)
    public void hasNextAndNext_FailWithPathRuntimeException()
    throws IOException {
        TraversePathIterable pathIterable = newInstance(TraversePathDepthPolicy.DEPTH_FIRST);
        pathIterable = pathIterable.withExceptionPolicy(TraversePathExceptionPolicy.THROW);
        TraversePathIterator pathIter = pathIterable.iterator();

        recursiveDeleteDir(BASE_DIR_PATH);
        assertTrue(BASE_DIR_PATH.mkdir());
        try {
            int max = createFiles(new String[] { "2/{1}", "3" });
            assertTrue(pathIter.hasNext());
            while (pathIter.hasNext()) {
                File path = pathIter.next();
                if (path.getName().equals("1.regularFile")) {
                    File dirPathToDelete = new File(BASE_DIR_PATH, "3.directory");
                    assertTrue(dirPathToDelete.delete());
                }
            }
        }
        finally {
            recursiveDeleteDir(BASE_DIR_PATH);
        }
    }
}
