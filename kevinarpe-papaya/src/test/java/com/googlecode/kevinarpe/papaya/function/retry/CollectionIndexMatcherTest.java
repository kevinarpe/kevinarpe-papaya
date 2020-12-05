package com.googlecode.kevinarpe.papaya.function.retry;

/*-
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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CollectionIndexMatcherTest {

    @DataProvider
    private static Object[][] _pass_Data() {
        return new Object[][] {
            { CollectionIndexMatcher.FIRST_ONLY, 0, 1, true },
            { CollectionIndexMatcher.FIRST_ONLY, 0, 7, true },
            { CollectionIndexMatcher.FIRST_ONLY, 1, 7, false },
            { CollectionIndexMatcher.FIRST_ONLY, 4, 7, false },
            { CollectionIndexMatcher.FIRST_ONLY, 6, 7, false },

            { CollectionIndexMatcher.LAST_ONLY, 0, 1, true },
            { CollectionIndexMatcher.LAST_ONLY, 0, 7, false },
            { CollectionIndexMatcher.LAST_ONLY, 1, 7, false },
            { CollectionIndexMatcher.LAST_ONLY, 4, 7, false },
            { CollectionIndexMatcher.LAST_ONLY, 6, 7, true },

            { CollectionIndexMatcher.FIRST_AND_LAST_ONLY, 0, 1, true },
            { CollectionIndexMatcher.FIRST_AND_LAST_ONLY, 0, 7, true },
            { CollectionIndexMatcher.FIRST_AND_LAST_ONLY, 1, 7, false },
            { CollectionIndexMatcher.FIRST_AND_LAST_ONLY, 4, 7, false },
            { CollectionIndexMatcher.FIRST_AND_LAST_ONLY, 6, 7, true },

            { CollectionIndexMatcher.ALL, 0, 1, true },
            { CollectionIndexMatcher.ALL, 0, 7, true },
            { CollectionIndexMatcher.ALL, 1, 7, true },
            { CollectionIndexMatcher.ALL, 4, 7, true },
            { CollectionIndexMatcher.ALL, 6, 7, true },
        };
    }

    @Test(dataProvider = "_pass_Data")
    public void pass(CollectionIndexMatcher matcher, final int index, final int size, final boolean expected) {

        Assert.assertEquals(matcher.isMatch(index, size), expected);
    }
}
