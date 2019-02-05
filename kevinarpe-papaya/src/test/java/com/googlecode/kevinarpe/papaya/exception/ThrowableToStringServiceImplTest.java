package com.googlecode.kevinarpe.papaya.exception;

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

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ThrowableToStringServiceImplTest {

    @Test
    public void toStringWithUniqueStackTrace_Pass() {

        final ThrowableToStringServiceImpl classUnderTest = new ThrowableToStringServiceImpl();

        final String message = "blah";
        final Exception e0 = new Exception(message);

        try {
            throw e0;
        }
        catch (Exception e) {
            final String s = classUnderTest.toStringWithUniqueStackTrace(e);
            Assert.assertTrue(s.contains(message));
            Assert.assertTrue(s.contains("toStringWithUniqueStackTrace_Pass"));
            Assert.assertTrue(s.contains(this.getClass().getSimpleName()));
        }
        try {
            throw e0;
        }
        catch (Exception e) {
            final String s = classUnderTest.toStringWithUniqueStackTrace(e);
            Assert.assertTrue(s.contains(message));
            Assert.assertFalse(s.contains("toStringWithUniqueStackTrace_Pass"));
            Assert.assertFalse(s.contains(this.getClass().getSimpleName()));
        }
    }
}
