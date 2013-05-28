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
 * This is a more specific version of {@link IOException} that focuses on path-related errors.
 * It applies to both file and directory paths.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public class PathException
extends IOException {

    private static final long serialVersionUID = 8568621218644282955L;

    /**
     * Reason for the error.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     */
    public enum PathExceptionReason {

        /**
         * {@link PathException#getAbsPath()} does not exist.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always {@code null}.
         */
        PATH_DOES_NOT_EXIST(false),

        /**
         * {@link PathException#getAbsPath()} does not exist, because
         * {@link PathException#getOptAbsParentPath()} does not exist.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always <b>not</b> {@code null}.
         */
        PARENT_PATH_DOES_NOT_EXIST(true),

        /**
         * {@link PathException#getAbsPath()} exists as a file.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always {@code null}.
         */
        PATH_IS_FILE(false),

        /**
         * {@link PathException#getOptAbsParentPath()} exists as a file.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always <b>not</b> {@code null}.
         */
        PARENT_PATH_IS_FILE(true),

        /**
         * {@link PathException#getAbsPath()} exists as a directory.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always {@code null}.
         */
        PATH_IS_DIRECTORY(false),

        /**
         * {@link PathException#getOptAbsParentPath()} exists as a directory, but is not writable.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always <b>not</b> {@code null}.
         */
        PARENT_PATH_IS_NON_WRITABLE_DIRECTORY(true),

        /**
         * {@link PathException#getAbsPath()} exists as a root directory.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always {@code null}.
         */
        PATH_IS_ROOT_DIRECTORY(false),

        /**
         * The reason for error is unknown.
         * <p>
         * {@link PathException#getOptAbsParentPath()} is always {@code null}.
         */
        UNKNOWN(false);
        
        /**
         * If {@code true}, then {@link PathException#getOptAbsParentPath()} is always <b>not</b>
         * {@code null}.
         * <br>If {@code false}, then {@link PathException#getOptAbsParentPath()} is always
         * {@code null}.
         */
        public final boolean hasParentPath;
        
        private PathExceptionReason(boolean hasParentPath) {
            this.hasParentPath = hasParentPath;
        }
        
        @Override
        public String toString() {
            String x = String.format("%s [name()='%s', hasParentPath=%s]",
                getClass().getSimpleName(), name(), hasParentPath);
            return x;
        }
    }
    
    private final PathExceptionReason _reason;
    private final File _absPath;
    private final File _optAbsParentPath;

    /**
     * Constructs a new PathException object.  These should be thrown instead of
     * {@link IOException} in cases where the error is specifically path-related.  Some errors are
     * caused by a parent path and others not.  This class is careful to distinguish between
     * both cases.
     * <p>
     * To understand when parent path is not {@code null}, consider this example:
     * <br>Imagine a utility method tries to make a directory...
     * <br>Where input path is {@code /home/user/abc/def}...
     * <br>But {@code /home/user/abc} exists as a file...
     * <br>After failing to make the directory, the method might throw an exception where:
     * <ol>
     *   <li>Parameter {@code reason} is
     *   {@link PathExceptionReason#PARENT_PATH_IS_FILE}</li>
     *   <li>Parameter {@code path} is {@code /home/user/abc/def}</li>
     *   <li>Parameter {@code optParentPath} is {@code /home/user/abc}</li>
     *   <li>Parameter {@code message} is
     *   {@code "Failed to make directory: '/home/user/abc/def'; Parent path is a file: '/home/user/abc'"}</li>
     * </ol>
     * 
     * @param reason why the error occured
     * @param path deepest path that caused the error
     *        <br>Relative paths are converted to absolute paths automatically.
     * @param optParentPath (optional) parent path that caused the error
     *        <br>If {@link PathExceptionReason#hasParentPath} is {@code true}, then this parameter
     *        must <b>not</b> be {@code null}.
     *        <br>If {@link PathExceptionReason#hasParentPath} is {@code false}, then this
     *        parameter must be {@code null}.
     *        <br>Relative paths are converted to absolute paths automatically.
     * @param message human-readable error message that is passed directly to
     *        superclass constructor
     * @throws NullPointerException if {@code reason}, {@code message} or {@code reason} is
     *         {@code null},
     *         <br>if {@link PathExceptionReason#hasParentPath} is {@code true} and
     *         {@code optParentPath} is {@code null}
     * @throws IllegalArgumentException if {@code message} is empty or only whitespace,
     *         <br>if {@link PathExceptionReason#hasParentPath} is {@code false} and
     *         {@code optParentPath} is <b>not</b> {@code null}
     */
    public PathException(
            PathExceptionReason reason, File path, File optParentPath, String message) {
        super(StringArgs.checkNotEmptyOrWhitespace(message, "message"));
        _reason = ObjectArgs.checkNotNull(reason, "reason");
        ObjectArgs.checkNotNull(path, "path");
        _absPath = path.getAbsoluteFile();
        if (reason.hasParentPath) {
            ObjectArgs.checkNotNull(optParentPath, "optParentPath");
            _optAbsParentPath = optParentPath.getAbsoluteFile();
        }
        else {
            ObjectArgs.checkNull(optParentPath, "optParentPath");
            _optAbsParentPath = null;
        }
    }

    /**
     * @return reason for this error
     */
    public PathExceptionReason getReason() {
        return _reason;
    }

    /**
     * @return deepest absolute path that caused the error
     */
    public File getAbsPath() {
        return _absPath;
    }
    
    /**
     * @return (optional) absolute parent path that caused this error
     *         <br>Here, <i>optional</i> means: may be {@code null}
     *         <br>See {@link #PathException(PathExceptionReason, File, File, String)} for further
     *         notes about when this field is {@code null} (or not) and why
     */
    public File getOptAbsParentPath() {
        return _optAbsParentPath;
    }

    /**
     * Generated by Eclipse.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((_optAbsParentPath == null) ? 0 : _optAbsParentPath.hashCode());
        result = prime * result + ((_absPath == null) ? 0 : _absPath.hashCode());
        result = prime * result + ((_reason == null) ? 0 : _reason.hashCode());
        return result;
    }

    /**
     * Generated by Eclipse.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PathException other = (PathException) obj;
        if (_optAbsParentPath == null) {
            if (other._optAbsParentPath != null)
                return false;
        }
        else if (!_optAbsParentPath.equals(other._optAbsParentPath))
            return false;
        if (_absPath == null) {
            if (other._absPath != null)
                return false;
        }
        else if (!_absPath.equals(other._absPath))
            return false;
        if (_reason != other._reason)
            return false;
        return true;
    }

    @Override
    public String toString() {
        String x = String.format(
            "%s [%n\tgetReason()='%s',%n\tgetAbsPath()='%s',%n\tgetOptAbsParentPath()='%s',%n\tgetMessage()='%s'",
            getClass().getSimpleName(),
            getReason(),
            getAbsPath(),
            getOptAbsParentPath(),
            getMessage());
        String indent = "\t";
        for (Throwable cause = getCause()
                ; null != cause
                ; cause = cause.getCause(), indent = indent.concat("\t")) {
            x = x.concat(String.format(",%n%sgetCause()='%s'", indent, cause));
        }
        x = x.concat(String.format("%n\t]"));
        return x;
    }
}
