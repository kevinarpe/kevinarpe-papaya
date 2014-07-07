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

import com.google.common.base.Charsets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Encapsulates the characters of a file.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ClassResourceInputSource2
 * @see StringInputSource2
 */
@FullyTested
public final class FileInputSource2
implements InputSource2 {

    private final File _filePath;
    private final InputStreamReader _inputStreamReader;

    /**
     * Constructs a new {@code InputStreamReader} with the default charset.
     * <p>
     * It is strongly recommended to always specify the charset, e.g., {@link Charsets#UTF_8}.
     *
     * @param filePath
     *        path to file
     *
     * @throws NullPointerException
     *         if {@code path} is {@code null}
     * @throws PathException
     * <ul>
     *     <li>with reason {@link PathExceptionReason#PATH_DOES_NOT_EXIST}
     *         if {@code path} does not exist</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_DIRECTORY}
     *         if {@code path} exists, but is not a file</li>
     * </ul>
     *
     * @see #FileInputSource2(File, Charset)
     */
    public FileInputSource2(File filePath)
    throws PathException {
        this(filePath, new InputStreamReader(_newFileInputStream(filePath)));
    }

    /**
     * Constructs a new {@code InputStreamReader} with a custom charset.
     *
     * @param filePath
     *        path to file
     * @param charset
     *        custom charset, e.g., {@link Charsets#UTF_8}
     *
     * @throws NullPointerException
     *         if {@code path} or {@code charset} is {@code null}
     * @throws PathException
     * <ul>
     *     <li>with reason {@link PathExceptionReason#PATH_DOES_NOT_EXIST}
     *         if {@code path} does not exist</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_DIRECTORY}
     *         if {@code path} exists, but is not a file</li>
     * </ul>
     *
     * @see #FileInputSource2(File)
     */
    public FileInputSource2(File filePath, Charset charset)
    throws PathException {
        this(
            filePath,
            new InputStreamReader(
                _newFileInputStream(filePath),
                ObjectArgs.checkNotNull(charset, "charset")));
    }

    /**
     * Constructs a new {@code InputStreamReader} with a custom charset decoder.  This may needed to
     * decode pre-Unicode charsets, like Shift-JIS and and Big5/HKSCS.
     *
     * @param filePath
     *        path to file
     * @param charsetDecoder
     *        customer charset decoder
     *
     * @throws NullPointerException
     *         if {@code path} or {@code charsetDecoder} is {@code null}
     * @throws PathException
     * <ul>
     *     <li>with reason {@link PathExceptionReason#PATH_DOES_NOT_EXIST}
     *         if {@code path} does not exist</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_DIRECTORY}
     *         if {@code path} exists, but is not a file</li>
     * </ul>
     */
    public FileInputSource2(File filePath, CharsetDecoder charsetDecoder)
    throws PathException {
        this(
            filePath,
            new InputStreamReader(
                _newFileInputStream(filePath),
                ObjectArgs.checkNotNull(charsetDecoder, "charsetDecoder")));
    }

    /**
     * Constructs a new {@code InputStreamReader} with a custom charset name.
     *
     * @param filePath
     *        path to file
     * @param charsetName
     *        name of custom charset, e.g., "UTF-8"
     *
     * @throws NullPointerException
     *         if {@code path} or {@code charsetName} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code charsetName} is empty or only whitespace
     * @throws PathException
     * <ul>
     *     <li>with reason {@link PathExceptionReason#PATH_DOES_NOT_EXIST}
     *         if {@code path} does not exist</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_DIRECTORY}
     *         if {@code path} exists, but is not a file</li>
     * </ul>
     *
     * @see #FileInputSource2(File, Charset)
     */
    public FileInputSource2(File filePath, String charsetName)
    throws PathException, UnsupportedEncodingException {
        this(filePath,
            new InputStreamReader(
                _newFileInputStream(filePath),
                StringArgs.checkNotEmptyOrWhitespace(charsetName, "charsetName")));
    }

    FileInputSource2(File filePath, InputStreamReader inputStreamReader) {
        _filePath = filePath;
        _inputStreamReader = inputStreamReader;
    }

    private static FileInputStream _newFileInputStream(File filePath)
    throws PathException {
        PathArgs.checkFileExists(filePath, "filePath");
        try {
            FileInputStream x = new FileInputStream(filePath);
            return x;
        }
        catch (FileNotFoundException e) {
            String msg = String.format("Failed to open file: '%s'", filePath.getAbsolutePath());
            throw new PathException(PathExceptionReason.UNKNOWN, filePath, (File) null, msg, e);
        }
    }

    /**
     * Always {@code null}.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public InputStream getByteStream() {
        return null;
    }

    /**
     * Always non-{@code null}.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public Reader getCharacterStream() {
        return _inputStreamReader;
    }

    @Override
    public String toString() {
        String x = String.format("File: '%s'", _filePath.getAbsolutePath());
        return x;
    }
}
