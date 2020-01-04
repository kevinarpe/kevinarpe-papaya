package com.googlecode.kevinarpe.papaya.container;

/*
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

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BaseFixedSizeForwardingListTest {

    private static class BaseFixedSizeForwardingListImpl
    extends BaseFixedSizeForwardingList<String> {

        private final List<String> list = Lists.newArrayList("abc", "def", "ghi");

        protected BaseFixedSizeForwardingListImpl() {
            super("sample");
        }

        @Override
        protected List<String> delegate() {
            return list;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.add(int, TValue)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail() {
        new BaseFixedSizeForwardingListImpl().add(99, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.addAll(int, Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAll_Fail() {
        new BaseFixedSizeForwardingListImpl().addAll(99, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.remove(int)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        new BaseFixedSizeForwardingListImpl().remove(99);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.iterator()
    //

    @Test
    public void iterator_Pass() {
        Assert.assertEquals(
            UnmodifiableForwardingIterator.class,
            new BaseFixedSizeForwardingListImpl().iterator().getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.removeAll(Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeAll_Fail() {
        new BaseFixedSizeForwardingListImpl().removeAll(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.add(TValue)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail2() {
        new BaseFixedSizeForwardingListImpl().add(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.remove(Object)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail2() {
        new BaseFixedSizeForwardingListImpl().remove(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.addAll(Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void addAll_Fail2() {
        new BaseFixedSizeForwardingListImpl().addAll(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.retainAll(Collection)
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void retainAll_Fail() {
        new BaseFixedSizeForwardingListImpl().retainAll(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseFixedSizeForwardingList.clear()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void clear_Fail() {
        new BaseFixedSizeForwardingListImpl().clear();
    }

}
