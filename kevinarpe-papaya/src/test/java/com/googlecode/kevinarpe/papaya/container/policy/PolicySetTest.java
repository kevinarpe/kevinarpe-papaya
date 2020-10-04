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

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PolicySetTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicySet.doNotAllowSet()
    //

    @Test
    public void doNotAllowSet_Pass() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicySet<String> c = new PolicySet<>(set, doNotAllowSet);
            Assert.assertEquals(c.doNotAllowSet(), doNotAllowSet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicySet.iterator()
    //

    @Test
    public void iterator_Pass() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicySet<String> c = new PolicySet<>(set, doNotAllowSet);
            final PolicyIterator<String> iter = c.iterator();
            Assert.assertEquals(iter.doNotAllowSet(), doNotAllowSet);
            Assert.assertEquals("abc", iter.next());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicySet.add(Object)
    //

    @Test
    public void add_PassWhenNullNotAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.add((String) null));
        Assert.assertTrue(c.add("jkl"));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi", "jkl"));
    }

    @Test
    public void add_PassWhenNullAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of());
        Assert.assertTrue(c.add((String) null));
        Assert.assertEquals(set, Arrays.asList("abc", "def", "ghi", (String) null));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicySet.remove(Object)
    //

    @Test
    public void remove_PassWhenNotAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove("jkl"));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.remove((String) null));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void remove_PassWhenAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of());
        Assert.assertFalse(c.remove("jkl"));
        Assert.assertFalse(c.remove((String) null));
        Assert.assertTrue(c.remove("def"));
        Assert.assertEquals(set, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.addAll(Collection)
    //

    @Test
    public void addAll_PassWhenNullNotAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class,
            () -> c.addAll(Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.addAll(ImmutableList.of("mno", "pqr")));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi", "mno", "pqr"));
    }

    @Test
    public void addAll_PassWhenNullAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of());
        Assert.assertTrue(c.addAll(Arrays.asList("jkl", (String) null, "xyz")));
        Assert.assertEquals(set, Arrays.asList("abc", "def", "ghi", "jkl", (String) null, "xyz"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.retainAll(Collection)
    //

    @Test
    public void retainAll_PassWhenNotAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.retainAll(ImmutableList.of("jkl")));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.retainAll(Arrays.asList((String) null)));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void retainAll_PassWhenAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of());
        Assert.assertFalse(c.retainAll(ImmutableList.of("jkl", "abc", "def", "ghi", "xyz")));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.retainAll(ImmutableList.of("abc", "ghi", "xyz")));
        Assert.assertEquals(set, ImmutableList.of("abc", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.removeAll(Collection)
    //

    @Test
    public void removeAll_PassWhenNotAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.removeAll(ImmutableList.of("jkl")));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.removeAll(Arrays.asList((String) null)));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void removeAll_PassWhenAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of());
        Assert.assertFalse(c.removeAll(ImmutableList.of("jkl", "xyz")));
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
        Assert.assertTrue(c.removeAll(ImmutableList.of("abc", "ghi", "xyz")));
        Assert.assertEquals(set, ImmutableList.of("def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyList.clear()
    //

    @Test
    public void clear_PassWhenNotAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.clear());
        Assert.assertEquals(set, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void clear_PassWhenAllowed() {

        final LinkedHashSet<String> set = new LinkedHashSet<>(ImmutableSet.of("abc", "def", "ghi"));
        final PolicySet<String> c = new PolicySet<>(set, ImmutableSet.of());
        c.clear();
        Assert.assertTrue(set.isEmpty());
    }
}
