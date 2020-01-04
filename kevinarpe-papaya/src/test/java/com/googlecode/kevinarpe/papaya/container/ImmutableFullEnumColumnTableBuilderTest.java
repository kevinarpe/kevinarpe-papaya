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

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableFullEnumColumnTableBuilderTest {

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumColumnTableBuilder.ctor
    //

    private enum _Enum { A, B }

    @Test
    public void ctor_passWhenEmptyTable() {

        new ImmutableFullEnumColumnTableBuilder<>(_Enum.class).build();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumColumnTableBuilder.put
    //

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

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumColumnTableBuilder.putAll
    //

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
