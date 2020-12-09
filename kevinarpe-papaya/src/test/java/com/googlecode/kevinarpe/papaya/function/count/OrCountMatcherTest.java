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
public class OrCountMatcherTest {

    @Test
    public void pass() {

        // x <= 3 || x >= 5
        final AtLeastCountMatcher min = new AtLeastCountMatcher(5);
        final AtLeastCountMatcher min2 = new AtLeastCountMatcher(5);
        final AtMostCountMatcher max = new AtMostCountMatcher(3);
        final AtMostCountMatcher max2 = new AtMostCountMatcher(3);
        final CountMatcher min_or_max = min.or(max);
        final CountMatcher min2_or_max = min2.or(max);
        final CountMatcher max_or_min = max.or(min);
        final CountMatcher max2_or_min = max2.or(min);

        /// isMatch

        _assertIsMatch(min_or_max, max_or_min, max.max, Assert::assertTrue);

        for (int value = min.min + 1; value <= max.max - 1; ++value) {

            _assertIsMatch(min_or_max, max_or_min, value, Assert::assertFalse);
        }
        _assertIsMatch(min_or_max, max_or_min, min.min, Assert::assertTrue);

        /// hashCode

        Assert.assertEquals(min_or_max.hashCode(), min2_or_max.hashCode());
        Assert.assertEquals(max_or_min.hashCode(), max2_or_min.hashCode());

        /// equals

        Assert.assertTrue(min_or_max.equals(min2_or_max));
        Assert.assertTrue(max_or_min.equals(max2_or_min));

        /// toString

        Assert.assertEquals(min_or_max.toString(), "(at least 5) or (at most 3)");
        Assert.assertEquals(min_or_max.toString(), min2_or_max.toString());
        Assert.assertEquals(max_or_min.toString(), "(at most 3) or (at least 5)");
        Assert.assertEquals(max_or_min.toString(), max2_or_min.toString());
    }

    private void
    _assertIsMatch(CountMatcher m_and_m2, CountMatcher m2_and_m, final int value, Consumer<Boolean> assertFunc) {

        assertFunc.accept(m_and_m2.isMatch(value));
        assertFunc.accept(m2_and_m.isMatch(value));
    }
}
