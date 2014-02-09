package com.googlecode.kevinarpe.papaya.testing;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class SourceFileToClassHelperImpl
implements SourceFileToClassHelper {

    public static final SourceFileToClassHelperImpl INSTANCE = new SourceFileToClassHelperImpl();

    // Ref: http://docs.oracle.com/javase/6/docs/technotes/tools/windows/javac.html
    // "Source code file names must have .java suffixes"
    public static final String JAVA_SUFFIX = ".java";

    // Inner and nested classes are intentionally ignored.
    @Override
    public Class<?> getClass(final File sourceFilePath)
    throws ClassNotFoundException {
        _assertIsSourceFilePath(sourceFilePath);
        File pathForIter = sourceFilePath;
        // Example: MyClass.java
        String fileName = pathForIter.getName();
        // Example: MyClass
        String className = fileName.substring(0, fileName.length() - JAVA_SUFFIX.length());
        while (true) {
            try {
                // TODO: Use a factory to abstract away?  Try Powermock first.
                Class<?> klass = Class.forName(className);
                return klass;
            }
            catch (Throwable ignored) {
                int dummy = 0;  // debug breakpoint
            }
            pathForIter = pathForIter.getParentFile();
            if (null == pathForIter) {
                throw new ClassNotFoundException(String.format(
                    "Failed to find class for path: '%s'", sourceFilePath.getAbsolutePath()));
            }
            className = pathForIter.getName() + "." + className;
        }
    }

    private void _assertIsSourceFilePath(File sourceFilePath) {
        ObjectArgs.checkNotNull(sourceFilePath, "sourceFilePath");
        String fileName = sourceFilePath.getName();
        if (!fileName.endsWith(JAVA_SUFFIX)) {
            throw new IllegalArgumentException(String.format(
                "File name does end with '%s': '%s'", JAVA_SUFFIX, fileName));
        }
    }
}
