package com.googlecode.kevinarpe.papaya.testing.mockito;

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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import org.mockito.Mockito;

import java.lang.reflect.TypeVariable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Test me
public final class MockitoUtils
implements IMockitoUtils {

    public static final MockitoUtils INSTANCE = new MockitoUtils();

    public MockitoUtils() {
        // Empty
    }

    // TODO: Can we make this safer?  Make a not about runtime ClassCastException if clazz is rubbish
    @Override
    public <T> T mockGenericInterface(Class<?> clazz) {
        ObjectArgs.checkNotNull(clazz, "clazz");
        final String mockObjectName = "mock" + clazz.getSimpleName();
        T x = mockGenericInterface(clazz, mockObjectName);
        return x;
    }

    @Override
    public <T> T mockGenericInterface(Class<?> clazz, String mockObjectName) {
        ObjectArgs.checkNotNull(clazz, "clazz");
        StringArgs.checkNotEmptyOrWhitespace(mockObjectName, "mockObjectName");
        // TODO: Create new ClassArgs to test class obj attributes.
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s' is not a generic interface, e.g., List.class: %s",
                "clazz", clazz.getName()));
        }
        {
            TypeVariable[] typeParamArr = clazz.getTypeParameters();
            if (0 == typeParamArr.length) {
                throw new IllegalArgumentException(String.format(
                    "Argument '%s' is not a generic type declaration, e.g., String.class: %s",
                    "clazz", clazz.getName()));
            }
        }

        @SuppressWarnings("unchecked")
        T x = (T) Mockito.mock(clazz, mockObjectName);
        return x;
    }
}
