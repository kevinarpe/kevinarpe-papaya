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
import java.util.List;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

public class AbstractListBuilderTest {

    private static final class _AbstractListBuilder
    extends AbstractListBuilder<String, List<String>, _AbstractListBuilder> {

        protected _AbstractListBuilder(List<String> list) {
            super(list);
        }

        @Override
        public List<String> build() {
            return delegate();
        }

        @Override
        protected _AbstractListBuilder self() {
            return this;
        }
    }

    private List<String> mockList;

    private _AbstractListBuilder classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockList = MockitoUtils.INSTANCE.mockGenericInterface(List.class);

        classUnderTest = new _AbstractListBuilder(mockList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractListBuilder.ctor()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new _AbstractListBuilder((List<String>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractListBuilder.build()
    //

    @Test
    public void build_Pass() {
        assertSame(classUnderTest.build(), mockList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractListBuilder.addMany(Object...)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_ObjectArr_FailWithNull() {
        classUnderTest.addMany((String[]) null);
    }

    @Test
    public void addMany_ObjectArr_PassWithEmpty() {
        classUnderTest.addMany();
        verify(mockList, never()).addAll(anyCollectionOf(String.class));
    }

    @Test
    public void addMany_ObjectArr_PassWithNoneEmpty() {
        classUnderTest.addMany("abc", "def");
        verify(mockList, never()).addAll(Arrays.asList("abc", "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractListBuilder.addMany(Iterable)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_Iterable_FailWithNull() {
        classUnderTest.addMany((Iterable<String>) null);
    }

    @Test
    public void addMany_Iterable_PassWithEmpty() {
        classUnderTest.addMany(Arrays.<String>asList());
        verify(mockList, never()).add(anyString());
    }

    @Test
    public void addMany_Iterable_PassWithNonEmpty() {
        classUnderTest.addMany(Arrays.asList("abc", "def"));
        verify(mockList).add("abc");
        verify(mockList).add("def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractListBuilder.addMany(Iterator)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void addMany_Iterator_FailWithNull() {
        classUnderTest.addMany((Iterator<String>) null);
    }

    @Test
    public void addMany_Iterator_PassWithEmpty() {
        classUnderTest.addMany(Arrays.<String>asList().iterator());
        verify(mockList, never()).add(anyString());
    }

    @Test
    public void addMany_Iterator_PassWithNonEmpty() {
        classUnderTest.addMany(Arrays.asList("abc", "def").iterator());
        verify(mockList).add("abc");
        verify(mockList).add("def");
    }
}
