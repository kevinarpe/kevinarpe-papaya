package com.googlecode.kevinarpe.papaya.process;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.LongArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.InvalidExitValueException;
import com.googlecode.kevinarpe.papaya.exception.TimeoutException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class must be created via {@link ProcessBuilder2#start()}.  An instance of
 * this class is guaranteed to track a started child process.
 * <p>
 * The following auxilliary threads may be created to manage child process input and output:
 * <ul>
 *   <li>Write data to STDIN: If data for STDIN is specified via
 *   {@link ProcessBuilder2#stdinData(byte[])} or {@link ProcessBuilder2#stdinText(String)}, a
 *   thread is spawned to write these bytes to the child process' STDIN stream.  A separate thread
 *   is used to prevent blocking in the main thread if the operating system or child process cannot
 *   read the entire data array in a single read.</li>
 *   <li>Read data from STDOUT: A thread is always spawned to read data from the child process'
 *   STDOUT stream.</li>
 *   <li>Read data from STDERR: If STDERR is <b>not</b> redirected to STDOUT via
 *   {@link ProcessBuilder2#redirectErrorStream(boolean)}, then a thread is spawned to read data
 *   from the child process' STDERR stream.</li>
 * </ul>
 * <p>
 * In the standard JDK implementation of {@link Process}, the process' original argument list,
 * environment, directory, and if STDERR is redirected to STDOUT are not available.  This extended
 * version provides the following method to access immutable versions of this data:
 * <ul>
 *   <li>{@link #command()}</li>
 *   <li>{@link #environment()}</li>
 *   <li>{@link #directory()}</li>
 *   <li>{@link #redirectErrorStream()}</li>
 * </ul>
 * <p>
 * Since this class and {@link ProcessBuilder2} both extend {@link AbstractProcessSettings}, all
 * settings for STDIN, STDOUT, and STDERR streams from {@link ProcessBuilder2} are available from
 * this class:
 * <ul>
 *   <li>STDIN config via {@link Process2#stdinData()} and {@link Process2#stdinText()}</li>
 *   <li>STDOUT config via {@link Process2#stdoutSettings()}</li>
 *   <li>STDERR config via {@link Process2#stderrSettings()}</li>
 * </ul>
 * <p>
 * This class has many different ways to retrieve the child process exit value:
 * <ul>
 *   <li>{@link #exitValue()}:
 *   Throws an exception if child process has not terminated</li>
 *   <li>{@link #tryExitValue()}:
 *   Does not throw an exception if child process has not terminated</li>
 *   <li>{@link #checkExitValue(int)}:
 *   Validates child process exit value</li>
 *   <li>{@link #checkExitValue(Collection)}:
 *   Validates child process exit value</li>
 *   <li>{@link #waitFor()}:
 *   Waits indefinitely for child process to terminate</li>
 *   <li>{@link #waitFor(long)}:
 *   Waits (up to a maximum number of milliseconds) for child process to terminate</li>
 *   <li>{@link #waitForThenCheckExitValue(int)}:
 *   Waits indefinitely for child process to terminate, then validates the exit value</li>
 *   <li>{@link #waitForThenCheckExitValue(Collection)}:
 *   Waits indefinitely for child process to terminate, then validates the exit value</li>
 *   <li>{@link #waitForThenCheckExitValue(long, int)}:
 *   Waits (up to a maximum number of milliseconds) for child process to terminate,
 *   then validates the exit value</li>
 *   <li>{@link #waitForThenCheckExitValue(long, Collection)}:
 *   Waits (up to a maximum number of milliseconds) for child process to terminate,
 *   then validates the exit value</li>
 * </ul>
 * <p>
 * Careful readers will note that many methods unexpectedly throw {@link IOException}.  This is a
 * wart of design.  If data is to be written to the child process STDIN stream, a separate thread
 * is spawned to prevent blocking in the main thread.  If an exception is caught in this background
 * STDIN writer thread, it is held and rethrown at the next opportunity.  This prevents I/O-related
 * exceptions from being lost in a background thread.
 * <p>
 * To better understand this issue by example, see {@link #destroy()}. 
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class Process2
extends AbstractProcessSettings {
    
    final Process _process;
    private final List<String> _argList;
    private final String _argListStr;
    private final Map<String, String> _environment;
    private final File _directory;
    private final boolean _redirectErrorStream;
    private final byte[] _optStdinByteArr;
    private final String _optStdinText;
    private final ReadInputStreamThread _readStdoutThread;
    private final ReadInputStreamThread _optReadStderrThread;
    private final WriteOutputStreamThread _optWriteStdinThread;
    
    Process2(ProcessBuilder2 pb, Process process) {
        super(ObjectArgs.checkNotNull(pb, "pb"), ChildProcessState.HAS_STARTED);
        
        _process = ObjectArgs.checkNotNull(process, "process");
        _argList = ImmutableList.copyOf(pb.command());
        _argListStr = ProcessBuilder2.argListToString(_argList);
        _environment = ImmutableMap.copyOf(pb.environment());
        _directory = pb.directory();
        _redirectErrorStream = pb.redirectErrorStream();
        
        byte[] optByteArr = pb.stdinData();
        _optStdinByteArr =
            (null == optByteArr ? null : Arrays.copyOf(optByteArr, optByteArr.length));
        _optStdinText = pb.stdinText();
        
        InputStream stdoutStream = _process.getInputStream();
        ProcessOutputStreamSettings stdoutSettings = this.stdoutSettings();
        _readStdoutThread =
            createReadInputStreamThreadAndStart(stdoutStream, stdoutSettings, "STDOUT");
        
        if (_redirectErrorStream) {
            _optReadStderrThread = null;
        }
        else {
            InputStream stderrStream = _process.getErrorStream();
            ProcessOutputStreamSettings stderrSettings = this.stderrSettings();
            _optReadStderrThread =
                createReadInputStreamThreadAndStart(stderrStream, stderrSettings, "STDERR");
        }
        
        OutputStream stdinStream = _process.getOutputStream();
        byte[] stdinByteArr = _optStdinByteArr;
        if (null == stdinByteArr && null != _optStdinText) {
            stdinByteArr = _optStdinText.getBytes();
        }
        if (null == stdinByteArr) {
            _optWriteStdinThread = null;
            try {
                stdinStream.close();
            }
            catch (IOException e) {
                // Intentionally ignore exception.
                @SuppressWarnings("unused")
                int dummy = 1;  // debug breakpoint
            }
        }
        else {
            _optWriteStdinThread = createWriteOutputStreamThread(stdinStream, stdinByteArr);
            setThreadName(_optWriteStdinThread, "STDIN");
            _optWriteStdinThread.start();
        }
    }
    
    /**
     * @return immutable copy of original argument list to start child process
     * 
     * @see ProcessBuilder2#command()
     */
    @Override
    public List<String> command() {
        return _argList;
    }
    
    /**
     * @return immutable copy of original environment variables (name/value pairs) for
     *         child process
     * 
     * @see ProcessBuilder2#environment()
     */
    @Override
    public Map<String, String> environment() {
        return _environment;
    }
    
    /**
     * @return original working directory for child process
     * 
     * @see ProcessBuilder2#directory()
     */
    @Override
    public File directory() {
        return _directory;
    }
    
    /**
     * @return original setting if STDERR stream is redirected to STDOUT stream in child process
     * 
     * @see ProcessBuilder2#redirectErrorStream()
     */
    @Override
    public boolean redirectErrorStream() {
        return _redirectErrorStream;
    }
    
    /**
     * Note: As there is no simple or lightweight way to create an immutable array in Java, a copy
     * is made on each call.  For huge arrays, this is very inefficient.  Call this method
     * with care!
     * 
     * @return <b>copy</b> of original byte array to be written to child process' STDIN stream
     */
    @Override
    public byte[] stdinData() {
        byte[] x = _optStdinByteArr;
        if (null != x) {
            x = Arrays.copyOf(x, x.length);
        }
        return x;
    }
    
    @Override
    protected byte[] stdinDataRef() {
        return _optStdinByteArr;
    }
    
    @Override
    public String stdinText() {
        return _optStdinText;
    }
    
    /**
     * Tests if the current process has returned an exit value via {@link #tryExitValue()}.
     * A return value of {@code null} is interpretaed as the child process is still running and has
     * yet to terminate.
     * <p>
     * All child processes tracked by instances of this class are guaranteed to have at least
     * started.  However, a process is not guaranteed to finish.  A simple infinite loop may
     * (un)intentionally prevent a process from terminating.
     * 
     * @return if {@code true}, the child process is current running
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     *         
     * @see #hasFinished()
     */
    public boolean isRunning()
    throws IOException {
        Integer exitValue = tryExitValue();
        boolean b = (null == exitValue);
        return b;
    }
    
    /**
     * Tests if the current process has returned an exit value via {@link #tryExitValue()}.
     * A return value of non-{@code null} is interpretaed as the child process has terminated.
     * <p>
     * All child processes tracked by instances of this class are guaranteed to have at least
     * started.  However, a process is not guaranteed to finish.  A simple infinite loop may
     * (un)intentionally prevent a process from terminating.
     * 
     * @return if {@code true}, the child process has terminated
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     *         
     * @see #isRunning()
     */
    public boolean hasFinished()
    throws IOException {
        Integer exitValue = tryExitValue();
        boolean b = (null != exitValue);
        return b;
    }
    
    protected void tryWriteStdinThreadRethrowCaughtException()
    throws IOException {
        if (null != _optWriteStdinThread) {
            Exception e = _optWriteStdinThread.getException();
            if (null != e) {
                _optWriteStdinThread.clearException();
                throw new IOException("Failed to write data to child process STDIN stream", e);
            }
        }
    }
    
    protected void tryReadStdxxxThreadRethrowCaughtException(ReadInputStreamThread optThread)
    throws IOException {
        if (null != optThread) {
            Exception e = optThread.getException();
            if (null != e) {
                optThread.clearException();
                String msg = String.format("Failed to read data from child process %s stream",
                    optThread.getStreamName());
                throw new IOException(msg, e);
            }
        }
    }

    /**
     * If {@link ProcessOutputStreamSettings#isDataAccumulated()} is {@code true} and bytes are read by
     * the STDOUT thread, the accumulated byte array is copied and returned.  This method may be
     * safely called repeatedly during the runtime and after the termination of the child process.
     * <p>
     * Given that the data accumulation feature may be safely (repeatedly) enabled or disabled as
     * the child process is running, this method is not guaranteed to return data unless the
     * feature was enabled for the entire runtime of the child process <i>and</i> the child process
     * wrote data to its STDOUT stream.
     * 
     * @return copy of currently accumulated bytes from child process' STDOUT stream.
     *         May be empty array, but will never be {@code null}
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     *
     * @see #stdoutDataAsString()
     * @see #stdoutDataAsString(Charset)
     */
    public byte[] stdoutDataAsByteArr()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        tryReadStdxxxThreadRethrowCaughtException(_readStdoutThread);
        
        byte[] x = _readStdoutThread.getDataAsByteArr();
        return x;
    }
    
    /**
     * This is a convenience methods to call {@link #stdoutDataAsString(Charset)} where
     * {@code optCs} is {@code null}.
     */
    public String stdoutDataAsString()
    throws IOException {
        String x = stdoutDataAsString(null);
        return x;
    }
    
    /**
     * Similar to {@link #stdoutDataAsByteArr()}, but bytes are converted to an instance of
     * {@link String} using the specified {@link Charset}.  This method may be safely called
     * repeatedly during runtime and after termination of child process.
     * <p>
     * As Java currently uses UTF-16 encoding
     * for {@link String}, it may be possible for badly behaving child processes to emit invalid
     * Unicode text.
     * <a href="http://stackoverflow.com/questions/887148/how-to-determine-if-a-string-contains-invalid-encoded-characters">
     * Read more about invalid Unicode text in Java here.</a>
     *
     * @param optCs
     *        {@code Charset} used to convert bytes to {@code String}.
     *        If {@code null}, result from {@link Charset#defaultCharset()} is used.
     * 
     * @return copy of currently accumulated characters from child process' STDOUT stream.
     *         May be empty string, but will never be {@code null}
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     *
     * @see #stdoutDataAsByteArr()
     * @see #stdoutDataAsString(Charset)
     */
    public String stdoutDataAsString(Charset optCs)
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        tryReadStdxxxThreadRethrowCaughtException(_readStdoutThread);
        
        String x = _readStdoutThread.getDataAsString(optCs);
        return x;
    }
    
    private static final byte[] _EMPTY_BYTE_ARRAY = new byte[0];
    
    /**
     * Retrieves raw bytes from child process' STDERR stream.
     * 
     * @return raw bytes from child process' STDERR stream.
     *         If zero bytes received, the result is an empty {@code byte} array
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public byte[] stderrDataAsByteArr()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        tryReadStdxxxThreadRethrowCaughtException(_optReadStderrThread);
        
        byte[] x = null;
        if (null != _optReadStderrThread) {
            x = _optReadStderrThread.getDataAsByteArr();
        }
        else {
            x = _EMPTY_BYTE_ARRAY;
        }
        return x;
    }
    
    /**
     * This is a convenience method for {@link #stderrDataAsString(Charset)} where {@code optCs} is
     * {@code null}.
     */
    public String stderrDataAsString()
    throws IOException {
        String x = stderrDataAsString(null);
        return x;
    }
    
    /**
     * Retrieves the text from child process' STDERR stream.
     * 
     * @param optCs
     * <ul>
     *   <li>optional {@link Charset} to convert bytes to {@link String}</li>
     *   <li>if {@code null}, results from {@link Charset#defaultCharset()} is used</li>
     *   <li>in the vast majority of use cases, the default value is acceptable</li>
     * </ul>
     * 
     * @return text from child process' STDERR stream.
     *         If zero bytes received, the result is an empty {@code String}, e.g., {@code ""}.
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public String stderrDataAsString(Charset optCs)
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        tryReadStdxxxThreadRethrowCaughtException(_optReadStderrThread);
        
        String x = "";
        if (null != _optReadStderrThread) {
            x = _optReadStderrThread.getDataAsString(optCs);
        }
        return x;
    }

    /**
     * Tries to retrieve the exit value from the terminated child process.
     * 
     * @return
     * <ul>
     *   <li>if the child process has not terminated, the result is {@code null}</li>
     *   <li>else, the exit value from the terminated child process</li>
     * </ul>
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public Integer tryExitValue()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        Integer optExitValue = null;
        try {
            optExitValue = _process.exitValue();
            if (null != _optWriteStdinThread) {
                _optWriteStdinThread.join();
            }
            _readStdoutThread.join();
            if (null != _optReadStderrThread) {
                _optReadStderrThread.join();
            }
        }
        catch (Exception e) {
            // We failed to get exit code.  Process is still running.
            // Intentionally ignore exception.
            @SuppressWarnings("unused")
            int dummy = 1;  // debug breakpoint
        }
        return optExitValue;
    }
    
    /**
     * Forwards to {@link Process#exitValue()}.
     * 
     * @see #waitFor(long)
     */
    public int waitFor()
    throws IOException, InterruptedException {
        tryWriteStdinThreadRethrowCaughtException();
        
        int exitValue = _process.waitFor();
        return exitValue;
    }
    
    /**
     * Wait for the child process to exit and retrieve its exit value.  Optionally, a number of
     * milliseconds to wait may be specified.
     * 
     * @param timeoutMillis
     * <ul>
     *   <li>Maximum number of milliseconds to wait for child process to exit.</li>
     *   <li>Value of zero implies "wait forever".</li>
     *   <li>Must not be negative.</li>
     * </ul>
     * 
     * @return
     * <ul>
     *   <li>if {@code null}, the child process has not yet terminated after waiting for
     *   {@code timeoutMillis} milliseconds.  This scenario only occurs when {@code timeoutMillis}
     *   is non-zero</li>
     *   <li>if not {@code null}, the child has terminated and the exit value is returned</li>
     * </ul>
     * 
     * @throws IllegalArgumentException
     *         if {@code timeoutMillis} is negative
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     * @throws InterruptedException
     *         if the current thread is waiting, but is interrupted with {@link Thread#interrupt()}
     *         by another thread
     * 
     * @see #waitForThenCheckExitValue(long, Collection)
     */
    public Integer waitFor(long timeoutMillis)
    throws IOException, InterruptedException {
        tryWriteStdinThreadRethrowCaughtException();
        LongArgs.checkNotNegative(timeoutMillis, "timeoutMillis");
        
        Integer optExitValue = null;
        if (0 == timeoutMillis) {
            optExitValue = _process.waitFor();
        }
        else {
            // Before we create a thread (expensive), check if process has finished.
            // If yes, get the exit code and skip the thread create below.
            optExitValue = this.tryExitValue();
            if (null != optExitValue) {
                return optExitValue;
            }
            // Unfortunately, due to the complexities of threading (spawn/join/interrupt), this
            // section of code looks *far* more complicated than necessary.  There are no secrets
            // here.
            WaitForProcessThread thread = createWaitForProcessThread();
            thread.start();
            try {
                // First:
                // Surprisingly, a failed join is not clearly indicated here.  It is necessary to
                // check if the thread is still alive via Thread.isAlive().  Why didn't JDK API add
                // a boolean return value to this method?  <My Jimmies Are Rustled>
                // Second:
                // This method only throws InterruptedException if this thread has its interrupt()
                // method called while we are waiting for Thread.join(long) to complete.  In this
                // section of code: Impossible (as I see it).
                thread.join(timeoutMillis);
            }
            catch (InterruptedException e) {
                // Ignore this exception.
                @SuppressWarnings("unused")
                int dummy = 1;  // debug breakpoint
            }
            // If null, our call to Thread.join(long) above failed (timed-out).
            optExitValue = thread.getOptExitValue();
            // This has no effect if the thread is not alive.  Always call to be safe.
            thread.interrupt();
        }
        return optExitValue;
    }
    
    /**
     * Override this method if class {@link WaitForProcessThread} has also been subclassed.
     * 
     * @return new thread to wait for child process to terminate
     */
    protected WaitForProcessThread createWaitForProcessThread() {
        WaitForProcessThread x = new WaitForProcessThread(_process);
        return x;
    }
    
    /**
     * This is a convenience method for {@link #waitForThenCheckExitValue(long, Collection)} where
     * {@code timeoutMillis} is zero (unlimited wait) and the only valid exit value is
     * {@code validExitValue}.
     */
    public int waitForThenCheckExitValue(int validExitValue)
    throws InvalidExitValueException, IOException, InterruptedException {
        long timeoutMillis = 0;
        List<Integer> validExitValueList = Arrays.asList(validExitValue);
        try {
            int exitValue = waitForThenCheckExitValue(timeoutMillis, validExitValueList);
            return exitValue;
        }
        catch (TimeoutException e) {
            // This exception is not possible when timeoutMillis is zero.
            String msg = String.format("Internal error: Unexpected exception %s thrown",
                e.getClass().getSimpleName());
            throw new RuntimeException(msg, e);
        }
    }
    
    /**
     * This is a convenience method for {@link #waitForThenCheckExitValue(long, Collection)} where
     * {@code timeoutMillis} is zero (unlimited wait).
     */
    public int waitForThenCheckExitValue(Collection<Integer> validExitValueCollection)
    throws InvalidExitValueException, IOException, InterruptedException {
        long timeoutMillis = 0;
        try {
            int exitValue = waitForThenCheckExitValue(timeoutMillis, validExitValueCollection);
            return exitValue;
        }
        catch (TimeoutException e) {
            // This exception is not possible when timeoutMillis is zero.
            String msg = String.format("Internal error: Unexpected exception %s thrown",
                e.getClass().getSimpleName());
            throw new RuntimeException(msg, e);
        }
    }
    
    /**
     * This is a convenience method for {@link #waitForThenCheckExitValue(long, Collection)} where
     * the only valid exit value is {@code validExitValue}.
     */
    public int waitForThenCheckExitValue(long timeoutMillis, int validExitValue)
    throws TimeoutException, InvalidExitValueException, IOException, InterruptedException {
        List<Integer> validExitValueList = Arrays.asList(validExitValue);
        int exitValue = waitForThenCheckExitValue(timeoutMillis, validExitValueList);
        return exitValue;
    }
    
    /**
     * Wait for the child process to exit, retrieve its exit value, and check the exit value is
     * valid.  Optionally, a number of milliseconds to wait may be specified.
     * 
     * @param timeoutMillis
     * <ul>
     *   <li>Maximum number of milliseconds to wait for child process to exit.</li>
     *   <li>Value of zero implies "wait forever".</li>
     *   <li>Must not be negative.</li>
     * </ul>
     * @param validExitValueCollection
     *        collection of valid exit values, e.g., zero, one, etc.
     * 
     * @return validated exit value
     * 
     * @throws TimeoutException
     *         if {@code timeoutMillis} is non-zero, and, if after waiting for
     *         {@code timeoutMillis} milliseconds, the child process has not terminated
     * @throws InvalidExitValueException
     *         if exit value is not found in {@code validExitValueCollection}
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     * @throws InterruptedException
     *         if the current thread is waiting, but is interrupted with {@link Thread#interrupt()}
     *         by another thread
     * 
     * @see #waitFor()
     * @see #waitFor(long)
     */
    public int waitForThenCheckExitValue(
            long timeoutMillis, Collection<Integer> validExitValueCollection)
    throws TimeoutException, InvalidExitValueException, IOException, InterruptedException {
        tryWriteStdinThreadRethrowCaughtException();
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            validExitValueCollection, "validExitValueCollection");
        
        Integer optExitValue = waitFor(timeoutMillis);
        if (null == optExitValue) {
            throw new TimeoutException(
                timeoutMillis, "Failed to wait for child process to terminate");
        }
        else {
            _checkExitValue(optExitValue, validExitValueCollection);
            return optExitValue;
        }
    }
    
    /**
     * This is a convenience method for {@link #checkExitValue(Collection)} where the only valid
     * exit value is {@code expectedExitValue}.
     */
    public int checkExitValue(int expectedExitValue)
    throws IOException, InvalidExitValueException {
        int x = checkExitValue(Arrays.asList(expectedExitValue));
        return x;
    }
    
    /**
     * This is a convenience method for {@link #checkExitValue(Collection)} where the valid exit
     * values are {@code expectedExitValueArr}.
     */
    public int checkExitValue(int... expectedExitValueArr)
    throws IOException, InvalidExitValueException {
        int x = checkExitValue(Ints.asList(expectedExitValueArr));
        return x;
    }
    
    /**
     * Retrieves the exit value from the child process and checks it is one of
     * {@code validExitValueCollection}.
     * 
     * @param validExitValueCollection
     *        collection of valid exit values from child process, e.g., zero, one, etc.
     * 
     * @return validated exit value
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     * @throws InvalidExitValueException
     *         if exit value is not found in {@code validExitValueCollection}
     */
    public int checkExitValue(Collection<Integer> validExitValueCollection)
    throws IOException, InvalidExitValueException {
        int exitValue = exitValue();
        _checkExitValue(exitValue, validExitValueCollection);
        return exitValue;
    }
    
    protected void _checkExitValue(int exitValue, Collection<Integer> validExitValueCollection)
    throws InvalidExitValueException {
        if (!validExitValueCollection.contains(exitValue)) {
            String argListStr = ProcessBuilder2.argListToString(_argList);
            throw new InvalidExitValueException(
                exitValue, validExitValueCollection, "Failed to run command: %s", argListStr);
        }
    }
    
    /**
     * Creates, but does not start (spawn), a new thread to read STDIN stream from a child process.
     * <p>
     * Subclasses of {@link Process2} that want to subclass {@link WriteOutputStreamThread} will
     * need to override this method.
     * 
     * @param outputStream
     *        STDIN stream handle ({@link Process#getOutputStream()})
     * @param byteArr
     * <ul>
     *   <li>array of bytes to write to STDIN stream of child process</li>
     *   <li>this is not a copy, so receiving class must <b>not</b> modify</li>
     * </ul>
     *        
     * @return new thread
     */
    protected WriteOutputStreamThread createWriteOutputStreamThread(
            OutputStream outputStream, byte[] byteArr) {
        WriteOutputStreamThread thread = new WriteOutputStreamThread(outputStream, byteArr);
        return thread;
    }
    
    /**
     * This method:
     * <ol>
     *   <li>Creates a new thread by via
     *   {@link Process2#createReadInputStreamThread(InputStream, ProcessOutputStreamSettings, String)}</li>
     *   <li>Sets the name of the thread via {@link Process2#setThreadName(Thread, String)}</li>
     *   <li>Spawns the new thread via {@link Thread#start()}</li>
     * </ol>
     */
    protected ReadInputStreamThread createReadInputStreamThreadAndStart(
            InputStream inputStream,
            ProcessOutputStreamSettings settings,
            String streamName) {
        ReadInputStreamThread thread =
            createReadInputStreamThread(inputStream, settings, streamName);
        setThreadName(thread, streamName);
        thread.start();
        return thread;
    }
    
    /**
     * Creates, but does not start (spawn), a new thread to read either STDOUT or STDERR stream
     * from a child process.
     * <p>
     * Subclasses of {@link Process2} that want to subclass {@link ReadInputStreamThread} will need
     * to override this method.
     * 
     * @param inputStream
     *        either STDOUT stream handle ({@link Process#getInputStream()})
     *        or STDERR stream handle ({@link Process#getErrorStream()})
     * @param settings
     *        either STDOUT settings ({@link Process#getErrorStream()})
     *        or STDERR settings ({@link Process2#stderrSettings()})
     * @param streamName
     *        either {@code "STDOUT"} or {@code "STDERR"}
     * 
     * @return new thread
     */
    protected ReadInputStreamThread createReadInputStreamThread(
            InputStream inputStream,
            ProcessOutputStreamSettings settings,
            String streamName) {
        ReadInputStreamThread thread =
            new ReadInputStreamThread(inputStream, settings, streamName);
        return thread;
    }
    
    /**
     * Adds descriptive text to identify a thread in a debugger.
     * 
     * @param thread
     *        new thread that has not yet started
     * @param prefix
     *        text to describe the thread, e.g., {@code "STDOUT"}
     * 
     * @throws NullPointerException
     *         if {@code thread} or {@code prefix} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code prefix} is empty or only
     *         {@linkplain Character#isWhitespace(char) whitespace}
     */
    protected void setThreadName(Thread thread, String prefix) {
        ObjectArgs.checkNotNull(thread, "thread");
        StringArgs.checkNotEmptyOrWhitespace(prefix, "prefix");
        
        String name = prefix + ": " + _argListStr;
        thread.setName(name);
    }
    
    /**
     * Forwards to {@link Process#getOutputStream()}.  Do not use this method unless you really
     * know what you are doing.  Writing to this stream directly, or -- worse -- closing it, will
     * likely cause runtime exceptions in your application.
     * 
     * @return stream handle for STDIN on child process
     */
    public OutputStream getOutputStream() {
        OutputStream x = _process.getOutputStream();
        return x;
    }

    /**
     * Forwards to {@link Process#getInputStream()}.  Do not use this method unless you really
     * know what you are doing.  Reading from this stream directly, or -- worse -- closing it, will
     * likely cause runtime exceptions in your application.
     * 
     * @return stream handle for STDOUT on child process
     */
    public InputStream getInputStream() {
        InputStream x = _process.getInputStream();
        return x;
    }

    /**
     * Forwards to {@link Process#getErrorStream()}.  Do not use this method unless you really
     * know what you are doing.  Reading from this stream directly, or -- worse -- closing it, will
     * likely cause runtime exceptions in your application.
     * 
     * @return stream handle for STDERR on child process
     */
    public InputStream getErrorStream() {
        InputStream x = _process.getErrorStream();
        return x;
    }

    /**
     * Forwards to {@link Process#exitValue()}
     * 
     * @return exit value from child process
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     * @throws IllegalThreadStateException
     *         if the child process has not yet terminated
     * 
     * @see #tryExitValue()
     * @see #waitFor()
     */
    public int exitValue()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.exitValue();
    }
    
    /**
     * Forwards to {@link Process#destroy()}.  Use this method with care.  It is not guaranteed to
     * kill the child process immediately.  On UNIX-like platforms, e.g., Linux, Solaris, HP-UX,
     * the following C code is executed: kill(pid, SIGTERM), where SIGTERM represents signal 15.
     * The process can catch this signal and gracefully shutdown.  This is less than instant.
     * Normally, SIGKILL (9) is used for an immediate, ungraceful shutdown, but is unavailable in
     * the JDK.
     * <p>
     * Further, if a tight loop with {@link Thread#yield()} and {@link #hasFinished()} is employed
     * to wait for the child process to terminate, an {@link IOException} may be thrown by
     * {@link #hasFinished()}, especially if data is being written to the STDIN stream of the child
     * process.  However, if data for STDIN has finished writing, {@link #hasFinished()} is
     * unlikely to throw an exception.
     * 
     * @return reference to {@code this}
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public Process2 destroy()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        _process.destroy();
        return this;
    }
}
