package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link EnumUtils} or {@link EnumUtils#INSTANCE}
 * will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see EnumUtils
 */
public interface IEnumUtils {

    /**
     * Drop-in replacement for {@code Enum.valueOf(Class, String)} with better exception message.
     * <p>
     * To suppress exception on failed lookup, see {@link #tryValueOf(Class, String)}.
     *
     * @param enumType
     *        class object for enum type, e.g., {@code Fruit.class}
     * @param name
     *        name of enum constant, e.g., {@code Fruit.BANANA -> "BANANA"}
     * @param <TEnum>
     *        type name for enum, e.g., {@code Fruit}
     *
     * @return enum value for name
     *
     * @throws NullPointerException
     *         if {@code enumType} or {@code name} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code name} is empty or only whitespace</li>
     *     <li>if {@code name} is not a member of enum type</li>
     * </ul>
     *
     * @see Enum#valueOf(Class, String)
     * @see #tryValueOf(Class, String)
     */
    <TEnum extends Enum<TEnum>>
    TEnum valueOf(Class<TEnum> enumType, String name);

    /**
     * Convenience method for {@code valueOf(Class, String)}, but {@code IllegalArgumentException}
     * is suppressed on failed lookup, and {@code null} is instead returned.
     *
     * @see #valueOf(Class, String)
     */
    <TEnum extends Enum<TEnum>>
    TEnum tryValueOf(Class<TEnum> enumType, String name);
}
