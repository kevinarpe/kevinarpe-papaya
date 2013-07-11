package com.googlecode.kevinarpe.papaya;

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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.args.ArrayArgs;
import com.googlecode.kevinarpe.papaya.args.CollectionArgs;
import com.googlecode.kevinarpe.papaya.args.IntArgs;
import com.googlecode.kevinarpe.papaya.args.LongArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.ProcessBuilder2;
import com.googlecode.kevinarpe.papaya.ByteAppendable;

/**
 * Instances of this class must be created via {@link ProcessBuilder2#start()}.  An instance of
 * this class is guaranteed to track a started child process.
 * <p>
 * The following auxilliary threads may be created to manage child process input and output:
 * <ul>
 *   <li>Write data to STDIN: If data for STDIN is specified via
 *   {@link ProcessBuilder2#stdinData(byte[])} or {@link ProcessBuilder2#stdinText(String)}, a
 *   thread is spawned to write these bytes to the child process' STDIN stream.  A separate thread
 *   is used in event the operating system or child process cannot read the entire data array in a
 *   single read.</li>
 *   <li>Read data from STDOUT: A thread is always spawned to read data from the child process'
 *   STDOUT stream.</li>
 *   <li>Read data from STDERR: If STDERR is <b>not</b> redirected to STDOUT via
 *   {@link ProcessBuilder2#redirectErrorStream(boolean)}, then a thread is spawned to read data
 *   from the child process' STDERR stream.</li>
 * </ul>
 * In the standard JDK implementation of {@link Process}, the process' original argument list,
 * environment, directory, and if STDERR is redirected to STDOUT are not available.  This extended
 * version provides the following method to access immutable versions of this data:
 * <ul>
 *   <li>{@link #command()}</li>
 *   <li>{@link #environment()}</li>
 *   <li>{@link #directory()}</li>
 *   <li>{@link #redirectErrorStream()}</li>
 * </ul>
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Process2
extends AbstractProcessSettings {
    
    private final Process _process;
    private final List<String> _argList;
    private final Map<String, String> _environment;
    private final File _directory;
    private final boolean _redirectErrorStream;
    private final byte[] _optStdinByteArr;
    private final String _optStdinText;
    private final ReadInputStreamThread _readStdoutThread;
    private final ReadInputStreamThread _optReadStderrThread;
    private final WriteOutputStreamThread _optWriteStdinThread;
    
    Process2(ProcessBuilder2 pb, Process process) {
        super(ObjectArgs.checkNotNull(pb, "pb"));
        
        _process = ObjectArgs.checkNotNull(process, "process");
        _argList = ImmutableList.copyOf(pb.command());
        _environment = ImmutableMap.copyOf(pb.environment());
        _directory = pb.directory();
        _redirectErrorStream = pb.redirectErrorStream();
        byte[] optByteArr = pb.stdinData();
        _optStdinByteArr =
            (null == optByteArr ? null : Arrays.copyOf(optByteArr, optByteArr.length));
        _optStdinText = pb.stdinText();
        
        InputStream stdoutStream = _process.getInputStream();
        _readStdoutThread =
            createReadInputStreamThreadAndStart(stdoutStream, this.stdoutSettings());
        
        if (_redirectErrorStream) {
            _optReadStderrThread = null;
        }
        else {
            InputStream stderrStream = _process.getErrorStream();
            _optReadStderrThread =
                createReadInputStreamThreadAndStart(stderrStream, this.stderrSettings());
        }
        
        if (null == _optStdinByteArr) {
            _optWriteStdinThread = null;
        }
        else {
            OutputStream stdinStream = _process.getOutputStream();
            _optWriteStdinThread =
                createWriteOutputStreamThreadAndStart(stdinStream, _optStdinByteArr);
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
            _optWriteStdinThread.rethrowCaughtException();
        }
    }

    /**
     * If {@link ProcessOutputSettings#isDataAccumulated()} is {@code true} and bytes are read by
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
        
        String x = _readStdoutThread.getDataAsString(optCs);
        return x;
    }
    
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    
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
        
        byte[] x = null;
        if (null != _optReadStderrThread) {
            x = _optReadStderrThread.getDataAsByteArr();
        }
        else {
            x = EMPTY_BYTE_ARRAY;
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
     * This is a convenience method for {@link #waitFor(long)} where {@code timeoutMillis} is zero
     * (unlimited wait).
     */
    public int waitFor()
    throws IOException, InterruptedException {
        long timeoutMillis = 0;
        int exitValue = waitFor(timeoutMillis);
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
     */
    public Integer waitFor(long timeoutMillis)
    throws IOException, InterruptedException {
        tryWriteStdinThreadRethrowCaughtException();
        LongArgs.checkNotNegative(timeoutMillis, "timeoutMillis");
        
        Integer optExitValue = null;
        if (0 == timeoutMillis) {
//            try {
                optExitValue = _process.waitFor();
//            }
//            catch (InterruptedException e) {
//                // Intentionally ignore exception.
//                @SuppressWarnings("unused")
//                int dummy = 1;  // debug breakpoint
//            }
            
        }
        else {
            // Before we create a thread (expensive), check if process has finished.
            // If yes, get the exit code and skip the thread create below.
            optExitValue = this.tryExitValue();
            if (null != optExitValue) {
                return optExitValue;
            }
            WaitForThread thread = WaitForThread.createAndStart(this);
            try {
                // Normally, this does not throw InterruptedException. 
                thread.join(timeoutMillis);
                optExitValue = thread.getOptExitValue();
            }
            catch (InterruptedException e) {
                // Ignore
            }
            if (null == optExitValue) {
                thread.interrupt();
            }
        }
        return optExitValue;
    }
    
    protected static class WaitForThread
    extends AbstractThreadWithException<InterruptedException> {
        
        public static WaitForThread createAndStart(Process2 parent) {
            WaitForThread x = new WaitForThread(parent);
            x.start();
            return x;
        }
        
        protected final Process2 _parent;
        protected Integer _optExitValue;
        
        protected WaitForThread(Process2 parent) {
            _parent = ObjectArgs.checkNotNull(parent, "parent");
        }
        
        public Integer getOptExitValue() {
            return _optExitValue;
        }
        
        @Override
        public void runWithException()
        throws InterruptedException {
            _optExitValue = _parent._process.waitFor();
        }
    }
    
    /**
     * This is a convenience method for {@link #waitForThenCheckExitValue(long, Collection)} where
     * {@code timeoutMillis} is zero (unlimited wait) and the only valid exit value is zero.
     */
    public int waitForThenCheckExitValue()
    throws TimeoutException, InvalidExitValueException, IOException, InterruptedException {
        long timeoutMillis = 0;
        List<Integer> validExitValueList = Arrays.asList(0);
        int exitValue = waitForThenCheckExitValue(timeoutMillis, validExitValueList);
        return exitValue;
    }
    
    /**
     * This is a convenience method for {@link #waitForThenCheckExitValue(long, Collection)} where
     * the only valid exit value is zero.
     */
    public int waitForThenCheckExitValue(long timeoutMillis)
    throws TimeoutException, InvalidExitValueException, IOException, InterruptedException {
        List<Integer> validExitValueList = Arrays.asList(0);
        int exitValue = waitForThenCheckExitValue(timeoutMillis, validExitValueList);
        return exitValue;
    }
    
    /**
     * This is a convenience method for {@link #waitForThenCheckExitValue(long, Collection)} where
     * {@code timeoutMillis} is zero (unlimited wait).
     */
    public int waitForThenCheckExitValue(Collection<Integer> validExitValueCollection)
    throws TimeoutException, InvalidExitValueException, IOException, InterruptedException {
        long timeoutMillis = 0;
        int exitValue = waitForThenCheckExitValue(timeoutMillis, validExitValueCollection);
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
     */
    public int waitForThenCheckExitValue(
            long timeoutMillis, Collection<Integer> validExitValueCollection)
    throws TimeoutException, InvalidExitValueException, IOException, InterruptedException {
        tryWriteStdinThreadRethrowCaughtException();
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            validExitValueCollection, "validExitValueCollection");
        // TODO: Add this to Papaya.
//        ArgUtil.static_check_greater_equal(validExitValueList, 0, "allowed_exit_code_array");
//        ArgUtil.static_check_less_equal(validExitValueList, 255, "allowed_exit_code_array");
        
        Integer optExitValue = waitFor(timeoutMillis);
        if (null == optExitValue) {
            throw new TimeoutException(timeoutMillis);
        }
        else {
            _checkExitValue(optExitValue, validExitValueCollection);
            return optExitValue;
        }
    }
    
    /**
     * This is a convenience method for {@link #checkExitValue(Collection)} where the only valid
     * exit value is zero.
     */
    public int checkExitValue()
    throws IOException, InvalidExitValueException {
        int x = checkExitValue(Arrays.asList(0));
        return x;
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
    
    @SuppressWarnings("serial")
    public static class TimeoutException
    extends Exception {
        
        private final long _timeoutMillis;
        
        private static String _createMessage(long timeoutMillis) {
            LongArgs.checkNotNegative(timeoutMillis, "timeoutMillis");
            
            String x = String.format("Timeout after %d milliseconds", timeoutMillis);
            return x;
        }
        
        public TimeoutException(long timeoutMillis) {
            super(_createMessage(timeoutMillis));
            
            _timeoutMillis = timeoutMillis;
        }
        
        public long getTimeoutMillis() {
            return _timeoutMillis;
        }
    }
    
    @SuppressWarnings("serial")
    public static class InvalidExitValueException
    extends Exception {
        
        private final int _exitValue;
        private final List<Integer> _validExitValueList;
        
        private static String _createMessage(
                int exitValue,
                Collection<Integer> validExitValueCollection,
                String format,
                Object[] argArr) {
            CollectionArgs.checkNotEmptyAndElementsNotNull(
                validExitValueCollection, "validExitValueCollection");
            
            String x = String.format(format, argArr);
            x += String.format(
                "%nInvalid exit value: %d%nValid values: %s",
                exitValue,
                Joiner.on(", ").join(validExitValueCollection));
            return x;
        }
        
        public InvalidExitValueException(
                int exitValue,
                Collection<Integer> validExitValueCollection,
                String format,
                Object... argArr) {
            super(_createMessage(exitValue, validExitValueCollection, format, argArr));
            
            _exitValue = exitValue;
            _validExitValueList = ImmutableList.copyOf(validExitValueCollection);
        }

        public int getExitValue() {
            return _exitValue;
        }

        public List<Integer> getValidExitValueList() {
            return _validExitValueList;
        }
    }
    
    protected WriteOutputStreamThread createWriteOutputStreamThreadAndStart(
            OutputStream outputStream,
            byte[] byteArr) {
        WriteOutputStreamThread thread =
            new WriteOutputStreamThread(outputStream, byteArr);
        thread.start();
        return thread;
    }
    
    protected static class WriteOutputStreamThread
    extends AbstractThreadWithException<IOException> {
        
        private final OutputStream _outputStream;
        private final byte[] _byteArr;

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
        
        protected OutputStream getOutputStream() {
            return _outputStream;
        }

        protected byte[] getByteArr() {
            return _byteArr;
        }
    }
    
    /**
     * Subclasses of {@link Process2} that want to subclass {@link ReadInputStreamThread} will need
     * to override this method create an instance of the new subclass of
     * {@link ReadInputStreamThread} and call {@link Thread#start()}.
     */
    protected ReadInputStreamThread createReadInputStreamThreadAndStart(
            InputStream inputStream,
            ProcessOutputSettings settings) {
        ReadInputStreamThread thread = new ReadInputStreamThread(inputStream, settings);
        thread.start();
        return thread;
    }
    
    protected static class ByteArrayBuilder {
        
        private byte[] _byteArr;
        private int _usedLength;
        
        public ByteArrayBuilder(int initialCapacity) {
            _byteArr = new byte[initialCapacity];
            _usedLength = 0;
        }
        
        public int capacity() {
            return _byteArr.length;
        }
        
        public int length() {
            return _usedLength;
        }
        
        public ByteArrayBuilder setLength(int newLength) {
            IntArgs.checkNotNegative(newLength, "newLength");
            
            if (newLength > _usedLength) {
                ensureCapacity(newLength);
            }
            _usedLength = newLength;
            return this;
        }
        
        public ByteArrayBuilder clear() {
            _usedLength = 0;
            return this;
        }
        
        public ByteArrayBuilder append(Byte oneByte) {
            ObjectArgs.checkNotNull(oneByte, "oneByte");
            
            byte b = oneByte.byteValue();
            append(b);
            return this;
        }
        
        public ByteArrayBuilder append(byte oneByte) {
            ensureCapacity(_usedLength + 1);
            _byteArr[_usedLength] = oneByte;
            ++_usedLength;
            return this;
        }
        
        public ByteArrayBuilder append(byte[] byteArr) {
            append(byteArr, 0, byteArr.length);
            return this;
        }
        
        public ByteArrayBuilder append(byte[] byteArr, int offset, int length) {
            ArrayArgs.checkIndexAndCount(byteArr, offset, length, "byteArr", "offset", "length");
            ensureCapacity(_usedLength + length);
            System.arraycopy(byteArr, offset, _byteArr, _usedLength, length);
            _usedLength += length;
            return this;
        }
        
        protected void ensureCapacity(int newCapacity) {
            int capacity = _byteArr.length;
            while (capacity < newCapacity) {
                capacity *= 2;
            }
            _byteArr = Arrays.copyOf(_byteArr, capacity);
        }
        
        public byte[] toArray() {
            byte[] x = Arrays.copyOf(_byteArr, _usedLength);
            return x;
        }
        
        public ByteArrayBuilder copy(byte[] byteArr, int offset, int length) {
            ArrayArgs.checkIndexAndCount(byteArr, offset, length, "byteArr", "offset", "length");
            if (length > _usedLength) {
                throw new IllegalArgumentException(String.format(
                    "Argument 'length': Larger than number of available bytes: %d > %d",
                    length, _usedLength));
            }
            
            System.arraycopy(_byteArr, 0, byteArr, offset, length);
            return this;
        }
   }
    
    protected static class ReadInputStreamThread
    extends AbstractThreadWithException<IOException> {
        
        private static final int DEFAULT_BYTE_ARR_LENGTH = 8192;
        
        private final InputStream _inputStream;
        private final ProcessOutputSettings _settings;
        //private final ByteArray.Builder _byteArrBuilder;
        private final ByteArrayBuilder _byteArrBuilder;

        public ReadInputStreamThread(
                InputStream inputStream,
                ProcessOutputSettings settings) {
            _inputStream = ObjectArgs.checkNotNull(inputStream, "inputStream");
            _settings = ObjectArgs.checkNotNull(settings, "settings");
            _byteArrBuilder = new ByteArrayBuilder(DEFAULT_BYTE_ARR_LENGTH);
        }
        
        protected byte[] getByteArr() {
            synchronized (_byteArrBuilder) {
//                ByteArray x = _byteArrBuilder.snapshot();
//                byte[] x2 = x.toByteArray();
//                return x2;
                byte[] x = _byteArrBuilder.toArray();
                return x;
            }
        }
        
        public byte[] getDataAsByteArr()
        throws IOException {
            rethrowCaughtException();
            byte[] x = getByteArr();
            return x;
        }
        
        public String getDataAsString(Charset optCs)
        throws IOException {
            rethrowCaughtException();
            
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
                    }
                }
                // Make a reference copy here to be thread-safe.
                Appendable optCharCallback = _settings.charCallback();
                if (null != optCharCallback) {
                    Charset cs = _settings.charset();
                    String s = new String(buffer, 0, readCount, cs);
                    optCharCallback.append(s);
                }
                // Make a reference copy here to be thread-safe.
                ByteAppendable optByteCallback = _settings.byteCallback();
                if (null != optByteCallback) {
                    byte[] truncBuffer = Arrays.copyOf(buffer, readCount);
                    optByteCallback.append(truncBuffer);
                }
            }
            @SuppressWarnings("unused")
            int dummy = 1;  // debug breakpoint
        }

        protected InputStream getInputStream() {
            return _inputStream;
        }

        protected ProcessOutputSettings getSettings() {
            return _settings;
        }

        protected ByteArrayBuilder getByteArrBuilder() {
            return _byteArrBuilder;
        }
    }

    /**
     * Forwards to {@link Process#getOutputStream()}
     * 
     * @return stream handle for STDIN on child process
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public OutputStream getOutputStream()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.getOutputStream();
    }

    /**
     * Forwards to {@link Process#getInputStream()}
     * 
     * @return stream handle for STDOUT on child process
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public InputStream getInputStream()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.getInputStream();
    }

    /**
     * Forwards to {@link Process#getErrorStream()}
     * 
     * @return stream handle for STDERR on child process
     * 
     * @throws IOException
     *         if an error occurred when writing data to STDIN (rethrown from STDIN thread)
     */
    public InputStream getErrorStream()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.getErrorStream();
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
     */
    public int exitValue()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.exitValue();
    }
    
    /**
     * Forwards to {@link Process#destroy()}
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
