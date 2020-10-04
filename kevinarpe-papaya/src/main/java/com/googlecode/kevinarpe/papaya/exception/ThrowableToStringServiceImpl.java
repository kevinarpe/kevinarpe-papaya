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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalWithReset;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
@ThreadSafe
public final class ThrowableToStringServiceImpl
implements ThrowableToStringService {

    private final AtomicInteger atomicNextNumber;
    private final ConcurrentHashMap<String, Integer> map;

    public ThrowableToStringServiceImpl() {

        this.atomicNextNumber = new AtomicInteger(1);
        this.map = new ConcurrentHashMap<>();
    }

    private static final ThreadLocalWithReset<boolean[]> threadLocalBooleanRef =
        ThreadLocalWithReset.withInitialAndReset(
            () -> new boolean[1],
            (boolean[] z) -> z[0] = false);

    /** {@inheritDoc} */
    @Override
    public String
    toStringWithUniqueStackTrace(Throwable throwable) {

        ObjectArgs.checkNotNull(throwable, "throwable");

        final String str = ThrowableUtils.toStringWithStackTrace(throwable);
        final boolean[] isNewKeyRef = threadLocalBooleanRef.getAndReset();
        final int num =
            map.computeIfAbsent(str,
                (any) -> {
                    isNewKeyRef[0] = true;
                    final int x = atomicNextNumber.getAndIncrement();
                    return x;
                });
        if (isNewKeyRef[0]) {
            final String x = _createPrefix(num) + str;
            return x;
        }
        else {
            final StackTraceElement[] arr = throwable.getStackTrace();
            final String x = _createPrefix(num) + throwable + " at " + arr[0];
            return x;
        }
    }

    private String
    _createPrefix(final int num) {

        final String x = "[ID:" + num + "] ";
        return x;
    }
}
