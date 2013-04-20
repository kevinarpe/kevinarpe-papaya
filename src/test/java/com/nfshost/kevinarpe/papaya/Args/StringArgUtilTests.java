package com.nfshost.kevinarpe.papaya.Args;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.Args.StringArgUtil;

public class StringArgUtilTests {

	@BeforeClass
	public void classSetup() {
	}
	
	@AfterClass
	public void classTearDown() {
	}

	@DataProvider
	private static final Object[][] _dataForShouldCheckAsNotEmpty() {
		return new Object[][] {
				{ " " },  // ASCII space
				{ "\t" },
				{ "　" },  // wide Japanese space
				{ "abc" },
				{ "僕は前に東京に住んでいました。" },
				{ "我会写中文。" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckAsNotEmpty")
	public void shouldCheckAsNotEmpty(String s) {
		StringArgUtil.staticCheckNotEmpty(s, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotEmptyOrWhitespace() {
		return new Object[][] {
				{ null, null },
				{ "abc", null },
				{ null, "value" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyOrWhitespace",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckAsNotEmpty(String s, String argName) {
		StringArgUtil.staticCheckNotEmpty(s, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotEmpty2() {
		return new Object[][] {
				{ "" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotEmpty2",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckAsNotEmpty2(String s) {
		StringArgUtil.staticCheckNotEmpty(s, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckNotEmptyOrWhitespace() {
		return new Object[][] {
				{ " abc" },  // ASCII space
				{ "\tabc" },
				{ "　abc" },  // wide japanese space
				{ "abc" },
				{ "僕は東京に住んでいました。" },
				{ "我会写中文。" },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckNotEmptyOrWhitespace")
	public void shouldCheckNotEmptyOrWhitespace(String s) {
		StringArgUtil.staticCheckNotEmptyOrWhitespace(s, "s");
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyOrWhitespace",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckNotEmptyOrWhitespace(String s, String argName) {
		StringArgUtil.staticCheckNotEmptyOrWhitespace(s, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotEmptyOrWhitespace2() {
		return new Object[][] {
				{ "" },
				{ " " },  // ASCII space
				{ "\t" },
				{ "　" },  // wide Japanese space
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyOrWhitespace2",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckNotEmptyOrWhitespace2(String s) {
		StringArgUtil.staticCheckNotEmptyOrWhitespace(s, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckLengthRange() {
		return new Object[][] {
				{ "abc", 3, 3 },
				{ "abc", 1, 3 },
				{ "abc", 3, 4 },
				{ "abc", 1, 4 },
				{ "abc", 0, 4 },
				{ "", 0, 4 },
				{ "", 0, 0 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckLengthRange")
	public void shouldCheckLengthRange(String s, int minLen, int maxLen) {
		StringArgUtil.staticCheckLengthRange(s, minLen, maxLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckLengthRangeWithInvalidMinOrMaxLen() {
		return new Object[][] {
				{ "abc", -3, 3 },
				{ "abc", 1, -3 },
				{ "abc", -3, -4 },
				{ "abc", 4, 3 },
				{ "abc", 6, 7 },
				{ "abc", 0, 1 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckLengthRangeWithInvalidMinOrMaxLen",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldCheckNotLengthRangeWithInvalidMinOrMaxLen(
			String s, int minLen, int maxLen) {
		StringArgUtil.staticCheckLengthRange(s, minLen, maxLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckLengthRangeWithNullString() {
		return new Object[][] {
				{ null, 4, 3 },
				{ null, 6, 7 },
				{ null, 0, 1 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckLengthRangeWithNullString",
			expectedExceptions = NullPointerException.class)
	public void shouldCheckNotLengthRangeWithNullString(
			String s, int minLen, int maxLen) {
		StringArgUtil.staticCheckLengthRange(s, minLen, maxLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckMinLength() {
		return new Object[][] {
				{ "abc", 3 },
				{ "abc", 1 },
				{ "abc", 0 },
				{ "", 0 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckMinLength")
	public void shouldCheckMinLength(String s, int minLen) {
		StringArgUtil.staticCheckMinLength(s, minLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckMinLengthWithInvalidMinLen() {
		return new Object[][] {
				{ "abc", -3 },
				{ "abc", 4 },
				{ "abc", 6 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckMinLengthWithInvalidMinLen",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldCheckNotMinLengthWithInvalidMinLen(String s, int minLen) {
		StringArgUtil.staticCheckMinLength(s, minLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckMinLengthWithNullString() {
		return new Object[][] {
				{ null, 4 },
				{ null, 6 },
				{ null, 0 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckMinLengthWithNullString",
			expectedExceptions = NullPointerException.class)
	public void shouldCheckNotMinLengthWithNullString(String s, int minLen) {
		StringArgUtil.staticCheckMinLength(s, minLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckMaxLength() {
		return new Object[][] {
				{ "abc", 3 },
				{ "abc", 4 },
				{ "abc", 5 },
				{ "", 0 },
				{ "", 2 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckMaxLength")
	public void shouldCheckMaxLength(String s, int maxLen) {
		StringArgUtil.staticCheckMaxLength(s, maxLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckMaxLengthWithInvalidMaxLen() {
		return new Object[][] {
				{ "abc", -3 },
				{ "abc", 1 },
				{ "abc", 2 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckMaxLengthWithInvalidMaxLen",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldCheckNotMaxLengthWithInvalidMaxLen(String s, int maxLen) {
		StringArgUtil.staticCheckMaxLength(s, maxLen, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckMaxLengthWithNullString() {
		return new Object[][] {
				{ null, 4 },
				{ null, 6 },
				{ null, 0 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckMaxLengthWithNullString",
			expectedExceptions = NullPointerException.class)
	public void shouldCheckNotMaxLengthWithNullString(String s, int maxLen) {
		StringArgUtil.staticCheckMaxLength(s, maxLen, "s");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@DataProvider
	private static final Object[][] _dataForShouldCheckExactLength() {
		return new Object[][] {
				{ "abc", 3 },
				{ "", 0 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckExactLength")
	public void shouldCheckExactLength(String s, int len) {
		StringArgUtil.staticCheckExactLength(s, len, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckExactLengthWithInvalidExactLen() {
		return new Object[][] {
				{ "abc", -3 },
				{ "abc", 1 },
				{ "abc", 2 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckExactLengthWithInvalidExactLen",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldCheckNotExactLengthWithInvalidExactLen(String s, int len) {
		StringArgUtil.staticCheckExactLength(s, len, "s");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckExactLengthWithNullString() {
		return new Object[][] {
				{ null, 4 },
				{ null, 6 },
				{ null, 0 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckExactLengthWithNullString",
			expectedExceptions = NullPointerException.class)
	public void shouldCheckNotExactLengthWithNullString(String s, int len) {
		StringArgUtil.staticCheckExactLength(s, len, "s");
	}
}
