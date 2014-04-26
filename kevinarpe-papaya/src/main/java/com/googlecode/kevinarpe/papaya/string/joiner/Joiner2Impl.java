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
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class Joiner2Impl
implements Joiner2 {

    private final String _separator;
    private final Formatter2 _formatter;
    private final String _nullText;
    private final String _noElementsText;
    private final boolean _skipNullsFlag;

    Joiner2Impl(String separator) {
        this(
            ObjectArgs.checkNotNull(separator, "separator"),
            Joiner2Utils.DEFAULT_NULL_TEXT,
            Joiner2Utils.DEFAULT_FORMATTER,
            Joiner2Utils.DEFAULT_NO_ELEMENTS_TEXT,
            Joiner2Utils.DEFAULT_SKIP_NULLS_FLAG);
    }

    Joiner2Impl(
            String separator,
            String nullText,
            Formatter2 formatter,
            String noElementsText,
            boolean skipNullsFlag) {
        this._separator = separator;
        this._nullText = nullText;
        this._formatter = formatter;
        this._noElementsText = noElementsText;
        this._skipNullsFlag = skipNullsFlag;
    }

    @Override
    public Joiner2Impl withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        Joiner2Impl x =
            new Joiner2Impl(
                separator, _nullText, _formatter, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public Joiner2Impl withSeparator(char separator) {
        String separatorString = String.valueOf(separator);
        Joiner2Impl x = withSeparator(separatorString);
        return x;
    }

    @Override
    public String withSeparator() {
        return _separator;
    }

    @Override
    public Joiner2Impl withFormatter(Formatter2 formatter) {
        ObjectArgs.checkNotNull(formatter, "formatter");

        Joiner2Impl x = new Joiner2Impl(
            _separator, _nullText, formatter, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public Formatter2 withFormatter() {
        return _formatter;
    }

    @Override
    public Joiner2Impl useForNoElements(String noElementsText) {
        ObjectArgs.checkNotNull(noElementsText, "noElementsText");

        Joiner2Impl x =
            new Joiner2Impl(
                _separator, _nullText, _formatter, noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public Joiner2Impl useForNoElements(char noElementsText) {
        String noElementsTextString = String.valueOf(noElementsText);
        Joiner2Impl x = useForNoElements(noElementsTextString);
        return x;
    }

    @Override
    public String useForNoElements() {
        return _noElementsText;
    }

    @Override
    public Joiner2Impl useForNull(String nullText) {
        ObjectArgs.checkNotNull(nullText, "nullText");

        Joiner2Impl x =
            new Joiner2Impl(
                _separator, nullText, _formatter, _noElementsText, _skipNullsFlag);
        return x;
    }

    @Override
    public Joiner2Impl useForNull(char nullText) {
        String nullTextString = String.valueOf(nullText);
        Joiner2Impl x = useForNull(nullTextString);
        return x;
    }

    @Override
    public String useForNull() {
        return _nullText;
    }

    @Override
    public Joiner2Impl skipNulls(boolean flag) {
        Joiner2Impl x =
            new Joiner2Impl(
                _separator, _nullText, _formatter, _noElementsText, flag);
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
        String valueString = _toString(value);
        appendable.append(separator);
        appendable.append(valueString);
    }

    private String _toString(Object value) {
        String x = (null == value) ? _nullText : value.toString();
        String y = _formatter.format(x);
        return y;
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
    public MapJoiner2Impl withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        MapJoiner2Impl x = new MapJoiner2Impl(this, keyValueSeparator);
        return x;
    }

    @Override
    public MapJoiner2Impl withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        MapJoiner2Impl x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }
}
