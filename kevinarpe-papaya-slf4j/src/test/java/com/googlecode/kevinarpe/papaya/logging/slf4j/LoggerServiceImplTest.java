package com.googlecode.kevinarpe.papaya.logging.slf4j;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringService;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceFactory;
import com.googlecode.kevinarpe.papaya.string.MessageFormatter;
import com.googlecode.kevinarpe.papaya.string.MessageFormatterImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class LoggerServiceImplTest {

    @ReadOnlyContainer
    private static final LoggerLevel[] LOGGER_LEVEL_ARR = LoggerLevel.values();
    public static final IncludeStackTrace[] INCLUDE_STACK_TRACE_ARR = IncludeStackTrace.values();

    @Mock
    private ThrowableToStringService mockThrowableToStringService;

    @Mock
    private MessageFormatter mockMessageFormatter;

    @Mock
    private Logger mockLogger;

    private LoggerServiceImpl classUnderTestWithMocks;

    private LoggerServiceImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTest() {

        MockitoAnnotations.initMocks(this);

        final ThrowableToStringServiceFactory throwableToStringServiceFactory = () -> mockThrowableToStringService;

        this.classUnderTestWithMocks = new LoggerServiceImpl(throwableToStringServiceFactory, mockMessageFormatter);

        this.classUnderTest =
            new LoggerServiceImpl(ThrowableToStringServiceFactory.DEFAULT_IMPL, MessageFormatterImpl.INSTANCE);
    }

    @Test
    public void log_Pass() {

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            classUnderTestWithMocks.log(mockLogger, loggerLevel, loggerLevel.name());

            switch (loggerLevel) {
                case INFO: {
                    Mockito.verify(mockLogger).info(loggerLevel.name());
                    break;
                }
                case ERROR: {
                    Mockito.verify(mockLogger).error(loggerLevel.name());
                    break;
                }
                case DEBUG: {
                    Mockito.verify(mockLogger).debug(loggerLevel.name());
                    break;
                }
                case TRACE: {
                    Mockito.verify(mockLogger).trace(loggerLevel.name());
                    break;
                }
                default: {
                    throw new IllegalStateException("Unreachable code");
                }
            }
        }
        Mockito.verifyNoMoreInteractions(mockLogger);
    }

    @DataProvider
    private static Object[][]
    _log_FailWhenInvalidMessage_Data() {
        return new Object[][] {
            {(String)null, NullPointerException.class},
            {"", IllegalArgumentException.class},
            {"   ", IllegalArgumentException.class},
            {"\t", IllegalArgumentException.class},
            {"\n", IllegalArgumentException.class},
        };
    }

    @Test(dataProvider = "_log_FailWhenInvalidMessage_Data")
    public void log_FailWhenInvalidMessage(String message, Class<? extends Exception> exceptionClass) {

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            Exception e0 = null;
            try {
                classUnderTestWithMocks.log(mockLogger, loggerLevel, message);
            }
            catch (Exception e) {
                e0 = e;
            }
            if (null == e0) {
                throw new IllegalStateException("Unreachable code");
            }
            if (false == exceptionClass.isInstance(e0)) {
                throw new IllegalStateException("Unreachable code");
            }
        }
    }

    @Test
    public void formatThenLog_Pass() {

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            final String expectedMessage = String.format("[%s]", loggerLevel.name());

            Mockito.when(mockMessageFormatter.format("[%s]", loggerLevel.name()))
                .thenReturn(expectedMessage);

            classUnderTestWithMocks.formatThenLog(mockLogger, loggerLevel, "[%s]", loggerLevel.name());

            switch (loggerLevel) {
                case INFO: {
                    Mockito.verify(mockLogger).info(expectedMessage);
                    break;
                }
                case ERROR: {
                    Mockito.verify(mockLogger).error(expectedMessage);
                    break;
                }
                case DEBUG: {
                    Mockito.verify(mockLogger).debug(expectedMessage);
                    break;
                }
                case TRACE: {
                    Mockito.verify(mockLogger).trace(expectedMessage);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unreachable code");
                }
            }
            Mockito.verify(mockMessageFormatter).format("[%s]", loggerLevel.name());
        }
        Mockito.verifyNoMoreInteractions(mockLogger, mockMessageFormatter);
    }

    @Test(dataProvider = "_log_FailWhenInvalidMessage_Data")
    public void formatThenLog_FailWhenInvalidMessage(String message, Class<? extends Exception> exceptionClass) {

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            Mockito.when(mockMessageFormatter.format("[%s]", loggerLevel.name()))
                .thenReturn(message);

            Exception e0 = null;
            try {
                classUnderTestWithMocks.formatThenLog(mockLogger, loggerLevel, "[%s]", loggerLevel.name());
            }
            catch (Exception e) {
                e0 = e;
            }
            if (null == e0) {
                throw new IllegalStateException("Unreachable code");
            }
            if (false == exceptionClass.isInstance(e0)) {
                throw new IllegalStateException("Unreachable code");
            }
            Mockito.verify(mockMessageFormatter).format("[%s]", loggerLevel.name());
        }
        Mockito.verifyNoMoreInteractions(mockMessageFormatter);
    }

    @Test
    public void logThrowable() {

        final String unique = "UNIQUE";
        Mockito.when(mockThrowableToStringService.toStringWithUniqueStackTrace(Mockito.any())).thenReturn(unique);

        final Exception e0 = new Exception();

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            for (final IncludeStackTrace includeStackTrace : INCLUDE_STACK_TRACE_ARR) {

                System.out.println(loggerLevel.name() + "/" + includeStackTrace.name());

                try {
                    throw e0;
                }
                catch (Exception e) {
                    classUnderTestWithMocks.logThrowable(
                        mockLogger, loggerLevel, includeStackTrace, loggerLevel.name(), e);

                    final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

                    switch (loggerLevel) {
                        case INFO: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .info(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(loggerLevel.name()),
                                    argumentCaptor.capture());
                            break;
                        }
                        case ERROR: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .error(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(loggerLevel.name()),
                                    argumentCaptor.capture());
                            break;
                        }
                        case DEBUG: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .debug(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(loggerLevel.name()),
                                    argumentCaptor.capture());
                            break;
                        }
                        case TRACE: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .trace(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(loggerLevel.name()),
                                    argumentCaptor.capture());
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unreachable code");
                        }
                    }
                    final List<String> messageList = argumentCaptor.getAllValues();
                    // Intentional: Always take *last* item from argumentCaptor.
                    final String message = messageList.get(messageList.size() - 1);

                    switch (includeStackTrace) {
                        case YES: {
                            _assertStringContains(message, this.getClass().getSimpleName());
                            break;
                        }
                        case UNIQUE_ONLY: {
                            _assertStringContains(message, unique);
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unreachable code");
                    }
                }
            }
        }
    }

    @Test(dataProvider = "_log_FailWhenInvalidMessage_Data")
    public void logThrowable_FailWhenInvalidMessage(String message, Class<? extends Exception> exceptionClass) {

        final Exception e0 = new Exception();

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            for (final IncludeStackTrace includeStackTrace : INCLUDE_STACK_TRACE_ARR) {

                try {
                    throw e0;
                }
                catch (Exception e) {

                    Exception e1 = null;
                    try {
                        classUnderTestWithMocks.logThrowable(mockLogger, loggerLevel, includeStackTrace, message, e);
                    }
                    catch (Exception e2) {
                        e1 = e2;
                    }
                    if (null == e1) {
                        throw new IllegalStateException("Unreachable code");
                    }
                    if (false == exceptionClass.isInstance(e1)) {
                        throw new IllegalStateException("Unreachable code");
                    }
                }
            }
        }
    }

    @Test
    public void formatThenLogThrowable_Pass() {

        final String unique = "UNIQUE";
        Mockito.when(mockThrowableToStringService.toStringWithUniqueStackTrace(Mockito.any())).thenReturn(unique);

        final Exception e0 = new Exception();

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            for (final IncludeStackTrace includeStackTrace : INCLUDE_STACK_TRACE_ARR) {

                final String expectedMessage = String.format("[%s/%s]", loggerLevel.name(), includeStackTrace.name());

                Mockito.when(mockMessageFormatter.format("[%s/%s]", loggerLevel.name(), includeStackTrace.name()))
                    .thenReturn(expectedMessage);

                System.out.println(loggerLevel.name() + "/" + includeStackTrace.name());

                try {
                    throw e0;
                }
                catch (Exception e) {

                    classUnderTestWithMocks.formatThenLogThrowable(
                        mockLogger, loggerLevel, includeStackTrace, e, "[%s/%s]", loggerLevel.name(), includeStackTrace.name());

                    final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

                    switch (loggerLevel) {
                        case INFO: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .info(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(expectedMessage),
                                    argumentCaptor.capture());
                            break;
                        }
                        case ERROR: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .error(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(expectedMessage),
                                    argumentCaptor.capture());
                            break;
                        }
                        case DEBUG: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .debug(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(expectedMessage),
                                    argumentCaptor.capture());
                            break;
                        }
                        case TRACE: {
                            Mockito.verify(mockLogger, Mockito.atLeastOnce())
                                .trace(
                                    Mockito.eq(LoggerServiceImpl.SLF4J_FORMAT),
                                    Mockito.eq(expectedMessage),
                                    argumentCaptor.capture());
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unreachable code");
                        }
                    }
                    Mockito.verify(mockMessageFormatter).format("[%s/%s]", loggerLevel.name(), includeStackTrace.name());

                    final List<String> messageList = argumentCaptor.getAllValues();
                    // Intentional: Always take *last* item from argumentCaptor.
                    final String message = messageList.get(messageList.size() - 1);

                    switch (includeStackTrace) {
                        case YES: {
                            _assertStringContains(message, this.getClass().getSimpleName());
                            break;
                        }
                        case UNIQUE_ONLY: {
                            _assertStringContains(message, unique);
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unreachable code");
                    }
                }
            }
        }
        Mockito.verifyNoMoreInteractions(mockLogger, mockMessageFormatter);
    }

    @Test(dataProvider = "_log_FailWhenInvalidMessage_Data")
    public void formatThenLogThrowable_FailWhenInvalidMessage(String message, Class<? extends Exception> exceptionClass) {

        final Exception e0 = new Exception();

        for (final LoggerLevel loggerLevel : LOGGER_LEVEL_ARR) {

            for (final IncludeStackTrace includeStackTrace : INCLUDE_STACK_TRACE_ARR) {

                Mockito.when(mockMessageFormatter.format("[%s/%s]", loggerLevel.name(), includeStackTrace.name()))
                    .thenReturn(message);

                try {
                    throw e0;
                }
                catch (Exception e) {

                    Exception e1 = null;
                    try {
                        classUnderTestWithMocks.formatThenLogThrowable(
                            mockLogger, loggerLevel, includeStackTrace, e, "[%s/%s]", loggerLevel.name(), includeStackTrace.name());
                    }
                    catch (Exception e2) {
                        e1 = e2;
                    }
                    if (null == e1) {
                        throw new IllegalStateException("Unreachable code");
                    }
                    if (false == exceptionClass.isInstance(e1)) {
                        throw new IllegalStateException("Unreachable code");
                    }
                }
            }
        }
    }

    private void _assertStringContains(String haystack, String needle) {

        Assert.assertTrue(haystack.contains(needle), haystack + ".contains(" + needle + ")");
    }
}
