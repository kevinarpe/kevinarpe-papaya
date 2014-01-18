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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TraversePathDepthLastIteratorTest
extends TraversePathIteratorTestBase {

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathDepthLastIterator.ctor()
    //

    @Test
    public void ctor_Pass() {
        core_ctor_Pass(TraversePathDepthPolicy.DEPTH_LAST);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathDepthFirstIterator.hasNext()/next()
    //

    @DataProvider
    private Object[][] _hasNextAndNext_Pass_Data() {
        return new Object[][] {
            // TODO: More tests!
            new Object[] {
                new String[] {
                },
            },
            new Object[] {
                new String[] {
                    "1/2/3/4/5/6/7/8/{9}",
                },
            },
            new Object[] {
                new String[] {
                    "{1,2,3}",
                    "4/{7,8,9}",
                    "5/{10,11,12}",
                    "6/13/{17,18,19}",
                    "6/{14,15,16}",
                },
            },
            new Object[] {
                new String[] {
                    "{1,2,3}",
                    "4/{7,8,9}",
                    "5/{10,11,12}",
                    "6/{13,14,15}",
                },
            },
            new Object[] {
                new String[] {
                    "1/{7,8,9}",
                    "2/{10,11,12}",
                    "3/{13,14,15}",
                    "{4,5,6}",
                },
            },
        };
    }

    @Test(dataProvider = "_hasNextAndNext_Pass_Data")
    public void hasNextAndNext_Pass(String[] pathSpecArr)
    throws IOException {
        core_hasNextAndNext_Pass(TraversePathDepthPolicy.DEPTH_LAST, pathSpecArr);
    }
}
