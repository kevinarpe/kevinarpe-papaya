package com.googlecode.kevinarpe.papaya.argument;

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

import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ClassArgs {

    /**
     * Tests if a class is not a generic type.  For example, {@link String} is not a generic type,
     * so {@code String.class} will pass this check.  However, {@link List} is a generic type, so
     * {@code List.class} will fail this check.
     * <p>
     * This method can be used by methods that need to safely cast input data.  Non-generic types
     * can be safely cast, whereas generic types cannot.
     *
     * @param clazz
     *        class of type to test
     * @param argName
     *        argument name for {@code clazz},
     *        e.g., "strListClass" or "searchRegexClass"
     *
     * @return the validated class object reference
     *
     * @throws NullPointerException
     *         if {@code clazz} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code clazz} has generic type parameters
     */
    // TODO: Test me
    // TODO: See MockitoUtils
    public static <T> Class<T> checkNotGenericType(Class<T> clazz, String argName) {
        ObjectArgs.checkNotNull(clazz, argName);

        TypeVariable<? extends Class<?>>[] typeParamArr = clazz.getTypeParameters();
        if (0 != typeParamArr.length) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s' is a generic type declaration, e.g., List.class: %s",
                argName, clazz.getName()));
        }
        return clazz;
    }
}
