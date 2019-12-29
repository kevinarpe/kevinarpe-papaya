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
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class Optionals {

    /**
     * Safely and conveniently transforms an optional value.  This method is useful when transforming an optional value
     * with a very long identifier (variable name).
     * <p>
     * Example: <pre>{@code
     * Optional<String> optionalValue = ...;
     * Optional<Integer> optionalLength = Optionals.apply(optionalValue, String::length);
     * }</pre>
     *
     * @param nullableOptionalValue
     *        input value to transform if not {@code null} and present
     *
     * @param transform
     *        function to convert {@code nullableOptionalValue} if not {@code null} and not {@link Optional#empty()}.
     *        <br>To be clear about limitations: This function may (a) return a non-{@code null} value,
     *        (b) return {@code null}, or (c) throw an unchecked exception, e.g., {@link RuntimeException}.
     *        <br>If a checked exception, e.g., {@link Exception}, can be thrown,
     *        instead use {@link #throwingApply(Optional, ThrowingFunction)}
     *
     * @return {@link Optional#empty()} if {@code nullableOptionalValue} is {@code null} or {@link Optional#empty()}
     *         <br>or result of {@code transform} wrapped with {@link Optional#ofNullable(Object)}
     *
     * @throws RuntimeException
     *         (or any unchecked exception) if thrown by {@code transform}
     *
     * @see #throwingApply(Optional, ThrowingFunction)
     */
    public static <TInput, TOutput>
    Optional<TOutput>
    apply(@Nullable
          Optional<TInput> nullableOptionalValue,
          Function<TInput, TOutput> transform) {

        if (null == nullableOptionalValue || false == nullableOptionalValue.isPresent()) {
            return Optional.empty();
        }
        else {
            final TInput value = nullableOptionalValue.get();
            @Nullable
            final TOutput output = transform.apply(value);
            return Optional.ofNullable(output);
        }
    }

    /**
     * Safely and conveniently transforms an optional value.  This method is useful when transforming an optional value
     * with a very long identifier (variable name).
     * <p>
     * Example: <pre>{@code
     * Optional<String> optionalValue = ...;
     * Optional<Integer> optionalLength = Optionals.apply(optionalValue, /* throwing lambda here * /);
     * }</pre>
     *
     * @param nullableOptionalValue
     *        input value to transform if not {@code null} and present
     *
     * @param transform
     *        function to convert {@code nullableOptionalValue} if not {@code null} and not {@link Optional#empty()}.
     *        <br>To be clear about limitations: This function may (a) return a non-{@code null} value,
     *        (b) return {@code null}, or (c) throw any exception, e.g., {@link Exception}.
     *
     * @return {@link Optional#empty()} if {@code nullableOptionalValue} is {@code null} or {@link Optional#empty()}
     *         <br>or result of {@code transform} wrapped with {@link Optional#ofNullable(Object)}
     *
     * @throws Exception
     *         (or any checked exception) if thrown by {@code transform}
     *
     * @see #apply(Optional, Function)
     */
    public static <TInput, TOutput>
    Optional<TOutput>
    throwingApply(@Nullable
                  Optional<TInput> nullableOptionalValue,
                  ThrowingFunction<TInput, TOutput> transform)
    throws Exception {

        if (null == nullableOptionalValue || false == nullableOptionalValue.isPresent()) {
            return Optional.empty();
        }
        else {
            final TInput value = nullableOptionalValue.get();
            @Nullable
            final TOutput output = transform.apply(value);
            return Optional.ofNullable(output);
        }
    }
}
