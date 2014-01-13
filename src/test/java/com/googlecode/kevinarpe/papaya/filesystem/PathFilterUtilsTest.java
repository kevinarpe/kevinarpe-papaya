package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collection;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
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
public class PathFilterUtilsTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PathFilterUtils.anyOf()
    //

    @Test
    public void anyOf_PassWithOnePathFilter() {
        PathFilter mockPathFilter = mock(PathFilter.class);

        assertSame(
            PathFilterUtils.anyOf(ImmutableList.of(mockPathFilter)),
            mockPathFilter);
    }

    @Test
    public void anyOf_PassWithTwoPathFilters() {
        PathFilter mockPathFilter = mock(PathFilter.class);
        PathFilter mockPathFilter2 = mock(PathFilter.class);

        PathFilter newPathFilter =
            PathFilterUtils.anyOf(ImmutableList.of(mockPathFilter, mockPathFilter2));

        assertNotSame(newPathFilter, mockPathFilter);
        assertNotSame(newPathFilter, mockPathFilter2);

        File path = new File("dummy");
        int count = 0;
        int count2 = 0;

        when(mockPathFilter.accept(eq(path), anyInt())).thenReturn(true);  ++count;
        when(mockPathFilter2.accept(eq(path), anyInt())).thenThrow(RuntimeException.class);

        assertTrue(newPathFilter.accept(path, 7));
        verify(mockPathFilter, times(count)).accept(eq(path), anyInt());
        verify(mockPathFilter2, times(count2)).accept(eq(path), anyInt());

        when(mockPathFilter.accept(eq(path), anyInt())).thenReturn(false);  ++count;
        when(mockPathFilter2.accept(eq(path), anyInt())).thenReturn(true);  ++count2;

        assertTrue(newPathFilter.accept(path, 7));
        verify(mockPathFilter, times(count)).accept(eq(path), anyInt());
        verify(mockPathFilter2, times(count2)).accept(eq(path), anyInt());

        when(mockPathFilter.accept(eq(path), anyInt())).thenReturn(false);  ++count;
        when(mockPathFilter2.accept(eq(path), anyInt())).thenReturn(false);  ++count2;

        assertFalse(newPathFilter.accept(path, 7));
        verify(mockPathFilter, times(count)).accept(eq(path), anyInt());
        verify(mockPathFilter2, times(count2)).accept(eq(path), anyInt());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void anyOf_FailWithNullCollection() {
        PathFilterUtils.anyOf((Collection<PathFilter>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void anyOf_FailWhenCollectionHasNullElement() {
        PathFilterUtils.anyOf(ImmutableList.<PathFilter>of(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void anyOf_FailWithEmptyCollection() {
        PathFilterUtils.anyOf(ImmutableList.<PathFilter>of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PathFilterUtils.allOf()
    //

    @Test
    public void allOf_PassWithOnePathFilter() {
        PathFilter mockPathFilter = mock(PathFilter.class);

        assertSame(
            PathFilterUtils.allOf(ImmutableList.of(mockPathFilter)),
            mockPathFilter);
    }

    @Test
    public void allOf_PassWithTwoPathFilters() {
        PathFilter mockPathFilter = mock(PathFilter.class);
        PathFilter mockPathFilter2 = mock(PathFilter.class);

        PathFilter newPathFilter =
            PathFilterUtils.allOf(ImmutableList.of(mockPathFilter, mockPathFilter2));

        assertNotSame(newPathFilter, mockPathFilter);
        assertNotSame(newPathFilter, mockPathFilter2);

        File path = new File("dummy");
        int count = 0;
        int count2 = 0;

        when(mockPathFilter.accept(eq(path), anyInt())).thenReturn(true);  ++count;
        when(mockPathFilter2.accept(eq(path), anyInt())).thenReturn(true);  ++count2;

        assertTrue(newPathFilter.accept(path, 7));
        verify(mockPathFilter, times(count)).accept(eq(path), anyInt());
        verify(mockPathFilter2, times(count2)).accept(eq(path), anyInt());

        when(mockPathFilter.accept(eq(path), anyInt())).thenReturn(false);  ++count;
        when(mockPathFilter2.accept(eq(path), anyInt())).thenReturn(true);

        assertFalse(newPathFilter.accept(path, 7));
        verify(mockPathFilter, times(count)).accept(eq(path), anyInt());
        verify(mockPathFilter2, times(count2)).accept(eq(path), anyInt());

        when(mockPathFilter.accept(eq(path), anyInt())).thenReturn(true);  ++count;
        when(mockPathFilter2.accept(eq(path), anyInt())).thenReturn(false);  ++count2;

        assertFalse(newPathFilter.accept(path, 7));
        verify(mockPathFilter, times(count)).accept(eq(path), anyInt());
        verify(mockPathFilter2, times(count2)).accept(eq(path), anyInt());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void allOf_FailWithNullCollection() {
        PathFilterUtils.allOf((Collection<PathFilter>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void allOf_FailWhenCollectionHasNullElement() {
        PathFilterUtils.allOf(ImmutableList.<PathFilter>of(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void allOf_FailWithEmptyCollection() {
        PathFilterUtils.allOf(ImmutableList.<PathFilter>of());
    }
}
