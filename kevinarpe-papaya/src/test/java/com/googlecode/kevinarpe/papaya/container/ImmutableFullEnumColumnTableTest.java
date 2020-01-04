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
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableFullEnumColumnTableTest {

    private enum _Empty {}
    private enum _Enum { A, B }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumColumnTable.of
    //

    @Test(expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "\\QMap argument 'listMap' is empty\\E")
    public void of_FailWhenEnumIsEmpty() {

        ImmutableFullEnumColumnTable.of(
            ImmutableFullEnumMap.<_Empty, ImmutableList<String>>builder(_Empty.class)
                .buildWhere(IsEmptyEnumAllowed.YES));
    }

    @Test
    public void of_PassWhenEmpty() {

        ImmutableFullEnumColumnTable.of(
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of()));
    }

    @Test
    public void of_PassWhenNotEmpty() {

        ImmutableFullEnumColumnTable.of(
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of("abc")));
    }

    @Test
    public void of_PassWhenNotEmpty2() {

        ImmutableFullEnumColumnTable.of(
            ImmutableFullEnumMap.<_Enum, ImmutableList<String>>builder(_Enum.class)
                .put(_Enum.A, ImmutableList.of("abc"))
                .put(_Enum.B, ImmutableList.of("def"))
                .build());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void of_FailWhenSubListSizeNotEqual() {

        ImmutableFullEnumColumnTable.of(
            ImmutableFullEnumMap.<_Enum, ImmutableList<String>>builder(_Enum.class)
                .put(_Enum.A, ImmutableList.of("abc"))
                .put(_Enum.B, ImmutableList.of("def", "ghi"))
                .build());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void of_FailWhenSubListSizeNotEqual2() {

        ImmutableFullEnumColumnTable.of(
            ImmutableFullEnumMap.<_Enum, ImmutableList<String>>builder(_Enum.class)
                .put(_Enum.A, ImmutableList.of("abc", "ghi"))
                .put(_Enum.B, ImmutableList.of("def"))
                .build());
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumColumnTable.copyOf
    //

    @Test(expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "\\QMap argument 'listMap' is empty\\E")
    public void copyOf_FailWhenMapIsEmpty() {

        ImmutableFullEnumColumnTable.copyOf(new HashMap<_Empty, ArrayList<String>>());
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "\\QMap argument 'listMap' is empty\\E")
    public void copyOf_FailWhenEnumIsEmpty() {

        ImmutableFullEnumColumnTable.copyOf(
            ImmutableFullEnumMap.<_Empty, ImmutableList<String>>builder(_Empty.class)
                .buildWhere(IsEmptyEnumAllowed.YES));
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "\\QMissing 1 keys: [B]\\E")
    public void copyOf_FailWhenMissingKey() {

        final HashMap<_Enum, ArrayList<String>> map = new HashMap<>();
        map.put(_Enum.A, new ArrayList<>());

        ImmutableFullEnumColumnTable.copyOf(map);
    }

    @Test
    public void copyOf_Pass() {

        final HashMap<_Enum, ArrayList<String>> map = new HashMap<>();
        map.put(_Enum.A, new ArrayList<>(Arrays.asList("abc", "def")));
        map.put(_Enum.B, new ArrayList<>(Arrays.asList("ghi", "jkl")));

        ImmutableFullEnumColumnTable.copyOf(map);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenSubListSizeNotEqual() {

        final HashMap<_Enum, ArrayList<String>> map = new HashMap<>();
        map.put(_Enum.A, new ArrayList<>(Arrays.asList("abc", "def")));
        map.put(_Enum.B, new ArrayList<>(Collections.singletonList("ghi")));

        ImmutableFullEnumColumnTable.copyOf(map);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenSubListSizeNotEqual2() {

        final HashMap<_Enum, ArrayList<String>> map = new HashMap<>();
        map.put(_Enum.A, new ArrayList<>(Collections.singletonList("abc")));
        map.put(_Enum.B, new ArrayList<>(Arrays.asList("ghi", "jkl")));

        ImmutableFullEnumColumnTable.copyOf(map);
    }
}
