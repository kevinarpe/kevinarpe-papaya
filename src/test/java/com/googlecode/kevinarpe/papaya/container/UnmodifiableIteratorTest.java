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

public class UnmodifiableIteratorTest {

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.of()
    //

    @Test
    public void of_PassWithModifiableIterator() {
        Assert.assertNotNull(UnmodifiableIterator.of(new ArrayList<String>().iterator()));
    }

    @Test
    public void of_PassWithUnmodifiableIterator() {
        UnmodifiableIterator<String> iter =
            UnmodifiableIterator.of(Lists.newArrayList("abc", "def", "ghi").iterator());
        Assert.assertEquals(iter, UnmodifiableIterator.of(iter));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void of_FailWithNull() {
        UnmodifiableIterator.of(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // UnmodifiableIterator.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_Fail() {
        UnmodifiableIterator.of(new ArrayList<String>().iterator()).remove();
    }
}
