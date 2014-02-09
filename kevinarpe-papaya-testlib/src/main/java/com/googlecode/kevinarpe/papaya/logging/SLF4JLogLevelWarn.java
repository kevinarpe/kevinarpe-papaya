package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
final class SLF4JLogLevelWarn
//implements SLF4JLogLevel {
extends AbstractSLF4JLogLevel {

//    public static final SLF4JLogLevelEnum ENUM = SLF4JLogLevelEnum.WARN;

    public SLF4JLogLevelWarn() {
        super("WARN");
    }

    /**
     * Calls {@link Logger#isWarnEnabled()}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger) {
        ObjectArgs.checkNotNull(logger, "logger");

        boolean b = logger.isWarnEnabled();
        return b;
    }

    /**
     * Calls {@link Logger#warn(String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.warn(msg);
    }

    /**
     * Calls {@link Logger#warn(String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.warn(format, arg);
    }

    /**
     * Calls {@link Logger#warn(String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.warn(format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#warn(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.warn(format, argArr);
    }

    /**
     * Calls {@link Logger#warn(String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.warn(msg, t);
    }

    /**
     * Calls {@link Logger#isWarnEnabled(Marker)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger, Marker marker) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        boolean b = logger.isWarnEnabled(marker);
        return b;
    }

    /**
     * Calls {@link Logger#warn(Marker, String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.warn(marker, msg);
    }

    /**
     * Calls {@link Logger#warn(Marker, String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.warn(marker, format, arg);
    }

    /**
     * Calls {@link Logger#warn(Marker, String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.warn(marker, format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#warn(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.warn(marker, format, argArr);
    }

    /**
     * Calls {@link Logger#warn(Marker, String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.warn(msg, t);
    }
}
