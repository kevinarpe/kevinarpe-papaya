package com.googlecode.kevinarpe.papaya.jdk.properties;

/*
 * #%L
 * This file is part of Papaya (JDK Derived Classes).
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya (JDK Derived Classes) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only as
 * published by the Free Software Foundation.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in the LICENSE
 * file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Special notice for this module of Papaya:
 * Classes in this module may contain significant portions that are originally
 * part of the JDK source base.  In such cases, prominent notices appear before
 * these blocks of source code.
 * #L%
 */

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import static org.testng.Assert.assertEquals;

public class JavaPropertiesLoaderImplTest {

    // TODO: Why don't Japanese kanji work here?
    private static final String DATA = String.format("abc=def%nabc=def%nabc=ghi%ndef=ghi");

    private JavaPropertiesLoaderImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new JavaPropertiesLoaderImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JavaPropertiesLoaderImpl.load(Reader)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void loadReader_FailWithNull()
    throws IOException {
        classUnderTest.load((Reader) null);
    }

    @Test
    public void loadReader_Pass()
    throws IOException {
        StringReader characterStream = new StringReader(DATA);
        ImmutableList<JavaProperty> list = classUnderTest.load(characterStream);
        _assertEquals(list);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JavaPropertiesLoaderImpl.load(Reader)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void loadInputStream_FailWithNull()
    throws IOException {
        classUnderTest.load((InputStream) null);
    }

    @Test
    public void loadInputStream_Pass()
    throws IOException {
        InputStream byteStream = new ByteArrayInputStream(DATA.getBytes(Charsets.ISO_8859_1));
        ImmutableList<JavaProperty> list = classUnderTest.load(byteStream);
        _assertEquals(list);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _assertEquals(ImmutableList<JavaProperty> list) {
        assertEquals(list.get(0).getKey(), "abc");
        assertEquals(list.get(0).getValue(), "def");
        assertEquals(list.get(1).getKey(), "abc");
        assertEquals(list.get(1).getValue(), "def");
        assertEquals(list.get(2).getKey(), "abc");
        assertEquals(list.get(2).getValue(), "ghi");
        assertEquals(list.get(3).getKey(), "def");
        assertEquals(list.get(3).getValue(), "ghi");
    }
}
