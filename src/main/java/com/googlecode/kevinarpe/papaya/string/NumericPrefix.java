package com.googlecode.kevinarpe.papaya.string;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumericPrefix {

    private static final Pattern PATTERN = Pattern.compile("^(\\d+)");
    public static final long NOT_FOUND = -1;

    private final String _input;
    private final long _value;

    public NumericPrefix(String str) {
        _input = ObjectArgs.checkNotNull(str, "str");

        Matcher matcher = PATTERN.matcher(str);
        long value = NOT_FOUND;
        if (matcher.find()) {
            value = Long.parseLong(matcher.group());
        }
        _value = value;
    }

    public String getInput() {
        return _input;
    }

    public boolean hasNumericValue() {
        return _value != NOT_FOUND;
    }

    public long getNumericValue() {
        if (!hasNumericValue()) {
            throw new IllegalStateException(String.format(
                "String does not have a numeric prefix: '%s'", _input));
        }
        return _value;
    }
}
