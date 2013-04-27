package com.nfshost.kevinarpe.papaya;

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

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.googlecode.kevinarpe.papaya.StringUtil;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class StringUtilTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldTrimWhitespacePrefix() {
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
                { "       aAa", "aAa" },
                { "        AaA", "AaA" },
                { "aAa ", "aAa " },
                { "AaA  ", "AaA  " },
                { " aAa ", "aAa " },
                { "  AaA  ", "AaA  " },
                { "       aAa       ", "aAa       " },
                { "        AaA        ", "AaA        " },
                
                { "a a", "a a" },
                { "A A", "A A" },
                { "a Aa", "a Aa" },
                { "A aA", "A aA" },
                { " a Aa", "a Aa" },
                { "  A aA", "A aA" },
                { "       a Aa", "a Aa" },
                { "        A aA", "A aA" },
                { "a Aa ", "a Aa " },
                { "A aA  ", "A aA  " },
                { " a Aa ", "a Aa " },
                { "  A aA  ", "A aA  " },
                { "       a Aa       ", "a Aa       " },
                { "        A aA        ", "A aA        " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldTrimWhitespacePrefix")
    public void shouldTrimWhitespacePrefix(String input, String expectedOutput) {
        String output = StringUtil.trimWhitespacePrefix(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTrimWhitespacePrefix() {
        String input = null;
        StringUtil.trimWhitespacePrefix(input);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldTrimWhitespaceSuffix() {
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
                { "       aAa", "       aAa" },
                { "        AaA", "        AaA" },
                { "aAa ", "aAa" },
                { "AaA  ", "AaA" },
                { " aAa ", " aAa" },
                { "  AaA  ", "  AaA" },
                { "       aAa       ", "       aAa" },
                { "        AaA        ", "        AaA" },
                
                { "a a", "a a" },
                { "A A", "A A" },
                { "a Aa", "a Aa" },
                { "A aA", "A aA" },
                { " a Aa", " a Aa" },
                { "  A aA", "  A aA" },
                { "       a Aa", "       a Aa" },
                { "        A aA", "        A aA" },
                { "a Aa ", "a Aa" },
                { "A aA  ", "A aA" },
                { " a Aa ", " a Aa" },
                { "  A aA  ", "  A aA" },
                { "       a Aa       ", "       a Aa" },
                { "        A aA        ", "        A aA" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldTrimWhitespaceSuffix")
    public void shouldTrimWhitespaceRight(String input, String expectedOutput) {
        String output = StringUtil.trimWhitespaceSuffix(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTrimWhitespaceSuffix() {
        String input = null;
        StringUtil.trimWhitespaceSuffix(input);
    }
}
