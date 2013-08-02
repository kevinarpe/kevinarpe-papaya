package com.googlecode.kevinarpe.papaya;

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

import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.StringUtils.TextProcessorOption;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class StringUtilsTest {

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
    private static final Object[][] _trimWhitespacePrefix_Pass_Data() {
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
    
    @Test(dataProvider = "_trimWhitespacePrefix_Pass_Data")
    public void trimWhitespacePrefix_Pass(String input, String expectedOutput) {
        String output = StringUtils.trimWhitespacePrefix(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void trimWhitespacePrefix_FailWithNull() {
        String input = null;
        StringUtils.trimWhitespacePrefix(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.trimWhitespaceSuffix
    //

    @DataProvider
    private static final Object[][] _trimWhitespaceSuffix_Pass_Data() {
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
    
    @Test(dataProvider = "_trimWhitespaceSuffix_Pass_Data")
    public void trimWhitespaceSuffix_Pass(String input, String expectedOutput) {
        String output = StringUtils.trimWhitespaceSuffix(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void trimWhitespaceSuffix_FailWithNull() {
        String input = null;
        StringUtils.trimWhitespaceSuffix(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.parseBoolean
    //

    @DataProvider
    private static final Object[][] _parseBoolean_Pass_Data() {
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
    
    @Test(dataProvider = "_parseBoolean_Pass_Data")
    public void parseBoolean_Pass(String input, boolean expectedOutput) {
        boolean output = StringUtils.parseBoolean(input);
        Assert.assertEquals(output, expectedOutput);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void parseBoolean_FailWithNull() {
        StringUtils.parseBoolean(null);
    }

    @DataProvider
    private static final Object[][] _parseBoolean_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "_parseBoolean_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void parseBoolean_FailWithInvalidInput(String input) {
        StringUtils.parseBoolean(input);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.isWhitespace
    //

    @DataProvider
    private static final Object[][] _isWhitespace_Pass_Data() {
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
    
    @Test(dataProvider = "_isWhitespace_Pass_Data")
    public void isWhitespace_Pass(String input, boolean expectedOutput) {
        boolean output = StringUtils.isEmptyOrWhitespace(input);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void isWhitespace_FailWithNull() {
        StringUtils.isEmptyOrWhitespace(null);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.substringPrefix
    //
    
    @DataProvider
    private static final Object[][] _substringPrefix_Pass_Data() {
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
    
    @Test(dataProvider = "_substringPrefix_Pass_Data")
    public void substringPrefix_Pass(String input, int count, String expectedOutput) {
        String output = StringUtils.substringPrefix(input, count);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @DataProvider
    private static final Object[][] _substringPrefix_FailWithNull_Data() {
        return new Object[][] {
                { null, 0 },
                { null, 2 },
                { null, 99 },
                { null, -1 },
                { null, -99 },
        };
    }
    
    @Test(dataProvider = "_substringPrefix_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void substringPrefix_FailWithNull(String input, int count) {
        StringUtils.substringPrefix(input, count);
    }
    
    @DataProvider
    private static final Object[][] _substringPrefix_FailWithInvalidCount_Data() {
        return new Object[][] {
                { "", -1 },
                { "", -99 },
                { "abc", -1 },
                { "abc", -99 },
        };
    }
    
    @Test(dataProvider = "_substringPrefix_FailWithInvalidCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void substringPrefix_FailWithInvalidCount(String input, int count) {
        StringUtils.substringPrefix(input, count);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.substringSuffix
    //
    
    @DataProvider
    private static final Object[][] _substringSuffix_Pass_Data() {
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
    
    @Test(dataProvider = "_substringSuffix_Pass_Data")
    public void substringSuffix_Pass(String input, int count, String expectedOutput) {
        String output = StringUtils.substringSuffix(input, count);
        Assert.assertEquals(output, expectedOutput);
    }
    
    @DataProvider
    private static final Object[][] _substringSuffix_FailWithNull_Data() {
        return new Object[][] {
                { null, 0 },
                { null, 2 },
                { null, 99 },
                { null, -1 },
                { null, -99 },
        };
    }
    
    @Test(dataProvider = "_substringSuffix_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void substringSuffix_FailWithNull(String input, int count) {
        StringUtils.substringSuffix(input, count);
    }
    
    @DataProvider
    private static final Object[][] _substringSuffix_FailWithInvalidCount_Data() {
        return new Object[][] {
                { "", -1 },
                { "", -99 },
                { "abc", -1 },
                { "abc", -99 },
        };
    }
    
    @Test(dataProvider = "_substringSuffix_FailWithInvalidCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void substringSuffix_FailWithInvalidCount(String input, int count) {
        StringUtils.substringSuffix(input, count);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.removeCharAt
    //
    
    @DataProvider
    private static final Object[][] _removeCharAt_Pass_Data() {
        return new Object[][] {
                { "abc", 0, "bc" },
                { "abc", 1, "ac" },
                { "abc", 2, "ab" },
        };
    }
    
    @Test(dataProvider = "_removeCharAt_Pass_Data")
    public void removeCharAt_Pass(String input, int index, String expectedOutput) {
        String output = StringUtils.removeCharAt(input, index);
        Assert.assertEquals(output, expectedOutput);
        Assert.assertTrue(input != output);
    }
    
    @DataProvider
    private static final Object[][] _removeCharAt_FailWithNull_Data() {
        return new Object[][] {
                { null, 0 },
                { null, 2 },
                { null, 99 },
                { null, -1 },
                { null, -99 },
        };
    }
    
    @Test(dataProvider = "_removeCharAt_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void removeCharAt_FailWithNull(String input, int index) {
        StringUtils.substringPrefix(input, index);
    }
    
    @DataProvider
    private static final Object[][] _removeCharAt_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { "", 0 },
                { "abc", -1 },
                { "abc", -99 },
        };
    }
    
    @Test(dataProvider = "_removeCharAt_FailWithInvalidIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void removeCharAt_FailWithInvalidIndex(String input, int index) {
        StringUtils.removeCharAt(input, index);
    }
    
    @DataProvider
    private static final Object[][] _removeCharAt_FailWithInvalidIndex2_Data() {
        return new Object[][] {
                { "abc", 3 },
                { "abc", 99 },
        };
    }
    
    @Test(dataProvider = "_removeCharAt_FailWithInvalidIndex2_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void removeCharAt_FailWithInvalidIndex2(String input, int index) {
        StringUtils.removeCharAt(input, index);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringUtil.addPrefixAndSuffixPerLine
    //

    @DataProvider
    private static final Object[][] _addPrefixAndSuffixPerLine_Pass_Data() {
        return new Object[][] {
                ///////////////////////////////////////////////////////////////
                // both optLinePrefix & optLineSuffix are not null and not empty
                {  // Regular
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">This is a sample<%n>multiline block<%n>of text.<"),  // expected
                },
                {  // Regular, skip first line
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { StringUtils.TextProcessorOption.SKIP_FIRST_LINE },  // optionArr
                    String.format("This is a sample%n>multiline block<%n>of text.<"),  // expected
                },
                {  // Regular, skip first line x 2
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { StringUtils.TextProcessorOption.SKIP_FIRST_LINE, StringUtils.TextProcessorOption.SKIP_FIRST_LINE },  // optionArr
                    String.format("This is a sample%n>multiline block<%n>of text.<"),  // expected
                },
                {  // Empty line in middle of pack
                    String.format("This is a sample%n%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">This is a sample<%n><%n>multiline block<%n>of text.<"),  // expected
                },
                {  // Trailing newline
                    String.format("This is a sample%nmultiline block%nof text.%n"),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">This is a sample<%n>multiline block<%n>of text.<%n"),  // expected
                },
                {  // Leading newline
                    String.format("%nThis is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("><%n>This is a sample<%n>multiline block<%n>of text.<"),  // expected
                },
                {  // Single char
                    String.format("x"),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">x<"),  // expected
                },
                {  // Single char, trailing newline
                    String.format("x%n"),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">x<%n"),  // expected
                },
                {  // Single char, leading newline
                    String.format("%nx"),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("><%n>x<"),  // expected
                },
                {  // Single newline
                    String.format("%n"),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("><%n"),  // expected
                },
                {  // Two newlines
                    String.format("%n%n"),  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("><%n><%n"),  // expected
                },
                {  // Single char, multichar prefix/suffix
                    String.format("x"),  // textBlock
                    ">>>",  // optLinePrefix
                    "<<<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">>>x<<<"),  // expected
                },
                
                ///////////////////////////////////////////////////////////////
                // only optLinePrefix is not null and not empty
                {  // Regular
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">This is a sample%n>multiline block%n>of text."),  // expected
                },
                {  // Regular, skip first line
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { StringUtils.TextProcessorOption.SKIP_FIRST_LINE },  // optionArr
                    String.format("This is a sample%n>multiline block%n>of text."),  // expected
                },
                {  // Regular, skip first line x 2
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { StringUtils.TextProcessorOption.SKIP_FIRST_LINE, StringUtils.TextProcessorOption.SKIP_FIRST_LINE },  // optionArr
                    String.format("This is a sample%n>multiline block%n>of text."),  // expected
                },
                {  // Empty line in middle of pack
                    String.format("This is a sample%n%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">This is a sample%n>%n>multiline block%n>of text."),  // expected
                },
                {  // Trailing newline
                    String.format("This is a sample%nmultiline block%nof text.%n"),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">This is a sample%n>multiline block%n>of text.%n"),  // expected
                },
                {  // Leading newline
                    String.format("%nThis is a sample%nmultiline block%nof text."),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">%n>This is a sample%n>multiline block%n>of text."),  // expected
                },
                {  // Single char
                    String.format("x"),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">x"),  // expected
                },
                {  // Single char, trailing newline
                    String.format("x%n"),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">x%n"),  // expected
                },
                {  // Single char, leading newline
                    String.format("%nx"),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">%n>x"),  // expected
                },
                {  // Single newline
                    String.format("%n"),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">%n"),  // expected
                },
                {  // Two newlines
                    String.format("%n%n"),  // textBlock
                    ">",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">%n>%n"),  // expected
                },
                {  // Single char, multichar prefix/suffix
                    String.format("x"),  // textBlock
                    ">>>",  // optLinePrefix
                    null,  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format(">>>x"),  // expected
                },
                ///////////////////////////////////////////////////////////////
                // only optLineSuffix is not null and not empty
                {  // Regular
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("This is a sample<%nmultiline block<%nof text.<"),  // expected
                },
                {  // Regular, skip first line
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { StringUtils.TextProcessorOption.SKIP_FIRST_LINE },  // optionArr
                    String.format("This is a sample%nmultiline block<%nof text.<"),  // expected
                },
                {  // Regular, skip first line x 2
                    String.format("This is a sample%nmultiline block%nof text."),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { StringUtils.TextProcessorOption.SKIP_FIRST_LINE, StringUtils.TextProcessorOption.SKIP_FIRST_LINE },  // optionArr
                    String.format("This is a sample%nmultiline block<%nof text.<"),  // expected
                },
                {  // Empty line in middle of pack
                    String.format("This is a sample%n%nmultiline block%nof text."),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("This is a sample<%n<%nmultiline block<%nof text.<"),  // expected
                },
                {  // Trailing newline
                    String.format("This is a sample%nmultiline block%nof text.%n"),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("This is a sample<%nmultiline block<%nof text.<%n"),  // expected
                },
                {  // Leading newline
                    String.format("%nThis is a sample%nmultiline block%nof text."),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("<%nThis is a sample<%nmultiline block<%nof text.<"),  // expected
                },
                {  // Single char
                    String.format("x"),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("x<"),  // expected
                },
                {  // Single char, trailing newline
                    String.format("x%n"),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("x<%n"),  // expected
                },
                {  // Single char, leading newline
                    String.format("%nx"),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("<%nx<"),  // expected
                },
                {  // Single newline
                    String.format("%n"),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("<%n"),  // expected
                },
                {  // Two newlines
                    String.format("%n%n"),  // textBlock
                    null,  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("<%n<%n"),  // expected
                },
                {  // Single char, multichar prefix/suffix
                    String.format("x"),  // textBlock
                    null,  // optLinePrefix
                    "<<<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    String.format("x<<<"),  // expected
                },
        };
    }
    
    @Test(dataProvider = "_addPrefixAndSuffixPerLine_Pass_Data")
    public void addPrefixAndSuffixPerLine_Pass(
            String textBlock,
            String optLinePrefix,
            String optLineSuffix,
            TextProcessorOption[] optionArr,
            String expected) {
        String actual =
            StringUtils.addPrefixAndSuffixPerLine(
                textBlock, optLinePrefix, optLineSuffix, optionArr);
        Assert.assertEquals(actual, expected);
        if (null == optLinePrefix) {
            actual =
                StringUtils.addPrefixAndSuffixPerLine(textBlock, "", optLineSuffix, optionArr);
            Assert.assertEquals(actual, expected);
        }
        if (null == optLineSuffix) {
            actual =
                StringUtils.addPrefixAndSuffixPerLine(textBlock, optLinePrefix, "", optionArr);
            Assert.assertEquals(actual, expected);
        }
    }
    
    @DataProvider
    private static final Object[][] _addPrefixAndSuffixPerLine_Fail_Data() {
        return new Object[][] {
                {
                    null,  // textBlock (bad)
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    NullPointerException.class,  // expectedExceptionClass
                },
                {
                    "",  // textBlock (bad)
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    IllegalArgumentException.class,  // expectedExceptionClass
                },
                {
                    "x",  // textBlock
                    null,  // optLinePrefix (bad)
                    null,  // optLineSuffix (bad)
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    IllegalArgumentException.class,  // expectedExceptionClass
                },
                {
                    "x",  // textBlock
                    "",  // optLinePrefix (bad)
                    null,  // optLineSuffix (bad)
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    IllegalArgumentException.class,  // expectedExceptionClass
                },
                {
                    "x",  // textBlock
                    null,  // optLinePrefix (bad)
                    "",  // optLineSuffix (bad)
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    IllegalArgumentException.class,  // expectedExceptionClass
                },
                {
                    "x",  // textBlock
                    "",  // optLinePrefix (bad)
                    "",  // optLineSuffix (bad)
                    new StringUtils.TextProcessorOption[] { },  // optionArr
                    IllegalArgumentException.class,  // expectedExceptionClass
                },
                {
                    "x",  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] { null },  // optionArr (bad)
                    NullPointerException.class,  // expectedExceptionClass
                },
                {
                    "x",  // textBlock
                    ">",  // optLinePrefix
                    "<",  // optLineSuffix
                    new StringUtils.TextProcessorOption[] {
                        StringUtils.TextProcessorOption.SKIP_FIRST_LINE, null },  // optionArr (bad)
                    NullPointerException.class,  // expectedExceptionClass
                },
        };
    }
    
    @Test(dataProvider = "_addPrefixAndSuffixPerLine_Fail_Data")
    public void addPrefixAndSuffixPerLine_Fail(
            String textBlock,
            String optLinePrefix,
            String optLineSuffix,
            TextProcessorOption[] optionArr,
            Class<?> expectedExceptionClass)
    throws Exception {
        try {
            StringUtils.addPrefixAndSuffixPerLine(
                textBlock, optLinePrefix, optLineSuffix, optionArr);
        }
        catch (Exception e) {
            if (expectedExceptionClass != e.getClass()) {
                throw e;
            }
        }
    }
}
