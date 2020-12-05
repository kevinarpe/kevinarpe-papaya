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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.OutputParam;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.googlecode.kevinarpe.papaya.annotation.OutputParams.out;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class RetryServiceImpTest {

    @Test
    public void run_Pass()
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.ALL);

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

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    public void run_FailWhenRunnableThrowsThenRethrowAll()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowAll(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.run(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    public void call_FailWhenRunnableThrowsThenRethrowAll()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowAll(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.call(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

    @FunctionalInterface
    private interface _Callback {

        void call(RetryServiceImp classUnderTest,
                  RetryStrategyFactory retryStrategyFactory,
                  @OutputParam int[] indexRef)
        throws Exception;
    }

//    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    private void _failWhenRunnableThrowsThenRethrowAll(_Callback callback)
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.ALL);

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        final int[] indexRef = {-1};
        try {
            callback.call(classUnderTest, retryStrategyFactory, out(indexRef));
        }
        catch (Exception e) {

            // 2?  One for each *retry* -- three attempts total
            Assert.assertEquals(indexRef[0], 2);
            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            // 2?  One for each *retry* -- three attempts total
            Assert.assertEquals(suppressedArr.length, 2);
            Assert.assertEquals(suppressedArr[0].getMessage(), "blah1");
            Assert.assertEquals(suppressedArr[1].getMessage(), "blah2");
            throw e;
        }
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    public void run_FailWhenRunnableThrowsThenRethrowFirstOnly()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowFirstOnly(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.run(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    public void call_FailWhenRunnableThrowsThenRethrowFirstOnly()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowFirstOnly(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.call(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

//    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    private void _failWhenRunnableThrowsThenRethrowFirstOnly(_Callback callback)
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.FIRST_ONLY);

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        final int[] indexRef = {-1};
        try {
            callback.call(classUnderTest, retryStrategyFactory, out(indexRef));
        }
        catch (Exception e) {

            // 2?  One for each *retry* -- three attempts total
            Assert.assertEquals(indexRef[0], 2);
            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 0);  // first only
            throw e;
        }
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    public void run_FailWhenRunnableThrowsThenRethrowFirstAndLastOnly()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowFirstAndLastOnly(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.run(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    public void call_FailWhenRunnableThrowsThenRethrowFirstAndLastOnly()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowFirstAndLastOnly(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.call(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

//    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah0$")
    private void _failWhenRunnableThrowsThenRethrowFirstAndLastOnly(_Callback callback)
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.FIRST_AND_LAST_ONLY);

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(5)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        final int[] indexRef = {-1};
        try {
            callback.call(classUnderTest, retryStrategyFactory, out(indexRef));
        }
        catch (Exception e) {

            // 5?  One for each *retry* -- six attempts total
            Assert.assertEquals(indexRef[0], 5);
            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 1);  // parent is first; child is last
            Assert.assertEquals(suppressedArr[0].getMessage(), "blah5");  // last only
            throw e;
        }
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah2$")
    public void run_FailWhenRunnableThrowsThenRethrowLastOnly()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowLastOnly(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.run(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah2$")
    public void call_FailWhenRunnableThrowsThenRethrowLastOnly()
    throws Exception {

        _failWhenRunnableThrowsThenRethrowLastOnly(
            new _Callback() {
                @Override
                public void call(RetryServiceImp classUnderTest,
                                 RetryStrategyFactory retryStrategyFactory,
                                 @OutputParam int[] indexRef)
                throws Exception {

                    classUnderTest.call(retryStrategyFactory,
                        () -> {
                            ++(indexRef[0]);
                            throw new Exception("blah" + indexRef[0]);
                        });
                }
            });
    }

//    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah2$")
    private void _failWhenRunnableThrowsThenRethrowLastOnly(_Callback callback)
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.LAST_ONLY);

        final RetryStrategyFactory retryStrategyFactory =
            BasicRetryStrategyImp.factoryBuilder()
                .maxRetryCount(2)
                .beforeRetrySleepDuration(Duration.ofMillis(1))
                .build();

        final int[] indexRef = {-1};
        try {
            callback.call(classUnderTest, retryStrategyFactory, out(indexRef));
        }
        catch (Exception e) {

            // 2?  One for each *retry* -- three attempts total
            Assert.assertEquals(indexRef[0], 2);
            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 0);  // last only
            throw e;
        }
    }

    @Test(expectedExceptions = Exception.class, expectedExceptionsMessageRegExp = "^blah$")
    public void run_FailWhenBeforeRetryThrows()
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.ALL);

        final RetryStrategy mockRetryStrategy = Mockito.mock(RetryStrategy.class);
        Mockito.when(mockRetryStrategy.canRetry()).thenReturn(true);
        Mockito.doThrow(new Exception("blah")).when(mockRetryStrategy).beforeRetry();

        final RetryStrategyFactory retryStrategyFactory = () -> mockRetryStrategy;

        try {
            classUnderTest.run(retryStrategyFactory,
                () -> {
                    throw new Exception("blah2");
                });
        }
        catch (Exception e) {

            @EmptyContainerAllowed
            final Throwable[] suppressedArr = e.getSuppressed();
            Assert.assertEquals(suppressedArr.length, 1);
            Assert.assertEquals(suppressedArr[0].getMessage(), "blah2");
            throw e;
        }
    }

    @Test
    public void call_Pass()
    throws Exception {

        final RetryServiceImp classUnderTest = new RetryServiceImp(CollectionIndexMatcher.ALL);

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
}
