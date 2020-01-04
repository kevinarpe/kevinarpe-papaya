package com.googlecode.kevinarpe.papaya.string;

/*-
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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Nullable;

public class MessageFormattersTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MessageFormatterImpl.formatAndRejectNulls()
    //

    @DataProvider
    private static Object[][]
    _formatNonEmptyOrWhitespace_FailWhenArgIsNull_Data() {
        return new Object[][] {
            { (MessageFormatter) null, (String) null, (Object[]) null},
            { MessageFormatterImpl.INSTANCE, (String) null, (Object[]) null},
            { (MessageFormatter) null, "abc", (Object[]) null},
            { (MessageFormatter) null, (String) null, new Object[0]},
            { MessageFormatterImpl.INSTANCE, "abc", (Object[]) null},
            { (MessageFormatter) null, "abc", new Object[0]},
            { MessageFormatterImpl.INSTANCE, (String) null, new Object[0]},
        };
    }

    @Test(
        dataProvider = "_formatNonEmptyOrWhitespace_FailWhenArgIsNull_Data",
        expectedExceptions = NullPointerException.class)
    public void formatNonEmptyOrWhitespace_FailWhenArgIsNull(@Nullable MessageFormatter messageFormatter,
                                                             @Nullable String format,
                                                             @Nullable Object[] argArr) {

        MessageFormatters.formatNonEmptyOrWhitespace(messageFormatter, format, argArr);
    }

    @DataProvider
    private static Object[][]
    _formatNonEmptyOrWhitespace_FailWhenResultsIsEmptyOrWhitespace_Data() {
        return new Object[][] {
            { "", new Object[0]},
            { "  ", new Object[0]},
            { "\t", new Object[0]},
            { "\n", new Object[0]},
            { "%n", new Object[0]},
            { "%s", new Object[] {""}},
            { "%s", new Object[] {"  "}},
            { "%s", new Object[] {"\t"}},
            { "%s", new Object[] {"\n"}},
            { "%s%s", new Object[] {"", " "}},
            { "%s%s", new Object[] {"  ", "\t"}},
            { "%s%s", new Object[] {"\t", "\t"}},
            { "%s%s", new Object[] {"\n", ""}},
        };
    }

    @Test(
        dataProvider = "_formatNonEmptyOrWhitespace_FailWhenResultsIsEmptyOrWhitespace_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void formatNonEmptyOrWhitespace_FailWhenResultsIsEmptyOrWhitespace(String format,
                                                                              Object[] argArr) {

        MessageFormatters.formatNonEmptyOrWhitespace(MessageFormatterImpl.INSTANCE, format, argArr);
    }

    @Test
    public void formatNonEmptyOrWhitespace_Pass() {

        final String x =
            MessageFormatters.formatNonEmptyOrWhitespace(
                MessageFormatterImpl.INSTANCE, "abc[%d][%s]", 123, "def");
        Assert.assertEquals(x, "abc[123][def]");
    }
}
