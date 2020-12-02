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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class RetryServiceImpTest {

    private RetryServiceImp classUnderTest;

    @BeforeMethod
    public void beforeEachMethod() {

        classUnderTest = new RetryServiceImp();
    }

    @Test
    public void run_Pass()
    throws Exception {

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(1)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        final int[] attemptRef = {0};

        classUnderTest.run(retryStrategyFactory,
            () -> {
                ++(attemptRef[0]);
                switch (attemptRef[0]) {
                    case 1: {
                        throw new Exception("blah");
                    }
                    case 2: {
                        int dummy = 1;
                        break;
                    }
                    default: {
                        throw new Exception("Internal error: Missing switch case " + attemptRef[0]);
                    }
                }
            });
        // Dummy assert to demonstrate the previous method call completes without throwing an exception.
        Assert.assertTrue(true);
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah$")
    public void run_FailWhenRunnableThrows()
    throws Exception {

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        classUnderTest.run(retryStrategyFactory,
            () -> {
                throw new Exception("blah");
            });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah$")
    public void run_FailWhenBeforeRetryThrows()
    throws Exception {

        final RetryStrategy mockRetryStrategy = Mockito.mock(RetryStrategy.class);
        Mockito.when(mockRetryStrategy.canRetry()).thenReturn(true);
        Mockito.doThrow(new Exception("blah")).when(mockRetryStrategy).beforeRetry();

        final RetryStrategyFactory retryStrategyFactory = () -> mockRetryStrategy;

        classUnderTest.run(retryStrategyFactory,
            () -> {
                throw new Exception("blah2");
            });
    }

    @Test
    public void call_Pass()
    throws Exception {

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(1)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        final int[] attemptRef = {0};

        final String result =
            classUnderTest.call(retryStrategyFactory,
                () -> {
                    ++(attemptRef[0]);
                    switch (attemptRef[0]) {
                        case 1: {
                            throw new Exception("blah");
                        }
                        case 2: {
                            return "abc";
                        }
                        default: {
                            throw new Exception("Internal error: Missing switch case " + attemptRef[0]);
                        }
                    }
                });
        Assert.assertEquals(result, "abc");
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah$")
    public void call_FailWhenSupplierThrows()
    throws Exception {

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        classUnderTest.call(retryStrategyFactory,
            () -> {
                throw new Exception("blah");
            });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah$")
    public void call_FailWhenBeforeRetryThrows()
    throws Exception {

        final RetryStrategy mockRetryStrategy = Mockito.mock(RetryStrategy.class);
        Mockito.when(mockRetryStrategy.canRetry()).thenReturn(true);
        Mockito.doThrow(new Exception("blah")).when(mockRetryStrategy).beforeRetry();

        final RetryStrategyFactory retryStrategyFactory = () -> mockRetryStrategy;

        classUnderTest.call(retryStrategyFactory,
            () -> {
                throw new Exception("blah2");
            });
    }
}
