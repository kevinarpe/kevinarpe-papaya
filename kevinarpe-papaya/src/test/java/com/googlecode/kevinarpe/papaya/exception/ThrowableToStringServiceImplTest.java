package com.googlecode.kevinarpe.papaya.exception;

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

import com.googlecode.kevinarpe.papaya.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ThrowableToStringServiceImplTest {

    @Test
    public void toStringWithUniqueStackTrace_Pass() {

        final ThrowableToStringServiceImpl classUnderTest = new ThrowableToStringServiceImpl();

        final String message = "blah";
        final Exception e0 = new Exception(message);

        // This is a trick to all the same exception to be throw twice from same line number.
        for (int i = 1; i <= 2; ++i) {

            try {
                throw e0;
            }
            catch (Exception e) {
                final String s = classUnderTest.toStringWithUniqueStackTrace(e);
                System.out.println(s);
                Assert.assertTrue(s.contains(message));
                Assert.assertTrue(s.contains("toStringWithUniqueStackTrace_Pass"));
                Assert.assertTrue(s.contains("[ID:1]"));
                Assert.assertTrue(s.contains(this.getClass().getSimpleName()));
                Assert.assertEquals(s.contains(StringUtils.NEW_LINE), (1 == i));
            }
        }
        try {
            throw new Exception("dummy");
        }
        catch (Exception e) {

            final String s = classUnderTest.toStringWithUniqueStackTrace(e);
            System.out.println(s);
            Assert.assertTrue(s.contains("dummy"));
            Assert.assertTrue(s.contains("toStringWithUniqueStackTrace_Pass"));
            Assert.assertTrue(s.contains("[ID:2]"));
            Assert.assertTrue(s.contains(this.getClass().getSimpleName()));
            Assert.assertTrue(s.contains(StringUtils.NEW_LINE));
        }
    }
}
