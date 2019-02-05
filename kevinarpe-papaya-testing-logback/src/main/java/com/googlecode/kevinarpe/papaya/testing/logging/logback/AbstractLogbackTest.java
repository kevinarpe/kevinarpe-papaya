package com.googlecode.kevinarpe.papaya.testing.logging.logback;

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

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.testing.logging.CapturingLogger;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class AbstractLogbackTest
implements CapturingLogger<ILoggingEvent> {

    @Nullable
    private Appender<ILoggingEvent> nullableMockAppender;

    protected AbstractLogbackTest() {
        // Empty
    }

    /**
     * Appends a mock logger to the root logger.  Logging events are only captured after calling
     * this method.
     *
     * Logging events are accessible via {@link #getLoggingEventList()}.
     *
     * @see LoggerFactory#getLogger(String)
     * @see Logger#ROOT_LOGGER_NAME
     * @see ch.qos.logback.classic.Logger#addAppender(Appender)
     * @see #removeMockAppender()
     * @see #getLoggingEventList()
     */
    protected final void
    addMockAppender() {

        final ch.qos.logback.classic.Logger rootLogger = _getRootLogger();
        @SuppressWarnings("unchecked")
        final Appender<ILoggingEvent> mockAppender = this.nullableMockAppender = mock(Appender.class);
        rootLogger.addAppender(mockAppender);
    }

    private ch.qos.logback.classic.Logger
    _getRootLogger() {

        final Logger uncastRootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        final ch.qos.logback.classic.Logger x =
            ObjectArgs.checkCast(
                uncastRootLogger,
                ch.qos.logback.classic.Logger.class,
                "LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)");
        return x;
    }

    /**
     * Removes the mock logger from the root logger.  Logging events are not capturing after calling
     * this method.
     *
     * Logging events are accessible via {@link #getLoggingEventList()}.
     *
     * @see LoggerFactory#getLogger(String)
     * @see Logger#ROOT_LOGGER_NAME
     * @see ch.qos.logback.classic.Logger#detachAppender(Appender)
     * @see #addMockAppender()
     * @see #getLoggingEventList()
     */
    protected final void
    removeMockAppender() {

        ObjectArgs.checkNotNull(nullableMockAppender, "nullableMockAppender");

        final ch.qos.logback.classic.Logger rootLogger = _getRootLogger();
        rootLogger.detachAppender(nullableMockAppender);
    }

    /** {@inheritDoc} */
    @Override
    @EmptyContainerAllowed
    @ReadOnlyContainer
    public List<ILoggingEvent>
    getLoggingEventList() {

        ObjectArgs.checkNotNull(nullableMockAppender, "nullableMockAppender");

        final ArgumentCaptor<ILoggingEvent> argumentCaptor = ArgumentCaptor.forClass(ILoggingEvent.class);
        try {
            verify(nullableMockAppender, atLeastOnce()).doAppend(argumentCaptor.capture());
            final List<ILoggingEvent> x = argumentCaptor.getAllValues();
            return x;
        }
        catch (Throwable ignore) {
            return ImmutableList.of();
        }
    }
}
