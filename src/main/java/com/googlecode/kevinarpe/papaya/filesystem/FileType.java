package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.io.IOException;

public enum FileType {

    REGULAR_FILE {
        @Override
        protected boolean _is(File path) {
            return path.isFile();
        }
    },
    DIRECTORY {
        @Override
        protected boolean _is(File path) {
            return path.isDirectory();
        }
    };

    public static FileType from(File path)
    throws IOException {
        ObjectArgs.checkNotNull(path, "path");

        for (FileType fileType : FileType.values()) {
            if (fileType._is(path)) {
                return fileType;
            }
        }
        throw new IOException(String.format(
            "Failed to find %s for path: '%s'",
            FileType.class.getSimpleName(), path.getAbsolutePath()));
    }

    protected abstract boolean _is(File path);

    public boolean is(File path) {
        ObjectArgs.checkNotNull(path, "path");

        return _is(path);
    }
}
