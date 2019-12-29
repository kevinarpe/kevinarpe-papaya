package com.googlecode.kevinarpe.papaya.container;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableColumnTableBuilderTest {

    public static class ImmutableFullEnumColumnTableBuilderTest {

        private enum _Enum { A, B }

        @Test
        public void ctor_passWhenEmptyTable() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class).build();
        }

        @Test
        public void put_passWhenNotEmptyTable() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .put(ImmutableMap.of(_Enum.A, "123", _Enum.B, "234"))
                .build();
        }

        @Test
        public void put_passWhenNotEmptyTable2() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .put(ImmutableMap.of(_Enum.B, "234", _Enum.A, "123"))
                .build();
        }

        @Test(expectedExceptions = IllegalArgumentException.class)
        public void put_failWhenMissingKey() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .put(ImmutableMap.of(_Enum.B, "234"));
        }

        @Test
        public void putAll_pass() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .putAll(ImmutableListMultimap.of(
                    _Enum.A, "123",
                    _Enum.B, "234"))
                .build();
        }

        @Test
        public void putAll_pass2() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .putAll(ImmutableListMultimap.of(
                    _Enum.A, "123",
                    _Enum.A, "1234",
                    _Enum.B, "234",
                    _Enum.B, "2345"))
                .build();
        }

        @Test(expectedExceptions = IllegalArgumentException.class)
        public void putAll_failWhenColumnSizeNotMatch() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .putAll(ImmutableListMultimap.of(
                    _Enum.A, "123",
                    _Enum.A, "1234",
                    _Enum.B, "234"))
                .build();
        }

        @Test(expectedExceptions = IllegalArgumentException.class)
        public void putAll_failWhenColumnSizeNotMatch2() {

            new ImmutableFullEnumColumnTableBuilder<>(_Enum.class)
                .putAll(ImmutableListMultimap.of(
                    _Enum.A, "123",
                    _Enum.B, "234",
                    _Enum.B, "2345"))
                .build();
        }
    }

    @Test
    public void ctor_passWhenEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def")).build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_failWhenEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of());
    }

    @Test
    public void put_passWhenNotEmptyTable() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("abc", "123", "def", "234"))
            .build();
    }

    @Test
    public void put_passWhenNotEmptyTable2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234", "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_failWhenMissingKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_failWhenExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234", "abc", "123", "ghi", "387"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_failWhenMissingKeyAndExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .put(ImmutableMap.of("def", "234", "ghi", "387"));
    }

    @Test
    public void putAll_pass() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "def", "234",
                "abc", "123"))
            .build();
    }

    @Test
    public void putAll_pass2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "def", "234",
                "def", "2345",
                "abc", "123",
                "abc", "1234"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_failWhenMissingKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_failWhenExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "ghi", "567",
                "def", "234",
                "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_failWhenMissingKeyAndExtraKey() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "ghi", "567",
                "abc", "123"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_failWhenColumnSizeNotMatch() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "abc", "123",
                "def", "234",
                "def", "2345"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_failWhenColumnSizeNotMatch2() {

        new ImmutableColumnTableBuilder<String, String>(ImmutableSet.of("abc", "def"))
            .putAll(ImmutableListMultimap.of(
                "abc", "123",
                "abc", "1234",
                "def", "234"));
    }

    private enum _Enum { A, B }

    @Test
    public void createFullEnum_passWhenEmpty() {

        ImmutableColumnTableBuilder.createFullEnum(_Enum.class).build();
    }

    @Test
    public void createFullEnum_passWhenNotEmpty() {

        ImmutableColumnTableBuilder.createFullEnum(_Enum.class)
            .put(ImmutableMap.of(_Enum.A, "abc", _Enum.B, "def"))
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createFullEnum_failWhenMissingKey() {

        ImmutableColumnTableBuilder.createFullEnum(_Enum.class)
            .put(ImmutableMap.of(_Enum.A, "abc"))
            .build();
    }
}
