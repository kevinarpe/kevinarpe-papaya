package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UniqueArrayListTest {
    
    private Random _random = new Random();
    
    @BeforeClass
    public void setup() {
        
    }
    
    @Test
    public void emptyCtor_Pass() {
        new UniqueArrayList<String>();
        new UniqueArrayList<String>(0);
        new UniqueArrayList<String>(10);
        new UniqueArrayList<String>(100);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void empytCtor_FailWithNegativeInitialCapacity() {
        new UniqueArrayList<String>(-1);
    }
    
    @Test
    public void copyCtor_Pass() {
        _copyCtor_Pass(new ArrayList<String>());
        _copyCtor_Pass(new ArrayList<String>(Arrays.asList("abc", "def", "ghi")));
    }
    
    private static void _copyCtor_Pass(List<String> list) {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>(list);
        Assert.assertEquals(list, UAL);
        _assertEqualsList(list, UAL);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void copyCtor_FailWithNull() {
        new UniqueArrayList<String>(null);
    }
    
    @Test
    public void add_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add("abc");
        UAL.add("def");
        UAL.add(null);
        UAL.add(1, "ghi");
        UAL.add(1, "jkl");
        UAL.add(1, "mno");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void add_FailWithDupe() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add("abc");
        UAL.add("abc");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void add_FailWithDupe2() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add(null);
        UAL.add(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void add_FailWithDupe3() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add(0, "abc");
        UAL.add(0, "abc");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void add_FailWithDupe4() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add(0, null);
        UAL.add(0, null);
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void add_FailWithNegativeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add(-1, "abc");
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void add_FailWithLargeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.add(99, "abc");
    }
    
    @Test
    public void contains_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        final String value = "abc";
        Assert.assertTrue(!UAL.contains(value));
        UAL.add(value);
        Assert.assertTrue(UAL.contains(value));
    }
    
    @Test
    public void indexOf_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        final String value1 = "abc";
        final String value2 = "def";
        Assert.assertEquals(-1, UAL.indexOf(value1));
        Assert.assertEquals(-1, UAL.lastIndexOf(value1));
        UAL.add(value1);
        Assert.assertEquals(0, UAL.indexOf(value1));
        Assert.assertEquals(0, UAL.lastIndexOf(value1));
        
        Assert.assertEquals(-1, UAL.indexOf(value2));
        Assert.assertEquals(-1, UAL.lastIndexOf(value2));
        UAL.add(value2);
        Assert.assertEquals(1, UAL.indexOf(value2));
        Assert.assertEquals(1, UAL.lastIndexOf(value2));
        
        UAL.clear();
        Assert.assertEquals(-1, UAL.indexOf(value1));
        Assert.assertEquals(-1, UAL.lastIndexOf(value1));
        Assert.assertEquals(-1, UAL.indexOf(value2));
        Assert.assertEquals(-1, UAL.lastIndexOf(value2));
        
        UAL.add(value1);
        UAL.add(0, value2);
        Assert.assertEquals(1, UAL.indexOf(value1));
        Assert.assertEquals(1, UAL.lastIndexOf(value1));
        Assert.assertEquals(0, UAL.indexOf(value2));
        Assert.assertEquals(0, UAL.lastIndexOf(value2));
    }
    
    @Test
    public void clone_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UniqueArrayList<String> UAL2 = (UniqueArrayList<String>) UAL.clone();
        Assert.assertEquals(UAL, UAL2);
        final String value1 = "abc";
        UAL.add(value1);
        Assert.assertTrue(UAL.contains(value1));
        Assert.assertTrue(!UAL2.contains(value1));
    }
    
    @Test
    public void toArray_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        CharSequence[] arr1 = new CharSequence[0];
        arr1 = UAL.toArray(arr1);
        Assert.assertEquals(new CharSequence[0], arr1);
        final String value1 = "abc";
        UAL.add(value1);
        arr1 = UAL.toArray(arr1);
        Assert.assertEquals(new CharSequence[] { value1 }, arr1);
        
        String[] arr2 = new String[0];
        arr2 = UAL.toArray(arr2);
        Assert.assertEquals(new CharSequence[] { value1 }, arr2);
        Assert.assertEquals(new String[] { value1 }, arr2);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void toArray_FailWithNull() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.toArray(null);
    }
    
    @Test
    public void get_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        _assertEqualsList(list, UAL);
        
        list = _backInsert(UAL);
        _assertEqualsList(list, UAL);
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void get_FailWithNegativeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.get(-1);
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void get_FailWithLargeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.get(0);
    }
    
    @Test
    public void set_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        int size = list.size();
        for (int i = 0; i < size; ++i) {
            String x = UUID.randomUUID().toString();
            UAL.set(i, x);
            list.set(i, x);
        }
        _assertEqualsList(list, UAL);
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void set_FailWithNegativeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.set(-1, "abc");
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void set_FailWithLargeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.set(0, "abc");
    }
    
    @Test
    public void remove_PassRemoveHead() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        while (!list.isEmpty()) {
            int index = 0;
            String x = list.remove(index);
            Assert.assertEquals(UAL.indexOf(x), index);
            Assert.assertEquals(UAL.lastIndexOf(x), index);
            String y = UAL.remove(index);
            Assert.assertEquals(x, y);
        }
    }
    
    @Test
    public void remove_PassRemoveTail() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        while (!list.isEmpty()) {
            int index = list.size() - 1;
            String x = list.remove(index);
            Assert.assertEquals(UAL.indexOf(x), index);
            Assert.assertEquals(UAL.lastIndexOf(x), index);
            String y = UAL.remove(index);
            Assert.assertEquals(x, y);
        }
    }
    
    @Test
    public void remove_PassRemoveRandom() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        while (!list.isEmpty()) {
            int index = _random.nextInt(list.size());
            String x = list.remove(index);
            Assert.assertEquals(UAL.indexOf(x), index);
            Assert.assertEquals(UAL.lastIndexOf(x), index);
            String y = UAL.remove(index);
            Assert.assertEquals(x, y);
        }
    }
    
    @Test
    public void remove_PassRemoveRandom2() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        while (!list.isEmpty()) {
            int index = _random.nextInt(list.size());
            String x = list.remove(index);
            Assert.assertEquals(UAL.indexOf(x), index);
            Assert.assertEquals(UAL.lastIndexOf(x), index);
            Assert.assertTrue(!UAL.remove(x + "blah"));
            Assert.assertTrue(UAL.remove(x));
        }
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void remove_FailWithNegativeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.remove(-1);
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void remove_FailWithLargeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.remove(0);
    }
    
    @Test
    public void clear_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.clear();
        _assertEqualsList(new ArrayList<String>(), UAL);
        UAL.clear();
        _assertEqualsList(new ArrayList<String>(), UAL);
        UAL.add("abc");
        _assertEqualsList(new ArrayList<String>(Arrays.asList("abc")), UAL);
        UAL.clear();
        _assertEqualsList(new ArrayList<String>(), UAL);
        ArrayList<String> list = _frontInsert(UAL);
        _assertEqualsList(list, UAL);
        UAL.clear();
        _assertEqualsList(new ArrayList<String>(), UAL);
    }
    
    @Test
    public void addAll_PassWithEmptyCollection() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        _assertEqualsList(list, UAL);
        UAL.addAll(new ArrayList<String>());
        _assertEqualsList(list, UAL);
        UAL.addAll(0, new ArrayList<String>());
        _assertEqualsList(list, UAL);
        UAL.addAll(list.size() / 2, new ArrayList<String>());
        _assertEqualsList(list, UAL);
    }
    
    @Test
    public void addAll_PassWithNonEmptyCollection() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        UniqueArrayList<String> UAL2 = new UniqueArrayList<String>();
        ArrayList<String> list2 = _frontInsert(UAL2);
        _assertEqualsList(list, UAL);
        _assertEqualsList(list2, UAL2);
        UAL.addAll(UAL2);
        list.addAll(list2);
        _assertEqualsList(list, UAL);
    }
    
    @Test
    public void addAll_PassWithNonEmptyCollection2() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        UniqueArrayList<String> UAL2 = new UniqueArrayList<String>();
        ArrayList<String> list2 = _frontInsert(UAL2);
        _assertEqualsList(list, UAL);
        _assertEqualsList(list2, UAL2);
        UAL.addAll(0, UAL2);
        list.addAll(0, list2);
        _assertEqualsList(list, UAL);
    }
    
    @Test
    public void addAll_PassWithNonEmptyCollection3() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        UniqueArrayList<String> UAL2 = new UniqueArrayList<String>();
        ArrayList<String> list2 = _frontInsert(UAL2);
        _assertEqualsList(list, UAL);
        _assertEqualsList(list2, UAL2);
        UAL.addAll(list.size() / 2, UAL2);
        list.addAll(list.size() / 2, list2);
        _assertEqualsList(list, UAL);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void addAll_FailWithNull() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.addAll(null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addAll_FailWithDuplicate() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        ArrayList<String> list = _frontInsert(UAL);
        UAL.addAll(list);
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void addAll_FailWithNegativeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.addAll(-1, new ArrayList<String>());
    }
    
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void addAll_FailWithLargeIndex() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.addAll(99, new ArrayList<String>());
    }
    
    @Test
    public void containsAll_Pass() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        Assert.assertTrue(UAL.containsAll(new ArrayList<String>()));
        ArrayList<String> list = _frontInsert(UAL);
        Assert.assertTrue(UAL.containsAll(list));
        Collections.shuffle(list);
        Assert.assertTrue(UAL.containsAll(list));
        list.add("abc");
        Assert.assertTrue(!UAL.containsAll(list));
        list.remove("abc");
        UAL.remove(0);
        Assert.assertTrue(!UAL.containsAll(list));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void containsAll_FailWithNull() {
        UniqueArrayList<String> UAL = new UniqueArrayList<String>();
        UAL.containsAll(null);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //
    
    private static ArrayList<String> _backInsert(UniqueArrayList<String> UAL) {
        UAL.clear();
        int size = 99;
        ArrayList<String> list = new ArrayList<String>(size);
        for (int i = 0; i < size; ++i) {
            String x = UUID.randomUUID().toString();
            UAL.add(x);
            list.add(x);
        }
        return list;
    }
    
    private static ArrayList<String> _frontInsert(UniqueArrayList<String> UAL) {
        UAL.clear();
        int size = 99;
        ArrayList<String> list = new ArrayList<String>(size);
        for (int i = 0; i < size; ++i) {
            String x = UUID.randomUUID().toString();
            UAL.add(0, x);
            list.add(0, x);
        }
        return list;
    }
    
    private static <T> void _assertEqualsList(List<T> expectedList, UniqueArrayList<T> UAL) {
        // Forward iteration
        int size = expectedList.size();
        for (int i = 0; i < size; ++i) {
            T x = expectedList.get(i);
            Assert.assertEquals(UAL.get(i), x);
            int firstIndex = UAL.indexOf(x);
            int lastIndex = UAL.lastIndexOf(x);
            Assert.assertEquals(firstIndex, i);
            Assert.assertEquals(lastIndex, i);
            Assert.assertTrue(UAL.contains(x));
        }
        // Reverse iteration
        for (int i = size - 1; i >= 0; --i) {
            T x = expectedList.get(i);
            Assert.assertEquals(UAL.get(i), x);
            int firstIndex = UAL.indexOf(x);
            int lastIndex = UAL.lastIndexOf(x);
            Assert.assertEquals(firstIndex, i);
            Assert.assertEquals(lastIndex, i);
            Assert.assertTrue(UAL.contains(x));
        }
        Assert.assertEquals(UAL, expectedList);
    }
}
