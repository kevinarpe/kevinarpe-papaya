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
public class ExactlyCountMatcherTest {

    @DataProvider
    public static Object[][]
    _fail_ctor_Data() {

        return new Object[][] {
            {-1},
            {-2},
            {-99},
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "_fail_ctor_Data")
    public void fail_ctor(final int exact) {

        new ExactlyCountMatcher(exact);
    }

    @DataProvider
    public static Object[][]
    _pass_isMatch_Data() {

        return new Object[][] {
            {0, 0, true},
            {0, 1, false},
            {1, 0, false},
            {2, 0, false},
            {2, -1, false},
            {7, 23, false},
            {7, 7, true},
            {23, 23, true},
        };
    }

    @Test(dataProvider = "_pass_isMatch_Data")
    public void pass_isMatch(final int exact, final int value, final boolean expectedResult) {

        final ExactlyCountMatcher m = new ExactlyCountMatcher(exact);
        Assert.assertEquals(m.isMatch(value), expectedResult);
    }

    @Test
    public void pass_hashCode() {

        Assert.assertEquals(new ExactlyCountMatcher(7).hashCode(), new ExactlyCountMatcher(7).hashCode());
        Assert.assertNotEquals(new ExactlyCountMatcher(6).hashCode(), new ExactlyCountMatcher(7).hashCode());
    }

    @DataProvider
    public static Object[][]
    _pass_equals_Data() {

        return new Object[][] {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1},
            {7, 23},
        };
    }

    @Test(dataProvider = "_pass_equals_Data")
    public void pass_equals(final int exact, final int exact2) {

        final ExactlyCountMatcher m = new ExactlyCountMatcher(exact);
        final ExactlyCountMatcher m2 = new ExactlyCountMatcher(exact2);
        Assert.assertFalse(m.equals(new Object()));
        Assert.assertFalse(m2.equals(new Object()));
        Assert.assertTrue(m.equals(m));
        Assert.assertTrue(m2.equals(m2));
        Assert.assertEquals(m.equals(m2), exact == exact2);
    }

    @Test
    public void pass_toString() {

        Assert.assertEquals(new ExactlyCountMatcher(7).toString(), "exactly 7");
        Assert.assertEquals(new ExactlyCountMatcher(23).toString(), "exactly 23");
    }
}
