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

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.googlecode.kevinarpe.papaya.AbstractThreadWithException;
import com.googlecode.kevinarpe.papaya.FuncUtils;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.appendable.ByteAppendable;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.container.ByteArraySimpleBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Used by {@link Process2} to read STDOUT or STDERR streams from a child process.  Normally, there
 * is no need to use this class directly, or subclass it.  The class {@code Process2} uses this
 * class internally.  At first glance, using the term "InputStream" in the class name seems wrong.
 * However, the output from the child process is captured in JDK as an <i>input</i> to the parent
 * process.
 * <p>
 * It is possible to customize the behavior of this class through subclassing and overriding
 * {@link Process2#createReadInputStreamThread(InputStream, ProcessOutputStreamSettings, String)}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Process2
 * @see #getDataAsByteArr()
 * @see #getDataAsString(Charset)
 */
@FullyTested
public class ReadInputStreamThread
extends AbstractThreadWithException {
    
    private static final int DEFAULT_BYTE_ARR_LENGTH = 8192;
    
    private final InputStream _inputStream;
    private final ProcessOutputStreamSettings _settings;
    private final String _streamName;
    private final ByteArraySimpleBuilder _byteArrBuilder;
    private final Appendable _optCharCallbackFromFactory;
    private final ByteAppendable _optByteCallbackFromFactory;

    /**
     * Constructor.
     * 
     * @param inputStream
     *        handle to STDOUT or STDERR stream from child process
     * @param settings
     *        reference to stream settings
     * @param streamName
     *        description for stream, e.g., {@code "STDOUT"}.  This only for debugging.
     * 
     * @throws NullPointerException
     *         if {@code inputStream}, {@code settings}, or {@code streamName} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code streamName} is empty or only
     *         {@linkplain Character#isWhitespace(char) whitespace}
     */
    public ReadInputStreamThread(
            InputStream inputStream,
            ProcessOutputStreamSettings settings,
            String streamName) {
        _inputStream = ObjectArgs.checkNotNull(inputStream, "inputStream");
        _settings = ObjectArgs.checkNotNull(settings, "settings");
        _streamName = StringArgs.checkNotEmptyOrWhitespace(streamName, "streamName");
        
        _byteArrBuilder = new ByteArraySimpleBuilder(DEFAULT_BYTE_ARR_LENGTH);
        FuncUtils.Func0<Appendable> ccFactory = _settings.charCallbackFactory();
        _optCharCallbackFromFactory = (null == ccFactory ? null : ccFactory.call());
        FuncUtils.Func0<ByteAppendable> bcFactory = _settings.byteCallbackFactory();
        _optByteCallbackFromFactory = (null == bcFactory ? null : bcFactory.call());
    }
    
    /**
     * @return retrieves the description for this child process output stream
     */
    public String getStreamName() {
        return _streamName;
    }
    
    /**
     * Copies all bytes from the byte buffer used to read the child process output stream.  This
     * method accesses the internal byte buffer in a thread-safe manner.
     * 
     * @return new array with all bytes from internal buffer.
     *         Never {@code null}, but may be empty.
     * 
     * @see #getDataAsByteArr()
     * @see #getDataAsString(Charset)
     */
    protected byte[] getByteArr() {
        synchronized (_byteArrBuilder) {
            byte[] x = _byteArrBuilder.toArray();
            return x;
        }
    }
    
    /**
     * Retrieves a copy of all bytes read from the child proces output stream.  This method is
     * thread-safe, so it may be called while the child process is outputting data -- not yet
     * terminated.
     * 
     * @return new array with all bytes from internal buffer
     *         Never {@code null}, but may be empty.
     * 
     * @see #getDataAsString(Charset)
     */
    public byte[] getDataAsByteArr() {
        byte[] x = getByteArr();
        return x;
    }
    
    /**
     * Retrieves a {@link String} created from all bytes read from the child process output stream.
     * This method is thread-safe, so it may be called while the child process is outputting data
     * -- not yet terminated.
     * 
     * @param optCs
     * <ul>
     *   <li>optional {@link Charset} to convert bytes to a {@code String}</li>
     *   <li>if {@code null}, the result of {@link Charset#defaultCharset()} is instead used</li>
     * </ul>
     * 
     * @return new {@code String} created from all bytes from internal buffer
     *         Never {@code null}, but may be empty.
     * 
     * @see #getDataAsByteArr()
     */
    public String getDataAsString(Charset optCs) {
        byte[] byteArr = getByteArr();
        if (null == optCs) {
            optCs = Charset.defaultCharset();
        }
        String s = new String(byteArr, optCs);
        return s;
    }
    
    @Override
    public void runWithException()
    throws IOException {
        byte[] buffer = new byte[8192];
        String lastText = "";
        while (true) {
            int readCount = _inputStream.read(buffer);
            if (-1 == readCount) {
                break;
            }
            if (_settings.isDataAccumulated()) {
                synchronized (_byteArrBuilder) {
                    int adjReadCount = readCount;
                    int maxByteCount = _settings.maxAccumulatedDataByteCount();
                    if (maxByteCount > 0) {
                        int maxReadCount = maxByteCount - _byteArrBuilder.length();
                        adjReadCount = Math.min(readCount, maxReadCount);
                    }
                    _byteArrBuilder.append(buffer, 0, adjReadCount);
                    //System.out.println(_byteArrBuilder.length());
                }
            }
            // Make a reference copy here, _settings.charCallback(), to be thread-safe.
            Appendable optCharCallback =
                (null != _optCharCallbackFromFactory
                    ? _optCharCallbackFromFactory : _settings.charCallback());
            if (null != optCharCallback) {
                Charset cs = _settings.charset();
                String s = new String(buffer, 0, readCount, cs);
                // Make a reference copy here to be thread-safe.
                Pattern optSplitRegex = _settings.splitRegex();
                if (null != optSplitRegex) {
                    String text = lastText.concat(s);
                    Iterable<String> partIter = Splitter.on(optSplitRegex).split(text);
                    ArrayList<String> partList = new ArrayList<String>();
                    Iterables.addAll(partList, partIter);
                    int partListSize = partList.size();
                    for (int i = 0; i < partListSize - 1; ++i) {
                        String part = partList.get(i);
                        optCharCallback.append(part);
                    }
                    lastText = partList.get(partListSize - 1);
                }
                else {
                    optCharCallback.append(s);
                }
            }
            // Make a reference copy here, _settings.byteCallback(),  to be thread-safe.
            ByteAppendable optByteCallback =
                (null != _optByteCallbackFromFactory
                    ? _optByteCallbackFromFactory : _settings.byteCallback());
            if (null != optByteCallback) {
                byte[] truncBuffer = Arrays.copyOf(buffer, readCount);
                optByteCallback.append(truncBuffer);
            }
        }
        // Make a reference copy here, _settings.charCallback(), to be thread-safe.
        Appendable optCharCallback =
            (null != _optCharCallbackFromFactory
                ? _optCharCallbackFromFactory : _settings.charCallback());
        if (null != optCharCallback && !lastText.isEmpty()) {
            optCharCallback.append(lastText);
        }
    }

    /**
     * For subclasses to access members.
     */
    protected InputStream getInputStream() {
        return _inputStream;
    }

    /**
     * For subclasses to access members.
     */
    protected ProcessOutputStreamSettings getSettings() {
        return _settings;
    }

    /**
     * For subclasses to access members.
     */
    protected ByteArraySimpleBuilder getByteArrBuilder() {
        return _byteArrBuilder;
    }
    
    /**
     * For subclasses to access members.
     */
    protected Appendable getOptCharCallbackFromFactory() {
        return _optCharCallbackFromFactory;
    }
    
    /**
     * For subclasses to access members.
     */
    protected ByteAppendable getOptByteCallbackFromFactory() {
        return _optByteCallbackFromFactory;
    }
}
