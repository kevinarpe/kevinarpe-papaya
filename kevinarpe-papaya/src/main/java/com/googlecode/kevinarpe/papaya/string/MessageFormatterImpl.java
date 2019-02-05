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

import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import com.googlecode.kevinarpe.papaya.annotation.OutputParam;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalWithReset;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalsWithReset;

import javax.annotation.Nullable;

/**
 * This version is intended for production code that should never throw when formatting messages.
 * <p>
 * Use {@link ThrowingMessageFormatterImpl} for tests that should throw when failing to format messages.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see ThrowingMessageFormatterImpl
 */
@FullyTested
public final class MessageFormatterImpl
implements MessageFormatter {

    public static final MessageFormatterImpl INSTANCE = new MessageFormatterImpl();

    // Intentional: private -- everyone should use INSTANCE.
    private MessageFormatterImpl() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    @EmptyStringAllowed
    public String
    format(@EmptyStringAllowed
           String format,
           @EmptyContainerAllowed
           @NullableElements
           Object... argArr) {

        ObjectArgs.checkNotNull(format, "format");
        ObjectArgs.checkNotNull(argArr, "argArr");

        final boolean[] nullableIsOkRef = null;
        @EmptyStringAllowed
        final String x = format0(nullableIsOkRef, format, argArr);
        return x;
    }

    private static final ThreadLocalWithReset<StringBuilder> threadLocalStringBuilder =
        ThreadLocalsWithReset.newInstanceForStringBuilder();

    // Intentional: package-private
    @EmptyStringAllowed
    static String
    format0(@Nullable
            @OutputParam
            boolean[] nullableIsOkRef,
            @EmptyStringAllowed
            String format,
            @EmptyContainerAllowed
            @NullableElements
            Object... argArr) {

        if (null != nullableIsOkRef) {
            ArrayArgs.checkExactLength(nullableIsOkRef, 1, "nullableIsOkRef");
        }
        try {
            @EmptyStringAllowed
            final String x = String.format(format, argArr);
            if (null != nullableIsOkRef) {
                nullableIsOkRef[0] = true;
            }
            return x;
        }
        catch (RuntimeException e) {
            final StringBuilder sb = threadLocalStringBuilder.getAndReset();
            sb.append("Failed to format message: [").append(format).append("]");

            if (null == argArr)  {
                sb.append(" (Internal error: Argument 'argArr' is null)");
            }
            else if (0 == argArr.length) {
                sb.append(" with zero args");
            }
            else {
                for (int index = 0; index < argArr.length; ++index) {
                    sb.append(StringUtils.NEW_LINE).append("\tArg[").append(index).append("]: ");
                    @Nullable
                    final Object arg = argArr[index];
                    if (null == arg) {
                        sb.append(arg);
                    }
                    else {
                        sb.append('(').append(arg.getClass().getSimpleName()).append(") [").append(arg).append(']');
                    }
                }
            }
            final String msg = sb.toString();
            if (null != nullableIsOkRef) {
                nullableIsOkRef[0] = false;
            }
            return msg;
        }
    }
}
