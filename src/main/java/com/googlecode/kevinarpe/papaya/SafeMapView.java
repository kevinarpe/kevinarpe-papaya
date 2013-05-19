package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ForwardingMap;
import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.MapArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

// TODO: Create and implement AdvancedMap interface with extra helpful methods.
// TODO: Think about how to expand this class to other collection types: List and Set
// TODO: Add feature for recursive/deep views
//       This may solve the immutable JSON objects without separate classes.
// TODO: Tests!

/**
 * Forwards calls to an underlying map, similar to {@link ForwardingMap}, except access can be
 * safely controlled.  Class {@link SafeMapView.Builder} must be used to constant view instances,
 * as there are number of control features that may be set.
 * <ul>
 *   <li>{@link #getThrowForNullKey()}: Throw an exception when inserting a {@code null} key</li>
 *   <li>{@link #getThrowForNullValue()}: Throw an exception when inserting a {@code null} vale</li>
 *   <li>{@link #getThrowForKeyDoesNotExistOnGet()}: Throw an exception if {@link #get(Object)}
 *       fails</li>
 *   <li>{@link #getThrowForKeyDoesNotExistOnRemove()}: Throw an exception if
 *       {@link #remove(Object)} fails</li>
 *   <li>{@link #getThrowForKeyExistsOnPut()}: Throw an exception if key already exists in
 *       underlying map when calling {@link #put(Object, Object)} and {@link #putAll(Map)}</li>
 *   <li>{@link #getThrowForMutate()}: Throw an exception if any method changes the map</li>
 * </ul>
 * Controls are not mutually exclusive.  For example, if {@link #getThrowForNullKey()} is
 * {@code false}, but {@link #getThrowForMutate()} is {@code true}, inserting new keys is
 * not allowed.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey> type for map entry keys
 * @param <TValue> type for map entry values
 */
