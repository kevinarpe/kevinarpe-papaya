package com.googlecode.kevinarpe.papaya.function;

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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ThrowingRunnableTest {

    @Test
    public void asRunnable_PassWhenNotThrow() {

        final ThrowingRunnable tc = () -> {};
        final Runnable c = tc.asRunnable();
        c.run();
    }

    @Test
    public void asRunnable_PassWhenThrow() {

        final Exception e = new Exception("dummy");
        final ThrowingRunnable tc = () -> { throw e; };
        final Runnable c = tc.asRunnable();
        try {
            c.run();
            throw new IllegalStateException("Unreachable code");
        }
        catch (Exception e2) {
            Assert.assertSame(e2.getCause(), e);
        }
    }
}
