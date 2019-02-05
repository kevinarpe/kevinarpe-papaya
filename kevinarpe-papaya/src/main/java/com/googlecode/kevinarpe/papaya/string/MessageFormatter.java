package com.googlecode.kevinarpe.papaya.string;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;

/**
 * See {@link MessageFormatterImpl} for version that <b>does not</b> throw.
 * <br>
 * See {@link ThrowingMessageFormatterImpl} for version that <b>does</b> throw.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface MessageFormatter {

    /**
     * Formats a human-readable message similar to sprintf() using {@link String#format(String, Object...)}.  An example
     * of a human-readable message is a log message or a user interface.  If the formatting is imperfect, a runtime
     * exception should not be thrown!  Unfortunately, the default Java implementation of {@code String.format()} throws
     * a runtime exception on failure.  The intent of this interface is only to format <i>human-readable messages</i>,
     * thus it should never be used for machine-readable messages.  (Instead, use {@link StringBuilder}.)
     * <p>
     * Non-production environments should use a throwing implementation to find invalid calls, but production
     * environments should use a non-throwing implementation.
     * <p>
     * Tip: Use {@code "%n"} for platform-specific line separator (newline).
     *
     * @throws NullPointerException
     *         if {@code format} or {@code argArr} is {@code null}
     *         <br>To be clear: Parameter {@code argArr} <em>may</em> contain {@code null} values.
     *
     * @see MessageFormatterImpl
     * @see ThrowingMessageFormatterImpl
     * @see MessageFormatters#formatNonEmptyOrWhitespace(MessageFormatter, String, Object...)
     */
    @EmptyStringAllowed
    String
    format(@EmptyStringAllowed
           String format,
           @EmptyContainerAllowed
           @NullableElements
           Object... argArr);
}
