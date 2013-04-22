/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

/**
 * This is a collection of static methods to check arguments received in methods.
 * The goal is provide a wide range of checks that produce readable, detailed errors.
 * 
 * <p>Unless noted, all methods throw unchecked exceptions -- {@link RuntimeException}
 * and its subclasses.  Most frequently, {@link IllegalArgumentException} is thrown.
 * 
 * <pre>
 *     public void myMethod(List<String> strList) {
 *         ArgUtil.Objects.staticCheckNotNull(strList, "strList");
 *         // Do work here.
 *     }</pre>
 * 
 * I have written similiar libraries in Perl, Python, VBA, C, C++, Java, and C# over the years.
 * It's time to put this into open source.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ObjectArgs {

    /**
     * Tests if an object reference passed as an argument is not null.
     * 
     * @param ref an object reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     */
    public static <T> T staticCheckNotNull(T ref, String argName) {
        if (null == argName) {
            throw new NullPointerException(String.format("Argument name (argName) is null"));
        }
        if (null == ref) {
            throw new NullPointerException(String.format("Argument '%s' is null", argName));
        }
        return ref;
    }
}
