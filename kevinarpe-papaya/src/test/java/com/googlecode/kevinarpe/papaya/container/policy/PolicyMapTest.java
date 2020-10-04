package com.googlecode.kevinarpe.papaya.container.policy;

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
import com.google.common.collect.ImmutableSet;
import com.googlecode.kevinarpe.papaya.container.FullEnumMapTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PolicyMapTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.ctor()
    //

    @Test
    public void ctor_PassWhenNullKey() {

//        final HashMap<String, Integer> map = FullEnumMapTest.createHashMap((String) null, (Integer) null);
        final HashMap<String, Integer> map = FullEnumMapTest.createHashMap((String) null, 123);

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            if (doNotAllowSet.contains(DoNotAllow.NullKey)) {

                Assert.assertThrows(NullPointerException.class, () -> new PolicyMap<String, Integer>(map, doNotAllowSet));
            }
            else {
                new PolicyMap<String, Integer>(map, doNotAllowSet);
            }
        }
    }

    @Test
    public void ctor_PassWhenNullValue() {

//        final HashMap<String, Integer> map = FullEnumMapTest.createHashMap((String) null, (Integer) null);
        final HashMap<String, Integer> map = FullEnumMapTest.createHashMap("abc", (Integer) null);

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            if (doNotAllowSet.contains(DoNotAllow.NullValue)) {

                Assert.assertThrows(NullPointerException.class, () -> new PolicyMap<String, Integer>(map, doNotAllowSet));
            }
            else {
                new PolicyMap<String, Integer>(map, doNotAllowSet);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.doNotAllowSet()
    //

    @Test
    public void doNotAllowSet_Pass() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyMap<String, Integer> c = new PolicyMap<>(map, doNotAllowSet);
            Assert.assertEquals(c.doNotAllowSet(), doNotAllowSet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.put(Object, Object)
    //

    @Test
    public void put_PassWhenNullKeyNotAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of(DoNotAllow.NullKey));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.put((String) null, 234));
        Assert.assertEquals(c.put("abc", 234), Integer.valueOf(123));
        Assert.assertEquals(c.put("xyz", 234), (Integer) null);
        Assert.assertEquals(c.put("def", (Integer) null), Integer.valueOf(456));
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap("abc", 234, "def", (Integer) null, "ghi", 789, "xyz", 234));
    }

    @Test
    public void put_PassWhenNullKeyAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of());
        Assert.assertEquals(c.put((String) null, 234), (Integer) null);
        Assert.assertEquals(c.put("abc", 234), Integer.valueOf(123));
        Assert.assertEquals(c.put("xyz", 234), (Integer) null);
        Assert.assertEquals(c.put("def", (Integer) null), Integer.valueOf(456));
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(
                (String) null, 234, "abc", 234, "def", (Integer) null, "ghi", 789, "xyz", 234));
    }

    @Test
    public void put_PassWhenNullValueNotAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.put("abc", (Integer) null));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.put("xyz", (Integer) null));
        Assert.assertEquals(c.put("abc", 234), Integer.valueOf(123));
        Assert.assertEquals(c.put("xyz", 234), (Integer) null);
        Assert.assertEquals(c.put((String) null, 234), (Integer) null);
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(
                (String) null, 234, "abc", 234, "def", 456, "ghi", 789, "xyz", 234));
    }

    @Test
    public void put_PassWhenNullValueAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of());
        Assert.assertEquals(c.put("abc", (Integer) null), Integer.valueOf(123));
        Assert.assertEquals(c.put("xyz", (Integer) null), (Integer) null);
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(
                "abc", (Integer) null, "def", 456, "ghi", 789, "xyz", (Integer) null));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.remove(Object)
    //

    @Test
    public void remove_PassWhenNotAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove("abc"));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove((String) null));
        Assert.assertEquals(map, ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));
    }

    @Test
    public void remove_PassWhenAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of());
        Assert.assertEquals(c.remove("abc"), Integer.valueOf(123));
        Assert.assertEquals(c.remove("xyz"), (Integer) null);
        Assert.assertEquals(c.remove((String) null), (Integer) null);
        Assert.assertEquals(map, ImmutableMap.of("def", 456, "ghi", 789));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.putAll(Map)
    //

    @Test
    public void putAll_PassWhenNullKeyNotAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of(DoNotAllow.NullKey));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.putAll(FullEnumMapTest.createHashMap((String) null, 234)));
        c.putAll(ImmutableMap.of("abc", 234));
        c.putAll(ImmutableMap.of("xyz", 234));
        c.putAll(FullEnumMapTest.createHashMap("def", (Integer) null));
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap("abc", 234, "def", (Integer) null, "ghi", 789, "xyz", 234));
    }

    @Test
    public void putAll_PassWhenNullKeyAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of());
        c.putAll(FullEnumMapTest.createHashMap((String) null, 234));
        c.putAll(ImmutableMap.of("abc", 234));
        c.putAll(ImmutableMap.of("xyz", 234));
        c.putAll(FullEnumMapTest.createHashMap("def", (Integer) null));
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(
                (String) null, 234, "abc", 234, "def", (Integer) null, "ghi", 789, "xyz", 234));
    }

    @Test
    public void putAll_PassWhenNullValueNotAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.putAll(FullEnumMapTest.createHashMap("abc", (Integer) null)));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.putAll(FullEnumMapTest.createHashMap("xyz", (Integer) null)));
        c.putAll(ImmutableMap.of("abc", 234));
        c.putAll(ImmutableMap.of("xyz", 234));
        c.putAll(FullEnumMapTest.createHashMap((String) null, 234));
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(
                (String) null, 234, "abc", 234, "def", 456, "ghi", 789, "xyz", 234));
    }

    @Test
    public void putAll_PassWhenNullValueAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of());
        c.putAll(FullEnumMapTest.createHashMap("abc", (Integer) null));
        c.putAll(FullEnumMapTest.createHashMap("xyz", (Integer) null));
        Assert.assertEquals(map,
            FullEnumMapTest.createHashMap(
                "abc", (Integer) null, "def", 456, "ghi", 789, "xyz", (Integer) null));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.clear()
    //

    @Test
    public void clear_PassWhenNotAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.clear());
        Assert.assertEquals(map, ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));
    }

    @Test
    public void clear_PassWhenAllowed() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final PolicyMap<String, Integer> c = new PolicyMap<>(map, ImmutableSet.of());
        c.clear();
        Assert.assertEquals(map, ImmutableMap.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.keySet()
    //

    @Test
    public void keySet_Pass() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final ImmutableSet<DoNotAllow> doNotAllowSet = ImmutableSet.of(DoNotAllow.Remove);
        final PolicyMap<String, Integer> c = new PolicyMap<>(map, doNotAllowSet);
        final PolicySet<String> keySet = c.keySet();
        Assert.assertEquals(keySet.doNotAllowSet(), doNotAllowSet);
        Assert.assertEquals(keySet.iterator().doNotAllowSet(), doNotAllowSet);
        Assert.assertEquals(keySet, map.keySet());
        Assert.assertThrows(UnsupportedOperationException.class, () -> keySet.clear());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.values()
    //

    @Test
    public void values_Pass() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final ImmutableSet<DoNotAllow> doNotAllowSet = ImmutableSet.of(DoNotAllow.Remove);
        final PolicyMap<String, Integer> c = new PolicyMap<>(map, doNotAllowSet);
        final PolicyCollection<Integer> values = c.values();
        Assert.assertEquals(values.doNotAllowSet(), doNotAllowSet);
        Assert.assertEquals(values.iterator().doNotAllowSet(), doNotAllowSet);
        Assert.assertEquals(values, map.values());
        Assert.assertThrows(UnsupportedOperationException.class, () -> values.clear());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMap.entrySet()
    //

    @Test
    public void entrySet_Pass() {

        final LinkedHashMap<String, Integer> map =
            new LinkedHashMap<>(ImmutableMap.of("abc", 123, "def", 456, "ghi", 789));

        final ImmutableSet<DoNotAllow> doNotAllowSet = ImmutableSet.of(DoNotAllow.Remove, DoNotAllow.NullValue);
        final PolicyMap<String, Integer> c = new PolicyMap<>(map, doNotAllowSet);
        final PolicySet<Map.Entry<String, Integer>> entrySet = c.entrySet();
        Assert.assertEquals(entrySet.doNotAllowSet(), doNotAllowSet);
        Assert.assertEquals(entrySet.iterator().doNotAllowSet(), doNotAllowSet);
        Assert.assertEquals(entrySet, map.entrySet());
        Assert.assertThrows(UnsupportedOperationException.class, () -> entrySet.clear());

        final PolicyIterator<Map.Entry<String, Integer>> iter = entrySet.iterator();
        final Map.Entry<String, Integer> entry = iter.next();
        Assert.assertEquals(entry, new AbstractMap.SimpleImmutableEntry<>("abc", 123));
        Assert.assertThrows(UnsupportedOperationException.class, () -> entry.setValue((Integer) null));
        Assert.assertEquals(entry.setValue(234), Integer.valueOf(123));
        Assert.assertEquals(entry, new AbstractMap.SimpleImmutableEntry<>("abc", 234));
        Assert.assertEquals(map, ImmutableMap.of("abc", 234, "def", 456, "ghi", 789));
    }
}
