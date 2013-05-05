package com.googlecode.kevinarpe.papaya.args;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;

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

/**
 * This is a collection of static methods to check arguments received by methods.  The goal is to
 * provide a wide range of checks that produce readable, detailed errors.
 * <p>
 * With the exception of {@link FileArgs} (and possibly a few others), all methods throw unchecked
 * exceptions -- {@link RuntimeException} and its subclasses.  Most frequently,
 * {@link IllegalArgumentException} is thrown.
 * 
 * <pre>
 *     public void myMethod(List&lt;String> strList) {
 *         ObjectsArgs.checkNotNull(strList, "strList");
 *         // Do work here.
 *     }</pre>
 * 
 * I have written similiar argument checking routines libraries in C, C++, Perl, Python,
 * JavaScript, VBA, C#, and Java over the years.  It's time to put these ideas into open source.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ObjectArgs {

	// Disable default constructor
	private ObjectArgs() {
	}

    /**
     * Tests if an object reference passed as an argument is not null.
     * 
     * @param ref an object reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     */
	@FullyTested
    public static <T> T checkNotNull(T ref, String argName) {
        if (null == ref) {
            StringArgs._checkArgNameValid(argName, "argName");
            throw new NullPointerException(String.format("Argument '%s' is null", argName));
        }
        return ref;
    }
}
