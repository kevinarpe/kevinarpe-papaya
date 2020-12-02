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

import static org.testng.Assert.*;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class NotCountMatcherTest {

    @Test
    public void pass() {

        // x >= 5
        final AtLeastCountMatcher min = new AtLeastCountMatcher(5);
        final CountMatcher not_min = min.not();
        final CountMatcher not_not_min = not_min.not();
        Assert.assertSame(min, not_not_min);
        final AtLeastCountMatcher min2 = new AtLeastCountMatcher(5);
        final CountMatcher not_min2 = min2.not();

        /// isMatch

        Assert.assertFalse(min.isMatch(4));
        Assert.assertTrue(min.isMatch(5));
        Assert.assertTrue(min.isMatch(6));

        Assert.assertFalse(not_not_min.isMatch(4));
        Assert.assertTrue(not_not_min.isMatch(5));
        Assert.assertTrue(not_not_min.isMatch(6));

        Assert.assertTrue(not_min.isMatch(4));
        Assert.assertFalse(not_min.isMatch(5));
        Assert.assertFalse(not_min.isMatch(6));

        /// hashCode

        Assert.assertNotEquals(min.hashCode(), not_min.hashCode());
        Assert.assertEquals(min.hashCode(), not_not_min.hashCode());
        Assert.assertNotEquals(not_min.hashCode(), not_not_min.hashCode());

        /// equals

        Assert.assertTrue(min.equals(not_not_min));
        Assert.assertFalse(min.equals(not_min));
        Assert.assertFalse(not_min.equals(not_not_min));
        Assert.assertTrue(not_min.equals(not_min2));

        /// toString

        Assert.assertEquals(min.toString(), "at least 5");
        Assert.assertEquals(not_min.toString(), "not at least 5");
        Assert.assertEquals(not_not_min.toString(), "at least 5");
    }
}
