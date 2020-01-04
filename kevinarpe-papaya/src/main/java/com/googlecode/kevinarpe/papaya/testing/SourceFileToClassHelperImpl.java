package com.googlecode.kevinarpe.papaya.testing;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class SourceFileToClassHelperImpl
implements SourceFileToClassHelper {

    public static final SourceFileToClassHelperImpl INSTANCE = new SourceFileToClassHelperImpl();

    // Inner and nested classes are intentionally ignored.
    @Override
    public Class<?> getClass(final File sourceFilePath)
    throws ClassNotFoundException {
        File pathForIter = sourceFilePath;
        // Example: /a/b/c/MyClass.java -> MyClass
        String className = _getFileNameWithSuffix(pathForIter);
        while (true) {
            try {
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

    private String _getFileNameWithSuffix(File path) {
        ObjectArgs.checkNotNull(path, "path");

        String fileName = path.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (-1 == dotIndex) {
            throw new IllegalArgumentException(
                String.format("Failed to find suffix: '%s'", path.getAbsolutePath()));
        }
        String baseFileName = fileName.substring(0, dotIndex);
        return baseFileName;
    }
}
