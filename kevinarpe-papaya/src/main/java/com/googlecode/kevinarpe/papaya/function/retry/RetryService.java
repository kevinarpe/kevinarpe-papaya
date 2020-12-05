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

import com.googlecode.kevinarpe.papaya.function.ThrowingRunnable;
import com.googlecode.kevinarpe.papaya.function.ThrowingSupplier;

import javax.annotation.concurrent.ThreadSafe;

/**
 * ThreadSafe?  Implementations must be thread-safe.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@ThreadSafe
public interface RetryService {

    /**
     * Executes a {@link ThrowingRunnable}.  If an exception is thrown by {@code runnable}, it is caught and retry is
     * attempted.  If no further retries are allowed, the exception is rethrown.
     * <p>
     * To be clear: "Attempt" and "retry" are not the same.  In short, the *first* attempt is not a retry.  All further
     * attempts are retries.  Thus, to try ten times, use a retry strategy with nine retries.
     * <p>
     * To return a value, see: {@link #call(RetryStrategyFactory, ThrowingSupplier)}.
     * <p>
     * Why doesn't a non-throwing version of this method exist with parameter type {@link Runnable}?
     * <ol>
     *     <li>Non-throwing is a misnomer, as {@link Runnable} may throw an unchecked {@link RuntimeException}.</li>
     *     <li>{@link RetryStrategy#beforeRetry()} throws a checked {@link Exception}.</li>
     * </ol>
     *
     * @param retryStrategyFactory
     *        factory to generate an instance of {@link RetryStrategy}
     *        <br>See: {@link BasicRetryStrategyImp#factoryBuilder()}
     *
     * @param runnable
     *        code to execute
     *
     * @throws Exception
     *         re-thrown from {@code runnable} if no further retries are allowed
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     *
     * @see #call(RetryStrategyFactory, ThrowingSupplier)
     */
    void run(RetryStrategyFactory retryStrategyFactory,
             ThrowingRunnable runnable)
    throws Exception;

    /**
     * This method is identical to {@link #run(RetryStrategyFactory, ThrowingRunnable)}, except a value may be returned.
     * <p>
     * Do not forget that {@code TValue} may be {@code Void}!
     *
     * @param retryStrategyFactory
     *        factory to generate an instance of {@link RetryStrategy}
     *        <br>See: {@link BasicRetryStrategyImp#factoryBuilder()}
     *
     * @param valueSupplier
     *        code to execute and return a value
     *
     * @return value returned by {@code valueSupplier}
     *
     * @throws Exception
     *         re-thrown from {@code runnable} if no further retries are allowed
     *         <br>or, thrown from {@link RetryStrategy#beforeRetry()}
     *
     * @see #run(RetryStrategyFactory, ThrowingRunnable)
     */
    <TValue>
    TValue call(RetryStrategyFactory retryStrategyFactory,
                ThrowingSupplier<TValue> valueSupplier)
    throws Exception;
}
