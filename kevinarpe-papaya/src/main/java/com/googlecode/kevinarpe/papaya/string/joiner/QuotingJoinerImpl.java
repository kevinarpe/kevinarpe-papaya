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
@FullyTested
final class QuotingJoinerImpl
implements QuotingJoiner {

    private final String _separator;
    private final String _leftQuote;
    private final String _rightQuote;
    private final String _nullText;
    private final String _noElementsText;
    private final boolean _skipNullsFlag;

    QuotingJoinerImpl(String separator) {
        this(
            ObjectArgs.checkNotNull(separator, "separator"),
            QuotingJoinerUtils.DEFAULT_NULL_TEXT,
            QuotingJoinerUtils.DEFAULT_LEFT_QUOTE,
            QuotingJoinerUtils.DEFAULT_RIGHT_QUOTE,
            QuotingJoinerUtils.DEFAULT_NO_ELEMENTS_TEXT,
            QuotingJoinerUtils.DEFAULT_SKIP_NULLS_FLAG);
    }

    QuotingJoinerImpl(
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

    @Override
    public QuotingJoinerImpl withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        QuotingJoinerImpl x =
            new QuotingJoinerImpl(
                separator, _nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoinerImpl withSeparator(char separator) {
        String separatorString = String.valueOf(separator);
        QuotingJoinerImpl x = withSeparator(separatorString);
        return x;
    }

    @Override
    public String withSeparator() {
        return _separator;
    }

    @Override
    public QuotingJoinerImpl withQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingJoinerImpl x = new QuotingJoinerImpl(
            _separator, _nullText, leftQuote, rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoinerImpl withQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoinerImpl x = withQuotes(leftQuote, rightQuoteString);
        return x;
    }

    @Override
    public QuotingJoinerImpl withQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingJoinerImpl x = withQuotes(leftQuoteString, rightQuote);
        return x;
    }

    @Override
    public QuotingJoinerImpl withQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingJoinerImpl x = withQuotes(leftQuoteString, rightQuoteString);
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
    public QuotingJoinerImpl useForNoElements(String noElementsText) {
        ObjectArgs.checkNotNull(noElementsText, "noElementsText");

        QuotingJoinerImpl x =
            new QuotingJoinerImpl(
                _separator, _nullText, _leftQuote, _rightQuote, noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoinerImpl useForNoElements(char noElementsText) {
        String noElementsTextString = String.valueOf(noElementsText);
        QuotingJoinerImpl x = useForNoElements(noElementsTextString);
        return x;
    }

    @Override
    public String useForNoElements() {
        return _noElementsText;
    }

    @Override
    public QuotingJoinerImpl useForNull(String nullText) {
        ObjectArgs.checkNotNull(nullText, "nullText");

        QuotingJoinerImpl x =
            new QuotingJoinerImpl(
                _separator, nullText, _leftQuote, _rightQuote, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public QuotingJoinerImpl useForNull(char nullText) {
        String nullTextString = String.valueOf(nullText);
        QuotingJoinerImpl x = useForNull(nullTextString);
        return x;
    }

    @Override
    public String useForNull() {
        return _nullText;
    }

    @Override
    public QuotingJoinerImpl skipNulls(boolean flag) {
        QuotingJoinerImpl x =
            new QuotingJoinerImpl(
                _separator, _nullText, _leftQuote, _rightQuote, _noElementsText, flag);
        return x;
    }

    @Override
    public boolean skipNulls() {
        return _skipNullsFlag;
    }

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

    public Appendable appendTo(Appendable appendable, Object[] partArr)
    throws IOException {
        ObjectArgs.checkNotNull(partArr, "partArr");

        Iterable<Object> partIterable = Arrays.asList(partArr);
        appendTo(appendable, partIterable);
        return appendable;
    }

    public Appendable appendTo(Appendable appendable, Iterable<?> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<?> partIter = partIterable.iterator();
        appendTo(appendable, partIter);
        return appendable;
    }

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
        String x = (null == value) ? _nullText : value.toString();
        x = leftQuote + x + rightQuote;
        return x;
    }

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
        catch (IOException impossible) {
            throw new Error("Unexpected exception", impossible);
        }
        return builder;
    }

    @Override
    public String join(Object part1, Object part2, Object... partArr) {
        ObjectArgs.checkNotNull(partArr, "valueArr");

        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(part1, part2, partArr);
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
    public QuotingMapJoinerImpl withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoinerImpl x = new QuotingMapJoinerImpl(this, keyValueSeparator);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoinerImpl x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }
}
