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
import com.googlecode.kevinarpe.papaya.annotation.NonBlocking;

/**
 * To be clear: The *first* attempt is not considered a retry.  Thus, if ten attempts are made, the last nine
 * are considered retries.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface RetryStrategy {

    public static final RetryStrategy NO_RETRY =
        new RetryStrategy() {
            @Override public boolean canRetry() { return false; }
            @Override public void beforeRetry() throws Exception { }
        };

    /**
     * This method is idempotent -- it is safely to call many times.  Only method {@link #beforeRetry()} will update the
     * internal state (if any).
     *
     * @return {@code true} if retry is allowed
     */
    @NonBlocking
    boolean canRetry();

    /**
     * Unlike method {@link #canRetry()}, this method is <b>not</b> idempotent -- it is <b>not</b> safe to call many
     * times.  Each call is expected to update internal state (increment retry counter, etc.).
     * <p>
     * Blocking?  Yes, most retry strategies will sleep before retry, and do not forget that {@link Thread#sleep(long)}
     * can throw {@link InterruptedException}.
     *
     * @throws Exception
     *         on error, e.g., {@link Thread#sleep(long)} can throw {@link InterruptedException}
     */
    @Blocking
    void beforeRetry()
    throws Exception;
}
