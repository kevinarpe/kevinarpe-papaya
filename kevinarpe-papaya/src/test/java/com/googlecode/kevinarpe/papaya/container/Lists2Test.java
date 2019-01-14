package com.googlecode.kevinarpe.papaya.container;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Lists2Test {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Lists2.newUnmodifiableListFromOneOrMoreValues(T, T...)
    //

    @DataProvider
    private static Object[][] _newUnmodifiableListFromOneOrMoreValues_Pass_Data() {
        return new Object[][] {
            { "abc", new String[] { } },
            { "abc", new String[] { "def" } },
            { "abc", new String[] { "def", "ghi" } },
            { "abc", new String[] { "def", "ghi", "jkl" } },
            { "abc", new String[] { "def", "ghi", "jkl", "ghi" } },
        };
    }

    @Test(dataProvider = "_newUnmodifiableListFromOneOrMoreValues_Pass_Data")
    public void newUnmodifiableListFromOneOrMoreValues_Pass(String first, String[] arr) {
        List<String> list = Lists2.newUnmodifiableListFromOneOrMoreValues(first, arr);
        ArrayList<String> list2 = new ArrayList<String>(1 + arr.length);
        list2.add(first);
        list2.addAll(Arrays.asList(arr));
        _assertEquals(list, list2);
    }

    private <T> void _assertEquals(List<T> L1, List<T> L2) {
        Assert.assertEquals(L1, L2);
        Assert.assertEquals(L2, L1);
        Assert.assertEquals(L1.size(), L2.size());

        Iterator<T> iter1 = L1.iterator();
        Iterator<T> iter2 = L2.iterator();
        while (iter1.hasNext() || iter2.hasNext()) {
            if (iter1.hasNext() != iter2.hasNext()) {
                throw new AssertionError("Comparison by iteration failed");
            }
            final T v1 = iter1.next();
            final T v2 = iter2.next();
            Assert.assertEquals(v1, v2);
            Assert.assertEquals(v2, v1);
        }

        final int size = L1.size();
        for (int i = 0; i < size; ++i) {
            final T v1 = L1.get(i);
            final T v2 = L2.get(i);
            Assert.assertEquals(v1, v2);
            Assert.assertEquals(v2, v1);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void newUnmodifiableListFromOneOrMoreValues_FailWithNullArr() {
        Lists2.newUnmodifiableListFromOneOrMoreValues("abc", (String[]) null);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class,
        dataProvider = "_newUnmodifiableListFromOneOrMoreValues_Pass_Data")
    public void newUnmodifiableListFromOneOrMoreValues_FailWithIndexOutOfBoundsException(
            String first, String[] arr) {
        List<String> list = Lists2.newUnmodifiableListFromOneOrMoreValues(first, arr);
        list.get(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class,
        dataProvider = "_newUnmodifiableListFromOneOrMoreValues_Pass_Data")
    public void newUnmodifiableListFromOneOrMoreValues_FailWithIndexOutOfBoundsException2(
            String first, String[] arr) {
        List<String> list = Lists2.newUnmodifiableListFromOneOrMoreValues(first, arr);
        list.get(list.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Lists2.newUnmodifiableListFromTwoOrMoreValues(T, T...)
    //

    @DataProvider
    private static Object[][] _newUnmodifiableListFromTwoOrMoreValues_Pass_Data() {
        return new Object[][] {
            { "abc", "123", new String[] { } },
            { "abc", "123", new String[] { "def" } },
            { "abc", "123", new String[] { "def", "ghi" } },
            { "abc", "123", new String[] { "def", "ghi", "jkl" } },
            { "abc", "123", new String[] { "def", "ghi", "jkl", "ghi" } },
        };
    }

    @Test(dataProvider = "_newUnmodifiableListFromTwoOrMoreValues_Pass_Data")
    public void newUnmodifiableListFromTwoOrMoreValues_Pass(
            String first, String second, String[] arr) {
        List<String> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(first, second, arr);
        ArrayList<String> list2 = new ArrayList<String>(2 + arr.length);
        list2.add(first);
        list2.add(second);
        list2.addAll(Arrays.asList(arr));
        _assertEquals(list, list2);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void newUnmodifiableListFromTwoOrMoreValues_FailWithNullArr() {
        Lists2.newUnmodifiableListFromTwoOrMoreValues("abc", "123", (String[]) null);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class,
            dataProvider = "_newUnmodifiableListFromTwoOrMoreValues_Pass_Data")
    public void newUnmodifiableListFromTwoOrMoreValues_FailWithIndexOutOfBoundsException(
            String first, String second, String[] arr) {
        List<String> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(first, second, arr);
        list.get(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class,
            dataProvider = "_newUnmodifiableListFromTwoOrMoreValues_Pass_Data")
    public void newUnmodifiableListFromTwoOrMoreValues_FailWithIndexOutOfBoundsException2(
            String first, String second, String[] arr) {
        List<String> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(first, second, arr);
        list.get(list.size());
    }
}
