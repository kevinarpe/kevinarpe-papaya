package com.googlecode.kevinarpe.papaya.input;

/*
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.ClassResourceNotFoundException;

import java.io.InputStream;
import java.io.Reader;

/**
 * Encapsulates the bytes of a class resource.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see InputSource2
 * @see FileInputSource2
 * @see StringInputSource2
 */
@FullyTested
public final class ClassResourceInputSource2
implements InputSource2 {

    private final Class<?> _clazz;
    private final String _pathname;
    private final InputStream _inputStream;

    /**
     * Constructs a new {@code InputSource2} for an class resource.
     *
     * @param clazz
     *        ref to class object to find resource
     * @param pathname
     *        relative path (without leading "/") to resource,
     *        e.g., {@code "proxy.properties"},
     *        <br>or absolute path (with leading "/") to resource,
     *        e.g., {@code "/proxy.properties"}
     *
     * @throws NullPointerException
     *         if {@code clazz} or {@code pathname} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code pathname} is empty
     * @throws ClassResourceNotFoundException
     *         if resource is not found
     *
     * @see PathArgs#checkClassResourceAsStreamExists(Class, String, String)
     */
    public ClassResourceInputSource2(Class<?> clazz, String pathname)
    throws ClassResourceNotFoundException {
        _clazz = clazz;
        _pathname = pathname;
        _inputStream = PathArgs.checkClassResourceAsStreamExists(clazz, pathname, "pathname");
    }

    /**
     * Always non-{@code null}.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public InputStream getByteStream() {
        return _inputStream;
    }

    /**
     * Always {@code null}.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public String toString() {
        String x = String.format("Class resource: %s -> '%s'", _clazz.getName(), _pathname);
        return x;
    }
}
