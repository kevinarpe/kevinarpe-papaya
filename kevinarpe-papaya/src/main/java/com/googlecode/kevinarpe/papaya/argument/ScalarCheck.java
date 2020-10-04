package com.googlecode.kevinarpe.papaya.argument;

/*-
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

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FunctionalInterface
public interface ScalarCheck<TValue> {

    /**
     * @param value
     *        a value to test
     *        <br>might be {@code null}, but might also throw when {@code null}
     *
     * @param argName
     *        argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     *
     * @return the validated input value
     *
     * @throws RuntimeException
     *         (including {@link NullPointerException}) when {@code value} is invalid
     *
     * @see CollectionArgs#checkElements(Collection, String, ScalarCheck)
     * @see MapArgs#checkKeys(Map, String, ScalarCheck)
     * @see MapArgs#checkValues(Map, String, ScalarCheck)
     */
    TValue check(@Nullable TValue value, String argName);
}
