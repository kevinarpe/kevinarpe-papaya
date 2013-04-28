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
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.trimWhitespacePrefix
    //

    @DataProvider
    private static final Object[][] _dataForShouldCorrectlyTrimWhitespacePrefix() {
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
    
    @Test(dataProvider = "_dataForShouldCorrectlyTrimWhitespacePrefix")
    public void shouldCorrectlyTrimWhitespacePrefix(String input, String expectedOutput) {
        String output = StringUtil.trimWhitespacePrefix(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTrimWhitespacePrefixWithNullInput() {
        String input = null;
        StringUtil.trimWhitespacePrefix(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.trimWhitespaceSuffix
    //

    @DataProvider
    private static final Object[][] _dataForShouldCorrectlyTrimWhitespaceSuffix() {
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
    
    @Test(dataProvider = "_dataForShouldCorrectlyTrimWhitespaceSuffix")
    public void shouldCorrectlyTrimWhitespaceRight(String input, String expectedOutput) {
        String output = StringUtil.trimWhitespaceSuffix(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTrimWhitespaceSuffixWithNullInput() {
        String input = null;
        StringUtil.trimWhitespaceSuffix(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.parseBoolean
    //

    @DataProvider
    private static final Object[][] _dataForShouldParseBoolean() {
        return new Object[][] {
        		{ "true", true },
        		{ "True", true },
        		{ "TRue", true },
        		{ "TrUe", true },
        		{ "TruE", true },
        		{ "TRUE", true },
        		
        		{ "false", false },
        		{ "False", false },
        		{ "fAlse", false },
        		{ "faLse", false },
        		{ "falSe", false },
        		{ "falsE", false },
        		{ "FALSE", false },
        };
    }
    
    @Test(dataProvider = "_dataForShouldParseBoolean")
    public void shouldParseBoolean(String input, boolean expectedOutput) {
        boolean output = StringUtil.parseBoolean(input);
        Assert.assertEquals(output, expectedOutput);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotParseBooleanWithNullInput() {
    	String input = null;
        StringUtil.parseBoolean(input);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotParseBooleanWithInvalidInput() {
        return new Object[][] {
        		{ "" },
        		{ "\t" },
        		{ "   " },
        		{ " true" },
        		{ "true " },
        		{ " false" },
        		{ "false " },
        		{ "vrai" },
        		{ "faux" },
        		{ "真" },
        		{ "偽" },
        		{ "真实的" },
        		{ "假的" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotParseBooleanWithInvalidInput",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotParseBooleanWithInvalidInput(String input) {
        StringUtil.parseBoolean(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.isWhitespace
    //

    @DataProvider
    private static final Object[][] _dataForShouldRunIsWhiteSpaceWithoutException() {
        return new Object[][] {
        		{ "", true },
                { "\t", true },
                { "   ", true },  // ASCII spaces
                { "　　　", true },  // wide Japanese spaces
                { "   ", true },  // narrow Japanese spaces
                
                { "abc", false },
                { "   abc", false },  // ASCII spaces
            		{ "   ｹﾋﾞﾝ", false },  // narrow Japanese space
                { "　　　東京", false },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldRunIsWhiteSpaceWithoutException")
    public void shouldRunIsWhiteSpaceWithoutException(String input, boolean expectedOutput) {
        boolean output = StringUtil.isWhitespace(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldRunIsWhiteSpaceWithException() {
    	String input = null;
        StringUtil.isWhitespace(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.substringPrefix
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCorrectlySubstringPrefix() {
        return new Object[][] {
        		{ "abc", 0, "" },
        		{ "abc", 1, "a" },
        		{ "abc", 2, "ab" },
        		{ "abc", 3, "abc" },
        		{ "abc", 4, "abc" },
        		{ "abc", 99, "abc" },
        		{ "僕の日本語の名前はケビンです。", 0, "" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 1, "僕" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 2, "僕の" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 3, "僕の日" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 4, "僕の日本" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 99, "僕の日本語の名前はケビンです。" },  // wide Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 0, "" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 9, "僕の日本語の名前は" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 10, "僕の日本語の名前はｹ" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 11, "僕の日本語の名前はｹﾋ" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 12, "僕の日本語の名前はｹﾋﾞ" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 99, "僕の日本語の名前はｹﾋﾞﾝです。" },  // wide and narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 0, "" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 1, "ｹ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 2, "ｹﾋ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 3, "ｹﾋﾞ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 4, "ｹﾋﾞﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 5, "ｹﾋﾞﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 99, "ｹﾋﾞﾝ" },  // narrow Japanese chars
        };
    }
    
    @Test(dataProvider = "_dataForShouldCorrectlySubstringPrefix")
    public void shouldCorrectlySubstringPrefix(String input, int count, String expectedOutput) {
        String output = StringUtil.substringPrefix(input, count);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotSubstringPrefixWithNullInput() {
        return new Object[][] {
        		{ null, 0 },
        		{ null, 2 },
        		{ null, 99 },
        		{ null, -1 },
        		{ null, -99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotSubstringPrefixWithNullInput",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotSubstringPrefixWithNullInput(String input, int count) {
    	StringUtil.substringPrefix(input, count);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotSubstringPrefixWithInvalidCount() {
        return new Object[][] {
        		{ "", -1 },
        		{ "", -99 },
        		{ "abc", -1 },
        		{ "abc", -99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotSubstringPrefixWithInvalidCount",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotSubstringPrefixWithInvalidCount(String input, int count) {
    	StringUtil.substringPrefix(input, count);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.substringSuffix
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCorrectlySubstringSuffix() {
        return new Object[][] {
        		{ "abc", 0, "" },
        		{ "abc", 1, "c" },
        		{ "abc", 2, "bc" },
        		{ "abc", 3, "abc" },
        		{ "abc", 4, "abc" },
        		{ "abc", 99, "abc" },
        		{ "僕の日本語の名前はケビンです。", 0, "" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 1, "。" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 2, "す。" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 3, "です。" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 4, "ンです。" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 5, "ビンです。" },  // wide Japanese chars
        		{ "僕の日本語の名前はケビンです。", 99, "僕の日本語の名前はケビンです。" },  // wide Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 0, "" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 1, "。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 2, "す。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 3, "です。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 4, "ﾝです。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 5, "ﾞﾝです。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 6, "ﾋﾞﾝです。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 7, "ｹﾋﾞﾝです。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 8, "はｹﾋﾞﾝです。" },  // wide and narrow Japanese chars
        		{ "僕の日本語の名前はｹﾋﾞﾝです。", 99, "僕の日本語の名前はｹﾋﾞﾝです。" },  // wide and narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 0, "" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 1, "ﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 2, "ﾞﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 3, "ﾋﾞﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 4, "ｹﾋﾞﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 5, "ｹﾋﾞﾝ" },  // narrow Japanese chars
        		{ "ｹﾋﾞﾝ", 99, "ｹﾋﾞﾝ" },  // narrow Japanese chars
        };
    }
    
    @Test(dataProvider = "_dataForShouldCorrectlySubstringSuffix")
    public void shouldCorrectlySubstringSuffix(String input, int count, String expectedOutput) {
        String output = StringUtil.substringSuffix(input, count);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotSubstringSuffixWithNullInput() {
        return new Object[][] {
        		{ null, 0 },
        		{ null, 2 },
        		{ null, 99 },
        		{ null, -1 },
        		{ null, -99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotSubstringSuffixWithNullInput",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotSubstringSuffixWithNullInput(String input, int count) {
    	StringUtil.substringSuffix(input, count);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotSubstringSuffixWithInvalidCount() {
        return new Object[][] {
        		{ "", -1 },
        		{ "", -99 },
        		{ "abc", -1 },
        		{ "abc", -99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotSubstringSuffixWithInvalidCount",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotSubstringSuffixWithInvalidCount(String input, int count) {
    	StringUtil.substringSuffix(input, count);
    }
}
