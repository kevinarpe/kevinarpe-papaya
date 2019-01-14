package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Formatter;

/**
 * Formats a value using {@code String.format()} with an incrementing counter.  Unlike
 * {@link StringFormatter2}, this class has state.  The next count is stored internally.  Be careful
 * when reusing instances of this class.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see AbstractStringFormatter2
 * @see Formatter2
 */
@FullyTested
public final class CountingStringFormatter2
extends AbstractStringFormatter2
implements Formatter2 {

    /**
     * Default value for first count.  Override the default using {@link #withFirstCount(int)}.
     */
    public static final int DEFAULT_FIRST_COUNT = 1;

    private final Formatter2 _formatter;
    private final int _firstCount;
    private final StringFormatterHelper _stringFormatterHelper;

    private int _countOffset;

    /**
     * This is a convenience constructor to call
     * {@link #CountingStringFormatter2(String, Formatter2)} where {@code formatter} is
     * {@link DefaultFormatter2#INSTANCE}.
     */
    public CountingStringFormatter2(String format) {
        this(format, DefaultFormatter2.INSTANCE);
    }

    /**
     * Creates a new two phase formatter with a format string.
     * <ol>
     *     <li>Use {@code formatter} to convert a value to string</li>
     *     <li>Call {@link String#format(String, Object...)} with {@code format}, current count, and
     *     result from previous step</li>
     * </ol>
     * <p>
     * To modify the default first count, see {@link #withFirstCount(int)}.
     * <p>
     * Example: {@code new CountingStringFormatter("%d) %s", new PathFormatter())}
     *
     * @param format
     * <ul>
     *     <li>Format string for {@link String#format(String, Object...)}</li>
     *     <li>Must not be {@code null}, empty, or only whitespace</li>
     *     <li>Must have at least one {@code "%d"} <u>and</u> one {@code "%s"} token</li>
     *     <li>Example: {@code "%d: [%s]"}</li>
     * </ul>
     * @param formatter
     *        used in step 1 to format each value
     *
     * @throws NullPointerException
     *         if {@code format} or {@code formatter} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code format} is empty or only whitespace
     *
     * @see #DEFAULT_FIRST_COUNT
     * @see #withFirstCount(int)
     * @see Formatter
     */
    public CountingStringFormatter2(String format, Formatter2 formatter) {
        this(
            format,
            ObjectArgs.checkNotNull(formatter, "formatter"),
            StringFormatterHelperImpl.INSTANCE);
    }

    CountingStringFormatter2(
            String format, Formatter2 formatter, StringFormatterHelper stringFormatterHelper) {
        this(
            format,
            ObjectArgs.checkNotNull(formatter, "formatter"),
            DEFAULT_FIRST_COUNT,
            ObjectArgs.checkNotNull(stringFormatterHelper, "stringFormatterHelper"));
    }

    private CountingStringFormatter2(
            String format,
            Formatter2 formatter,
            int firstCount,
            StringFormatterHelper stringFormatterHelper) {
        super(format);
        _formatter = formatter;
        _firstCount = firstCount;
        _stringFormatterHelper = stringFormatterHelper;
        _countOffset = 0;
    }

    /**
     * Constructs a new instance with new first count.
     *
     * @param firstCount
     *        first count used during formatting, e.g., 0, 1, 100, etc.
     *
     * @return new instance with new first count
     *
     * @throws IllegalStateException
     *         if this method is called after {@link #format(Object)}
     *
     * @see #DEFAULT_FIRST_COUNT
     */
    public CountingStringFormatter2 withFirstCount(int firstCount) {
        if (0 != _countOffset) {
            throw new IllegalStateException(
                "Cannot change first count after format() has been successfully called");
        }
        CountingStringFormatter2 x =
            new CountingStringFormatter2(
                getFormat(), _formatter, firstCount, _stringFormatterHelper);
        return x;
    }

    /**
     * Increments the internal count only if no formatting exception is thrown.
     * <hr>
     * Inherited docs:
     * <br>
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException
     *         if {@link String#format(String, Object...)} fails
     *
     * @see #getNextCount()
     */
    @Override
    public String format(Object value) {
        final String format = getFormat();
        String valueString = _formatter.format(value);
        String x =
            _stringFormatterHelper.format("value", format, _firstCount + _countOffset, valueString);
        ++_countOffset;
        return x;
    }

    /**
     * @return first count used by {@link #format(Object)}
     *
     * @see #withFirstCount(int)
     * @see #getNextCount()
     */
    public int getFirstCount() {
        return _firstCount;
    }

    /**
     * @return next count to be used by {@link #format(Object)}
     *
     * @see #getFirstCount()
     */
    public int getNextCount() {
        int x = _firstCount + _countOffset;
        return x;
    }

    @Override
    public String toString() {
        String x = String.format(
            "%s: {%n\tgetFormat(): '%s'%n\tthis._formatter: %s%n\tgetFirstCount(): %d%n\tgetNextCount(): %d }",
            this.getClass().getName(),
            getFormat(),
            _formatter,
            getFirstCount(),
            getNextCount());
        return x;
    }
}
