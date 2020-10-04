package com.googlecode.kevinarpe.papaya.concurrent;

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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.function.ThrowingConsumer;
import com.googlecode.kevinarpe.papaya.function.ThrowingSupplier;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ThreadLocalWithReset
 */
@FullyTested
public final class ThrowingThreadLocalWithReset<TValue> {

    /**
     * If you do <b>not</b> need to throw from {@code supplier} or {@code reset}, see {@link ThreadLocalWithReset}.
     */
    public static <TValue2>
    ThrowingThreadLocalWithReset<TValue2>
    withInitialAndReset(ThrowingSupplier<? extends TValue2> supplier,
                        ThrowingConsumer<? super TValue2> reset) {

        final ThrowingThreadLocalWithReset<TValue2> x = new ThrowingThreadLocalWithReset<>(supplier, reset);
        return x;
    }

    private final ThreadLocal<TValue> threadLocal;
    private final ThrowingConsumer<? super TValue> reset;

    private ThrowingThreadLocalWithReset(ThrowingSupplier<? extends TValue> supplier,
                                         ThrowingConsumer<? super TValue> reset) {

        ObjectArgs.checkNotNull(supplier, "supplier");
        this.threadLocal = ThreadLocal.withInitial(supplier.asSupplier());
        this.reset = ObjectArgs.checkNotNull(reset, "reset");
    }

    public TValue
    getAndReset()
    throws Exception {
        final TValue value = threadLocal.get();
        reset.accept(value);
        return value;
    }
}
