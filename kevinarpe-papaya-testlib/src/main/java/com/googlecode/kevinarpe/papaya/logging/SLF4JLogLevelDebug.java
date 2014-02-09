package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
final class SLF4JLogLevelDebug
//implements SLF4JLogLevel {
extends AbstractSLF4JLogLevel {

//    public static final SLF4JLogLevelEnum ENUM = SLF4JLogLevelEnum.DEBUG;

    public SLF4JLogLevelDebug() {
        super("DEBUG");
    }

    /**
     * Calls {@link Logger#isDebugEnabled()}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger) {
        ObjectArgs.checkNotNull(logger, "logger");

        boolean b = logger.isDebugEnabled();
        return b;
    }

    /**
     * Calls {@link Logger#debug(String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.debug(msg);
    }

    /**
     * Calls {@link Logger#debug(String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.debug(format, arg);
    }

    /**
     * Calls {@link Logger#debug(String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.debug(format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.debug(format, argArr);
    }

    /**
     * Calls {@link Logger#debug(String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.debug(msg, t);
    }

    /**
     * Calls {@link Logger#isDebugEnabled(Marker)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger, Marker marker) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        boolean b = logger.isDebugEnabled(marker);
        return b;
    }

    /**
     * Calls {@link Logger#debug(Marker, String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.debug(marker, msg);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.debug(marker, format, arg);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.debug(marker, format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.debug(marker, format, argArr);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.debug(msg, t);
    }
}
