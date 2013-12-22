package com.googlecode.kevinarpe.papaya.filesystem;

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
