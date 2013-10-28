package com.googlecode.kevinarpe.papaya.argument;

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

import java.util.Iterator;

final class ValueAsTypeIterator {

    // Disable default constructor
    private ValueAsTypeIterator() {
    }

    interface _IValueAsTypeIterator {
        
        public boolean hasNext();
        
        public int nextIndex();
        
        public String getUnderlyingDescription();
    }

    interface _IValueAsLongIterator
    extends _IValueAsTypeIterator {
        
        public long nextAsLong();
    }

    interface _IValueAsDoubleIterator
    extends _IValueAsTypeIterator {
        
        public double nextAsDouble();
    }
    
    static abstract class _AbstractValueAsTypeIterator
    implements _IValueAsTypeIterator {
        
        private int _index;
        
        protected _AbstractValueAsTypeIterator() {
            _index = -1;
        }
        
        protected int incrementIndex() {
            ++_index;
            return _index;
        }
        
        @Override
        public int nextIndex() {
            return 1 + _index;
        }
    }
    
    static abstract class _AbstractValueAsTypeArrayIterator
    extends _AbstractValueAsTypeIterator {

        private final int _size;
        
        protected _AbstractValueAsTypeArrayIterator(int size) {
            _size = size;
        }
        
        @Override
        public boolean hasNext() {
            int index = nextIndex();
            boolean x = (index < _size);
            return x;
        }
        
        @Override
        public String getUnderlyingDescription() {
            return "Array";
        }
    }
    
    static abstract class _AbstractValueAsTypeIterableIterator<TValue>
    extends _AbstractValueAsTypeIterator {

        private Iterator<TValue> _iter;
        
        protected _AbstractValueAsTypeIterableIterator(Iterable<TValue> iterable) {
            super();
            _iter = iterable.iterator();
        }
        
        protected Iterator<TValue> iter() {
            return _iter;
        }
        
        @Override
        public boolean hasNext() {
            boolean x = _iter.hasNext();
            return x;
        }
        
        protected TValue next() {
            TValue x = _iter.next();
            incrementIndex();
            return x;
        }
        
        @Override
        public String getUnderlyingDescription() {
            return "Iterable";
        }
    }
    
    static abstract class _AbstractValueAsLongArrayIterator
    extends _AbstractValueAsTypeArrayIterator
    implements _IValueAsLongIterator {
        
        public _AbstractValueAsLongArrayIterator(int size) {
            super(size);
        }
        
        @Override
        public long nextAsLong() {
            int index = nextIndex();
            long x = getValueAsLong(index);
            incrementIndex();
            return x;
        }
        
        abstract protected long getValueAsLong(int index);
    }
    
    static abstract class _AbstractValueAsDoubleArrayIterator
    extends _AbstractValueAsTypeArrayIterator
    implements _IValueAsDoubleIterator {
        
        public _AbstractValueAsDoubleArrayIterator(int size) {
            super(size);
        }
        
        @Override
        public double nextAsDouble() {
            int index = nextIndex();
            double x = getValueAsDouble(index);
            incrementIndex();
            return x;
        }
        
        abstract protected double getValueAsDouble(int index);
    }

