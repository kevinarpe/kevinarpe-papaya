package com.googlecode.kevinarpe.papaya.function.count;

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
public class RangeCountMatcherTest {

    @DataProvider
    public static Object[][]
    _fail_ctor_Data() {

        return new Object[][] {
            {-1, 0},
            {0, -1},
            {2, 1},
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "_fail_ctor_Data")
    public void fail_ctor(final int min, final int max) {

        new RangeCountMatcher(min, max);
    }

    @DataProvider
    public static Object[][]
    _pass_isMatch_Data() {

        return new Object[][] {
            {0, 7, 0, true},
            {0, 0, 1, false},
            {0, 7, 1, true},
            {1, 4, 0, false},
            {2, 4, 0, false},
            {2, 4, -1, false},
            {7, 23, 6, false},
            {7, 23, 7, true},
            {7, 23, 23, true},
            {7, 23, 24, false},
        };
    }

    @Test(dataProvider = "_pass_isMatch_Data")
    public void pass_isMatch(final int min, final int max, final int value, final boolean expectedResult) {

        final RangeCountMatcher m = new RangeCountMatcher(min, max);
        Assert.assertEquals(m.isMatch(value), expectedResult);
    }

    @Test
    public void pass_hashCode() {

        Assert.assertEquals(new RangeCountMatcher(7, 9).hashCode(), new RangeCountMatcher(7, 9).hashCode());
        Assert.assertNotEquals(new RangeCountMatcher(7, 9).hashCode(), new RangeCountMatcher(8, 9).hashCode());
        Assert.assertNotEquals(new RangeCountMatcher(7, 9).hashCode(), new RangeCountMatcher(7, 10).hashCode());
    }

    @DataProvider
    public static Object[][]
    _pass_equals_Data() {

        return new Object[][] {
            {0, 0, 0, 0},
            {0, 0, 0, 1},
            {7, 23, 6, 23},
            {7, 23, 7, 23},
            {7, 23, 7, 24},
        };
    }

    @Test(dataProvider = "_pass_equals_Data")
    public void pass_equals(final int min, final int max, final int min2, final int max2) {

        final RangeCountMatcher m = new RangeCountMatcher(min, max);
        final RangeCountMatcher m2 = new RangeCountMatcher(min2, max2);
        Assert.assertFalse(m.equals(new Object()));
        Assert.assertFalse(m2.equals(new Object()));
        Assert.assertTrue(m.equals(m));
        Assert.assertTrue(m2.equals(m2));
        Assert.assertEquals(m.equals(m2), min == min2 && max == max2);
    }

    @Test
    public void pass_toString() {

        Assert.assertEquals(new RangeCountMatcher(2, 7).toString(), "x >= 2 && x <= 7");
        Assert.assertEquals(new RangeCountMatcher(12, 23).toString(), "x >= 12 && x <= 23");
    }
}
