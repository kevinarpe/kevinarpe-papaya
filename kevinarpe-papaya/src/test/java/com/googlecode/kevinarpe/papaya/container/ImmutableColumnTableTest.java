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
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableColumnTableTest {

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTable.of
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void of_FailWhenZeroColumns() {

        ImmutableColumnTable.of(ImmutableMap.of());
    }

    @Test
    public void of_PassWhenEmpty() {

        ImmutableColumnTable.of(ImmutableMap.of("abc", ImmutableList.of()));
    }

    @Test
    public void of_PassWhenNotEmpty() {

        ImmutableColumnTable.of(ImmutableMap.of("abc", ImmutableList.of("123")));
    }

    @Test
    public void of_PassWhenNotEmpty2() {

        ImmutableColumnTable.of(ImmutableMap.of(
            "abc", ImmutableList.of("123", "456"),
            "def", ImmutableList.of("456", "789")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void of_FailWhenNotEmpty() {

        ImmutableColumnTable.of(ImmutableMap.of(
            "abc", ImmutableList.of("123", "456"),
            "def", ImmutableList.of("456")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void of_FailWhenNotEmpty2() {

        ImmutableColumnTable.of(ImmutableMap.of(
            "abc", ImmutableList.of("123"),
            "def", ImmutableList.of("456", "789")));
    }

    ///////////////////////////////////////////////////////////////////////////
    // ImmutableColumnTable.copyOf
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenZeroColumns() {

        ImmutableColumnTable.copyOf(ImmutableMap.of());
    }

    @Test
    public void copyOf_PassWhenEmpty() {

        ImmutableColumnTable.copyOf(ImmutableMap.of("abc", ImmutableList.of()));
    }

    @Test
    public void copyOf_PassWhenNotEmpty() {

        ImmutableColumnTable.copyOf(ImmutableMap.of("abc", ImmutableList.of("123")));
    }

    @Test
    public void copyOf_PassWhenNotEmpty2() {

        ImmutableColumnTable.copyOf(ImmutableMap.of(
            "abc", ImmutableList.of("123", "456"),
            "def", ImmutableList.of("456", "789")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenNotEmpty() {

        ImmutableColumnTable.copyOf(ImmutableMap.of(
            "abc", ImmutableList.of("123", "456"),
            "def", ImmutableList.of("456")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_FailWhenNotEmpty2() {

        ImmutableColumnTable.copyOf(ImmutableMap.of(
            "abc", ImmutableList.of("123"),
            "def", ImmutableList.of("456", "789")));
    }

    @Test
    public void copyOf_PassWhenBaseType() {

        ImmutableColumnTable.<Number, Number>copyOf(ImmutableMap.<Double, List<Double>>of(
            1.5d, ImmutableList.of(123d, 456d),
            2.5d, ImmutableList.of(456d, 789d)));

        ImmutableColumnTable.<Double, Number>copyOf(ImmutableMap.<Double, List<Double>>of(
            1.5d, ImmutableList.of(123d, 456d),
            2.5d, ImmutableList.of(456d, 789d)));

        ImmutableColumnTable.<Number, Double>copyOf(ImmutableMap.<Double, List<Double>>of(
            1.5d, ImmutableList.of(123d, 456d),
            2.5d, ImmutableList.of(456d, 789d)));

        ImmutableColumnTable.<Double, Double>copyOf(ImmutableMap.<Double, List<Double>>of(
            1.5d, ImmutableList.of(123d, 456d),
            2.5d, ImmutableList.of(456d, 789d)));
    }
}
