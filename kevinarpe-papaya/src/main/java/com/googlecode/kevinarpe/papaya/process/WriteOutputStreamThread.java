package com.googlecode.kevinarpe.papaya.process;

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

import java.io.IOException;
import java.io.OutputStream;
import com.googlecode.kevinarpe.papaya.AbstractThreadWithException;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * Used by {@link Process2} to write to STDIN stream of a child process.  Normally, there is no
 * need to use this class directly, or subclass it.  The class {@code Process2} uses this class
 * internally.  At first glance, using the term "OutputStream" in the class name seems wrong.
 * However, the input to the child process is captured in JDK as an <i>output</i> from the parent
 * process.
 * <p>
 * It is possible to customize the behavior of this class through subclassing and overriding
 * {@link Process2#createWriteOutputStreamThread(OutputStream, byte[])}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Process2
 */
@FullyTested
public class WriteOutputStreamThread
extends AbstractThreadWithException {
    
    private final OutputStream _outputStream;
    private final byte[] _byteArr;

    /**
     * Constructor.
     * 
     * @param outputStream
     *        handle to STDIN stream from child process
     * @param byteArr
     * <ul>
     *   <li>array of bytes to write to STDIN stream of child process</li>
     *   <li>this not copied, so the array <b>must</b> remain unmodified after construction</li>
     * </ul>
     * 
     * @throws NullPointerException
     *         if {@code outputStream} or {@code byteArr} is {@code null}
     */
    public WriteOutputStreamThread(
            OutputStream outputStream,
            byte[] byteArr) {
        _outputStream = ObjectArgs.checkNotNull(outputStream, "outputStream");
        _byteArr = ObjectArgs.checkNotNull(byteArr, "byteArr");
    }
    
    @Override
    public void runWithException()
    throws IOException {
        try {
            // This is synchronous / blocking.  For massive byte arrays (1 meg +), this will
            // not be received in a single read by the listening process.
            _outputStream.write(_byteArr);
        }
        catch (IOException e) {
            throw e;  // debug breakpoint
        }
        finally {
            try {
                _outputStream.close();
            }
            catch (IOException e) {
                // Intentionally ignore exception.
                @SuppressWarnings("unused")
                int dummy = 1;  // debug breakpoint
            }
        }
    }
    
    /**
     * For subclasses to access members.
     */
    protected OutputStream getOutputStream() {
        return _outputStream;
    }

    /**
     * For subclasses to access members.
     */
    protected byte[] getByteArr() {
        return _byteArr;
    }
}
