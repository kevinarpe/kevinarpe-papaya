package com.googlecode.kevinarpe.papaya.testing.logging;

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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.container.Lists2;
import com.googlecode.kevinarpe.papaya.testing.logging.AnyLoggingEventAttributeValuePredicate;
import com.googlecode.kevinarpe.papaya.testing.logging.ILoggingEventAttribute;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class AnyLoggingEventAttributeValuePredicateTest {

    interface TestLoggingEvent {
        // Empty.
    }

    interface TestLoggingEventAttribute
    extends ILoggingEventAttribute<TestLoggingEvent> {
        // Empty.
    }

    private TestLoggingEvent mockLoggingEvent;
    private TestLoggingEventAttribute mockLoggingEventAttribute;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLoggingEvent = mock(TestLoggingEvent.class);
        mockLoggingEventAttribute = mock(TestLoggingEventAttribute.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AnyLoggingEventAttributeValuePredicate.ctor(ILoggingEventAttribute, Set)
    //

    @DataProvider
    private static Object[][] _ctor1_FailWithNull_Data() {
        TestLoggingEventAttribute mockLoggingEventAttribute2 =
            mock(TestLoggingEventAttribute.class);

        return new Object[][] {
            { (TestLoggingEventAttribute) null, (Set<?>) null },
            { mockLoggingEventAttribute2, (Set<?>) null },
            { (TestLoggingEventAttribute) null, ImmutableSet.of("value") },
        };
    }

    @Test(dataProvider = "_ctor1_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor1_FailWithNull(TestLoggingEventAttribute attribute, Set<?> valueSet) {
        new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(attribute, valueSet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor1_FailWithEmptySet() {
        new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
            mockLoggingEventAttribute, new HashSet<Object>());
    }

    @Test
    public void ctor1_Pass() {
        Set<?> valueSet = Sets.newHashSet("value");
        AnyLoggingEventAttributeValuePredicate classUnderTest =
            new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
                mockLoggingEventAttribute, valueSet);
        assertSame(classUnderTest.getAttribute(), mockLoggingEventAttribute);
        assertEquals(classUnderTest.getValueSet(), valueSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AnyLoggingEventAttributeValuePredicate.ctor(ILoggingEventAttribute, Object, Object...)
    //

    @DataProvider
    private static Object[][] _ctor2_FailWithNull_Data() {
        TestLoggingEventAttribute mockLoggingEventAttribute2 =
            mock(TestLoggingEventAttribute.class);

        return new Object[][] {
            // The second arg can be null and third arg may contain nulls.
            { (TestLoggingEventAttribute) null, (Object) null, (Object[]) null },
            { mockLoggingEventAttribute2, (Object) null, (Object[]) null },
            { (TestLoggingEventAttribute) null, (Object) null, new Object[0] },
            { (TestLoggingEventAttribute) null, (Object) null, new Object[] { null } },

            { (TestLoggingEventAttribute) null, "value", (Object[]) null },
            { mockLoggingEventAttribute2, "value", (Object[]) null },
            { (TestLoggingEventAttribute) null, "value", new Object[0] },
            { (TestLoggingEventAttribute) null, "value", new Object[] { null } },
        };
    }

    @Test(dataProvider = "_ctor2_FailWithNull_Data",
        expectedExceptions = NullPointerException.class)
    public void ctor2_FailWithNull_Data(
        TestLoggingEventAttribute attribute, Object value, Object[] moreValueArr) {
        new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(attribute, value, moreValueArr);
    }

    @DataProvider
    private static Object[][] _ctor2_Pass_Data() {
        return new Object[][] {
            { "value", (Object[]) null },
            { null, (Object[]) null },
            { null, new Object[] { null } },
            { null, new Object[] { null, null } },
            { "value", new Object[] { null } },
            { "value", new Object[] { "value" } },
            { "value", new Object[] { "value2" } },
            { "value", new Object[] { "value", "value2" } },
            { null, new Object[] { "value" } },
            { null, new Object[] { "value2" } },
            { null, new Object[] { "value", "value2" } },
        };
    }

    @Test(dataProvider = "_ctor2_Pass_Data")
    public void ctor2_Pass(Object value, Object[] moreValueArr) {
        AnyLoggingEventAttributeValuePredicate classUnderTest;
        Set<Object> valueSet;
        if (null == moreValueArr) {
            classUnderTest =
                new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
                    mockLoggingEventAttribute, value);
            valueSet = Sets.newHashSet(value);
        }
        else {
            classUnderTest =
                new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
                    mockLoggingEventAttribute, value, moreValueArr);
            valueSet =
                Sets.newHashSet(Lists2.newUnmodifiableListFromOneOrMoreValues(value, moreValueArr));
        }
        assertSame(classUnderTest.getAttribute(), mockLoggingEventAttribute);
        assertEquals(classUnderTest.getValueSet(), valueSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AnyLoggingEventAttributeValuePredicate.apply(SLF4JLoggingEvent)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void apply_FailWithNull() {
        AnyLoggingEventAttributeValuePredicate<TestLoggingEvent> classUnderTest =
            new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
                mockLoggingEventAttribute, "blah");
        classUnderTest.apply((TestLoggingEvent) null);
    }

    @Test
    public void apply_PassWhenTrue() {
        AnyLoggingEventAttributeValuePredicate<TestLoggingEvent> classUnderTest =
            new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
                mockLoggingEventAttribute, "blah");
        when(mockLoggingEventAttribute.getValue(mockLoggingEvent)).thenReturn("blah");

        assertTrue(classUnderTest.apply(mockLoggingEvent));
    }

    @Test
    public void apply_PassWhenFalse() {
        AnyLoggingEventAttributeValuePredicate<TestLoggingEvent> classUnderTest =
            new AnyLoggingEventAttributeValuePredicate<TestLoggingEvent>(
                mockLoggingEventAttribute, "blah");
        when(mockLoggingEventAttribute.getValue(mockLoggingEvent)).thenReturn("notBlah");

        assertFalse(classUnderTest.apply(mockLoggingEvent));
    }
}
