package com.googlecode.kevinarpe.papaya.obsolete;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.joe_e.array.ByteArray;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.args.ArrayArgs;
import com.googlecode.kevinarpe.papaya.args.CollectionArgs;
import com.googlecode.kevinarpe.papaya.args.IntArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;

/**
 * This class is used to spawn external child processes.  Instances of {@link Process2}
 * are used to manage these spawned child processes.  Multiple child processes may be spawned from
 * a single instance of {@link ProcessBuilder2}.
 * <p>
 * This class, in conjunction with class {@code Process2}, act as powerful replacements for
 * {@link ProcessBuilder} and {@link Process}, respectively.  These default implementations
 * included in the JDK are deceptively simple, and, unfortunately, easily misused.
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
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ProcessBuilder2 {
    
    /**
     * Reduces the number of methods required for implementation by interface {@link Appendable}
     * from three to one.  Only {@link #append(CharSequence)} must be implemented by subclasses.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     */
    public static abstract class AbstractSimpleAppendable
    implements Appendable {

        /**
         * This method call {@link #append(CharSequence)}.
         * <p>
         * {@inheritDoc}
         */
        @Override
        public Appendable append(CharSequence csq, int start, int end)
        throws IOException {
            CharSequence csq2 = csq.subSequence(start, end);
            Appendable result = append(csq2);
            return result;
        }


        /**
         * This method call {@link #append(CharSequence)}.
         * <p>
         * {@inheritDoc}
         */
        @Override
        public Appendable append(char c)
        throws IOException {
            String x = String.valueOf(c);
            Appendable result = append(x);
            return result;
        }
    }

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

    /**
     * Used internally by both {@link ProcessBuilder2} and {@link Process2}.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     */
    static class _ProcessSettings {
        
        /**
         * Not synchronized with underlying instance of {@link ProcessBuilder} unless this instance
         * was created via {@link #clone2(ProcessBuilder2)}.
         */
        public List<String> command;
        
        /**
         * Not synchronized with underlying instance of {@link ProcessBuilder} unless this instance
         * was created via {@link #clone2(ProcessBuilder2)}.
         */
        public Map<String, String> environment;
        
        /**
         * Not synchronized with underlying instance of {@link ProcessBuilder} unless this instance
         * was created via {@link #clone2(ProcessBuilder2)}.
         */
        public File directory;
        
        /**
         * Not synchronized with underlying instance of {@link ProcessBuilder} unless this instance
         * was created via {@link #clone2(ProcessBuilder2)}.
         */
        public boolean redirectErrorStream;
        
        /**
         * Used internally by both {@link ProcessBuilder2} and {@link Process2}.
         * 
         * @author Kevin Connor ARPE (kevinarpe@gmail.com)
         */
        static class _InputStreamSettings {
            
            public Charset charset;
            public Appendable optCharCallback;
            public ByteAppendable optByteCallback;
            public boolean accumulateData;
            public int maxAccumulateByteCount;
            
            public _InputStreamSettings() {
                charset = Charset.defaultCharset();
                accumulateData = false;
                maxAccumulateByteCount = -1;
            }
            
            public _InputStreamSettings clone2() {
                _InputStreamSettings x = new _InputStreamSettings();
                
                x.charset = this.charset;
                x.optCharCallback = this.optCharCallback;
                x.optByteCallback = this.optByteCallback;
                x.accumulateData = this.accumulateData;
                x.maxAccumulateByteCount = this.maxAccumulateByteCount;
                
                return x;
            }
        }
        
        public byte[] optStdinByteArr;
        public _InputStreamSettings stdoutSettings;
        public _InputStreamSettings stderrSettings;
        
        public _ProcessSettings() {
            redirectErrorStream = false;
            stdoutSettings = new _InputStreamSettings();
            stderrSettings = new _InputStreamSettings();
        }
        
        /**
         * Called by {@link ProcessBuilder2#start()}.
         */
        public _ProcessSettings clone2(ProcessBuilder2 pb) {
            _ProcessSettings x = new _ProcessSettings();
            
            x.command = ImmutableList.copyOf(pb.command());
            x.environment = ImmutableMap.copyOf(pb.environment());
            x.directory = pb.directory();
            x.redirectErrorStream = pb.redirectErrorStream();
            x.optStdinByteArr =
                (null == this.optStdinByteArr
                    ? null
                    : Arrays.copyOf(this.optStdinByteArr, this.optStdinByteArr.length));
            x.stdoutSettings = this.stdoutSettings.clone2();
            x.stderrSettings = this.stderrSettings.clone2();
            
            return x;
        }
    }
    
    private final ProcessBuilder _processBuilder;
    private final _ProcessSettings _processSettings;
    
    /**
     * Forwards to {@link ProcessBuilder#ProcessBuilder(List)}.
     */
    public ProcessBuilder2(List<String> command) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(command, "command");
        
        _processBuilder = new ProcessBuilder(command);
        _processSettings = new _ProcessSettings();
    }
    
    /**
     * Forwards to {@link ProcessBuilder#ProcessBuilder(String...)}.
     */
    public ProcessBuilder2(String... commandArr) {
        this(new ArrayList<String>(Arrays.asList(commandArr)));
    }

    public int hashCode() {
        return _processBuilder.hashCode();
    }

    public boolean equals(Object obj) {
        return _processBuilder.equals(obj);
    }

    /**
     * Forwards to {@link ProcessBuilder#command(List)}.
     * <p>
     * Each argument, include the program name, should be a separate element, which need not be
     * quoted with {@code ""} or {@code ''}.  Quotes allow shells to parse command lines.
     * 
     * @return reference to {@code this}
     * 
     * @see #command(String...)
     * @see #command()
     */
    public ProcessBuilder2 command(List<String> command) {
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
     * @see #command(List<String>)
     * @see #command()
     */
    public ProcessBuilder2 command(String... command) {
        _processBuilder.command(command);
        return this;
    }

    /**
     * Forwards to {@link ProcessBuilder#command()}.
     * 
     * @see #command(List<String>)
     * @see #command(String...)
     */
    public List<String> command() {
        return _processBuilder.command();
    }

    /**
     * Forwards to {@link ProcessBuilder#environment()}.
     */
    public Map<String, String> environment() {
        return _processBuilder.environment();
    }

    public String toString() {
        return _processBuilder.toString();
    }

    /**
     * Forwards to {@link ProcessBuilder#directory()}.
     * 
     * @see #directory(File)
     */
    public File directory() {
        return _processBuilder.directory();
    }

    /**
     * Forwards to {@link ProcessBuilder#directory(File)}.
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
     * 
     * @param optByteArr
     *        (optional) array of bytes for STDIN stream.
     *        May be {@code null}.
     *        This array is <i>not</i> copied.
     * 
     * @return reference to {@code this}
     * 
     * @throws IllegalArgumentException
     *         if {@code optByteArr} is not {@code null} and empty
     * 
     * @see #stdinData(String)
     * @see #stdinData()
     */
    public ProcessBuilder2 stdinData(byte[] optByteArr) {
        if (null == optByteArr) {
            _processSettings.optStdinByteArr = null;
        }
        else {
            ArrayArgs.checkNotEmpty(optByteArr, "optByteArr");
            _processSettings.optStdinByteArr = optByteArr;
        }
        return this;
    }
    
    /**
     * This is a convenience method for {@link #stdinData(byte[]) where
     * {@code optByteArr} is {@code optStr.getBytes}.
     * 
     * @param optStr
     *        (optional) text for STDIN stream.
     *        May be {@code null}.
     * 
     * @return reference to {@code this}
     * 
     * @throws IllegalArgumentException
     *         if {@code optStr} is not {@code null} and empty
     *         
     * @see String#getBytes()
     * @see #stdinData(byte[])
     * @see #stdinData()
     */
    public ProcessBuilder2 stdinData(String optStr) {
        if (null == optStr) {
            _processSettings.optStdinByteArr = null;
        }
        else {
            StringArgs.checkNotEmpty(optStr, "optStr");
            _processSettings.optStdinByteArr = optStr.getBytes();
        }
        return this;
    }
    
    /**
     * Retrieves the optional array of bytes to be written to the STDIN stream of the new process.
     * 
     * @return array of bytes for STDIN.
     *         May be {@code null}.
     *         
     * @see #stdinData(String)
     * @see #stdinData(byte[])
     */
    public byte[] stdinData() {
        return _processSettings.optStdinByteArr;
    }
    
    /**
     * Sets the {@link Charset} used to convert bytes read from STDOUT to {@link String}.
     * The default value is {@link Charset#defaultCharset()}, and works correctly in the vast
     * majority of use cases. 
     * 
     * @return reference to {@code this}
     * 
     * @throws NullPointerException
     *         if {@code cs} is {@code null}
     *         
     * @see Charset#defaultCharset()
     * @see #stdoutCharset
     */
    public ProcessBuilder2 stdoutCharset(Charset cs) {
        ObjectArgs.checkNotNull(cs, "cs");
        
        _processSettings.stdoutSettings.charset = cs;
        return this;
    }
    
    /**
     * Retrieves the {@link Charset} used to convert bytes read from STDOUT to {@link String}.
     * The initial value is {@link Charset#defaultCharset()}.
     *         
     * @see Charset#defaultCharset()
     * @see #stdoutCharset(Charset)
     */
    public Charset stdoutCharset() {
        return _processSettings.stdoutSettings.charset;
    }

    /**
     * Sets the optional character-based callback for STDOUT.  As bytes are received from STDOUT,
     * they are converted to {@link String} using {@link Charset} from {@link #stdoutCharset()},
     * then appended to this callback.
     * <p>
     * If incoming data needs to be processed in byte-based form, use
     * {@link #stdoutByteCallback(ByteAppendable)}.  The callbacks for character- and byte-based
     * data are mutually exclusive.  Zero, one, or both may be employed.
     * <p>
     * It is not a requirement to set a callback to receive incoming data from STDOUT.
     * Alternatively, there is a setting to accumulate all incoming data via
     * {@link #accumulateStdoutData(boolean)}.  This feature is also independent of callbacks to
     * process incoming data.  This means it is possible to process data from both STDOUT and
     * STDERR simultaneously (and separately) in both character- and byte-based form.  If the
     * accumulator feature is enabled, after the process has started, call one of these methods to
     * access the accumulated data:
     * <ul>
     *   <li>{@link Process2#getStdoutDataAsByteArr()}</li>
     *   <li>{@link Process2#getStdoutDataAsString()}</li>
     *   <li>{@link Process2#getStdoutDataAsString(Charset)}</li>
     * </ul>
     * A separate thread always is used by {@link Process2} to read STDOUT.  Thus, incoming data
     * is appended to the callback from a different thread than that used to start the process.
     * This may be important for specialised implementations of {@link Appendable}.
     * <p>
     * If {@link #accumulateStdoutData()} is enabled, all data from STDERR is redirected to STDOUT.
     * This may help to simplify processing logic, at the expense of distinguishing from STDOUT and
     * STDERR data.
     * 
     * @param optCallback
     * <ul>
     *   <li>(optional) Appendable reference to receive incoming data from STDOUT.</li>
     *   <li>Must not be an instance of {@link StringBuilder}, which is not thread-safe.</li>
     *   <li>Intead use {@link StringBuffer}.</li>
     *   <li>May be {@null}.</li>
     * </ul>
     *        
     * @return reference to {@code this}
     * 
     * @see #stdoutByteCallback()
     * @see #accumulateStdoutData(boolean)
     * @see #redirectErrorStream()
     */
    public ProcessBuilder2 stdoutCharCallback(Appendable optCallback) {
        checkCharCallback(optCallback, "optCallback");
        
        _processSettings.stdoutSettings.optCharCallback = optCallback;
        return this;
    }
    
    /**
     * Retrieves the character-based callback for STDOUT.  Default value is {@code null}.
     * 
     * @return may be {@code null}
     * 
     * @see #stdoutCharCallback(Appendable)
     * @see #accumulateStdoutData()
     */
    public Appendable stdoutCharCallback() {
        return _processSettings.stdoutSettings.optCharCallback;
    }
    
    /**
     * Sets the optional byte-based callback for STDOUT.  As bytes are received from STDOUT, they
     * are appended to this callback.
     * <p>
     * If incoming data needs to be processed in character-based form, use
     * {@link #stdoutCharCallback(Appendable)}.  The callbacks for character- and byte-based
     * data are mutually exclusive.  Zero, one, or both may be employed.
     * <p>
     * It is not a requirement to set a callback to receive incoming data from STDOUT.
     * Alternatively, there is a setting to accumulate all incoming data via
     * {@link #accumulateStdoutData(boolean)}.  This feature is also independent of callbacks to
     * process incoming data.  This means it is possible to process data from both STDOUT and
     * STDERR simultaneously (and separately) in both character- and byte-based form.  If the
     * accumulator feature is enabled, after the process has started, call one of these methods to
     * access the accumulated data:
     * <ul>
     *   <li>{@link Process2#getStdoutDataAsByteArr()}</li>
     *   <li>{@link Process2#getStdoutDataAsString()}</li>
     *   <li>{@link Process2#getStdoutDataAsString(Charset)}</li>
     * </ul>
     * A separate thread always is used by {@link Process2} to read STDOUT.  Thus, incoming data
     * is appended to the callback from a different thread than that used to start the process.
     * This may be important for specialised implementations of {@link Appendable}.
     * <p>
     * If {@link #accumulateStdoutData()} is enabled, all data from STDERR is redirected to STDOUT.
     * This may help to simplify processing logic, at the expense of distinguishing from STDOUT and
     * STDERR data.
     * 
     * @param optCallback
     * <ul>
     *   <li>(optional) ByteAppendable reference to receive incoming data from STDOUT.</li>
     *   <li>May be {@null}.</li>
     * </ul>
     *        
     * @return reference to {@code this}
     * 
     * @see #stdoutByteCallback()
     * @see #accumulateStdoutData(boolean)
     * @see #redirectErrorStream()
     */
    public ProcessBuilder2 stdoutByteCallback(ByteAppendable optCallback) {
        _processSettings.stdoutSettings.optByteCallback = optCallback;
        return this;
    }
    
    /**
     * Retrieves the byte-based callback for STDOUT.  Default value is {@code null}.
     * 
     * @return may be {@code null}
     * 
     * @see #stdoutByteCallback(ByteAppendable)
     * @see #accumulateStdoutData()
     */
    public ByteAppendable stdoutByteCallback() {
        return _processSettings.stdoutSettings.optByteCallback;
    }
    
    /**
     * If this feature is enabled, it is crucial to also set
     * {@link #maxAccumulateStdoutByteCount(int)}.  By default, an unlimited amount of data is
     * accumulated.  If a large number of processes are launched simultaneously and each outputs
     * a large amount of data on STDOUT, the parent Java Virtual Machine may easily exhaust
     * available memory.
     * 
     * @param b
     *        if {@code true}, all incoming data from STDOUT is accumulated
     *        
     * @return reference to {@code this}
     * 
     * @see #accumulateStdoutData()
     * @see #maxAccumulateStdoutByteCount(int)
     * @see Process2#getStdoutDataAsByteArr()
     * @see Process2#getStdoutDataAsString()
     * @see Process2#getStdoutDataAsString(Charset)
     */
    public ProcessBuilder2 accumulateStdoutData(boolean b) {
        _processSettings.stdoutSettings.accumulateData = b;
        return this;
    }
    
    /**
     * Retrieves the setting for the data accumulation feature for STDOUT stream.  The default
     * value is {@code false}.
     */
    public boolean accumulateStdoutData() {
        return _processSettings.stdoutSettings.accumulateData;
    }
    
    /**
     * Sets the maximum number of bytes to accumulate from STDOUT stream.  This feature is only
     * relevant if {@link #accumulateStdoutData()} is enabled.
     * <p>
     * It is very important to enable this feature in parallel with
     * {@link #accumulateStdoutData()}.  It is easy for a errant process to produce gigabytes of
     * data on STDOUT and exhaust all available memory for the parent Java virtual machine.
     * 
     * @param max
     *        any value except zero.  Negative value implies this feature is disabled.
     * 
     * @return reference to {@code this}
     * 
     * @see #maxAccumulateStdoutByteCount()
     * @see #accumulateStdoutData()
     */
    public ProcessBuilder2 maxAccumulateStdoutByteCount(int max) {
        checkMaxAccumulateByteCount(max);
        
        _processSettings.stdoutSettings.maxAccumulateByteCount = max;
        return this;
    }
    
    /**
     * Retrieves the maximum number of bytes to accumulate from STDOUT stream.  This feature is
     * only relevant if {@link #accumulateStdoutData()} is enabled.
     *
     * @return if negative, this feature is disabled
     * 
     * @see #maxAccumulateStdoutByteCount(int)
     * @see #accumulateStdoutData()
     */
    public int maxAccumulateStdoutByteCount() {
        return _processSettings.stdoutSettings.maxAccumulateByteCount;
    }
    
    protected void checkMaxAccumulateByteCount(int max) {
        IntArgs.checkNotExactValue(max, 0, "max");
    }
    
    public ProcessBuilder2 stderrCharset(Charset cs) {
        ObjectArgs.checkNotNull(cs, "cs");
        
        _processSettings.stderrSettings.charset = cs;
        return this;
    }
    
    public Charset stderrCharset() {
        return _processSettings.stderrSettings.charset;
    }

    public ProcessBuilder2 stderrCharCallback(Appendable optCallback) {
        checkCharCallback(optCallback, "optCallback");
        
        _processSettings.stderrSettings.optCharCallback = optCallback;
        return this;
    }
    
    public Appendable stderrCharCallback() {
        return _processSettings.stderrSettings.optCharCallback;
    }
    
    public ProcessBuilder2 stderrByteCallback(ByteAppendable optCallback) {
        _processSettings.stderrSettings.optByteCallback = optCallback;
        return this;
    }
    
    public ByteAppendable stderrByteCallback() {
        return _processSettings.stderrSettings.optByteCallback;
    }
    
    public ProcessBuilder2 accumulateStderrData(boolean b) {
        _processSettings.stderrSettings.accumulateData = b;
        return this;
    }
    
    public boolean accumulateStderrData() {
        return _processSettings.stderrSettings.accumulateData;
    }
    
    public ProcessBuilder2 maxAccumulateStderrByteCount(int max) {
        checkMaxAccumulateByteCount(max);
        
        _processSettings.stderrSettings.maxAccumulateByteCount = max;
        return this;
    }
    
    public int maxAccumulateStderrByteCount() {
        return _processSettings.stderrSettings.maxAccumulateByteCount;
    }
    
    protected void checkCharCallback(Appendable optCallback, String argName) {
        if (optCallback instanceof StringBuilder) {
            throw new IllegalArgumentException(String.format(
                "Arg '%s' cannot be an instance of type StringBuilder (not thread-safe)."
                + "%n\tUse class StringBuffer instead",
                argName));
        }
    }
    
    /**
     * Spawns a child process and creates an instance of {@link Process2} to manage and control
     * this new child process.
     * 
     * @return handle to new child process
     * 
     * @throws IOException
     *         if new child process cannot be started
     */
    public Process2 start()
    throws IOException {
        Process p = null;
        try {
            p = _processBuilder.start();
        }
        catch (IOException e) {
            List<String> argList = command();
            String s = String.format("Failed to start command: %s", argListToString(argList));
            throw new IOException(s, e);
        }
        _ProcessSettings ps = _processSettings.clone2(this);
        Process2 p2 = new Process2(p, ps);
        return p2;
    }
    
    static String argListToString(List<String> argList) {
        String x = "[" + Joiner.on(", ").join(argList) + "]";
        return x;
    }
}
