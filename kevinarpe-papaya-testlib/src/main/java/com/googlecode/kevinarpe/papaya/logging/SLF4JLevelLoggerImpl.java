package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class SLF4JLevelLoggerImpl
implements SLF4JLevelLogger {

    private final Logger _optLogger;
    private final SLF4JLogLevel _logLevel;

    public SLF4JLevelLoggerImpl(Logger optLogger, SLF4JLogLevel logLevel) {
        //_optLogger = ObjectArgs.checkNotNull(optLogger, "optLogger");
        _optLogger = optLogger;
        _logLevel = ObjectArgs.checkNotNull(logLevel, "logLevel");
    }

    @Override
    public boolean isEnabled() {
        boolean b = _logLevel.isEnabled(_optLogger);
        return b;
    }

    @Override
    public void log(String msg) {
        _logLevel.log(_optLogger, msg);
    }

    @Override
    public void log(String format, Object arg) {
        _logLevel.log(_optLogger, format, arg);
    }

    @Override
    public void log(String format, Object arg1, Object arg2) {
        _logLevel.log(_optLogger, format, arg1, arg2);
    }

    @Override
    public void log(String format, Object... argArr) {
        _logLevel.log(_optLogger, format, argArr);
    }

    @Override
    public void log(String msg, Throwable t) {
        _logLevel.log(_optLogger, msg, t);
    }

    @Override
    public boolean isEnabled(Marker marker) {
        boolean b = _logLevel.isEnabled(_optLogger, marker);
        return b;
    }

    @Override
    public void log(Marker marker, String msg) {
        _logLevel.log(_optLogger, msg);
    }

    @Override
    public void log(Marker marker, String format, Object arg) {
        _logLevel.log(_optLogger, format, arg);
    }

    @Override
    public void log(Marker marker, String format, Object arg1, Object arg2) {
        _logLevel.log(_optLogger, format, arg1, arg2);
    }

    @Override
    public void log(Marker marker, String format, Object... argArr) {
        _logLevel.log(_optLogger, format, argArr);
    }

    @Override
    public void log(Marker marker, String msg, Throwable t) {
        _logLevel.log(_optLogger, msg, t);
    }
}
