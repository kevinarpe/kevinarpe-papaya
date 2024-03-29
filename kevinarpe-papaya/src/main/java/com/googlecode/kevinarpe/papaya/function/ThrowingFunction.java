package com.googlecode.kevinarpe.papaya.function;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Function
 */
@FullyTested
@FunctionalInterface
public interface ThrowingFunction<TInput, TOutput> {

    TOutput apply(TInput input)
    throws Exception;

    /**
     * Creates a {@link Consumer} that wraps this instance and re-throws any exceptions using {@link RuntimeException}.
     */
    default Function<TInput, TOutput>
    asFunction() {

        final Function<TInput, TOutput> x =
            (TInput input) -> {
                try {
                    final TOutput z = apply(input);
                    return z;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
        return x;
    }
}
