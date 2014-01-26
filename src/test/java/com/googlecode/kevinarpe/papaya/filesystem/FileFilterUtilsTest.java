package com.googlecode.kevinarpe.papaya.filesystem;

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
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileFilterUtilsTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileFilterUtils.anyOf()
    //

    @Test
    public void anyOf_PassWithOneFileFilter() {
        FileFilter mockFileFilter = mock(FileFilter.class);

        assertSame(
            FileFilterUtils.anyOf(ImmutableList.of(mockFileFilter)),
            mockFileFilter);
    }

    @Test
    public void anyOf_PassWithTwoFileFilters() {
        FileFilter mockFileFilter = mock(FileFilter.class);
        FileFilter mockFileFilter2 = mock(FileFilter.class);

        FileFilter newFileFilter =
            FileFilterUtils.anyOf(ImmutableList.of(mockFileFilter, mockFileFilter2));

        assertNotSame(newFileFilter, mockFileFilter);
        assertNotSame(newFileFilter, mockFileFilter2);

        File path = new File("dummy");
        int count = 0;
        int count2 = 0;

        when(mockFileFilter.accept(path)).thenReturn(true);  ++count;
        when(mockFileFilter2.accept(path)).thenThrow(new RuntimeException());

        assertTrue(newFileFilter.accept(path));
        verify(mockFileFilter, times(count)).accept(path);
        verify(mockFileFilter2, times(count2)).accept(path);

        when(mockFileFilter.accept(path)).thenReturn(false);  ++count;
        // Inverted version required here else throws RuntimeException
        doReturn(true).when(mockFileFilter2).accept(path);  ++count2;

        assertTrue(newFileFilter.accept(path));
        verify(mockFileFilter, times(count)).accept(path);
        verify(mockFileFilter2, times(count2)).accept(path);

        when(mockFileFilter.accept(path)).thenReturn(false);  ++count;
        when(mockFileFilter2.accept(path)).thenReturn(false);  ++count2;

        assertFalse(newFileFilter.accept(path));
        verify(mockFileFilter, times(count)).accept(path);
        verify(mockFileFilter2, times(count2)).accept(path);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void anyOf_FailWithNullCollection() {
        FileFilterUtils.anyOf((Collection<FileFilter>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void anyOf_FailWhenCollectionHasNullElement() {
        FileFilterUtils.anyOf(ImmutableList.<FileFilter>of(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void anyOf_FailWithEmptyCollection() {
        FileFilterUtils.anyOf(ImmutableList.<FileFilter>of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileFilterUtils.allOf()
    //

    @Test
    public void allOf_PassWithOneFileFilter() {
        FileFilter mockFileFilter = mock(FileFilter.class);

        assertSame(
            FileFilterUtils.allOf(ImmutableList.of(mockFileFilter)),
            mockFileFilter);
    }

    @Test
    public void allOf_PassWithTwoFileFilters() {
        FileFilter mockFileFilter = mock(FileFilter.class);
        FileFilter mockFileFilter2 = mock(FileFilter.class);

        FileFilter newFileFilter =
            FileFilterUtils.allOf(ImmutableList.of(mockFileFilter, mockFileFilter2));

        assertNotSame(newFileFilter, mockFileFilter);
        assertNotSame(newFileFilter, mockFileFilter2);

        File path = new File("dummy");
        int count = 0;
        int count2 = 0;

        when(mockFileFilter.accept(path)).thenReturn(true);  ++count;
        when(mockFileFilter2.accept(path)).thenReturn(true);  ++count2;

        assertTrue(newFileFilter.accept(path));
        verify(mockFileFilter, times(count)).accept(path);
        verify(mockFileFilter2, times(count2)).accept(path);

        when(mockFileFilter.accept(path)).thenReturn(false);  ++count;
        when(mockFileFilter2.accept(path)).thenReturn(true);

        assertFalse(newFileFilter.accept(path));
        verify(mockFileFilter, times(count)).accept(path);
        verify(mockFileFilter2, times(count2)).accept(path);

        when(mockFileFilter.accept(path)).thenReturn(true);  ++count;
        when(mockFileFilter2.accept(path)).thenReturn(false);  ++count2;

        assertFalse(newFileFilter.accept(path));
        verify(mockFileFilter, times(count)).accept(path);
        verify(mockFileFilter2, times(count2)).accept(path);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void allOf_FailWithNullCollection() {
        FileFilterUtils.allOf((Collection<FileFilter>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void allOf_FailWhenCollectionHasNullElement() {
        FileFilterUtils.allOf(ImmutableList.<FileFilter>of(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void allOf_FailWithEmptyCollection() {
        FileFilterUtils.allOf(ImmutableList.<FileFilter>of());
    }
}
