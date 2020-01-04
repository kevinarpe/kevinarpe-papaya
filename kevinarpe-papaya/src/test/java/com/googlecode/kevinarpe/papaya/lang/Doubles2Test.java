package com.googlecode.kevinarpe.papaya.lang;

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
public class Doubles2Test {

    ///////////////////////////////////////////////////////////////////////////
    // Doubles2.checkedCastFromLong(long)
    //

    @DataProvider
    private static Object[][] checkedCastFromLong_Pass_Data() {
        return new Object[][] {
            { 0 },
            { 123 },
            { Long.MIN_VALUE },  // Surprising!
            { Long.MAX_VALUE },  // Surprising!
        };
    }

    @Test(dataProvider = "checkedCastFromLong_Pass_Data")
    public void checkedCastFromLong_Pass(final long input) {

        final double output = Doubles2.checkedCastFromLong(input);
        Assert.assertEquals((long) output, input);
    }

    @DataProvider
    private static Object[][] checkedCastFromLong_Fail_Data() {
        return new Object[][] {
            { Long.MIN_VALUE + 1 },  // Surprising!
            { Long.MAX_VALUE - 1 },  // Surprising!
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "checkedCastFromLong_Fail_Data")
    public void checkedCastFromLong_Fail(final long input) {

        final double output = Doubles2.checkedCastFromLong(input);
    }
}
