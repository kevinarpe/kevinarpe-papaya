/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.Args.ObjectArgUtil;

public class ObjectArgUtilTests {

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
		ObjectArgUtil.staticCheckNotNull(x, "x");
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
		ObjectArgUtil.staticCheckNotNull(value, "value");
	}
}
