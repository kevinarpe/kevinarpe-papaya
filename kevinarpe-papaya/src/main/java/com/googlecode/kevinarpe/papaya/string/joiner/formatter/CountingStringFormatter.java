package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.util.Formatter;

/**
 * Formats a value using {@code String.format()} with an incrementing counter.  Unlike
 * {@link StringFormatter}, this class has state.  The next count is stored internally.  Be careful
 * not to reuse instances of this class.
 *
 * The only constructor is private; new instances are constructed via
 * {@link #withDefaultFirstCount(String)} or {@link #withFirstCount(String, int)}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Formatter2
 * @see #withDefaultFirstCount(String)
 * @see #withFirstCount(String, int)
 */
@FullyTested
public final class CountingStringFormatter
implements Formatter2 {

    public static final int DEFAULT_FIRST_COUNT = 1;

    private final int _firstCount;
    private int _countOffset;
    private final String _format;

    private CountingStringFormatter(String format, int firstCount) {
        _format = StringArgs.checkNotEmptyOrWhitespace(format, "format");

        _firstCount = firstCount;
        _countOffset = 0;

        _format("test value", _format, DEFAULT_FIRST_COUNT, "test");
    }

    /**
     * This is a convenience method that calls {@link #withFirstCount(String, int)} with
     * {@link #DEFAULT_FIRST_COUNT}.
     */
    public static CountingStringFormatter withDefaultFirstCount(String format) {
        CountingStringFormatter x = withFirstCount(format, DEFAULT_FIRST_COUNT);
        return x;
    }

    /**
     * Creates a new counting formatter.
     *
     * @param format
     * <ul>
     *     <li>Format string for {@link Formatter}</li>
     *     <li>Must not be {@code null}, empty, or only whitespace</li>
     *     <li>Example 1: {@code "[%.6f]"}</li>
     *     <li>Example 2: {@code "%d: [%s]"}</li>
     *     <li>Example 3: {@code "[%2$s] %1$d"}</li>
     * </ul>
     * @param firstCount
     *        the first number to use when counting.  May be any value (zero, negative, etc.)
     *
     * @return new formatter
     *
     * @throws IllegalArgumentException
     *         if {@link String#format(String, Object...)} fails
     *
     * @see #withDefaultFirstCount(String)
     * @see Formatter
     */
    public static CountingStringFormatter withFirstCount(String format, int firstCount) {
        CountingStringFormatter x = new CountingStringFormatter(format, firstCount);
        return x;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException
     *         if {@link String#format(String, Object...)} fails
     */
    @Override
    public String format(Object value) {
        String x = _format("value", _format, _firstCount + _countOffset, value);
        ++_countOffset;
        return x;
    }

    private static String _format(String description, String format, int count, Object value) {
        try {
            String x = String.format(format, count, value);
            return x;
        }
        catch (Exception e) {
            String msg =
                String.format("Failed to format %s: String.format([%s], %d, [%s])",
                    description, format, count, value);
            throw new IllegalArgumentException(msg, e);
        }
    }
}
