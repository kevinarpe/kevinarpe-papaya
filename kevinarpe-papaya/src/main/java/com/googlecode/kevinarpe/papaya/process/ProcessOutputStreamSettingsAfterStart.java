package com.googlecode.kevinarpe.papaya.process;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.FuncUtils;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.appendable.ByteAppendable;

/**
 * This simple class holds the settings for child process output streams (either STDOUT or STDERR)
 * <b>after</b> the child process starts.  After the child process has started, certain output
 * stream settings (for STDOUT or STDERR) cannot be reasonably changed, e.g.,
 * {@link #charCallbackFactory(FuncUtils.Func0)}.  This subclass implements no new behavior except
 * to throw {@link UnsupportedOperationException} for certain restricted setter methods.
 * <p>
 * This class may not be instantiated directly.  Normally, only class
 * {@link AbstractProcessSettings} creates instances.
 * <p>
 * The following methods throw {@link UnsupportedOperationException}:
 * <ul>
 *   <li>{@link ProcessOutputStreamSettingsAfterStart#charCallbackFactory(FuncUtils.Func0)}</li>
 *   <li>{@link ProcessOutputStreamSettingsAfterStart#byteCallbackFactory(FuncUtils.Func0)}</li>
 * </ul>
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class ProcessOutputStreamSettingsAfterStart
extends ProcessOutputStreamSettings {
    
    static final String UNSUPPORTED_EXCEPTION_MESSAGE =
        "This method may not be called after child process has started";

    /**
     * Copy constructor
     */
    public ProcessOutputStreamSettingsAfterStart(ProcessOutputStreamSettings x) {
        super(x);
    }

    /**
     * Always throws {@link UnsupportedOperationException}.  As the child process starts, the
     * character-based callback factory is used to create a new callback.
     * <p>
     * {@inheritDoc}
     * 
     * @throws UnsupportedOperationException
     *         always
     */
    @Override
    public ProcessOutputStreamSettings charCallbackFactory(
            FuncUtils.Func0<Appendable> optCallbackFactory) {
        throw new UnsupportedOperationException(UNSUPPORTED_EXCEPTION_MESSAGE);
    }
    
    /**
     * Always throws {@link UnsupportedOperationException}.  As the child process starts, the
     * byte-based callback factory is used to create a new callback.
     * <p>
     * {@inheritDoc}
     * 
     * @throws UnsupportedOperationException
     *         always
     */
    @Override
    public ProcessOutputStreamSettings byteCallbackFactory(
            FuncUtils.Func0<ByteAppendable> optCallbackFactory) {
        throw new UnsupportedOperationException(UNSUPPORTED_EXCEPTION_MESSAGE);
    }
}
