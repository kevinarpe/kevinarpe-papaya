package com.googlecode.kevinarpe.papaya.string.joiner;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class MapJoiner2Impl
implements MapJoiner2 {

    private final Joiner2Impl _quotingJoiner;
    private final String _keyValueSeparator;
    private final Formatter2 _keyFormatter;
    private final Formatter2 _valueFormatter;
    private final String _keyNullText;
    private final String _valueNullText;

    // package-private for Joiner2Impl to call
    MapJoiner2Impl(Joiner2Impl quotingJoiner, String keyValueSeparator) {
        this(
            ObjectArgs.checkNotNull(quotingJoiner, "quotingJoiner"),
            ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator"),
            Joiner2Utils.DEFAULT_KEY_FORMATTER,
            Joiner2Utils.DEFAULT_VALUE_FORMATTER,
            Joiner2Utils.DEFAULT_KEY_NULL_TEXT,
            Joiner2Utils.DEFAULT_VALUE_NULL_TEXT);
    }

    private MapJoiner2Impl(
            Joiner2Impl quotingJoiner,
            String keyValueSeparator,
            Formatter2 keyFormatter,
            Formatter2 valueFormatter,
            String keyNullText,
            String valueNullText) {
        _quotingJoiner = quotingJoiner;
        _keyValueSeparator = keyValueSeparator;
        _keyFormatter = keyFormatter;
        _valueFormatter = valueFormatter;
        _keyNullText = keyNullText;
        _valueNullText = valueNullText;
    }

    @Override
    public MapJoiner2Impl withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        MapJoiner2Impl x =
            new MapJoiner2Impl(
                _quotingJoiner,
                keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public MapJoiner2Impl withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        MapJoiner2Impl x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }

    @Override
    public String withKeyValueSeparator() {
        return _keyValueSeparator;
    }

    @Override
    public MapJoiner2Impl withKeyFormatter(Formatter2 keyFormatter) {
        ObjectArgs.checkNotNull(keyFormatter, "keyFormatter");

        MapJoiner2Impl x =
            new MapJoiner2Impl(
                _quotingJoiner,
                _keyValueSeparator,
                keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public Formatter2 withKeyFormatter() {
        return _keyFormatter;
    }

    @Override
    public MapJoiner2Impl withValueFormatter(Formatter2 valueFormatter) {
        ObjectArgs.checkNotNull(valueFormatter, "valueFormatter");

        MapJoiner2Impl x =
            new MapJoiner2Impl(
                _quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public Formatter2 withValueFormatter() {
        return _valueFormatter;
    }

    @Override
    public MapJoiner2Impl useForNullKey(String keyNullText) {
        ObjectArgs.checkNotNull(keyNullText, "keyNullText");

        MapJoiner2Impl x =
            new MapJoiner2Impl(
                _quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public MapJoiner2Impl useForNullKey(char keyNullText) {
        String keyNullTextString = String.valueOf(keyNullText);

        MapJoiner2Impl x = useForNullKey(keyNullTextString);
        return x;
    }

    @Override
    public String useForNullKey() {
        return _keyNullText;
    }

    @Override
    public MapJoiner2Impl useForNullValue(String valueNullText) {
        ObjectArgs.checkNotNull(valueNullText, "valueNullText");

        MapJoiner2Impl x =
            new MapJoiner2Impl(
                _quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                valueNullText);
        return x;
    }

    @Override
    public MapJoiner2Impl useForNullValue(char valueNullText) {
        String valueNullTextString = String.valueOf(valueNullText);

        MapJoiner2Impl x = useForNullValue(valueNullTextString);
        return x;
    }

    @Override
    public String useForNullValue() {
        return _valueNullText;
    }

    @Override
    public Appendable appendTo(Appendable appendable, Map<?, ?> map)
    throws IOException {
        String x = _join(map);
        _appendTo(appendable, x);
        return appendable;
    }

    @Override
    public Appendable appendTo(
            Appendable appendable, Iterable<? extends Map.Entry<?, ?>> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        String x = _join(partIterable);
        _appendTo(appendable, x);
        return appendable;
    }

    @Override
    public Appendable appendTo(Appendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        ObjectArgs.checkNotNull(partIter, "partIter");

        String x = _join(partIter);
        _appendTo(appendable, x);
        return appendable;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
        String x = _join(map);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public StringBuilder appendTo(
            StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        String x = _join(partIterable);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public StringBuilder appendTo(
            StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> partIter) {
        ObjectArgs.checkNotNull(partIter, "partIter");

        String x = _join(partIter);
        _appendTo(builder, x);
        return builder;
    }

    @Override
    public String join(Map<?, ?> map) {
        String x = _join(map);
        return x;
    }

    @Override
    public String join(Iterable<? extends Map.Entry<?, ?>> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        String x = _join(partIterable);
        return x;
    }

    @Override
    public String join(Iterator<? extends Map.Entry<?, ?>> partIter) {
        ObjectArgs.checkNotNull(partIter, "partIter");

        String x = _join(partIter);
        return x;
    }

    @Override
    public MapJoiner2Impl withSeparator(String separator) {
        Joiner2Impl quotingJoiner = _quotingJoiner.withSeparator(separator);
        MapJoiner2Impl x =
            new MapJoiner2Impl(
                quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public MapJoiner2Impl withSeparator(char separator) {
        Joiner2Impl quotingJoiner = _quotingJoiner.withSeparator(separator);
        MapJoiner2Impl x =
            new MapJoiner2Impl(
                quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public String withSeparator() {
        return _quotingJoiner.withSeparator();
    }

    @Override
    public MapJoiner2Impl withElementFormatter(Formatter2 formatter) {
        Joiner2Impl quotingJoiner = _quotingJoiner.withElementFormatter(formatter);
        MapJoiner2Impl x =
            new MapJoiner2Impl(
                quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public Formatter2 withElementFormatter() {
        return _quotingJoiner.withElementFormatter();
    }

    @Override
    public MapJoiner2Impl withFinalFormatter(Formatter2 formatter) {
        Joiner2Impl quotingJoiner = _quotingJoiner.withFinalFormatter(formatter);
        MapJoiner2Impl x =
            new MapJoiner2Impl(
                quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public Formatter2 withFinalFormatter() {
        return _quotingJoiner.withFinalFormatter();
    }

    @Override
    public MapJoiner2Impl useForNoElements(String noElementsText) {
        Joiner2Impl quotingJoiner = _quotingJoiner.useForNoElements(noElementsText);
        MapJoiner2Impl x =
            new MapJoiner2Impl(
                quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public MapJoiner2Impl useForNoElements(char noElementsText) {
        Joiner2Impl quotingJoiner = _quotingJoiner.useForNoElements(noElementsText);
        MapJoiner2Impl x =
            new MapJoiner2Impl(
                quotingJoiner,
                _keyValueSeparator,
                _keyFormatter,
                _valueFormatter,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public String useForNoElements() {
        return _quotingJoiner.useForNoElements();
    }

    private String _join(Map<?, ?> map) {
        ObjectArgs.checkNotNull(map, "map");

        Iterable<? extends Map.Entry<?, ?>> partIterable = map.entrySet();
        String x = _join(partIterable);
        return x;
    }

    private String _join(Iterable<? extends Map.Entry<?, ?>> partIterable) {
        Iterator<? extends Map.Entry<?, ?>> partIter = partIterable.iterator();
        String x = _join(partIter);
        return x;
    }

    private String _join(Iterator<? extends Map.Entry<?, ?>> partIter) {
        String result = null;
        if (partIter.hasNext()) {
            StringBuilder sb = new StringBuilder();
            _appendNext(sb, "", partIter);
            final String separator = _quotingJoiner.withSeparator();
            while (partIter.hasNext()) {
                _appendNext(sb, separator, partIter);
            }
            result = sb.toString();
        }
        else {
            result = _quotingJoiner.useForNoElements();
        }
        result = _quotingJoiner.withFinalFormatter().format(result);
        return result;
    }

    private void _appendNext(
            StringBuilder stringBuilder,
            String separator,
            Iterator<? extends Map.Entry<?, ?>> partIter) {
        Map.Entry<?, ?> entry = partIter.next();
        if (null == entry) {
            throw new NullPointerException("Map entry is null");
        }
        Object key = entry.getKey();
        String quotedKeyString = _keyToString(key);
        Object value = entry.getValue();
        String quotedValueString = _valueToString(value);
        String keyValuePair = quotedKeyString + _keyValueSeparator + quotedValueString;
        String quotedKeyValuePair = _quotingJoiner.withElementFormatter().format(keyValuePair);
        stringBuilder.append(separator);
        stringBuilder.append(quotedKeyValuePair);
    }

    private String _keyToString(Object key) {
        if (null == key) {
            return _keyNullText;
        }
        String x = _keyFormatter.format(key);
        return x;
    }

    private String _valueToString(Object value) {
        if (null == value) {
            return _valueNullText;
        }
        String x = _valueFormatter.format(value);
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
}
