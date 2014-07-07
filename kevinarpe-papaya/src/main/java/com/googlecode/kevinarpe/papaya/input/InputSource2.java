package com.googlecode.kevinarpe.papaya.input;

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

import com.googlecode.kevinarpe.papaya.properties.PropertiesLoader;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.Reader;

/**
 * Union of byte- and character-based streams to be used as input.  Inspired by David Megginson's
 * {@link InputSource} from SAX, but named with a suffix '2' to prevent name clashes.
 * <p>
 * Exactly one byte- or one character-based stream is encapsulated by each instance.  It is never
 * safe to reuse an input source, as Java byte- and character-streams cannot always reliably
 * be reset (to offset zero).
 * <p>
 * When possible, implementations are strongly recommended to override {@link Object#toString()} to
 * provide a human-readable description of the source for logging and debugging purposes.
 * <p>
 * About {@link Object#hashCode()} and {@link Object#equals(Object)}: They are normally not
 * overridden by implementations as not all streams are guaranteed to be seekable (rewindable) in
 * Java.  As a result, instances of this class should not be re-used.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see InputSource
 * @see StringInputSource2
 * @see FileInputSource2
 * @see ClassResourceInputSource2
 * @see PropertiesLoader
 */
public interface InputSource2 {

    /**
     * Retrieves the byte-based stream.  If this stream is {@code null}, the character-based stream
     * must not be {@code null}.
     *
     * @see #getCharacterStream()
     */
    InputStream getByteStream();

    /**
     * Retrieves the character-based stream.  If this stream is {@code null}, the byte-based stream
     * must not be {@code null}.
     *
     * @see #getByteStream()
     */
    Reader getCharacterStream();
}
