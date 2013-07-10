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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joe_e.array.ByteArray;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.AbstractThreadWithException;
import com.googlecode.kevinarpe.papaya.args.CollectionArgs;
import com.googlecode.kevinarpe.papaya.args.LongArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.obsolete.ProcessBuilder2.ByteAppendable;

public class Process2 {
    
    private final Process _process;
    private final ProcessBuilder2._ProcessSettings _processSettings;
    private final ReadInputStreamThread _readStdoutThread;
    private final ReadInputStreamThread _optReadStderrThread;
    private final WriteOutputStreamThread _optWriteStdinThread;
    
    Process2(
            Process process,
            ProcessBuilder2._ProcessSettings processSettings) {
        _process = ObjectArgs.checkNotNull(process, "process");
        _processSettings = ObjectArgs.checkNotNull(processSettings, "processSettings");
        
        InputStream stdout = _process.getInputStream();
        _readStdoutThread =
            createReadInputStreamThreadAndStart(stdout, _processSettings.stdoutSettings);
        
        if (_processSettings.redirectErrorStream) {
            _optReadStderrThread = null;
        }
        else {
            InputStream stderr = _process.getErrorStream();
            _optReadStderrThread =
                createReadInputStreamThreadAndStart(stderr, _processSettings.stderrSettings);
        }
        
        if (null == _processSettings.optStdinByteArr) {
            _optWriteStdinThread = null;
        }
        else {
            OutputStream stdin = _process.getOutputStream();
            _optWriteStdinThread =
                createWriteOutputStreamThreadAndStart(stdin, _processSettings.optStdinByteArr);
        }
    }
    
    // TODO: Make STDOUT/STDERR Charset gettable/settable in this class.
    // Create a generic base classes with getters/setters.
    // Pass subclass type as (generic) type argument.
    // Both Process2 and ProcessBuilder2 can derive from the same base class.
    
    public List<String> command() {
        return _processSettings.command;
    }
    
    public Map<String, String> environment() {
        return _processSettings.environment;
    }
    
    public File directory() {
        return _processSettings.directory;
    }
    
    public boolean redirectErrorStream() {
        return _processSettings.redirectErrorStream;
    }
    
    public boolean isRunning()
    throws IOException {
        if (null == _process) {
            return false;
        }
        Integer exitValue = tryGetExitValue();
        boolean b = (null == exitValue);
        return b;
    }
    
    public boolean hasFinished()
    throws IOException {
        if (null == _process) {
            return false;
        }
        Integer exitValue = tryGetExitValue();
        boolean b = (null != exitValue);
        return b;
    }
    
    protected void tryWriteStdinThreadRethrowCaughtException()
    throws IOException {
        if (null == _optWriteStdinThread) {
            _optWriteStdinThread.rethrowCaughtException();
        }
    }
    
