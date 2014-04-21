package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.testing.logging.AnyLoggingEventAttributeValuePredicate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventAnalyzerImplTest {

    public static final String MESSAGE1 = "message1";
    public static final String MESSAGE2 = "message2";
    private SLF4JLoggingEvent mockSLF4JLoggingEvent1;
    private SLF4JLoggingEvent mockSLF4JLoggingEvent2;

    private SLF4JLoggingEventAnalyzerImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockSLF4JLoggingEvent1 = mock(SLF4JLoggingEvent.class);
        mockSLF4JLoggingEvent2 = mock(SLF4JLoggingEvent.class);
        classUnderTest =
            new SLF4JLoggingEventAnalyzerImpl(
                Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _whenLoggingEventGetMessage() {
        // We need to mock both here as there is no guarantee which method will be called.
        // The double mock will make this unit test more resiliant to underlying code changes.
        when(mockSLF4JLoggingEvent1.getAttributeValue(SLF4JLoggingEventAttribute.MESSAGE))
            .thenReturn(MESSAGE1);
        when(mockSLF4JLoggingEvent1.getMessage()).thenReturn(MESSAGE1);

        when(mockSLF4JLoggingEvent2.getAttributeValue(SLF4JLoggingEventAttribute.MESSAGE))
            .thenReturn(MESSAGE2);
        when(mockSLF4JLoggingEvent2.getMessage()).thenReturn(MESSAGE2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.ctor()
    //

    @Test
    public void ctor_PassWithEmptyList() {
        new SLF4JLoggingEventAnalyzerImpl(Arrays.<SLF4JLoggingEvent>asList());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new SLF4JLoggingEventAnalyzerImpl((List<SLF4JLoggingEvent>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullElement() {
        new SLF4JLoggingEventAnalyzerImpl(
            Arrays.asList(mockSLF4JLoggingEvent1, null, mockSLF4JLoggingEvent2));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventList()
    //

    @Test
    public void getLoggingEventList_Pass() {
        assertEquals(
            classUnderTest.getLoggingEventList(),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventListIncluding(Predicate)
    //

    @Test
    public void getLoggingEventListIncluding1_Pass() {
        _whenLoggingEventGetMessage();

        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate non-matching message strings are ignored, e.g., "blah".
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, "blah", MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate duplicate message strings work.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate second logging event can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate both messages can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, "blah", MESSAGE2)),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));

        // Demonstrate both messages can be matched (again).
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2, "blah", MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getLoggingEventListIncluding_FaillWithNull() {
        classUnderTest.getLoggingEventListIncluding((Predicate<SLF4JLoggingEvent>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventListIncluding(SLF4JLoggingEventAttribute, Object, Object...)
    //

    @Test
    public void getLoggingEventListIncluding2_Pass() {
        _whenLoggingEventGetMessage();

        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate non-matching message strings are ignored, e.g., "blah".
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, "blah", MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate duplicate message strings work.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate second logging event can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate both messages can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, "blah", MESSAGE2),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));

        // Demonstrate both messages can be matched (again).
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2, "blah", MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));
    }

    @DataProvider
    private static Object[][] _getLoggingEventList_cluding2_FailWithNull_Data() {
        return new Object[][] {
            { (SLF4JLoggingEventAttribute) null, new Object(), new Object[] { } },
            { SLF4JLoggingEventAttribute.TIME_STAMP, new Object(), (Object[]) null },

            { (SLF4JLoggingEventAttribute) null, (Object) null, new Object[] { } },
            { SLF4JLoggingEventAttribute.TIME_STAMP, (Object) null, (Object[]) null },
        };
    }

    @Test(dataProvider = "_getLoggingEventList_cluding2_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void getLoggingEventListIncluding2_FailWithNull(
            SLF4JLoggingEventAttribute attribute, Object value, Object[] moreValueArr) {
        classUnderTest.getLoggingEventListIncluding(attribute, value, moreValueArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventListIncluding(SLF4JLoggingEventAttribute, Set)
    //

    @Test
    public void getLoggingEventListIncluding3_Pass() {
        // We need to mock both here as there is no guarantee which method will be called.
        // The double mock will make this unit test more resiliant to underlying code changes.
        _whenLoggingEventGetMessage();

        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate non-matching message strings are ignored, e.g., "blah".
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of("blah", MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate duplicate message strings work.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE1, MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate second logging event can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE2)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate both messages can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE1, "blah", MESSAGE2)),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));

        // Demonstrate both messages can be matched (again).
        assertEquals(
            classUnderTest.getLoggingEventListIncluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE2, "blah", MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent1, mockSLF4JLoggingEvent2));
    }

    @DataProvider
    private static Object[][] _getLoggingEventList_cluding3_FailWithNull_Data() {
        return new Object[][] {
            { (SLF4JLoggingEventAttribute) null, ImmutableSet.of() },
            { SLF4JLoggingEventAttribute.TIME_STAMP, (Set<?>) null },
        };
    }

    @Test(dataProvider = "_getLoggingEventList_cluding3_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void getLoggingEventListIncluding3_FailWithNull(
            SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        classUnderTest.getLoggingEventListIncluding(attribute, valueSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getLoggingEventListIncluding3_FailWithEmptyValueSet() {
        classUnderTest.getLoggingEventListIncluding(
            SLF4JLoggingEventAttribute.MARKER, ImmutableSet.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventListExcluding(Predicate)
    //

    @Test
    public void getLoggingEventListExcluding1_Pass() {
        _whenLoggingEventGetMessage();

        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate non-matching message strings are ignored, e.g., "blah".
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, "blah", MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate duplicate message strings work.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate second logging event can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate both messages can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, "blah", MESSAGE2)),
            Arrays.<Object>asList());

        // Demonstrate both messages can be matched (again).
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2, "blah", MESSAGE1)),
            Arrays.<Object>asList());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getLoggingEventListExcluding_FaillWithNull() {
        classUnderTest.getLoggingEventListExcluding((Predicate<SLF4JLoggingEvent>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventListExcluding(SLF4JLoggingEventAttribute, Object, Object...)
    //

    @Test
    public void getLoggingEventListExcluding2_Pass() {
        _whenLoggingEventGetMessage();

        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate non-matching message strings are ignored, e.g., "blah".
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, "blah", MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate duplicate message strings work.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, MESSAGE1),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate second logging event can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate both messages can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE1, "blah", MESSAGE2),
            Arrays.<Object>asList());

        // Demonstrate both messages can be matched (again).
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, MESSAGE2, "blah", MESSAGE1),
            Arrays.<Object>asList());
    }

    @Test(dataProvider = "_getLoggingEventList_cluding2_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void getLoggingEventListExcluding2_FailWithNull(
            SLF4JLoggingEventAttribute attribute, Object value, Object[] moreValueArr) {
        classUnderTest.getLoggingEventListExcluding(attribute, value, moreValueArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAnalyzerImpl.getLoggingEventListExcluding(SLF4JLoggingEventAttribute, Set)
    //

    @Test
    public void getLoggingEventListExcluding3_Pass() {
        // We need to mock both here as there is no guarantee which method will be called.
        // The double mock will make this unit test more resiliant to underlying code changes.
        _whenLoggingEventGetMessage();

        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate non-matching message strings are ignored, e.g., "blah".
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of("blah", MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate duplicate message strings work.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE1, MESSAGE1)),
            Arrays.asList(mockSLF4JLoggingEvent2));

        // Demonstrate second logging event can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE2)),
            Arrays.asList(mockSLF4JLoggingEvent1));

        // Demonstrate both messages can be matched.
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE1, "blah", MESSAGE2)),
            Arrays.<Object>asList());

        // Demonstrate both messages can be matched (again).
        assertEquals(
            classUnderTest.getLoggingEventListExcluding(
                SLF4JLoggingEventAttribute.MESSAGE, ImmutableSet.of(MESSAGE2, "blah", MESSAGE1)),
            Arrays.<Object>asList());
    }

    @Test(dataProvider = "_getLoggingEventList_cluding3_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void getLoggingEventListExcluding3_FailWithNull(
        SLF4JLoggingEventAttribute attribute, Set<?> valueSet) {
        classUnderTest.getLoggingEventListExcluding(attribute, valueSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getLoggingEventListExcluding3_FailWithEmptyValueSet() {
        classUnderTest.getLoggingEventListExcluding(
            SLF4JLoggingEventAttribute.MARKER, ImmutableSet.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventImpl.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventAnalyzerImpl(ImmutableList.<SLF4JLoggingEvent>of()),
            new SLF4JLoggingEventAnalyzerImpl(ImmutableList.<SLF4JLoggingEvent>of()));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventAnalyzerImpl(ImmutableList.of(mockSLF4JLoggingEvent1)),
            new SLF4JLoggingEventAnalyzerImpl(ImmutableList.of(mockSLF4JLoggingEvent1)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventAnalyzerImpl(ImmutableList.of(mockSLF4JLoggingEvent2)),
            new SLF4JLoggingEventAnalyzerImpl(ImmutableList.of(mockSLF4JLoggingEvent2)));

        equalsTester.testEquals();
    }
}
