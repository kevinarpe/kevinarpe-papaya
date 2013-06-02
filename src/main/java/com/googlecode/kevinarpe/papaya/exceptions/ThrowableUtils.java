package com.googlecode.kevinarpe.papaya.exceptions;

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

final class ThrowableUtils {

    static int hashCode(Throwable t) {
        int x = _hashCode(t, false);
        return x;
    }
    
    static int hashCodeExcludingStackTrace(Throwable t) {
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
            result = 31 * result + Objects.hashCode((Object[]) t.getStackTrace());
        }
        result = 31 * result + hashCode(t.getCause());
        return result;
    }
    
    static boolean equals(Throwable t1, Throwable t2) {
        boolean x = _equals(t1, t2, false);
        return x;
    }
    
    static boolean equalsExcludingStackTrace(Throwable t1, Throwable t2) {
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
    
    static String toString(Throwable t) {
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
