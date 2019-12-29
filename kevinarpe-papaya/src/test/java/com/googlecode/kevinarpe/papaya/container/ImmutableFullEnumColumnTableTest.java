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

import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ImmutableFullEnumColumnTableTest {

    private enum _Enum { A, B }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNegativeRowCount() {

        new ImmutableFullEnumColumnTable<_Enum, String>(-4,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNegativeRowCount2() {

        new ImmutableFullEnumColumnTable<_Enum, String>(-4,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of("abc")));
    }

    @Test
    public void ctor_PassWhenEmpty() {

        new ImmutableFullEnumColumnTable<_Enum, String>(0,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenEmpty() {

        new ImmutableFullEnumColumnTable<_Enum, String>(5,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNotEmpty() {

        new ImmutableFullEnumColumnTable<_Enum, String>(0,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of("abc")));
    }

    @Test
    public void ctor_PassWhenNotEmpty() {

        new ImmutableFullEnumColumnTable<_Enum, String>(2,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of("abc", "def")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNotEmpty2() {

        new ImmutableFullEnumColumnTable<_Enum, String>(3,
            ImmutableFullEnumMap.ofKeys(_Enum.class, any -> ImmutableList.of("abc", "def")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWhenNotEmpty3() {

        new ImmutableFullEnumColumnTable<_Enum, String>(3,
            ImmutableFullEnumMap.<_Enum, ImmutableList<String>>builder(_Enum.class)
                .put(_Enum.A, ImmutableList.of("abc", "def", "ghi"))
                .put(_Enum.B, ImmutableList.of("abc", "def"))
                .build());
    }
}
