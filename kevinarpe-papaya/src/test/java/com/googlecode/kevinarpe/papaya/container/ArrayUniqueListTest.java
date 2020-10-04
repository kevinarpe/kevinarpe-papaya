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
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * These tests cover: {@link ArrayUniqueList} and {@link ForwardingUniqueList}.
 */
public class ArrayUniqueListTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.withInitialCapacity(int)
    //

    @Test
    public void withInitialCapacity_Pass() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.withInitialCapacity(172);
        Assert.assertTrue(ul.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.ctor()
    //

    @Test
    public void ctor_Pass() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.copyOf(Collection)
    //

    @Test
    public void copyCtor_PassWhenEmpty() {

        final ArrayList<String> list = new ArrayList<>();
        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(list);
        Assert.assertTrue(ul.isEmpty());
        list.add("abc");
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void copyCtor_PassWhenNotEmpty() {

        final ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(list);
        Assert.assertEquals(ul, list);
        list.add("def");
        Assert.assertNotEquals(ul, list);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.trimToSize()
    //

    @Test
    public void trimToSize_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.trimToSize();
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void trimToSize_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.trimToSize();
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.ensureCapacity()
    //

    @Test
    public void ensureCapacity_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.ensureCapacity(172);
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void ensureCapacity_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.ensureCapacity(172);
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.size()
    //

    @Test
    public void size_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void size_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.size(), 2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.isEmpty()
    //

    @Test
    public void isEmpty_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void isEmpty_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.contains()
    //

    @Test
    public void contains_PassWhenValueIsNull() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.contains(null));
    }

    @Test
    public void contains_PassWhenValueDoesNotExist() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.contains("ghi"));
    }

    @Test
    public void contains_PassWhenValueExists() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.contains("def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.iterator()
    //

    @Test
    public void iterator_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.iterator().hasNext());
    }

    @Test
    public void iterator_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final Iterator<String> iter = ul.iterator();
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertFalse(iter.hasNext());
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void iterator_FailWhenRemove() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final Iterator<String> iter = ul.iterator();
        iter.remove();
    }

    @Test
    public void iterator_PassWhenRemove() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final Iterator<String> iter = ul.iterator();
        Assert.assertEquals(iter.next(), "abc");
        iter.remove();
        Assert.assertEquals(ul, ImmutableList.of("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.toArray()
    //

    @Test
    public void toArray_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertEquals(ul.toArray(), new String[0]);
    }

    @Test
    public void toArray_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.toArray(), new String[]{"abc", "def"});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.toArray(T[])
    //

    @Test
    public void toArrayT_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertEquals(ul.toArray(new String[0]), new String[0]);
        Assert.assertEquals(ul.toArray(new String[]{}), new String[0]);
    }

    @Test
    public void toArrayT_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.toArray(), new String[]{"abc", "def"});
        Assert.assertEquals(ul.toArray(new String[0]), new String[]{"abc", "def"});
        Assert.assertEquals(ul.toArray(new String[]{null}), new String[]{"abc", "def"});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.add(Object)
    //

    @Test
    public void add_PassWhenNull() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.add((String) null));
        Assert.assertEquals(ul, Arrays.asList((String) null));
    }

    @Test
    public void add_PassWhenNotNull() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("abc"));
        Assert.assertFalse(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.remove(Object)
    //

    @Test
    public void remove_PassWhenNull() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.remove((String) null));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("abc"));
        Assert.assertFalse(ul.remove((String) null));
        Assert.assertTrue(ul.add((String) null));
        Assert.assertEquals(ul, Arrays.asList("abc", (String) null));
        Assert.assertTrue(ul.remove((String) null));
        Assert.assertEquals(ul, Arrays.asList("abc"));
    }

    @Test
    public void remove_PassWhenNotNull() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.remove("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("abc"));
        Assert.assertFalse(ul.remove("def"));
        Assert.assertTrue(ul.add("def"));
        Assert.assertEquals(ul, Arrays.asList("abc", "def"));
        Assert.assertTrue(ul.remove("def"));
        Assert.assertEquals(ul, Arrays.asList("abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.containsAll()
    //

    @Test
    public void containsAll_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.containsAll(ImmutableList.of()));
        Assert.assertFalse(ul.containsAll(ImmutableList.of("abc")));
        Assert.assertFalse(ul.containsAll(ImmutableList.of("ghi")));
        Assert.assertFalse(ul.containsAll(new ArrayList<>(Arrays.asList((String) null))));
    }

    @Test
    public void containsAll_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.containsAll(ImmutableList.of()));
        Assert.assertTrue(ul.containsAll(ImmutableList.of("abc")));
        Assert.assertTrue(ul.containsAll(ImmutableList.of("def")));
        Assert.assertFalse(ul.containsAll(ImmutableList.of("ghi")));
        Assert.assertFalse(ul.containsAll(new ArrayList<>(Arrays.asList((String) null))));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.addAll(Collection)
    //

    @Test
    public void addAllCollection_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.addAll(ImmutableList.of()));
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void addAllCollection_PassWhenEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.addAll(ImmutableList.of()));
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    @Test
    public void addAllCollection_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.addAll(ImmutableList.of("ghi", "jkl")));
        Assert.assertEquals(ul, ImmutableList.of("ghi", "jkl"));
    }

    @Test
    public void addAllCollection_PassWhenNotEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.addAll(ImmutableList.of("ghi", "jkl")));
        Assert.assertEquals(ul, ImmutableList.of("abc", "def", "ghi", "jkl"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.addAll(int, Collection)
    //

    @Test
    public void addAllCollectionIndex_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.addAll(0, ImmutableList.of()));
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void addAllCollectionIndex_PassWhenEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.addAll(0, ImmutableList.of()));
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    @Test
    public void addAllCollectionIndex_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.addAll(0, ImmutableList.of("ghi")));
        Assert.assertEquals(ul, ImmutableList.of("ghi"));
    }

    @Test
    public void addAllCollectionIndex_PassWhenNotEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.addAll(1, ImmutableList.of("ghi")));
        Assert.assertEquals(ul, ImmutableList.of("abc", "ghi", "def"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addAllCollectionIndex_PassWhenAddDupes() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.addAll(1, ImmutableList.of("abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.removeAll(Collection)
    //

    @Test
    public void removeAll_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.removeAll(ImmutableList.<String>of()));
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void removeAll_PassWhenEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.removeAll(ImmutableList.<String>of()));
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    @Test
    public void removeAll_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.removeAll(ImmutableList.of("ghi")));
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void removeAll_PassWhenNotEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.removeAll(ImmutableList.of("ghi")));
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.removeAll(ImmutableList.of("abc", "def")));
        Assert.assertEquals(ul, ImmutableList.of());
        Assert.assertTrue(ul.add("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.retainAll(Collection)
    //

    @Test
    public void retainAll_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.retainAll(ImmutableList.<String>of()));
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void retainAll_PassWhenEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.retainAll(ImmutableList.<String>of()));
        Assert.assertEquals(ul, ImmutableList.of());
        Assert.assertTrue(ul.add("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
    }

    @Test
    public void retainAll_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.retainAll(ImmutableList.of("ghi")));
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void retainAll_PassWhenNotEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.retainAll(ImmutableList.of("ghi", "def")));
        Assert.assertEquals(ul, ImmutableList.of("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.clear()
    //

    @Test
    public void clear_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.clear();
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void clear_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.clear();
        Assert.assertTrue(ul.isEmpty());
        // Let's check the internal set was also cleared.
        Assert.assertTrue(ul.add("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
        ul.clear();
        Assert.assertTrue(ul.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.set()
    //

    @Test
    public void set_PassWhenNull() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.set(1, (String) null), "def");
        Assert.assertEquals(ul, Arrays.asList("abc", (String) null));
        Assert.assertEquals(ul.set(1, "def"), (String) null);
        Assert.assertEquals(ul, Arrays.asList("abc", "def"));
    }

    @Test
    public void set_PassWhenNull2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(Arrays.asList("abc", (String) null));
        Assert.assertEquals(ul.set(1, (String) null), (String) null);
        Assert.assertEquals(ul, Arrays.asList("abc", (String) null));
    }

    @Test
    public void set_PassWhenNotNull() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.set(1, "ghi"), "def");
        Assert.assertEquals(ul, Arrays.asList("abc", "ghi"));
        Assert.assertEquals(ul.set(1, "def"), "ghi");
        Assert.assertEquals(ul, Arrays.asList("abc", "def"));
    }

    @Test
    public void set_PassWhenNotNull2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(Arrays.asList("abc", "ghi"));
        Assert.assertEquals(ul.set(1, "ghi"), "ghi");
        Assert.assertEquals(ul, Arrays.asList("abc", "ghi"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void set_FailWhenNullAndDupe() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.set(1, (String) null), "def");
        ul.set(0, (String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void set_FailWhenNotNullAndDupe() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.set(1, "ghi"), "def");
        ul.set(0, "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.add(int, Object)
    //

    @Test
    public void addIndex_PassWhenNull() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.add(1, (String) null);
        Assert.assertEquals(ul, Arrays.asList("abc", (String) null, "def"));
    }

    @Test
    public void addIndex_PassWhenNotNull() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.add(0, "abc");
        Assert.assertEquals(ul, ImmutableList.of("abc"));
        ul.add(1, "def");
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
        ul.add(1, "ghi");
        Assert.assertEquals(ul, ImmutableList.of("abc", "ghi", "def"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void add_FailWhenNullAndDupe() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.add(1, (String) null);
        ul.add(0, (String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void add_FailWhenNotNullAndDupe() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.add(1, "ghi");
        ul.add(0, "ghi");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.remove(int)
    //

    @Test
    public void removeIndex_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.remove(0), "abc");
        Assert.assertEquals(ul, ImmutableList.of("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.indexOf()
    //

    @Test
    public void indexOf_PassWhenValueIsNull() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.indexOf(null), -1);
    }

    @Test
    public void indexOf_PassWhenValueDoesNotExist() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.indexOf("ghi"), -1);
    }

    @Test
    public void indexOf_PassWhenValueExists() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.indexOf("def"), 1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.lastIndexOf()
    //

    @Test
    public void lastIndexOf_PassWhenValueIsNull() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.lastIndexOf(null), -1);
    }

    @Test
    public void lastIndexOf_PassWhenValueDoesNotExist() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.lastIndexOf("ghi"), -1);
    }

    @Test
    public void lastIndexOf_PassWhenValueExists() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.lastIndexOf("def"), 1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.listIterator()
    //

    @Test
    public void listIterator_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.listIterator().hasNext());
    }

    @Test
    public void listIterator_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        Assert.assertFalse(iter.hasPrevious());
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertEquals(iter.previous(), "def");
        Assert.assertEquals(iter.previous(), "abc");
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertFalse(iter.hasNext());
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void listIterator_FailWhenRemove() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        iter.remove();
    }

    @Test
    public void listIterator_PassWhenRemove() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        Assert.assertEquals(iter.next(), "abc");
        iter.remove();
        Assert.assertEquals(ul, ImmutableList.of("def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("def", "abc"));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void listIterator_FailWhenSetBeforeNext() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        iter.set("ghi");
    }

    @Test
    public void listIterator_PassWhenSet() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        Assert.assertEquals(iter.next(), "abc");
        iter.set("abc");
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
        iter.set("ghi");
        Assert.assertEquals(ul, ImmutableList.of("ghi", "def"));
        Assert.assertTrue(ul.add("abc"));
        Assert.assertEquals(ul, ImmutableList.of("ghi", "def", "abc"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void listIterator_FailWhenSetDupeNullValue() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(Arrays.asList("abc", "def", (String) null));
        final ListIterator<String> iter = ul.listIterator();
        Assert.assertEquals(iter.next(), "abc");
        iter.set((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void listIterator_FailWhenSetDupeNonNullValue() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        Assert.assertEquals(iter.next(), "abc");
        iter.set("def");
    }

    @Test
    public void listIterator_PassWhenAdd() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        Assert.assertEquals(iter.next(), "abc");
        iter.add("ghi");
        Assert.assertEquals(ul, ImmutableList.of("abc", "ghi", "def"));
        Assert.assertFalse(ul.add("ghi"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void listIterator_FailWhenAddDupeNullValue() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(Arrays.asList("abc", "def", (String) null));
        final ListIterator<String> iter = ul.listIterator();
        iter.add((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void listIterator_FailWhenAddDupeNonNullValue() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator();
        iter.add("def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.listIterator(int)
    //

    @Test
    public void listIteratorIndex_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertFalse(ul.listIterator(0).hasNext());
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void listIteratorIndex_FailWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.listIterator(1);
    }

    @Test
    public void listIteratorIndex_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        final ListIterator<String> iter = ul.listIterator(1);
        Assert.assertTrue(iter.hasPrevious());
        Assert.assertEquals(iter.next(), "def");
        Assert.assertEquals(iter.previous(), "def");
        Assert.assertEquals(iter.previous(), "abc");
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertFalse(iter.hasNext());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.subList()
    //

    @Test
    public void subList_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        Assert.assertTrue(ul.subList(0, 0).isEmpty());
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void subList_FailWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.subList(3, 7);
    }

    @Test
    public void subList_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def", "ghi"));
        Assert.assertEquals(ul.subList(0, 2), ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.subList(1, 2), ImmutableList.of("def"));
        Assert.assertEquals(ul.subList(1, 3), ImmutableList.of("def", "ghi"));
        Assert.assertEquals(ul.subList(0, 3), ImmutableList.of("abc", "def", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.replaceAll()
    //

    @Test
    public void replaceAll_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.replaceAll(UnaryOperator.identity());
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void replaceAll_PassWhenEmpty2() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.replaceAll((String s) -> s + "x");
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void replaceAll_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.replaceAll(UnaryOperator.identity());
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    @Test
    public void replaceAll_PassWhenNotEmpty2() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.replaceAll((String s) -> s + "x");
        Assert.assertEquals(ul, ImmutableList.of("abcx", "defx"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void replaceAll_FailWhenDupe() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.replaceAll((String s) -> "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.sort()
    //

    @Test
    public void sort_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.sort(String::compareTo);
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void sort_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("def", "abc"));
        ul.sort(String::compareTo);
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayUniqueList.removeIf()
    //

    @Test
    public void removeIf_PassWhenEmpty() {

        final ArrayUniqueList<String> ul = new ArrayUniqueList<>();
        ul.removeIf((String any) -> true);
        Assert.assertEquals(ul, ImmutableList.of());
    }

    @Test
    public void removeIf_PassWhenNotEmpty() {

        final ArrayUniqueList<String> ul = ArrayUniqueList.copyOf(ImmutableList.of("abc", "def"));
        ul.removeIf((String any) -> false);
        Assert.assertEquals(ul, ImmutableList.of("abc", "def"));
        ul.removeIf((String any) -> true);
        Assert.assertEquals(ul, ImmutableList.of());
    }
}
