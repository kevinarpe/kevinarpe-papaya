package com.googlecode.kevinarpe.papaya.exception;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.Arrays;

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * Static methods for {@link Throwable}s.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class ThrowableUtils {

    /**
     * Calculates the hash code of any throwable, including {@code null}.  Stack trace is included
     * in the calculation, which may give surprising results.  (It is harder than most realise to
     * create two exceptions with the exact same stack trace.)  To exclude stack trace in the
     * calculation, see {@link #hashCodeExcludingStackTrace(Throwable)}.
     * 
     * @param t any throwable reference, including {@code null}
     * 
     * @return if input is {@code null}, then 0 (zero), else the hash code, including the
     *         stack trace.
     * 
     * @see #hashCodeExcludingStackTrace(Throwable)
     */
    @NotFullyTested
    public static int hashCode(Throwable t) {
        int x = _hashCode(t, false);
        return x;
    }
    
    /**
     * Calculates the hash code of any throwable, including {@code null}.  Stack trace is
     * <b>not</b> included in the calculation.  To include stack trace in the calculation, see
     * {@link #hashCode(Throwable)}.
     * 
     * @param t any throwable reference, including {@code null}
     * 
     * @return if input is {@code null}, then 0 (zero), else the hash code, <b>not</b> including
     *         the stack trace.
     * 
     * @see #hashCodeExcludingStackTrace(Throwable)
     */
    @NotFullyTested
    public static int hashCodeExcludingStackTrace(Throwable t) {
        int x = _hashCode(t, true);
        return x;
    }

    private static int _hashCode(Throwable t, boolean excludeStackTrace) {
        if (null == t) {
            return 0;
        }
        int result = 1;
        String message = t.getMessage();
        result = 31 * result + (null == message ? 0 : message.hashCode());
        String localMessage = t.getLocalizedMessage();
        if (message != localMessage) {
            result = 31 * result + (null == localMessage ? 0 : localMessage.hashCode());
        }
        if (!excludeStackTrace) {
            Object[] stackTrace = t.getStackTrace();
            result = 31 * result + Arrays.hashCode(stackTrace);
        }
        Throwable cause = t.getCause();
        if (null != cause) {
            result = 31 * result + hashCode(cause);
        }
        return result;
    }
    
    /**
     * Compare two {@link Throwable} references for equality.  Inputs of {@code null} (none, one,
     * or both) are handled correctly.  Stack trace is included in the comparison, which may give
     * surprising results.  (It is harder than most realise to create two exceptions with the exact
     * same stack trace.)  To exclude stack trace in the comparison, see
     * {@link #equalsExcludingStackTrace(Throwable, Throwable)}.
     * 
     * @param t1 first Throwable for comparison
     * @param t2 second Throwable for comparison
     * 
     * @return {@code true} if the two Throwable references are equal, including stack traces.  If
     *         <b>both</b> input references are {@code null}, the result is also {@code true}.
     */
    @NotFullyTested
    public static boolean equals(Throwable t1, Throwable t2) {
        boolean x = _equals(t1, t2, false);
        return x;
    }
    
    /**
     * Compare two {@link Throwable} references for equality.  Inputs of {@code null} (none, one,
     * or both) are handled correctly.  Stack trace is <b>not</b> included in the comparison.  To
     * include stack trace in the comparison, see
     * {@link #equals(Throwable, Throwable)}.
     * 
     * @param t1 first Throwable for comparison
     * @param t2 second Throwable for comparison
     * 
     * @return {@code true} if the two Throwable references are equal, excluding stack traces.  If
     *         <b>both</b> input references are {@code null}, the result is also {@code true}.
     */
    @NotFullyTested
    public static boolean equalsExcludingStackTrace(Throwable t1, Throwable t2) {
        boolean x = _equals(t1, t2, true);
        return x;
    }
    
    private static boolean _equals(Throwable t1, Throwable t2, boolean excludeStackTrace) {
        if (t1 == t2) {
            return true;
        }
        if ((null == t1 && null != t2) || (null != t1 && null == t2)) {
            return false;
        }
        String message1 = t1.getMessage();
        String localMessage1 = t1.getLocalizedMessage();
        String message2 = t2.getMessage();
        String localMessage2 = t2.getLocalizedMessage();
        boolean result =
            Objects.equal(message1, message2)
            && ((message1 == localMessage1 && message2 == localMessage2)
                    || Objects.equal(t1.getLocalizedMessage(), t2.getLocalizedMessage()))
            && (excludeStackTrace || Arrays.equals(t1.getStackTrace(), t2.getStackTrace()))
            && equals(t1.getCause(), t2.getCause());
        return result;
    }
    
    /**
     * Creates a descriptive string useful for debugging from a {@link Throwable} reference.
     * Causes are fully recursed via {@link Throwable#getCause()}.
     * 
     * @param t non-{@code null} Throwable reference
     * 
     * @return string to describe the Throwable reference
     * 
     * @throws NullPointerException
     *         if {@code t} is {@code null}
     */
    @NotFullyTested
    public static String toString(Throwable t) {
        ObjectArgs.checkNotNull(t, "t");
        
        String x = String.format("%n\tgetMessage()='%s'", t.getMessage());
        String indent = "\t";
        for (Throwable cause = t.getCause()
                ; null != cause
                ; cause = cause.getCause(), indent = indent.concat("\t")) {
            x = x.concat(String.format(",%n%sgetCause()='%s'", indent, cause));
        }
        x = x.concat(String.format("%n\t]"));
        return x;
    }
}
