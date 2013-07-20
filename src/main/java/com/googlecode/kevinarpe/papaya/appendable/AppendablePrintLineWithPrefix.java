package com.googlecode.kevinarpe.papaya.appendable;

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

import java.io.IOException;
import java.io.PrintStream;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.process.ProcessBuilder2;
import com.googlecode.kevinarpe.papaya.process.ProcessOutputStreamSettings;

/**
 * This is a class is offered as a sample implementation of {@link AbstractSimplifiedAppendable},
 * which implements {@link Appendable}.  It may be used as a character-based callback in
 * {@link ProcessBuilder2} via {@link ProcessBuilder2#stdoutSettings()}, then
 * {@link ProcessOutputStreamSettings#charCallback(Appendable)}.
 * <p>
 * Instances of this class are constructed with a {@link PrintStream}, such as {@link System#out},
 * and a prefix, such as {@code "STDOUT: "}.  When {@link #append(CharSequence)} is called, the
 * prefix, then the text, is printed via {@link PrintStream#println(String)}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public class AppendablePrintLineWithPrefix
extends AbstractSimplifiedAppendable {
    
    private final PrintStream _stream;
    private final String _prefix;
    
    /**
     * @param stream
     * <ul>
     *   <li>{@link PrintStream} to write via {@link PrintStream#println(String)}</li>
     *   <li>Must not be {@code null}</li>
     *   <li>Example: {@link System#out} or {@link System#err}</li>
     * </ul>
     * @param prefix
     * <ul>
     *   <li>Prepended to each {@link CharSequence} received by
     *   {@link #append(CharSequence)}</li>
     *   <li>Must not be {@code null}, but can be empty</li>
     *   <li>Example: {@code "STDOUT: "} or {@code "STDERR: "}</li>
     * </ul>
     */
    public AppendablePrintLineWithPrefix(PrintStream stream, String prefix) {
        _stream = ObjectArgs.checkNotNull(stream, "stream");
        _prefix = ObjectArgs.checkNotNull(prefix, "prefix");
    }

    /**
     * Incoming text has a prefix added, then passed to
     * {@link PrintStream#println(String)}.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public Appendable append(CharSequence csq)
    throws IOException {
        CharSequence x = (_prefix.isEmpty() ? csq : _prefix + csq);
        _stream.println(x);
        return this;
    }
}
