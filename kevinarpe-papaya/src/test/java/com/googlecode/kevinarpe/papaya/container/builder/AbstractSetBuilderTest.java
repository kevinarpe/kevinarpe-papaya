package com.googlecode.kevinarpe.papaya.container.builder;

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

import com.googlecode.kevinarpe.papaya.testing.mockito.MockitoUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

public class AbstractSetBuilderTest {

    private static final class _AbstractSetBuilder
        extends AbstractSetBuilder<String, Set<String>, _AbstractSetBuilder> {

        private _AbstractSetBuilder(Set<String> set) {
            super(set);
        }

        @Override
        public Set<String> build() {
            return delegate();
        }

        @Override
        protected _AbstractSetBuilder self() {
            return this;
        }
    }

    private Set<String> mockSet;

    private _AbstractSetBuilder classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockSet = MockitoUtils.INSTANCE.mockGenericInterface(Set.class);

        classUnderTest = new _AbstractSetBuilder(mockSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSetBuilder.ctor()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new _AbstractSetBuilder((Set<String>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSetBuilder.build()
    //

    @Test
    public void build_Pass() {
        assertSame(classUnderTest.build(), mockSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSetBuilder.addMany(Object, Object...)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_Object_ObjectArr_FailWithNull() {
        classUnderTest.addMany("abc", (String[]) null);
    }

    @Test
    public void addMany_Object_ObjectArr_PassWithNotEmpty() {
        classUnderTest.addMany("abc", "def");
        verify(mockSet, never()).addAll(Arrays.asList("abc", "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSetBuilder.addMany(Object[])
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_ObjectArr_FailWithNull() {
        classUnderTest.addMany((String[]) null);
    }

    @Test
    public void addMany_ObjectArr_PassWithEmpty() {
        classUnderTest.addMany(new String[0]);
        verify(mockSet, never()).addAll(anyCollectionOf(String.class));
    }

    @Test
    public void addMany_ObjectArr_PassWithNotEmpty() {
        classUnderTest.addMany(new String[] { "abc", "def" });
        verify(mockSet, never()).addAll(Arrays.asList("abc", "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSetBuilder.addMany(Iterable)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_Iterable_FailWithNull() {
        classUnderTest.addMany((Iterable<String>) null);
    }

    @Test
    public void addMany_Iterable_PassWithEmpty() {
        classUnderTest.addMany(Arrays.<String>asList());
        verify(mockSet, never()).add(anyString());
    }

    @Test
    public void addMany_Iterable_PassWithNonEmpty() {
        classUnderTest.addMany(Arrays.asList("abc", "def"));
        verify(mockSet).add("abc");
        verify(mockSet).add("def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSetBuilder.addMany(Iterator)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_Iterator_FailWithNull() {
        classUnderTest.addMany((Iterator<String>) null);
    }

    @Test
    public void addMany_Iterator_PassWithEmpty() {
        classUnderTest.addMany(Arrays.<String>asList().iterator());
        verify(mockSet, never()).add(anyString());
    }

    @Test
    public void addMany_Iterator_PassWithNonEmpty() {
        classUnderTest.addMany(Arrays.asList("abc", "def").iterator());
        verify(mockSet).add("abc");
        verify(mockSet).add("def");
    }
}
