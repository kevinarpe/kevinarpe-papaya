package com.googlecode.kevinarpe.papaya.logging.slf4j;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.IncludeStackTrace;
import com.googlecode.kevinarpe.papaya.exception.ThrowableUtils;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.Arrays;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Needs to be public?
@FullyTested
final class SLF4JLoggingEventImpl
implements SLF4JLoggingEvent {

    private static final Object[] EMPTY_FORMAT_ARG_ARR = new Object[0];

    private final Logger _logger;
    private final SLF4JLogLevel _logLevel;
    private final Marker _marker;
    private final String _message;
    private final Object[] _formatArgArr;
    private final Throwable _optionalThrowable;
    private final String _threadName;
    private final long _timeStamp;

    interface ISystem {
        String getCurrentThreadName();
        long currentTimeMillis();
    }

    private static class SystemImpl
    implements ISystem {

        public static final SystemImpl INSTANCE = new SystemImpl();

        @Override
        public String getCurrentThreadName() {
            String x = Thread.currentThread().getName();
            return x;
        }

        @Override
        public long currentTimeMillis() {
            long x = System.currentTimeMillis();
            return x;
        }
    }

    public SLF4JLoggingEventImpl(
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object[] optionalFormatArgArr,
            Throwable optionalThrowable) {
        this(
            logger,
            logLevel,
            marker,
            message,
            optionalFormatArgArr,
            optionalThrowable,
            SystemImpl.INSTANCE);
    }

    SLF4JLoggingEventImpl(
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object[] optionalFormatArgArr,
            Throwable optionalThrowable,
            ISystem system) {
        _logger = ObjectArgs.checkNotNull(logger, "logger");
        _logLevel = ObjectArgs.checkNotNull(logLevel, "logLevel");
        _marker = ObjectArgs.checkNotNull(marker, "marker");
        _message = ObjectArgs.checkNotNull(message, "message");
        // Extract last element from 'optionalFormatArgArr' if element has type Throwable.
        if (null == optionalThrowable
            && null != optionalFormatArgArr
            && optionalFormatArgArr.length > 0
            && optionalFormatArgArr[optionalFormatArgArr.length - 1] instanceof Throwable) {
            optionalThrowable = (Throwable) optionalFormatArgArr[optionalFormatArgArr.length - 1];
            Object[] arr = new Object[optionalFormatArgArr.length - 1];
            System.arraycopy(optionalFormatArgArr, 0, arr, 0, arr.length);
            optionalFormatArgArr = arr;
        }
        _formatArgArr =
            (null == optionalFormatArgArr ? EMPTY_FORMAT_ARG_ARR : optionalFormatArgArr.clone());
        _optionalThrowable = optionalThrowable;
        ObjectArgs.checkNotNull(system, "system");
        _threadName = system.getCurrentThreadName();
        _timeStamp = system.currentTimeMillis();
    }

    @Override
    public Logger getLogger() {
        return _logger;
    }

    @Override
    public SLF4JLogLevel getLevel() {
        return _logLevel;
    }

    @Override
    public Marker getMarker() {
        return _marker;
    }

    @Override
    public String getMessage() {
        return _message;
    }

    @Override
    public Object[] getMessageFormatArgArr() {
        if (EMPTY_FORMAT_ARG_ARR == _formatArgArr) {
            return _formatArgArr;
        }
        return _formatArgArr.clone();
    }

    @Override
    public Throwable getThrowable() {
        return _optionalThrowable;
    }

    @Override
    public String getThreadName() {
        return _threadName;
    }

    @Override
    public long getTimeStamp() {
        return _timeStamp;
    }

    @Override
    public String getFormattedMessage() {
        FormattingTuple ft = MessageFormatter.arrayFormat(_message, _formatArgArr);
        String x = ft.getMessage();
        return x;
    }

    @Override
    public <T> T getAttributeValue(ISLF4JLoggingEventAttribute attribute) {
        ObjectArgs.checkNotNull(attribute, "attribute");

        Object value = attribute.getValue(this);
        @SuppressWarnings("unchecked")
        T castValue = (T) value;
        return castValue;
    }

    @Override
    public int hashCode() {
        int result =
            Objects.hashCode(
                _logger, _logLevel, _marker, _message, _optionalThrowable, _threadName, _timeStamp);
        result = 31 * result + Arrays.hashCode(_formatArgArr);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof SLF4JLoggingEventImpl) {
            final SLF4JLoggingEventImpl other = (SLF4JLoggingEventImpl) obj;
            result =
                _timeStamp == other._timeStamp
                    && Objects.equal(_logger, other._logger)
                    && Objects.equal(_logLevel, other._logLevel)
                    && Objects.equal(_marker, other._marker)
                    && Objects.equal(_message, other._message)
                    && Arrays.equals(_formatArgArr, other._formatArgArr)
                    && ThrowableUtils.equals(
                    _optionalThrowable, other._optionalThrowable, IncludeStackTrace.YES)
                    && Objects.equal(_threadName, other._threadName);
        }
        return result;
    }
}
