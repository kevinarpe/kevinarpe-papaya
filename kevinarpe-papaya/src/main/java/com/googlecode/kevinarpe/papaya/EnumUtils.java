package com.googlecode.kevinarpe.papaya;

import com.google.common.base.Joiner;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public class EnumUtils {

    public static <TEnum extends Enum<TEnum>>
    TEnum valueOf(Class<TEnum> enumType, String name) {
        // TODO: Create test for isValidJavaToken -- already exists in Apache libs?
        try {
            TEnum value = _fastValueOf(enumType, name);
            return value;
        }
        catch (Exception e) {
            Enum[] enumArr = enumType.getEnumConstants();
            String enumStringList = _toStringList(enumArr);
            String msg = String.format(
                "Failed to find constant '%s' in enum type %s%nAvailable constants: %s",
                name, enumType.getName(), enumStringList);
            throw new IllegalArgumentException(msg, e);
        }
    }

    private static String _toStringList(Enum[] enumArr) {
        if (0 == enumArr.length) {
            return "(none)";
        }
        String[] enumNameArr = new String[enumArr.length];
        for (int i = 0; i < enumArr.length; ++i) {
            Enum anEnum = enumArr[i];
            String name = anEnum.name();
            enumNameArr[i] = name;
        }
        // TODO: Make Quoter/QuotingJoiner with prefix -- ', `, ( -- and suffix -- ', ', ) -- ?
        // Allow valueToString override to convert value toString(), e.g., Enum.name()
        String x = Joiner.on("', '").join(enumNameArr);
        x = "'" + x + "'";
        return x;
    }

    private static <TEnum extends Enum<TEnum>>
    TEnum _fastValueOf(Class<TEnum> enumType, String name) {
        // TODO: Create test for isValidJavaToken -- already exists in Apache libs?
        ObjectArgs.checkNotNull(enumType, "enumType");
        StringArgs.checkNotEmptyOrWhitespace(name, "name");

        TEnum value = Enum.valueOf(enumType, name);
        return value;
    }

    public static <TEnum extends Enum<TEnum>>
    TEnum tryValueOf(Class<TEnum> enumType, String name) {
        try {
            TEnum value = _fastValueOf(enumType, name);
            return value;
        }
        catch (Exception ignore) {
            return null;
        }
    }
}
