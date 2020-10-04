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

import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PolicyMapEntryTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMapEntry.doNotAllowSet()
    //

    @Test
    public void doNotAllowSet_Pass() {

        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("abc", 123);

        for (final ImmutableSet<DoNotAllow> doNotAllowSet : PolicyIteratorTest.DO_NOW_ALLOW_SET_SET) {

            final PolicyMapEntry<String, Integer> c = new PolicyMapEntry<>(entry, doNotAllowSet);
            Assert.assertEquals(c.doNotAllowSet(), doNotAllowSet);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PolicyMapEntry.setValue(Object)
    //

    @Test
    public void setValue_PassWhenNullNotAllowed() {

        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("abc", 123);
        final PolicyMapEntry<String, Integer> c = new PolicyMapEntry<>(entry, ImmutableSet.of(DoNotAllow.NullValue));
        Assert.assertThrows(UnsupportedOperationException.class, () -> c.setValue((Integer) null));
        Assert.assertEquals(c.getValue(), Integer.valueOf(123));
        Assert.assertEquals(c.setValue(456), Integer.valueOf(123));
        Assert.assertEquals(c.getValue(), Integer.valueOf(456));
   }

    @Test
    public void setValue_PassWhenNullAllowed() {

        Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("abc", 123);
        final PolicyMapEntry<String, Integer> c = new PolicyMapEntry<>(entry, ImmutableSet.of());
        Assert.assertEquals(c.getValue(), Integer.valueOf(123));
        Assert.assertEquals(c.setValue((Integer) null), Integer.valueOf(123));
        Assert.assertEquals(c.getValue(), (Integer) null);
    }
}