    public byte[] getStdoutDataAsByteArr()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        byte[] x = _readStdoutThread.getDataAsByteArr();
        return x;
    }
    
    public String getStdoutDataAsString()
    throws IOException {
        String x = getStdoutDataAsString(null);
        return x;
    }
    
    public String getStdoutDataAsString(Charset optCs)
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        String x = _readStdoutThread.getDataAsString(optCs);
        return x;
    }
    
    public byte[] getStderrDataAsByteArr()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        byte[] x = null;
        if (null != _optReadStderrThread) {
            x = _optReadStderrThread.getDataAsByteArr();
        }
        return x;
    }
    
    public String getStderrDataAsString()
    throws IOException {
        String x = getStderrDataAsString(null);
        return x;
    }
    
    public String getStderrDataAsString(Charset optCs)
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        String x = null;
        if (null != _optReadStderrThread) {
            x = _optReadStderrThread.getDataAsString(optCs);
        }
        return x;
    }
    
    public Integer tryGetExitValue()
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
    
    public int getExitValue()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        long timeoutMillis = 0;
        int exitValue = getExitValue(timeoutMillis);
        return exitValue;
    }
    
    public Integer getExitValue(long timeoutMillis)
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        LongArgs.checkNotNegative(timeoutMillis, "timeoutMillis");
        
        Integer optExitValue = null;
        if (0 == timeoutMillis) {
            try {
                optExitValue = _process.waitFor();
            }
            catch (InterruptedException e) {
                // Intentionally ignore exception.
                @SuppressWarnings("unused")
                int dummy = 1;  // debug breakpoint
            }
        }
        else {
            // Before we create a thread (expensive), check if process has finished.
            // If yes, get the exit code and skip the thread create below.
            optExitValue = this.tryGetExitValue();
            if (null != optExitValue) {
                return optExitValue;
            }
            WaitForThread thread = WaitForThread.createAndStart(this);
            try {
                thread.join(timeoutMillis);
                optExitValue = thread.getOptExitValue();
            }
            catch (InterruptedException e) {
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
    
    public int checkExitValue()
    throws TimeoutException, InvalidExitValueException, IOException {
        long timeoutMillis = 0;
        List<Integer> validExitValueList = Arrays.asList(0);
        int exitValue = checkExitValue(timeoutMillis, validExitValueList);
        return exitValue;
    }
    
    public int checkExitValue(long timeoutMillis)
    throws TimeoutException, InvalidExitValueException, IOException {
        List<Integer> validExitValueList = Arrays.asList(0);
        int exitValue = checkExitValue(timeoutMillis, validExitValueList);
        return exitValue;
    }
    
    public int checkExitValue(Collection<Integer> validExitValueCollection)
    throws TimeoutException, InvalidExitValueException, IOException {
        long timeoutMillis = 0;
        int exitValue = checkExitValue(timeoutMillis, validExitValueCollection);
        return exitValue;
    }
    
    public int checkExitValue(long timeoutMillis, Collection<Integer> validExitValueCollection)
    throws TimeoutException, InvalidExitValueException, IOException {
        tryWriteStdinThreadRethrowCaughtException();
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            validExitValueCollection, "validExitValueCollection");
        // TODO: Add this to Papaya.
//        ArgUtil.static_check_greater_equal(validExitValueList, 0, "allowed_exit_code_array");
//        ArgUtil.static_check_less_equal(validExitValueList, 255, "allowed_exit_code_array");
        
        Integer optExitValue = getExitValue(timeoutMillis);
        if (null == optExitValue) {
            throw new TimeoutException(timeoutMillis);
        }
        else {
            if (validExitValueCollection.contains(optExitValue)) {
                return optExitValue;
            }
            String argListStr = ProcessBuilder2.argListToString(_processSettings.command);
            throw new InvalidExitValueException(
                optExitValue, validExitValueCollection, "Failed to run command: %s", argListStr);
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
    
    protected ReadInputStreamThread createReadInputStreamThreadAndStart(
            InputStream inputStream,
            ProcessBuilder2._ProcessSettings._InputStreamSettings settings) {
        ReadInputStreamThread thread = new ReadInputStreamThread(inputStream, settings);
        thread.start();
        return thread;
    }
    
    protected static class ReadInputStreamThread
    extends AbstractThreadWithException<IOException> {
        
        private final InputStream _inputStream;
        private final ProcessBuilder2._ProcessSettings._InputStreamSettings _settings;
        private final ByteArray.Builder _optByteArrBuilder;

        public ReadInputStreamThread(
                InputStream inputStream,
                ProcessBuilder2._ProcessSettings._InputStreamSettings settings) {
            _inputStream = ObjectArgs.checkNotNull(inputStream, "inputStream");
            _settings = ObjectArgs.checkNotNull(settings, "settings");
            _optByteArrBuilder = (_settings.accumulateData ? ByteArray.builder() : null);
        }
        
        protected ByteArray getByteArray() {
            if (!_settings.accumulateData) {
                throw new IllegalStateException("Incoming data was not accumulated");
            }
            synchronized (_optByteArrBuilder) {
                ByteArray x = _optByteArrBuilder.snapshot();
                return x;
            }
        }
        
        public byte[] getDataAsByteArr()
        throws IOException {
            rethrowCaughtException();
            ByteArray byteArr = getByteArray();
            byte[] x = byteArr.toByteArray();
            return x;
        }
        
        public String getDataAsString(Charset optCs)
        throws IOException {
            rethrowCaughtException();
            if (null == optCs) {
                optCs = Charset.defaultCharset();
            }
            
            ByteArray byteArr = getByteArray();
            byte[] x = byteArr.toByteArray();
            String s = new String(x, optCs);
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
                if (_settings.accumulateData) {
                    synchronized (_optByteArrBuilder) {
                        int adjReadCount = readCount;
                        if (_settings.maxAccumulateByteCount < 0
//                                && _optByteArrBuilder.length() + readCount >
//                                    _settings.maxAccumulateByteCount
                                    ) {
                            int maxReadCount =
                                _settings.maxAccumulateByteCount - _optByteArrBuilder.length();
                            adjReadCount = Math.min(readCount, maxReadCount);
                        }
                        _optByteArrBuilder.append(buffer, 0, adjReadCount);
                    }
                }
                // TODO: Make a local copy of _settings.optCharCallback
                // To be thread-safe, we cannot read twice!
                if (null != _settings.optCharCallback) {
                    String s = new String(buffer, 0, readCount, _settings.charset);
                    _settings.optCharCallback.append(s);
                }
                if (null != _settings.optByteCallback) {
                    byte[] truncBuffer = Arrays.copyOf(buffer, readCount);
                    _settings.optByteCallback.append(truncBuffer);
                }
            }
            @SuppressWarnings("unused")
            int dummy = 1;  // debug breakpoint
        }
    }

    public int hashCode() {
        return _process.hashCode();
    }

    public OutputStream getOutputStream() {
        return _process.getOutputStream();
    }

    public InputStream getInputStream() {
        return _process.getInputStream();
    }

    public InputStream getErrorStream() {
        return _process.getErrorStream();
    }

    public boolean equals(Object obj) {
        return _process.equals(obj);
    }

    public int waitFor()
    throws InterruptedException, IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.waitFor();
    }

    public int exitValue()
    throws IOException {
        tryWriteStdinThreadRethrowCaughtException();
        
        return _process.exitValue();
    }
    
    public Process2 destroy()
    throws Exception {
        tryWriteStdinThreadRethrowCaughtException();
        
        _process.destroy();
        return this;
    }

    public String toString() {
        return _process.toString();
    }
}
