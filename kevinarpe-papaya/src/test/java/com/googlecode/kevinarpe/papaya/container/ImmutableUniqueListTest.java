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
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.UnaryOperator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableUniqueListTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.empty()
    //

    @Test
    public void empty_Pass() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertTrue(ul.isEmpty());
        Assert.assertSame(ul, ImmutableUniqueList.of());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.of()
    //

    @Test
    public void of_Pass() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of();
        Assert.assertTrue(ul.isEmpty());
        Assert.assertSame(ul, ImmutableUniqueList.empty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.of(Collection)
    //

    @Test
    public void ofCollection_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of());
        Assert.assertTrue(ul.isEmpty());
        Assert.assertSame(ul, ImmutableUniqueList.empty());
    }

    @Test
    public void ofCollection_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.size(), 2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ofCollection_FailWhenDupes() {

        ImmutableUniqueList.of(ImmutableList.of("abc", "def", "abc"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.copyOf()
    //

    @Test
    public void copyOf_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.copyOf(new ArrayList<>());
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void copyOf_PassWhenNotEmpty() {

        final ArrayList<String> list = new ArrayList<>(Arrays.asList("abc", "def"));
        final ImmutableUniqueList<String> ul = ImmutableUniqueList.copyOf(list);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(ul.size(), 2);
        list.add("ghi");
        Assert.assertEquals(list.size(), 3);
        Assert.assertEquals(ul.size(), 2);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_PassWhenContainsNull() {

        final ArrayList<String> list = new ArrayList<>(Arrays.asList("abc", null, "def"));
        ImmutableUniqueList.copyOf(list);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.size()
    //

    @Test
    public void size_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void size_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.size(), 2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.isEmpty()
    //

    @Test
    public void isEmpty_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertTrue(ul.isEmpty());
    }

    @Test
    public void isEmpty_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.contains()
    //

    @Test
    public void contains_PassWhenValueIsNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.contains(null));
    }

    @Test
    public void contains_PassWhenValueDoesNotExist() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertFalse(ul.contains("ghi"));
    }

    @Test
    public void contains_PassWhenValueExists() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.contains("def"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.iterator()
    //

    @Test
    public void iterator_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertFalse(ul.iterator().hasNext());
    }

    @Test
    public void iterator_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        final UnmodifiableIterator<String> iter = ul.iterator();
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertFalse(iter.hasNext());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void iterator_FailWhenNotEmptyAndRemove() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        final UnmodifiableIterator<String> iter = ul.iterator();
        Assert.assertEquals(iter.next(), "abc");
        // @SuppressWarnings("deprecation")
        iter.remove();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.toArray()
    //

    @Test
    public void toArray_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertEquals(ul.toArray(), new String[0]);
    }

    @Test
    public void toArray_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.toArray(), new String[]{"abc", "def"});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.toArray(T[])
    //

    @Test
    public void toArrayT_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertEquals(ul.toArray(new String[0]), new String[0]);
        Assert.assertEquals(ul.toArray(new String[]{}), new String[0]);
    }

    @Test
    public void toArrayT_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.toArray(), new String[]{"abc", "def"});
        Assert.assertEquals(ul.toArray(new String[0]), new String[]{"abc", "def"});
        Assert.assertEquals(ul.toArray(new String[]{null}), new String[]{"abc", "def"});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.add(Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_FailWhenNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.add((String) null);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_FailWhenNotNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.add("xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.remove(Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_FailWhenNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.remove((String) null);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_FailWhenNotNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.remove("xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.containsAll()
    //

    @Test
    public void containsAll_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertTrue(ul.containsAll(ImmutableList.of()));
        Assert.assertFalse(ul.containsAll(ImmutableList.of("abc")));
        Assert.assertFalse(ul.containsAll(ImmutableList.of("ghi")));
        Assert.assertFalse(ul.containsAll(new ArrayList<>(Arrays.asList((String) null))));
    }

    @Test
    public void containsAll_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertTrue(ul.containsAll(ImmutableList.of()));
        Assert.assertTrue(ul.containsAll(ImmutableList.of("abc")));
        Assert.assertTrue(ul.containsAll(ImmutableList.of("def")));
        Assert.assertFalse(ul.containsAll(ImmutableList.of("ghi")));
        Assert.assertFalse(ul.containsAll(new ArrayList<>(Arrays.asList((String) null))));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.addAll(Collection)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollection_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.addAll(ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollection_FailWhenEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.addAll(ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollection_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.addAll(ImmutableList.of("ghi"));
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollection_FailWhenNotEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.addAll(ImmutableList.of("ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.addAll(int, Collection)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollectionIndex_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.addAll(7, ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollectionIndex_FailWhenEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.addAll(7, ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollectionIndex_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.addAll(7, ImmutableList.of("ghi"));
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAllCollectionIndex_FailWhenNotEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.addAll(7, ImmutableList.of("ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.removeAll(Collection)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeAll_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.removeAll(ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeAll_FailWhenEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.removeAll(ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeAll_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.removeAll(ImmutableList.of("ghi"));
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeAll_FailWhenNotEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.removeAll(ImmutableList.of("ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.retainAll(Collection)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void retainAll_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.retainAll(ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void retainAll_FailWhenEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.retainAll(ImmutableList.of());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void retainAll_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.retainAll(ImmutableList.of("ghi"));
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void retainAll_FailWhenNotEmpty2() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        // @SuppressWarnings("deprecation")
        ul.retainAll(ImmutableList.of("ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.clear()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void clear_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.clear();
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void clear_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        ul.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.set()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void set_FailWhenNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.set(7, (String) null);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void set_FailWhenNotNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.set(7, "xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.add(int, Object)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addIndex_FailWhenNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.add(7, (String) null);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addIndex_FailWhenNotNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        // @SuppressWarnings("deprecation")
        ul.add(7, "xyz");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.remove(int)
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeIndex_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.remove(7);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeIndex_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        ul.remove(7);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.indexOf()
    //

    @Test
    public void indexOf_PassWhenValueIsNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.indexOf(null), -1);
    }

    @Test
    public void indexOf_PassWhenValueDoesNotExist() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.indexOf("ghi"), -1);
    }

    @Test
    public void indexOf_PassWhenValueExists() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.indexOf("def"), 1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.lastIndexOf()
    //

    @Test
    public void lastIndexOf_PassWhenValueIsNull() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.lastIndexOf(null), -1);
    }

    @Test
    public void lastIndexOf_PassWhenValueDoesNotExist() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.lastIndexOf("ghi"), -1);
    }

    @Test
    public void lastIndexOf_PassWhenValueExists() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.lastIndexOf("def"), 1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.listIterator()
    //

    @Test
    public void listIterator_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertFalse(ul.listIterator().hasNext());
    }

    @Test
    public void listIterator_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        final UnmodifiableListIterator<String> iter = ul.listIterator();
        Assert.assertFalse(iter.hasPrevious());
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertEquals(iter.previous(), "def");
        Assert.assertEquals(iter.previous(), "abc");
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertFalse(iter.hasNext());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void listIterator_FailWhenNotEmptyAndRemove() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        final UnmodifiableListIterator<String> iter = ul.listIterator();
        Assert.assertEquals(iter.next(), "abc");
        // @SuppressWarnings("deprecation")
        iter.remove();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.listIterator(int)
    //

    @Test
    public void listIteratorIndex_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertFalse(ul.listIterator(0).hasNext());
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void listIteratorIndex_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.listIterator(1);
    }

    @Test
    public void listIteratorIndex_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        final UnmodifiableListIterator<String> iter = ul.listIterator(1);
        Assert.assertTrue(iter.hasPrevious());
        Assert.assertEquals(iter.next(), "def");
        Assert.assertEquals(iter.previous(), "def");
        Assert.assertEquals(iter.previous(), "abc");
        Assert.assertEquals(iter.next(), "abc");
        Assert.assertEquals(iter.next(), "def");
        Assert.assertFalse(iter.hasNext());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void listIteratorIndex_FailWhenNotEmptyAndRemove() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        final UnmodifiableListIterator<String> iter = ul.listIterator(1);
        Assert.assertEquals(iter.next(), "def");
        // @SuppressWarnings("deprecation")
        iter.remove();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.subList()
    //

    @Test
    public void subList_PassWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        Assert.assertTrue(ul.subList(0, 0).isEmpty());
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void subList_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.subList(3, 7);
    }

    @Test
    public void subList_PassWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def", "ghi"));
        Assert.assertEquals(ul.subList(0, 2), ImmutableList.of("abc", "def"));
        Assert.assertEquals(ul.subList(1, 2), ImmutableList.of("def"));
        Assert.assertEquals(ul.subList(1, 3), ImmutableList.of("def", "ghi"));
        Assert.assertEquals(ul.subList(0, 3), ImmutableList.of("abc", "def", "ghi"));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.replaceAll()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void replaceAll_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.replaceAll(UnaryOperator.identity());
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void replaceAll_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        ul.replaceAll(UnaryOperator.identity());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.sort()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void sort_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.sort(String::compareTo);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void sort_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        ul.sort(String::compareTo);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableUniqueList.removeIf()
    //

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeIf_FailWhenEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.empty();
        ul.removeIf((String any) -> true);
    }

    @SuppressWarnings("deprecation")
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeIf_FailWhenNotEmpty() {

        final ImmutableUniqueList<String> ul = ImmutableUniqueList.of(ImmutableList.of("abc", "def"));
        ul.removeIf((String any) -> true);
    }
}
