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

package com.nfshost.kevinarpe.papaya.tests;

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.ArgUtil;

public class ArgUtilTests {

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
		ArgUtil.staticCheckNotNull(x, "x");
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
		ArgUtil.staticCheckNotNull(value, "value");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckAsStringNotEmpty() {
		return new Object[][] {
				{ " " },  // ASCII space
				{ "\t" },
				{ "　" },  // wide japanese space
				{ "abc" },
				{ "僕は東京に住んでいました。" },
				{ "我会写中文。" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckAsStringNotEmpty")
	public void shouldCheckAsStringNotEmpty(String s) {
		ArgUtil.staticCheckStringNotEmpty(s, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsStringNotEmptyOrWhitespace() {
		return new Object[][] {
				{ null, null },
				{ "abc", null },
				{ null, "value" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsStringNotEmptyOrWhitespace",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckAsStringNotEmpty(String s, String argName) {
		ArgUtil.staticCheckStringNotEmpty(s, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsStringNotEmpty2() {
		return new Object[][] {
				{ "" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsStringNotEmpty2",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckAsStringNotEmpty2(String s) {
		ArgUtil.staticCheckStringNotEmpty(s, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckStringNotEmptyOrWhitespace() {
		return new Object[][] {
				{ " abc" },  // ASCII space
				{ "\tabc" },
				{ "　abc" },  // wide japanese space
				{ "abc" },
				{ "僕は東京に住んでいました。" },
				{ "我会写中文。" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckStringNotEmptyOrWhitespace")
	public void shouldCheckStringNotEmptyOrWhitespace(String s) {
		ArgUtil.staticCheckStringNotEmptyOrWhitespace(s, "s");
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsStringNotEmptyOrWhitespace",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckStringNotEmptyOrWhitespace(String s, String argName) {
		ArgUtil.staticCheckStringNotEmptyOrWhitespace(s, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsStringNotEmptyOrWhitespace2() {
		return new Object[][] {
				{ "" },  // ASCII space
				{ " " },  // ASCII space
				{ "\t" },
				{ "　" },  // wide japanese space
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsStringNotEmptyOrWhitespace2",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckStringNotEmptyOrWhitespace2(String s) {
		ArgUtil.staticCheckStringNotEmptyOrWhitespace(s, "s");
	}
}
