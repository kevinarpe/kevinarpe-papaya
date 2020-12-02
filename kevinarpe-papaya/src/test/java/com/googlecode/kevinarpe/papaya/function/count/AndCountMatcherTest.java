package com.googlecode.kevinarpe.papaya.function.count;

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

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.function.Consumer;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class AndCountMatcherTest {

    @Test
    public void pass() {

        // x >= 3 && x <= 5
        final AtLeastCountMatcher min = new AtLeastCountMatcher(3);
        final AtLeastCountMatcher min2 = new AtLeastCountMatcher(3);
        final AtMostCountMatcher max = new AtMostCountMatcher(5);
        final AtMostCountMatcher max2 = new AtMostCountMatcher(5);
        final CountMatcher min_and_max = min.and(max);
        final CountMatcher min2_and_max = min2.and(max);
        final CountMatcher max_and_min = max.and(min);
        final CountMatcher max2_and_min = max2.and(min);

        /// isMatch

        _assertIsMatch(min_and_max, max_and_min, min.min - 1, Assert::assertFalse);

        for (int value = min.min; value <= max.max; ++value) {

            _assertIsMatch(min_and_max, max_and_min, value, Assert::assertTrue);
        }
        _assertIsMatch(min_and_max, max_and_min, max.max + 1, Assert::assertFalse);

        /// hashCode

        Assert.assertEquals(min_and_max.hashCode(), min2_and_max.hashCode());
        Assert.assertEquals(max_and_min.hashCode(), max2_and_min.hashCode());

        /// equals

        Assert.assertTrue(min_and_max.equals(min2_and_max));
        Assert.assertTrue(max_and_min.equals(max2_and_min));

        /// toString

        Assert.assertEquals(min_and_max.toString(), "at least 3 and at most 5");
        Assert.assertEquals(min_and_max.toString(), min2_and_max.toString());
        Assert.assertEquals(max_and_min.toString(), "at most 5 and at least 3");
        Assert.assertEquals(max_and_min.toString(), max2_and_min.toString());
    }

    private void
    _assertIsMatch(CountMatcher m_and_m2, CountMatcher m2_and_m, final int value, Consumer<Boolean> assertFunc) {

        assertFunc.accept(m_and_m2.isMatch(value));
        assertFunc.accept(m2_and_m.isMatch(value));
    }
}
