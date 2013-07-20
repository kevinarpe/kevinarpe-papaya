package com.googlecode.kevinarpe.papaya.process;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;

/**
 * This class is used to spawn external child processes by calling {@link #start()}.  Instances of
 * {@link Process2} are used to manage these spawned child processes.  Multiple child processes may
 * be spawned from a single instance of {@link ProcessBuilder2}.
 * <p>
 * This class, in conjunction with class {@code Process2}, act as powerful replacements for
 * {@link ProcessBuilder} and {@link Process}, respectively.  These default implementations
 * included in the JDK are deceptively simple and unfortunately easily misused.
 * <p>
 * Due to the nature of modern operating system processes, there are normally three streams
 * associated with each process: STDIN, STDOUT, and STDERR.  Instances of {@link Process2} will
 * construct helper threads as necessary to ensure none of these streams is blocking the child
 * process.
 * <p>
 * For example, if the input (via STDIN) to a process is huge (many megabytes or more), normally,
 * the receiving process cannot consume the input in a single non-blocking call.  Instead, the
 * child process will block to process the incoming data, then resume reading from STDIN.  The same
 * is true for the output streams: STDOUT and STDERR.  If large amounts of data are produced by
 * STDOUT or STDERR, the child process will block until this data is consumed.
 * <p>
 * While normal uses of STDIN, STDOUT, and STDERR are human-readable text, provisions are made in
 * this class to provide byte-oriented streams as well as character-oriented streams.  These may be
 * mixed and matched as necessary.
 * <p>
 * Where possible, setter methods return a reference to {@code this}, facilitating interface
 * fluency.
 * <p>
 * Finally, as processes will vary in their time to termination, a helpful set of methods has been
 * added to {@link Process2} to wait for the exit value in a friendly and convenient manner.
 * <p>
 * Here are some extensions on top of {@code ProcessBuilder}:
 * <ul>
 *   <li>Set text for STDIN via {@link ProcessBuilder2#stdinText(String)}</li>
 *   <li>Set raw bytes for STDIN via {@link ProcessBuilder2#stdinData(byte[])}</li>
 *   <li>Configure STDOUT handling via {@link ProcessBuilder2#stdoutSettings()}</li>
 *   <li>Configure STDERR handling via {@link ProcessBuilder2#stderrSettings()}</li>
 * </ul>
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class ProcessBuilder2
extends AbstractProcessSettings {
    
    private final ProcessBuilder _processBuilder;
    private byte[] _optStdinByteArr;
    private String _optStdinText;
    
    /**
     * Forwards to {@link ProcessBuilder#ProcessBuilder(List)}.
     */
    public ProcessBuilder2(List<String> command) {
        CollectionArgs.checkElementsNotNull(command, "command");
        
        _processBuilder = new ProcessBuilder(command);
    }
    
    /**
     * Forwards to {@link ProcessBuilder#ProcessBuilder(String...)}.
     */
    public ProcessBuilder2(String... commandArr) {
        this(new ArrayList<String>(Arrays.asList(commandArr)));
    }
    
    /**
     * Copy constructor
     * 
     * @param pb
     *        reference to another instance of {@link ProcessBuilder2}
     * 
     * @throws NullPointerException
     *         if {@code pb} is {@code null}
     */
    public ProcessBuilder2(ProcessBuilder2 pb) {
        super(ObjectArgs.checkNotNull(pb, "pb"), ChildProcessState.HAS_NOT_STARTED);
        
        _processBuilder = new ProcessBuilder(pb.command());
        
        Map<String, String> env = this.environment();
        env.clear();
        Map<String, String> env2 = pb.environment();
        env.putAll(env2);
        
        File dirPath = pb.directory();
        this.directory(dirPath);
        
        boolean isRedirected = pb.redirectErrorStream();
        this.redirectErrorStream(isRedirected);
        
        byte[] stdinData = pb.stdinData();
        String stdinText = pb.stdinText();
        
        if (null != stdinData) {
            this.stdinData(stdinData);
        }
        
        if (null != stdinText) {
            this.stdinText(stdinText);
        }
    }

    /**
     * Forwards to {@link ProcessBuilder#command(List)}.
     * <p>
     * Each argument, include the program name, should be a separate element, which need not be
     * quoted with {@code ""} or {@code ''}.  Quotes allow shells to parse command lines.
     * 
     * @return reference to {@code this}
     * 
     * @throws NullPointerException
     *         if {@code command} is {@code null}
     * 
     * @see #command(String...)
     * @see #command()
     */
    public ProcessBuilder2 command(List<String> command) {
        CollectionArgs.checkElementsNotNull(command, "command");
        
        _processBuilder.command(command);
        return this;
    }

    /**
     * Forwards to {@link ProcessBuilder#command(String...)}.
     * <p>
     * Each argument, include the program name, should be a separate element, which need not be
     * quoted with {@code ""} or {@code ''}.  Quotes allow shells to parse command lines.
     * 
     * @return reference to {@code this}
     * 
     * @see #command(List)
     * @see #command()
     */
    public ProcessBuilder2 command(String... command) {
        ArrayArgs.checkElementsNotNull(command, "command");
        
        _processBuilder.command(command);
        return this;
    }

    /**
     * Forwards to {@link ProcessBuilder#command()}.
     * 
     * @see #command(List)
     * @see #command(String...)
     */
    @Override
    public List<String> command() {
        return _processBuilder.command();
    }

    /**
     * Forwards to {@link ProcessBuilder#environment()}.
     */
    @Override
    public Map<String, String> environment() {
        return _processBuilder.environment();
    }

    /**
     * Forwards to {@link ProcessBuilder#directory()}.
     *
     * @return (optional) override working directory for child process.  May be {@code null}.
     *         There is no guarantee the path is a directory.
     * 
     * @see #directory(File)
     */
    @Override
    public File directory() {
        return _processBuilder.directory();
    }

    /**
     * Forwards to {@link ProcessBuilder#directory(File)}.
     * 
     * @param directory
     *        (optional) override working directory for child process.  May be {@code null}.
     *        The path is only checked if directory when {@link #start()} is called.
     * 
     * @return reference to {@code this}
     * 
     * @see #directory()
     */
    public ProcessBuilder2 directory(File directory) {
        _processBuilder.directory(directory);
        return this;
    }

    /**
     * Forwards to {@link ProcessBuilder#redirectErrorStream()}.
     * 
     * @see #redirectErrorStream(boolean)
     */
    @Override
    public boolean redirectErrorStream() {
        return _processBuilder.redirectErrorStream();
    }

    /**
     * Forwards to {@link ProcessBuilder#redirectErrorStream(boolean)}.
     * <p>
     * Generally, it is <b>not</b> recommended to use this feature, as this class readily
     * facilitates the capture and processing of STDOUT and STDERR separately.  In the common case
     * where all output is logged, it is helpful for debugging to mark each line of text (or block
     * of byte data) with its source stream: STDOUT or STDERR.
     * 
     * @return reference to {@code this}
     * 
     * @see #redirectErrorStream()
     */
    public ProcessBuilder2 redirectErrorStream(boolean redirectErrorStream) {
        _processBuilder.redirectErrorStream(redirectErrorStream);
        return this;
    }
    
    /**
     * Sets array of bytes to write to STDIN stream in new process.
     * <p>
     * This method clears any text previously set with {@link #stdinText(String)}.
     * 
     * @param optByteArr
     * <ul>
     *   <li>(optional) array of bytes for STDIN stream</li>
     *   <li>May be {@code null} or empty, but empty is treated as {@code null}.</li>
     *   <li>This array is <i>not</i> copied.</li>
     * </ul>
     * 
     * @return reference to {@code this}
     * 
     * @see #stdinText(String)
     * @see #stdinData()
     */
    public ProcessBuilder2 stdinData(byte[] optByteArr) {
        _optStdinText = null;
        if (null == optByteArr || 0 == optByteArr.length) {
            _optStdinByteArr = null;
        }
        else {
            _optStdinByteArr = optByteArr;
        }
        return this;
    }
    
    /**
     * Sets text to write to STDIN stream in new process.  This text is not written directly to
     * STDIN stream.  Instead, the bytes first are extracted via {@link String#getBytes()}, then
     * written to STDIN stream.
     * <p>
     * This method clears any bytes previously set with {@link #stdinData(byte[])}.
     * 
     * @param optStr
     * <ul>
     *   <li>(optional) text for STDIN stream</li>
     *   <li>May be {@code null} or empty, but empty is treated as {@code null}.</li>
     * </ul>
     * 
     * @return reference to {@code this}
     * 
     * @see String#getBytes()
     * @see #stdinData(byte[])
     * @see #stdinText()
     */
    public ProcessBuilder2 stdinText(String optStr) {
        _optStdinByteArr = null;
        if (null == optStr || optStr.isEmpty()) {
            _optStdinText = null;
        }
        else {
            _optStdinText = optStr;
        }
        return this;
    }
    
    /**
     * Retrieves the optional array of bytes to be written to the STDIN stream of the new process.
     * The return value is <b>not</b> a copy.  Callers may directly modify the array elements.
     * <p>
     * If the return value is <b>not</b> {@code null}, the return value of {@link #stdinText()} is
     * guaranteed to be {@code null}.
     * 
     * @return array of bytes for STDIN.  Never an empty array.  May be {@code null}.
     *         
     * @see #stdinData(byte[])
     * @see #stdinText(String)
     * @see #stdinText()
     */
    @Override
    public byte[] stdinData() {
        return _optStdinByteArr;
    }
    
    @Override
    protected byte[] stdinDataRef() {
        return _optStdinByteArr;
    }
    
    /**
     * Retrieves the optional text to be written to the STDIN stream of the new process.
     * <p>
     * If the return value is <b>not</b> {@code null}, the return value of {@link #stdinData()} is
     * guaranteed to be {@code null}.
     * 
     * @return text for STDIN.  Never an empty {@link String}.  May be {@code null}.
     *         
     * @see #stdinText(String)
     * @see #stdinData(byte[])
     * @see #stdinData()
     */
    @Override
    public String stdinText() {
        return _optStdinText;
    }
    
    /**
     * Spawns a child process and creates an instance of {@link Process2} to manage and control
     * this new child process.
     * 
     * @return handle to new child process
     *
     * @throws IllegalArgumentException
     *         if {@link #command()} is empty or contains {@code null} values
     * @throws IOException
     * <ul>
     *   <li>if {@link #directory()} is not {@code null} and is not a directory</li>
     *   <li>if new child process cannot be started</li>
     * </ul>
     */
    public Process2 start()
    throws IOException {
        List<String> command = this.command();
        try {
            CollectionArgs.checkNotEmptyAndElementsNotNull(command, "command");
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Arguments are invalid", e);
        }
        
        File directory = this.directory();
        if (null != directory) {
            try {
                PathArgs.checkDirectoryExists(directory, "directory");
            }
            catch (Exception e) {
                throw new IOException("Working directory override is invalid", e);
            }
        }
        
        Process p = null;
        try {
            p = _processBuilder.start();
        }
        catch (IOException e) {
            List<String> argList = command();
            String s = String.format("Failed to start command: %s", argListToString(argList));
            throw new IOException(s, e);
        }
        Process2 p2 = new Process2(this, p);
        return p2;
    }
    
    static String argListToString(List<String> argList) {
        String x = "[" + Joiner.on(", ").join(argList) + "]";
        return x;
    }
}
