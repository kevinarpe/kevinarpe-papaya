package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Enabling this class causing NPE on JVM start-up.  Do we REALLY need this class?
// If no, delete it, and AbstractSLF4JLogLevel
public enum SLF4JLogLevelEnum {

    OFF(SLF4JLogLevel.OFF),
    ERROR(SLF4JLogLevel.ERROR),
    WARN(SLF4JLogLevel.WARN),
    INFO(SLF4JLogLevel.INFO),
    DEBUG(SLF4JLogLevel.DEBUG),
    TRACE(SLF4JLogLevel.TRACE),
    ;

    private final SLF4JLogLevel _logLevel;

    private SLF4JLogLevelEnum(SLF4JLogLevel logLevel) {
        _logLevel = ObjectArgs.checkNotNull(logLevel, "logLevel");
    }

    public SLF4JLogLevel getLogLevel() {
        return _logLevel;
    }
}
