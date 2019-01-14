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

import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameLexicographicalComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileSizeSmallestToLargestComparator;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ReverseComparatorTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ReverseComparator.of()
    //

    @Test
    public void of_Pass() {
        Assert.assertNotNull(ReverseComparator.of(new FileSizeSmallestToLargestComparator()));
    }

    @Test
    public void of_PassWithReverseComparator() {
        final Comparator<File> comp = new FileSizeSmallestToLargestComparator();
        final Comparator<File> rev = ReverseComparator.of(comp);
        final Comparator<File> rev2 = ReverseComparator.of(rev);
        Assert.assertSame(rev2, comp);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        Assert.assertNotNull(ReverseComparator.of(null));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ReverseComparator.compare()
    //

    @Test
    public void compare_Pass() {
        File mockPath1 = Mockito.mock(File.class);
        File mockPath2 = Mockito.mock(File.class);

        Mockito.when(mockPath1.length()).thenReturn(Long.valueOf(5));
        Mockito.when(mockPath2.length()).thenReturn(Long.valueOf(10));

        final Comparator<File> comp = new FileSizeSmallestToLargestComparator();
        final Comparator<File> rev = ReverseComparator.of(comp);
        Assert.assertEquals(comp.compare(mockPath1, mockPath2), -1);
        Assert.assertEquals(rev.compare(mockPath1, mockPath2), +1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ReverseComparator.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(
                ReverseComparator.of(new FileSizeSmallestToLargestComparator()),
                ReverseComparator.of(new FileSizeSmallestToLargestComparator()))
            .addEqualityGroup(
                ReverseComparator.of(new FileNameLexicographicalComparator()),
                ReverseComparator.of(new FileNameLexicographicalComparator()))
            .testEquals();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ReverseComparator.toString()
    //

    @Test
    public void toString_Pass() {
        Assert.assertNotNull(ReverseComparator.of(new FileSizeSmallestToLargestComparator()).toString());
    }
}
