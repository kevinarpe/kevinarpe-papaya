package com.googlecode.kevinarpe.papaya.compare;

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

import com.google.common.collect.ImmutableList;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ComparatorUtilsTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ComparatorUtils.normalizeCompareResult()
    //

    @DataProvider
    private static Object[][] normalizeCompareResult_Pass_Data() {
        return new Object[][] {
            { -17, -1 },
            { +17, +1 },
            { -1, -1 },
            { 0, 0 },
            { +1, +1 },
        };
    }

    @Test(dataProvider = "normalizeCompareResult_Pass_Data")
    public void normalizeCompareResult_Pass(int rawCompareResult, int finalCompareResult) {
        Assert.assertEquals(ComparatorUtils.normalizeCompareResult(rawCompareResult), finalCompareResult);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ComparatorUtils.chain()
    //

    @Test
    public void chain_PassWithOneComparator() {
        @SuppressWarnings("unchecked")
        Comparator<Object> mockComparator = (Comparator<Object>) Mockito.mock(Comparator.class);

        Assert.assertSame(
            ComparatorUtils.chain(ImmutableList.of(mockComparator)),
            mockComparator);
    }

    @Test
    public void chain_PassWithTwoComparators() {
        @SuppressWarnings("unchecked")
        Comparator<Object> mockComparator = (Comparator<Object>) Mockito.mock(Comparator.class);
        @SuppressWarnings("unchecked")
        Comparator<Object> mockComparator2 = (Comparator<Object>) Mockito.mock(Comparator.class);

        Comparator<Object> newComparator =
            ComparatorUtils.chain(ImmutableList.of(mockComparator, mockComparator2));

        Assert.assertNotSame(newComparator, mockComparator);
        Assert.assertNotSame(newComparator, mockComparator2);

        Object value = new Object();
        int count = 0;
        int count2 = 0;

        Mockito.when(mockComparator.compare(value, value)).thenReturn(0);  ++count;
        Mockito.when(mockComparator2.compare(value, value)).thenReturn(0);  ++count2;

        Assert.assertEquals(newComparator.compare(value, value), 0);
        Mockito.verify(mockComparator, Mockito.times(count)).compare(value, value);
        Mockito.verify(mockComparator2, Mockito.times(count2)).compare(value, value);

        Mockito.when(mockComparator.compare(value, value)).thenReturn(+99);  ++count;
        Mockito.when(mockComparator2.compare(value, value)).thenReturn(0);

        Assert.assertEquals(newComparator.compare(value, value), +99);
        Mockito.verify(mockComparator, Mockito.times(count)).compare(value, value);
        Mockito.verify(mockComparator2, Mockito.times(count2)).compare(value, value);

        Mockito.when(mockComparator.compare(value, value)).thenReturn(0);  ++count;
        Mockito.when(mockComparator2.compare(value, value)).thenReturn(-99);  ++count2;

        Assert.assertEquals(newComparator.compare(value, value), -99);
        Mockito.verify(mockComparator, Mockito.times(count)).compare(value, value);
        Mockito.verify(mockComparator2, Mockito.times(count2)).compare(value, value);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void chain_FailWithNullCollection() {
        ComparatorUtils.chain((List<Comparator<Object>>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void chain_FailWhenCollectionHasNullElement() {
        ComparatorUtils.chain(ImmutableList.<Comparator<Object>>of(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void chain_FailWithEmptyCollection() {
        ComparatorUtils.chain(ImmutableList.<Comparator<Object>>of());
    }
}
