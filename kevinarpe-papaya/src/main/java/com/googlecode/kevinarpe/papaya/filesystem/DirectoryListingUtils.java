package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import javax.annotation.concurrent.Immutable;
import java.io.File;

/**
 * Static utilities for {@code DirectoryListing} and {@code DirectoryListingFactory}.
 * <p>
 * To use the methods in this class create a new instance via {@link #DirectoryListingUtils()}
 * or use the public static member {@link #INSTANCE}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see IDirectoryListingUtils
 * @see DirectoryListing
 * @see DirectoryListingFactory
 */
@Immutable
public final class DirectoryListingUtils
extends StatelessObject
implements IDirectoryListingUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final DirectoryListingUtils INSTANCE = new DirectoryListingUtils();

    public DirectoryListingUtils() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public DirectoryListingFactory getFactory() {
        return DirectoryListingFactoryImpl.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public DirectoryListing newInstance(File dirPath)
    throws PathException {
        DirectoryListing x = getFactory().newInstance(dirPath);
        return x;
    }
}
