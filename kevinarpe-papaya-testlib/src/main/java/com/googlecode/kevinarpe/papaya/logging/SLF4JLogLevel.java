package com.googlecode.kevinarpe.papaya.logging;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface SLF4JLogLevel {

    // TODO: How to distinguish between SLF4JLogLevel and SLF4JLevelLogger?

    SLF4JLogLevelEnum getEnum();

    boolean isEnabled(Logger logger);
    void log(Logger logger, String msg);
    void log(Logger logger, String format, Object arg);
    void log(Logger logger, String format, Object arg1, Object arg2);
    void log(Logger logger, String format, Object... argArr);
    void log(Logger logger, String msg, Throwable t);

    boolean isEnabled(Logger logger, Marker marker);
    void log(Logger logger, Marker marker, String msg);
    void log(Logger logger, Marker marker, String format, Object arg);
    void log(Logger logger, Marker marker, String format, Object arg1, Object arg2);
    void log(Logger logger, Marker marker, String format, Object... argArr);
    void log(Logger logger, Marker marker, String msg, Throwable t);

    public static final SLF4JLogLevel OFF = new SLF4JLogLevelOff();

    /**
     * Corresponds to methods {@link Logger#isErrorEnabled()}, Logger#error(String), etc.
     */
    public static final SLF4JLogLevel ERROR = new SLF4JLogLevelError();

    /**
     * Corresponds to methods {@link Logger#isWarnEnabled()}, Logger#warn(String), etc.
     */
    public static final SLF4JLogLevel WARN = new SLF4JLogLevelWarn();

    /**
     * Corresponds to methods {@link Logger#isInfoEnabled()}, Logger#info(String), etc.
     */
    public static final SLF4JLogLevel INFO = new SLF4JLogLevelInfo();

    /**
     * Corresponds to methods {@link Logger#isDebugEnabled()}, Logger#debug(String), etc.
     */
    public static final SLF4JLogLevel DEBUG = new SLF4JLogLevelDebug();

    /**
     * Corresponds to methods {@link Logger#isTraceEnabled()}, Logger#trace(String), etc.
     */
    public static final SLF4JLogLevel TRACE = new SLF4JLogLevelTrace();

}
