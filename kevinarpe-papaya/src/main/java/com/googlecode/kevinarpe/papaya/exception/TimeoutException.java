package com.googlecode.kevinarpe.papaya.exception;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.LongArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.process.Process2;

import java.util.Collection;

/**
 * Thrown by class {@link Process2} when waiting for the child process to terminate exceeds the
 * specified time limit.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Process2#waitFor(long)
 * @see Process2#waitForThenCheckExitValue(long, Collection)
 */
@FullyTested
public class TimeoutException
extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private final long _timeoutMillis;
    
    /**
     * This is a convenience method for
     * {@link #TimeoutException(long, Throwable, String, Object...)} where param {@code optCause}
     * is {@code null}.
     */
    public TimeoutException(
            long timeoutMillis,
            String format,
            Object... optArgArr) {
        this(timeoutMillis, null, false, format, optArgArr);
    }
    
    /**
     * Constructor.  The message, as specified by parameters {@code format} and {@code optArgArr}
     * has a suffix appended with additional details about timeout millis.  The message need not
     * specify this detail.
     * <p>
     * Example {@code format}: {@code "Failed to wait for filter command to terminate: %s"}
     * Example {@code optArgArr}: (filter command arguments)
     * 
     * @param timeoutMillis
     * <ul>
     *   <li>number of milliseconds waited before timeout</li>
     *   <li>Accessible via {@link #getTimeoutMillis()}</li>
     * </ul>
     * @param optCause
     * <ul>
     *   <li>optional underlying cause of this exception that is passed directory to superclass
     *   constructor</li>
     *   <li>May be {@code null}</li>
     *   <li>Access via {@link #getCause()}</li>
     *   <li>The placement of this argument compared to the
     *   {@linkplain #TimeoutException(long, String, Object...) other constructor}
     *   may appear odd.  This is necessary as parameters {@code format} and {@code optArgArr} form
     *   a pair, where (i) {@code optArgArr} is varargs, and (ii) both are passed directly to
     *   {@link String#format(String, Object...)}.</li>
     * </ul>
     * @param format
     * <ul>
     *   <li>passed directly to {@link String#format(String, Object...)}</li>
     *   <li>must not be {@code null}, empty, or only
     *   {@linkplain Character#isWhitespace(char) whitespace}</li>
     * </ul>
     * @param optArgArr
     *        optional list of arguments to pass directly to
     *        {@link String#format(String, Object...)}
     * 
     * @throws NullPointerException
     *         if {@code format} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code timeoutMillis} is not positive</li>
     *   <li>if {@code format} is empty or only
     *   {@linkplain Character#isWhitespace(char) whitespace}</li>
     * </ul>
     */
    public TimeoutException(
            long timeoutMillis,
            Throwable optCause,
            String format,
            Object... optArgArr) {
        this(timeoutMillis, optCause, false, format, optArgArr);
    }
    
    /**
     * Copy constructor.
     * 
     * @throws NullPointerException
     *         if {@code other} is {@code null}
     */
    public TimeoutException(TimeoutException other) {
        this(
                ObjectArgs.checkNotNull(other, "other").getTimeoutMillis(),
                other.getCause(),
                true,
                other.getMessage());
    }
    
    /**
     * Root constructor for copies and non-copies.
     * 
     * @param timeoutMillis
     * @param optCause
     * @param isCopy
     * <ul>
     *   <li>if {@code true}, this is a copy constructor call -- copy the message exactly</li>
     *   <li>if {@code false}, this is <b>not</b> a copy constructor call -- append the message
     *   with additional details</li>
     * </ul>
     * @param format
     * @param optArgArr
     */
    protected TimeoutException(
            long timeoutMillis,
            Throwable optCause,
            boolean isCopy,
            String format,
            Object... optArgArr) {
        super(
                _createMessage(timeoutMillis, isCopy, format, optArgArr),
                optCause);
        
        _timeoutMillis = timeoutMillis;
    }
    
    private static String _createMessage(
            long timeoutMillis,
            boolean isCopy,
            String format,
            Object... optArgArr) {
        LongArgs.checkPositive(timeoutMillis, "timeoutMillis");
        StringArgs.checkNotEmptyOrWhitespace(format, "format");
        
        String x = String.format(format, optArgArr);
        if (!isCopy) {
            x += String.format("%nTimeout after %d milliseconds", timeoutMillis);
        }
        return x;
    }
    
    /**
     * @return number of milliseconds waited before timeout
     */
    public long getTimeoutMillis() {
        return _timeoutMillis;
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(getTimeoutMillis());
        result = 31 * result + ThrowableUtils.hashCode(this, IncludeStackTrace.YES);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof TimeoutException) {
            final TimeoutException other = (TimeoutException) obj;
            result =
                ThrowableUtils.equals(this, other, IncludeStackTrace.YES)
                && this.getTimeoutMillis() == other.getTimeoutMillis();
        }
        return result;
    }
    
    /**
     * Used by test code.
     */
    boolean equalsExcludingStackTrace(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof TimeoutException) {
            final TimeoutException other = (TimeoutException) obj;
            result =
                ThrowableUtils.equals(this, other, IncludeStackTrace.NO)
                && this.getTimeoutMillis() == other.getTimeoutMillis();
        }
        return result;
    }
    
    @Override
    public String toString() {
        String x = String.format(
            "%s ["
            + "%n\tgetTimeoutMillis()=%s,%s",
            getClass().getSimpleName(),
            getTimeoutMillis(),
            ThrowableUtils.toString(this));
        return x;
    }
}
