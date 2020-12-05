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
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalWithReset;
import com.googlecode.kevinarpe.papaya.concurrent.ThreadLocalsWithReset;
import com.googlecode.kevinarpe.papaya.function.ThrowingRunnable;
import com.googlecode.kevinarpe.papaya.function.ThrowingSupplier;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Scope: Global singleton
@FullyTested
@ThreadSafe
public final class RetryServiceImp
implements RetryService {

    private final CollectionIndexMatcher exceptionListIndexMatcher;

    public RetryServiceImp(CollectionIndexMatcher exceptionListIndexMatcher) {

        this.exceptionListIndexMatcher =
            ObjectArgs.checkNotNull(exceptionListIndexMatcher, "exceptionListIndexMatcher");
    }

    // This ThreadLocal is absolutely necessary to keep this impl thread-safe!
    private static final ThreadLocalWithReset<ArrayList<Exception>> threadLocalExceptionList =
        ThreadLocalsWithReset.newInstanceForArrayList();

    /** {@inheritDoc} */
    @Override
    public void run(RetryStrategyFactory retryStrategyFactory,
                    ThrowingRunnable runnable)
    throws Exception {

        final ArrayList<Exception> exceptionList = threadLocalExceptionList.getAndReset();
        final RetryStrategy retryStrategy = retryStrategyFactory.newInstance();

        while (true) {
            try {
                runnable.run();
                return;
            }
            catch (Exception e) {

                _catch(e, exceptionList, retryStrategy);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public <TValue>
    TValue call(RetryStrategyFactory retryStrategyFactory,
                ThrowingSupplier<TValue> valueSupplier)
    throws Exception {

        final ArrayList<Exception> exceptionList = threadLocalExceptionList.getAndReset();
        final RetryStrategy retryStrategy = retryStrategyFactory.newInstance();

        while (true) {
            try {
                final TValue x = valueSupplier.get();
                return x;
            }
            catch (Exception e) {

                _catch(e, exceptionList, retryStrategy);
            }
        }
    }

    private void
    _catch(Exception e,
           ArrayList<Exception> exceptionList,
           RetryStrategy retryStrategy)
    throws Exception {

        exceptionList.add(e);

        if (retryStrategy.canRetry()) {

            _beforeRetry(retryStrategy, exceptionList);
        }
        else {
            throw _rethrowExceptions(exceptionList);
        }
    }

    private void
    _beforeRetry(RetryStrategy retryStrategy,
                 ArrayList<Exception> exceptionList)
    throws Exception {

        try {
            retryStrategy.beforeRetry();
        }
        catch (Exception e2) {

            try {
                throw _rethrowExceptions(exceptionList);
            }
            catch (Exception e3) {

                e2.addSuppressed(e3);
                throw e2;
            }
        }
    }

    private Exception
    _rethrowExceptions(ArrayList<Exception> exceptionList)
    throws Exception {

        CollectionArgs.checkNotEmpty(exceptionList, "exceptionList");
        @Nullable
        final Exception nullableException = _tryGetException(exceptionList);
        if (null != nullableException) {
            throw nullableException;
        }
        else {
            // Unreachable code in testing
            final Exception e = new Exception("Internal error: Zero exceptions selected for rethrow -- RETHROW ALL");

            for (final Exception e2 : exceptionList) {

                e.addSuppressed(e2);
            }
            throw e;
        }
    }

    @Nullable
    private Exception
    _tryGetException(ArrayList<Exception> exceptionList) {

        final int size = exceptionList.size();
        @Nullable
        Exception nullableException = null;
        for (int i = 0; i < size; ++i) {

            if (exceptionListIndexMatcher.isMatch(i, size)) {

                final Exception e = exceptionList.get(i);
                if (null == nullableException) {
                    nullableException = e;
                }
                else {
                    nullableException.addSuppressed(e);
                }
            }
        }
        return nullableException;
    }
}
