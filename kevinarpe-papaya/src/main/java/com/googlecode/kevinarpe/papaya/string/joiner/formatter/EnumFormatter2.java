package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EnumFormatter2
extends StatelessObject
implements Formatter2 {

    @Override
    public String format(Object value) {
        if (null == value) {
            return StringFormatterHelperImpl.NULL_VALUE_AS_STRING;
        }
        Enum<?> enumValue = ObjectArgs.checkCast(value.getClass(), Enum.class, "class of value");
        String x = enumValue.name();
        return x;
    }
}
