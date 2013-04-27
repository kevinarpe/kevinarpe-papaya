package com.googlecode.kevinarpe.papaya.Args;

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

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class DateTimeArgs {

	/**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #checkMinValue(DateTime, DateTime, String)
	 * @see #checkMaxValue(DateTime, DateTime, String)
	 * @see #checkExactValue(DateTime, DateTime, String)
	 */
	public static DateTime checkValueRange(
			DateTime dt, DateTime minDt, DateTime maxDt, String argName) {
        ComparableArgs.checkValueRange(
    		(ReadableInstant) dt, (ReadableInstant) minDt, (ReadableInstant) maxDt, argName);
		return dt;
	}

	/**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #checkValueRange(DateTime, DateTime, DateTime, String)
	 * @see #checkMaxValue(DateTime, DateTime, String)
	 * @see #checkExactValue(DateTime, DateTime, String)
	 */
	public static DateTime checkMinValue(
			DateTime dt, DateTime minDt, String argName) {
        ComparableArgs.checkMinValue((ReadableInstant) dt, (ReadableInstant) minDt, argName);
		return dt;
	}

	/**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #checkValueRange(DateTime, DateTime, DateTime, String)
	 * @see #checkMinValue(DateTime, DateTime, String)
	 * @see #checkExactValue(DateTime, DateTime, String)
	 */
	public static DateTime checkMaxValue(
			DateTime dt, DateTime maxDt, String argName) {
        ComparableArgs.checkMaxValue((ReadableInstant) dt, (ReadableInstant) maxDt, argName);
		return dt;
	}

	/**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #checkValueRange(DateTime, DateTime, DateTime, String)
	 * @see #checkMinValue(DateTime, DateTime, String)
	 * @see #checkMaxValue(DateTime, DateTime, String)
	 */
	public static DateTime checkExactValue(
			DateTime dt, DateTime exactDt, String argName) {
        ComparableArgs.checkExactValue((ReadableInstant) dt, (ReadableInstant) exactDt, argName);
		return dt;
	}
}
