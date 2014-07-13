package com.googlecode.kevinarpe.papaya.container;

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

import com.google.common.testing.EqualsTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AbstractTupleTest {

    private static final String value1 = "abc";
    private static final Integer value2 = 456;

    private AbstractTuple<Object> classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new AbstractTuple<Object>(value1, value2) {};
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.empty()
    //
    @Test
    public void empty_Pass() {
        AbstractTuple<String> classUnderTest = AbstractTuple.empty();
        assertEquals(0, classUnderTest.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.ctor()
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithEmpty() {
        new AbstractTuple<String>() {};
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new AbstractTuple<String>((String[]) null) {};
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithArrayArg() {
        new AbstractTuple<Object>("abc", new Object[0], "def") {};
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.copyCtor()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void copyCtor_FailWithNull() {
        new AbstractTuple<String>((Tuple<String>) null) {};
    }

    @Test
    public void copyCtor_PassWithNonEmpty() {
        AbstractTuple<Object> copy = new AbstractTuple<Object>(classUnderTest) {};
        assertEquals(copy, classUnderTest);
    }

    @Test
    public void copyCtor_PassEmpty() {
        AbstractTuple<Object> copy = new AbstractTuple<Object>(AbstractTuple.empty()) {};
        assertEquals(copy, AbstractTuple.empty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.size()
    //

    @Test
    public void size_Pass() {
        assertEquals(AbstractTuple.empty().size(), 0);
        assertEquals(classUnderTest.size(), 2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.get()
    //

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void get_FailWithNegativeIndex() {
        classUnderTest.get(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void get_FailWithIndexTooLarge() {
        classUnderTest.get(99);
    }

    @Test
    public void get_Pass() {
        assertEquals(classUnderTest.get(0), value1);
        assertEquals(classUnderTest.get(1), value2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.toArray()
    //

    @Test
    public void toArray_Pass() {
        assertEquals(AbstractTuple.empty().toArray(), new Object[0]);
        assertEquals(classUnderTest.toArray(), new Object[] { value1, value2 });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.toArray()
    //

    @Test
    public void List_Pass() {
        Iterator<Object> iter = classUnderTest.iterator();
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), value1);
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), value2);
        assertFalse(iter.hasNext());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.toString()
    //

    @Test
    public void toString_PassWithEmpty() {
        String actual = AbstractTuple.empty().toString();
        String expected = AbstractTuple.JOINER.join(new Object[0]);
        assertEquals(actual, expected);
    }

    @Test
    public void toString_PassWithNonEmpty() {
        String actual = classUnderTest.toString();
        String expected = AbstractTuple.JOINER.join(value1, value2);
        assertEquals(actual, expected);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTuple.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(AbstractTuple.empty(), AbstractTuple.empty())
            .addEqualityGroup(classUnderTest, new AbstractTuple<Object>(classUnderTest) {})
            .addEqualityGroup(
                new AbstractTuple<Object>(123, "def") {}, new AbstractTuple<Object>(123, "def") {})
            .testEquals();
    }
}
