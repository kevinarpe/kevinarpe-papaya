package com.googlecode.kevinarpe.papaya.exception;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class ClassNotFoundRuntimeException
extends RuntimeException {

    /**
     * Calls RuntimeException#RuntimeException()
     */
    public ClassNotFoundRuntimeException() {
        super();
    }

    /**
     * Calls RuntimeException#RuntimeException(String)
     */
    public ClassNotFoundRuntimeException(String message) {
        super(message);
    }

    /**
     * Calls RuntimeException#RuntimeException(String, Throwable)
     */
    public ClassNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Calls RuntimeException#RuntimeException(Throwable)
     */
    public ClassNotFoundRuntimeException(Throwable cause) {
        super(cause);
    }
}
