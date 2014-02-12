package com.googlecode.kevinarpe.papaya.string;

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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class QuotingJoiner {

    // TODO: Also add transform function?  Or create class: TransformingQuotingJoiner?

    public static final String DEFAULT_LEFT_QUOTE = "";
    public static final String DEFAULT_RIGHT_QUOTE = "";
    public static final String DEFAULT_NULL_TEXT = null;
    public static final String DEFAULT_EMPTY_TEXT = "";
    public static final boolean DEFAULT_SKIP_NULLS = false;

    private final String _separator;
    private final String _leftQuote;
    private final String _rightQuote;
    private final String _nullText;
    private final String _emptyText;
    private final boolean _skipNulls;

    private QuotingJoiner(String separator) {
        this(
            separator,
            DEFAULT_NULL_TEXT,
            DEFAULT_LEFT_QUOTE,
            DEFAULT_RIGHT_QUOTE,
            DEFAULT_EMPTY_TEXT,
            DEFAULT_SKIP_NULLS);
    }

    private QuotingJoiner(
            String separator,
            String nullText,
            String leftQuote,
            String rightQuote,
            String emptyText,
            boolean skipNulls) {
        this._separator = separator;
        this._nullText = nullText;
        this._leftQuote = leftQuote;
        this._rightQuote = rightQuote;
        this._emptyText = emptyText;
        this._skipNulls = skipNulls;
    }

    public static QuotingJoiner on(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x = new QuotingJoiner(separator);
        return x;
    }

    public static QuotingJoiner on(char separator) {
        String separatorString = String.valueOf(separator);

        QuotingJoiner x = new QuotingJoiner(separatorString);
        return x;
    }

    public QuotingJoiner withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x =
            new QuotingJoiner(
                separator, _nullText, _leftQuote, _rightQuote, _emptyText, _skipNulls);
        return x;
    }

    public QuotingJoiner withSeparator(char separator) {
        String separatorString = String.valueOf(separator);

        QuotingJoiner x =
            new QuotingJoiner(
                separatorString, _nullText, _leftQuote, _rightQuote, _emptyText, _skipNulls);
        return x;
    }

    public String withSeparator() {
        return _separator;
    }

    public QuotingJoiner withQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingJoiner x =
            new QuotingJoiner(_separator, _nullText, leftQuote, rightQuote, _emptyText, _skipNulls);
        return x;
    }

    public QuotingJoiner withQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoiner x = withQuotes(leftQuote, rightQuoteString);
        return x;
    }

    public QuotingJoiner withQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingJoiner x = withQuotes(leftQuoteString, rightQuote);
        return x;
    }

    public QuotingJoiner withQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoiner x = withQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    public String withLeftQuote() {
        return _leftQuote;
    }

    public String withRightQuote() {
        return _rightQuote;
    }

    public QuotingJoiner useForEmpty(String emptyText) {
        ObjectArgs.checkNotNull(emptyText, "emptyText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _nullText, _leftQuote, _rightQuote, emptyText, _skipNulls);
        return x;
    }

    public QuotingJoiner useForEmpty(char emptyText) {
        String emptyTextString = String.valueOf(emptyText);
        QuotingJoiner x = useForEmpty(emptyTextString);
        return x;
    }

    public String useForEmpty() {
        return _emptyText;
    }

    public QuotingJoiner useForNull(String nullText) {
        ObjectArgs.checkNotNull(nullText, "nullText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, nullText, _leftQuote, _rightQuote, _emptyText, _skipNulls);
        return x;
    }

    public QuotingJoiner useForNull(char nullText) {
        String nullTextString = String.valueOf(nullText);
        QuotingJoiner x = useForNull(nullTextString);
        return x;
    }

    public String useForNull() {
        return _nullText;
    }

    public QuotingJoiner skipNulls(boolean flag) {
        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _nullText, _leftQuote, _rightQuote, _emptyText, flag);
        return x;
    }

    public boolean skipNulls() {
        return _skipNulls;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(
            TAppendable appendable,
            Object value1,
            Object value2,
            Object... valueArr)
    throws IOException {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(value1, value2, valueArr);
        appendTo(appendable, list);
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Object[] partArr)
    throws IOException {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            appendable.append(_emptyText);
        }
        else {
            Iterable<Object> partIterable = Arrays.asList(partArr);
            appendTo(appendable, partIterable);
        }
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Iterable<?> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(appendable, partIter);
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Iterator<?> partIter)
    throws IOException {
        ObjectArgs.checkNotNull(appendable, "appendable");
        ObjectArgs.checkNotNull(partIter, "partIter");

        if (partIter.hasNext()) {
            _appendNext(appendable, partIter);
            while (partIter.hasNext()) {
                appendable.append(_separator);
                _appendNext(appendable, partIter);
            }
        }
        else {
            appendable.append(_emptyText);
        }
        return appendable;
    }

    private <TAppendable extends Appendable>
    void _appendNext(TAppendable appendable, Iterator<?> partIter)
        throws IOException {
        Object value = partIter.next();
        String valueString = _toString(value, this, _leftQuote, _rightQuote);
        appendable.append(valueString);
    }

    static String _toString(
        Object value, QuotingJoiner quotingJoiner, String leftQuote, String rightQuote) {
        String x = null;
        if (null == value) {
            String nullText = quotingJoiner.useForNull();
            if (null == nullText) {
                throw new NullPointerException(
                    String.format("Failed to convert null value to text.  See %s.useForNull()",
                        QuotingJoiner.class.getSimpleName()));
            }
            x = nullText;
        }
        else {
            x = value.toString();
        }
        x = leftQuote + x + rightQuote;
        return x;
    }

    public StringBuilder appendTo(
            StringBuilder builder,
            Object value1,
            Object value2,
            Object... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(value1, value2, valueArr);
        appendTo(builder, list);
        return builder;
    }

    public StringBuilder appendTo(StringBuilder builder, Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            return builder;
        }
        Iterable<Object> partIterable = Arrays.asList(partArr);
        appendTo(builder, partIterable);
        return builder;
    }

    public StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(builder, partIter);
        return builder;
    }

    public StringBuilder appendTo(StringBuilder builder, Iterator<?> partIter) {
        try {
            appendTo((Appendable) builder, partIter);
        }
        catch (IOException e) {
            throw new Error("Unexpected exception", e);
        }
        return builder;
    }

    public String join(Object value1, Object value2, Object... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(value1, value2, valueArr);
        String x = join(list);
        return x;
    }

    public String join(Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        if (0 == partArr.length) {
            return _emptyText;
        }
        Iterable<Object> partIterable = Arrays.asList(partArr);
        String x = join(partIterable);
        return x;
    }

    public String join(Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        String x = join(partIter);
        return x;
    }

    public String join(Iterator<?> partIter) {
        ObjectArgs.checkNotNull(partIter, "partIter");

        StringBuilder sb = new StringBuilder();
        appendTo(sb, partIter);
        String sbs = sb.toString();
        return sbs;
    }

    public QuotingMapJoiner withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoiner x = new QuotingMapJoiner(this, keyValueSeparator);
        return x;
    }

    public QuotingMapJoiner withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoiner x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }
}
