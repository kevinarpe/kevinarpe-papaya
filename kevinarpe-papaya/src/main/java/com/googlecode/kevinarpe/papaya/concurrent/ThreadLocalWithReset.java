package com.googlecode.kevinarpe.papaya.concurrent;

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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ThreadLocalWithReset<TValue> {

    public static <TValue2>
    ThreadLocalWithReset<TValue2>
    withInitialAndReset(Supplier<? extends TValue2> supplier,
                        Consumer<? super TValue2> reset) {

        final ThreadLocalWithReset<TValue2> x = new ThreadLocalWithReset<>(supplier, reset);
        return x;
    }

    private final ThreadLocal<TValue> threadLocal;
    private final Consumer<? super TValue> reset;

    private ThreadLocalWithReset(Supplier<? extends TValue> supplier,
                                 Consumer<? super TValue> reset) {

        this.threadLocal = ThreadLocal.withInitial(supplier);
        this.reset = ObjectArgs.checkNotNull(reset, "reset");
    }

    public TValue
    getAndReset() {
        final TValue value = threadLocal.get();
        reset.accept(value);
        return value;
    }
}
