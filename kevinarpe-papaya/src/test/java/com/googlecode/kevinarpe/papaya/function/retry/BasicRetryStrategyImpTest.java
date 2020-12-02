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

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Ref: https://stackoverflow.com/a/19494717/257299
// Ref: https://stackoverflow.com/a/35815153/257299
@PrepareForTest(BasicRetryStrategyImp.class)  // Intentional: This class calls Thread.sleep() -- that will be mocked.
public class BasicRetryStrategyImpTest
extends PowerMockTestCase {

    @Test
    public void pass()
    throws Exception {

        // This is a *reasonably* large value so we can reliably detect if PowerMock on Thread.sleep(long) works
        // correctly.  If not, this test will take a long time!
        final int millis = 12345;

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(millis))
                .build();

        final RetryStrategy retryStrategy = retryStrategyFactory.newInstance();

        // We do not want to mock all methods, only specific methods, such as Thread.sleep().
        // Use spy() instead of mockStatic().
        PowerMockito.spy(Thread.class);

        // These two lines are tightly bound.
        PowerMockito.doNothing().when(Thread.class);
//        Thread.sleep(Mockito.anyLong());
        Thread.sleep(millis);  // more precise

        Assert.assertTrue(retryStrategy.canRetry());
        retryStrategy.beforeRetry();

        Assert.assertTrue(retryStrategy.canRetry());
        retryStrategy.beforeRetry();

        Assert.assertFalse(retryStrategy.canRetry());

        // These two lines are tightly bound.
        PowerMockito.verifyStatic(Thread.class, Mockito.times(2));
//        Thread.sleep(Mockito.anyLong());
        Thread.sleep(millis);  // more precise
    }

    @Test(
        expectedExceptions = InterruptedException.class,
        expectedExceptionsMessageRegExp = "^blah$")
    public void fail_beforeRetry_WhenThreadSleepThrows()
    throws Exception {

        // This is a *reasonably* large value so we can reliably detect if PowerMock on Thread.sleep(long) works
        // correctly.  If not, this test will take a long time!
        final int millis = 12345;

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(millis))
                .build();

        // We do not want to mock all methods, only specific methods, such as Thread.sleep().
        // Use spy() instead of mockStatic().
        PowerMockito.spy(Thread.class);

        // These two lines are tightly bound.
        PowerMockito.doThrow(new InterruptedException("blah")).when(Thread.class);
//        Thread.sleep(Mockito.anyLong());
        Thread.sleep(millis);  // more precise

        final RetryStrategy retryStrategy = retryStrategyFactory.newInstance();
        try {
            retryStrategy.beforeRetry();
        }
        catch (Exception e) {
            // @DebugBreakpoint
            throw e;
        }
    }
}
