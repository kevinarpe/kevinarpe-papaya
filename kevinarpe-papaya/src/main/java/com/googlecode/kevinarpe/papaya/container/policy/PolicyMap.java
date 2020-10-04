package com.googlecode.kevinarpe.papaya.container.policy;

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

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PolicyIterator
 * @see PolicyListIterator
 * @see PolicyCollection
 * @see PolicyList
 * @see PolicySet
 * @see PolicyMapEntry
 */
@FullyTested
public class PolicyMap<TKey, TValue>
extends ForwardingMap<TKey, TValue> {

    private final Map<TKey, TValue> map;
    private final ImmutableSet<DoNotAllow> doNotAllowSet;

    public PolicyMap(@EmptyContainerAllowed
                     Map<TKey, TValue> map,
                     @EmptyContainerAllowed
                     Set<DoNotAllow> doNotAllowSet) {

        this.map = ObjectArgs.checkNotNull(map, "map");
        CollectionArgs.checkElementsNotNull(doNotAllowSet, "doNotAllowSet");
        this.doNotAllowSet = Sets.immutableEnumSet(doNotAllowSet);

        for (final DoNotAllow doNotAllow : doNotAllowSet) {

            switch (doNotAllow) {

                case NullKey: {

                    MapArgs.checkKeysNotNull(map, "map");
                    break;
                }
                case NullValue: {

                    MapArgs.checkValuesNotNull(map, "map");
                    break;
                }
                case Remove: {
                    break;
                }
                default: {
                    throw new IllegalStateException("Unreachable code");
                }
            }
        }
    }

    @Override
    protected Map<TKey, TValue>
    delegate() {
        return map;
    }

    public ImmutableSet<DoNotAllow>
    doNotAllowSet() {
        return doNotAllowSet;
    }
//
//    @Override
//    public int size() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public boolean isEmpty() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public boolean containsKey(Object key) {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public boolean containsValue(Object value) {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public TValue get(Object key) {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code key} is {@code null} when
     * {@link DoNotAllow#NullKey},
     * <br>or if {@code value} is {@code null} when {@link DoNotAllow#NullValue}
     */
    @Nullable
    @Override
    public TValue
    put(@Nullable TKey key,
        @Nullable TValue value) {

        if (null == key) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullKey);
        }
        if (null == value) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullValue);
        }
        @Nullable
        final TValue oldValue = super.put(key, value);
        return oldValue;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Nullable
    @Override
    public TValue
    remove(@Nullable Object key) {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        @Nullable
        final TValue value = super.remove(key);
        return value;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code map} has {@code null} key when
     * {@link DoNotAllow#NullKey},
     * <br>or if {@code map} has {@code null} values when {@link DoNotAllow#NullValue}
     */
    @Override
    public void
    putAll(@EmptyContainerAllowed Map<? extends TKey, ? extends TValue> map) {

        // Check 'map' keys for null
        if (map.containsKey(null)) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullKey);
        }
        // Check 'map' values for null
        if (doNotAllowSet().contains(DoNotAllow.NullValue)) {
            try {
                MapArgs.checkValuesNotNull(map, "map");
            }
            catch (Exception e) {
                throw new UnsupportedOperationException(
                    DoNotAllow.NullValue.description + " is not allowed: " + e.getMessage());
            }
        }
        super.putAll(map);
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Override
    public void
    clear() {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        super.clear();
    }

    private transient PolicySet<TKey> keySet = null;

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicySet} with matching {@link #doNotAllowSet()}
     */
    @Override
    public PolicySet<TKey>
    keySet() {

        PolicySet<TKey> ks = keySet;
        if (null == ks) {
            keySet = ks = new PolicySet<>(super.keySet(), doNotAllowSet());
        }
        return ks;
    }

    private transient PolicyCollection<TValue> values = null;

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicyCollection} with matching {@link #doNotAllowSet()}
     */
    @Override
    public PolicyCollection<TValue>
    values() {

        PolicyCollection<TValue> vs = values;
        if (null == vs) {
            values = vs = new PolicyCollection<>(super.values(), doNotAllowSet());
        }
        return vs;
    }

    private transient _EntrySet entrySet;

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicySet} with matching {@link #doNotAllowSet()},
     * <br>and iterator elements have type {@link PolicyMapEntry} with matching {@link #doNotAllowSet()}.
     */
    @Override
    public PolicySet<Entry<TKey, TValue>>
    entrySet() {

        _EntrySet es = entrySet;
        if (null == es) {
            entrySet = es = new _EntrySet();
        }
        return es;
    }
//
//    // The OpenJDK impl calls get(Object) and containsKey(Object).
//    // default
//    @Override
//    public TValue getOrDefault(Object key, TValue defaultValue) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls entrySet(), Entry.getKey(), and Entry.getValue().
//    // default
//    @Override
//    public void forEach(BiConsumer<? super TKey, ? super TValue> action) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls entrySet(), Entry.getKey(), Entry.getValue(), and Entry.setValue(Object).
//    // default
//    @Override
//    public void replaceAll(BiFunction<? super TKey, ? super TValue, ? extends TValue> function) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object) and put(Object, Object).
//    // default
//    @Override
//    public TValue putIfAbsent(TKey key, TValue value) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object), containsKey(Object), and remove(Object).
//    // default
//    @Override
//    public boolean remove(Object key, Object value) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object), containsKey(Object), and put(Object, Object).
//    // default
//    @Override
//    public boolean replace(TKey key, TValue oldValue, TValue newValue) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object), containsKey(Object), and put(Object, Object).
//    // default
//    @Override
//    public TValue replace(TKey key, TValue value) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object) and put(Object, Object).
//    // default
//    @Override
//    public TValue computeIfAbsent(TKey key, Function<? super TKey, ? extends TValue> mappingFunction) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object), put(Object, Object), and remove(Object).
//    // default
//    @Override
//    public TValue computeIfPresent(TKey key,
//                                   BiFunction<? super TKey, ? super TValue, ? extends TValue> remappingFunction) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object), containsKey(Object), remove(Object), and put(Object, Object).
//    // default
//    @Override
//    public TValue compute(TKey key, BiFunction<? super TKey, ? super TValue, ? extends TValue> remappingFunction) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls get(Object), remove(Object), and put(Object, Object).
//    // default
//    @Override
//    public TValue merge(TKey key,
//                        TValue value,
//                        BiFunction<? super TValue, ? super TValue, ? extends TValue> remappingFunction) {
//        abc // Intentional syntax error
//    }

    final class _EntrySet
    extends PolicySet<Entry<TKey, TValue>> {

        public _EntrySet() {
            super(PolicyMap.super.entrySet(), PolicyMap.this.doNotAllowSet());
        }

        @Override
        public PolicyIterator<Entry<TKey, TValue>>
        iterator() {
            final _Iterator x = new _Iterator();
            return x;
        }

        final class _Iterator
        extends PolicyIterator<Entry<TKey, TValue>> {

            public _Iterator() {
                super(_EntrySet.super.iterator(), _EntrySet.this.doNotAllowSet());
            }

            @Override
            public PolicyMapEntry<TKey, TValue>
            next() {
                final Entry<TKey, TValue> entry = super.next();
                final PolicyMapEntry<TKey, TValue> x = new PolicyMapEntry<>(entry, doNotAllowSet());
                return x;
            }
        }
    }
}
