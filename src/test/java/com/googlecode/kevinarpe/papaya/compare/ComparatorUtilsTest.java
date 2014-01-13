package com.googlecode.kevinarpe.papaya.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

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
        assertEquals(ComparatorUtils.normalizeCompareResult(rawCompareResult), finalCompareResult);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ComparatorUtils.chain()
    //

    @Test
    public void chain_PassWithOneComparator() {
        @SuppressWarnings("unchecked")
        Comparator<Object> mockComparator = (Comparator<Object>) mock(Comparator.class);

        assertSame(
            ComparatorUtils.chain(ImmutableList.of(mockComparator)),
            mockComparator);
    }

    @Test
    public void chain_PassWithTwoComparators() {
        @SuppressWarnings("unchecked")
        Comparator<Object> mockComparator = (Comparator<Object>) mock(Comparator.class);
        @SuppressWarnings("unchecked")
        Comparator<Object> mockComparator2 = (Comparator<Object>) mock(Comparator.class);

        Comparator<Object> newComparator =
            ComparatorUtils.chain(ImmutableList.of(mockComparator, mockComparator2));

        assertNotSame(newComparator, mockComparator);
        assertNotSame(newComparator, mockComparator2);

        Object value = new Object();
        int count = 0;
        int count2 = 0;

        when(mockComparator.compare(value, value)).thenReturn(0);  ++count;
        when(mockComparator2.compare(value, value)).thenReturn(0);  ++count2;

        assertEquals(newComparator.compare(value, value), 0);
        verify(mockComparator, times(count)).compare(value, value);
        verify(mockComparator2, times(count2)).compare(value, value);

        when(mockComparator.compare(value, value)).thenReturn(+99);  ++count;
        when(mockComparator2.compare(value, value)).thenReturn(0);

        assertEquals(newComparator.compare(value, value), +99);
        verify(mockComparator, times(count)).compare(value, value);
        verify(mockComparator2, times(count2)).compare(value, value);

        when(mockComparator.compare(value, value)).thenReturn(0);  ++count;
        when(mockComparator2.compare(value, value)).thenReturn(-99);  ++count2;

        assertEquals(newComparator.compare(value, value), -99);
        verify(mockComparator, times(count)).compare(value, value);
        verify(mockComparator2, times(count2)).compare(value, value);
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
