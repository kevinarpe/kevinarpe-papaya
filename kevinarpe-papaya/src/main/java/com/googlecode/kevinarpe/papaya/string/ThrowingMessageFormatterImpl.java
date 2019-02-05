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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * This version is intended for test code that should throw when failing to format messages.
 * <p>
 * Use {@link MessageFormatterImpl} for production code that should never throw when formatting messages.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see MessageFormatterImpl
 */
@FullyTested
public final class ThrowingMessageFormatterImpl
implements MessageFormatter {

    public static final ThrowingMessageFormatterImpl INSTANCE = new ThrowingMessageFormatterImpl();

    // Intentional: private -- everyone should use INSTANCE.
    private ThrowingMessageFormatterImpl() {
        // Empty
    }

    private static final ThreadLocal<boolean[]> threadLocalIsOkRef = ThreadLocal.withInitial(() -> new boolean[1]);

    /**
     * @throws IllegalArgumentException
     *         on format error
     *
     * {@inheritDoc}
     */
    @EmptyStringAllowed
    @Override
    public String
    format(@EmptyStringAllowed
           String format,
           @EmptyContainerAllowed
           @NullableElements
           Object... argArr) {

        ObjectArgs.checkNotNull(format, "format");
        ObjectArgs.checkNotNull(argArr, "argArr");

        final boolean[] isOkRef = threadLocalIsOkRef.get();
        @EmptyStringAllowed
        final String x = MessageFormatterImpl.format0(isOkRef, format, argArr);
        if (isOkRef[0]) {
            return x;
        }
        else {
            throw new IllegalArgumentException(x);
        }
    }
}
