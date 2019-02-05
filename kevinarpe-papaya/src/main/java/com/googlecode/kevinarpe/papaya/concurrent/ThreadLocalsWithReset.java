package com.googlecode.kevinarpe.papaya.concurrent;

/*-
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ThreadLocalsWithReset {

    private ThreadLocalsWithReset() {
        // Empty
    }

    public static ThreadLocalWithReset<StringBuilder>
    newInstanceForStringBuilder() {

        final ThreadLocalWithReset<StringBuilder> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new StringBuilder(),
                (StringBuilder z) -> z.delete(0, z.length()));
        return x;
    }

    public static <TValue>
    ThreadLocalWithReset<ArrayList<TValue>>
    newInstanceForArrayList() {

        final ThreadLocalWithReset<ArrayList<TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new ArrayList<>(),
                (ArrayList<TValue> z) -> z.clear());
        return x;
    }

    public static <TValue>
    ThreadLocalWithReset<HashSet<TValue>>
    newInstanceForHashSet() {

        final ThreadLocalWithReset<HashSet<TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new HashSet<>(),
                (HashSet<TValue> z) -> z.clear());
        return x;
    }

    public static <TValue>
    ThreadLocalWithReset<LinkedHashSet<TValue>>
    newInstanceForLinkedHashSet() {

        final ThreadLocalWithReset<LinkedHashSet<TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new LinkedHashSet<>(),
                (LinkedHashSet<TValue> z) -> z.clear());
        return x;
    }
}
