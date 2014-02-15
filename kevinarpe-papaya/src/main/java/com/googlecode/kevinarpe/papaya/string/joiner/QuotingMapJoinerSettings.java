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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface QuotingMapJoinerSettings<TSelf extends QuotingMapJoinerSettings<TSelf>>
extends SharedQuotingJoinerSettings<TSelf, TSelf> {

    String withKeyValueSeparator();

    TSelf withKeyQuotes(String leftQuote, String rightQuote);

    TSelf withKeyQuotes(String leftQuote, char rightQuote);

    TSelf withKeyQuotes(char leftQuote, String rightQuote);

    TSelf withKeyQuotes(char leftQuote, char rightQuote);

    String withKeyLeftQuote();

    String withKeyRightQuote();

    TSelf withValueQuotes(String leftQuote, String rightQuote);

    TSelf withValueQuotes(String leftQuote, char rightQuote);

    TSelf withValueQuotes(char leftQuote, String rightQuote);

    TSelf withValueQuotes(char leftQuote, char rightQuote);

    String withValueLeftQuote();

    String withValueRightQuote();

    TSelf useForNullKey(String keyNullText);

    TSelf useForNullKey(char keyNullText);

    String useForNullKey();

    TSelf useForNullValue(String valueNullText);

    TSelf useForNullValue(char valueNullText);

    String useForNullValue();
}