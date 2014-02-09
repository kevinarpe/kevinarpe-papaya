package com.googlecode.kevinarpe.papaya.logging;

import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface SLF4JLevelLogger {

    boolean isEnabled();
    void log(String msg);
    void log(String format, Object arg);
    void log(String format, Object arg1, Object arg2);
    void log(String format, Object... argArr);
    void log(String msg, Throwable t);

    boolean isEnabled(Marker marker);
    void log(Marker marker, String msg);
    void log(Marker marker, String format, Object arg);
    void log(Marker marker, String format, Object arg1, Object arg2);
    void log(Marker marker, String format, Object... argArr);
    void log(Marker marker, String msg, Throwable t);
}
