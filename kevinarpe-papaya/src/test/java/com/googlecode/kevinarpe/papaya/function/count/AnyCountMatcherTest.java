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

import com.google.common.primitives.Ints;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class AnyCountMatcherTest {

    @DataProvider
    public static Object[][] _pass_IsMatch() {
        return new Object[][] {
            {
                0,  // exitValue
                Ints.asList(1),  // validExitValueCollection
                new Exception(  // optCause
                    UUID.randomUUID().toString(),
                    new Exception(UUID.randomUUID().toString())),
                "Random: %s",  // format
                new Object[] { UUID.randomUUID().toString() },  // optArgArr
            },
            {
                99,  // exitValue
                Ints.asList(97, 98, 100, 101),  // validExitValueCollection
                new Exception(  // optCause
                    UUID.randomUUID().toString(),
                    new Exception(UUID.randomUUID().toString())),
                UUID.randomUUID().toString(),  // format
                new Object[] { },  // optArgArr
            },
        };
    }

    @Test
    public void pass_isMatch() {

        for (int count = -10; count <= 10; ++count) {

            Assert.assertTrue(AnyCountMatcher.INSTANCE.isMatch(count));
        }
    }

    @Test
    public void pass_hashCode() {

        Assert.assertEquals(AnyCountMatcher.INSTANCE.hashCode(), new AnyCountMatcher().hashCode());
    }

    @Test
    public void pass_equals() {

        Assert.assertFalse(AnyCountMatcher.INSTANCE.equals(new Object()));
        Assert.assertTrue(AnyCountMatcher.INSTANCE.equals(AnyCountMatcher.INSTANCE));
        Assert.assertTrue(AnyCountMatcher.INSTANCE.equals(new AnyCountMatcher()));
    }

    @Test
    public void pass_toString() {

        Assert.assertEquals(AnyCountMatcher.INSTANCE.toString(), "any");
    }
}
