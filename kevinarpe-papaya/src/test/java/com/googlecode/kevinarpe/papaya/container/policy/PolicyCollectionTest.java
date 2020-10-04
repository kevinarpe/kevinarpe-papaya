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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PolicyCollectionTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.doNotAllowSet()
    //

    @Test
    public void doNotAllowSet_Pass() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyCollection<String> c = new PolicyCollection<>(list, doNotAllowSet);
            Assert.assertEquals(c.doNotAllowSet(), doNotAllowSet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.iterator()
    //

    @Test
    public void iterator_PassWhenRemoveNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.Remove));
        final PolicyIterator<String> iter = c.iterator();
        Assert.assertEquals("abc", iter.next());
        Assert.assertThrows(UnsupportedOperationException.class, () -> iter.remove());
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void iterator_PassWhenRemoveAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        final PolicyIterator<String> iter = c.iterator();
        Assert.assertEquals("abc", iter.next());
        iter.remove();
        Assert.assertEquals(list, ImmutableList.of("def", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.add(Object)
    //

    @Test
    public void add_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.add((String) null));
        Assert.assertTrue(c.add("jkl"));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi", "jkl"));
    }

    @Test
    public void add_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        Assert.assertTrue(c.add((String) null));
        Assert.assertEquals(list, Arrays.asList("abc", "def", "ghi", (String) null));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.remove(Object)
    //

    @Test
    public void remove_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove("jkl"));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove((String) null));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void remove_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        Assert.assertFalse(c.remove("jkl"));
        Assert.assertFalse(c.remove((String) null));
        Assert.assertTrue(c.remove("def"));
        Assert.assertEquals(list, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.addAll(Collection)
    //

    @Test
    public void addAll_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.addAll(Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.addAll(ImmutableList.of("mno", "pqr")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi", "mno", "pqr"));
    }

    @Test
    public void addAll_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        Assert.assertTrue(c.addAll(Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(list, Arrays.asList("abc", "def", "ghi", "jkl", (String) null, "xyz"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.removeAll(Collection)
    //

    @Test
    public void removeAll_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.removeAll(ImmutableList.of("jkl")));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.removeAll(Arrays.asList((String) null)));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void removeAll_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        Assert.assertFalse(c.removeAll(ImmutableList.of("jkl", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.removeAll(ImmutableList.of("abc", "ghi", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.retainAll(Collection)
    //

    @Test
    public void retainAll_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.retainAll(ImmutableList.of("jkl")));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.retainAll(Arrays.asList((String) null)));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void retainAll_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        Assert.assertFalse(c.retainAll(ImmutableList.of("jkl", "abc", "def", "ghi", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.retainAll(ImmutableList.of("abc", "ghi", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyCollection.clear()
    //

    @Test
    public void clear_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.clear());
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void clear_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyCollection<String> c = new PolicyCollection<>(list, ImmutableSet.of());
        c.clear();
        Assert.assertTrue(list.isEmpty());
    }
}
