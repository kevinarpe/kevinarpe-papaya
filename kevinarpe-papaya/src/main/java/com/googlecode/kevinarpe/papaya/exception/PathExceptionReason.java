package com.googlecode.kevinarpe.papaya.exception;

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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.filesystem.FileType;

/**
 * Reason for the error in {@link PathException}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public enum PathExceptionReason {

    /**
     * {@link PathException#getAbsPath()} does not exist.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     */
    PATH_DOES_NOT_EXIST(HasParentPath.NO),

    /**
     * {@link PathException#getAbsPath()} does not exist, because
     * {@link PathException#getOptAbsParentPath()} does not exist.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is never {@code null}.
     */
    PARENT_PATH_DOES_NOT_EXIST(HasParentPath.YES),

    /**
     * {@link PathException#getAbsPath()} exists as a normal file (non-directory).
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     *
     * @see FileType#NORMAL_FILE
     */
    PATH_IS_NORMAL_FILE(HasParentPath.NO),

    /**
     * {@link PathException#getOptAbsParentPath()} exists as a normal file (non-directory).
     * <p>
     * {@link PathException#getOptAbsParentPath()} is never {@code null}.
     *
     * @see FileType#NORMAL_FILE
     */
    PARENT_PATH_IS_NORMAL_FILE(HasParentPath.YES),

    /**
     * {@link PathException#getAbsPath()} exists as a directory.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     */
    PATH_IS_DIRECTORY(HasParentPath.NO),

    /**
     * {@link PathException#getAbsPath()} exists as a directory, but execute permissions are not
     * set.  (Cannot list files stored within this path.)
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     */
    PATH_IS_NON_EXECUTABLE_DIRECTORY(HasParentPath.NO),

    /**
     * {@link PathException#getOptAbsParentPath()} exists as a directory, but is not writable.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is never {@code null}.
     */
    PARENT_PATH_IS_NON_WRITABLE_DIRECTORY(HasParentPath.YES),

    /**
     * {@link PathException#getAbsPath()} exists as a root directory.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     */
    PATH_IS_ROOT_DIRECTORY(HasParentPath.NO),

    /**
     * {@link PathException#getAbsPath()} cannot be created on a full disk partition.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     */
    PATH_DISK_PARTITION_IS_FULL(HasParentPath.NO),

    /**
     * {@link PathException#getOptAbsParentPath()} cannot be created on a full disk partition.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is never {@code null}.
     */
    PARENT_PATH_DISK_PARTITION_IS_FULL(HasParentPath.YES),

    /**
     * The reason for error is unknown.
     * <p>
     * {@link PathException#getOptAbsParentPath()} is always {@code null}.
     */
    UNKNOWN(HasParentPath.NO);

    private static enum HasParentPath {
        YES,
        NO,
    }

    /**
     * If {@code true}, then {@link PathException#getOptAbsParentPath()} is always <b>not</b>
     * {@code null}.
     * <br>If {@code false}, then {@link PathException#getOptAbsParentPath()} is always
     * {@code null}.
     */
    public final boolean hasParentPath;

    private PathExceptionReason(HasParentPath hasParentPath) {
        ObjectArgs.checkNotNull(hasParentPath, "hasParentPath");

        this.hasParentPath = (HasParentPath.YES == hasParentPath);
    }

    @Override
    public String toString() {
        String x = String.format("%s [name()='%s', hasParentPath=%s]",
            getClass().getSimpleName(), name(), hasParentPath);
        return x;
    }
}
