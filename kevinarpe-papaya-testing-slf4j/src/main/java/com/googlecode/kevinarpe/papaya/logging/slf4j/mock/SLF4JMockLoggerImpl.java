package com.googlecode.kevinarpe.papaya.logging.slf4j.mock;

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

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEvent;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventImpl;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JMarkerNone;
import org.slf4j.Marker;

import java.util.Collections;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Do we need to create ForwardingLogger?
public class SLF4JMockLoggerImpl
implements SLF4JMockLogger {

    private final String _name;
    private final SLF4JMockLoggerConfig _config;
    private final List<SLF4JLoggingEvent> _loggingEventList;

    public SLF4JMockLoggerImpl(String name, SLF4JMockLoggerConfigImpl config) {
        _name = StringArgs.checkNotEmptyOrWhitespace(name, "name");
        ObjectArgs.checkNotNull(config, "config");
        _config = new SLF4JMockLoggerConfigImpl(config);
        _loggingEventList = Lists.newArrayList();
    }

    @Override
    public SLF4JMockLoggerConfig getConfig() {
        return _config;
    }

    public List<SLF4JLoggingEvent> getLoggingEventList() {
        List<SLF4JLoggingEvent> x = Collections.unmodifiableList(_loggingEventList);
        return x;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public boolean isTraceEnabled() {
        boolean x = isTraceEnabled(SLF4JMarkerNone.INSTANCE);
        return x;
    }

    @Override
    public void trace(String msg) {
        if (isTraceEnabled()) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(this, SLF4JLogLevel.TRACE, SLF4JMarkerNone.INSTANCE, msg);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled()) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(
                    this, SLF4JLogLevel.TRACE, SLF4JMarkerNone.INSTANCE, format, arg);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled()) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(
                    this, SLF4JLogLevel.TRACE, SLF4JMarkerNone.INSTANCE, format, arg1, arg2);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled()) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(
                    this, SLF4JLogLevel.TRACE, SLF4JMarkerNone.INSTANCE, format, arguments);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (isTraceEnabled()) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(
                    this, SLF4JLogLevel.TRACE, SLF4JMarkerNone.INSTANCE, msg, t);
            _loggingEventList.add(x);
        }
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        boolean x = _config.isEnabled(marker, SLF4JLogLevel.TRACE);
        return x;
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (isTraceEnabled(marker)) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(this, SLF4JLogLevel.TRACE, marker, msg);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (isTraceEnabled(marker)) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(this, SLF4JLogLevel.TRACE, marker, format, arg);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (isTraceEnabled(marker)) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(this, SLF4JLogLevel.TRACE, marker, format, arg1, arg2);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        if (isTraceEnabled(marker)) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(this, SLF4JLogLevel.TRACE, marker, format, argArray);
            _loggingEventList.add(x);
        }
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        if (isTraceEnabled(marker)) {
            SLF4JLoggingEventImpl x =
                new SLF4JLoggingEventImpl(this, SLF4JLogLevel.TRACE, marker, msg, t);
            _loggingEventList.add(x);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {

    }

    @Override
    public void debug(String format, Object arg) {

    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(String format, Object... arguments) {

    }

    @Override
    public void debug(String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void info(String format, Object arg) {

    }

    @Override
    public void info(String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(String format, Object... arguments) {

    }

    @Override
    public void info(String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override
    public void info(Marker marker, String msg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {

    }

    @Override
    public void warn(String format, Object arg) {

    }

    @Override
    public void warn(String format, Object... arguments) {

    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {

    }

    @Override
    public void warn(String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    public void warn(Marker marker, String msg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void error(String format, Object arg) {

    }

    @Override
    public void error(String format, Object arg1, Object arg2) {

    }

    @Override
    public void error(String format, Object... arguments) {

    }

    @Override
    public void error(String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return false;
    }

    @Override
    public void error(Marker marker, String msg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {

    }
}
