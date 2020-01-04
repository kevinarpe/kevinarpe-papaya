package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.IOException;
import java.io.InputStream;

/**
 * Static helper methods for {@link InputStream}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class InputStreamUtils {

    // Disable default constructor
    private InputStreamUtils() {
    }
    
    /**
     * Calls {@link InputStream#read()} and checks the result has range: [-1, 255].  At first
     * glance, this method appears nearly useless, save for the fact that the valid range of values
     * from method {@link InputStream#read()} cannot be guaranteed by the interface
     * {@link InputStream}.
     * <p>
     * Most primitive types in Java are signed (except {@code char}).  The range for {@code byte}
     * is -128 to 127.  This method returns an unsigned byte stored in a signed integer type.  Be
     * careful when casting between {@code byte} and {@code integer}.  For example, if this method
     * returns 255, casting to byte will have value -128.  If then cast to an integer, the value
     * will by -128, not 255.
     * 
     * @param in
     *        stream handle from which to read the next byte
     *        
     * @return the next byte of data, or -1 if the end of the stream is reached
     * 
     * @throws NullPointerException
     *         if {@code in} is {@code null}
     * @throws IOException
     *         if any I/O error occurs
     * 
     * @see InputStream#read()
     */
    public static int checkedReadNextUnsignedByte(InputStream in)
    throws IOException {
        ObjectArgs.checkNotNull(in, "in");
        
        int x = in.read();
        if (x < -1 || x > 255) {
            String msg = String.format(
                "Read one byte from %s:"
                + "%n\tValue (%d) is outside valid range: -1 to 255",
                in.getClass().getCanonicalName(),
                x);
            throw new IllegalStateException(msg);
        }
        return x;
    }
}
