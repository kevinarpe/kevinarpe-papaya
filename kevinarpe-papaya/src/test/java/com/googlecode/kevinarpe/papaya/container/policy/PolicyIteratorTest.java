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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PolicyIteratorTest {

    public static final ImmutableSet<ImmutableSet<DoNotAllow>> DO_NOW_ALLOW_SET_SET =
        ImmutableSet.of(
            ImmutableSet.of(),
            ImmutableSet.of(DoNotAllow.NullKey),
            ImmutableSet.of(DoNotAllow.NullKey, DoNotAllow.NullValue),
            ImmutableSet.of(DoNotAllow.NullKey, DoNotAllow.Remove),
            ImmutableSet.of(DoNotAllow.NullKey, DoNotAllow.NullValue, DoNotAllow.Remove),
            ImmutableSet.of(DoNotAllow.NullValue),
            ImmutableSet.of(DoNotAllow.NullValue, DoNotAllow.Remove),
            ImmutableSet.of(DoNotAllow.Remove)
        );

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyIterator.doNotAllowSet()
    //

    @Test
    public void doNotAllowSet_Pass() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : DO_NOW_ALLOW_SET_SET) {

            final PolicyIterator<String> iter = new PolicyIterator<>(list.iterator(), doNotAllowSet);
            Assert.assertEquals(iter.doNotAllowSet(), doNotAllowSet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyIterator.remove()
    //

    @Test
    public void remove_PassWhenNotAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyIterator<String> iter = new PolicyIterator<>(list.iterator(), ImmutableSet.of(DoNotAllow.Remove));
        Assert.assertEquals("abc", iter.next());
        Assert.assertThrows(UnsupportedOperationException.class, () -> iter.remove());
        Assert.assertEquals(list, ImmutableList.of("abc", "def", "ghi"));
    }

    @Test
    public void remove_PassWhenAllowed() {

        final ArrayList<String> list = new ArrayList<>(ImmutableList.of("abc", "def", "ghi"));
        final PolicyIterator<String> iter = new PolicyIterator<>(list.iterator(), ImmutableSet.of());
        Assert.assertEquals("abc", iter.next());
        iter.remove();
        Assert.assertEquals(list, ImmutableList.of("def", "ghi"));
    }
}
