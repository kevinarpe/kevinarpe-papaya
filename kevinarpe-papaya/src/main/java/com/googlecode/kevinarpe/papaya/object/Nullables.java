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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class Nullables {

    /**
     * Safely and conveniently transforms a nullable value.  This method is useful when transforming a nullable value
     * with a very long identifier (variable name).
     * <p>
     * Example: <pre>{@code
     * String nullableValue = ...;
     * Integer nullableLength = Nullables.apply(nullableValue, String::length);
     * }</pre>
     *
     * @param nullableValue
     *        input value to transform if not {@code null}
     *
     * @param transform
     *        function to convert {@code nullableValue} if not {@code null}.
     *        <br>To be clear about limitations: This function may (a) return a non-{@code null} value,
     *        (b) return {@code null}, or (c) throw an unchecked exception, e.g., {@link RuntimeException}.
     *        <br>If a checked exception, e.g., {@link Exception}, can be thrown,
     *        instead use {@link #throwingApply(Object, ThrowingFunction)}
     *
     * @return {@code null} if {@code nullableValue} is {@code null}
     *         <br>or result of {@code transform}
     *
     * @throws RuntimeException
     *         (or any unchecked exception) if thrown by {@code transform}
     *         
     * @see #throwingApply(Object, ThrowingFunction)
     */
    @Nullable
    public static <TInput, TOutput>
    TOutput
    apply(@Nullable
          TInput nullableValue,
          Function<TInput, TOutput> transform) {

        if (null == nullableValue) {
            return null;
        }
        else {
            @Nullable
            final TOutput x = transform.apply(nullableValue);
            return x;
        }
    }

    /**
     * Safely and conveniently transforms a nullable value.  This method is useful when transforming a nullable value
     * with a very long identifier (variable name).
     * <p>
     * Example: <pre>{@code
     * String nullableValue = ...;
     * Integer nullableLength = Nullables.throwingApply(nullableValue, /* throwing lambda here * /);
     * }</pre>
     *
     * @param nullableValue
     *        input value to transform if not {@code null}
     *
     * @param transform
     *        function to convert {@code nullableValue} if not {@code null}.
     *        <br>To be clear about limitations: This function may (a) return a non-{@code null} value,
     *        (b) return {@code null}, or (c) throw any exception, e.g., {@link Exception}.
     *
     * @return {@code null} if {@code nullableValue} is {@code null}
     *         <br>or result of {@code transform}
     *
     * @throws Exception
     *         (or any checked exception) if thrown by {@code transform}
     *
     * @see #apply(Object, Function)
     */
    @Nullable
    public static <TInput, TOutput>
    TOutput
    throwingApply(@Nullable
                  TInput nullableValue,
                  ThrowingFunction<TInput, TOutput> transform)
    throws Exception {

        if (null == nullableValue) {
            return null;
        }
        else {
            @Nullable
            final TOutput x = transform.apply(nullableValue);
            return x;
        }
    }
}
