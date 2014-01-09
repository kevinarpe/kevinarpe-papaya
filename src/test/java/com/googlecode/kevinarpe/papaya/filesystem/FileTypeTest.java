package com.googlecode.kevinarpe.papaya.filesystem;

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

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileTypeTest {

    ///////////////////////////////////////////////////////////////////////////
    // FileType.from()
    //

    @Test
    public void from_Pass() throws IOException {
        File mockPath = mock(File.class);

        when(mockPath.isFile()).thenReturn(true);
        when(mockPath.isDirectory()).thenReturn(false);
        assertEquals(FileType.from(mockPath), FileType.NORMAL_FILE);

        when(mockPath.isFile()).thenReturn(false);
        when(mockPath.isDirectory()).thenReturn(true);
        assertEquals(FileType.from(mockPath), FileType.DIRECTORY);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void from_FailWithNull()
    throws IOException {
        FileType.from((File) null);
    }

    @Test(expectedExceptions = IOException.class)
    public void from_FailWithIOException()
    throws IOException {
        File mockPath = mock(File.class);
        when(mockPath.isFile()).thenReturn(false);
        when(mockPath.isDirectory()).thenReturn(false);

        FileType.from(mockPath);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileType.is()
    //

    @Test
    public void is_Pass() {
        File mockPath = mock(File.class);

        when(mockPath.isFile()).thenReturn(true);
        when(mockPath.isDirectory()).thenReturn(false);
        assertTrue(FileType.NORMAL_FILE.is(mockPath));
        assertFalse(FileType.DIRECTORY.is(mockPath));

        when(mockPath.isFile()).thenReturn(false);
        when(mockPath.isDirectory()).thenReturn(true);
        assertTrue(FileType.DIRECTORY.is(mockPath));
        assertFalse(FileType.NORMAL_FILE.is(mockPath));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void is_FailWithNull() {
        FileType.NORMAL_FILE.is((File) null);
    }
}
