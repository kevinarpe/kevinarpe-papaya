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

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.PrimitiveTypeUtil;

public class LongArgsTest {
	
	@BeforeClass
	public void classSetup() {
	}
	
	@AfterClass
	public void classTearDown() {
	}
	
	///////////////////////////////////////////////////////////////////////////
	// StringArgUtil.staticCheckPositive
	//

	@DataProvider
	private static final Object[][] _dataForShouldCheckAsPositive() {
		return new Object[][] {
				{ 1 },
				{ 99 },
				{ Long.MAX_VALUE },
				{ (long)(1.0f + PrimitiveTypeUtil.EPSILON_POSITIVE_FLOAT), },
				{ (long)(1.0f - PrimitiveTypeUtil.EPSILON_POSITIVE_FLOAT), },
				{ (long)(1.0d + PrimitiveTypeUtil.EPSILON_POSITIVE_DOUBLE), },
				{ (long)(1.0d - PrimitiveTypeUtil.EPSILON_POSITIVE_DOUBLE), },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckAsPositive")
	public void shouldCheckAsPositive(long i) {
		LongArgs.staticCheckPositive(i, "i");
	}

	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsPositiveWithNullArgName() {
		return new Object[][] {
				{ 1, null },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNullArgName",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckAsPositiveWithNullArgName(long i, String argName) {
		LongArgs.staticCheckPositive(i, argName);
	}

	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsPositiveWithNonPositiveInput() {
		return new Object[][] {
				{ 0 },
				{ -1 },
				{ Long.MIN_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckAsPositiveWithNonPositiveInput(long i) {
		LongArgs.staticCheckPositive(i, "i");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// StringArgUtil.staticCheckNotPositive
	//

	@DataProvider
	private static final Object[][] _dataForShouldCheckAsNotPositive() {
		return new Object[][] {
				{ 0 },
				{ -99 },
				{ Long.MIN_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckAsNotPositive")
	public void shouldCheckAsNotPositive(long i) {
		LongArgs.staticCheckNotPositive(i, "i");
	}

	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithNullArgName() {
		return new Object[][] {
				{ 1, null },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithNullArgName",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckAsNotPositiveWithNullArgName(long i, String argName) {
		LongArgs.staticCheckNotPositive(i, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithPositiveInput() {
		return new Object[][] {
				{ 1 },
				{ Long.MAX_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckAsNotPositiveWithPositiveInput(long i) {
		LongArgs.staticCheckNotPositive(i, "i");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// StringArgUtil.staticCheckNegative
	//

	@DataProvider
	private static final Object[][] _dataForShouldCheckAsNegative() {
		return new Object[][] {
				{ -1 },
				{ -99 },
				{ Long.MIN_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckAsNegative")
	public void shouldCheckAsNegative(long i) {
		LongArgs.staticCheckNegative(i, "i");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNegativeWithNullArgName() {
		return new Object[][] {
				{ 1, null },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNullArgName",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckAsNegativeWithNullArgName(long i, String argName) {
		LongArgs.staticCheckNegative(i, argName);
	}

	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNegativeWithNonNegativeInput() {
		return new Object[][] {
				{ 0 },
				{ 1 },
				{ Long.MAX_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckAsNegativeWithNonNegativeInput(long i) {
		LongArgs.staticCheckNegative(i, "i");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// StringArgUtil.staticCheckNotNegative
	//

	@DataProvider
	private static final Object[][] _dataForShouldCheckAsNotNegative() {
		return new Object[][] {
				{ 0 },
				{ 99 },
				{ Long.MAX_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckAsNotNegative")
	public void shouldCheckAsNotNegative(long i) {
		LongArgs.staticCheckNotNegative(i, "i");
	}

	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNullArgName() {
		return new Object[][] {
				{ 1, null },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNullArgName",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckAsNotNegativeWithNullArgName(long i, String argName) {
		LongArgs.staticCheckNotNegative(i, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
		return new Object[][] {
				{ -1 },
				{ Long.MIN_VALUE },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckAsNotNegativeWithNegativeInput(long i) {
		LongArgs.staticCheckNotNegative(i, "i");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// StringArgUtil.staticCheckValueRange
	//

	@DataProvider
	private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
		return new Object[][] {
				{ 1, -1, 2 },
				{ 1, -1, 1 },
				{ 1, 0, 1 },
				{ 1, 0, 2 },
				{ 1, 1, 1 },
				{ 1, 1, 2 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
	public void shouldCheckValueRangeAsValid(long i, long minValue, long maxValue) {
		LongArgs.staticCheckValueRange(i, minValue, maxValue, "i");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithNullArgName() {
		return new Object[][] {
				{ 1, 1, 1, null },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithNullArgName",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckValueRangeAsValidWithNullArgName(
			long i, long minValue, long maxValue, String argName) {
		LongArgs.staticCheckValueRange(i, minValue, maxValue, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
		return new Object[][] {
				{ 1, -1, 0 },
				{ 1, 0, 0 },
				{ 1, 2, 2 },
				{ 1, 2, 1 },
				{ 1, -1, -2 },
				{ 1, -2, -1 },
				
				{ -1, 1, 0 },
				{ -1, 0, 0 },
				{ -1, -2, -2 },
				{ -1, -1, -2 },
				{ -1, 1, 2 },
				{ -1, 2, 1 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckValueRangeAsValidWithInvalidInput(
			long i, long minValue, long maxValue) {
		LongArgs.staticCheckValueRange(i, minValue, maxValue, "i");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// StringArgUtil.staticCheckMinValue
	//

	@DataProvider
	private static final Object[][] _dataForShouldCheckMinValueAsValid() {
		return new Object[][] {
				{ 1, -1 },
				{ 1, 0 },
				{ 1, 1 },
				
				{ -1, -1 },
				{ -1, -2 },
				{ -1, -3 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
	public void shouldCheckMinValueAsValid(long i, long minValue) {
		LongArgs.staticCheckMinValue(i, minValue, "i");
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithNullArgName() {
		return new Object[][] {
				{ 1, 1, null },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithNullArgName",
			expectedExceptions = NullPointerException.class)
	public void shouldNotCheckMinValueAsValidWithNullArgName(
			long i, long minValue, String argName) {
		LongArgs.staticCheckMinValue(i, minValue, argName);
	}
	
	@DataProvider
	private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
		return new Object[][] {
				{ 1, 2 },
				{ 1, 3 },
				{ -1, 0 },
				{ -1, 1 },
		};
	}
	
	@Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
			expectedExceptions = IllegalArgumentException.class)
	public void shouldNotCheckMinValueAsValidWithInvalidInput(long i, long minValue) {
		LongArgs.staticCheckMinValue(i, minValue, "i");
	}
}
