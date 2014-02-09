package com.googlecode.kevinarpe.papaya.logging;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
// TODO: Really need to expose this class?  Probably not.  And all others like it.
// Package-private is OK.
final class SLF4JLogLevelOff
//implements SLF4JLogLevel {
extends AbstractSLF4JLogLevel {

//    public static final SLF4JLogLevelEnum ENUM = SLF4JLogLevelEnum.OFF;

    public SLF4JLogLevelOff() {
        super("OFF");
    }

    /**
     * @return always {@code false}
     */
    @Override
    public boolean isEnabled(Logger logger) {
        return false;
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String msg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String format, Object arg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String format, Object arg1, Object arg2) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String format, Object... argArr) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String msg, Throwable t) {
    }

    /**
     * @return always {@code false}
     */
    @Override
    public boolean isEnabled(Logger logger, Marker marker) {
        return false;
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String msg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg1, Object arg2) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object... argArr) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String msg, Throwable t) {
    }
}
