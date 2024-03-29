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

public class FixedSizeForwardingListTest {

    private static class FixedSizeForwardingListImpl
    extends FixedSizeForwardingList<String> {

        private final List<String> list = Lists.newArrayList("abc", "def", "ghi");

        @Override
        protected List<String> delegate() {
            return list;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeForwardingList.listIterator()
    //

    @Test
    public void listIterator_Pass() {
        Assert.assertEquals(
            FixedSizeListIterator.class,
            new FixedSizeForwardingListImpl().listIterator().getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeForwardingList.listIterator(int)
    //

    @Test
    public void listIterator_Pass2() {
        Assert.assertEquals(
            FixedSizeListIterator.class,
            new FixedSizeForwardingListImpl().listIterator(1).getClass());
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeForwardingList.subList(int, int)
    //

    @Test
    public void subList_Pass() {
        List<String> sublist = new FixedSizeForwardingListImpl().subList(1, 2);
        Assert.assertTrue(sublist instanceof FixedSizeForwardingList);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void subList_FailOnRemove() {
        List<String> sublist = new FixedSizeForwardingListImpl().subList(1, 2);
        sublist.remove(0);
    }
}
