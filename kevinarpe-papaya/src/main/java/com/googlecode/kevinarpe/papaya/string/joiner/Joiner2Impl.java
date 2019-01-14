package com.googlecode.kevinarpe.papaya.string.joiner;

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
    private final Formatter2 _elementFormatter;
    private final Formatter2 _finalFormatter;
    private final String _nullText;
    private final String _noElementsText;
    private final boolean _skipNullsFlag;

    Joiner2Impl(String separator) {
        this(
            ObjectArgs.checkNotNull(separator, "separator"),
            Joiner2Utils.DEFAULT_NULL_TEXT,
            Joiner2Utils.DEFAULT_ELEMENT_FORMATTER,
            Joiner2Utils.DEFAULT_FINAL_FORMATTER,
            Joiner2Utils.DEFAULT_NO_ELEMENTS_TEXT,
            Joiner2Utils.DEFAULT_SKIP_NULLS_FLAG);
    }

    Joiner2Impl(
        String separator,
        String nullText,
        Formatter2 elementFormatter,
        Formatter2 finalFormatter,
        String noElementsText,
        boolean skipNullsFlag) {
        this._separator = separator;
        this._nullText = nullText;
        this._elementFormatter = elementFormatter;
        this._finalFormatter = finalFormatter;
        this._noElementsText = noElementsText;
        this._skipNullsFlag = skipNullsFlag;
    }

    @Override
    public Joiner2Impl withSeparator(String separator) {
        ObjectArgs.checkNotNull(separator, "separator");

        Joiner2Impl x =
            new Joiner2Impl(
                separator,
                _nullText,
                _elementFormatter,
                _finalFormatter,
                _noElementsText,
                _skipNullsFlag);
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
    public Joiner2Impl withElementFormatter(Formatter2 elementFormatter) {
        ObjectArgs.checkNotNull(elementFormatter, "elementFormatter");

        Joiner2Impl x =
            new Joiner2Impl(
                _separator,
                _nullText,
                elementFormatter,
                _finalFormatter,
                _noElementsText,
                _skipNullsFlag);
        return x;
    }

    @Override
    public Formatter2 withElementFormatter() {
        return _elementFormatter;
    }

    @Override
    public Joiner2Impl withFinalFormatter(Formatter2 finalFormatter) {
        ObjectArgs.checkNotNull(finalFormatter, "finalFormatter");

        Joiner2Impl x =
            new Joiner2Impl(
                _separator,
                _nullText,
                _elementFormatter,
                finalFormatter,
                _noElementsText,
                _skipNullsFlag);
        return x;
    }

    @Override
    public Formatter2 withFinalFormatter() {
        return _finalFormatter;
    }

    @Override
    public Joiner2Impl useForNoElements(String noElementsText) {
        ObjectArgs.checkNotNull(noElementsText, "noElementsText");

        Joiner2Impl x =
            new Joiner2Impl(
                _separator,
                _nullText,
                _elementFormatter,
                _finalFormatter,
                noElementsText,
                _skipNullsFlag);
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
                _separator,
                nullText,
                _elementFormatter,
                _finalFormatter,
                _noElementsText,
                _skipNullsFlag);
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
                _separator, _nullText, _elementFormatter, _finalFormatter, _noElementsText, flag);
        return x;
    }

    @Override
    public boolean skipNulls() {
        return _skipNullsFlag;
    }

    @Override
    public Appendable appendTo(Appendable appendable, Object part1, Object part2, Object... partArr)
    throws IOException {
        String x = _join(part1, part2, partArr);
        _appendTo(appendable, x);
        return appendable;
    }

    public Appendable appendTo(Appendable appendable, Object[] partArr)
    throws IOException {
        ObjectArgs.checkNotNull(partArr, "partArr");

        String x = _join(partArr);
        _appendTo(appendable, x);
        return appendable;
    }

    public Appendable appendTo(Appendable appendable, Iterable<?> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        String x = _join(partIterable);
        _appendTo(appendable, x);
        return appendable;
    }

    public Appendable appendTo(Appendable appendable, Iterator<?> partIter)
    throws IOException {
        ObjectArgs.checkNotNull(partIter, "partIter");

        String x = _join(partIter);
        _appendTo(appendable, x);
        return appendable;
    }

    @Override
    public StringBuilder appendTo(
            StringBuilder builder, Object part1, Object part2, Object... partArr) {
        String x = _join(part1, part2, partArr);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Object[] partArr) {
        String x = _join(partArr);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        String x = _join(partIterable);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Iterator<?> partIter) {
        ObjectArgs.checkNotNull(partIter, "partIter");

        String x = _join(partIter);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public String join(Object part1, Object part2, Object... partArr) {
        String x = _join(part1, part2, partArr);
        return x;
    }

    @Override
    public String join(Object[] partArr) {
        String x = _join(partArr);
        return x;
    }

    @Override
    public String join(Iterable<?> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        String x = _join(partIterable);
        return x;
    }

    @Override
    public String join(Iterator<?> partIter) {
        ObjectArgs.checkNotNull(partIter, "partIter");

        String x = _join(partIter);
        return x;
    }

    private String _join(Object part1, Object part2, Object... partArr) {
        List<Object> list = Lists2.newUnmodifiableListFromTwoOrMoreValues(part1, part2, partArr);
        String x = _join(list);
        return x;
    }

    private String _join(Object[] partArr) {
        ObjectArgs.checkNotNull(partArr, "partArr");

        List<Object> partList = Arrays.asList(partArr);
        String x = _join(partList);
        return x;
    }

    private String _join(Iterable<?> partIterable) {
        Iterator<?> partIter = partIterable.iterator();
        String x = _join(partIter);
        return x;
    }

    private String _join(Iterator<?> partIter) {
        String result = null;
        if (partIter.hasNext()) {
            StringBuilder sb = new StringBuilder();
            _appendNext(sb, "", partIter);
            while (partIter.hasNext()) {
                _appendNext(sb, _separator, partIter);
            }
            result = sb.toString();
        }
        else {
            result = _noElementsText;
        }
        result = _finalFormatter.format(result);
        return result;
    }

    private void _appendNext(StringBuilder builder, String separator, Iterator<?> partIter) {
        Object value = partIter.next();
        if (null == value && _skipNullsFlag) {
            return;
        }
        String valueString = _toString(value);
        builder.append(separator);
        builder.append(valueString);
    }

    private String _toString(Object value) {
        if (null == value) {
            return _nullText;
        }
        String x = _elementFormatter.format(value);
        return x;
    }

    private void _appendTo(Appendable appendable, String s)
        throws IOException {
        ObjectArgs.checkNotNull(appendable, "appendable");

        appendable.append(s);
    }

    private void _appendTo(StringBuilder builder, String s) {
        ObjectArgs.checkNotNull(builder, "builder");

        builder.append(s);
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
