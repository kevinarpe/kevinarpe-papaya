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
public final class QuotingMapJoiner
implements IQuotingMapJoiner<QuotingMapJoiner> {

    // TODO: What if 'null' Map.Entry found when appending/joining?  Throw intelligent NullPointerException

    public static final String DEFAULT_LEFT_KEY_QUOTE = "";
    public static final String DEFAULT_RIGHT_KEY_QUOTE = "";
    public static final String DEFAULT_LEFT_VALUE_QUOTE = "";
    public static final String DEFAULT_RIGHT_VALUE_QUOTE = "";
    public static final String DEFAULT_KEY_NULL_TEXT = "";
    public static final String DEFAULT_VALUE_NULL_TEXT = "";

    private final QuotingJoiner _quotingJoiner;
    private final String _keyValueSeparator;
    private final String _keyLeftQuote;
    private final String _keyRightQuote;
    private final String _valueLeftQuote;
    private final String _valueRightQuote;
    private final String _keyNullText;
    private final String _valueNullText;

    QuotingMapJoiner(QuotingJoiner quotingJoiner, String keyValueSeparator) {
        this(
            ObjectArgs.checkNotNull(quotingJoiner, "quotingJoiner"),
            ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator"),
            DEFAULT_LEFT_KEY_QUOTE,
            DEFAULT_RIGHT_KEY_QUOTE,
            DEFAULT_LEFT_VALUE_QUOTE,
            DEFAULT_RIGHT_VALUE_QUOTE,
            DEFAULT_KEY_NULL_TEXT,
            DEFAULT_VALUE_NULL_TEXT);
    }

    private QuotingMapJoiner(
            QuotingJoiner quotingJoiner,
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
    public QuotingMapJoiner withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoiner x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }

    @Override
    public String withKeyValueSeparator() {
        return _keyValueSeparator;
    }

    @Override
    public QuotingMapJoiner withKeyQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withKeyQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withKeyQuotes(leftQuote, rightQuoteString);
        return x;
    }

    @Override
    public QuotingMapJoiner withKeyQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingMapJoiner x = withKeyQuotes(leftQuoteString, rightQuote);
        return x;
    }

    @Override
    public QuotingMapJoiner withKeyQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withKeyQuotes(leftQuoteString, rightQuoteString);
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
    public QuotingMapJoiner withValueQuotes(String leftQuote, String rightQuote) {
        ObjectArgs.checkNotNull(leftQuote, "leftQuote");
        ObjectArgs.checkNotNull(rightQuote, "rightQuote");

        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withValueQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withValueQuotes(leftQuote, rightQuoteString);
        return x;
    }

    @Override
    public QuotingMapJoiner withValueQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingMapJoiner x = withValueQuotes(leftQuoteString, rightQuote);
        return x;
    }

    @Override
    public QuotingMapJoiner withValueQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withValueQuotes(leftQuoteString, rightQuoteString);
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
    public QuotingMapJoiner useForNullKey(String keyNullText) {
        ObjectArgs.checkNotNull(keyNullText, "keyNullText");

        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner useForNullKey(char keyNullText) {
        String keyNullTextString = String.valueOf(keyNullText);

        QuotingMapJoiner x = useForNullKey(keyNullTextString);
        return x;
    }

    @Override
    public String useForNullKey() {
        return _keyNullText;
    }

    @Override
    public QuotingMapJoiner useForNullValue(String valueNullText) {
        ObjectArgs.checkNotNull(valueNullText, "valueNullText");

        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner useForNullValue(char valueNullText) {
        String valueNullTextString = String.valueOf(valueNullText);

        QuotingMapJoiner x = useForNullValue(valueNullTextString);
        return x;
    }

    @Override
    public String useForNullValue() {
        return _valueNullText;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Map<?, ?> map)
    throws IOException {
        ObjectArgs.checkNotNull(map, "map");

        Iterable<? extends Map.Entry<?, ?>> partIterable = map.entrySet();
        appendTo(appendable, partIterable);
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Iterable<? extends Map.Entry<?, ?>> partIterable)
    throws IOException {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<? extends Map.Entry<?, ?>> partIter = partIterable.iterator();
        appendTo(appendable, partIter);
        return appendable;
    }

    public <TAppendable extends Appendable>
    TAppendable appendTo(TAppendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
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

    private <TAppendable extends Appendable>
    void _appendNext(TAppendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        Map.Entry<?, ?> entry = partIter.next();
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
        String x = (null == key) ? _checkKeyNullText() : key.toString();
        x = _keyLeftQuote + x + _keyRightQuote;
        return x;
    }

    private String _valueToString(Object value) {
        String x = (null == value) ? _checkValueNullText() : value.toString();
        x = _valueLeftQuote + x + _valueRightQuote;
        return x;
    }

    private String _checkKeyNullText() {
        _checkNullText("key", _keyNullText, "useForNullKey");
        return _keyNullText;
    }

    private String _checkValueNullText() {
        _checkNullText("value", _valueNullText, "useForNullValue");
        return _valueNullText;
    }

    private void _checkNullText(String target, String nullText, String methodName) {
        if (null == nullText) {
            throw new NullPointerException(String.format(
                "Failed to convert null %s to text.  See %s.%s(String)",
                target, QuotingMapJoiner.class.getSimpleName(), methodName));
        }
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
    public QuotingMapJoiner withSeparator(String separator) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withSeparator(separator);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withSeparator(char separator) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withSeparator(separator);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withQuotes(String leftQuote, String rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withQuotes(String leftQuote, char rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withQuotes(char leftQuote, String rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner withQuotes(char leftQuote, char rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner useForNoElements(String emptyText) {
        QuotingJoiner quotingJoiner = _quotingJoiner.useForNoElements(emptyText);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
    public QuotingMapJoiner useForNoElements(char emptyText) {
        QuotingJoiner quotingJoiner = _quotingJoiner.useForNoElements(emptyText);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
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