@NotFullyTested
public class SafeMapView<TKey, TValue>
implements Map<TKey, TValue> {
    
    private static class Features {
        
        private boolean _throwForNullKey;
        private boolean _throwForNullValue;
        private boolean _throwForKeyDoesNotExistOnGet;
        private boolean _throwForKeyDoesNotExistOnRemove;
        private boolean _throwForKeyExistsOnPut;
        // TOOD: _throwForInsert/Update/Delete/Mutate
        private boolean _throwForMutate;
        
        public Features() {
            _throwForNullKey = true;
            _throwForNullValue = true;
            _throwForKeyDoesNotExistOnGet = true;
            _throwForKeyDoesNotExistOnRemove = true;
            _throwForKeyExistsOnPut = true;
            _throwForMutate = true;
        }
        
        public void checkMap(Class<?> clazz, Map<?, ?> map) {
            if (getThrowForNullKey() && getThrowForNullValue()) {
                try {
                    MapArgs.checkKeysAndValuesNotNull(map, "map");
                }
                catch (Exception e) {
                    _rethrow(clazz, e);
                }
            }
            else if (getThrowForNullKey()) {
                try {
                    MapArgs.checkKeysNotNull(map, "map");
                }
                catch (Exception e) {
                    _rethrow(clazz, e);
                }
            }
            else if (getThrowForNullValue()) {
                try {
                    MapArgs.checkValuesNotNull(map, "map");
                }
                catch (Exception e) {
                    _rethrow(clazz, e);
                }
            }
        }
        
        private void _rethrow(Class<?> clazz, Exception e) {
            String msg = formatMessage(clazz, "Invalid map");
            throw new IllegalArgumentException(msg, e);
        }
        
        public String formatMessage(Class<?> clazz, String format, Object... argArr) {
            String s = String.format(format, argArr);
            s += _getMapDesc(clazz);
            return s;
        }
        
        private String _getMapDesc(Class<?> clazz) {
            String msg = String.format("This instance of %s:%n\t%s",
                clazz.getName(),
                Joiner.on(StringUtils.NEW_LINE + "\t").join(
                    _describeFeature("null key", getThrowForNullKey()),
                    _describeFeature("null value", getThrowForNullValue()),
                    _describeFeature("key does not exist on get()", getThrowForKeyDoesNotExistOnGet()),
                    _describeFeature("key does not exist on remove()", getThrowForKeyDoesNotExistOnRemove()),
                    _describeFeature("key exists on put()", getThrowForKeyExistsOnPut()),
                    _describeFeature("mutate (any data change)", getThrowForMutate())));
            return msg;
        }
        
        private String _describeFeature(String feature, boolean flag) {
            String s = (flag ? "Throws" : "Does not throw") + " for " + feature;
            return s;
        }

        public boolean getThrowForNullKey() {
            return _throwForNullKey;
        }

        public Features setThrowForNullKey(boolean b) {
            _throwForNullKey = b;
            return this;
        }

        public boolean getThrowForNullValue() {
            return _throwForNullValue;
        }

        public Features setThrowForNullValue(boolean b) {
            _throwForNullValue = b;
            return this;
        }

        public boolean getThrowForKeyDoesNotExistOnGet() {
            return _throwForKeyDoesNotExistOnGet;
        }

        public Features setThrowForKeyDoesNotExistOnGet(boolean b) {
            _throwForKeyDoesNotExistOnGet = b;
            return this;
        }

        public boolean getThrowForKeyDoesNotExistOnRemove() {
            return _throwForKeyDoesNotExistOnRemove;
        }

        public Features setThrowForKeyDoesNotExistOnRemove(boolean b) {
            _throwForKeyDoesNotExistOnRemove = b;
            return this;
        }

        public boolean getThrowForKeyExistsOnPut() {
            return _throwForKeyExistsOnPut;
        }

        public Features setThrowForKeyExistsOnPut(boolean b) {
            _throwForKeyExistsOnPut = b;
            return this;
        }

        public boolean getThrowForMutate() {
            return _throwForMutate;
        }

        public Features setThrowForMutate(boolean b) {
            _throwForMutate = b;
            return this;
        }
    }
    
    /**
     * Returns a new builder. The generated builder is equivalent to the builder
     * created by the {@link Builder} constructor.
     * 
     * @see Builder#Builder(Map)
     */
    public Builder<TKey, TValue> builder(Map<? extends TKey, ? extends TValue> map) {
        Builder<TKey, TValue> x = new Builder<TKey, TValue>(map);
        return x;
    }
    
    /**
     * A builder for creating safe map views of an underlying map.
     * <p>
     * Builder instances can be reused - it is safe to call {@link #build} multiple times to build
     * multiple maps in series.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     *
     * @param <TKey> type for map entry keys
     * @param <TValue> type for map entry values
     */
    public static class Builder<TKey, TValue> {
        
        private final Map<TKey, TValue> _map;
        private final Features _features;
        
        // TODO: Mofo crazy idea: "deep" or "recursive" option....
        //       Create a suite of "safeViews".  Like C# classes,
        //       all recursive application of views via RTTI.
        // TODO: Entry is immutable? <-- as of now: do not implement

        /**
         * Creates a new builder. The returned builder is equivalent to the builder
         * generated by {@link SafeMapView#builder}.
         * 
         * @param map underlying map
         * @throw NullPointerExeption if {@code map} is {@code null}
         */
        @SuppressWarnings("unchecked")
        public Builder(Map<? extends TKey, ? extends TValue> map) {
            ObjectArgs.checkNotNull(map, "map");
            if (map instanceof SafeMapView<?, ?>) {
                _map = ((SafeMapView<TKey, TValue>) map)._map;
            }
            else {
                _map = (Map<TKey, TValue>) map;
            }
            _features = new Features();
        }
        
        /**
         * @return new safe map view
         */
        public SafeMapView<TKey, TValue> build() {
            _features.checkMap(this.getClass(), _map);
            
            SafeMapView<TKey, TValue> x =
                new SafeMapView<TKey, TValue>((Map<TKey, TValue>) _map, _features);
            return x;
        }

        /**
         * @see {@link SafeMapView#getThrowForNullKey()}
         */
        public boolean getThrowForNullKey() {
            return _features.getThrowForNullKey();
        }

        public Builder<TKey, TValue> setThrowForNullKey(boolean b) {
            _features.setThrowForNullKey(b);
            return this;
        }

        /**
         * @see {@link SafeMapView#getThrowForNullValue()}
         */
        public boolean getThrowForNullValue() {
            return _features.getThrowForNullValue();
        }

        public Builder<TKey, TValue> setThrowForNullValue(boolean b) {
            _features.setThrowForNullValue(b);
            return this;
        }

        /**
         * @see {@link SafeMapView#getThrowForKeyDoesNotExistOnGet()}
         */
        public boolean getThrowForKeyDoesNotExistOnGet() {
            return _features.getThrowForKeyDoesNotExistOnGet();
        }

        public Builder<TKey, TValue> setThrowForKeyDoesNotExistOnGet(boolean b) {
            _features.setThrowForKeyDoesNotExistOnGet(b);
            return this;
        }

        /**
         * @see {@link SafeMapView#getThrowForKeyDoesNotExistOnRemove()}
         */
        public boolean getThrowForKeyDoesNotExistOnRemove() {
            return _features.getThrowForKeyDoesNotExistOnRemove();
        }

        public Builder<TKey, TValue> setThrowForKeyDoesNotExistOnRemove(boolean b) {
            _features.setThrowForKeyDoesNotExistOnRemove(b);
            return this;
        }

        /**
         * @see {@link SafeMapView#getThrowForKeyExistsOnPut()}
         */
        public boolean getThrowForKeyExistsOnPut() {
            return _features.getThrowForKeyExistsOnPut();
        }

        public Builder<TKey, TValue> setThrowForKeyExistsOnPut(boolean b) {
            _features.setThrowForKeyExistsOnPut(b);
            return this;
        }

        /**
         * @see {@link SafeMapView#getThrowForMutate()}
         */
        public boolean getThrowForMutate() {
            return _features.getThrowForMutate();
        }

        public Builder<TKey, TValue> setThrowForMutate(boolean b) {
            _features.setThrowForMutate(b);
            return this;
        }
    }
    
    private final Map<TKey, TValue> _map;
    private final Features _features;

    private SafeMapView(Map<TKey, TValue> map, Features features) {
        _map = map;
        _features = features;
    }

    /**
     * If {@code true}, throw an exception when inserting a {@code null} key.  This feature is
     * enforced when calling:
     * <ul>
     *   <li>{@link Builder#build()}, but underlying map has a {@code null} key</li>
     *   <li>{@link #put(Object, Object)} and argument {@code key} is {@code null}</li>
     *   <li>{@link #putAll(Map)} and argument {@code map} has a {@code null} key</li>
     * </ul>
     * <p>
     * This setting is final once the view is created.  It must be set in the builder using
     * {@link Builder#setThrowForNullKey(boolean)}.
     */
    public boolean getThrowForNullKey() {
        return _features.getThrowForNullKey();
    }

    /**
     * If {@code true}, throw an exception when inserting a {@code null} value.  This feature is
     * enforced when calling:
     * <ul>
     *   <li>{@link Builder#build()}, but underlying map has {@code null} values</li>
     *   <li>{@link #put(Object, Object)} and argument {@code value} is {@code null}</li>
     *   <li>{@link #putAll(Map)} and argument {@code map} has {@code null} values</li>
     *   <li>{@link SafeEntryView#setValue(Object)} and argument {@code value} is {@code null}</li>
     * </ul>
     * <p>
     * This setting is final once the view is created.  It must be set in the builder using
     * {@link Builder#setThrowForNullValue(boolean)}.
     */
    public boolean getThrowForNullValue() {
        return _features.getThrowForNullValue();
    }

    /**
     * If {@code true}, throw an exception when {@link #get(Object)} fails because the key does
     * not exist.
     * <p>
     * This setting is final once the view is created.  It must be set in the builder using
     * {@link Builder#setThrowForKeyDoesNotExistOnGet(boolean)}.
     */
    public boolean getThrowForKeyDoesNotExistOnGet() {
        return _features.getThrowForKeyDoesNotExistOnGet();
    }

    /**
     * If {@code true}, throw an exception when {@link #remove(Object)} fails because the key does
     * not exist.
     * <p>
     * This setting is final once the view is created.  It must be set in the builder using
     * {@link Builder#setThrowForKeyDoesNotExistOnRemove(boolean)}.
     */
    public boolean getThrowForKeyDoesNotExistOnRemove() {
        return _features.getThrowForKeyDoesNotExistOnRemove();
    }

    /**
     * If {@code true}, throw an exception when calling:
     * <ul>
     *   <li>{@link #put(Object, Object)} and argument {@code key} already exists in underlying
     *       map</li>
     *   <li>{@link #putAll(Map)} and argument {@code map} has a key that already exists in
     *       underlying map</li>
     * </ul>
     * <p>
     * This setting is final once the view is created.  It must be set in the builder using
     * {@link Builder#setThrowForKeyExistsOnPut(boolean)}.
     */
    public boolean getThrowForKeyExistsOnPut() {
        return _features.getThrowForKeyExistsOnPut();
    }

    /**
     * If {@code true}, throw an exception when calling any method that changes the underlying map.
     * <p>
     * This setting is final once the view is created.  It must be set in the builder using
     * {@link Builder#setThrowForMutate(boolean)}.
     */
    public boolean getThrowForMutate() {
        return _features.getThrowForMutate();
    }
    
    @Override
    public int size() {
        int x = _map.size();
        return x;
    }

    @Override
    public boolean isEmpty() {
        boolean b = _map.isEmpty();
        return b;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean b = _map.containsKey(key);
        return b;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean b = _map.containsValue(value);
        return b;
    }

    /**
     * @throws UnsupportedOperationException if {@link #getThrowForKeyDoesNotExistOnGet()} is
     *         {@code true} and the underlying map does not contain {@code key}
     */
    @Override
    public TValue get(Object key) {
        if (getThrowForKeyDoesNotExistOnGet() && !_map.containsKey(key)) {
            String msg = _features.formatMessage(this.getClass(), "Key does not exist: '%s'", key);
            throw new UnsupportedOperationException(msg);
        }
        TValue x = _map.get(key);
        return x;
    }

    /**
     * @throws NullPointerException if {@link #getThrowForNullKey()} is {@code true} and
     *         {@code key} is {@code null},
     *         <br>or if {@link #getThrowForNullValue()} is {@code true} and {@code value} is
     *         {@code null}
     * @throws UnsupportedOperationException if {@link #getThrowForKeyExistsOnPut()} is
     *         {@code true} and {@code key} already exists,
     *         <br>or if {@link #getThrowForMutate()} is {@code true}, {@code key} already, and
     *         {@code value} does not equal the current value
     *         <br>or if {@link #getThrowForMutate()} is {@code true} and {@code key} does
     *         not exist
     */
    @Override
    public TValue put(TKey key, TValue value) {
        String msg = "";
        if (!getThrowForNullKey() && null == key) {
            msg += "Key is null";
        }
        if (!getThrowForNullValue() && null == value) {
            if (!msg.isEmpty()) {
                msg += StringUtils.NEW_LINE;
            }
            msg += "Value is null";
        }
        if (!msg.isEmpty()) {
            String msg2 = _features.formatMessage(this.getClass(), msg);
            throw new UnsupportedOperationException(msg2);
        }
        
        if (getThrowForKeyExistsOnPut() && _map.containsKey(key)) {
            String msg2 =
                _features.formatMessage(this.getClass(), "Key already exists: '%s'", key);
            throw new UnsupportedOperationException(msg2);
        }
        
        if (getThrowForMutate()) {
            boolean throwFlag = false;
            if (_map.containsKey(key)) {
                TValue oldValue = _map.get(key);
                if (!Objects.equal(oldValue, value)) {
                    throwFlag = true;
                }
            }
            else {
                throwFlag = true;
            }
            if (throwFlag) {
                String msg2 = _features.formatMessage(this.getClass(), "Map is immutable");
                throw new UnsupportedOperationException(msg2);
            }
        }
        
        TValue oldValue = _map.put(key, value);
        return oldValue;
    }

    /**
     * @throws UnsupportedOperationException if {@link #getThrowForKeyDoesNotExistOnRemove()} is
     *         {@code true} and {@code key} does not exist,
     *         <br>or if {@link #getThrowForMutate()} is {@code true} and {@code key} already
     *         exists
     */
    @Override
    public TValue remove(Object key) {
        if (getThrowForKeyDoesNotExistOnRemove() && !_map.containsKey(key)) {
            String msg = _features.formatMessage(this.getClass(), "Key does not exist: '%s'", key);
            throw new UnsupportedOperationException(msg);
        }
        if (getThrowForMutate() && _map.containsKey(key)) {
            String msg2 = _features.formatMessage(this.getClass(), "Map is immutable");
            throw new UnsupportedOperationException(msg2);
        }
        
        TValue value = _map.remove(key);
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Repeatedly calls {@link #put(Object, Object)}.
     * 
     * @throws NullPointerException if {@code map} is {@code null}
     */
    @Override
    public void putAll(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");
        if (map.isEmpty()) {
            return;
        }
        _features.checkMap(this.getClass(), map);
        for (Map.Entry<? extends TKey, ? extends TValue> entry: map.entrySet()) {
            TKey key = entry.getKey();
            TValue value = entry.getValue();
            this.put(key, value);
        }
    }

    /**
     * @throws UnsupportedOperationException if {@link #getThrowForMutate()} is {@code true} and
     *         map is not empty
     */
    @Override
    public void clear() {
        if (getThrowForMutate() && !_map.isEmpty()) {
            String msg = _features.formatMessage(this.getClass(),
                "Cannot clear non-empty immutable map (size: %d)", _map.size());
            throw new UnsupportedOperationException(msg);
        }
        _map.clear();
    }

    @Override
    public Set<TKey> keySet() {
        Set<TKey> x = _map.keySet();
        return x;
    }

    @Override
    public Collection<TValue> values() {
        Collection<TValue> x = _map.values();
        return x;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Creates a set of map entries where {@link Entry#setValue(Object)} respects the settings of
     * {@link #getThrowForNullValue()} and {@link #getThrowForMutate()}.
     */
    @Override
    public Set<Map.Entry<TKey, TValue>> entrySet() {
        int size = size();
        Set<Map.Entry<TKey, TValue>> set = new HashSet<Map.Entry<TKey, TValue>>(size);
        for (Map.Entry<TKey, TValue> entry: _map.entrySet()) {
            Map.Entry<TKey, TValue> entry2 = new SafeEntryView<TKey, TValue>(this._features, entry);
            set.add(entry2);
        }
        Set<Map.Entry<TKey, TValue>> set2 = Collections.unmodifiableSet(set);
        return set2;
    }

    private static class SafeEntryView<TKey, TValue>
    implements Map.Entry<TKey, TValue> {
        
        private final Features _features;
        private final Map.Entry<TKey, TValue> _entry;

        public SafeEntryView(Features features, Map.Entry<TKey, TValue> entry) {
            _features = features;
            _entry = entry;
        }

        /**
         * @throws UnsupportedOperationException if {@link #getThrowForNullValue()} is {@code true}
         *         and {@code value} is {@code null},
         *         <br>or if {@link #getThrowForMutate()} is {@code true} and {@code value} does 
         *         not equal the current value
         */
        @Override
        public TValue setValue(TValue value) {
            if (_features.getThrowForNullValue() && null == value) {
                String msg = _features.formatMessage(this.getClass(), "Value is null");
                throw new UnsupportedOperationException(msg);
            }
            if (_features.getThrowForMutate()) {
                TValue oldValue = _entry.getValue();
                if (!Objects.equal(oldValue, value)) {
                    String msg2 = _features.formatMessage(this.getClass(), "Map is immutable");
                    throw new UnsupportedOperationException(msg2);
                }
            }
            TValue oldValue = _entry.setValue(value);
            return oldValue;
        }

        @Override
        public TKey getKey() {
            TKey x = _entry.getKey();
            return x;
        }

        @Override
        public TValue getValue() {
            TValue x = _entry.getValue();
            return x;
        }
    }
}
