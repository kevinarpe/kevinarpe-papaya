package com.googlecode.kevinarpe.papaya.primitives;

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

import com.google.common.math.DoubleMath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Longs2Test {

    ///////////////////////////////////////////////////////////////////////////
    // Longs2.checkedCastFromDouble(double)
    //

    @DataProvider
    private static Object[][] checkedCastFromDouble_Pass_Data() {
        return new Object[][] {
            { 0L },
            { -4.0d },
            { 123.0d },
            { (double) +Long.MAX_VALUE },
            { (double) -Long.MAX_VALUE },
        };
    }

    @Test(dataProvider = "checkedCastFromDouble_Pass_Data")
    public void checkedCastFromDouble_Pass(final double input) {

        final int I1 = Integer.MAX_VALUE - 1;
        final float F1 = (float) I1;
        final boolean IFB1 = I1 == (int) F1;
        final float F2 = Float.MAX_VALUE + Float.MIN_VALUE;
        final long L1 = Long.MAX_VALUE - 1;
        final double D1 = (double) L1;
        final boolean B1 = L1 == (long) D1;
        final boolean B1b = DoubleMath.isMathematicalInteger(D1);
        final long L2 = 499999999000000001L;
        final double D2 = (double) L2;
        final boolean B2 = L2 == (long) D1;
        final boolean B2b = DoubleMath.isMathematicalInteger(D2);
        final long output = Longs2.checkedCastFromDouble(input);
        Assert.assertEquals(output, (long) input);
    }

    @DataProvider
    private static Object[][] checkedCastFromDouble_Fail_Data() {
        return new Object[][] {
            { Double.NaN },
            { Double.POSITIVE_INFINITY },
            { Double.NEGATIVE_INFINITY },
            { -4.5d },
            { 123.456d },
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "checkedCastFromDouble_Fail_Data")
    public void checkedCastFromDouble_Fail(final double input) {

        final long output = Longs2.checkedCastFromDouble(input);
    }
}
