package com.googlecode.kevinarpe.papaya.container;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableListMultimap;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableColumnTableTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNegativeRowCount() {

        new ImmutableColumnTable<String, String>(-4, ImmutableListMultimap.of());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNegativeRowCount2() {

        new ImmutableColumnTable<String, String>(-4, ImmutableListMultimap.of("abc", "def"));
    }

    @Test
    public void ctor_PassWhenEmpty() {

        new ImmutableColumnTable<String, String>(0, ImmutableListMultimap.of());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenEmpty() {

        new ImmutableColumnTable<String, String>(5, ImmutableListMultimap.of());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNotEmpty() {

        new ImmutableColumnTable<String, String>(0, ImmutableListMultimap.of("abc", "def"));
    }

    @Test
    public void ctor_PassWhenNotEmpty() {

        new ImmutableColumnTable<String, String>(2,
            ImmutableListMultimap.of(
                "abc", "123",
                "abc", "456",
                "def", "234",
                "def", "567"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNotEmpty2() {

        new ImmutableColumnTable<String, String>(3,
            ImmutableListMultimap.of(
                "abc", "123",
                "abc", "456",
                "def", "234",
                "def", "567"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNotEmpty3() {

        new ImmutableColumnTable<String, String>(3,
            ImmutableListMultimap.of(
                "abc", "123",
                "abc", "456",
                "abc", "789",
                "def", "234",
                "def", "567"));
    }
}
