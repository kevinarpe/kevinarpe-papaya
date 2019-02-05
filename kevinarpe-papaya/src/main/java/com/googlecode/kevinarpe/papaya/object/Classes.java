package com.googlecode.kevinarpe.papaya.object;

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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.string.MessageFormatterImpl;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class Classes {

    private Classes() {
        // Empty
    }

    /**
     * Convenience method to wrap exceptions from {@link Class#getConstructor(Class[])} with better exception messages.
     *
     * @throws IllegalArgumentException
     *         wraps any exception thrown by {@link Class#getConstructor(Class[])}
     */
    public static <TValue>
    Constructor<TValue>
    getConstructorOrThrow(Class<TValue> klass,
                          @EmptyContainerAllowed Class<?>... paramTypeArr) {

        ObjectArgs.checkNotNull(klass, "klass");
        ArrayArgs.checkElementsNotNull(paramTypeArr, "paramTypeArr");
        try {
            final Constructor<TValue> x = klass.getConstructor(paramTypeArr);
            return x;
        }
        catch (Exception e) {
            final StringBuilder sb = new StringBuilder();
            sb.append(MessageFormatterImpl.INSTANCE.format("Failed to find constructor: %s(", klass.getSimpleName()));
            _mapThenAppendArray(sb, paramTypeArr, ", ", Class::getSimpleName);
            sb.append(')');
            final String m = sb.toString();
            throw new IllegalArgumentException(m, e);
        }
    }

    /**
     * Convenience method to wrap exceptions from {@link Constructor#newInstance(Object...)} with better exception messages.
     *
     * @throws IllegalArgumentException
     *         wraps any exception thrown by {@link Constructor#newInstance(Object...)}
     */
    public static <TValue>
    TValue
    newInstanceOrThrow(Constructor<TValue> ctor,
                       @NullableElements Object... argArr) {

        ObjectArgs.checkNotNull(ctor, "ctor");
        ObjectArgs.checkNotNull(argArr, "argArr");
        try {
            final TValue x = ctor.newInstance(argArr);
            return x;
        }
        catch (Exception e) {
            final StringBuilder sb = new StringBuilder();
            sb.append(MessageFormatterImpl.INSTANCE.format("Failed to call constructor %s with args:", ctor));
            _mapThenAppendArray(sb, argArr, "",
                (@Nullable Object arg) -> {
                    if (null == arg) {
                        return MessageFormatterImpl.INSTANCE.format("%n\tnull");
                    }
                    else {
                        final String x =
                            MessageFormatterImpl.INSTANCE.format("%n\t%s: [%s]", arg.getClass().getSimpleName(), arg);
                        return x;
                    }
                });
            final String m = sb.toString();
            throw new IllegalArgumentException(m, e);
        }
    }

    @FunctionalInterface
    private interface _Mapper<TInput, TOutput> {

        @Nullable
        TOutput
        map(@Nullable TInput input);
    }

    private static <TValue>
    StringBuilder
    _mapThenAppendArray(StringBuilder sb,
                        @Nullable TValue[] arr,
                        @Nullable String delim,
                       _Mapper<TValue, Object> mapper) {

        ObjectArgs.checkNotNull(sb, "sb");
        ObjectArgs.checkNotNull(mapper, "mapper");
        if (null == arr) {
            sb.append((Object) null);
            return sb;
        }
        @Nullable
        String d = "";
        for (@Nullable final TValue v : arr) {
            @Nullable final Object v2 = mapper.map(v);
            sb.append(d).append(v2);
            d = delim;
        }
        return sb;
    }
}
