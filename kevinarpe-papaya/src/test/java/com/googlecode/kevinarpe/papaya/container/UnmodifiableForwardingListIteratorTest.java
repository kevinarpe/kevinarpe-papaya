package com.googlecode.kevinarpe.papaya.container;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;

public class UnmodifiableForwardingListIteratorTest {

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingListIterator.of()
    //

    @Test
    public void of_PassWithModifiableIterator() {
        Assert.assertNotNull(
            UnmodifiableForwardingListIterator.of(new ArrayList<String>().listIterator()));
    }

    @Test
    public void of_PassWithUnmodifiableListIterator() {
        UnmodifiableForwardingListIterator<String> iter =
            UnmodifiableForwardingListIterator.of(
                Lists.newArrayList("abc", "def", "ghi").listIterator());
        Assert.assertEquals(iter, UnmodifiableForwardingListIterator.of(iter));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        UnmodifiableForwardingListIterator.of(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingIterator.add()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void add_Fail() {
        UnmodifiableForwardingListIterator.of(new ArrayList<String>().listIterator()).add("abc");
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingIterator.set()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void set_Fail() {
        UnmodifiableForwardingListIterator.of(new ArrayList<String>().listIterator()).set("abc");
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableForwardingIterator.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        UnmodifiableForwardingListIterator.of(new ArrayList<String>().listIterator()).remove();
    }
}
