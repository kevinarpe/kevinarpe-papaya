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

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.nfshost.kevinarpe.papaya.StringUtil;

public class StringUtilTests {

	@BeforeClass
	public void classSetup() {
	}
	
	@AfterClass
	public void classTearDown() {
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldWhitespaceTrimLeft() {
		return new Object[][] {
				{ " ", "" },  // ASCII space
				{ "\t", "" },
				{ "　", "" },  // wide Japanese space
				{ " ｹﾋﾞﾝ", "ｹﾋﾞﾝ" },  // narrow Japanese space
				
				{ "", "" },
				{ "a", "a" },
				{ "A", "A" },
				
				{ "aa", "aa" },
				{ "AA", "AA" },
				{ "aAa", "aAa" },
				{ "AaA", "AaA" },
				{ " aAa", "aAa" },
				{ "  AaA", "AaA" },
				{ " 	  aAa", "aAa" },
				{ "  	  AaA", "AaA" },
				{ "aAa ", "aAa " },
				{ "AaA  ", "AaA  " },
				{ " aAa ", "aAa " },
				{ "  AaA  ", "AaA  " },
				{ " 	  aAa 	  ", "aAa 	  " },
				{ "  	  AaA  	  ", "AaA  	  " },
				
				{ "a a", "a a" },
				{ "A A", "A A" },
				{ "a Aa", "a Aa" },
				{ "A aA", "A aA" },
				{ " a Aa", "a Aa" },
				{ "  A aA", "A aA" },
				{ " 	  a Aa", "a Aa" },
				{ "  	  A aA", "A aA" },
				{ "a Aa ", "a Aa " },
				{ "A aA  ", "A aA  " },
				{ " a Aa ", "a Aa " },
				{ "  A aA  ", "A aA  " },
				{ " 	  a Aa 	  ", "a Aa 	  " },
				{ "  	  A aA  	  ", "A aA  	  " },
		};
	}
	
	@Test(dataProvider = "_dataForShouldWhitespaceTrimLeft")
	public void shouldWhitespaceTrimLeft(String input, String expectedOutput) {
		String output = StringUtil.staticWhitespaceTrimLeft(input);
		Assert.assertEquals(output, expectedOutput);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void shouldNotWhitespaceTrimLeft() {
		String input = null;
		StringUtil.staticWhitespaceTrimLeft(input);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldWhitespaceTrimRight() {
		return new Object[][] {
				{ " ", "" },  // ASCII space
				{ "\t", "" },
				{ "　", "" },  // wide Japanese space
				{ "ｹﾋﾞﾝ ", "ｹﾋﾞﾝ" },  // narrow Japanese space
				
				{ "", "" },
				{ "a", "a" },
				{ "A", "A" },
				
				{ "aa", "aa" },
				{ "AA", "AA" },
				{ "aAa", "aAa" },
				{ "AaA", "AaA" },
				{ " aAa", " aAa" },
				{ "  AaA", "  AaA" },
				{ " 	  aAa", " 	  aAa" },
				{ "  	  AaA", "  	  AaA" },
				{ "aAa ", "aAa" },
				{ "AaA  ", "AaA" },
				{ " aAa ", " aAa" },
				{ "  AaA  ", "  AaA" },
				{ " 	  aAa 	  ", " 	  aAa" },
				{ "  	  AaA  	  ", "  	  AaA" },
				
				{ "a a", "a a" },
				{ "A A", "A A" },
				{ "a Aa", "a Aa" },
				{ "A aA", "A aA" },
				{ " a Aa", " a Aa" },
				{ "  A aA", "  A aA" },
				{ " 	  a Aa", " 	  a Aa" },
				{ "  	  A aA", "  	  A aA" },
				{ "a Aa ", "a Aa" },
				{ "A aA  ", "A aA" },
				{ " a Aa ", " a Aa" },
				{ "  A aA  ", "  A aA" },
				{ " 	  a Aa 	  ", " 	  a Aa" },
				{ "  	  A aA  	  ", "  	  A aA" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldWhitespaceTrimRight")
	public void shouldWhitespaceTrimRight(String input, String expectedOutput) {
		String output = StringUtil.staticWhitespaceTrimRight(input);
		Assert.assertEquals(output, expectedOutput);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public void shouldNotWhitespaceTrimRight() {
		String input = null;
		StringUtil.staticWhitespaceTrimRight(input);
	}
}
