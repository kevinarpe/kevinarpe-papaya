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

import java.nio.charset.Charset;
import java.util.regex.Pattern;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.googlecode.kevinarpe.papaya.GenericFactory;
import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.annotations.FullyTested;
import com.googlecode.kevinarpe.papaya.appendable.AbstractSimplifiedAppendable;
import com.googlecode.kevinarpe.papaya.appendable.AppendablePrintLineWithPrefix;
import com.googlecode.kevinarpe.papaya.appendable.ByteAppendable;
import com.googlecode.kevinarpe.papaya.args.IntArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

/**
 * This simple class holds the settings for child process output streams (either STDOUT or
 * STDERR).  The parent class, {@link AbstractProcessSettings}, has two instances: one each for
 * STDOUT and STDERR.
 * <p>
 * This class may not be instantiated directly.  Normally, only class
 * {@link AbstractProcessSettings} creates instances.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class ProcessOutputStreamSettings {
    
    static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    static final Pattern DEFAULT_SPLIT_REGEX = StringUtils.NEW_LINE_REGEX;
    static final Appendable DEFAULT_CHAR_CALLBACK = null;
    static final GenericFactory<Appendable> DEFAULT_CHAR_CALLBACK_FACTORY = null;
    static final ByteAppendable DEFAULT_BYTE_CALLBACK = null;
    static final GenericFactory<ByteAppendable> DEFAULT_BYTE_CALLBACK_FACTORY = null;
    static final boolean DEFAULT_IS_DATA_ACCUMULATED = false;
    static final int DEFAULT_MAX_ACCUMULATED_DATA_BYTE_COUNT = -1;
    
    private Charset _charset;
    private Pattern _optSplitRegex;
    private Appendable _optCharCallback;
    private GenericFactory<Appendable> _optCharCallbackFactory;
    private ByteAppendable _optByteCallback;
    private GenericFactory<ByteAppendable> _optByteCallbackFactory;
    private boolean _isDataAccumulated;
    private int _maxAccumulatedDataByteCount;

    ProcessOutputStreamSettings() {
        _charset = DEFAULT_CHARSET;
        _optSplitRegex = DEFAULT_SPLIT_REGEX;
        _optCharCallback = DEFAULT_CHAR_CALLBACK;
        _optCharCallbackFactory = DEFAULT_CHAR_CALLBACK_FACTORY;
        _optByteCallback = DEFAULT_BYTE_CALLBACK;
        _optByteCallbackFactory = DEFAULT_BYTE_CALLBACK_FACTORY;
        _isDataAccumulated = DEFAULT_IS_DATA_ACCUMULATED;
        _maxAccumulatedDataByteCount = DEFAULT_MAX_ACCUMULATED_DATA_BYTE_COUNT;
    }
    
    /**
     * Copies another instance.
     */
    ProcessOutputStreamSettings(ProcessOutputStreamSettings x) {
        ObjectArgs.checkNotNull(x, "x");
        
        this._charset = x._charset;
        this._optSplitRegex = x._optSplitRegex;
        this._optCharCallback = x._optCharCallback;
        this._optCharCallbackFactory = x._optCharCallbackFactory;
        this._optByteCallback = x._optByteCallback;
        this._optByteCallbackFactory = x._optByteCallbackFactory;
        this._isDataAccumulated = x._isDataAccumulated;
        this._maxAccumulatedDataByteCount = x._maxAccumulatedDataByteCount;
    }
    
    /**
     * Sets the {@link Charset} used to convert bytes read from STDOUT or STDERR to
     * {@link String}.  The default value is {@link Charset#defaultCharset()}, and works
     * correctly in the vast majority of use cases.
     * <p>
     * This feature is only relevant if {@link #charCallback(Appendable)} is set to a
     * non-{@code null} value.
     * 
     * @return reference to {@code this}
     * 
     * @throws NullPointerException
     *         if {@code cs} is {@code null}
     *         
     * @see Charset#defaultCharset()
     * @see #_charset
     */
    public ProcessOutputStreamSettings charset(Charset cs) {
        ObjectArgs.checkNotNull(cs, "cs");
        
        _charset = cs;
        return this;
    }
    
    /**
     * Retrieves the {@link Charset} used to convert bytes read from STDOUT or STDERR to
     * {@link String}.  The initial value is {@link Charset#defaultCharset()}.
     * <p>
     * This feature is only relevant if {@link #charCallback(Appendable)} is set to a
     * non-{@code null} value.
     * 
     * @see Charset#defaultCharset()
     * @see #charset(Charset)
     */
    public Charset charset() {
        return _charset;
    }
    
    /**
     * Sets the optional {@link Pattern} used to split incoming characters.  The initial value
     * is {@link StringUtils#NEW_LINE_REGEX}.
     * <p>
     * If this feature is enabled and a character-based callback is set, as bytes are read from
     * the child process stream handle, they are converted to {@link String} using
     * {@link Charset} from {@link #charset()}.  Then, the resulting {@code String} is then
     * split using this regular expression using {@link Splitter}.  Important note: The text
     * matched by the regular expression is discarded!  Remaining characters are saved between
     * stream reads until the regular expression matches.  When the child process terminates,
     * all remaining characters are written to the character-based callback.
     * <p>
     * If the description above confuses, follow this example:
     * <ul>
     *   <li>Pattern is "\n" (UNIX newline)</li>
     *   <li>Remaining text is ""</li>
     *   <li>Read #1: {@code "abc\ndef"}</li>
     *   <ul>
     *     <li>Prepend remaining text ({@code ""}): {@code "abc\ndef"}</li>
     *     <li>Split to: {@code "abc"} and {@code "def"}</li>
     *     <li>Write {@code "abc"} to char callback</li>
     *     <li>Remaining text is {@code "def"}</li>
     *   </ul>
     *   <li>Read #2: {@code "\nghi\n"}</li>
     *   <ul>
     *     <li>Prepend remaining text ({@code "def"}): {@code "def\nghi\n"}</li>
     *     <li>Split to: {@code "def"}, {@code "ghi"}, and {@code ""}</li>
     *     <li>Write {@code "def"} to char callback</li>
     *     <li>Then, write {@code "ghi"} to char callback</li>
     *     <li>Remaining text is {@code ""}</li>
     *   </ul>
     *   <li>Read #3: {@code "jkl"}</li>
     *   <ul>
     *     <li>Prepend remaining text ({@code ""}): {@code "jkl"}</li>
     *     <li>Split to: {@code "jkl"}</li>
     *     <li>Write nothing to char callback</li>
     *     <li>Remaining text is {@code "jkl"}</li>
     *   </ul>
     *   <li>Read #4: {@code "mno"}</li>
     *   <ul>
     *     <li>Prepend remaining text ({@code "jkl"}): {@code "jklmno"}</li>
     *     <li>Split to: {@code "jklmno"}</li>
     *     <li>Write nothing to char callback</li>
     *     <li>Remaining text is {@code "jklmno"}</li>
     *   </ul>
     *   <li>Child process terminates</li>
     *   <li>Write "jklmno" to char callback</li>
     * </ul>
     * This feature is only relevant if {@link #charCallback(Appendable)} is set to a
     * non-{@code null} value.
     * 
     * @param optRegex
     * <ul>
     *   <li>optional regular expression to split incoming characters</li>
     *   <li>May be {@code null}</li>
     *   <li>Must not match the empty string</li>
     * </ul>
     * 
     * @return reference to {@code this}
     * 
     * @throws IllegalArgumentException
     *         if {@code optRegex} is non-{@code null} and matches the empty string
     * 
     * @see #splitRegex()
     * @see StringUtils#NEW_LINE_REGEX
     * @see #charset(Charset)
     * @see #charCallback(Appendable)
     */
    public ProcessOutputStreamSettings splitRegex(Pattern optRegex) {
        if (null != optRegex && optRegex.matcher("").matches()) {
            throw new IllegalArgumentException(String.format(
                "Argument 'optRegex' matches empty string: '%s'", optRegex));
        }
        
        _optSplitRegex = optRegex;
        return this;
    }
    
    /**
     * Retrieves the optional {@link Pattern} used to split incoming characters.  The initial
     * value is {@link StringUtils#NEW_LINE_REGEX}.
     * <p>
     * This feature is only relevant if {@link #charCallback(Appendable)} is set to a
     * non-{@code null} value.
     * 
     * @return (optional) regular expression used to split incoming characters.
     *         May be {@code null}
     * 
     * @see #splitRegex(Pattern)
     * @see #charset()
     * @see #charCallback()
     */
    public Pattern splitRegex() {
        return _optSplitRegex;
    }
    
    /**
     * Sets the optional character-based callback for STDOUT or STDERR.  As bytes are received
     * from STDOUT or STDERR, they are converted to {@link String} using {@link Charset} from
     * {@link #charset()}, then appended to this callback.  The initial character-based
     * callback is {@code null}.
     * <p>
     * If incoming data needs to be processed in byte-based form, use
     * {@link #byteCallback(ByteAppendable)}.  The callbacks for character- and byte-based data
     * are mutually exclusive.  Neither, one, or both may be employed.
     * <p>
     * It is <b>not</b> a requirement to set a callback to receive incoming data from STDOUT or
     * STDERR.  Alternatively, there is a setting to accumulate all incoming data from STDOUT
     * or STDERR via {@link #isDataAccumulated(boolean)}.  This feature is also independent of
     * callbacks to process incoming data.
     * <p>
     * A separate thread always is used by {@link Process2} to read STDOUT or STDERR.  Thus,
     * incoming data is appended to the callback from a different thread than that used to
     * start the process.  This may be important for specialised implementations of
     * {@link Appendable}.  As a special case, this method does not accept
     * {@link StringBuilder}, as it is not thread-safe.  Use {@link StringBuffer} instead.
     * <p>
     * If {@link ProcessBuilder2#redirectErrorStream()} is enabled, all data from STDERR is
     * redirected to STDOUT.  This may help to simplify processing logic, at the expense of
     * distinguishing from STDOUT and STDERR data.
     * <p>
     * The {@link Appendable} interface is somewhat cumbersome with three methods.  There is a
     * simplified version to reduce boilerplate code: {@link AbstractSimplifiedAppendable}.
     * Also, there is a full sample implementation: {@link AppendablePrintLineWithPrefix}.
     * <p>
     * If the character-based callback has state, such as {@link StringBuffer}, and multiple child
     * processes are started from a single {@link ProcessBuilder2}, a factory should instead
     * be set via {@link #charCallbackFactory(GenericFactory)}.
     * <p>
     * All calls to this method, which do not throw exceptions, clear previous settings to
     * {@link #charCallbackFactory(GenericFactory)}.
     * 
     * @param optCallback
     * <ul>
     *   <li>(optional) Appendable reference to receive incoming data from STDOUT.</li>
     *   <li>Must not be an instance of {@link StringBuilder}, which is not thread-safe.
     *   Instead use {@link StringBuffer}.</li>
     *   <li>May be {@code null}.</li>
     * </ul>
     * 
     * @return reference to {@code this}
     * 
     * @throws IllegalArgumentException
     *         if {@code optCallback} is an instance of {@link StringBuilder}
     * 
     * @see #charset(Charset)
     * @see #splitRegex(Pattern)
     * @see #charCallbackFactory(GenericFactory)
     * @see #charCallback()
     * @see #byteCallback(ByteAppendable)
     * @see #isDataAccumulated(boolean)
     * @see #redirectErrorStream()
     */
    public ProcessOutputStreamSettings charCallback(Appendable optCallback) {
        if (optCallback instanceof StringBuilder) {
            throw new IllegalArgumentException(String.format(
                "Arg 'optCallback': Cannot be an instance of type StringBuilder, which is not thread-safe."
                + "%nUse class %s instead",
                StringBuffer.class.getSimpleName()));
        }
        
        _optCharCallbackFactory = null;
        _optCharCallback = optCallback;
        return this;
    }
    
    /**
     * Retrieves the character-based callback for STDOUT or STDERR.  Initial value is
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
     * Sets the factory to create a new character-based callback.  The initial value is
     * {@code null}.
     * <p>
     * If the character-based callback has state, such as {@link StringBuffer}, and multiple child
     * processes are started from a single {@link ProcessBuilder2}, a factory should instead
     * be used.
     * <p>
     * All calls to this method clear previous settings to {@link #charCallback(Appendable)}.
     * 
     * @param optCallbackFactory
     *        optional character-based callback factory.  May be {@code null}.
     *        
     * @return reference to {@code this}
     * 
     * @see #charset(Charset)
     * @see #splitRegex(Pattern)
     * @see #charCallback(Appendable)
     * @see #charCallbackFactory()
     * @see #byteCallback(ByteAppendable)
     * @see #isDataAccumulated(boolean)
     * @see #redirectErrorStream()
     */
    public ProcessOutputStreamSettings charCallbackFactory(
            GenericFactory<Appendable> optCallbackFactory) {
        _optCharCallback = null;
        _optCharCallbackFactory = optCallbackFactory;
        return this;
    }
    
    /**
     * Retrieves the character-based callback factory for STDOUT or STDERR.  Initial value is
     * {@code null}.
     * 
     * @return may be {@code null}
     * 
     * @see #charCallbackFactory(GenericFactory)
     * @see #isDataAccumulated()
     */
    public GenericFactory<Appendable> charCallbackFactory() {
        return _optCharCallbackFactory;
    }
    
    /**
     * Sets the optional byte-based callback for STDOUT or STDERR.  As bytes are received from
     * STDOUT or STDERR, they are appended to this callback.
     * <p>
     * If incoming data needs to be processed in character-based form, use
     * {@link #charCallback(Appendable)}.  The callbacks for character- and byte-based data are
     * mutually exclusive.  Neither, one, or both may be employed.
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
     * <p>
     * If the byte-based callback has state and multiple child processes are started from a single
     * {@link ProcessBuilder2}, a factory should instead be set via
     * {@link #byteCallbackFactory(GenericFactory)}.
     * <p>
     * All calls to this method, which do not throw exceptions, clear previous settings to
     * {@link #byteCallbackFactory(GenericFactory)}.
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
    public ProcessOutputStreamSettings byteCallback(ByteAppendable optCallback) {
        _optByteCallbackFactory = null;
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
     * Sets the factory to create a new byte-based callback.  The initial value is {@code null}.
     * <p>
     * If the byte-based callback has state and multiple child
     * processes are started from a single {@link ProcessBuilder2}, a factory should instead
     * be used.
     * <p>
     * All calls to this method clear previous settings to {@link #byteCallback(ByteAppendable)}.
     * 
     * @param optCallbackFactory
     *        optional byte-based callback factory.  May be {@code null}.
     *        
     * @return reference to {@code this}
     * 
     * @see #charset(Charset)
     * @see #splitRegex(Pattern)
     * @see #charCallback(Appendable)
     * @see #byteCallback(ByteAppendable)
     * @see #byteCallbackFactory()
     * @see #isDataAccumulated(boolean)
     * @see #redirectErrorStream()
     */
    public ProcessOutputStreamSettings byteCallbackFactory(
            GenericFactory<ByteAppendable> optCallbackFactory) {
        _optByteCallback = null;
        _optByteCallbackFactory = optCallbackFactory;
        return this;
    }
    
    /**
     * Retrieves the byte-based callback factory for STDOUT or STDERR.  Initial value is
     * {@code null}.
     * 
     * @return may be {@code null}
     * 
     * @see #byteCallbackFactory(GenericFactory)
     * @see #isDataAccumulated()
     */
    public GenericFactory<ByteAppendable> byteCallbackFactory() {
        return _optByteCallbackFactory;
    }
    
    /**
     * Sets whether or not the child process manager should accumulate incoming data from this
     * stream (either STDOUT or STDERR).  The default setting is disabled ({@code false}).
     * This feature may be controller from the builder ({@link ProcessBuilder2}) or the child
     * process ({@link Process2}).  After the child process has started, it is still possible
     * to enable or disable this feature.
     * <p>
     * If this feature is enabled, it is crucial to also set
     * {@link #maxAccumulateStdoutByteCount(int)}.  By default, an unlimited amount of data is
     * accumulated.  If a large number of processes are launched simultaneously and each
     * outputs a large amount of data on STDOUT or STDERR, the parent Java Virtual Machine may
     * easily exhaust available memory.
     * <p>
     * If this feature is enabled, call one of these methods to access the accumulated data
     * while the child process is running or after it has terminated:
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
    public ProcessOutputStreamSettings isDataAccumulated(boolean b) {
        _isDataAccumulated = b;
        return this;
    }
    
    /**
     * Retrieves the setting for the data accumulation feature for this stream (either STDOUT
     * or STDERR).  The default value is {@code false}.
     */
    public boolean isDataAccumulated() {
        return _isDataAccumulated;
    }
    
    /**
     * Sets the maximum number of bytes to accumulate from this stream (either STDOUT or
     * STDERR).  This feature is only relevant if {@link #accumulateStdoutData()} is enabled.
     * <p>
     * It is very important to configure this feature in parallel with
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
    public ProcessOutputStreamSettings maxAccumulatedDataByteCount(int max) {
        IntArgs.checkNotExactValue(max, 0, "max");
        
        _maxAccumulatedDataByteCount = max;
        return this;
    }
    
    /**
     * Retrieves the maximum number of bytes to accumulate from this stream (either STDOUT or
     * STDERR).  This feature is only relevant if {@link #accumulateStdoutData()} is enabled.
     *
     * @return if negative, this feature is disabled
     * 
     * @see #maxAccumulatedDataByteCount(int)
     * @see #isDataAccumulated()
     */
    public int maxAccumulatedDataByteCount() {
        return _maxAccumulatedDataByteCount;
    }
    
    // TODO: Add char/byteCallbackFactory
    @Override
    public String toString() {
        String x = String.format(
            "%s ["
            + "%n\tcharset()=(%s) '%s'"
            + "%n\tsplitRegex()='%s'"
            + "%n\tcharCallback()=%s"
            + "%n\tcharCallbackFactory()=%s"
            + "%n\tbyteCallback()=%s"
            + "%n\tbyteCallbackFactory()=%s"
            + "%n\tisDataAccumulated()=%s"
            + "%n\tmaxAccumulatedDataByteCount()=%d"
            + "%n\t]",
            getClass().getSimpleName(),
            _charset.getClass().getSimpleName(),
            _charset,
            (null == _optSplitRegex ? "null" : _optSplitRegex),
            (null == _optCharCallback ? "null" : _optCharCallback.getClass().getSimpleName()),
            (null == _optCharCallbackFactory
                ? "null" : _optCharCallbackFactory.getClass().getSimpleName()),
            (null == _optByteCallback ? "null" : _optByteCallback.getClass().getSimpleName()),
            (null == _optByteCallbackFactory
                ? "null" : _optByteCallbackFactory.getClass().getSimpleName()),
            _isDataAccumulated,
            _maxAccumulatedDataByteCount);
        return x;
    }
    
    /**
     * Since this object is mutable, apply caution when calling this method and interpreting
     * the result.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int x = Objects.hashCode(
            _charset,
            _optSplitRegex,
            _optCharCallback,
            _optCharCallbackFactory,
            _optByteCallback,
            _optByteCallbackFactory,
            _isDataAccumulated,
            _maxAccumulatedDataByteCount);
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
        if (!result && obj instanceof ProcessOutputStreamSettings) {
            ProcessOutputStreamSettings other = (ProcessOutputStreamSettings) obj;
            result =
                (this._isDataAccumulated == other._isDataAccumulated)
                && (this._maxAccumulatedDataByteCount == other._maxAccumulatedDataByteCount)
                && Objects.equal(this._charset, other._charset)
                && Objects.equal(this._optSplitRegex, other._optSplitRegex)
                && Objects.equal(this._optCharCallback, other._optCharCallback)
                && Objects.equal(this._optCharCallbackFactory, other._optCharCallbackFactory)
                && Objects.equal(this._optByteCallback, other._optByteCallback)
                && Objects.equal(this._optByteCallbackFactory, other._optByteCallbackFactory)
                ;
        }
        return result;
    }
}
