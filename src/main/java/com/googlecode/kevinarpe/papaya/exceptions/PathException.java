package com.googlecode.kevinarpe.papaya.exceptions;

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

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;

/**
 * This is a more specific version of {@link IOException}.  This should be used as a base class
 * for exceptions related to file and directory paths.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 * @see RegularFileNotFoundException
 * @see DirectoryNotFoundException
 */
@NotFullyTested
public class PathException
extends IOException {

    private static final long serialVersionUID = 0;

    private final File _path;
    
    public PathException(File path, String message) {
        super(StringArgs.checkNotEmptyOrWhitespace(message, "message"));
        _path = ObjectArgs.checkNotNull(path, "path");
    }

    public File getPath() {
        return _path;
    }
}
