package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

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

import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMarkerNone;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JMarkerNoneTest {

    private final SLF4JMarkerNone classUnderTest = SLF4JMarkerNone.INSTANCE;

    private Marker mockMarker;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMarker = mock(Marker.class);
    }

    @Test
    public void getName_Pass() {
        assertSame(classUnderTest.getName(), SLF4JMarkerNone.NAME);
    }

    @Test
    public void add_Pass() {
        classUnderTest.add(mockMarker);
    }

    @Test
    public void remove_Pass() {
        assertFalse(classUnderTest.remove(mockMarker));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void hasChildren_Pass() {
        assertFalse(classUnderTest.hasChildren());
    }

    @Test
    public void hasReferences_Pass() {
        assertFalse(classUnderTest.hasReferences());
    }

    @Test
    public void iterator_Pass() {
        assertFalse(classUnderTest.iterator().hasNext());
    }

    @Test
    public void containsMarker_Pass() {
        assertFalse(classUnderTest.contains(mockMarker));
    }

    @Test
    public void containsString_Pass() {
        assertFalse(classUnderTest.contains("anotherMarkerName"));
    }
}
