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

import java.io.FileNotFoundException;
import java.io.IOException;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.PathArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;

/**
 * This is a more specific version of {@link FileNotFoundException} for resources available via
 * {@link Class#getResourceAsStream(String)}.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 * @see PathArgs#checkClassResourceAsStreamExists(Class, String, String)
 */
@NotFullyTested
public class ClassResourceNotFoundException
extends IOException {

    private static final long serialVersionUID = 0;
    
    private final Class<?> _classForResource;
    private final String _resourceName;
    
    public ClassResourceNotFoundException(
            Class<?> classForResource, String resourceName, String message) {
        super(StringArgs.checkNotEmptyOrWhitespace(message, "message"));
        _classForResource = ObjectArgs.checkNotNull(classForResource, "classForResource");
        _resourceName = StringArgs.checkNotEmpty(resourceName, "resourceName");
    }

    public Class<?> getClassForResource() {
        return _classForResource;
    }

    public String getResourceName() {
        return _resourceName;
    }
}
