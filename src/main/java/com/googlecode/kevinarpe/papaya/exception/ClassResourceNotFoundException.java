package com.googlecode.kevinarpe.papaya.exception;

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

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

/**
 * This is a more specific version of {@link FileNotFoundException} for resources available via
 * {@link Class#getResourceAsStream(String)}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PathArgs#checkClassResourceAsStreamExists(Class, String, String)
 */
@FullyTested
public class ClassResourceNotFoundException
extends IOException {

    private static final long serialVersionUID = 1L;
    
    private final Class<?> _classForResource;
    private final String _resourceName;
    
    /**
     * This is a convenience method for
     * {@link #ClassResourceNotFoundException(Class, String, String, Throwable)}
     * where param {@code optCause} is {@code null}.
     */
    public ClassResourceNotFoundException(
            Class<?> classForResource, String resourceName, String message) {
        this(classForResource, resourceName, message, null);
    }
    
    /**
     * Constructs a new ClassResourceNotFoundException object.  These should be thrown instead of
     * {@link IOException} in cases where the error is specifically class resource path-related.
     * 
     * @param classForResource
     * <ul>
     *   <li>class used when check if resource exists</li>
     *   <li>Access via {@link #getClassForResource()}</li>
     * </ul>
     * @param resourceName
     * <ul>
     *   <li>path to class resource</li>
     *   <li>Access via {@link #getResourceName()}</li>
     *   <li>See {@link PathArgs#checkClassResourceAsStreamExists(Class, String, String)} for a
     *   detailed explanation about resource paths</li>
     * </ul>
     * @param message
     * <ul>
     *   <li>human-readable error message that is passed directly to superclass constructor</li>
     *   <li>Access via {@link #getMessage()}</li>
     * </ul>
     * @param optCause
     * <ul>
     *   <li>optional underlying cause of this exception that is passed directory to superclass
     *   constructor</li>
     *   <li>May be {@code null}</li>
     *   <li>Access via {@link #getCause()}</li>
     * </ul>
     *
     * @throws NullPointerException
     *         if {@code classForResource}, {@code resourceName}, or {@code message} is
     *         {@code null}
     * @throws IllegalArgumentException
     *         if {@code message} is empty
     */
    public ClassResourceNotFoundException(
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        super(StringArgs.checkNotEmptyOrWhitespace(message, "message"), optCause);
        
        _classForResource = ObjectArgs.checkNotNull(classForResource, "classForResource");
        _resourceName = StringArgs.checkNotEmpty(resourceName, "resourceName");
    }
    
    /**
     * Copy constructor to call
     * {@link #ClassResourceNotFoundException(Class, String, String, Throwable)}.
     * 
     * @throws NullPointerException
     *         if {@code other} is {@code null}
     */
    public ClassResourceNotFoundException(ClassResourceNotFoundException other) {
        this(
            ObjectArgs.checkNotNull(other, "other").getClassForResource(),
            other.getResourceName(),
            other.getMessage(),
            other.getCause());
    }

    /**
     * @return class used when checking if resource exists
     */
    public Class<?> getClassForResource() {
        return _classForResource;
    }

    /**
     * @return path to class resource
     */
    public String getResourceName() {
        return _resourceName;
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(getClassForResource(), getResourceName());
        result = 31 * result + ThrowableUtils.hashCode(this);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof ClassResourceNotFoundException) {
            final ClassResourceNotFoundException other = (ClassResourceNotFoundException) obj;
            result =
                ThrowableUtils.equals(this, other)
                && getClassForResource() == other.getClassForResource()
                && Objects.equal(getResourceName(), other.getResourceName());
        }
        return result;
    }
    
    /**
     * Used by test code.
     */
    boolean equalsExcludingStackTrace(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof ClassResourceNotFoundException) {
            final ClassResourceNotFoundException other = (ClassResourceNotFoundException) obj;
            result =
                ThrowableUtils.equalsExcludingStackTrace(this, other)
                && getClassForResource() == other.getClassForResource()
                && Objects.equal(getResourceName(), other.getResourceName());
        }
        return result;
    }

    @Override
    public String toString() {
        String x = String.format(
            "%s ["
            + "%n\tgetClassForResource()=%s,"
            + "%n\tgetResourceName()='%s',%s",
            getClass().getSimpleName(),
            getClassForResource(),
            getResourceName(),
            ThrowableUtils.toString(this));
        return x;
    }
}
