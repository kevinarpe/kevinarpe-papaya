package com.googlecode.kevinarpe.papaya.exception;

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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.Classes;
import com.googlecode.kevinarpe.papaya.string.MessageFormatter;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ExceptionThrowerImpl
implements ExceptionThrower {

    private final MessageFormatter messageFormatter;

    public ExceptionThrowerImpl(MessageFormatter messageFormatter) {

        this.messageFormatter = ObjectArgs.checkNotNull(messageFormatter, "messageFormatter");
    }

    @Override
    public <TException extends Exception>
    TException
    throwCheckedException(Class<TException> exceptionClass,
                          @EmptyStringAllowed
                          String format,
                          @EmptyContainerAllowed
                          @NullableElements
                          Object... argArr)
    throws TException {
        @EmptyStringAllowed
        final String message = messageFormatter.format(format, argArr);
        final Constructor<TException> ctor = Classes.getConstructorOrThrow(exceptionClass, String.class);
        final TException x = Classes.newInstanceOrThrow(ctor, message);
        throw x;
    }

    @Override
    public <TException extends Exception>
    TException
    throwChainedCheckedException(Class<TException> exceptionClass,
                                 @Nullable
                                 Throwable cause,
                                 @EmptyStringAllowed
                                 String format,
                                 @EmptyContainerAllowed
                                 @NullableElements
                                 Object... argArr)
    throws TException {
        @EmptyStringAllowed
        final String message = messageFormatter.format(format, argArr);
        final Constructor<TException> ctor = Classes.getConstructorOrThrow(exceptionClass, String.class, Throwable.class);
        final TException x = Classes.newInstanceOrThrow(ctor, message, cause);
        throw x;
    }

    @Override
    public <TRuntimeException extends RuntimeException>
    TRuntimeException
    throwRuntimeException(Class<TRuntimeException> exceptionClass,
                          @EmptyStringAllowed
                          String format,
                          @EmptyContainerAllowed
                          @NullableElements
                          Object... argArr)
    throws TRuntimeException {
        @EmptyStringAllowed
        final String message = messageFormatter.format(format, argArr);
        final Constructor<TRuntimeException> ctor = Classes.getConstructorOrThrow(exceptionClass, String.class);
        final TRuntimeException x = Classes.newInstanceOrThrow(ctor, message);
        throw x;
    }

    @Override
    public <TRuntimeException extends RuntimeException>
    TRuntimeException
    throwChainedRuntimeException(Class<TRuntimeException> exceptionClass,
                                 @Nullable
                                 Throwable cause,
                                 @EmptyStringAllowed
                                 String format,
                                 @EmptyContainerAllowed
                                 @NullableElements
                                 Object... argArr)
    throws TRuntimeException {
        @EmptyStringAllowed
        final String message = messageFormatter.format(format, argArr);
        final Constructor<TRuntimeException> ctor =
            Classes.getConstructorOrThrow(exceptionClass, String.class, Throwable.class);
        final TRuntimeException x = Classes.newInstanceOrThrow(ctor, message, cause);
        throw x;
    }
}
