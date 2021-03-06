package com.googlecode.kevinarpe.papaya.appendable;

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

import com.googlecode.kevinarpe.papaya.container.ByteArraySimpleBuilder;
import com.googlecode.kevinarpe.papaya.process.ProcessBuilder2;
import com.googlecode.kevinarpe.papaya.process.ProcessOutputStreamSettings;

import java.io.IOException;

/**
 * This is a byte-based-version of {@link Appendable}, which is character-based.  If STDOUT or
 * STDERR are expect to produce non-character data, use
 * {@link ProcessOutputStreamSettings#byteCallback(ByteAppendable)}
 * via:
 * <ul>
 *   <li>{@link ProcessBuilder2#stdoutSettings()}</li>
 *   <li>{@link ProcessBuilder2#stderrSettings()}</li>
 * </ul>
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ByteAppendable {
    
    /**
     * If you need to accumulate the incoming bytes, consider using {@link ByteArraySimpleBuilder}.
     * 
     * @param byteArr the byte array to append
     * @throws IOException if {@code byteArr} cannot be consumed -- I/O error
     */
    void append(byte[] byteArr)
    throws IOException;
}
