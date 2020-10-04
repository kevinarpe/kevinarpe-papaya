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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FullEnumMapBuilderImplTest {

    private enum _EmptyEnum {}

    private enum _Enum { _1, _2 }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.getEnumClass()
    //

    @Test
    public void getEnumClass_Pass() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        Assert.assertSame(classUnderTest.getEnumClass(), _Enum.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.getEnumClass()
    //

    @Test
    public void areNullValuesAllowed_Pass() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        Assert.assertSame(classUnderTest.areNullValuesAllowed(), AreNullValuesAllowed.NO);

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest2 =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        Assert.assertSame(classUnderTest2.areNullValuesAllowed(), AreNullValuesAllowed.YES);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.getReadOnlyMap()
    //

    @Test
    public void getReadOnlyMap_PassWhenNoNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        final Map<_Enum, String> readOnlyMap = classUnderTest.getReadOnlyMap();

        Assert.assertTrue(readOnlyMap.isEmpty());

        classUnderTest.put(_Enum._1, "abc");

        Assert.assertEquals(readOnlyMap, ImmutableMap.of(_Enum._1, "abc"));

        Assert.assertThrows(() -> readOnlyMap.put(_Enum._2, "def"));
    }

    @Test
    public void getReadOnlyMap_PassWhenNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        final Map<_Enum, String> readOnlyMap = classUnderTest.getReadOnlyMap();

        Assert.assertTrue(readOnlyMap.isEmpty());

        classUnderTest.put(_Enum._1, (String) null);

        Assert.assertEquals(readOnlyMap, FullEnumMapTest.createHashMap(_Enum._1, (String) null));

        Assert.assertThrows(() -> readOnlyMap.put(_Enum._2, "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.put()
    //

    @Test
    public void put_PassWhenNoNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        Assert.assertThrows(NullPointerException.class, () -> classUnderTest.put(_Enum._1, (String) null));

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def");

        final FullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(map, ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def"));
    }

    @Test
    public void put_PassWhenNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, (String) null).put(_Enum._2, "def");

        final FullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(_Enum._1, (String) null, _Enum._2, "def"));
    }

    @Test
    public void put_PassWhenNoNullsAndSamePairTwice() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def").put(_Enum._1, "abc");

        final FullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(map, ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def"));
    }

    @Test
    public void put_PassWhenNullsAndSamePairTwice() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, (String) null).put(_Enum._2, "def").put(_Enum._1, (String) null);

        final FullEnumMap<_Enum, String> map = classUnderTest.build();

        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(_Enum._1, (String) null, _Enum._2, "def"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_FailWhenNoNullsAndKeyMappedToDiffValue() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._1, "def");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void put_FailWhenNullsAndKeyMappedToDiffValue() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, (String) null).put(_Enum._1, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.putAll()
    //

    @Test
    public void putAll_PassWhenNoNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        final HashMap<_Enum, String> badInputMap =
            FullEnumMapTest.createHashMap(_Enum._1, (String) null, _Enum._2, "def");

        Assert.assertThrows(NullPointerException.class, () -> classUnderTest.putAll(badInputMap));

        final ImmutableMap<_Enum, String> inputMap = ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def");
        classUnderTest.putAll(inputMap);
        // Intentional: Test we can add the *same* map again.
        classUnderTest.putAll(inputMap);

        final FullEnumMap<_Enum, String> map = classUnderTest.build();
        Assert.assertEquals(map, inputMap);
    }

    @Test
    public void putAll_PassWhenNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        final HashMap<_Enum, String> inputMap =
            FullEnumMapTest.createHashMap(_Enum._1, (String) null, _Enum._2, "def");

        classUnderTest.putAll(inputMap);
        // Intentional: Test we can add the *same* map again.
        classUnderTest.putAll(inputMap);

        final FullEnumMap<_Enum, String> map = classUnderTest.build();
        Assert.assertEquals(map, inputMap);
    }

    @Test
    public void putAll_Pass2WhenNoNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.putAll(ImmutableMap.of());
        classUnderTest.putAll(ImmutableMap.of(_Enum._2, "def"));
        classUnderTest.putAll(ImmutableMap.of());
        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "abc"));
        classUnderTest.putAll(ImmutableMap.of());

        final FullEnumMap<_Enum, String> map = classUnderTest.build();
        Assert.assertEquals(map, ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def"));
    }

    @Test
    public void putAll_Pass2WhenNulls() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.putAll(ImmutableMap.of());
        classUnderTest.putAll(ImmutableMap.of(_Enum._2, "def"));
        classUnderTest.putAll(ImmutableMap.of());
        classUnderTest.putAll(
            FullEnumMapTest.createHashMap(_Enum._1, (String) null));
        classUnderTest.putAll(ImmutableMap.of());

        final FullEnumMap<_Enum, String> map = classUnderTest.build();
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(_Enum._1, (String) null, _Enum._2, "def"));
    }

    @Test
    public void putAll_PassWhenNoNullsAndEmptyEnum() {

        final FullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_EmptyEnum.class, AreNullValuesAllowed.NO);

        classUnderTest.putAll(ImmutableMap.of());

        final FullEnumMap<_EmptyEnum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.YES);
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void putAll_PassWhenNullsAndEmptyEnum() {

        final FullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_EmptyEnum.class, AreNullValuesAllowed.YES);

        classUnderTest.putAll(ImmutableMap.of());

        final FullEnumMap<_EmptyEnum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.YES);
        Assert.assertTrue(map.isEmpty());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenNoNullsAndKeyMappedToDiffValue() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "abc"));
        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "def"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putAll_FailWhenNullsAndKeyMappedToDiffValue() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.putAll(
            FullEnumMapTest.createHashMap(_Enum._1, (String) null));

        classUnderTest.putAll(ImmutableMap.of(_Enum._1, "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.build()
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void build_FailWhenEmptyEnum() {

        final FullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_EmptyEnum.class, AreNullValuesAllowed.NO);

        classUnderTest.build();

        final FullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest2 =
            new FullEnumMapBuilderImpl<>(_EmptyEnum.class, AreNullValuesAllowed.YES);

        classUnderTest2.build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_FailWhenNoNullsAndNonEmptyEnum_And_NotFull() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.put(_Enum._1, "abc");

        classUnderTest.build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuild_FailWhenNullsAndNonEmptyEnum_And_NotFull() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, (String) null);

        classUnderTest.build();
    }

    @Test
    public void testBuild_PassWhenNoNullsAndNonEmptyEnum() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def");

        final FullEnumMap<_Enum, String> map = classUnderTest.build();
        Assert.assertEquals(map, ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def"));
    }

    @Test
    public void testBuild_PassWhenNullsAndNonEmptyEnum() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, (String) null).put(_Enum._2, "def");

        final FullEnumMap<_Enum, String> map = classUnderTest.build();
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(_Enum._1, (String) null, _Enum._2, "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapBuilderImpl.buildWhere()
    //

    @Test
    public void buildWhere_PassWhenNoNullsAndEmptyEnum() {

        final FullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_EmptyEnum.class, AreNullValuesAllowed.NO);

        final FullEnumMap<_EmptyEnum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.YES);
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void buildWhere_PassWhenNullsAndEmptyEnum() {

        final FullEnumMapBuilderImpl<_EmptyEnum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_EmptyEnum.class, AreNullValuesAllowed.YES);

        final FullEnumMap<_EmptyEnum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.YES);
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void buildWhere_PassWhenNoNullsAndNonEmptyEnum() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, "def");

        final FullEnumMap<_Enum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.NO);
        Assert.assertEquals(map, ImmutableMap.of(_Enum._1, "abc", _Enum._2, "def"));
    }

    @Test
    public void buildWhere_PassWhenNullsAndNonEmptyEnum() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, "abc").put(_Enum._2, (String) null);

        final FullEnumMap<_Enum, String> map = classUnderTest.buildWhere(IsEmptyEnumAllowed.NO);
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(_Enum._1, "abc", _Enum._2, (String) null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void buildWhere_FailWhenNoNullsAndNonEmptyEnum_And_NotFull() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.NO);

        classUnderTest.put(_Enum._1, "abc");
        classUnderTest.buildWhere(IsEmptyEnumAllowed.NO);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void buildWhere_FailWhenNullsAndNonEmptyEnum_And_NotFull() {

        final FullEnumMapBuilderImpl<_Enum, String> classUnderTest =
            new FullEnumMapBuilderImpl<>(_Enum.class, AreNullValuesAllowed.YES);

        classUnderTest.put(_Enum._1, (String) null);
        classUnderTest.buildWhere(IsEmptyEnumAllowed.NO);
    }
}
