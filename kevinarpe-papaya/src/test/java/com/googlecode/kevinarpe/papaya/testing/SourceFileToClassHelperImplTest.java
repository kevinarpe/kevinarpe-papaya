package com.googlecode.kevinarpe.papaya.testing;

/*
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.UUID;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SourceFileToClassHelperImplTest {

    private static final File THIS_SOURCE_FILE_PATH = new File(
        String.format(
            "%s/%s.java",
            UUID.randomUUID().toString(),
            SourceFileToClassHelperImplTest.class.getName().replace('.', '/')));

    private SourceFileToClassHelperImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new SourceFileToClassHelperImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SourceFileToClassHelperImpl.getClass()
    //

    @Test
    public void getClass_Pass()
    throws ClassNotFoundException {
        Class<?> thisClass = classUnderTest.getClass(THIS_SOURCE_FILE_PATH);
        Assert.assertSame(thisClass, SourceFileToClassHelperImplTest.class);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getClass_FailWithNull()
    throws ClassNotFoundException {
        classUnderTest.getClass((File) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getClass_FailWithBadFileSuffix()
    throws ClassNotFoundException {
        classUnderTest.getClass(new File(UUID.randomUUID().toString()));
    }

    @DataProvider
    private static Object[][] _getClass_FailWithClassNotFoundException_Data() {
        return new Object[][] {
            {
                new File(UUID.randomUUID().toString() + ".java")
            },
            {
                new File(
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString() + ".java")
            },
        };
    }

    @Test(dataProvider = "_getClass_FailWithClassNotFoundException_Data",
            expectedExceptions = ClassNotFoundException.class)
    public void getClass_FailWithClassNotFoundException(File path)
    throws ClassNotFoundException {
        classUnderTest.getClass(path);
    }
}
