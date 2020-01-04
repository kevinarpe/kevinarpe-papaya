package com.googlecode.kevinarpe.papaya.container;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableColumnTableBuilderTest {

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTableBuilder.ctor(Set)
    //

    @Test
    public void ctorSet_PassWhenEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def")).build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctorSet_FailWhenEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of());
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTableBuilder.ctor(List)
    //

    @Test
    public void ctorList_PassWhenEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableList.of("abc", "def")).build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctorList_FailWhenEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableList.of());
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "\\QFound 1 duplicate keys: [abc]\\E")
    public void ctorList_FailWhenDuplicates() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableList.of("abc", "def", "abc"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "\\QFound 3 duplicate keys: [abc, abc, def]\\E")
    public void ctorList_FailWhenDuplicates2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableList.of("abc", "def", "abc", "abc", "def"));
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTableBuilder.put
    //

    @Test
    public void put_PassWhenNotEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("abc", "123", "def", "234"))
            .build();
    }

    @Test
    public void put_PassWhenNotEmptyTable2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234", "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_FailWhenMissingKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_FailWhenExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234", "abc", "123", "ghi", "387"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_FailWhenMissingKeyAndExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234", "ghi", "387"));
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTableBuilder.putAll
    //

    @Test
    public void putAll_Pass() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "def", "234",
                "abc", "123"))
            .build();
    }

    @Test
    public void putAll_Pass2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "def", "234",
                "def", "2345",
                "abc", "123",
                "abc", "1234"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenMissingKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "ghi", "567",
                "def", "234",
                "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenMissingKeyAndExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "ghi", "567",
                "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenColumnSizeNotMatch() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "abc", "123",
                "def", "234",
                "def", "2345"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenColumnSizeNotMatch2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "abc", "123",
                "abc", "1234",
                "def", "234"));
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTableBuilder.allOf
    //

    private enum _Enum { A, B }

    @Test
    public void allOf_PassWhenEmpty() {

        ImmutableColumnTableBuilder.allOf(_Enum.class).build();
    }

    @Test
    public void allOf_PassWhenNotEmpty() {

        ImmutableColumnTableBuilder.allOf(_Enum.class)
            .put(ImmutableMap.of(_Enum.A, "abc", _Enum.B, "def"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void allOf_FailWhenMissingKey() {

        ImmutableColumnTableBuilder.allOf(_Enum.class)
            .put(ImmutableMap.of(_Enum.A, "abc"))
            .build();
    }
}
