package com.googlecode.kevinarpe.papaya.string;

/*-
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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ForwardingMessageFormatterTest {

    private static final class _ForwardingMessageFormatter
    extends ForwardingMessageFormatter {

        private final MessageFormatter messageFormatter;

        private _ForwardingMessageFormatter(MessageFormatter messageFormatter) {
            this.messageFormatter = ObjectArgs.checkNotNull(messageFormatter, "messageFormatter");
        }

        @Override
        protected MessageFormatter
        delegate() {
            return messageFormatter;
        }
    }

    private MessageFormatter mockMessageFormatter;
    private _ForwardingMessageFormatter classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {

        mockMessageFormatter = Mockito.mock(MessageFormatter.class);
        classUnderTest = new _ForwardingMessageFormatter(mockMessageFormatter);
    }

    @Test
    public void format_Pass() {

        Mockito.when(mockMessageFormatter.format("abc", 123, "def")).thenReturn("xyz");
        final String x = classUnderTest.format("abc", 123, "def");
        Assert.assertEquals(x, "xyz");
        Mockito.verify(mockMessageFormatter).format("abc", 123, "def");
    }
}
