package com.googlecode.kevinarpe.papaya.comparator;

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

import com.google.common.testing.EqualsTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CaseSensitiveTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CaseSensitive.compare()
    //

    @DataProvider
    private static Object[][] compare_Pass_Data() {
        return new Object[][] {
            { "abc", "def", CaseSensitive.YES, -1 },
            { "def", "abc", CaseSensitive.YES, +1  },
            { "abc", "abc", CaseSensitive.YES, 0  },
            { "ABC", "def", CaseSensitive.YES, -1  },
            { "def", "ABC", CaseSensitive.YES, +1  },
            { "DEF", "abc", CaseSensitive.YES, -1  },
            { "abc", "DEF", CaseSensitive.YES, +1 },
            { "ABC", "abc", CaseSensitive.YES, -1  },
            { "ABC", "ABC", CaseSensitive.YES, 0  },
            { "DEF", "def", CaseSensitive.YES, -1  },
            { "DEF", "DEF", CaseSensitive.YES, 0  },

            { "abc", "def", CaseSensitive.NO, -1 },
            { "def", "abc", CaseSensitive.NO, +1  },
            { "abc", "abc", CaseSensitive.NO, 0  },
            { "ABC", "def", CaseSensitive.NO, -1  },
            { "def", "ABC", CaseSensitive.NO, +1  },
            { "DEF", "abc", CaseSensitive.NO, +1  },
            { "abc", "DEF", CaseSensitive.NO, -1 },
            { "ABC", "abc", CaseSensitive.NO, 0  },
            { "ABC", "ABC", CaseSensitive.NO, 0  },
            { "DEF", "def", CaseSensitive.NO, 0  },
            { "DEF", "DEF", CaseSensitive.NO, 0  },
        };
    }

    @Test(dataProvider = "compare_Pass_Data")
    public void compare_Pass(String left, String right, CaseSensitive cs, int result) {
        assertEquals(cs.compare(left, right), result);
    }

    @DataProvider
    private static Object[][] compare_Fail_Data() {
        return new Object[][] {
            { "abc", null },
            { null, "abc" },
            { null, null },
        };
    }

    @Test(dataProvider = "compare_Fail_Data")
    public void compare_Fail(String left, String right) {
        try {
            for (CaseSensitive cs : CaseSensitive.values()) {
                cs.compare(left, right);
            }
        }
        catch (NullPointerException e) {
            // ignore
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CaseSensitive.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(CaseSensitive.YES, CaseSensitive.YES)
            .addEqualityGroup(CaseSensitive.NO, CaseSensitive.NO)
            .testEquals();
    }
}
