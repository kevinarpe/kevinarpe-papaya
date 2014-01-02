package com.googlecode.kevinarpe.papaya.argument;

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

    interface _IValueAsComparableIterator<TValue extends Comparable<? super TValue>>
    extends _IValueAsTypeIterator {

        public TValue nextAsComparable();
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

    static abstract class _AbstractValueAsComparableArrayIterator<TValue extends Comparable<? super TValue>>
    extends _AbstractValueAsTypeArrayIterator
    implements _IValueAsComparableIterator<TValue> {

        public _AbstractValueAsComparableArrayIterator(int size) {
            super(size);
        }

        @Override
        public TValue nextAsComparable() {
            int index = nextIndex();
            TValue x = getValueAsComparable(index);
            incrementIndex();
            return x;
        }

        abstract protected TValue getValueAsComparable(int index);
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

    static class _UncheckedPrimitiveCharArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {

        private char[] _arr;

        public _UncheckedPrimitiveCharArrayAsLongIterator(char[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }

    static class _UncheckedPrimitiveShortArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {

        private short[] _arr;

        public _UncheckedPrimitiveShortArrayAsLongIterator(short[] arr) {
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

    static class _UncheckedCharObjectArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {

        private Character[] _arr;

        public _UncheckedCharObjectArrayAsLongIterator(Character[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected long getValueAsLong(int index) {
            long x = _arr[index];
            return x;
        }
    }

    static class _UncheckedShortObjectArrayAsLongIterator
    extends _AbstractValueAsLongArrayIterator {

        private Short[] _arr;

        public _UncheckedShortObjectArrayAsLongIterator(Short[] arr) {
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

    static class _UncheckedComparableArrayAsComparableIterator<TValue extends Comparable<? super TValue>>
    extends _AbstractValueAsComparableArrayIterator<TValue> {

        private TValue[] _arr;

        public _UncheckedComparableArrayAsComparableIterator(TValue[] arr) {
            super(arr.length);
            _arr = arr;
        }

        @Override
        protected TValue getValueAsComparable(int index) {
            TValue x = _arr[index];
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

    static class _UncheckedCharObjectIterableAsLongIterator
    extends _AbstractValueAsTypeIterableIterator<Character>
    implements _IValueAsLongIterator {

        public _UncheckedCharObjectIterableAsLongIterator(Iterable<Character> iterable) {
            super(iterable);
        }

        @Override
        public long nextAsLong() {
            long x = next();
            return x;
        }
    }

    static class _UncheckedShortObjectIterableAsLongIterator
    extends _AbstractValueAsTypeIterableIterator<Short>
    implements _IValueAsLongIterator {

        public _UncheckedShortObjectIterableAsLongIterator(Iterable<Short> iterable) {
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

    static class _UncheckedComparableIterableAsComparableIterator<TValue extends Comparable<? super TValue>>
    extends _AbstractValueAsTypeIterableIterator<TValue>
    implements _IValueAsComparableIterator<TValue> {

        public _UncheckedComparableIterableAsComparableIterator(Iterable<TValue> iterable) {
            super(iterable);
        }

        @Override
        public TValue nextAsComparable() {
            TValue x = next();
            return x;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // _IValueAsLongIterator Helpers
    //
    
    static void _checkPositive(_IValueAsLongIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value <= 0) {
                throwPositiveException(iter, value, argName);
            }
        }
    }
    
    static void _checkNotPositive(_IValueAsLongIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value > 0) {
                throwNotPositiveException(iter, value, argName);
            }
        }
    }
    
    static void _checkNegative(_IValueAsLongIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value >= 0) {
                throwNegativeException(iter, value, argName);
            }
        }
    }
    
    static void _checkNotNegative(_IValueAsLongIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value < 0) {
                throwNotNegativeException(iter, value, argName);
            }
        }
    }
    
    private static void _checkMinAndMaxRangeValues(
            _IValueAsLongIterator iter,
            long minRangeValue,
            long maxRangeValue,
            String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (minRangeValue > maxRangeValue) {
            throwMinAndMaxRangeValuesException(iter, minRangeValue, maxRangeValue, iterArgName);
        }
    }
    
    static void _checkValueInsideRange(
            _IValueAsLongIterator iter,
            long minRangeValue,
            long maxRangeValue,
            String iterArgName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, iterArgName);
        
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value < minRangeValue || value > maxRangeValue) {
                throwValueInsideRangeException(
                    iter, value, minRangeValue, maxRangeValue, iterArgName);
            }
        }
    }
    
    static void _checkValueOutsideRange(
            _IValueAsLongIterator iter,
            long minRangeValue,
            long maxRangeValue,
            String iterArgName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, iterArgName);
        
        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value >= minRangeValue && value <= maxRangeValue) {
                throwValueOutsideRangeException(
                    iter, value, minRangeValue, maxRangeValue, iterArgName);
            }
        }
    }
    
    static void _checkMinValue(_IValueAsLongIterator iter, long minValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value < minValue) {
                throwMinValueException(iter, value, minValue, iterArgName);
            }
        }
    }
    
    static void _checkMaxValue(_IValueAsLongIterator iter, long maxValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value > maxValue) {
                throwMaxValueException(iter, value, maxValue, iterArgName);
            }
        }
    }
    
    static void _checkExactValue(_IValueAsLongIterator iter, long exactValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value != exactValue) {
                throwExactValueException(iter, value, exactValue, iterArgName);
            }
        }
    }
    
    static void _checkNotExactValue(
            _IValueAsLongIterator iter, long exactValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final long value = iter.nextAsLong();
            if (value == exactValue) {
                throwNotExactValueException(iter, value, exactValue, iterArgName);
            }
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // _IValueAsDoubleIterator Helpers
    //
    
    static void _checkPositive(_IValueAsDoubleIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value <= 0.0d) {
                throwPositiveException(iter, value, argName);
            }
        }
    }

    static void _checkNotPositive(_IValueAsDoubleIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value > 0.0d) {
                throwNotPositiveException(iter, value, argName);
            }
        }
    }

    static void _checkNegative(_IValueAsDoubleIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value >= 0.0d) {
                throwNegativeException(iter, value, argName);
            }
        }
    }

    static void _checkNotNegative(_IValueAsDoubleIterator iter, String argName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < 0.0d) {
                throwNotNegativeException(iter, value, argName);
            }
        }
    }

    // TODO: Safe to remove?  Discovered on 2013-11-03
