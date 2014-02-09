package com.googlecode.kevinarpe.papaya.testing;

import java.io.File;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface SourceFileToClassHelper {

    Class<?> getClass(File sourceFilePath)
    throws ClassNotFoundException;
}
