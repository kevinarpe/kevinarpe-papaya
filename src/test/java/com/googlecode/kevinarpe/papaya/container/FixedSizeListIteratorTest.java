package com.googlecode.kevinarpe.papaya.container;

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

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class FixedSizeListIteratorTest {

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeListIterator.of()
    //

    @Test
    public void of_PassWithModifiableIterator() {
        Assert.assertNotNull(FixedSizeListIterator.of(new ArrayList<String>().listIterator()));
    }

    @Test
    public void of_PassWithFixedSizeListIterator() {
        FixedSizeListIterator<String> iter =
            FixedSizeListIterator.of(Lists.newArrayList("abc", "def", "ghi").listIterator());
        Assert.assertEquals(iter, FixedSizeListIterator.of(iter));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        FixedSizeListIterator.of(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeIterator.add()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail() {
        FixedSizeListIterator.of(new ArrayList<String>().listIterator()).add("abc");
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeIterator.set()
    //

    @Test
    public void set_Pass() {
        List<String> list = Lists.newArrayList("abc");
        Assert.assertEquals("abc", list.get(0));
        ListIterator<String> iter = FixedSizeListIterator.of(list.listIterator());
        Assert.assertEquals("abc", iter.next());
        iter.set("def");
        Assert.assertEquals("def", list.get(0));
    }

    ///////////////////////////////////////////////////////////////////////////
    // FixedSizeIterator.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        FixedSizeListIterator.of(new ArrayList<String>().listIterator()).remove();
    }
}
