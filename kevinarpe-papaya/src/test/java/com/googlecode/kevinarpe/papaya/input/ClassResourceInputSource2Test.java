package com.googlecode.kevinarpe.papaya.input;

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

import com.googlecode.kevinarpe.papaya.argument.PathArgsTest;
import com.googlecode.kevinarpe.papaya.exception.ClassResourceNotFoundException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class ClassResourceInputSource2Test {

    private static final Class<?> CLAZZ = PathArgsTest.class;
    private static final String SAMPLE_RESOURCE_ABSOLUTE_PATHNAME =
        PathArgsTest.SAMPLE_RESOURCE_ABSOLUTE_PATHNAME;

    @Test
    public void ctor_Pass()
    throws ClassResourceNotFoundException {
        ClassResourceInputSource2 classUnderTest =
            new ClassResourceInputSource2(CLAZZ, SAMPLE_RESOURCE_ABSOLUTE_PATHNAME);
        assertNotNull(classUnderTest.getByteStream());
        assertNull(classUnderTest.getCharacterStream());
        assertNotNull(classUnderTest.toString());
    }
}