    static class _UncheckedPrimitiveByteArrayAsLongIterator
            extends _AbstractValueAsLongArrayIterator {

        private byte[] _arr;

        public _UncheckedPrimitiveByteArrayAsLongIterator(byte[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }

    static class _UncheckedPrimitiveIntArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {
        
        private int[] _arr;
        
        public _UncheckedPrimitiveIntArrayAsLongIterator(int[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }
    
    static class _UncheckedPrimitiveLongArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {
        
        private long[] _arr;
        
        public _UncheckedPrimitiveLongArrayAsLongIterator(long[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }
    
    static class _UncheckedPrimitiveFloatArrayAsDoubleIterator
    extends _AbstractValueAsDoubleArrayIterator {
        
        private float[] _arr;
        
        public _UncheckedPrimitiveFloatArrayAsDoubleIterator(float[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected double getValueAsDouble(int index) {
            double x = _arr[index];
            return x;
        }
    }
    
    static class _UncheckedPrimitiveDoubleArrayAsDoubleIterator
    extends _AbstractValueAsDoubleArrayIterator {
        
        private double[] _arr;
        
        public _UncheckedPrimitiveDoubleArrayAsDoubleIterator(double[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected double getValueAsDouble(int index) {
            double x = _arr[index];
            return x;
        }
    }

    static class _UncheckedByteObjectArrayAsLongIterator
            extends _AbstractValueAsLongArrayIterator {

        private Byte[] _arr;

        public _UncheckedByteObjectArrayAsLongIterator(Byte[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }

    static class _UncheckedIntObjectArrayAsLongIterator
            extends _AbstractValueAsLongArrayIterator {

        private Integer[] _arr;

        public _UncheckedIntObjectArrayAsLongIterator(Integer[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }

    static class _UncheckedLongObjectArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {
        
        private Long[] _arr;
        
        public _UncheckedLongObjectArrayAsLongIterator(Long[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }
    
    static class _UncheckedFloatObjectArrayAsDoubleIterator
    extends _AbstractValueAsDoubleArrayIterator {
        
        private Float[] _arr;
        
        public _UncheckedFloatObjectArrayAsDoubleIterator(Float[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected double getValueAsDouble(int index) {
            double x = _arr[index];
            return x;
        }
    }
    
    static class _UncheckedDoubleObjectArrayAsDoubleIterator
    extends _AbstractValueAsDoubleArrayIterator {
        
        private Double[] _arr;
        
        public _UncheckedDoubleObjectArrayAsDoubleIterator(Double[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected double getValueAsDouble(int index) {
            double x = _arr[index];
            return x;
        }
    }

    static class _UncheckedByteObjectIterableAsLongIterator
    extends _AbstractValueAsTypeIterableIterator<Byte>
    implements _IValueAsLongIterator {

        public _UncheckedByteObjectIterableAsLongIterator(Iterable<Byte> iterable) {
            super(iterable);
        }

        @Override
        public long nextAsLong() {
            long x = next();
            return x;
        }
    }

    static class _UncheckedIntObjectIterableAsLongIterator
    extends _AbstractValueAsTypeIterableIterator<Integer>
    implements _IValueAsLongIterator {

        public _UncheckedIntObjectIterableAsLongIterator(Iterable<Integer> iterable) {
            super(iterable);
        }

        @Override
        public long nextAsLong() {
            long x = next();
            return x;
        }
    }
    
    static class _UncheckedLongObjectIterableAsLongIterator
    extends _AbstractValueAsTypeIterableIterator<Long>
    implements _IValueAsLongIterator {

        public _UncheckedLongObjectIterableAsLongIterator(Iterable<Long> iterable) {
            super(iterable);
        }

        @Override
        public long nextAsLong() {
            long x = next();
            return x;
        }
    }
    
    static class _UncheckedFloatObjectIterableAsDoubleIterator
    extends _AbstractValueAsTypeIterableIterator<Float>
    implements _IValueAsDoubleIterator {

        public _UncheckedFloatObjectIterableAsDoubleIterator(Iterable<Float> iterable) {
            super(iterable);
        }

        @Override
        public double nextAsDouble() {
            double x = next();
            return x;
        }
    }
    
    static class _UncheckedDoubleObjectIterableAsDoubleIterator
    extends _AbstractValueAsTypeIterableIterator<Double>
    implements _IValueAsDoubleIterator {

        public _UncheckedDoubleObjectIterableAsDoubleIterator(Iterable<Double> iterable) {
            super(iterable);
        }

        @Override
        public double nextAsDouble() {
            double x = next();
            return x;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // _IValueAsLongIterator Helpers
    //
    
    static void _checkPositive(_IValueAsLongIterator iter, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value <= 0) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is not positive: %d%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkNotPositive(_IValueAsLongIterator iter, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value > 0) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is positive: %d%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkNegative(_IValueAsLongIterator iter, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value >= 0) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is not negative: %d%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkNotNegative(_IValueAsLongIterator iter, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value < 0) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is negative: %d%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    private static void _checkMinAndMaxRangeValues(
            _IValueAsLongIterator iter, long minRangeValue, long maxRangeValue, String argName) {
        if (minRangeValue > maxRangeValue) {
            final String desc = iter.getUnderlyingDescription();
            final String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': 'minRangeValue' > 'maxRangeValue': %d > %d%s",
                desc, argName, minRangeValue, maxRangeValue, w));
        }
    }
    
    static void _checkValueInsideRange(
            _IValueAsLongIterator iter, long minRangeValue, long maxRangeValue, String argName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, argName);
        
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value < minRangeValue || value > maxRangeValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d outside valid range: (min) %d <= %d <= %d (max)%s",
                    desc, argName, index, minRangeValue, value, maxRangeValue, w));
            }
        }
    }
    
    static void _checkValueOutsideRange(
            _IValueAsLongIterator iter, long minRangeValue, long maxRangeValue, String argName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, argName);
        
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value >= minRangeValue && value <= maxRangeValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d inside invalid range: (min) %d >= %d <= %d (max)%s",
                    desc, argName, index, minRangeValue, value, maxRangeValue, w));
            }
        }
    }
    
    static void _checkMinValue(_IValueAsLongIterator iter, long minValue, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value < minValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d < 'minValue': %d < %d%s",
                    desc, argName, index, value, minValue, w));
            }
        }
    }
    
    static void _checkMaxValue(_IValueAsLongIterator iter, long maxValue, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value > maxValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d > 'maxValue': %d > %d%s",
                    desc, argName, index, value, maxValue, w));
            }
        }
    }
    
    static void _checkExactValue(_IValueAsLongIterator iter, long exactValue, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value != exactValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d != 'exactValue': %d != %d%s",
                    desc, argName, index, value, exactValue, w));
            }
        }
    }
    
    static void _checkNotExactValue(_IValueAsLongIterator iter, long exactValue, String argName) {
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value == exactValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d == 'exactValue': %d == %d%s",
                    desc, argName, index, value, exactValue, w));
            }
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // _IValueAsDoubleIterator Helpers
    //
    
    static void _checkPositive(_IValueAsDoubleIterator iter, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value <= 0.0d) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is not positive: %f%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkNotPositive(_IValueAsDoubleIterator iter, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value > 0.0d) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is positive: %f%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkNegative(_IValueAsDoubleIterator iter, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value >= 0.0d) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is not negative: %f%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkNotNegative(_IValueAsDoubleIterator iter, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < 0.0d) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d is negative: %f%s",
                    desc, argName, index, value, w));
            }
        }
    }
    
    static void _checkValueRange(
            _IValueAsDoubleIterator iter, double minValue, double maxValue, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < minValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d < 'minValue': %f < %f%s",
                    desc, argName, index, value, minValue, w));
            }
            if (value > maxValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d > 'maxValue': %f > %f%s",
                    desc, argName, index, value, maxValue, w));
            }
        }
    }
    
    private static void _checkMinAndMaxRangeValues(
            _IValueAsDoubleIterator iter,
            double minRangeValue,
            double maxRangeValue,
            String argName) {
        if (minRangeValue > maxRangeValue) {
            final String desc = iter.getUnderlyingDescription();
            final String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "%s argument '%s': 'minRangeValue' > 'maxRangeValue': %f > %f%s",
                desc, argName, minRangeValue, maxRangeValue, w));
        }
    }
    
    static void _checkValueInsideRange(
            _IValueAsDoubleIterator iter,
            double minRangeValue,
            double maxRangeValue,
            String argName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, argName);
        
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < minRangeValue || value > maxRangeValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d outside valid range: (min) %f <= %f >= %f (max)%s",
                    desc, argName, index, minRangeValue, value, maxRangeValue, w));
            }
        }
    }
    
    static void _checkValueOutsideRange(
            _IValueAsDoubleIterator iter,
            double minRangeValue,
            double maxRangeValue,
            String argName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, argName);
        
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value >= minRangeValue && value <= maxRangeValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d inside invalid range: (min) %f >= %f <= %f (max)%s",
                    desc, argName, index, minRangeValue, value, maxRangeValue, w));
            }
        }
    }
    
    static void _checkMinValue(_IValueAsDoubleIterator iter, double minValue, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < minValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d < 'minValue': %f < %f%s",
                    desc, argName, index, value, minValue, w));
            }
        }
    }
    
    static void _checkMaxValue(_IValueAsDoubleIterator iter, double maxValue, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value > maxValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d > 'maxValue': %f > %f%s",
                    desc, argName, index, value, maxValue, w));
            }
        }
    }
    
    static void _checkExactValue(_IValueAsDoubleIterator iter, double exactValue, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value != exactValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d != 'exactValue': %f != %f%s",
                    desc, argName, index, value, exactValue, w));
            }
        }
    }
    
    static void _checkNotExactValue(
            _IValueAsDoubleIterator iter, double exactValue, String argName) {
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value == exactValue) {
                final String desc = iter.getUnderlyingDescription();
                final int index = iter.nextIndex() - 1;
                final String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new IllegalArgumentException(String.format(
                    "%s argument '%s': Value at index %d == 'exactValue': %f == %f%s",
                    desc, argName, index, value, exactValue, w));
            }
        }
    }
}
