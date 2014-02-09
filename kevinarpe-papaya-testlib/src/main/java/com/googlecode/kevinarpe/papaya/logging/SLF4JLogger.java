package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Maybe useless now?
@NotFullyTested
public final class SLF4JLogger
implements Logger {

    private final Logger _optLogger;

    public SLF4JLogger() {
        this(null);
    }

    public SLF4JLogger(Logger optLogger) {
        _optLogger = optLogger;
    }

    @Override
    public String getName() {
        if (null == _optLogger) {
            return null;
        }
        return _optLogger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        if (null == _optLogger) {
            return false;
        }
        return _optLogger.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        if (null != _optLogger) {
            _optLogger.trace(msg);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if (null != _optLogger) {
            _optLogger.trace(format, arg);
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (null != _optLogger) {
            _optLogger.trace(format, arg1, arg2);
        }
    }

    @Override
    public void trace(String format, Object... argArr) {
        if (null != _optLogger) {
            _optLogger.trace(format, argArr);
        }
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (null != _optLogger) {
            _optLogger.trace(msg, t);
        }
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        if (null == _optLogger) {
            return false;
        }
        return _optLogger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (null != _optLogger) {
            _optLogger.trace(marker, msg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (null != _optLogger) {
            _optLogger.trace(marker, format, arg);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (null != _optLogger) {
            _optLogger.trace(marker, format, arg1, arg2);
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... argArr) {
        if (null != _optLogger) {
            _optLogger.trace(marker, format, argArr);
        }
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        if (null != _optLogger) {
            _optLogger.trace(marker, msg, t);
        }
    }

    // TODO: Finish me
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
