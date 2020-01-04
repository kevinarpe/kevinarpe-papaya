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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class NumbersTest {

    private static final class _Number
        extends Number {

        @Override
        public int intValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long longValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public float floatValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public double doubleValue() {
            throw new UnsupportedOperationException();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Numbers.doubleValue(Number, AllowNaN, AllowInfinity)
    //

    @DataProvider
    private static Object[][] doubleValue_PassRegular_Data() {
        return new Object[][] {
            { 0.0d, 0.0d },
            { 123.456d, 123.456d },
            { 123, 123d },
            { 123L, 123d },
            { Double.MIN_VALUE, Double.MIN_VALUE },
            { Double.MAX_VALUE, Double.MAX_VALUE },
            { -Double.MAX_VALUE, -Double.MAX_VALUE },
            { BigInteger.valueOf(123L), 123d },
            { BigInteger.valueOf(Long.MAX_VALUE), (double) Long.MAX_VALUE },
            { BigInteger.valueOf(Long.MIN_VALUE), (double) Long.MIN_VALUE },
        };
    }

    @Test(dataProvider = "doubleValue_PassRegular_Data")
    public void doubleValue_PassRegular(Number input, final double expectedOutput) {

        for (final AllowNaN allowNaN : AllowNaN.values()) {

            for (final AllowInfinity allowInfinity : AllowInfinity.values()) {

                final double output = Numbers.doubleValue(input, allowNaN, allowInfinity);
                Assert.assertEquals(output, expectedOutput);
            }
        }
    }

    @DataProvider
    private static Object[][] doubleValue_Fail_Data() {
        return new Object[][] {
            { new BigInteger("999999999999999999999999999999999999999999999999999999999999999999") },
            { BigInteger.valueOf(Long.MAX_VALUE - 1) },
            { BigInteger.valueOf(Long.MIN_VALUE + 1) },
            { new _Number() },
            { Long.MAX_VALUE - 1 },
            { Long.MIN_VALUE + 1 },
        };
    }

    @Test(
        expectedExceptions = {ArithmeticException.class, IllegalArgumentException.class},
        dataProvider = "doubleValue_Fail_Data"
    )
    public void doubleValue_Fail(Number input) {

        final double output = Numbers.doubleValue(input, AllowNaN.NO, AllowInfinity.NO);
    }

    @DataProvider
    private static Object[][] doubleValue_FailNull_Data() {
        return new Object[][] {
            { (AllowNaN) null, AllowInfinity.YES },
            { (AllowNaN) null, AllowInfinity.NO },
            { AllowNaN.YES, (AllowInfinity) null },
            { AllowNaN.NO, (AllowInfinity) null },
            { (AllowNaN) null, (AllowInfinity) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class, dataProvider = "doubleValue_FailNull_Data")
    public void doubleValue_FailNull(AllowNaN allowNaN, AllowInfinity allowInfinity) {

        final double output = Numbers.doubleValue(123.456d, allowNaN, allowInfinity);
    }

    @Test
    public void doubleValue_PassNaN() {

        final double input = Double.NaN;

        for (final AllowInfinity allowInfinity : AllowInfinity.values()) {

            final double output = Numbers.doubleValue(input, AllowNaN.YES, allowInfinity);
            Assert.assertEquals(output, input);
        }
    }

    @DataProvider
    private static Object[][] doubleValue_FailNaN_Data() {
        return new Object[][] {
            { AllowInfinity.YES },
            { AllowInfinity.NO },
        };
    }

    @Test(expectedExceptions = ArithmeticException.class, dataProvider = "doubleValue_FailNaN_Data")
    public void doubleValue_FailNaN(AllowInfinity allowInfinity) {

        final double input = Double.NaN;
        final double output = Numbers.doubleValue(input, AllowNaN.NO, allowInfinity);
    }

    @DataProvider
    private static Object[][] doubleValue_Infinity_Data() {
        return new Object[][] {
            { Double.POSITIVE_INFINITY, AllowNaN.YES },
            { Double.NEGATIVE_INFINITY, AllowNaN.YES },
            { Double.POSITIVE_INFINITY, AllowNaN.NO },
            { Double.NEGATIVE_INFINITY, AllowNaN.NO },
        };
    }

    @Test(dataProvider = "doubleValue_Infinity_Data")
    public void doubleValue_PassInfinity(final double input, AllowNaN allowNaN) {

        final double output = Numbers.doubleValue(input, allowNaN, AllowInfinity.YES);
        Assert.assertEquals(output, input);
    }

    @Test(expectedExceptions = ArithmeticException.class, dataProvider = "doubleValue_Infinity_Data")
    public void doubleValue_FailInfinity(final double input, AllowNaN allowNaN) {

        final double output = Numbers.doubleValue(input, allowNaN, AllowInfinity.NO);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Numbers.longValue(Number)
    //

    @DataProvider
    private static Object[][] longValue_PassRegular_Data() {
        return new Object[][] {
            { 0.0d, 0L },
            { (long) 123, 123L },
            { (int) 123, 123L },
            { (short) 123, 123L },
            { (byte) 123, 123L },
            { Long.MIN_VALUE, Long.MIN_VALUE },
            { Long.MAX_VALUE, Long.MAX_VALUE },
            { BigInteger.valueOf(123L), 123L },
            { BigInteger.valueOf(Long.MAX_VALUE), Long.MAX_VALUE },
            { BigInteger.valueOf(Long.MIN_VALUE), Long.MIN_VALUE },
        };
    }

    @Test(dataProvider = "longValue_PassRegular_Data")
    public void longValue_PassRegular(Number input, final long expectedOutput) {

        final long output = Numbers.longValue(input);
        Assert.assertEquals(output, expectedOutput);
    }

    @DataProvider
    private static Object[][] longValue_Fail_Data() {
        return new Object[][] {
            { 1.5d },
            { new BigInteger("999999999999999999999999999999999999999999999999999999999999999999") },
            { Double.NaN },
            { Double.POSITIVE_INFINITY },
            { Double.NEGATIVE_INFINITY },
            { new BigDecimal("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999") },
            { new BigDecimal("-999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999") },
            { new _Number() },
        };
    }

    @Test(
        expectedExceptions = {ArithmeticException.class, IllegalArgumentException.class},
        dataProvider = "longValue_Fail_Data"
    )
    public void longValue_Fail(Number input) {

        final long output = Numbers.longValue(input);
    }
}
