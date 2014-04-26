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

import com.google.common.base.Joiner;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.StringFormatter;

/**
 * Constants and static utilities for {@link Joiner2} and {@link MapJoiner2}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #withSeparator(String)
 * @see #withSeparator(char)
 * @see #newQuotingJoinerFactory(String)
 * @see #newQuotingJoinerFactory(char)
 * @see StatelessObject
 * @see IJoiner2Utils
 */
@FullyTested
public final class Joiner2Utils
extends StatelessObject
implements IJoiner2Utils {

    private static final Formatter2 _DEFAULT_FORMATTER = new StringFormatter("%s");

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final Joiner2Utils INSTANCE = new Joiner2Utils();

    /**
     * For projects that require total, static-free mocking capabilities, use this constructor.
     * Else, the static constant {@link #INSTANCE} will suffice.
     */
    public Joiner2Utils() {
        // Empty.
    }

    /**
     * Default value for {@link SharedJoiner2Settings#withFormatter()}: (no prefix or suffix)
     *
     * @see SharedJoiner2Settings#withFormatter(Formatter2)
     */
    public static final Formatter2 DEFAULT_FORMATTER = _DEFAULT_FORMATTER;

    /**
     * Default value for {@link SharedJoiner2Settings#useForNoElements()}: {@code ""}
     * (empty string).
     *
     * @see SharedJoiner2Settings#useForNoElements(String)
     */
    public static final String DEFAULT_NO_ELEMENTS_TEXT = "";

    /**
     * Default value for {@link Joiner2Settings#useForNull()}: {@code "null"}.
     * <p>
     * This default differs from class {@link Joiner}, which uses {@code null}, and throws a
     * {@link NullPointerException} when joining a {@code null} value, unless
     * {@link Joiner#useForNull(String)} is called before joining.
     *
     * @see Joiner2Settings#useForNull(String)
     */
    public static final String DEFAULT_NULL_TEXT = "null";

    /**
     * Default value for {@link Joiner2Settings#skipNulls()}: {@code false}.
     *
     * @see Joiner2Settings#skipNulls(boolean)
     */
    public static final boolean DEFAULT_SKIP_NULLS_FLAG = false;

    /**
     * Default value for {@link MapJoiner2Settings#withKeyFormatter()}: (no prefix or suffix)
     *
     * @see MapJoiner2Settings#withKeyFormatter(Formatter2)
     */
    public static final Formatter2 DEFAULT_KEY_FORMATTER = _DEFAULT_FORMATTER;

    /**
     * Default value for {@link MapJoiner2Settings#withValueFormatter()}: (no prefix or suffix)
     *
     * @see MapJoiner2Settings#withValueFormatter(Formatter2)
     */
    public static final Formatter2 DEFAULT_VALUE_FORMATTER = _DEFAULT_FORMATTER;

    /**
     * Default value for {@link MapJoiner2Settings#useForNullKey()}: {@code "null"}.
     *
     * @see MapJoiner2Settings#useForNullKey(String)
     */
    public static final String DEFAULT_KEY_NULL_TEXT = "null";

    /**
     * Default value for {@link MapJoiner2Settings#useForNullValue()}: {@code "null"}.
     *
     * @see MapJoiner2Settings#useForNullValue(String)
     */
    public static final String DEFAULT_VALUE_NULL_TEXT = "null";

    /**
     * {@inheritDoc}
     */
    @Override
    public Joiner2 withSeparator(String separator) {
        Joiner2FactoryImpl factory = new Joiner2FactoryImpl(separator);
        Joiner2 x = factory.newInstance();
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Joiner2 withSeparator(char separator) {
        String separatorString = String.valueOf(separator);
        Joiner2 x = withSeparator(separatorString);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Joiner2Factory newQuotingJoinerFactory(String separator) {
        Joiner2FactoryImpl x = new Joiner2FactoryImpl(separator);
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Joiner2Factory newQuotingJoinerFactory(char separator) {
        String separatorString = String.valueOf(separator);
        Joiner2Factory x = newQuotingJoinerFactory(separatorString);
        return x;
    }
}
