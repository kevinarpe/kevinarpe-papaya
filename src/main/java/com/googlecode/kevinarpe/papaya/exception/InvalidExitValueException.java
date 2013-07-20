package com.googlecode.kevinarpe.papaya.exception;

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

import java.util.Collection;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.process.Process2;

/**
 * Thrown by class {@link Process2} if the child process exit value is unexpected.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Process2#checkExitValue(Collection)
 * @see Process2#waitForThenCheckExitValue(long, Collection)
 */
@FullyTested
public class InvalidExitValueException
extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private final int _exitValue;
    private final List<Integer> _validExitValueList;
    
    /**
     * This is a convenience method for
     * {@link #InvalidExitValueException(int, Collection, Throwable, String, Object...)}
     * where param {@code optCause} is {@code null}.
     */
    public InvalidExitValueException(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            String format,
            Object... optArgArr) {
        this(exitValue, validExitValueCollection, null, false, format, optArgArr);
    }

    /**
     * Constructor.  The message, as specified by parameters {@code format} and {@code optArgArr}
     * has a suffix appended with additional details about actual vs. allowed exit values.  The
     * message need not specify these details.
     * <p>
     * Example {@code format}: {@code "Failed to run external filter command: %s"}
     * Example {@code optArgArr}: (filter command arguments)
     * 
     * @param exitValue
     * <ul>
     *   <li>actual exit value from child process</li>
     *   <li>Accessible via {@link #getExitValue()}</li>
     * </ul>
     * @param validExitValueCollection
     * <ul>
     *   <li>non-empty collection of expected (or valid) child process exit values</li>
     *   <li>Must not be empty or contain {@code null} values</li>
     *   <li>Must not contain the actual exit value from parameter {@code exitValue}</li>
     *   <li>This collection is copied</li>
     * </ul>
     * @param optCause
     * <ul>
     *   <li>optional underlying cause of this exception that is passed directory to superclass
     *   constructor</li>
     *   <li>May be {@code null}</li>
     *   <li>Access via {@link #getCause()}</li>
     *   <li>The placement of this argument compared to the
     *   {@linkplain #InvalidExitValueException(int, Collection, String, Object...) other constructor}
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
     * <ul>
     *   <li>if {@code validExitValueCollection} is {@code null} or contains {@code null}
     *   elements</li>
     *   <li>if {@code format} is {@code null}</li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code validExitValueCollection} is empty</li>
     *   <li>if {@code format} is empty or only
     *   {@linkplain Character#isWhitespace(char) whitespace}</li>
     *   <li>if {@code validExitValueCollection} contains {@code exitValue}</li>
     * </ul>
     */
    public InvalidExitValueException(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object... optArgArr) {
        this(exitValue, validExitValueCollection, optCause, false, format, optArgArr);
    }
    
    /**
     * Copy constructor.
     * 
     * @throws NullPointerException
     *         if {@code other} is {@code null}
     */
    public InvalidExitValueException(InvalidExitValueException other) {
        this(
                ObjectArgs.checkNotNull(other, "other").getExitValue(),
                other.getValidExitValueList(),
                other.getCause(),
                true,
                other.getMessage());
    }
    
    /**
     * Root constructor for copies and non-copies.
     * 
     * @param exitValue
     * @param validExitValueCollection
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
    protected InvalidExitValueException(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            boolean isCopy,
            String format,
            Object... optArgArr) {
        super(
                _createMessage(exitValue, validExitValueCollection, isCopy, format, optArgArr),
                optCause);
        
        if (validExitValueCollection.contains(exitValue)) {
            String x = String.format("Exit value %d is found in list of valid exit values: %s",
                exitValue,
                Joiner.on(", ").join(validExitValueCollection));
            throw new IllegalArgumentException(x);
        }
        
        _exitValue = exitValue;
        _validExitValueList = ImmutableList.copyOf(validExitValueCollection);
    }
    
    private static String _createMessage(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            boolean isCopy,
            String format,
            Object[] optArgArr) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            validExitValueCollection, "validExitValueCollection");
        StringArgs.checkNotEmptyOrWhitespace(format, "format");
        
        String x = String.format(format, optArgArr);
        if (!isCopy) {
            x += String.format(
                "%nInvalid exit value: %d%nValid values: %s",
                exitValue,
                Joiner.on(", ").join(validExitValueCollection));
        }
        return x;
    }

    /**
     * @return actual exit value from child process
     */
    public int getExitValue() {
        return _exitValue;
    }

    /**
     * @return immutable, non-empty list of expected (or valid) child process exit values
     */
    public List<Integer> getValidExitValueList() {
        return _validExitValueList;
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(getExitValue(), getValidExitValueList());
        result = 31 * result + ThrowableUtils.hashCode(this);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof InvalidExitValueException) {
            final InvalidExitValueException other = (InvalidExitValueException) obj;
            result =
                ThrowableUtils.equals(this, other)
                && getExitValue() == other.getExitValue()
                && Objects.equal(this.getValidExitValueList(), other.getValidExitValueList());
        }
        return result;
    }
    
    /**
     * Used by test code.
     */
    boolean equalsExcludingStackTrace(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof InvalidExitValueException) {
            final InvalidExitValueException other = (InvalidExitValueException) obj;
            result =
                ThrowableUtils.equalsExcludingStackTrace(this, other)
                && this.getExitValue() == other.getExitValue()
                && Objects.equal(this.getValidExitValueList(), other.getValidExitValueList());
        }
        return result;
    }

    @Override
    public String toString() {
        String x = String.format(
            "%s ["
            + "%n\tgetExitValue()=%s,"
            + "%n\tgetValidExitValueList()='%s',%s",
            getClass().getSimpleName(),
            getExitValue(),
            Joiner.on(", ").join(getValidExitValueList()),
            ThrowableUtils.toString(this));
        return x;
    }
}
