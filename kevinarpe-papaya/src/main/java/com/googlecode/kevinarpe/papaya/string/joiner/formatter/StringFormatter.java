package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Formatter;

/**
 * Formats a value using {@code String.format()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StatelessObject
 * @see Formatter2
 * @see String#format(String, Object...)
 */
@FullyTested
public final class StringFormatter
extends StatelessObject
implements Formatter2 {

    private final String _format;

    /**
     * Creates a new formatter with a format string.
     *
     * @param format
     * <ul>
     *     <li>Format string for {@link Formatter}</li>
     *     <li>Must not be {@code null}, empty, or only whitespace</li>
     *     <li>Example 1: {@code "%.6f"}</li>
     *     <li>Example 2: {@code "[%s]"}</li>
     *     <li>Example 3: {@code "[%1$s] '%1$s'"}</li>
     * </ul>
     *
     * @throws IllegalArgumentException
     *         if {@link String#format(String, Object...)} fails
     *
     * @see Formatter
     */
    public StringFormatter(String format) {
        _format = StringArgs.checkNotEmptyOrWhitespace(format, "format");

        _format("test value", _format, "test");
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException
     *         if {@link String#format(String, Object...)} fails
     */
    @Override
    public String format(Object value) {
        String x = _format("value", _format, value);
        return x;
    }

    private static String _format(String description, String format, Object value) {
        try {
            String x = String.format(format, value);
            return x;
        }
        catch (Exception e) {
            String msg = String.format(
                "Failed to format %s: String.format([%s], [%s])", description, format, value);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
