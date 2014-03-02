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
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventAnalyzerImpl;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventFactory;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventFactoryImpl;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventFactoryUtils;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventFactoryUtilsInterface;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JMarkerNone;
import org.slf4j.Marker;

import java.util.Collections;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class SLF4JMockLoggerImpl
implements SLF4JMockLogger {

    private final String _name;
    private final SLF4JMockLoggerConfig _config;
    private final SLF4JLoggingEventFactory _factory;
    private final SLF4JLoggingEventFactoryUtilsInterface _factoryUtils;
    private final List<SLF4JLoggingEvent> _loggingEventList;

    public SLF4JMockLoggerImpl(String name, SLF4JMockLoggerConfigImpl config) {
        this(
            name,
            config,
            SLF4JLoggingEventFactoryImpl.INSTANCE,
            new SLF4JLoggingEventFactoryUtils());
    }

    SLF4JMockLoggerImpl(
            String name,
            SLF4JMockLoggerConfigImpl config,
            SLF4JLoggingEventFactory factory,
            SLF4JLoggingEventFactoryUtilsInterface factoryUtils) {
        _name = StringArgs.checkNotEmptyOrWhitespace(name, "name");
        ObjectArgs.checkNotNull(config, "config");
        _config = new SLF4JMockLoggerConfigImpl(config);
        _factory = ObjectArgs.checkNotNull(factory, "factory");
        _factoryUtils = ObjectArgs.checkNotNull(factoryUtils, "factoryUtils");
        _loggingEventList = Lists.newArrayList();
    }

    @Override
    public SLF4JMockLoggerConfig getConfig() {
        return _config;
    }

    /**
     *
     * @return
     *
     * @see SLF4JLoggingEventAnalyzerImpl
     */
    public List<SLF4JLoggingEvent> getLoggingEventList() {
        List<SLF4JLoggingEvent> x = Collections.unmodifiableList(_loggingEventList);
        return x;
    }

    @Override
    public String getName() {
        return _name;
    }

    private boolean _isEnabled(SLF4JLogLevel logLevel, Marker marker) {
        boolean x = _config.isEnabled(marker, logLevel);
        return x;
    }

    private void _log(SLF4JLogLevel logLevel, String msg) {
        _log(logLevel, SLF4JMarkerNone.INSTANCE, msg);
    }

    private void _log(SLF4JLogLevel logLevel, Marker marker, String msg) {
        if (_isEnabled(logLevel, marker)) {
            SLF4JLoggingEvent x = _factoryUtils.newInstance(_factory, this, logLevel, marker, msg);
            _loggingEventList.add(x);
        }
    }

    private void _log(SLF4JLogLevel logLevel, String format, Object arg) {
        _log(logLevel, SLF4JMarkerNone.INSTANCE, format, arg);
    }

    private void _log(SLF4JLogLevel logLevel, Marker marker, String format, Object arg) {
        if (_isEnabled(logLevel, marker)) {
            SLF4JLoggingEvent x =
                _factoryUtils.newInstance(_factory, this, logLevel, marker, format, arg);
            _loggingEventList.add(x);
        }
    }

    private void _log(SLF4JLogLevel logLevel, String format, Object arg1, Object arg2) {
        _log(logLevel, SLF4JMarkerNone.INSTANCE, format, arg1, arg2);
    }

    private void _log(
            SLF4JLogLevel logLevel, Marker marker, String format, Object arg1, Object arg2) {
        if (_isEnabled(logLevel, marker)) {
            SLF4JLoggingEvent x =
                _factoryUtils.newInstance(_factory, this, logLevel, marker, format, arg1, arg2);
            _loggingEventList.add(x);
        }
    }

    private void _log(SLF4JLogLevel logLevel, String format, Object... arguments) {
        _log(logLevel, SLF4JMarkerNone.INSTANCE, format, arguments);
    }

    private void _log(SLF4JLogLevel logLevel, Marker marker, String format, Object... arguments) {
        if (_isEnabled(logLevel, marker)) {
            SLF4JLoggingEvent x =
                _factoryUtils.newInstance(_factory, this, logLevel, marker, format, arguments);
            _loggingEventList.add(x);
        }
    }

    private void _log(SLF4JLogLevel logLevel, String msg, Throwable t) {
        _log(logLevel, SLF4JMarkerNone.INSTANCE, msg, t);
    }

    private void _log(SLF4JLogLevel logLevel, Marker marker, String msg, Throwable t) {
        if (_isEnabled(logLevel, marker)) {
            SLF4JLoggingEvent x =
                _factoryUtils.newInstance(_factory, this, logLevel, marker, msg, t);
            _loggingEventList.add(x);
        }
    }

    @Override
    public boolean isTraceEnabled() {
        boolean x = _isEnabled(SLF4JLogLevel.TRACE, SLF4JMarkerNone.INSTANCE);
        return x;
    }

    @Override
    public void trace(String msg) {
        _log(SLF4JLogLevel.TRACE, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        _log(SLF4JLogLevel.TRACE, format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        _log(SLF4JLogLevel.TRACE, format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        _log(SLF4JLogLevel.TRACE, format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        _log(SLF4JLogLevel.TRACE, msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean x = _isEnabled(SLF4JLogLevel.TRACE, marker);
        return x;
    }

    @Override
    public void trace(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.TRACE, marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.TRACE, marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.TRACE, marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.TRACE, marker, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.TRACE, marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        boolean x = _isEnabled(SLF4JLogLevel.DEBUG, SLF4JMarkerNone.INSTANCE);
        return x;
    }

    @Override
    public void debug(String msg) {
        _log(SLF4JLogLevel.DEBUG, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        _log(SLF4JLogLevel.DEBUG, format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        _log(SLF4JLogLevel.DEBUG, format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        _log(SLF4JLogLevel.DEBUG, format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        _log(SLF4JLogLevel.DEBUG, msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean x = _isEnabled(SLF4JLogLevel.DEBUG, marker);
        return x;
    }

    @Override
    public void debug(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.DEBUG, marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.DEBUG, marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.DEBUG, marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... argArray) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.DEBUG, marker, format, argArray);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.DEBUG, marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        boolean x = _isEnabled(SLF4JLogLevel.INFO, SLF4JMarkerNone.INSTANCE);
        return x;
    }

    @Override
    public void info(String msg) {
        _log(SLF4JLogLevel.INFO, msg);
    }

    @Override
    public void info(String format, Object arg) {
        _log(SLF4JLogLevel.INFO, format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        _log(SLF4JLogLevel.INFO, format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        _log(SLF4JLogLevel.INFO, format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        _log(SLF4JLogLevel.INFO, msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean x = _isEnabled(SLF4JLogLevel.INFO, marker);
        return x;
    }

    @Override
    public void info(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.INFO, marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.INFO, marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.INFO, marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... argArray) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.INFO, marker, format, argArray);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.INFO, marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        boolean x = _isEnabled(SLF4JLogLevel.WARN, SLF4JMarkerNone.INSTANCE);
        return x;
    }

    @Override
    public void warn(String msg) {
        _log(SLF4JLogLevel.WARN, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        _log(SLF4JLogLevel.WARN, format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        _log(SLF4JLogLevel.WARN, format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        _log(SLF4JLogLevel.WARN, format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        _log(SLF4JLogLevel.WARN, msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean x = _isEnabled(SLF4JLogLevel.WARN, marker);
        return x;
    }

    @Override
    public void warn(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.WARN, marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.WARN, marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.WARN, marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... argArray) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.WARN, marker, format, argArray);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.WARN, marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        boolean x = _isEnabled(SLF4JLogLevel.ERROR, SLF4JMarkerNone.INSTANCE);
        return x;
    }

    @Override
    public void error(String msg) {
        _log(SLF4JLogLevel.ERROR, msg);
    }

    @Override
    public void error(String format, Object arg) {
        _log(SLF4JLogLevel.ERROR, format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        _log(SLF4JLogLevel.ERROR, format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        _log(SLF4JLogLevel.ERROR, format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        _log(SLF4JLogLevel.ERROR, msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean x = _isEnabled(SLF4JLogLevel.ERROR, marker);
        return x;
    }

    @Override
    public void error(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.ERROR, marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.ERROR, marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.ERROR, marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... argArray) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.ERROR, marker, format, argArray);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _log(SLF4JLogLevel.ERROR, marker, msg, t);
    }
}
