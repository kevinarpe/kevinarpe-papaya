package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

/**
 * Formats a value to a {code String}.  This interface is named {@code Formatter2} to prevent name
 * clashes with existing classes named {@code Formatter}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StringFormatter
 * @see CountingStringFormatter
 */
public interface Formatter2 {

    /**
     * Formats a single value as a {@code String}.
     *
     * @param value
     *        any value.  May be {@code null}.
     *
     * @return value formatted as a {@link String}
     */
    String format(Object value);
}
