package com.googlecode.kevinarpe.papaya.function.retry;

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

import com.googlecode.kevinarpe.papaya.annotation.Blocking;
import com.googlecode.kevinarpe.papaya.annotation.Debug;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.LongArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.Duration;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Multiple global singletons
@FullyTested
@NotThreadSafe
public final class BasicRetryStrategyImp
implements RetryStrategy {

    public static BasicRetryStrategyFactoryBuilder
    factoryBuilder() {

        final BasicRetryStrategyFactoryBuilder x = new BasicRetryStrategyFactoryBuilder();
        return x;
    }

    // package-private
    static int
    checkMaxRetryCount(final int maxRetryCount) {

        IntArgs.checkMinValue(maxRetryCount, 0, "maxRetryCount");
        return maxRetryCount;
    }

    // package-private
    static Duration
    checkBeforeRetrySleepDuration(Duration beforeRetrySleepDuration) {

        ObjectArgs.checkNotNull(beforeRetrySleepDuration, "beforeRetrySleepDuration");

        LongArgs.checkMinValue(
            beforeRetrySleepDuration.toMillis(), 1, "beforeRetrySleepDuration.toMillis()");

        return beforeRetrySleepDuration;
    }

    private final int maxRetryCount;
    @Debug
    private final Duration beforeRetrySleepDuration;
    private final long beforeRetrySleepMillis;

    private int retryCount;

    // package-private
    BasicRetryStrategyImp(final int maxRetryCount,
                          Duration beforeRetrySleepDuration) {

        this.maxRetryCount = IntArgs.checkMinValue(maxRetryCount, 0, "maxRetryCount");

        this.beforeRetrySleepDuration =
            ObjectArgs.checkNotNull(beforeRetrySleepDuration, "beforeRetrySleepDuration");

        this.beforeRetrySleepMillis =
            LongArgs.checkMinValue(
                beforeRetrySleepDuration.toMillis(), 1, "beforeRetrySleepDuration.toMillis()");
    }

    /** {@inheritDoc} */
    @NonBlocking
    @Override
    public boolean
    canRetry() {

        final boolean x = (retryCount < maxRetryCount);
        return x;
    }

    /** {@inheritDoc} */
    @Blocking
    @Override
    public void beforeRetry()
    throws Exception {

        ++retryCount;
        try {
            Thread.sleep(beforeRetrySleepMillis);
        }
        catch (Exception e) {
            // @DebugBreakpoint
            throw e;
        }
    }
}
