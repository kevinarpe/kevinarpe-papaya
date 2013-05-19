package com.googlecode.kevinarpe.papaya.exceptions;

import java.io.File;
import java.io.FileNotFoundException;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.PathArgs;

/**
 * This is a more specific version of {@link FileNotFoundException}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 * @see PathArgs#checkRegularFileNotExists(File, String)
 */
@NotFullyTested
public class RegularFileFoundException
extends PathException {

    private static final long serialVersionUID = 0;

    public RegularFileFoundException(File file, String message) {
        super(file, message);
    }
}
