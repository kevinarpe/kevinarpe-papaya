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
public class Floats2Test {

    ///////////////////////////////////////////////////////////////////////////
    // Floats2.checkedCastFromInt(int)
    //

    @DataProvider
    private static Object[][] checkedCastFromInt_Pass_Data() {
        return new Object[][] {
            { 0 },
            { 123 },
            { Integer.MIN_VALUE },  // Surprising!
            { Integer.MAX_VALUE },  // Surprising!
        };
    }

    @Test(dataProvider = "checkedCastFromInt_Pass_Data")
    public void checkedCastFromInt_Pass(final int input) {

        final float output = Floats2.checkedCastFromInt(input);
        Assert.assertEquals((int) output, input);
    }

    @DataProvider
    private static Object[][] checkedCastFromInt_Fail_Data() {
        return new Object[][] {
            { Integer.MIN_VALUE + 1 },  // Surprising!
            { Integer.MAX_VALUE - 1 },  // Surprising!
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "checkedCastFromInt_Fail_Data")
    public void checkedCastFromInt_Fail(final int input) {

        final float output = Floats2.checkedCastFromInt(input);
    }
}
