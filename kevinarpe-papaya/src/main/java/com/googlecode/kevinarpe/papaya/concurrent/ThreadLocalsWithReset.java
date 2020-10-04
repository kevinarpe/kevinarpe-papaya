package com.googlecode.kevinarpe.papaya.concurrent;

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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ThreadLocalsWithReset {

    private ThreadLocalsWithReset() {
        // Empty
    }

    /**
     * @param supplier
     *        Ex: {@code () -> new ArrayList<>()}
     *        <br>Ex: {@code () -> HashMultiset.create()}
     */
    public static <TValue, TCollection extends Collection<TValue>>
    ThreadLocalWithReset<TCollection>
    newInstanceForCollection(Supplier<TCollection> supplier) {

        final ThreadLocalWithReset<TCollection> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> supplier.get(),
                (TCollection z) -> z.clear());
        return x;
    }

    /**
     * @param supplier
     *        Ex: {@code () -> new HashMap<>()}
     */
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    ThreadLocalWithReset<TMap>
    newInstanceForMap(Supplier<TMap> supplier) {

        final ThreadLocalWithReset<TMap> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> supplier.get(),
                (TMap z) -> z.clear());
        return x;
    }

    /**
     * @param supplier
     *        Ex: {@code () -> new HashMap<>()}
     */
    public static <TKey, TValue, TBiMap extends BiMap<TKey, TValue>>
    ThreadLocalWithReset<TBiMap>
    newInstanceForBiMap(Supplier<TBiMap> supplier) {

        final ThreadLocalWithReset<TBiMap> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> supplier.get(),
                (TBiMap z) -> z.clear());
        return x;
    }

    /**
     * @param supplier
     *        Ex: {@code () -> ArrayListMultimap.create()}
     */
    public static <TKey, TValue, TMultimap extends Multimap<TKey, TValue>>
    ThreadLocalWithReset<TMultimap>
    newInstanceForMultimap(Supplier<TMultimap> supplier) {

        final ThreadLocalWithReset<TMultimap> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> supplier.get(),
                (TMultimap z) -> z.clear());
        return x;
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

    public static <TValue extends Enum<TValue>>
    ThreadLocalWithReset<EnumSet<TValue>>
    newInstanceForEnumSet(Class<TValue> valueClass) {

        ObjectArgs.checkNotNull(valueClass, "valueClass");

        final ThreadLocalWithReset<EnumSet<TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> EnumSet.noneOf(valueClass),
                (EnumSet<TValue> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<HashMap<TKey, TValue>>
    newInstanceForHashMap() {

        final ThreadLocalWithReset<HashMap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new HashMap<>(),
                (HashMap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<LinkedHashMap<TKey, TValue>>
    newInstanceForLinkedHashMap() {

        final ThreadLocalWithReset<LinkedHashMap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new LinkedHashMap<>(),
                (LinkedHashMap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<HashBiMap<TKey, TValue>>
    newInstanceForHashBiMap() {

        final ThreadLocalWithReset<HashBiMap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> HashBiMap.create(),
                (HashBiMap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey extends Enum<TKey>, TValue>
    ThreadLocalWithReset<EnumMap<TKey, TValue>>
    newInstanceForEnumMap(Class<TKey> keyClass) {

        ObjectArgs.checkNotNull(keyClass, "keyClass");

        final ThreadLocalWithReset<EnumMap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> new EnumMap<>(keyClass),
                (EnumMap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey extends Enum<TKey>, TValue>
    ThreadLocalWithReset<EnumHashBiMap<TKey, TValue>>
    newInstanceForEnumHashBiMap(Class<TKey> keyClass) {

        ObjectArgs.checkNotNull(keyClass, "keyClass");

        final ThreadLocalWithReset<EnumHashBiMap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> EnumHashBiMap.create(keyClass),
                (EnumHashBiMap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey>
    ThreadLocalWithReset<HashMultiset<TKey>>
    newInstanceForHashMultiset() {

        final ThreadLocalWithReset<HashMultiset<TKey>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> HashMultiset.create(),
                (HashMultiset<TKey> z) -> z.clear());
        return x;
    }

    public static <TKey>
    ThreadLocalWithReset<LinkedHashMultiset<TKey>>
    newInstanceForLinkedHashMultiset() {

        final ThreadLocalWithReset<LinkedHashMultiset<TKey>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> LinkedHashMultiset.create(),
                (LinkedHashMultiset<TKey> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<ArrayListMultimap<TKey, TValue>>
    newInstanceForArrayListMultimap() {

        final ThreadLocalWithReset<ArrayListMultimap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> ArrayListMultimap.create(),
                (ArrayListMultimap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<HashMultimap<TKey, TValue>>
    newInstanceForHashMultimap() {

        final ThreadLocalWithReset<HashMultimap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> HashMultimap.create(),
                (HashMultimap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<LinkedListMultimap<TKey, TValue>>
    newInstanceForLinkedListMultimap() {

        final ThreadLocalWithReset<LinkedListMultimap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> LinkedListMultimap.create(),
                (LinkedListMultimap<TKey, TValue> z) -> z.clear());
        return x;
    }

    public static <TKey, TValue>
    ThreadLocalWithReset<LinkedHashMultimap<TKey, TValue>>
    newInstanceForLinkedHashMultimap() {

        final ThreadLocalWithReset<LinkedHashMultimap<TKey, TValue>> x =
            ThreadLocalWithReset.withInitialAndReset(
                () -> LinkedHashMultimap.create(),
                (LinkedHashMultimap<TKey, TValue> z) -> z.clear());
        return x;
    }
}
