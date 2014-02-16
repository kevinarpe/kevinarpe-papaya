package com.googlecode.kevinarpe.papaya.string.joiner;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Joiner;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Extension of Google Guava's {@link Joiner}.  To construct a new instance, see {@link #on(String)}
 * and {@link #on(char)}.
 * <p>
 * Differences:
 * <ul>
 *     <li>{@link #withQuotes(String, String)}: Prefix and suffix for each joined element</li>
 *     <li>Default text for {@code null} values is {@code "null"}.  This matches the behavior of
 *     {@link Formatter}. By default, class {@link Joiner} will throw a {@link NullPointerException}
 *     when joining a {@code null} value, unless {@link Joiner#useForNull(String)} is called before
 *     joining.  This is a source of many surprising/annoying/accidental runtime exceptions.</li>
 *     <li>{@link #useForNoElements(String)}: During a join, if no elements are found, this text
 *     is used.  When joining elements for an exception or log message, {@code "(empty)"} is a good
 *     value to indicate to (exception and log) readers that no elements were found.</li>
 *     <li>Implements interface {@link IQuotingJoiner}.  This is helpful for mocking during testing.
 *     Due to Java interface limitations with generic methods, the methods
 *     {@code appendTo(Appendable, *)} are slightly different from {@link Joiner}.  The
 *     {@code Appendable} reference is not a generic type (for input and output).</li>
 *     <li>All settings may always be changed, e.g., separator, null text, etc.  Class
 *     {@code Joiner} does now allow these attributes to be set more than once.</li>
 *     <li>Settings accessors, e.g., {@link #withSeparator()}.</li>
 *     <li>Default settings are available as {@code public static final} members, e.g.,
 *     {@link #DEFAULT_NULL_TEXT}.</li>
 * </ul>
 * <p>
 * See {@link SharedQuotingJoinerSettings} for an inheritance diagram.
 * <p>
 * Examples:
 * <pre>{@code
 * QuotingJoiner.on(", ").withQuotes("[", "]").join(list) -> "[a], [b], [c], ..."
 * QuotingJoiner.on(", ").withQuotes("[", "]").join(listWithNulls) -> "[a], [b], [null], [c], ..."
 * QuotingJoiner.on(", ").withQuotes("[", "]").skipNulls(true).join(listWithNulls) -> "[a], [b], [c], ..."
 * QuotingJoiner.on(", ").withQuotes("[", "]").useForNoElements("(empty)").join(emptyList) -> "(empty)"
 * }</pre>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see IQuotingJoiner
 * @see QuotingMapJoiner
 */
@FullyTested
public final class QuotingJoiner
implements IQuotingJoiner<QuotingJoiner, QuotingMapJoiner> {

    /**
     * @see SharedQuotingJoinerSettings#DEFAULT_LEFT_QUOTE
     */
    public static final String DEFAULT_LEFT_QUOTE = SharedQuotingJoinerSettings.DEFAULT_LEFT_QUOTE;

    /**
     * @see SharedQuotingJoinerSettings#DEFAULT_RIGHT_QUOTE
     */
    public static final String DEFAULT_RIGHT_QUOTE =
        SharedQuotingJoinerSettings.DEFAULT_RIGHT_QUOTE;

    /**
     * @see QuotingJoinerSettings#DEFAULT_NULL_TEXT
     */
    public static final String DEFAULT_NULL_TEXT = QuotingJoinerSettings.DEFAULT_NULL_TEXT;

    /**
     * @see SharedQuotingJoinerSettings#DEFAULT_NO_ELEMENTS_TEXT
     */
    public static final String DEFAULT_NO_ELEMENTS_TEXT =
        SharedQuotingJoinerSettings.DEFAULT_NO_ELEMENTS_TEXT;

    /**
     * @see QuotingJoinerSettings#DEFAULT_SKIP_NULLS_FLAG
     */
    public static final boolean DEFAULT_SKIP_NULLS_FLAG =
        QuotingJoinerSettings.DEFAULT_SKIP_NULLS_FLAG;

    private final String _separator;
    private final String _leftQuote;
    private final String _rightQuote;
    private final String _nullText;
    private final String _noElementsText;
    private final boolean _skipNullsFlag;

    private QuotingJoiner(
            String separator,
            String nullText,
            String leftQuote,
            String rightQuote,
            String noElementsText,
            boolean skipNullsFlag) {
        this._separator = separator;
        this._nullText = nullText;
        this._leftQuote = leftQuote;
        this._rightQuote = rightQuote;
        this._noElementsText = noElementsText;
        this._skipNullsFlag = skipNullsFlag;
    }

    /**
     * Constructs a new instance.  There is no public constructor for this class, so use this
     * method instead.
     *
     * @param separator
     *        String to insert between elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ", "} (comma + space)
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code separator} is {@code null}
     *
     * @see #on(char)
     * @see #withSeparator(String)
     * @see #withSeparator(char)
     */
    public static QuotingJoiner on(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x =
            new QuotingJoiner(
                separator,
                DEFAULT_NULL_TEXT,
                DEFAULT_LEFT_QUOTE,
                DEFAULT_RIGHT_QUOTE,
                DEFAULT_NO_ELEMENTS_TEXT,
                DEFAULT_SKIP_NULLS_FLAG);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public static QuotingJoiner on(char separator) {
        String separatorString = String.valueOf(separator);
        QuotingJoiner x = on(separatorString);
        return x;
    }

    /**
     * {@inheritDoc}
     *
     * @see #on(String)
     * @see #on(char)
     */
    @Override
    public QuotingJoiner withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x =
            new QuotingJoiner(
                separator, _nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner withSeparator(char separator) {
        String separatorString = String.valueOf(separator);
        QuotingJoiner x = withSeparator(separatorString);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String withSeparator() {
        return _separator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner withQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingJoiner x = new QuotingJoiner(
            _separator, _nullText, leftQuote, rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner withQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoiner x = withQuotes(leftQuote, rightQuoteString);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner withQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingJoiner x = withQuotes(leftQuoteString, rightQuote);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner withQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoiner x = withQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String withLeftQuote() {
        return _leftQuote;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String withRightQuote() {
        return _rightQuote;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner useForNoElements(String noElementsText) {
        ObjectArgs.checkNotNull(noElementsText, "noElementsText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _nullText, _leftQuote, _rightQuote, noElementsText, _skipNullsFlag);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner useForNoElements(char noElementsText) {
        String noElementsTextString = String.valueOf(noElementsText);
        QuotingJoiner x = useForNoElements(noElementsTextString);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String useForNoElements() {
        return _noElementsText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner useForNull(String nullText) {
        ObjectArgs.checkNotNull(nullText, "nullText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner useForNull(char nullText) {
        String nullTextString = String.valueOf(nullText);
        QuotingJoiner x = useForNull(nullTextString);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String useForNull() {
        return _nullText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingJoiner skipNulls(boolean flag) {
        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _nullText, _leftQuote, _rightQuote, _noElementsText, flag);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean skipNulls() {
        return _skipNullsFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Appendable appendTo(
            Appendable appendable,
            Object part1,
            Object part2,
            Object... partArr)
    throws IOException {
        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(part1, part2, partArr);
        appendTo(appendable, list);
        return appendable;
    }

    /**
     * {@inheritDoc}
     */
    public Appendable appendTo(Appendable appendable, Object[] partArr)
    throws IOException {
        ObjectArgs.checkNotNull(partArr, "partArr");

        Iterable<Object> partIterable = Arrays.asList(partArr);
        appendTo(appendable, partIterable);
        return appendable;
    }

    /**
     * {@inheritDoc}
     */
    public Appendable appendTo(Appendable appendable, Iterable<?> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(appendable, partIter);
        return appendable;
    }

    /**
     * {@inheritDoc}
     */
    public Appendable appendTo(Appendable appendable, Iterator<?> partIter)
    throws IOException {
        ObjectArgs.checkNotNull(appendable, "appendable");
        ObjectArgs.checkNotNull(partIter, "partIter");

        if (partIter.hasNext()) {
            _appendNext(appendable, "", partIter);
            while (partIter.hasNext()) {
                _appendNext(appendable, _separator, partIter);
            }
        }
        else {
            appendable.append(_noElementsText);
        }
        return appendable;
    }

    private <TAppendable extends Appendable>
    void _appendNext(TAppendable appendable, String separator, Iterator<?> partIter)
    throws IOException {
        Object value = partIter.next();
        if (null == value && _skipNullsFlag) {
            return;
        }
        String valueString = _toString(value, _leftQuote, _rightQuote);
        appendable.append(separator);
        appendable.append(valueString);
    }

    private String _toString(Object value, String leftQuote, String rightQuote) {
        String x = (null == value) ? _checkNullText() : value.toString();
        x = leftQuote + x + rightQuote;
        return x;
    }

    private String _checkNullText() {
        if (null == _nullText) {
            throw new NullPointerException(String.format(
                "Failed to convert null value to text.  See %s.useForNull(String)",
                QuotingJoiner.class.getSimpleName()));
        }
        return _nullText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder appendTo(
            StringBuilder builder,
            Object part1,
            Object part2,
            Object... partArr) {
        ObjectArgs.checkNotNull(partArr, "valueArr");

        List<?> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(part1, part2, partArr);
        appendTo(builder, list);
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder appendTo(StringBuilder builder, Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        Iterable<?> partIterable = Arrays.asList(partArr);
        appendTo(builder, partIterable);
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(builder, partIter);
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder appendTo(StringBuilder builder, Iterator<?> partIter) {
        try {
            appendTo((Appendable) builder, partIter);
        }
        catch (IOException e) {
            throw new Error("Unexpected exception", e);
        }
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String join(Object part1, Object part2, Object... partArr) {
        ObjectArgs.checkNotNull(partArr, "valueArr");

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(part1, part2, partArr);
        String x = join(list);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String join(Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            return _noElementsText;
        }
        Iterable<Object> partIterable = Arrays.asList(partArr);
        String x = join(partIterable);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String join(Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        String x = join(partIter);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String join(Iterator<?> partIter) {
        StringBuilder sb = new StringBuilder();
        appendTo(sb, partIter);
        String sbs = sb.toString();
        return sbs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingMapJoiner withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoiner x = new QuotingMapJoiner(this, keyValueSeparator);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuotingMapJoiner withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoiner x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }
}
