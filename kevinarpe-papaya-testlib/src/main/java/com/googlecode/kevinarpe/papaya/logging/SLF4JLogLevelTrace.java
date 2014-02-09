package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
final class SLF4JLogLevelTrace
//implements SLF4JLogLevel {
extends AbstractSLF4JLogLevel {

//    public static final SLF4JLogLevelEnum ENUM = SLF4JLogLevelEnum.TRACE;

    public SLF4JLogLevelTrace() {
        super("TRACE");
    }

    /**
     * Calls {@link Logger#isTraceEnabled()}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger) {
        ObjectArgs.checkNotNull(logger, "logger");

        boolean b = logger.isTraceEnabled();
        return b;
    }

    /**
     * Calls {@link Logger#trace(String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(msg);
    }

    /**
     * Calls {@link Logger#trace(String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(format, arg);
    }

    /**
     * Calls {@link Logger#trace(String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(format, argArr);
    }

    /**
     * Calls {@link Logger#trace(String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(msg, t);
    }

    /**
     * Calls {@link Logger#isTraceEnabled(Marker)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger, Marker marker) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        boolean b = logger.isTraceEnabled(marker);
        return b;
    }

    /**
     * Calls {@link Logger#trace(Marker, String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, msg);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, format, arg);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, format, argArr);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(msg, t);
    }
}
