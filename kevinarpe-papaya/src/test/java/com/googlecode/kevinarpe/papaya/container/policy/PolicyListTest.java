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
public class PolicyListTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.doNotAllowSet()
    //

    @Test
    public void doNotAllowSet_Pass() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyList<String> c = new PolicyList<>(list, doNotAllowSet);
            Assert.assertEquals(c.doNotAllowSet(), doNotAllowSet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.iterator()
    //

    @Test
    public void iterator_Pass() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyList<String> c = new PolicyList<>(list, doNotAllowSet);
            final PolicyIterator<String> iter = c.iterator();
            Assert.assertEquals(iter.doNotAllowSet(), doNotAllowSet);
            Assert.assertEquals("abc", iter.next());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.add(Object)
    //

    @Test
    public void add_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.add((String) null));
        Assert.assertTrue(c.add("jkl"));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi", "jkl"));
    }

    @Test
    public void add_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertTrue(c.add((String) null));
        Assert.assertEquals(list, Arrays.asList("abc", "def", "ghi", (String) null));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.remove(Object)
    //

    @Test
    public void remove_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove("jkl"));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove((String) null));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void remove_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertFalse(c.remove("jkl"));
        Assert.assertFalse(c.remove((String) null));
        Assert.assertTrue(c.remove("def"));
        Assert.assertEquals(list, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.addAll(Collection)
    //

    @Test
    public void addAll_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.addAll(Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.addAll(ImmutableList.of("mno", "pqr")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi", "mno", "pqr"));
    }

    @Test
    public void addAll_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertTrue(c.addAll(Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(list, Arrays.asList("abc", "def", "ghi", "jkl", (String) null, "xyz"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.addAll(int, Collection)
    //

    @Test
    public void addAllIndex_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.addAll(1, Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.addAll(1, ImmutableList.of("mno", "pqr")));
        Assert.assertEquals(list, ImmutableList.of("abc", "mno", "pqr", "def", "ghi"));
    }

    @Test
    public void addAllIndex_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertTrue(c.addAll(1, Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(list, Arrays.asList("abc", "jkl", (String) null, "xyz", "def", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.removeAll(Collection)
    //

    @Test
    public void removeAll_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.removeAll(ImmutableList.of("jkl")));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.removeAll(Arrays.asList((String) null)));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void removeAll_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertFalse(c.removeAll(ImmutableList.of("jkl", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.removeAll(ImmutableList.of("abc", "ghi", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.retainAll(Collection)
    //

    @Test
    public void retainAll_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.retainAll(ImmutableList.of("jkl")));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.retainAll(Arrays.asList((String) null)));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void retainAll_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertFalse(c.retainAll(ImmutableList.of("jkl", "abc", "def", "ghi", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.retainAll(ImmutableList.of("abc", "ghi", "xyz")));
        Assert.assertEquals(list, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.clear()
    //

    @Test
    public void clear_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.clear());
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void clear_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        c.clear();
        Assert.assertTrue(list.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.set(int, Object)
    //

    @Test
    public void set_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.set(1, (String) null));
        Assert.assertEquals(c.set(1, "xyz"), "def");
        Assert.assertEquals(list, ImmutableList.of("abc", "xyz", "ghi"));
    }

    @Test
    public void set_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertEquals(c.set(1, (String) null), "def");
        Assert.assertEquals(list, Arrays.asList("abc", (String) null, "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.add(int, Object)
    //

    @Test
    public void addIndex_PassWhenNullNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.add(1, (String) null));
        c.add(1, "jkl");
        Assert.assertEquals(list, ImmutableList.of("abc", "jkl", "def", "ghi"));
    }

    @Test
    public void addIndex_PassWhenNullAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        c.add(1, (String) null);
        Assert.assertEquals(list, Arrays.asList("abc", (String) null, "def", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.remove(int)
    //

    @Test
    public void removeIndex_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove(1));
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void removeIndex_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertEquals(c.remove(1), "def");
        Assert.assertEquals(list, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.listIterator() & listIterator(int)
    //

    @Test
    public void listIterator_Pass() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyList<String> c = new PolicyList<>(list, doNotAllowSet);
            final PolicyListIterator<String> iter = c.listIterator();
            Assert.assertEquals(iter.doNotAllowSet(), doNotAllowSet);
            Assert.assertEquals("abc", iter.next());

            final PolicyListIterator<String> iter2 = c.listIterator(1);
            Assert.assertEquals(iter2.doNotAllowSet(), doNotAllowSet);
            Assert.assertEquals("def", iter2.next());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.subList()
    //

    @Test
    public void subList_PassWhenEmpty() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyList<String> c = new PolicyList<>(list, doNotAllowSet);
            final PolicyList<String> subList = c.subList(0, 0);
            Assert.assertEquals(subList.doNotAllowSet(), doNotAllowSet);
            Assert.assertTrue(subList.isEmpty());
        }
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void subList_FailWhenEmpty() {

        final PolicyList<String> c = new PolicyList<>(ImmutableList.of(), ImmutableSet.of());
        c.subList(3, 7);
    }

    @Test
    public void subList_PassWhenNotEmpty() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        Assert.assertEquals(c.subList(0, 2), ImmutableList.of("abc", "def"));
        Assert.assertEquals(c.subList(1, 2), ImmutableList.of("def"));
        Assert.assertEquals(c.subList(1, 3), ImmutableList.of("def", "ghi"));
        Assert.assertEquals(c.subList(0, 3), ImmutableList.of("abc", "def", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.sort()
    //

    @Test
    public void sort_PassWhenEmpty() {

        final ArrayList<String> list = new ArrayList<>();
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        c.sort(String::compareTo);
        Assert.assertEquals(c, ImmutableList.of());
    }

    @Test
    public void sort_PassWhenNotEmpty() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("def", "abc"));
        final PolicyList<String> c = new PolicyList<>(list, ImmutableSet.of());
        c.sort(String::compareTo);
        Assert.assertEquals(c, ImmutableList.of("abc", "def"));
    }
}
