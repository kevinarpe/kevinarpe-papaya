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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
final class QuotingMapJoinerImpl
implements QuotingMapJoiner {

    private final QuotingJoinerImpl _quotingJoiner;
    private final String _keyValueSeparator;
    private final String _keyLeftQuote;
    private final String _keyRightQuote;
    private final String _valueLeftQuote;
    private final String _valueRightQuote;
    private final String _keyNullText;
    private final String _valueNullText;

    // package-private for QuotingJoinerImpl to call
    QuotingMapJoinerImpl(QuotingJoinerImpl quotingJoiner, String keyValueSeparator) {
        this(
            ObjectArgs.checkNotNull(quotingJoiner, "quotingJoiner"),
            ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator"),
            QuotingJoiners.DEFAULT_KEY_LEFT_QUOTE,
            QuotingJoiners.DEFAULT_KEY_RIGHT_QUOTE,
            QuotingJoiners.DEFAULT_VALUE_LEFT_QUOTE,
            QuotingJoiners.DEFAULT_VALUE_RIGHT_QUOTE,
            QuotingJoiners.DEFAULT_KEY_NULL_TEXT,
            QuotingJoiners.DEFAULT_VALUE_NULL_TEXT);
    }

    private QuotingMapJoinerImpl(
        QuotingJoinerImpl quotingJoiner,
        String keyValueSeparator,
        String keyLeftQuote,
        String keyRightQuote,
        String valueLeftQuote,
        String valueRightQuote,
        String keyNullText,
        String valueNullText) {
        _quotingJoiner = quotingJoiner;
        _keyValueSeparator = keyValueSeparator;
        _keyLeftQuote = keyLeftQuote;
        _keyRightQuote = keyRightQuote;
        _valueLeftQuote = valueLeftQuote;
        _valueRightQuote = valueRightQuote;
        _keyNullText = keyNullText;
        _valueNullText = valueNullText;
    }

    @Override
    public QuotingMapJoinerImpl withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                _quotingJoiner,
                keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoinerImpl x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }

    @Override
    public String withKeyValueSeparator() {
        return _keyValueSeparator;
    }

    @Override
    public QuotingMapJoinerImpl withKeyQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                _quotingJoiner,
                _keyValueSeparator,
                leftQuote,
                rightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withKeyQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoinerImpl x = withKeyQuotes(leftQuote, rightQuoteString);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withKeyQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingMapJoinerImpl x = withKeyQuotes(leftQuoteString, rightQuote);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withKeyQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoinerImpl x = withKeyQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    @Override
    public String withKeyLeftQuote() {
        return _keyLeftQuote;
    }

    @Override
    public String withKeyRightQuote() {
        return _keyRightQuote;
    }

    @Override
    public QuotingMapJoinerImpl withValueQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                _quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                leftQuote,
                rightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withValueQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoinerImpl x = withValueQuotes(leftQuote, rightQuoteString);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withValueQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingMapJoinerImpl x = withValueQuotes(leftQuoteString, rightQuote);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withValueQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoinerImpl x = withValueQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    @Override
    public String withValueLeftQuote() {
        return _valueLeftQuote;
    }

    @Override
    public String withValueRightQuote() {
        return _valueRightQuote;
    }

    @Override
    public QuotingMapJoinerImpl useForNullKey(String keyNullText) {
        ObjectArgs.checkNotNull(keyNullText, "keyNullText");

        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                _quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl useForNullKey(char keyNullText) {
        String keyNullTextString = String.valueOf(keyNullText);

        QuotingMapJoinerImpl x = useForNullKey(keyNullTextString);
        return x;
    }

    @Override
    public String useForNullKey() {
        return _keyNullText;
    }

    @Override
    public QuotingMapJoinerImpl useForNullValue(String valueNullText) {
        ObjectArgs.checkNotNull(valueNullText, "valueNullText");

        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                _quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl useForNullValue(char valueNullText) {
        String valueNullTextString = String.valueOf(valueNullText);

        QuotingMapJoinerImpl x = useForNullValue(valueNullTextString);
        return x;
    }

    @Override
    public String useForNullValue() {
        return _valueNullText;
    }

    @Override
    public Appendable appendTo(Appendable appendable, Map<?, ?> map)
    throws IOException {
        ObjectArgs.checkNotNull(map, "map");

        Iterable<? extends Map.Entry<?, ?>> partIterable = map.entrySet();
        appendTo(appendable, partIterable);
        return appendable;
    }

    @Override
    public Appendable appendTo(
            Appendable appendable, Iterable<? extends Map.Entry<?, ?>> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<? extends Map.Entry<?, ?>> partIter = partIterable.iterator();
        appendTo(appendable, partIter);
        return appendable;
    }

    @Override
    public Appendable appendTo(Appendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        ObjectArgs.checkNotNull(appendable, "appendable");
        ObjectArgs.checkNotNull(partIter, "partIter");

        if (partIter.hasNext()) {
            _appendNext(appendable, partIter);
            while (partIter.hasNext()) {
                appendable.append(_quotingJoiner.withSeparator());
                _appendNext(appendable, partIter);
            }
        }
        else {
            appendable.append(_quotingJoiner.useForNoElements());
        }
        return appendable;
    }

    private void _appendNext(Appendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        Map.Entry<?, ?> entry = partIter.next();
        if (null == entry) {
            throw new NullPointerException("Map entry is null");
        }
        Object key = entry.getKey();
        String quotedKeyString = _keyToString(key);
        Object value = entry.getValue();
        String quotedValueString = _valueToString(value);
        String keyValuePair = quotedKeyString + _keyValueSeparator + quotedValueString;
        String quotedKeyValuePair =
            _quotingJoiner.withLeftQuote() + keyValuePair + _quotingJoiner.withRightQuote();
        appendable.append(quotedKeyValuePair);
    }

    private String _keyToString(Object key) {
        String x = (null == key) ? _keyNullText : key.toString();
        x = _keyLeftQuote + x + _keyRightQuote;
        return x;
    }

    private String _valueToString(Object value) {
        String x = (null == value) ? _valueNullText : value.toString();
        x = _valueLeftQuote + x + _valueRightQuote;
        return x;
    }

    @Override
    public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
        ObjectArgs.checkNotNull(map, "map");

        Iterable<? extends Map.Entry<?, ?>> partIterable = map.entrySet();
        appendTo(builder, partIterable);
        return builder;
    }

    @Override
    public StringBuilder appendTo(
            StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<? extends Map.Entry<?, ?>> partIter = partIterable.iterator();
        appendTo(builder, partIter);
        return builder;
    }

    @Override
    public StringBuilder appendTo(
        StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> partIter) {
            try {
            appendTo((Appendable) builder, partIter);
        }
        catch (IOException e) {
            throw new Error("Unexpected exception", e);
        }
        return builder;
    }

    @Override
    public String join(Map<?, ?> map) {
        ObjectArgs.checkNotNull(map, "map");

        Iterable<? extends Map.Entry<?, ?>> partIterable = map.entrySet();
        String x = join(partIterable);
        return x;
    }

    @Override
    public String join(Iterable<? extends Map.Entry<?, ?>> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<? extends Map.Entry<?, ?>> partIter = partIterable.iterator();
        String x = join(partIter);
        return x;
    }

    @Override
    public String join(Iterator<? extends Map.Entry<?, ?>> partIter) {
        StringBuilder sb = new StringBuilder();
        appendTo(sb, partIter);
        String sbs = sb.toString();
        return sbs;
    }

    @Override
    public QuotingMapJoinerImpl withSeparator(String separator) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.withSeparator(separator);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withSeparator(char separator) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.withSeparator(separator);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public String withSeparator() {
        return _quotingJoiner.withSeparator();
    }

    @Override
    public QuotingMapJoinerImpl withQuotes(String leftQuote, String rightQuote) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withQuotes(String leftQuote, char rightQuote) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withQuotes(char leftQuote, String rightQuote) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl withQuotes(char leftQuote, char rightQuote) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public String withLeftQuote() {
        return _quotingJoiner.withLeftQuote();
    }

    @Override
    public String withRightQuote() {
        return _quotingJoiner.withRightQuote();
    }

    @Override
    public QuotingMapJoinerImpl useForNoElements(String noElementsText) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.useForNoElements(noElementsText);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public QuotingMapJoinerImpl useForNoElements(char noElementsText) {
        QuotingJoinerImpl quotingJoiner = _quotingJoiner.useForNoElements(noElementsText);
        QuotingMapJoinerImpl x =
            new QuotingMapJoinerImpl(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote,
                _keyNullText,
                _valueNullText);
        return x;
    }

    @Override
    public String useForNoElements() {
        return _quotingJoiner.useForNoElements();
    }
}
