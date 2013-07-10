package com.googlecode.kevinarpe.papaya;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.args.IntArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.Process2;
import com.googlecode.kevinarpe.papaya.ByteAppendable;

/**
 * This is the shared base class for {@link ProcessBuilder2} and {@link Process2}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
abstract class AbstractProcessSettings {
    
    private final ProcessOutputSettings _stdoutSettings;
    private final ProcessOutputSettings _stderrSettings;

    protected AbstractProcessSettings() {
        _stdoutSettings = new ProcessOutputSettings();
        _stderrSettings = new ProcessOutputSettings();
    }
    
    protected AbstractProcessSettings(AbstractProcessSettings x) {
        _stdoutSettings = new ProcessOutputSettings(x._stdoutSettings);
        _stderrSettings = new ProcessOutputSettings(x._stderrSettings);
    }

    public ProcessOutputSettings stdoutSettings() {
        return _stdoutSettings;
    }

    public ProcessOutputSettings stderrSettings() {
        return _stderrSettings;
    }
    
    public abstract List<String> command();
    
    public abstract Map<String, String> environment();
    
    public abstract File directory();
    
    public abstract boolean redirectErrorStream();
    
    public abstract byte[] stdinData();
    
    protected abstract byte[] stdinDataRef();
    
    public abstract String stdinText();

    /**
     * The most beautiful {@code toString()} you ever saw.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // TODO: Add stdout/stderrSettings!
        String commandStr = Joiner.on("', '").join(command());
        if (!commandStr.isEmpty()) {
            commandStr = "'" + commandStr + "'";
        }
        
        Map<String, String> envMap = environment();
        String envStr = Joiner.on("', '").withKeyValueSeparator(" => ").join(envMap);
        if (!envStr.isEmpty()) {
            envStr = "'" + envStr + "'";
        }
        
        File dirPath = directory();
        String x = String.format(
            "%s [%n\tcommand()=[%s],%n\tenvironment()={%s},%n\tdirectory()=%s,%n\tredirectErrorStream()=%s,%n\tstdinData()=%s,%n\tstdinText()=%s%n\t]",
            getClass().getSimpleName(),
            commandStr,
            envStr,
            (null == dirPath ? "null" : "'" + dirPath.getAbsolutePath() + "'"),
            redirectErrorStream(),
            _stdinByteArrToString(),
            _stdinTextToString());
        return x;
    }
    
    private final int MAX_STDIN_TEXT_SAMPLE_LENGTH = 256;
    private final int MAX_STDIN_BYTE_ARR_SAMPLE_LENGTH = MAX_STDIN_TEXT_SAMPLE_LENGTH / 2;
    
    private String _stdinTextToString() {
        String optStdinText = stdinText();
        if (null == optStdinText) {
            return "null";
        }
        int len = optStdinText.length();
        if (len <= MAX_STDIN_TEXT_SAMPLE_LENGTH) {
            return "'" + optStdinText + "'";
        }
        String sample = String.format("(sample: %d of %d chars) '%s'",
            MAX_STDIN_TEXT_SAMPLE_LENGTH,
            len,
            optStdinText.substring(0, MAX_STDIN_TEXT_SAMPLE_LENGTH));
        return sample;
    }
    
    private static final char[] HEX_ARR = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    
    private String _stdinByteArrToString() {
        byte[] optStdinByteArr = stdinDataRef();
        if (null == optStdinByteArr) {
            return "null";
        }
        byte[] byteArr = optStdinByteArr;
        String result = "";
        if (optStdinByteArr.length > MAX_STDIN_BYTE_ARR_SAMPLE_LENGTH) {
            byteArr = Arrays.copyOf(byteArr, MAX_STDIN_BYTE_ARR_SAMPLE_LENGTH);
            result = String.format("(sample: %d of %d bytes) ",
                MAX_STDIN_BYTE_ARR_SAMPLE_LENGTH, optStdinByteArr.length);
        }
        String[] hexArr = new String[byteArr.length];
        char[] hexCharArr = new char[2];
        for (int i = 0; i < byteArr.length; ++i) {
            // Ref: http://stackoverflow.com/a/9855338/257299
            int x = byteArr[i] & 0xFF;
            hexCharArr[0] = HEX_ARR[x >>> 4];
            hexCharArr[1] = HEX_ARR[x & 0x0F];
            String hex = new String(hexCharArr);
            hexArr[i] = hex;
        }
        result += "[" + Joiner.on(", ").join(hexArr) + "]";
        return result;
    }
    
//    @Override
//    public int hashCode() {
//        int x = Objects.hashCode(_stdoutSettings, _stderrSettings);
//        return x;
//    }

    // TODO: Make this generic?  Borrow more logic from ProcessBuilder2
    
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        // This handles when obj == null also.
        if (obj instanceof AbstractProcessSettings) {
            AbstractProcessSettings other = (AbstractProcessSettings) obj;
            result =
                    Objects.equal(this._stdoutSettings, other._stdoutSettings)
                    && Objects.equal(this._stderrSettings, other._stderrSettings)
                    ;
        }
        return result;
    }

    /**
     * One for STDOUT and another for STDERR.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     */
    public static class ProcessOutputSettings {
        
        private Charset _charset;
        private Appendable _optCharCallback;
        private ByteAppendable _optByteCallback;
        private boolean _isDataAccumulated;
        private int _maxAccumulatedDataByteCount;

        public ProcessOutputSettings() {
            _charset = Charset.defaultCharset();
            _isDataAccumulated = false;
            _maxAccumulatedDataByteCount = -1;
        }
        
        public ProcessOutputSettings(ProcessOutputSettings x) {
            this._charset = x._charset;
            this._optCharCallback = x._optCharCallback;
            this._optByteCallback = x._optByteCallback;
            this._isDataAccumulated = x._isDataAccumulated;
            this._maxAccumulatedDataByteCount = x._maxAccumulatedDataByteCount;
        }
        
        /**
         * Sets the {@link Charset} used to convert bytes read from STDOUT or STDERR to
         * {@link String}.  The default value is {@link Charset#defaultCharset()}, and works
         * correctly in the vast majority of use cases.
         * 
         * @return reference to {@code this}
         * 
         * @throws NullPointerException
         *         if {@code cs} is {@code null}
         *         
         * @see Charset#defaultCharset()
         * @see #_charset
         */
        public ProcessOutputSettings charset(Charset cs) {
            ObjectArgs.checkNotNull(cs, "cs");
            
            _charset = cs;
            return this;
        }
        
        /**
         * Retrieves the {@link Charset} used to convert bytes read from STDOUT or STDERR to
         * {@link String}.  The initial value is {@link Charset#defaultCharset()}.
         *         
         * @see Charset#defaultCharset()
         * @see #charset(Charset)
         */
        public Charset charset() {
            return _charset;
        }
        
        /**
         * Sets the optional character-based callback for STDOUT or STDERR.  As bytes are received
         * from STDOUT or STDERR, they are converted to {@link String} using {@link Charset} from
         * {@link #charset()}, then appended to this callback.
         * <p>
         * If incoming data needs to be processed in byte-based form, use
         * {@link #byteCallback(ByteAppendable)}.  The callbacks for character- and byte-based data
         * are mutually exclusive.  Zero, one, or both may be employed.
         * <p>
         * It is not a requirement to set a callback to receive incoming data from STDOUT or
         * STDERR.  Alternatively, there is a setting to accumulate all incoming data via
         * {@link #isDataAccumulated(boolean)}.  This feature is also independent of callbacks to
         * process incoming data.  This means it is possible to process data from both STDOUT and
         * STDERR simultaneously (and separately) in both character- and byte-based form.  If the
         * accumulator feature is enabled, after the process has started, call one of these methods
         * to access the accumulated data:
         * <ul>
         *   <li>STDOUT:</li>
         *   <ul>
         *     <li>{@link Process2#stdoutDataAsByteArr()}</li>
         *     <li>{@link Process2#stdoutDataAsString()}</li>
         *     <li>{@link Process2#stdoutDataAsString(Charset)}</li>
         *   </ul>
         *   <li>STDERR:</li>
         *   <ul>
         *     <li>{@link Process2#stderrDataAsByteArr()}</li>
         *     <li>{@link Process2#stderrDataAsString()}</li>
         *     <li>{@link Process2#stderrDataAsString(Charset)}</li>
         *   </ul>
         * </ul>
         * A separate thread always is used by {@link Process2} to read STDOUT or STDERR.  Thus,
         * incoming data is appended to the callback from a different thread than that used to
         * start the process.  This may be important for specialised implementations of
         * {@link Appendable}.
         * <p>
         * If {@link ProcessBuilder2#redirectErrorStream()} is enabled, all data from STDERR is
         * redirected to STDOUT.  This may help to simplify processing logic, at the expense of
         * distinguishing from STDOUT and STDERR data.
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
         * @see #byteCallback()
         * @see #isDataAccumulated(boolean)
         * @see #redirectErrorStream()
         */
        public ProcessOutputSettings charCallback(Appendable optCallback) {
            if (optCallback instanceof StringBuilder) {
                throw new IllegalArgumentException(String.format(
                    "Arg 'optCallback': Cannot be an instance of type StringBuilder, which is not thread-safe."
                    + "%nUse class StringBuffer instead"));
            }
            
            _optCharCallback = optCallback;
            return this;
        }
        
        /**
         * Retrieves the character-based callback for STDOUT or STDERR.  Default value is
         * {@code null}.
         * 
         * @return may be {@code null}
         * 
         * @see #charCallback(Appendable)
         * @see #isDataAccumulated()
         */
        public Appendable charCallback() {
            return _optCharCallback;
        }
        
        /**
         * Sets the optional byte-based callback for STDOUT or STDERR.  As bytes are received from
         * STDOUT or STDERR, they are appended to this callback.
         * <p>
         * If incoming data needs to be processed in character-based form, use
         * {@link #charCallback(Appendable)}.  The callbacks for character- and byte-based data are
         * mutually exclusive.  Zero, one, or both may be employed.
         * <p>
         * It is not a requirement to set a callback to receive incoming data from STDOUT or
         * STDERR.  Alternatively, there is a setting to accumulate all incoming data via
         * {@link #isDataAccumulated(boolean)}.  This feature is also independent of callbacks to
         * process incoming data.  This means it is possible to process data from both STDOUT and
         * STDERR simultaneously (and separately) in both character- and byte-based form.  If the
         * accumulator feature is enabled, after the process has started, call one of these methods
         * to access the accumulated data:
         * <ul>
         *   <li>STDOUT:</li>
         *   <ul>
         *     <li>{@link Process2#stdoutDataAsByteArr()}</li>
         *     <li>{@link Process2#stdoutDataAsString()}</li>
         *     <li>{@link Process2#stdoutDataAsString(Charset)}</li>
         *   </ul>
         *   <li>STDERR:</li>
         *   <ul>
         *     <li>{@link Process2#stderrDataAsByteArr()}</li>
         *     <li>{@link Process2#stderrDataAsString()}</li>
         *     <li>{@link Process2#stderrDataAsString(Charset)}</li>
         *   </ul>
         * </ul>
         * A separate thread always is used by {@link Process2} to read STDOUT or STDERR.  Thus,
         * incoming data is appended to the callback from a different thread than that used to
         * start the process.  This may be important for specialised implementations of
         * {@link Appendable}.
         * <p>
         * If {@link #isDataAccumulated()} is enabled, all data from STDERR is redirected to
         * STDOUT or STDERR.  This may help to simplify processing logic, at the expense of
         * distinguishing from STDOUT and STDERR data.
         * 
         * @param optCallback
         * <ul>
         *   <li>(optional) ByteAppendable reference to receive incoming data from STDOUT or
         *   STDERR.</li>
         *   <li>May be {@null}.</li>
         * </ul>
         *        
         * @return reference to {@code this}
         * 
         * @see #byteCallback()
         * @see #isDataAccumulated(boolean)
         * @see #redirectErrorStream()
         */
        public ProcessOutputSettings byteCallback(ByteAppendable optCallback) {
            _optByteCallback = optCallback;
            return this;
        }
        
        /**
         * Retrieves the byte-based callback for STDOUT.  Default value is {@code null}.
         * 
         * @return may be {@code null}
         * 
         * @see #byteCallback(ByteAppendable)
         * @see #isDataAccumulated()
         */
        public ByteAppendable byteCallback() {
            return _optByteCallback;
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
        public ProcessOutputSettings isDataAccumulated(boolean b) {
            _isDataAccumulated = b;
            return this;
        }
        
        /**
         * Retrieves the setting for the data accumulation feature for STDOUT stream.  The default
         * value is {@code false}.
         */
        public boolean isDataAccumulated() {
            return _isDataAccumulated;
        }
        
        /**
         * Sets the maximum number of bytes to accumulate from STDOUT stream.  This feature is only
         * relevant if {@link #accumulateStdoutData()} is enabled.
         * <p>
         * It is very important to enable this feature in parallel with
         * {@link #accumulateStdoutData()}.  It is easy for a errant process to produce gigabytes
         * of data on STDOUT or STDERR and exhaust all available memory for the parent Java virtual
         * machine.
         * 
         * @param max
         *        any value except zero.  Negative value implies this feature is disabled.
         * 
         * @return reference to {@code this}
         * 
         * @see #maxAccumulatedDataByteCount()
         * @see #isDataAccumulated()
         */
        public ProcessOutputSettings maxAccumulatedDataByteCount(int max) {
            IntArgs.checkNotExactValue(max, 0, "max");
            
            _maxAccumulatedDataByteCount = max;
            return this;
        }
        
        /**
         * Retrieves the maximum number of bytes to accumulate from STDOUT or STDERR stream.  This
         * feature is only relevant if {@link #accumulateStdoutData()} is enabled.
         *
         * @return if negative, this feature is disabled
         * 
         * @see #maxAccumulatedDataByteCount(int)
         * @see #isDataAccumulated()
         */
        public int maxAccumulatedDataByteCount() {
            return _maxAccumulatedDataByteCount;
        }
        
//        @Override
//        public int hashCode() {
//            int x = Objects.hashCode(
//                _charset,
//                _optCharCallback,
//                _optByteCallback,
//                _isDataAccumulated,
//                _maxAccumulatedDataByteCount);
//            return x;
//        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            // This handles when obj == null also.
            if (obj instanceof ProcessOutputSettings) {
                ProcessOutputSettings other = (ProcessOutputSettings) obj;
                result =
                        (this._isDataAccumulated == other._isDataAccumulated)
                        && (this._maxAccumulatedDataByteCount == other._maxAccumulatedDataByteCount)
                        && Objects.equal(this._charset, other._charset)
                        && Objects.equal(this._optCharCallback, other._optCharCallback)
                        && Objects.equal(this._optByteCallback, other._optByteCallback)
                        ;
            }
            return result;
        }
    }
}
