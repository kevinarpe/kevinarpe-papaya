package com.googlecode.kevinarpe.papaya.container;

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

/**
 * Allows for mocking of {@link Class#newInstance()} for containers.  As of writing, it is not
 * possible to directly mock {@link Class#newInstance()}, so this interface may be used as a
 * substitute.
 * <p>
 * Since containers are a synthesis of types: a parent (container) and child (element), two types
 * are needed.  However, due to restrictions in the Java typing system, it is not possible to
 * statically specify a class object having an inner type.  For example,
 * {@code ArrayList<String>.class} is not allowed. Instead, {@code ArrayList.class} is used.  To
 * instantiate an instance of {@code ArrayList<String>} at runtime <i>only</i> from a
 * {@code Class<?>} reference, the result must be cast from the raw type {@code ArrayList} to
 * {@code ArrayList<String>}.  Finally, there is risk for a {@link ClassCastException} to be thrown.
 * <p>
 * This interface is inherently flawed and show be used with great care.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ContainerFactory {

    /**
     * Calls {@link Class#newInstance()} and casts result.
     *
     * @param containerClass
     *        class object used to instantiate, e.g., {@code ArrayList.class}.
     *        Must not be {@code null}.
     * @param <TContainerWithElementType>
     *        return type for cast, e.g., {@code ArrayList<String>}
     *
     * @return new instance of class, cast to expected type
     *
     * @throws NullPointerException
     *         if {@code containerClass} is {@code null}
     * @throws IllegalAccessException
     *         may be thrown directly by {@link Class#newInstance()}
     * @throws InstantiationException
     *         may be thrown directly by {@link Class#newInstance()}
     * @throws ClassCastException
     *         if cast to type {@code TContainerWithElementType} fails for result of
     *         {@link Class#newInstance()}.
     */
    <TContainerWithElementType>
    TContainerWithElementType newInstance(Class<?> containerClass)
    throws IllegalAccessException, InstantiationException, ClassCastException;
}
