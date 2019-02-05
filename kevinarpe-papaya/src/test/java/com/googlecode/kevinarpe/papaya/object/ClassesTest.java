package com.googlecode.kevinarpe.papaya.object;

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

import com.googlecode.kevinarpe.papaya.string.MessageFormatterImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ClassesTest {

    private static final class _Class {

        public _Class() {
            // Empty
        }

        public _Class(String x, long[] y, boolean z) {
            // Empty
        }
    }

    private static abstract class _AbstractClass {

        public _AbstractClass() {
            // Empty
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Classes.getConstructorOrThrow()
    //

    @Test
    public void getConstructorOrThrow_Pass() {

        final Constructor<_Class> x = Classes.getConstructorOrThrow(_Class.class);
        Assert.assertNotNull(x);
    }

    @Test
    public void getConstructorOrThrow2_Pass() {

        final Constructor<_Class> x =
            Classes.getConstructorOrThrow(_Class.class, String.class, long[].class, Boolean.TYPE);
        Assert.assertNotNull(x);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getConstructorOrThrow_FailWhenWrappedBoolean() {
        try {
            Classes.getConstructorOrThrow(_Class.class, String.class, long[].class, Boolean.class);
        }
        catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(),"Failed to find constructor: _Class(String, long[], Boolean)");
            throw e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Classes.newInstanceOrThrow()
    //

    @Test
    public void newInstanceOrThrow_Pass() {

        final Constructor<_Class> ctor = Classes.getConstructorOrThrow(_Class.class);
        final _Class x = Classes.newInstanceOrThrow(ctor);
        Assert.assertNotNull(x);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void newInstanceOrThrow_FailWhenMissingCtorArgs() {

        final Constructor<_Class> ctor =
            Classes.getConstructorOrThrow(_Class.class, String.class, long[].class, Boolean.TYPE);
        Classes.newInstanceOrThrow(ctor);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void newInstanceOrThrow_FailWhenAbstract() {

        final Constructor<_AbstractClass> ctor = Classes.getConstructorOrThrow(_AbstractClass.class);
        Classes.newInstanceOrThrow(ctor);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void newInstanceOrThrow_FailWhenAbstract2() {

        final Constructor<_AbstractClass> ctor = Classes.getConstructorOrThrow(_AbstractClass.class);
        try {
            Classes.newInstanceOrThrow(ctor, "abc", "def", null, 123);
        }
        catch (IllegalArgumentException e) {
            final String expectedMessage =
                MessageFormatterImpl.INSTANCE.format(
                    "Failed to call constructor public com.googlecode.kevinarpe.papaya.object.ClassesTest$_AbstractClass() with args:"
                        + "%n\tString: [abc]"
                        + "%n\tString: [def]"
                        + "%n\tnull"
                        + "%n\tInteger: [123]");
            Assert.assertEquals(e.getMessage(), expectedMessage);
            throw e;
        }
    }
}