//    static void _checkValueRange(
//            _IValueAsDoubleIterator iter, double minValue, double maxValue, String argName) {
//        while (iter.hasNext()) {
//            final double value = iter.nextAsDouble();
//            if (value < minValue) {
//                final String desc = iter.getUnderlyingDescription();
//                final int index = iter.nextIndex() - 1;
//                final String w = StringArgs._getArgNameWarning(argName, "argName");
//                throw new IllegalArgumentException(String.format(
//                    "%s argument '%s': Value at index %d < 'minValue': %f < %f%s",
//                    desc, argName, index, value, minValue, w));
//            }
//            if (value > maxValue) {
//                final String desc = iter.getUnderlyingDescription();
//                final int index = iter.nextIndex() - 1;
//                final String w = StringArgs._getArgNameWarning(argName, "argName");
//                throw new IllegalArgumentException(String.format(
//                    "%s argument '%s': Value at index %d > 'maxValue': %f > %f%s",
//                    desc, argName, index, value, maxValue, w));
//            }
//        }
//    }
    
    private static void _checkMinAndMaxRangeValues(
            _IValueAsDoubleIterator iter,
            double minRangeValue,
            double maxRangeValue,
            String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (minRangeValue > maxRangeValue) {
            throwMinAndMaxRangeValuesException(iter, minRangeValue, maxRangeValue, iterArgName);
        }
    }
    
    static void _checkValueInsideRange(
            _IValueAsDoubleIterator iter,
            double minRangeValue,
            double maxRangeValue,
            String iterArgName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, iterArgName);
        
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < minRangeValue || value > maxRangeValue) {
                throwValueInsideRangeException(
                    iter, value, minRangeValue, maxRangeValue, iterArgName);
            }
        }
    }
    
    static void _checkValueOutsideRange(
            _IValueAsDoubleIterator iter,
            double minRangeValue,
            double maxRangeValue,
            String iterArgName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, iterArgName);
        
        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value >= minRangeValue && value <= maxRangeValue) {
                throwValueOutsideRangeException(
                    iter, value, minRangeValue, maxRangeValue, iterArgName);
            }
        }
    }
    
    static void _checkMinValue(_IValueAsDoubleIterator iter, double minValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value < minValue) {
                throwMinValueException(iter, value, minValue, iterArgName);
            }
        }
    }
    
    static void _checkMaxValue(_IValueAsDoubleIterator iter, double maxValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value > maxValue) {
                throwMaxValueException(iter, value, maxValue, iterArgName);
            }
        }
    }
    
    static void _checkExactValue(
            _IValueAsDoubleIterator iter, double exactValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value != exactValue) {
                throwExactValueException(iter, value, exactValue, iterArgName);
            }
        }
    }
    
    static void _checkNotExactValue(
            _IValueAsDoubleIterator iter, double exactValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final double value = iter.nextAsDouble();
            if (value == exactValue) {
                throwNotExactValueException(iter, value, exactValue, iterArgName);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // _IValueAsComparableIterator Helpers
    //

    private static <TValue extends Comparable<? super TValue>> void _checkMinAndMaxRangeValues(
            _IValueAsComparableIterator<TValue> iter,
            TValue minRangeValue,
            TValue maxRangeValue,
            String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");
        ObjectArgs.checkNotNull(minRangeValue, "minRangeValue");
        ObjectArgs.checkNotNull(maxRangeValue, "maxRangeValue");

        if (minRangeValue.compareTo(maxRangeValue) > 0) {
            throwMinAndMaxRangeValuesException(iter, minRangeValue, maxRangeValue, iterArgName);
        }
    }

    static <TValue extends Comparable<? super TValue>> void _checkValueInsideRange(
            _IValueAsComparableIterator<TValue> iter,
            TValue minRangeValue,
            TValue maxRangeValue,
            String iterArgName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, iterArgName);

        while (iter.hasNext()) {
            final TValue value = iter.nextAsComparable();
            if (value.compareTo(minRangeValue) < 0 || value.compareTo(maxRangeValue) > 0) {
                throwValueInsideRangeException(iter, value, minRangeValue, maxRangeValue, iterArgName);
            }
        }
    }

    static <TValue extends Comparable<? super TValue>> void _checkValueOutsideRange(
            _IValueAsComparableIterator<TValue> iter,
            TValue minRangeValue,
            TValue maxRangeValue,
            String iterArgName) {
        _checkMinAndMaxRangeValues(iter, minRangeValue, maxRangeValue, iterArgName);

        while (iter.hasNext()) {
            final TValue value = iter.nextAsComparable();
            if (value.compareTo(minRangeValue) >= 0 && value.compareTo(maxRangeValue) <= 0) {
                throwValueOutsideRangeException(iter, value, minRangeValue, maxRangeValue, iterArgName);
            }
        }
    }

    static <TValue extends Comparable<? super TValue>> void _checkMinValue(
            _IValueAsComparableIterator<TValue> iter, TValue minValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");
        ObjectArgs.checkNotNull(minValue, "minValue");

        while (iter.hasNext()) {
            final TValue value = iter.nextAsComparable();
            if (value.compareTo(minValue) < 0) {
                throwMinValueException(iter, value, minValue, iterArgName);
            }
        }
    }

    static <TValue extends Comparable<? super TValue>> void _checkMaxValue(
            _IValueAsComparableIterator<TValue> iter, TValue maxValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");
        ObjectArgs.checkNotNull(maxValue, "maxValue");

        while (iter.hasNext()) {
            final TValue value = iter.nextAsComparable();
            if (value.compareTo(maxValue) > 0) {
                throwMaxValueException(iter, value, maxValue, iterArgName);
            }
        }
    }

    static <TValue extends Comparable<? super TValue>> void _checkExactValue(
        _IValueAsComparableIterator<TValue> iter, TValue exactValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final TValue value = iter.nextAsComparable();
            if (value.compareTo(exactValue) != 0) {
                throwExactValueException(iter, value, exactValue, iterArgName);
            }
        }
    }

    static <TValue extends Comparable<? super TValue>> void _checkNotExactValue(
        _IValueAsComparableIterator<TValue> iter, TValue exactValue, String iterArgName) {
        ObjectArgs.checkNotNull(iter, "iter");

        while (iter.hasNext()) {
            final TValue value = iter.nextAsComparable();
            if (value.compareTo(exactValue) == 0) {
                throwNotExactValueException(iter, value, exactValue, iterArgName);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // throw*Exception Helpers
    //

    private static <TValue> void throwPositiveException(
            _IValueAsTypeIterator iter, TValue value, String argName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(argName, "argName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d is not positive: %s%s",
            desc, argName, index, value, w));
    }

    private static <TValue> void throwNotPositiveException(
            _IValueAsTypeIterator iter, TValue value, String argName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(argName, "argName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d is positive: %s%s",
            desc, argName, index, value, w));
    }

    private static <TValue> void throwNegativeException(
            _IValueAsTypeIterator iter, TValue value, String argName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(argName, "argName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d is not negative: %s%s",
            desc, argName, index, value, w));
    }

    private static <TValue> void throwNotNegativeException(
            _IValueAsTypeIterator iter, TValue value, String argName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(argName, "argName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d is negative: %s%s",
            desc, argName, index, value, w));
    }

    private static <TValue> void throwMinAndMaxRangeValuesException(
            _IValueAsTypeIterator iter,
            TValue minRangeValue,
            TValue maxRangeValue,
            String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': 'minRangeValue' > 'maxRangeValue': %s > %s%s",
            desc, iterArgName, minRangeValue, maxRangeValue, w));
    }

    private static <TValue> void throwValueInsideRangeException(
            _IValueAsTypeIterator iter,
            TValue value,
            TValue minRangeValue,
            TValue maxRangeValue,
            String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d outside valid range: (min) %s < %s > %s (max)%s",
            desc, iterArgName, index, minRangeValue, value, maxRangeValue, w));
    }

    private static <TValue> void throwValueOutsideRangeException(
            _IValueAsTypeIterator iter,
            TValue value,
            TValue minRangeValue,
            TValue maxRangeValue,
            String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d inside invalid range: (min) %s >= %s <= %s (max)%s",
            desc, iterArgName, index, minRangeValue, value, maxRangeValue, w));
    }

    private static <TValue> void throwMinValueException(
            _IValueAsTypeIterator iter, TValue value, TValue minValue, String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d < 'minValue': %s < %s%s",
            desc, iterArgName, index, value, minValue, w));
    }

    private static <TValue> void throwMaxValueException(
            _IValueAsTypeIterator iter, TValue value, TValue maxValue, String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d > 'maxValue': %s > %s%s",
            desc, iterArgName, index, value, maxValue, w));
    }

    private static <TValue> void throwExactValueException(
            _IValueAsTypeIterator iter, TValue value, TValue exactValue, String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d != 'exactValue': %s != %s%s",
            desc, iterArgName, index, value, exactValue, w));
    }

    private static <TValue> void throwNotExactValueException(
            _IValueAsTypeIterator iter, TValue value, TValue exactValue, String iterArgName) {
        final String desc = iter.getUnderlyingDescription();
        final int index = iter.nextIndex() - 1;
        final String w = StringArgs._getArgNameWarning(iterArgName, "iterArgName");
        throw new IllegalArgumentException(String.format(
            "%s argument '%s': Value at index %d == 'exactValue': %s == %s%s",
            desc, iterArgName, index, value, exactValue, w));
    }
}
