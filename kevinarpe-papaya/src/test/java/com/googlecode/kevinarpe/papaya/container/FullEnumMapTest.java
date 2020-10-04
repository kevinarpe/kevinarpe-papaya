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
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FullEnumMapTest {

    private enum _EmptyEnum {}

    private enum _Enum { _1, _2 }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMapImpl.ctor()
    //

    @Test
    public void ctor_PassWhenNullValuesAllowed() {

        final FullEnumMap<_Enum, String> map =
            new FullEnumMap<>(
                _Enum.class,
                AreNullValuesAllowed.NO,
                ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"),
                IsEmptyEnumAllowed.NO);

        Assert.assertEquals(map.get(_Enum._1), "def");
        Assert.assertEquals(map.get(_Enum._2), "abc");
    }

    @Test
    public void ctor_PassWhenNullValuesNotAllowed() {

        final FullEnumMap<_Enum, String> map =
            new FullEnumMap<>(
                _Enum.class,
                AreNullValuesAllowed.YES,
                createHashMap(_Enum._2, "abc", _Enum._1, (String) null),
                IsEmptyEnumAllowed.NO);

        Assert.assertEquals(map.get(_Enum._1), null);
        Assert.assertEquals(map.get(_Enum._2), "abc");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenMissingKeyAndNullValuesAllowed() {

        new FullEnumMap<>(
            _Enum.class,
            AreNullValuesAllowed.NO,
            ImmutableMap.of(_Enum._2, "blah"),
            IsEmptyEnumAllowed.NO);
    }

    @Test
    public void ctor_PassWhenMissingKeyAndNullValuesNotAllowed() {

        new FullEnumMap<>(
            _Enum.class,
            AreNullValuesAllowed.YES,
            createHashMap(_Enum._2, (String) null, _Enum._1, "def"),
            IsEmptyEnumAllowed.NO);
    }

    @Test
    public void ctor_PassWhenEmptyEnum() {

        new FullEnumMap<_EmptyEnum, String>(
            _EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        new FullEnumMap<_EmptyEnum, String>(
            _EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.YES);
    }

    @Test
    public void ctor_FailWhenEmptyEnum() {

        Assert.assertThrows(
            () ->
                new FullEnumMap<_EmptyEnum, String>(
                    _EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.NO));

        Assert.assertThrows(
            () ->
                new FullEnumMap<_EmptyEnum, String>(
                    _EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.NO));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.ofKeys()
    //

    @Test
    public void ofKeys_PassWhenNoNullValues() {

        final ImmutableMap<_Enum, String> map = ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def");

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.ofKeys(_Enum.class, AreNullValuesAllowed.NO, e -> map.get(e));

        Assert.assertEquals(map2, map);

        final FullEnumMap<_Enum, String> map3 =
            FullEnumMap.ofKeys(_Enum.class, AreNullValuesAllowed.YES, e -> map.get(e));

        Assert.assertEquals(map3, map);
    }

    @Test
    public void ofKeys_PassWhenNullValues() {

        final HashMap<_Enum, String> map = createHashMap(_Enum._2, "abc", _Enum._1, (String) null);

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.ofKeys(_Enum.class, AreNullValuesAllowed.YES, e -> map.get(e));

        Assert.assertEquals(map2, map);

        Assert.assertThrows(
            () -> FullEnumMap.ofKeys(_Enum.class, AreNullValuesAllowed.NO, e -> map.get(e)));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.ofKeys2()
    //

    @Test
    public void ofKeys2_Pass()
    throws Exception {

        final ImmutableMap<_Enum, String> map = ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def");

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.ofKeys2(_Enum.class, AreNullValuesAllowed.NO, e -> map.get(e));

        Assert.assertEquals(map2, map);

        final FullEnumMap<_Enum, String> map3 =
            FullEnumMap.ofKeys2(_Enum.class, AreNullValuesAllowed.YES, e -> map.get(e));

        Assert.assertEquals(map3, map);
    }

    @Test
    public void ofKeys2_FailWithIOException() {

        Assert.assertThrows(
            IOException.class,
            () -> FullEnumMap.ofKeys2(_Enum.class, AreNullValuesAllowed.NO, e -> { throw new IOException(); }));

        Assert.assertThrows(
            IOException.class,
            () -> FullEnumMap.ofKeys2(_Enum.class, AreNullValuesAllowed.YES, e -> { throw new IOException(); }));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.ofValues()
    //

    @Test
    public void ofValues_Pass() {

        final ImmutableBiMap<String, _Enum> map =
            ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.ofValues(_Enum.class, AreNullValuesAllowed.NO, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map2, map.inverse());

        final FullEnumMap<_Enum, String> map3 =
            FullEnumMap.ofValues(_Enum.class, AreNullValuesAllowed.YES, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map3, map.inverse());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.ofValues2()
    //

    @Test
    public void ofValues2_Pass()
    throws Exception {

        final ImmutableBiMap<String, _Enum> map =
            ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.ofValues2(_Enum.class, AreNullValuesAllowed.NO, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map2, map.inverse());

        final FullEnumMap<_Enum, String> map3 =
            FullEnumMap.ofValues2(_Enum.class, AreNullValuesAllowed.YES, map.keySet(), v -> map.get(v));

        Assert.assertEquals(map3, map.inverse());
    }

    @Test
    public void ofValues2_FailWithIOException() {

        final ImmutableBiMap<String, _Enum> map =
            ImmutableBiMap.of("abc", _Enum._2, "def", _Enum._1);

        Assert.assertThrows(
            IOException.class,
            () -> FullEnumMap.ofValues2(
                _Enum.class, AreNullValuesAllowed.NO, map.keySet(), v -> { throw new IOException(); }));

        Assert.assertThrows(
            IOException.class,
            () -> FullEnumMap.ofValues2(
                _Enum.class, AreNullValuesAllowed.YES, map.keySet(), v -> { throw new IOException(); }));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.builder()
    //

    @Test
    public void builder_PassWhenEmpty() {

        FullEnumMap.builder(_EmptyEnum.class, AreNullValuesAllowed.NO);
        FullEnumMap.builder(_EmptyEnum.class, AreNullValuesAllowed.YES);
    }

    @Test
    public void builder_PassWhenNonEmtpy() {

        FullEnumMap.builder(_Enum.class, AreNullValuesAllowed.NO);
        FullEnumMap.builder(_Enum.class, AreNullValuesAllowed.YES);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.copyOf()
    //

    @Test
    public void copyOf_PassWhenNonEmptyAndNoNulls() {

        FullEnumMap.copyOf(
            _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "123", _Enum._1, "def"));

        FullEnumMap.copyOf(
            _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "123", _Enum._1, "def"));
    }

    @Test
    public void copyOf_PassWhenNonEmptyAndNulls() {

        final HashMap<_Enum, String> map = createHashMap(_Enum._2, "abc", _Enum._1, (String) null);

        Assert.assertThrows(() -> FullEnumMap.copyOf(_Enum.class, AreNullValuesAllowed.NO, map));

        FullEnumMap.copyOf(_Enum.class, AreNullValuesAllowed.YES, map);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenEmpty() {

        FullEnumMap.copyOf(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of());

        FullEnumMap.copyOf(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.getEnumClass()
    //

    @Test
    public void getEnumClass_PassWhenNonEmptyAndNoNulls() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "123", _Enum._1, "def"));

        Assert.assertEquals(map.getEnumClass(), _Enum.class);

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "123", _Enum._1, "def"));

        Assert.assertEquals(map2.getEnumClass(), _Enum.class);
    }

    @Test
    public void getEnumClass_PassWhenNonEmptyAndNulls() {

        final HashMap<_Enum, String> map = createHashMap(_Enum._2, "abc", _Enum._1, (String) null);

        final FullEnumMap<_Enum, String> map2 = FullEnumMap.copyOf(_Enum.class, AreNullValuesAllowed.YES, map);

        Assert.assertEquals(map2.getEnumClass(), _Enum.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getEnumClass_FailWhenEmpty() {

        final FullEnumMap<_EmptyEnum, String> map =
            FullEnumMap.copyOf(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of());

        Assert.assertEquals(map.getEnumClass(), _EmptyEnum.class);

        final FullEnumMap<_EmptyEnum, String> map2 =
            FullEnumMap.copyOf(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of());

        Assert.assertEquals(map2.getEnumClass(), _EmptyEnum.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.getByEnum()
    //

    @Test
    public void getByEnum_FailWhenEmptyAndKeyIsNull() {

        final FullEnumMap<_EmptyEnum, String> map =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class, () -> map.getByEnum(null));

        final FullEnumMap<_EmptyEnum, String> map2 =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class, () -> map2.getByEnum(null));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getByEnum_FailWhenNonEmptyAndKeyIsNull() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        map.getByEnum(null);

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        map2.getByEnum(null);
    }

    @Test
    public void getByEnum_PassWhenNonEmpty() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map.getByEnum(_Enum._1), "def");

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map2.getByEnum(_Enum._1), "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.getByEnumOrThrow()
    //

    @Test
    public void getByEnumOrThrow_FailWhenEmptyAndKeyIsNull() {

        final FullEnumMap<_EmptyEnum, String> map =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class, () -> map.getByEnumOrThrow((_EmptyEnum) null));

        final FullEnumMap<_EmptyEnum, String> map2 =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class, () -> map2.getByEnumOrThrow((_EmptyEnum) null));
    }

    @Test
    public void getByEnumOrThrow_FailWhenNonEmptyAndKeyIsNull() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(NullPointerException.class, () -> map.getByEnumOrThrow((_Enum) null));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(NullPointerException.class, () -> map2.getByEnumOrThrow((_Enum) null));
    }

    @Test
    public void getByEnumOrThrow_PassWhenNonEmpty() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map.getByEnumOrThrow(_Enum._1), "def");

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map2.getByEnumOrThrow(_Enum._1), "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.getByEnumOrDefault()
    //

    @Test
    public void getByEnumOrDefault_FailWhenEmptyAndKeyIsNull() {

        final FullEnumMap<_EmptyEnum, String> map =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class,
            () -> map.getByEnumOrDefault((_EmptyEnum) null, "abc"));

        final FullEnumMap<_EmptyEnum, String> map2 =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class,
            () -> map2.getByEnumOrDefault((_EmptyEnum) null, "abc"));
    }

    @Test
    public void getByEnumOrDefault_FailWhenNonEmptyAndKeyIsNull() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(NullPointerException.class, () -> map.getByEnumOrDefault((_Enum) null, "abc"));
        Assert.assertThrows(NullPointerException.class, () -> map.getByEnumOrDefault((_Enum) null, (String) null));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(NullPointerException.class, () -> map2.getByEnumOrDefault((_Enum) null, "abc"));
        Assert.assertThrows(NullPointerException.class, () -> map2.getByEnumOrDefault((_Enum) null, (String) null));
    }

    @Test
    public void getByEnumOrDefault_PassWhenNonEmpty() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map.getByEnumOrDefault(_Enum._1, "xyz"), "def");
        Assert.assertEquals(map.getByEnumOrDefault(_Enum._1, (String) null), "def");

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map2.getByEnumOrDefault(_Enum._1, "xyz"), "def");
        Assert.assertEquals(map2.getByEnumOrDefault(_Enum._1, (String) null), "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.getOrThrow()
    //

    @Test
    public void getOrThrow_FailWhenEmptyAndKeyIsNull() {

        final FullEnumMap<_EmptyEnum, String> map =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class, () -> map.getOrThrow(null));

        final FullEnumMap<_EmptyEnum, String> map2 =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(NullPointerException.class, () -> map2.getOrThrow(null));
    }

    @Test
    public void getOrThrow_FailWhenEmptyAndKeyIsNonNull() {

        final FullEnumMap<_EmptyEnum, String> map =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.NO, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        final Object key = new Object();
        Assert.assertThrows(IllegalArgumentException.class, () -> map.getOrThrow(key));

        final FullEnumMap<_EmptyEnum, String> map2 =
            new FullEnumMap<>(_EmptyEnum.class, AreNullValuesAllowed.YES, ImmutableMap.of(), IsEmptyEnumAllowed.YES);

        Assert.assertThrows(IllegalArgumentException.class, () -> map2.getOrThrow(key));
    }

    @Test
    public void getOrThrow_FailWhenNonEmptyAndKeyIsNull() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(NullPointerException.class, () -> map.getOrThrow(null));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(NullPointerException.class, () -> map2.getOrThrow(null));
    }

    @Test
    public void getOrThrow_FailWhenNonEmptyAndKeyIsNonNull() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        final Object key = new Object();
        Assert.assertThrows(IllegalArgumentException.class, () -> map.getOrThrow(key));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertThrows(IllegalArgumentException.class, () -> map2.getOrThrow(key));
    }

    @Test
    public void getOrThrow_PassWhenNonEmpty() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map.getOrThrow(_Enum._1), "def");

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        Assert.assertEquals(map2.getOrThrow(_Enum._1), "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.remove(Object)
    //

    @SuppressWarnings("deprecation")
    @Test
    public void remove_AlwaysFail() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map.remove(_Enum._1));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map2.remove(_Enum._1));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.clear()
    //

    @SuppressWarnings("deprecation")
    @Test
    public void clear_AlwaysFail() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map.clear());

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map2.clear());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.putIfAbsent(Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test
    public void putIfAbsent_AlwaysFail() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map.putIfAbsent(_Enum._1, "ghi"));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map2.putIfAbsent(_Enum._1, "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.remove(Object, Object)
    //

    @SuppressWarnings("deprecation")
    @Test
    public void removeKeyValue_AlwaysFail() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map.remove(_Enum._1, "ghi"));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.YES, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map2.remove(_Enum._1, "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FullEnumMap.computeIfAbsent(Object, Function)
    //

    @SuppressWarnings("deprecation")
    @Test
    public void computeIfAbsent_AlwaysFail() {

        final FullEnumMap<_Enum, String> map =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map.computeIfAbsent(_Enum._1, any -> "xyz"));

        final FullEnumMap<_Enum, String> map2 =
            FullEnumMap.copyOf(
                _Enum.class, AreNullValuesAllowed.NO, ImmutableMap.of(_Enum._2, "abc", _Enum._1, "def"));

        // @SuppressWarnings("deprecation")
        Assert.assertThrows(UnsupportedOperationException.class, () -> map2.computeIfAbsent(_Enum._1, any -> "xyz"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    public static <TKey, TValue>
    HashMap<TKey, TValue>
    createHashMap(@NullableElements Object... keysAndValuesArr) {

        final HashMap<TKey, TValue> map = new HashMap<>();
        for (int i = 0; i < keysAndValuesArr.length; i += 2) {

            @SuppressWarnings("unchecked")
            final TKey key = (TKey) keysAndValuesArr[i];
            @SuppressWarnings("unchecked")
            final TValue value = (TValue) keysAndValuesArr[i + 1];

            if (null != map.put(key, value)) {
                throw new IllegalStateException();
            }
        }
        return map;
    }
}
