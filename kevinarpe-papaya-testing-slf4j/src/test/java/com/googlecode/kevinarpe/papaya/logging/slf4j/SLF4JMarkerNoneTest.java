package com.googlecode.kevinarpe.papaya.logging.slf4j;

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
