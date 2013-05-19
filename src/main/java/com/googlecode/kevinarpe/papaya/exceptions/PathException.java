package com.googlecode.kevinarpe.papaya.exceptions;

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
