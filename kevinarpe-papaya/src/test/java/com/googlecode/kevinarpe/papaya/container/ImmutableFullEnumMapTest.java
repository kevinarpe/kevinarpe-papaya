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

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Note: Full coverage is achieved in conjunction with ImmutableFullEnumMapBuilderImplTest.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableFullEnumMapTest {

    private enum _EmptyEnum {}

    private enum _Enum { _1, _2 }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.ctor()
    //

    @Test
    public void ctor_Pass() {

        final ImmutableFullEnumMap<_Enum, String> map =
            new ImmutableFullEnumMap<>(
                _Enum.class,
                ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"),
                IsEmptyEnumAllowed.NO);

        Assert.assertEquals(map.get(_Enum._1), "def");
        Assert.assertEquals(map.get(_Enum._2), "abc");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenMissingKey() {

        new ImmutableFullEnumMap<>(_Enum.class, ImmutableMap.of(_Enum._2, "blah"), IsEmptyEnumAllowed.NO);
    }

    @Test
    public void ctor_PassWhenEmptyEnum() {

        new ImmutableFullEnumMap<_EmptyEnum, String>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.YES);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenEmptyEnum() {

        new ImmutableFullEnumMap<_EmptyEnum, String>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.NO);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.ofKeys()
    //

    @Test
    public void ofKeys_Pass() {

        final ImmutableMap<_Enum, String> map = ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def");

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.ofKeys(_Enum.class, e -> map.get(e));

        Assert.assertEquals(map2, map);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.ofKeys2()
    //

    @Test
    public void ofKeys2_Pass()
    throws Exception {

        final ImmutableMap<_Enum, String> map = ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def");

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.ofKeys2(_Enum.class, e -> map.get(e));

        Assert.assertEquals(map2, map);
    }

    @Test(expectedExceptions = IOException.class)
    public void ofKeys2_FailWithIOException()
    throws Exception {

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.ofKeys2(_Enum.class, e -> { throw new IOException(); });

        throw new Exception("Unreachable code");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.ofValues()
    //

    @Test
    public void ofValues_Pass() {

        final ImmutableBiMap<String, _Enum> map = ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.ofValues(_Enum.class, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map2, map.inverse());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.ofValues2()
    //

    @Test
    public void ofValues2_Pass()
    throws Exception {

        final ImmutableBiMap<String, _Enum> map = ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.ofValues2(_Enum.class, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map2, map.inverse());
    }

    @Test(expectedExceptions = IOException.class)
    public void ofValues2_FailWithIOException()
    throws Exception {

        final ImmutableBiMap<String, _Enum> map = ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        final ImmutableFullEnumMap<_Enum, String> map2 =
            ImmutableFullEnumMap.<_Enum, String>ofValues2(_Enum.class, map.keySet(), v -> { throw new IOException(); });

        throw new Exception("Unreachable code");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.builder()
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
    // ImmutableFullEnumMap.copyOf()
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
    // ImmutableFullEnumMap.getEnumClass()
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.getByEnum()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnum_FailWhenEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_EmptyEnum, String> map =
            new ImmutableFullEnumMap<>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        map.getByEnum(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnum_FailWhenNonEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        map.getByEnum(null);
    }

    @Test
    public void getByEnum_PassWhenNonEmpty() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map.getByEnum(_Enum._1), "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.getByEnumOrThrow()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnumOrThrow_FailWhenEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_EmptyEnum, String> map =
            new ImmutableFullEnumMap<>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        map.getByEnumOrThrow(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnumOrThrow_FailWhenNonEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        map.getByEnumOrThrow(null);
    }

    @Test
    public void getByEnumOrThrow_PassWhenNonEmpty() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        final String value = map.getByEnumOrThrow(_Enum._1);
        Assert.assertEquals(value, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.getByEnumOrDefault()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnumOrDefault_FailWhenEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_EmptyEnum, String> map =
            new ImmutableFullEnumMap<>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        map.getByEnumOrDefault(null, "xyz");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnumOrDefault_FailWhenNonEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        map.getByEnumOrDefault(null, "xyz");
    }

    @Test
    public void getByEnumOrDefault_PassWhenNonEmpty() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map.getByEnumOrDefault(_Enum._1, "xyz"), "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.getOrThrow()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void getOrThrow_FailWhenEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_EmptyEnum, String> map =
            new ImmutableFullEnumMap<>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        map.getOrThrow(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getOrThrow_FailWhenEmptyAndKeyIsNonNull() {

        final ImmutableFullEnumMap<_EmptyEnum, String> map =
            new ImmutableFullEnumMap<>(_EmptyEnum.class, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        final Object key = new Object();
        map.getOrThrow(key);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getOrThrow_FailWhenNonEmptyAndKeyIsNull() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        map.getOrThrow(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getOrThrow_FailWhenNonEmptyAndKeyIsNonNull() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        final Object key = new Object();
        map.getOrThrow(key);
    }

    @Test
    public void getOrThrow_PassWhenNonEmpty() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        final String value = map.getOrThrow(_Enum._1);
        Assert.assertEquals(value, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.put(Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void put_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.put(_Enum._1, "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.remove(Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.remove(_Enum._1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.putAll(Map)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void putAll_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.putAll(ImmutableMap.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.clear()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void clear_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.replaceAll()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void replaceAll_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.replaceAll((any, any2) -> "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.putIfAbsent(Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void putIfAbsent_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.putIfAbsent(_Enum._1, "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.remove(Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeKeyValue_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.remove(_Enum._1, "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.replace(Object, Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void replace_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.replace(_Enum._1, "ghi", "xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.replace(Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void replace2_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.replace(_Enum._1, "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.computeIfAbsent(Object, Function)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void computeIfAbsent_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.computeIfAbsent(_Enum._1, any -> "xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.computeIfPresent(Object, BiFunction)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void computeIfPresent_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.computeIfPresent(_Enum._1, (any, any2) -> "xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.compute(Object, BiFunction)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void compute_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.compute(_Enum._1, (any, any2) -> "xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableFullEnumMap.merge(Object, Object, BiFunction)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void merge_AlwaysFail() {

        final ImmutableFullEnumMap<_Enum, String> map =
            ImmutableFullEnumMap.copyOf(_Enum.class, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        map.merge(_Enum._1, "uvw", (any, any2) -> "xyz");
    }
}
