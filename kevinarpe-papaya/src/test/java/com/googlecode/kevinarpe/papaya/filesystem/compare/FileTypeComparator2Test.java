package com.googlecode.kevinarpe.papaya.filesystem.compare;

/*
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

import com.googlecode.kevinarpe.papaya.filesystem.FileType;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

/**
 * This special test class only exists to segregate Powermock tests from others.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest(FileType.class)
public class FileTypeComparator2Test
extends PowerMockTestCase {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileTypeComparator.compare()
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void compare_FailWhenUnableToDetermineFileType()
        throws IOException {
        IOException anIOException = new IOException("blah");
        PowerMockito.mockStatic(FileType.class);
        when(FileType.from(any(File.class))).thenThrow(anIOException);
        File path1 = new File("dummy1");
        File path2 = new File("dummy2");
        try {
            new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE)).compare(path1, path2);
        }
        catch (IllegalArgumentException e) {
            assertSame(e.getCause(), anIOException);
            throw e;
        }
    }
}
