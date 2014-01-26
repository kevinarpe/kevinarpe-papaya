package com.googlecode.kevinarpe.papaya.exception;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.io.File;
import java.io.IOException;

/**
 * This is a more specific version of {@link IOException} that focuses on path-related errors.
 * It applies to both file and directory paths.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class PathException
extends IOException {
    
    // TODO: Convert all exceptions to follow TimeoutException with String format & Object[] argArr

    private static final long serialVersionUID = 1L;

    private final PathExceptionReason _reason;
    private final File _absPath;
    private final File _optAbsParentPath;

    /**
     * This is a convenience method for
     * {@link #PathException(PathExceptionReason, File, File, String, Throwable)}
     * where param {@code optCause} is {@code null}.
     */
    public PathException(
            PathExceptionReason reason, File path, File optParentPath, String message) {
        this(reason, path, optParentPath, message, null);
    }

    /**
     * Constructs a new PathException object.  These should be thrown instead of
     * {@link IOException} in cases where the error is specifically path-related.  Some errors are
     * caused by a parent path and others not.  This class is careful to distinguish between
     * both cases.
     * <p>
     * To understand when parent path is not {@code null}, consider this example:
     * <ol>
     *   <li>Imagine a utility method tries to make a directory...</li>
     *   <li>Where input path is {@code /home/user/abc/def}...</li>
     *   <li>But {@code /home/user/abc} exists as a file...</li>
     *   <li>After failing to make the directory, the method might throw an exception where:</li>
     *   <ol>
     *     <li>Parameter {@code reason} is
     *     {@link PathExceptionReason#PARENT_PATH_IS_NORMAL_FILE}</li>
     *     <li>Parameter {@code path} is {@code /home/user/abc/def}</li>
     *     <li>Parameter {@code optParentPath} is {@code /home/user/abc}</li>
     *     <li>Parameter {@code message} is
     *     {@code "Failed to make directory: '/home/user/abc/def'; Parent path is a file: '/home/user/abc'"}</li>
     *     <li>Parameter {@code optCause} is {@code null}
     *   </ol>
     * </ol>
     * 
     * @param reason
     *        why the error occured.
     *        Access via {@link #getReason()}.
     * @param path
     *        deepest path that caused the error.  Relative paths are converted to absolute paths
     *        automatically.
     *        Access via {@link #getAbsPath()}.
     * @param optParentPath
     *        (optional) parent path that caused the error.
     *        If {@link PathExceptionReason#hasParentPath} is:
     *        <ul>
     *          <li>{@code true}, then this parameter must <b>not</b> be {@code null}.</li>
     *          <li>{@code false}, then this parameter must be {@code null}.</li>
     *        </ul>
     *        Relative paths are converted to absolute paths automatically.
     *        Access via {@link #getOptAbsParentPath()}.
     * @param optCause
     *        (optional) underlying cause of this exception that is passed directly to the
     *        superclass constructor.  May be {@code null}.
     *        Access via {@link #getMessage()}.
     *
     * @throws NullPointerException
     * <ul>
     *   <li>if {@code reason}, {@code message} or {@code reason} is {@code null}</li>
     *   <li>if {@link PathExceptionReason#hasParentPath} is {@code true} and
     *   {@code optParentPath} is {@code null}</li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code message} is empty or only whitespace</li>
     *   <li>if {@link PathExceptionReason#hasParentPath} is {@code false} and
     *   {@code optParentPath} is <b>not</b> {@code null}</li>
     * </ul>
     */
    public PathException(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        super(StringArgs.checkNotEmptyOrWhitespace(message, "message"), optCause);
        
        _reason = ObjectArgs.checkNotNull(reason, "reason");
        ObjectArgs.checkNotNull(path, "path");
        
        _absPath = path.getAbsoluteFile();
        if (reason.hasParentPath) {
            if (null == optParentPath) {
                String msg = String.format(
                    "%s.hasParentPath is true, but argument 'optParentPath' is null",
                    reason.name());
                throw new NullPointerException(msg);
            }
            _optAbsParentPath = optParentPath.getAbsoluteFile();
        }
        else {
            if (null != optParentPath) {
                String msg = String.format(
                        "%s.hasParentPath is false, but argument 'optParentPath' is not null",
                        reason.name());
                throw new IllegalArgumentException(msg);
            }
            _optAbsParentPath = null;
        }
    }

    /**
     * Copy constructor to call
     * {@link #PathException(PathExceptionReason, File, File, String, Throwable)}.
     * 
     * @throws NullPointerException
     *         if {@code other} is {@code null}
     */
    public PathException(PathException other) {
        this(
            ObjectArgs.checkNotNull(other, "other").getReason(),
            other.getAbsPath(),
            other.getOptAbsParentPath(),
            other.getMessage(),
            other.getCause());
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
     * @return (optional) absolute parent path that caused this error.
     *         Here, <i>optional</i> means: may be {@code null}.
     *         See {@link #PathException(PathExceptionReason, File, File, String)} for further
     *         notes about when and why this field is {@code null} (or not).
     */
    public File getOptAbsParentPath() {
        return _optAbsParentPath;
    }

    @Override
    public int hashCode() {
        int result =
            Objects.hashCode(
                getReason(),
                getAbsPath(),
                getOptAbsParentPath());
        result = 31 * result + ThrowableUtils.hashCode(this);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof PathException) {
            final PathException other = (PathException) obj;
            result =
                ThrowableUtils.equals(this, other)
                && (this.getReason() == other.getReason())
                && Objects.equal(this.getAbsPath(), other.getAbsPath())
                && Objects.equal(this.getOptAbsParentPath(), other.getOptAbsParentPath());
        }
        return result;
    }
    
    /**
     * Used by test code.
     */
    boolean equalsExcludingStackTrace(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof PathException) {
            final PathException other = (PathException) obj;
            result =
                ThrowableUtils.equalsExcludingStackTrace(this, other)
                && (getReason() == other.getReason())
                && Objects.equal(this.getAbsPath(), other.getAbsPath())
                && Objects.equal(this.getOptAbsParentPath(), other.getOptAbsParentPath());
        }
        return result;
    }
    
    @Override
    public String toString() {
        String x = String.format(
            "%s ["
            + "%n\tgetReason()='%s',"
            + "%n\tgetAbsPath()='%s',"
            + "%n\tgetOptAbsParentPath()='%s',%s",
            getClass().getSimpleName(),
            getReason(),
            getAbsPath(),
            getOptAbsParentPath(),
            ThrowableUtils.toString(this));
        return x;
    }
}
