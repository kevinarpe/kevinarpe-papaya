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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Think about factory interfaces.  Do we need this?
@FullyTested
public final class QuotingJoiner
implements IQuotingJoiner<QuotingJoiner, QuotingMapJoiner> {

    public static final String DEFAULT_LEFT_QUOTE = "";
    public static final String DEFAULT_RIGHT_QUOTE = "";
    public static final String DEFAULT_NULL_TEXT = null;
    public static final String DEFAULT_NO_ELEMENTS_TEXT = "";
    public static final boolean DEFAULT_SKIP_NULLS_FLAG = false;

    private final String _separator;
    private final String _leftQuote;
    private final String _rightQuote;
    private final String _nullText;
    private final String _noElementsText;
    private final boolean _skipNullsFlag;

    private QuotingJoiner(String separator) {
        this(
            separator,
            DEFAULT_NULL_TEXT,
            DEFAULT_LEFT_QUOTE,
            DEFAULT_RIGHT_QUOTE,
            DEFAULT_NO_ELEMENTS_TEXT,
            DEFAULT_SKIP_NULLS_FLAG);
    }

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

    @Override
    public QuotingJoiner withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoiner x =
            new QuotingJoiner(
                separator, _nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoiner withSeparator(char separator) {
        String separatorString = String.valueOf(separator);

        QuotingJoiner x = new QuotingJoiner(
            separatorString, _nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public String withSeparator() {
        return _separator;
    }

    @Override
    public QuotingJoiner withQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingJoiner x = new QuotingJoiner(
            _separator, _nullText, leftQuote, rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoiner withQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoiner x = withQuotes(leftQuote, rightQuoteString);
        return x;
    }

    @Override
    public QuotingJoiner withQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingJoiner x = withQuotes(leftQuoteString, rightQuote);
        return x;
    }

    @Override
    public QuotingJoiner withQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoiner x = withQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    @Override
    public String withLeftQuote() {
        return _leftQuote;
    }

    @Override
    public String withRightQuote() {
        return _rightQuote;
    }

    @Override
    public QuotingJoiner useForNoElements(String noElementsText) {
        ObjectArgs.checkNotNull(noElementsText, "noElementsText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _nullText, _leftQuote, _rightQuote, noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoiner useForNoElements(char noElementsText) {
        String noElementsTextString = String.valueOf(noElementsText);
        QuotingJoiner x = useForNoElements(noElementsTextString);
        return x;
    }

    @Override
    public String useForNoElements() {
        return _noElementsText;
    }

    @Override
    public QuotingJoiner useForNull(String nullText) {
        ObjectArgs.checkNotNull(nullText, "nullText");

        QuotingJoiner x =
            new QuotingJoiner(
                _separator, nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoiner useForNull(char nullText) {
        String nullTextString = String.valueOf(nullText);
        QuotingJoiner x = useForNull(nullTextString);
        return x;
    }

    @Override
    public String useForNull() {
        return _nullText;
    }

    @Override
    public QuotingJoiner skipNulls(boolean flag) {
        QuotingJoiner x =
            new QuotingJoiner(
                _separator, _nullText, _leftQuote, _rightQuote, _noElementsText, flag);
        return x;
    }

    @Override
    public boolean skipNulls() {
        return _skipNullsFlag;
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

        Iterable<Object> partIterable = Arrays.asList(partArr);
        appendTo(appendable, partIterable);
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

    @Override
    public StringBuilder appendTo(
            StringBuilder builder,
            Object value1,
            Object value2,
            Object... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        List<?> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(value1, value2, valueArr);
        appendTo(builder, list);
        return builder;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        Iterable<?> partIterable = Arrays.asList(partArr);
        appendTo(builder, partIterable);
        return builder;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(builder, partIter);
        return builder;
    }

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

    @Override
    public String join(Object value1, Object value2, Object... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(value1, value2, valueArr);
        String x = join(list);
        return x;
    }

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

    @Override
    public String join(Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        String x = join(partIter);
        return x;
    }

    @Override
    public String join(Iterator<?> partIter) {
        StringBuilder sb = new StringBuilder();
        appendTo(sb, partIter);
        String sbs = sb.toString();
        return sbs;
    }

    @Override
    public QuotingMapJoiner withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoiner x = new QuotingMapJoiner(this, keyValueSeparator);
        return x;
    }

    @Override
    public QuotingMapJoiner withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoiner x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }
}
