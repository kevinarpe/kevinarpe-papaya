package com.googlecode.kevinarpe.papaya.input;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

/**
 * Static utilities for {@link InputSource2}.  To use the methods in this class create a new
 * instance via {@link #InputSource2Utils()} or use the public static member {@link #INSTANCE}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see IInputSource2Utils
 */
@FullyTested
public final class InputSource2Utils
extends StatelessObject
implements IInputSource2Utils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final InputSource2Utils INSTANCE = new InputSource2Utils();

    /**
     * For projects that require total, static-free mocking capabilities, use this constructor.
     * Else, the static constant {@link #INSTANCE} will suffice.
     */
    public InputSource2Utils() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public void checkValid(InputSource2 inputSource, String argName) {
        ObjectArgs.checkNotNull(inputSource, argName);

        if (null == inputSource.getByteStream() && null == inputSource.getCharacterStream()) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s': Both byte- and character-based streams are null; exactly one must be non-null: %s",
                argName, inputSource));
        }
        if (null != inputSource.getByteStream() && null != inputSource.getCharacterStream()) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s': Both byte- and character-based streams are not null; exactly one must be non-null: %s",
                argName, inputSource));
        }
    }

    /** {@inheritDoc} */
    @Override
    public void checkValid(
            Collection<? extends InputSource2> inputSourceCollection, String argName) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(inputSourceCollection, argName);

        final int size = inputSourceCollection.size();
        int index = 0;
        for (InputSource2 inputSource : inputSourceCollection) {
            String argName2 = String.format("%s[%d]", argName, index);
            checkValid(inputSource, argName2);
            ++index;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void close(InputSource2 inputSource)
    throws IOException {
        checkValid(inputSource, "inputSource");

        if (null != inputSource.getByteStream()) {
            _close(inputSource.getByteStream(), "byte-based stream");
        }
        else {
            _close(inputSource.getCharacterStream(), "character-based stream");
        }
    }

    private void _close(Closeable closeable, String description)
    throws IOException {
        try {
            closeable.close();
        }
        catch (IOException e) {
            String msg = String.format("Failed to close %s", description);
            throw new IOException(msg, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void closeQuietly(InputSource2 inputSource) {
        try {
            close(inputSource);
        }
        catch (IOException ignore) {
            int dummy = 1;  // debug breakpoint
        }
    }
}
