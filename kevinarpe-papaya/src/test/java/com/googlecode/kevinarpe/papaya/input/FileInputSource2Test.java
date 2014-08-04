package com.googlecode.kevinarpe.papaya.input;

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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStreamReader;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class FileInputSource2Test {

    File mockFile;
    InputStreamReader mockInputStreamReader;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockFile = mock(File.class);
        mockInputStreamReader = mock(InputStreamReader.class);
    }

    @Test
    public void ctor_Pass() {
        FileInputSource2 classUnderTest = new FileInputSource2(mockFile, mockInputStreamReader);
        assertNull(classUnderTest.getByteStream());
        assertNotNull(classUnderTest.getCharacterStream());
        assertNotNull(classUnderTest.toString());
    }
}