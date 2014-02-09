package com.googlecode.kevinarpe.papaya.logging;

import com.googlecode.kevinarpe.papaya.EnumUtils;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public abstract class AbstractSLF4JLogLevel
implements SLF4JLogLevel {

    private final String _theEnumName;
    private SLF4JLogLevelEnum _theEnum;

    public AbstractSLF4JLogLevel(String theEnumName) {
        _theEnumName = StringArgs.checkNotEmptyOrWhitespace(theEnumName, "theEnumName");
    }

    @Override
    public final SLF4JLogLevelEnum getEnum() {
        if (null == _theEnum) {
            _theEnum = EnumUtils.valueOf(SLF4JLogLevelEnum.class, _theEnumName);
        }
        return _theEnum;
    }
}
