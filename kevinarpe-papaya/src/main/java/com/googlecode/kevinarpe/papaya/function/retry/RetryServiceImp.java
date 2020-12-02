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
import com.googlecode.kevinarpe.papaya.function.ThrowingRunnable;
import com.googlecode.kevinarpe.papaya.function.ThrowingSupplier;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
public final class RetryServiceImp
implements RetryService {

    public RetryServiceImp() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public void run(RetryStrategyFactory retryStrategyFactory,
                    ThrowingRunnable runnable)
    throws Exception {

        final RetryStrategy retryStrategy = retryStrategyFactory.newInstance();
        while (true) {

            try {
                runnable.run();
                return;
            }
            catch (Exception e) {
                if (false == retryStrategy.canRetry()) {
                    throw e;
                }
            }
            _beforeRetry(retryStrategy);
        }
    }

    private void
    _beforeRetry(RetryStrategy retryStrategy)
    throws Exception {
        try {
            retryStrategy.beforeRetry();
        }
        catch (Exception e) {
            // @DebugBreakpoint
            throw e;
        }
    }

    /** {@inheritDoc} */
    @Override
    public <TValue>
    TValue call(RetryStrategyFactory retryStrategyFactory,
                ThrowingSupplier<TValue> valueSupplier)
    throws Exception {

        final RetryStrategy retryStrategy = retryStrategyFactory.newInstance();
        while (true) {

            try {
                final TValue x = valueSupplier.get();
                return x;
            }
            catch (Exception e) {
                if (false == retryStrategy.canRetry()) {
                    throw e;
                }
            }
            _beforeRetry(retryStrategy);
        }
    }
}
