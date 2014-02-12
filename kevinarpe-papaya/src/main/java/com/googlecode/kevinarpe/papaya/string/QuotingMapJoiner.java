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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public final class QuotingMapJoiner {

    public static final String DEFAULT_LEFT_KEY_QUOTE = "";
    public static final String DEFAULT_RIGHT_KEY_QUOTE = "";
    public static final String DEFAULT_LEFT_VALUE_QUOTE = "";
    public static final String DEFAULT_RIGHT_VALUE_QUOTE = "";

    private final QuotingJoiner _quotingJoiner;
    private final String _keyValueSeparator;
    private final String _keyLeftQuote;
    private final String _keyRightQuote;
    private final String _valueLeftQuote;
    private final String _valueRightQuote;

    QuotingMapJoiner(QuotingJoiner quotingJoiner, String keyValueSeparator) {
        this(
            ObjectArgs.checkNotNull(quotingJoiner, "quotingJoiner"),
            ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator"),
            DEFAULT_LEFT_KEY_QUOTE,
            DEFAULT_RIGHT_KEY_QUOTE,
            DEFAULT_LEFT_VALUE_QUOTE,
            DEFAULT_RIGHT_VALUE_QUOTE);
    }

    private QuotingMapJoiner(
            QuotingJoiner quotingJoiner,
            String keyValueSeparator,
            String keyLeftQuote,
            String keyRightQuote,
            String valueLeftQuote,
            String valueRightQuote) {
        _quotingJoiner = quotingJoiner;
        _keyValueSeparator = keyValueSeparator;
        _keyLeftQuote = keyLeftQuote;
        _keyRightQuote = keyRightQuote;
        _valueLeftQuote = valueLeftQuote;
        _valueRightQuote = valueRightQuote;
    }

    public QuotingMapJoiner withKeyValueSeparator(String keyValueSeparator) {
        ObjectArgs.checkNotNull(keyValueSeparator, "keyValueSeparator");

        QuotingMapJoiner x =
            new QuotingMapJoiner(
                _quotingJoiner,
                keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner withKeyValueSeparator(char keyValueSeparator) {
        String keyValueSeparatorString = String.valueOf(keyValueSeparator);
        QuotingMapJoiner x = withKeyValueSeparator(keyValueSeparatorString);
        return x;
    }

    public String withKeyValueSeparator() {
        return _keyValueSeparator;
    }

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
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner withKeyQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withKeyQuotes(leftQuote, rightQuoteString);
        return x;
    }

    public QuotingMapJoiner withKeyQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingMapJoiner x = withKeyQuotes(leftQuoteString, rightQuote);
        return x;
    }

    public QuotingMapJoiner withKeyQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withKeyQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    public String withKeyLeftQuote() {
        return _keyLeftQuote;
    }

    public String withKeyRightQuote() {
        return _keyRightQuote;
    }

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
                rightQuote);
        return x;
    }

    public QuotingMapJoiner withValueQuotes(String leftQuote, char rightQuote) {
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withValueQuotes(leftQuote, rightQuoteString);
        return x;
    }

    public QuotingMapJoiner withValueQuotes(char leftQuote, String rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        QuotingMapJoiner x = withValueQuotes(leftQuoteString, rightQuote);
        return x;
    }

    public QuotingMapJoiner withValueQuotes(char leftQuote, char rightQuote) {
        String leftQuoteString = String.valueOf(leftQuote);
        String rightQuoteString = String.valueOf(rightQuote);
        QuotingMapJoiner x = withValueQuotes(leftQuoteString, rightQuoteString);
        return x;
    }

    public String withValueLeftQuote() {
        return _valueLeftQuote;
    }

    public String withValueRightQuote() {
        return _valueRightQuote;
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
    TAppendable appendTo(
            TAppendable appendable,
            Iterable<? extends Map.Entry<?, ?>> partIterable)
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
            appendable.append(_quotingJoiner.useForEmpty());
        }
        return appendable;
    }

    private <TAppendable extends Appendable>
    void _appendNext(TAppendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        Map.Entry<?, ?> entry = partIter.next();
        Object key = entry.getKey();
        String quotedKeyString =
            QuotingJoiner._toString(key, _quotingJoiner, _keyLeftQuote, _keyRightQuote);
        Object value = entry.getValue();
        String quotedValueString =
            QuotingJoiner._toString(value, _quotingJoiner, _valueLeftQuote, _valueRightQuote);
        String keyValuePair = quotedKeyString + _keyValueSeparator + quotedValueString;
        String quotedKeyValuePair =
            QuotingJoiner._toString(
                keyValuePair,
                _quotingJoiner,
                _quotingJoiner.withLeftQuote(),
                _quotingJoiner.withRightQuote());
        appendable.append(quotedKeyValuePair);
    }

    public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
        ObjectArgs.checkNotNull(map, "map");

        Iterable<? extends Map.Entry<?, ?>> partIterable = map.entrySet();
        appendTo(builder, partIterable);
        return builder;
    }

    public StringBuilder appendTo(
            StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> partIterable) {
        ObjectArgs.checkNotNull(partIterable, "partIterable");

        Iterator<? extends Map.Entry<?, ?>> partIter = partIterable.iterator();
        appendTo(builder, partIter);
        return builder;
    }

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

    public QuotingMapJoiner withSeparator(String separator) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withSeparator(separator);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner withSeparator(char separator) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withSeparator(separator);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public String withSeparator() {
        return _quotingJoiner.withSeparator();
    }

    public QuotingMapJoiner withQuotes(String leftQuote, String rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner withQuotes(String leftQuote, char rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner withQuotes(char leftQuote, String rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner withQuotes(char leftQuote, char rightQuote) {
        QuotingJoiner quotingJoiner = _quotingJoiner.withQuotes(leftQuote, rightQuote);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public String withLeftQuote() {
        return _quotingJoiner.withLeftQuote();
    }

    public String withRightQuote() {
        return _quotingJoiner.withRightQuote();
    }

    public QuotingMapJoiner useForEmpty(String emptyText) {
        QuotingJoiner quotingJoiner = _quotingJoiner.useForEmpty(emptyText);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner useForEmpty(char emptyText) {
        QuotingJoiner quotingJoiner = _quotingJoiner.useForEmpty(emptyText);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public String useForEmpty() {
        return _quotingJoiner.useForEmpty();
    }

    public QuotingMapJoiner useForNull(String nullText) {
        QuotingJoiner quotingJoiner = _quotingJoiner.useForNull(nullText);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public QuotingMapJoiner useForNull(char nullText) {
        QuotingJoiner quotingJoiner = _quotingJoiner.useForNull(nullText);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public String useForNull() {
        return _quotingJoiner.useForNull();
    }

    public QuotingMapJoiner skipNulls(boolean flag) {
        QuotingJoiner quotingJoiner = _quotingJoiner.skipNulls(flag);
        QuotingMapJoiner x =
            new QuotingMapJoiner(
                quotingJoiner,
                _keyValueSeparator,
                _keyLeftQuote,
                _keyRightQuote,
                _valueLeftQuote,
                _valueRightQuote);
        return x;
    }

    public boolean skipNulls() {
        return _quotingJoiner.skipNulls();
    }
}
