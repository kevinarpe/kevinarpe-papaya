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

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Note: Full coverage is achieved in conjunction with ImmutableFullEnumMapBuilderImplTest.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableFullEnumMapTest {

    private enum _EmptyEnum {}

    private enum _Enum { _1, _2 }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapImpl.ctor()
    //

    @Test
    public void ctor_Pass() {

        final ImmutableFullEnumMap<_Enum, String> map =
            new ImmutableFullEnumMap<>(
                _Enum.class,
                ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"),
                ImmutableFullEnumMap.IsEmptyEnumAllowed.NO);

        Assert.assertEquals(map.get(_Enum._1), "def");
        Assert.assertEquals(map.get(_Enum._2), "abc");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenMissingKey() {

        new ImmutableFullEnumMap<_Enum, String>(
            _Enum.class, ImmutableMap.of(_Enum._2, "blah"), ImmutableFullEnumMap.IsEmptyEnumAllowed.NO);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenEmptyEnum() {

        new ImmutableFullEnumMap<_EmptyEnum, String>(
            _EmptyEnum.class, ImmutableMap.of(), ImmutableFullEnumMap.IsEmptyEnumAllowed.NO);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapImpl.ofKeys()
    //

    @Test
    public void ofKeys_Pass() {

        final ImmutableMap<_Enum, String> map = ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def");

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.<_Enum, String>ofKeys(_Enum.class, e -> map.get(e));

        Assert.assertEquals(map2, map);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapImpl.ofValues()
    //

    @Test
    public void ofValues_Pass() {

        final ImmutableBiMap<String, _Enum> map = ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.<_Enum, String>ofValues(_Enum.class, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map2, map.inverse());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapImpl.builder()
    //

    @Test
    public void builder_PassWhenEmpty() {

        ImmutableFullEnumMap.builder(_EmptyEnum.class);
    }

    @Test
    public void builder_PassWhenNonEmtpy() {

        ImmutableFullEnumMap.builder(_Enum.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapImpl.copyOf()
    //

    @Test
    public void copyOf_PassWhenNonEmpty() {

        ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "123", _Enum._1, "def"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenEmpty() {

        ImmutableFullEnumMap.copyOf(_EmptyEnum.class, ImmutableMap.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapImpl.getEnumClass()
    //

    @Test
    public void getEnumClass_PassWhenNonEmpty() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "123", _Enum._1, "def"));

        Assert.assertEquals(map.getEnumClass(), _Enum.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getEnumClass_FailWhenEmpty() {

        final ImmutableFullEnumMap<_EmptyEnum, String> map =
            ImmutableFullEnumMap.copyOf(_EmptyEnum.class, ImmutableMap.of());

        Assert.assertEquals(map.getEnumClass(), _EmptyEnum.class);
    }
}
