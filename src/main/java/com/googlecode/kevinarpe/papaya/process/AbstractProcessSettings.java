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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.ObjectUtils;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * This is the shared base class for {@link ProcessBuilder2} and {@link Process2}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public abstract class AbstractProcessSettings {
    
    /**
     * Used by the
     * {@linkplain AbstractProcessSettings#AbstractProcessSettings(AbstractProcessSettings, ChildProcessState)
     * copy constructor} to distinguish between copies of {@link ProcessBuilder2}, where the child
     * process <b>has not</b> started, and {@link Process2}, where the child process <b>has</b>
     * started.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     * 
     * @see AbstractProcessSettings#AbstractProcessSettings(AbstractProcessSettings, ChildProcessState)
     */
    public static enum ChildProcessState {
        
        /**
         * The child process <b>has not</b> started.  Used when creating an instance of
         * {@link ProcessBuilder2}.
         */
        HAS_NOT_STARTED,
        
        /**
         * The child process <b>has</b> started.  Use when creating an instance of
         * {@link Process2}.
         */
        HAS_STARTED;
    }
    
    private final ChildProcessState _childProcessState;
    private final ProcessOutputStreamSettings _stdoutSettings;
    private final ProcessOutputStreamSettings _stderrSettings;

    protected AbstractProcessSettings() {
        _childProcessState = ChildProcessState.HAS_NOT_STARTED;
        _stdoutSettings = new ProcessOutputStreamSettings();
        _stderrSettings = new ProcessOutputStreamSettings();
    }
    
    /**
     * Copy constructor.
     * 
     * @param other
     *        reference to another instance of this class, perhaps {@link ProcessBuilder2} or
     *        {@link Process}
     * @param state
     * <ul>
     *   <li>state of child process -- started or not</li>
     *   <li>To copy {@link ProcessBuilder2}, use {@link ChildProcessState#HAS_NOT_STARTED}</li>
     *   <li>To copy {@link Process2}, use {@link ChildProcessState#HAS_STARTED}</li>
     * </ul>
     *
     * @throws NullPointerException
     *         if {@code other} or {@code state} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code state} is an unknown value
     */
    protected AbstractProcessSettings(AbstractProcessSettings other, ChildProcessState state) {
        ObjectArgs.checkNotNull(other, "other");
        ObjectArgs.checkNotNull(state, "state");
        
        if (ChildProcessState.HAS_NOT_STARTED == state) {
            _stdoutSettings = new ProcessOutputStreamSettings(other._stdoutSettings);
            _stderrSettings = new ProcessOutputStreamSettings(other._stderrSettings);
        }
        else if (ChildProcessState.HAS_STARTED == state) {
            _stdoutSettings = new ProcessOutputStreamSettingsAfterStart(other._stdoutSettings);
            _stderrSettings = new ProcessOutputStreamSettingsAfterStart(other._stderrSettings);
        }
        else {
            throw new IllegalArgumentException(String.format("Unknown child process state: '%s'",
                state.name()));
        }
        _childProcessState = state;
    }
    
    /**
     * This value is set on construction and never changes.
     * 
     * @return state of child process -- started or not
     * 
     * @see ChildProcessState
     */
    public ChildProcessState childProcessState() {
        return _childProcessState;
    }

    /**
     * Settings for STDOUT stream of child process.  Initially, settings are controlled from
     * {@link ProcessBuilder2}.  When the child process is spawned via
     * {@link ProcessBuilder2#start()}, the settings are copied by {@link Process2}.  Most settings
     * remain mutable after the child process starts.
     * 
     * @return
     * <ul>
     *   <li>reference to settings for STDOUT stream of child process</li>
     *   <li>If child process <b>has not</b> started, this is a reference to
     *   {@link ProcessOutputStreamSettings}</li>
     *   <li>If child process <b>has</b> started, this is a reference to
     *   {@link ProcessOutputStreamSettingsAfterStart}</li>
     * </ul>
     * 
     * @see #stderrSettings()
     * @see #childProcessState()
     * @see ProcessOutputStreamSettings
     * @see ProcessOutputStreamSettingsAfterStart
     */
    public ProcessOutputStreamSettings stdoutSettings() {
        return _stdoutSettings;
    }

    /**
     * Settings for STDERR stream of child process.  Initially, settings are controlled from
     * {@link ProcessBuilder2}.  When the child process is spawned via
     * {@link ProcessBuilder2#start()}, the settings are copied by {@link Process2}.  Most settings
     * remain mutable after the child process starts.
     * 
     * @return
     * <ul>
     *   <li>reference to settings for STDERR stream of child process</li>
     *   <li>If child process <b>has not</b> started, this is a reference to
     *   {@link ProcessOutputStreamSettings}</li>
     *   <li>If child process <b>has</b> started, this is a reference to
     *   {@link ProcessOutputStreamSettingsAfterStart}</li>
     * </ul>
     * 
     * @see #stdoutSettings()
     * @see #childProcessState()
     * @see ProcessOutputStreamSettings
     * @see ProcessOutputStreamSettingsAfterStart
     */
    public ProcessOutputStreamSettings stderrSettings() {
        return _stderrSettings;
    }
    
    /**
     * @see ProcessBuilder#command()
     */
    public abstract List<String> command();
    
    
    /**
     * @see ProcessBuilder#environment()
     */
    public abstract Map<String, String> environment();
    
    
    /**
     * @see ProcessBuilder#directory()
     */
    public abstract File directory();
    
    
    /**
     * @see ProcessBuilder#redirectErrorStream()
     */
    public abstract boolean redirectErrorStream();
    
    /**
     * Retrieves an array of bytes to be written to the STDIN stream of the child process.
     * <p>
     * Depending upon the implementation, this may or may not create a copy of the byte array.
     * Generally, {@link ProcessBuilder2} will return a reference to the original array to allow
     * modifications, but {@link Process2} will return a copy of the original array.
     * 
     * @return array of bytes to write to child process STDIN stream
     * 
     * @see #stdinDataRef()
     */
    public abstract byte[] stdinData();
    
    /**
     * Similar to {@link #stdinData()}, but guarantees the return value is a reference to, not a
     * copy of, the original byte array.
     * 
     * @see #stdinData()
     */
    protected abstract byte[] stdinDataRef();
    
    /**
     * Retrieves a reference to text to be written to STDIN stream of child process.
     */
    public abstract String stdinText();

    /**
     * The most beautiful {@code toString()} you ever saw.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public String toString() {
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
            "%s ["
            + "%n\tstdoutSettings()=[%s]"
            + "%n\tstderrSettings()=[%s]"
            + "%n\tcommand()=[%s]"
            + "%n\tenvironment()={%s}"
            + "%n\tdirectory()=%s"
            + "%n\tredirectErrorStream()=%s"
            + "%n\tstdinData()=%s"
            + "%n\tstdinText()=%s"
            + "%n\t]",
            getClass().getSimpleName(),
            stdoutSettings(),
            stderrSettings(),
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
            int x = byteArr[i] & 0xFF;  // safely convert a byte to an integer
            hexCharArr[0] = HEX_ARR[x >>> 4];
            hexCharArr[1] = HEX_ARR[x & 0x0F];
            String hex = new String(hexCharArr);
            hexArr[i] = hex;
        }
        result += "[" + Joiner.on(", ").join(hexArr) + "]";
        return result;
    }
    
    /**
     * Since this object is mutable, apply caution when calling this method and interpreting
     * the result.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // This order closely follows that of equals().
        int x = Objects.hashCode(
            _stdoutSettings,
            _stderrSettings,
            redirectErrorStream(),
            directory(),
            command(),
            stdinText(),
            environment());
        x = ObjectUtils.appendHashCodes(x, Arrays.hashCode(stdinDataRef()));
        return x;
    }
    
    /**
     * Since this object is mutable, apply caution when calling this method and interpreting
     * the result.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = (this == obj);
        // This also handles when obj == null.
        if (!result && obj instanceof AbstractProcessSettings) {
            AbstractProcessSettings other = (AbstractProcessSettings) obj;
            // This long, complicated expression is carefully organized from fastest-to-slowest for
            // equals testing.  Primitives appear first, followed by arrays and collections.
            result =
                // Both settings objects consist of many primitives.
                this._stdoutSettings.equals(other._stdoutSettings)
                && this._stderrSettings.equals(other._stderrSettings)
                && Objects.equal(this.redirectErrorStream(),  other.redirectErrorStream())
                && Objects.equal(this.directory(), other.directory())
                && Objects.equal(this.command(), other.command())
                && Objects.equal(this.environment(), other.environment())
                && Objects.equal(this.stdinText(), other.stdinText())
                && Arrays.equals(this.stdinDataRef(), other.stdinDataRef())
                ;
//            if (result) {
//                // From Javadocs for ProcessBuilder.environment():
//                // The returned map and its collection views may not obey the general contract of
//                // the Object.equals and Object.hashCode methods.
//                // Thus, we need to make a copy before comparing.  This is expensive!
//                result = result
//                    && Objects.equal(
//                        ImmutableMap.copyOf(this.environment()),
//                        ImmutableMap.copyOf(other.environment()));
//            }
        }
        return result;
    }
}
