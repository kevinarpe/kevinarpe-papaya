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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class SLF4JLoggingEventImpl
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

    public SLF4JLoggingEventImpl(
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object[] optionalFormatArgArr,
            Throwable optionalThrowable) {
        _logger = ObjectArgs.checkNotNull(logger, "logger");
        _logLevel = ObjectArgs.checkNotNull(logLevel, "logLevel");
        _marker = ObjectArgs.checkNotNull(marker, "marker");
        _message = ObjectArgs.checkNotNull(message, "message");
        // The last element in 'optionalFormatArgArr' might be our Throwable.
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
        _threadName = Thread.currentThread().getName();
        _timeStamp = System.currentTimeMillis();
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
    public <T> T getAttributeValue(SLF4JLoggingEventAttribute attribute) {
        Object value = attribute.getValue(this);
        @SuppressWarnings("unchecked")
        T castValue = (T) value;
        return castValue;
    }
}
