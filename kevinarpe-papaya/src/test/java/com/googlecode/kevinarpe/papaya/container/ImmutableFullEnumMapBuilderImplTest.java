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

import com.google.common.collect.ImmutableMap;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableFullEnumMapBuilderImplTest {

    private enum _EmptyEnum {}

    private enum _Enum { _1, _2 }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapBuilderImpl.put()
    //

    @Test
    public void put_Pass() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def");

        final ImmutableFullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(2, map.size());
    }

    @Test
    public void put_PassWhenSamePairTwice() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def").put(_Enum._1, "abc");

        final ImmutableFullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(2, map.size());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_FailWhenKeyMappedToDiffValue() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._1, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapBuilderImpl.putAll()
    //

    @Test
    public void putAll_Pass() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def"));

        final ImmutableFullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(2, map.size());
    }

    @Test
    public void putAll_Pass2() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.putAll(ImmutableMap.of());
        classUnderTest.putAll(ImmutableMap.of(_Enum._2, "def"));
        classUnderTest.putAll(ImmutableMap.of());
        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "abc"));
        classUnderTest.putAll(ImmutableMap.of());

        final ImmutableFullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(2, map.size());
    }

    @Test
    public void putAll_PassWhenEmptyEnum() {

        final ImmutableFullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_EmptyEnum.class);

        classUnderTest.putAll(ImmutableMap.of());

        final ImmutableFullEnumMap<_EmptyEnum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.YES);
        Assert.assertTrue(map.isEmpty());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenKeyMappedToDiffValue() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "abc"));
        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapBuilderImpl.build()
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void build_FailWhenEmptyEnum() {

        final ImmutableFullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_EmptyEnum.class);

        classUnderTest.build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_FailWhenNonEmptyEnum_And_NotFull() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc");

        classUnderTest.build();
    }

    @Test
    public void testBuild_PassWhenNonEmptyEnum() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def");

        final ImmutableFullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(2, map.size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMapBuilderImpl.buildWhere()
    //

    @Test
    public void buildWhere_PassWhenEmptyEnum() {

        final ImmutableFullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_EmptyEnum.class);

        final ImmutableFullEnumMap<_EmptyEnum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.YES);
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void buildWhere_PassWhenNonEmptyEnum() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def");

        final ImmutableFullEnumMap<_Enum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.NO);
        Assert.assertEquals(2, map.size());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void buildWhere_FailWhenNonEmptyEnum_And_NotFull() {

        final ImmutableFullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new ImmutableFullEnumMapBuilderImpl<>(_Enum.class);

        classUnderTest.put(_Enum._1, "abc");
        classUnderTest.buildWhere(IsEmptyEnumAllowed.NO);
    }
}
