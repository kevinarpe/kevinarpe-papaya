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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import javax.annotation.Nullable;
import java.time.Duration;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Multiple global singletons
@FullyTested  // See: BasicRetryStrategyImpTest
public final class BasicRetryStrategyFactoryBuilder {

    private int maxRetryCount;
    @Nullable
    private Duration nullableBeforeRetrySleepDuration;

    public BasicRetryStrategyFactoryBuilder() {

        this.maxRetryCount = -1;
        this.nullableBeforeRetrySleepDuration = null;
    }

    /**
     * @param maxRetryCount
     *        zero is the minimum allowed (do not allow any retry)
     *
     * @return self for fluent interface / method chaining
     *
     * @see #beforeRetrySleepDuration(Duration)
     * @see #build()
     */
    public BasicRetryStrategyFactoryBuilder
    maxRetryCount(final int maxRetryCount) {

        this.maxRetryCount = BasicRetryStrategyImp.checkMaxRetryCount(maxRetryCount);
        return this;
    }

    /**
     * @param beforeRetrySleepDuration
     *        one millisecond is the minimum allowed
     *
     * @return self for fluent interface / method chaining
     *
     * @see #maxRetryCount(int)
     * @see #build()
     */
    public BasicRetryStrategyFactoryBuilder
    beforeRetrySleepDuration(Duration beforeRetrySleepDuration) {

        this.nullableBeforeRetrySleepDuration =
            BasicRetryStrategyImp.checkBeforeRetrySleepDuration(beforeRetrySleepDuration);
        return this;
    }

    /**
     * @return new factory to generate instances of {@link RetryStrategy}
     *
     * @see #maxRetryCount(int)
     * @see #beforeRetrySleepDuration(Duration)
     */
    public RetryStrategyFactory
    build() {

        final RetryStrategyFactory x =
            () -> new BasicRetryStrategyImp(maxRetryCount, nullableBeforeRetrySleepDuration);
        return x;
    }
}
