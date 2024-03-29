package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.io.File;

/**
 * Formats file and directory paths to distinguish between relative and absolute paths.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see Formatter2
 */
@FullyTested
public class PathFormatter2
extends StatelessObject
implements Formatter2 {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final PathFormatter2 INSTANCE = new PathFormatter2();

    /**
     * Converts a path to a string.
     * <ul>
     *     <li>{@code null} to {@code "null"}</li>
     *     <li>absolute path to {@code "'/abs/path'"}</li>
     *     <li>relative path to {@code "'rel/path' -> '/a/b/c/rel/path'"}</li>
     * </ul>
     * <hr>
     * Inherited docs:
     * <br>
     * {@inheritDoc}
     *
     * @throws ClassCastException
     *         if {@code value} is not a {@link File}
     */
    @Override
    public String format(Object value) {
        if (null == value) {
            return StringFormatterHelperImpl.NULL_VALUE_AS_STRING;
        }
        File path = ObjectArgs.checkCast(value, File.class, "class of value");

        String x = String.format("'%s'", path.getPath());
        if (!path.isAbsolute()) {
            x = String.format("%s -> '%s'", x, path.getAbsolutePath());
        }
        return x;
    }
}
