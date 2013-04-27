package com.nfshost.kevinarpe.papaya.Args;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.Args.ObjectArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ObjectArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotNull() {
        return new Object[][] {
                { (byte)7 },
                { (short)7 },
                { (int)7 },
                { (long)7 },
                { (float)7 },
                { (double)7 },
                { (char)7 },
                { "" },
                { "abc" },
                { new Object() },
                { new ArrayList<Integer>() },
                { (boolean)true },
                { (boolean)false },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotNull")
    public void shouldCheckAsNotNull(Object x) {
        ObjectArgs.checkNotNull(x, "x");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNull() {
        return new Object[][] {
                { null, null },
                { null, "value" },
                { 123, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNull",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNull(Object x, String argName) {
        Object value = null;
        ObjectArgs.checkNotNull(value, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
                { null, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNullWithInvalidArgName(Object x, String argName) {
        Object value = null;
        ObjectArgs.checkNotNull(value, argName);
    }
}
