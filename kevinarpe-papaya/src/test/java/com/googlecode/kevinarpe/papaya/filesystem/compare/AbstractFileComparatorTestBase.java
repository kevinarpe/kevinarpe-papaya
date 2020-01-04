package com.googlecode.kevinarpe.papaya.filesystem.compare;

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

import com.google.common.primitives.Longs;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.compare.ComparatorUtils;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class AbstractFileComparatorTestBase
    <
        TComparator extends Comparator<File>,
        TComparableValue
    >
{
    /** Call new. */
    protected abstract TComparator newComparator();

    protected abstract void setupMocksForComparePass(
        File mockPath, TComparableValue mockReturnValue);

    /**
     * @see #compareStrings(TComparator, String, String)
     * @see #compareLongs(TComparator, long, long)
     */
    protected abstract int compareValues(
        TComparator comparator, TComparableValue value1, TComparableValue value2);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    public int compareStrings(TComparator comparator, String left, String right) {
        int result = left.compareTo(right);
        result = ComparatorUtils.normalizeCompareResult(result);
        return result;
    }

    public int compareLongs(TComparator comparator, long left, long right) {
        int result = Longs.compare(left, right);
        result = ComparatorUtils.normalizeCompareResult(result);
        return result;
    }

    public static void assertCompareResultEquals(
            int actualCompareResult, int expectedCompareResult) {
        assertCompareResultValid(actualCompareResult, "actualCompareResult");
        assertCompareResultValid(expectedCompareResult, "expectedCompareResult");
        assertEquals(actualCompareResult, expectedCompareResult);
    }

    public static void assertCompareResultValid(int compareResult, String argName) {
        if (0 != compareResult && +1 != compareResult && -1 != compareResult) {
            throw new AssertionError(String.format(
                "Invalid compare result: %s = %d; valid values: -1, 0, or +1",
                argName, compareResult));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractFileComparatorTestBase.ctor()
    //

    @Test
    public void ctor_Pass() {
        newComparator();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractFileComparatorTestBase.compare()
    //

    protected void compare_Pass_core(TComparableValue leftValue, TComparableValue rightValue) {
        TComparator classUnderTest = newComparator();
        compare_Pass_core(classUnderTest, leftValue, rightValue);
    }

    protected void compare_Pass_core(
            TComparator classUnderTest, TComparableValue leftValue, TComparableValue rightValue) {

        File mockPath1 = Mockito.mock(File.class);
        File mockPath2 = Mockito.mock(File.class);
        setupMocksForComparePass(mockPath1, leftValue);
        setupMocksForComparePass(mockPath2, rightValue);

        assertCompareResultEquals(
            classUnderTest.compare(mockPath1, mockPath2),
            compareValues(classUnderTest, leftValue, rightValue));

        assertCompareResultEquals(
            classUnderTest.compare(mockPath2, mockPath1),
            compareValues(classUnderTest, rightValue, leftValue));

        assertCompareResultEquals(
            classUnderTest.compare(mockPath1, mockPath1),
            compareValues(classUnderTest, leftValue, leftValue));

        assertCompareResultEquals(
            classUnderTest.compare(mockPath1, mockPath1),
            0);

        assertCompareResultEquals(
            classUnderTest.compare(mockPath2, mockPath2),
            compareValues(classUnderTest, rightValue, rightValue));

        assertCompareResultEquals(
            classUnderTest.compare(mockPath2, mockPath2),
            0);
    }

    @DataProvider
    private static Object[][] compare_Fail_Data() {
        File path = new File("abc");
        return new Object[][] {
            { path, null },
            { null, path },
            { null, null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProviderClass = AbstractFileComparatorTestBase.class,
            dataProvider = "compare_Fail_Data")
    public void compare_FailWithNull(File path1, File path2) {
        Comparator<File> classUnderTest = newComparator();
        classUnderTest.compare(path1, path2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractFileComparatorTestBase.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(newComparator(), newComparator())
            .testEquals();
    }
}
