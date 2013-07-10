package com.googlecode.kevinarpe.papaya;

import java.io.IOException;

import org.joe_e.array.ByteArray;

/**
 * This is a byte-based-version of {@link Appendable}, which is character-based.  If STDOUT or
 * STDERR are expect to produce non-character data, use
 * {@link ProcessBuilder2#stdoutByteCallback(ByteAppendable)} or
 * {@link ProcessBuilder2#stderrByteCallback(ByteAppendable)}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ByteAppendable {
    
    /**
     * If you need to accumulate the incoming bytes, consider using {@link ByteArray} (via
     * {@link ByteArray#builder()}).
     * 
     * @param byteArr the byte array to append
     * @throws IOException if {@code byteArr} cannot be consumed -- I/O error
     */
    void append(byte[] byteArr)
    throws IOException;
}