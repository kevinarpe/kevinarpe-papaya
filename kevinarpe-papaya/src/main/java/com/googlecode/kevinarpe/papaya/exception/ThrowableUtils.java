package com.googlecode.kevinarpe.papaya.exception;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;

/**
 * Static methods for {@link Throwable}s.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class ThrowableUtils {

    // Disable default constructor
    private ThrowableUtils() {
    }
    
    /**
     * Calculates the hash code of any {@code Throwable}.  The stack trace may be optionally
     * included which may give surprising results.  (It is harder than most realise to create two
     * exceptions with exact same stack trace.)
     *
     * @param throwable
     *        any {@link Throwable} reference, including {@code null}
     * @param includeStackTrace
     *        if {@link IncludeStackTrace#YES}, stack trace is included in the calculation.
     *
     * @return if input is {@code null}, then 0 (zero), else the hash code, which may or may not
     *         include the stack trace
     *
     * @throws NullPointerException if {@code includeStackTrace} is {@code null}
     */
    public static int hashCode(Throwable throwable, IncludeStackTrace includeStackTrace) {
        ObjectArgs.checkNotNull(includeStackTrace, "includeStackTrace");

        if (null == throwable) {
            return 0;
        }
        int result = 1;
        String message = throwable.getMessage();
        result = 31 * result + (null == message ? 0 : message.hashCode());
        String localMessage = throwable.getLocalizedMessage();
        if (message != localMessage) {
            result = 31 * result + (null == localMessage ? 0 : localMessage.hashCode());
        }
        if (IncludeStackTrace.YES == includeStackTrace) {
            Object[] stackTrace = throwable.getStackTrace();
            result = 31 * result + Arrays.hashCode(stackTrace);
        }
        Throwable cause = throwable.getCause();
        if (null != cause) {
            result = 31 * result + hashCode(cause, includeStackTrace);
        }
        return result;
    }
    
    /**
     * Compare two {@code Throwable} references for equality.  Inputs of {@code null} (none, one,
     * or both) are handled correctly.  The stack trace may be optionally included which may give
     * surprising results.  (It is harder than most realise to create two exceptions with exact same
     * stack trace.)
     *
     * @param throwable1
     *        first {@link Throwable} for comparison
     * @param throwable2
     *        second {@link Throwable} for comparison
     * @param includeStackTrace
     *        if {@link IncludeStackTrace#YES}, stack trace is included in the calculation.
     *
     * @return
     * <ul>
     *     <li>{@code true} if both {@code Throwables} are {@code null}</li>
     *     <li>{@code true} if both {@code Throwables} are not {@code null} and equals by attribute
     *     (message, cause, etc.)</li>
     *     <li>{@code false} for all other cases</li>
     * </ul>
     *
     * @throws NullPointerException if {@code includeStackTrace} is {@code null}
     */
    public static boolean equals(
            Throwable throwable1, Throwable throwable2, IncludeStackTrace includeStackTrace) {
        ObjectArgs.checkNotNull(includeStackTrace, "includeStackTrace");

        if (throwable1 == throwable2) {
            return true;
        }
        if ((null == throwable1 && null != throwable2)
                || (null != throwable1 && null == throwable2)) {
            return false;
        }
        String message1 = throwable1.getMessage();
        String localMessage1 = throwable1.getLocalizedMessage();
        String message2 = throwable2.getMessage();
        String localMessage2 = throwable2.getLocalizedMessage();
        boolean result =
            Objects.equal(message1, message2)
            && ((message1 == localMessage1 && message2 == localMessage2)
                    || Objects.equal(throwable1.getLocalizedMessage(), throwable2.getLocalizedMessage()))
            && (IncludeStackTrace.NO == includeStackTrace
                    || Arrays.equals(throwable1.getStackTrace(), throwable2.getStackTrace()))
            && equals(throwable1.getCause(), throwable2.getCause(), includeStackTrace);
        return result;
    }
    
    /**
     * Creates a descriptive string useful for debugging from a {@code Throwable} reference.
     * Causes are fully recursed via {@link Throwable#getCause()}.
     * 
     * @param throwable
     *        non-{@code null} Throwable reference
     * 
     * @return string to describe the Throwable reference
     * 
     * @throws NullPointerException
     *         if {@code throwable} is {@code null}
     */
    @NotFullyTested
    public static String toString(Throwable throwable) {
        ObjectArgs.checkNotNull(throwable, "throwable");
        
        String x = String.format("%n\tgetMessage()='%s'", throwable.getMessage());
        String indent = "\t";
        for (Throwable cause = throwable.getCause()
                ; null != cause
                ; cause = cause.getCause(), indent = indent.concat("\t")) {
            x = x.concat(String.format(",%n%sgetCause()='%s'", indent, cause));
        }
        x = x.concat(String.format("%n\t]"));
        return x;
    }
}
